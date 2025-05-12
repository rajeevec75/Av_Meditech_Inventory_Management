/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.PurchaseService;
import com.AvMeditechInventory.service.StockReportService;
import com.AvMeditechInventory.util.PaginationUtil;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Controller
public class StockReportController {

    @Autowired
    private StockReportService stockReportService;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/stockReportProductWise")
    public String stockReportProductWisePage(
            @RequestParam(required = true, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = true, defaultValue = "100") Integer pageSize,
            Model model,
            HttpSession session, RedirectAttributes redirectAttributes,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand, HttpServletRequest request) {

        try {
            // Check authorization
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                model.addAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Retrieve channel ID from session
            Integer channelId = (Integer) session.getAttribute("selectedStoreId");
            if (channelId == null) {
                model.addAttribute("errorMessage", "Store ID is missing from the session.");
                return "stockReportProductWise";
            }

            // Retrieve categories
            List<Category> fetchAllCategories = this.paginationUtil.fetchAllCategories(pageNumber, authToken, request);
            model.addAttribute("categoryList", fetchAllCategories);

            // Retrieve product types
            List<ProductType> fetchAllProductTypes = this.paginationUtil.fetchAllProductTypes(pageNumber, authToken, request);
            model.addAttribute("productTypeList", fetchAllProductTypes);

            // Retrieve stock report data
            DataResult<List<Response>> stockReportProductWise = stockReportService.getStockReportProductWise(channelId, pageNumber, pageSize, productName, productType, brand);

            List<Response> responseList = stockReportProductWise.getData();

            // Retrieve total items count
            int totalItems = this.serviceDao.countTotalStockReportProductWiseByChannelId(channelId);
            int noOfPages = (int) Math.ceil((double) totalItems / pageSize);

            model.addAttribute("stockReportProductWiseList", responseList);
            // Add pageNumber and pageSize to the model
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("noOfPages", noOfPages);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("productName", productName); // Add Model productName
            model.addAttribute("productType", productType); // Add Model productName
            model.addAttribute("brand", brand); // Add Model productName

            int totalQuantity = 0;
            for (int i = 0; i < responseList.size(); i++) {
                totalQuantity = totalQuantity + responseList.get(i).getQuantity();
            }
            model.addAttribute("totalQuantity", totalQuantity);

            return "stockReportProductWise";

        } catch (Exception exception) {
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "stockReportProductWise";
        }
    }

    @GetMapping("/stockReportProductWiseExportToExcel")
    public void stockReportProductWiseExportToExcel(
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            HttpServletResponse response, HttpSession session) throws IOException, ParseException {

        Integer pageNumber = null;
        Integer pageSize = null;

        String authToken = (String) session.getAttribute("token");
        if (authToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized to access this resource.");
            return;
        }

        Integer channelId = (Integer) session.getAttribute("selectedStoreId");
        if (channelId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Store ID is missing from the session.");
            return;
        }

        DataResult<List<Response>> stockReportProductWise;
        stockReportProductWise = this.stockReportService.getStockReportProductWise(channelId, pageNumber, pageSize, productName, productType, brand);
        List<Response> responses = stockReportProductWise.getData();

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=stock_report_product_wise_" + timestamp + ".xlsx");

        try ( Workbook workbook = new XSSFWorkbook()) {
            String sheetName = "Stock Report " + timestamp;
            Sheet sheet = workbook.createSheet(sheetName);

            // Define Headers
            List<String> headers = List.of("Stock Number", "Item Description", "Brand", "Product Type", "Quantity");

            // Create Header Row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            // Check if data is null or empty
            if (responses == null || responses.isEmpty()) {
                // Optionally, write "No Data Available" message
                Row row = sheet.createRow(1);
                row.createCell(0).setCellValue("No Data Available");
            } else {
                // Create Data Rows
                int rowNum = 1;
                for (Response product : responses) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(product.getSku());
                    row.createCell(1).setCellValue(product.getProductName());
                    row.createCell(2).setCellValue(product.getProductTypeName());
                    row.createCell(3).setCellValue(product.getCategoryName());
                    row.createCell(4).setCellValue(product.getQuantity());
                }
            }

            workbook.write(response.getOutputStream());
        }
    }

    @GetMapping("/stockReportSerialNumberWise")
    public String stockReportSerialNumberWisePage(
            @RequestParam(required = true, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = true, defaultValue = "100") Integer pageSize,
            Model model, HttpSession session, RedirectAttributes redirectAttributes,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "itemBarCode", required = false, defaultValue = "") String itemBarCode,
            @RequestParam(value = "purchaseItemSerialMasterId", required = false, defaultValue = "") Integer purchaseItemSerialMasterId, 
            HttpServletRequest request) {
        try {
            // Check authorization
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                model.addAttribute("errorMessage", "You are not authorized to access this resource.");
                return "stockReportSerialNumberWise";
            }

            // Retrieve channel ID from session
            Integer channelId = (Integer) session.getAttribute("selectedStoreId");
            if (channelId == null) {
                model.addAttribute("errorMessage", "Store ID is missing from the session.");
                return "stockReportSerialNumberWise";
            }

            // Retrieve categories
            List<Category> fetchAllCategories = this.paginationUtil.fetchAllCategories(pageNumber, authToken, request);
            model.addAttribute("categoryList", fetchAllCategories);

            // Retrieve product types
            List<ProductType> fetchAllProductTypes = this.paginationUtil.fetchAllProductTypes(pageNumber, authToken, request);
            model.addAttribute("productTypeList", fetchAllProductTypes);

            // Retrieve product list
            DataResult<List<ProductDto>> productListResult = purchaseService.getProductList();
            List<ProductDto> productList = productListResult.getData();
            model.addAttribute("productList", productList);

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If endDate is not provided, use the date 5 years from today
            Date endDate;
            if (endDateStr == null || endDateStr.isEmpty()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.YEAR, 5);
                endDate = calendar.getTime();
            } else {
                endDate = dateFormat.parse(endDateStr);
            }

            // Convert dates to strings
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);
            model.addAttribute("productName", productName);
            model.addAttribute("selectedBrandId", brand);
            model.addAttribute("selectedProductTypeId", productType);
            model.addAttribute("itemBarCode", itemBarCode);

            // Retrieve stock report data
            DataResult<List<Response>> stockReportSerialNumberWise = this.stockReportService.
                    getStockReportSerialNumberWise(channelId, pageNumber, pageSize, brand, productType, startDate, endDate, productName, itemBarCode, purchaseItemSerialMasterId);

            // Check if the service call was successful
            if (stockReportSerialNumberWise.isSuccess()) {
                List<Response> responses = stockReportSerialNumberWise.getData();

                // Check if the response list is empty
                if (responses == null || responses.isEmpty()) {
                    model.addAttribute("errorMessage", "No stock report data available.");
                    return "stockReportSerialNumberWise";
                }

                // Calculate pagination
                int totalItems = this.serviceDao.countTotalStockReportSerialNumberWiseByChannelId(channelId, brand, productType, startDate, endDate, productName);
                int noOfPages = (int) Math.ceil((double) totalItems / pageSize);

                // Add data to model
                model.addAttribute("stockReportSerialNumberWiseList", responses);
                model.addAttribute("currentPage", pageNumber);
                model.addAttribute("noOfPages", noOfPages);
                model.addAttribute("pageSize", pageSize);

                int totalQuantity = 0;
                for (int i = 0; i < responses.size(); i++) {
                    totalQuantity = totalQuantity + responses.get(i).getQuantity();
                }
                model.addAttribute("totalQuantity", totalQuantity);

                return "stockReportSerialNumberWise";
            } else {
                // Handle the case where service call was not successful
                model.addAttribute("errorMessage", stockReportSerialNumberWise.getMessage());
                return "stockReportSerialNumberWise";
            }
        } catch (ParseException exception) {
            // Handle any unexpected exceptions
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "stockReportSerialNumberWise";
        }
    }

    @GetMapping("/stockReportSerialNumberWiseExportToExcel")
    public void stockReportSerialNumberWiseExportToExcel(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "itemBarCode", required = false, defaultValue = "") String itemBarCode,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "purchaseItemSerialMasterId", required = false, defaultValue = "") Integer purchaseItemSerialMasterId,
            Model model, RedirectAttributes redirectAttributes, HttpServletResponse response, HttpSession session) throws IOException, ParseException {

        Integer pageNumber = null;
        Integer pageSize = null;

        String authToken = (String) session.getAttribute("token");
        if (authToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized to access this resource.");
            return;
        }

        Integer channelId = (Integer) session.getAttribute("selectedStoreId");
        if (channelId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Store ID is missing from the session.");
            return;
        }

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Parse start and end dates
        Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);
        Date endDate;
        if (endDateStr == null || endDateStr.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, 5);
            endDate = calendar.getTime();
        } else {
            endDate = dateFormat.parse(endDateStr);
        }

        DataResult<List<Response>> stockReportSerialNumberWise;
        stockReportSerialNumberWise = this.stockReportService.getStockReportSerialNumberWise(channelId, pageNumber, pageSize, brand, productType, startDate, endDate, productName, itemBarCode, purchaseItemSerialMasterId);

        List<Response> responses = stockReportSerialNumberWise.getData();

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=stock_report_serial_number_wise_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".xlsx");

        // Generate a unique sheet name using the current timestamp in milliseconds
        try ( Workbook workbook = new XSSFWorkbook()) {
            String sheetName = "Stock Report " + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            Sheet sheet = workbook.createSheet(sheetName);

            // Define Headers
            List<String> headers = List.of("Stock Number", "Brand", "Product Type", "Serial Number", "Expiry Date", "Bar Code");

            // Create Header Row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            // Check if data is null or empty
            if (responses == null || responses.isEmpty()) {
                // Optionally, write "No Data Available" message
                Row row = sheet.createRow(1);
                row.createCell(0).setCellValue("No Data Available");
            } else {
                // Create Data Rows
                int rowNum = 1;
                for (Response product : responses) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(product.getSku());
                    row.createCell(1).setCellValue(product.getProductTypeName());
                    row.createCell(2).setCellValue(product.getCategoryName());
                    row.createCell(3).setCellValue(product.getSerialNumber());
                    row.createCell(4).setCellValue(product.getExpiryDate());
                    row.createCell(5).setCellValue(product.getItemBarCode());
                }
            }

            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            handleExportError(redirectAttributes, e, response);
        }
    }

    private void handleExportError(RedirectAttributes redirectAttributes, Exception e, HttpServletResponse response) {
        // Set flash attributes to display error message and stack trace (if needed) on the redirected page
        redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while exporting stock report: " + e.getMessage());
        try {
            response.sendRedirect("/stockReportSerialNumberWise");
        } catch (IOException ioException) {

        }
    }

    @GetMapping("/getDetailstockReportSerialNumberWise")
    public String getDetailstockReportSerialNumberWise(
            @RequestParam(required = true, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = true, defaultValue = "100") Integer pageSize,
            Model model, HttpSession session, RedirectAttributes redirectAttributes,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "itemBarCode", required = false, defaultValue = "") String itemBarCode,
            @RequestParam(value = "purchaseItemSerialMasterId", required = false, defaultValue = "") Integer purchaseItemSerialMasterId, HttpServletRequest request) {
        try {
            // Check authorization
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                model.addAttribute("errorMessage", "You are not authorized to access this resource.");
                return "stockReportSerialNumberWise";
            }

            // Retrieve channel ID from session
            Integer channelId = (Integer) session.getAttribute("selectedStoreId");
            if (channelId == null) {
                model.addAttribute("errorMessage", "Store ID is missing from the session.");
                return "stockReportSerialNumberWise";
            }

            // Retrieve categories
            List<Category> fetchAllCategories = this.paginationUtil.fetchAllCategories(pageNumber, authToken, request);
            model.addAttribute("categoryList", fetchAllCategories);

            // Retrieve product types
            List<ProductType> fetchAllProductTypes = this.paginationUtil.fetchAllProductTypes(pageNumber, authToken, request);
            model.addAttribute("productTypeList", fetchAllProductTypes);

            // Retrieve product list
            DataResult<List<ProductDto>> productListResult = purchaseService.getProductList();
            List<ProductDto> productList = productListResult.getData();
            model.addAttribute("productList", productList);

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If endDate is not provided, use the date of next month
            Date endDate;
            if (endDateStr == null || endDateStr.isEmpty()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH, 1);
                endDate = calendar.getTime();
            } else {
                endDate = dateFormat.parse(endDateStr);
            }

            // Convert dates to strings
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);
            model.addAttribute("productName", productName);
            model.addAttribute("selectedBrandId", brand);
            model.addAttribute("selectedProductTypeId", productType);
            model.addAttribute("itemBarCode", itemBarCode);

            // Retrieve stock report data
            DataResult<List<Response>> stockReportSerialNumberWise = this.stockReportService.
                    getStockReportSerialNumberWise(channelId, pageNumber, pageSize, brand, productType, startDate, endDate, productName, itemBarCode, purchaseItemSerialMasterId);

            // Check if the service call was successful
            if (stockReportSerialNumberWise.isSuccess()) {
                List<Response> responses = stockReportSerialNumberWise.getData();

                // Check if the response list is empty
                if (responses == null || responses.isEmpty()) {
                    model.addAttribute("errorMessage", "No stock report data available.");
                    return "stockReportSerialNumberWise";
                }

                // Calculate pagination
                int totalItems = this.serviceDao.countTotalStockReportSerialNumberWiseByChannelId(channelId, brand, productType, startDate, endDate, productName);
                int noOfPages = (int) Math.ceil((double) totalItems / pageSize);

                // Add data to model
                model.addAttribute("stockReportSerialNumberWiseList", responses);
                model.addAttribute("currentPage", pageNumber);
                model.addAttribute("noOfPages", noOfPages);
                model.addAttribute("pageSize", pageSize);

                return "getDetailstockReportSerialNumberWise";
            } else {
                // Handle the case where service call was not successful
                model.addAttribute("errorMessage", stockReportSerialNumberWise.getMessage());
                return "getDetailstockReportSerialNumberWise";
            }
        } catch (ParseException exception) {
            // Handle any unexpected exceptions
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "getDetailstockReportSerialNumberWise";
        }
    }

    @GetMapping("/retrieveStockReportBySerialNumberAndPurchaseId")
    @ResponseBody
    public ResponseEntity<?> retrieveStockReportBySerialNumberAndPurchaseId(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "") Integer pageSize,
            HttpSession session,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "itemBarCode", required = false, defaultValue = "") String itemBarCode,
            @RequestParam(value = "purchaseItemSerialMasterId", required = false, defaultValue = "") Integer purchaseItemSerialMasterId) {
        try {
            // Retrieve the channel ID from the session
            Integer channelId = (Integer) session.getAttribute("selectedStoreId");
            if (channelId == null) {
                return ResponseEntity.badRequest().body("Store ID is not selected in the session.");
            }

            // Ensure startDate and endDate are always null
            Date startDate = null;
            Date endDate = null;

            // Retrieve the stock report
            DataResult<List<Response>> stockReportResult = stockReportService.getStockReportSerialNumberWise(
                    channelId, pageNumber, pageSize, brand, productType, startDate, endDate, productName, itemBarCode,
                    purchaseItemSerialMasterId
            );

            if (stockReportResult.isSuccess()) {
                List<Response> responseResultData = stockReportResult.getData();
                return ResponseEntity.ok(responseResultData);
            } else {
                return ResponseEntity.ok(stockReportResult.getMessage());
            }

        } catch (Exception e) {
            return ResponseEntity.ok("An error occurred while retrieving the stock report.");
        }
    }

    @PostMapping("/updateItemDetails")
    public String updateStockReportSerialNumberWise(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "") Integer pageSize,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "itemBarCode", required = false, defaultValue = "") String itemBarCode,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "newProductName", required = false, defaultValue = "") String newProductName,
            @RequestParam(value = "newSerialNumber", required = false, defaultValue = "") String newSerialNumber,
            @RequestParam(value = "expiryDate", required = false, defaultValue = "") String expiryDateStr,
            @RequestParam(value = "newBarCode", required = false, defaultValue = "") String newBarCode,
            @RequestParam(value = "purchaseItemSerialMasterId", required = false) Integer purchaseItemSerialMasterId,
            Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        try {
            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Parse the expiry date, defaulting to today's date if not provided
            Date expiryDate = (expiryDateStr == null || expiryDateStr.isEmpty()) ? new Date() : dateFormat.parse(expiryDateStr);

            // Update item details
            Result updateItemDetails = this.serviceDao.updateItemDetails(newBarCode, newSerialNumber, expiryDate, purchaseItemSerialMasterId);

            // Update productId 
            Result updateProductId = this.serviceDao.updateProductId(purchaseItemSerialMasterId, newProductName);

            // Check if the item details update was successful
            if (updateItemDetails.isSuccess()) {
                // If item details update was successful, now check if productId update was successful
                if (updateProductId.isSuccess()) {
                    // Both operations succeeded, add success message
                    redirectAttributes.addFlashAttribute("successMessage", updateProductId.getMessage());
                } else {
                    // Item details updated but productId update failed
                    redirectAttributes.addFlashAttribute("errorMessage", updateProductId.getMessage());
                }
            } else {
                // Item details update failed
                redirectAttributes.addFlashAttribute("errorMessage", updateItemDetails.getMessage());
            }

            // Creating the dynamic redirect URL by concatenating all query parameters as part of the string
            String redirectUrl = "redirect:/stockReportSerialNumberWise?startDate=" + startDateStr
                    + "&endDate=" + endDateStr
                    + "&itemBarCode=" + (itemBarCode != null ? itemBarCode : "")
                    + "&productName=" + (productName != null ? productName : "")
                    + "&brand=" + (brand != null ? brand : "")
                    + "&productType=" + (productType != null ? productType : "");

            // Returning the constructed redirect URL
            return redirectUrl;

        } catch (ParseException parseException) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error parsing date: " + parseException.getMessage());

            // Redirect to the same page with error message
            return "redirect:/stockReportSerialNumberWise";
        } catch (Exception e) {
            // Handle any other exceptions
            redirectAttributes.addFlashAttribute("errorMessage", "Unexpected error: " + e.getMessage());

            // Redirect to the same page with error message
            return "redirect:/stockReportSerialNumberWise";
        }
    }

}
