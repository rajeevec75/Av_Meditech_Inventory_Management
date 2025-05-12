/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.PurchaseImportResponse;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.Sale;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.ChallanService;
import com.AvMeditechInventory.service.PurchaseService;
import com.AvMeditechInventory.service.SaleService;
import com.AvMeditechInventory.service.StockService;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private ChallanService challanService;

    @GetMapping("/saleReturnList")
    public String getSaleReturnList(@RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        return fetchSaleReturnList(startDateStr, endDateStr, customerName, productName, pageNumber, pageSize, model, session, redirectAttributes);
    }

    @PostMapping("/saleReturnListByDateRangeAndFilters")
    public String getSaleReturnListByDateRangeAndFilters(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            HttpSession session, Model model,
            RedirectAttributes redirectAttributes) {
        return fetchSaleReturnList(startDateStr, endDateStr, customerName, productName, pageNumber, pageSize, model, session, redirectAttributes);
    }

    // Common method to handle the shared logic
    private String fetchSaleReturnList(String startDateStr, String endDateStr, String customerName, String productName,
            Integer pageNumber, Integer pageSize, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            // Retrieve customer names based on user types
            List<String> userTypes = Arrays.asList(Constant.CUSTOMER, Constant.BOTH);
            DataResult<List<Response>> customerNameListResult = this.saleService.customerNameList(userTypes);
            List<Response> customerNameList = customerNameListResult.getData();
            model.addAttribute("customerNameList", customerNameList);

            // Retrieve product list
            DataResult<List<ProductDto>> productListResult = this.purchaseService.getProductList();
            List<ProductDto> productList = productListResult.getData();
            model.addAttribute("productList", productList);

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If endDate is not provided, use today's date
            Date endDate;
            if (endDateStr == null || endDateStr.isEmpty()) {
                endDate = new Date();
            } else {
                endDate = dateFormat.parse(endDateStr);
            }

            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            // Call the service to get the sale return list
            DataResult<List<Response>> stockListResult = this.stockService.getSaleReturnList(channelId, startDate, endDate,
                    customerName, productName, pageNumber, pageSize);

            List<Response> stockList = stockListResult.getData();
            model.addAttribute("saleReturnList", stockList);

            int totalQuantity = 0;
            for (int i = 0; i < stockList.size(); i++) {
                totalQuantity = totalQuantity + stockList.get(i).getQuantity();
            }
            model.addAttribute("totalQuantity", totalQuantity);

            // Convert dates to strings
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);
            model.addAttribute("customerName", customerName);
            model.addAttribute("productName", productName);

            return "stockList";
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format: ");
            return "stockList"; // Redirect to the error page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request: ");
            return "stockList"; // Redirect to the error page
        }
    }

    @GetMapping("/stockTransfer/json")
    @ResponseBody
    public ResponseEntity<?> getStockTransferJson(@RequestParam(value = "itemSerialNumber", required = false) String itemBarCode,
            @RequestParam(value = "customerId", required = false) String customerId, HttpSession session) {
        try {
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");
            // Call the service to get the barcode for the item serial number
            DataResult<Response> barcodeFromItemSerialNumber = this.stockService.getBarcodeFromItemSerialNumber(itemBarCode, customerId, channelId);
            Response response = barcodeFromItemSerialNumber.getData();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle exceptions
            String errorMessage = "An error occurred while processing your request.";
            return ResponseEntity.ok(errorMessage);
        }
    }

    @PostMapping("/checkSaleReturn")
    @ResponseBody
    public ResponseEntity<?> checkSaleReturn(@RequestParam(value = "purchaseData", required = false) String[] purchaseData) {

        DataResult<String> saleCreate = this.stockService.checkSaleReturn(purchaseData);
        if (saleCreate.isSuccess()) {
            return ResponseEntity.ok(saleCreate.getMessage() + "|" + "true");
        } else {
            return ResponseEntity.ok(saleCreate.getMessage() + "|" + "false");
        }
    }

    @PostMapping("/processSaleReturn")
    public String processStockTransfer(
            @ModelAttribute("sale") Sale sale,
            @RequestParam(value = "purchaseData", required = false) String[] purchaseData,
            @RequestParam(value = "purchaseSerialData", required = true) String[] purchaseSerialData,
            @RequestParam(value = "formattedDate", required = true) String formattedDate,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Process the list of Response objects
            DataResult<Integer> stockReturn = this.stockService.stockTransfer(sale, purchaseData, formattedDate, purchaseSerialData, session);
            if (stockReturn.isSuccess()) {
                Integer saleReturnId = stockReturn.getData();
                redirectAttributes.addFlashAttribute("successMessage", stockReturn.getMessage());
                return "redirect:/getSaleReturnById?saleId=" + saleReturnId;
            }
            redirectAttributes.addFlashAttribute("errorMessage", stockReturn.getMessage());
            return "redirect:/saleReturn";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/saleReturn";
        }
    }

    @PostMapping("/updateStockCreate")
    public String updateStockCreate(@ModelAttribute("sale") Sale sale,
            @RequestParam(value = "purchaseData", required = false) String[] purchaseData,
            @RequestParam(value = "purchaseSerialData", required = true) String[] purchaseSerialData,
            @RequestParam(value = "sale_return_date", required = false) String saleReturnDateStr,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        DataResult<Integer> updateSaleReturnDataResult = null;
        Integer saleId = sale.getSaleId();
        try {
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Validate and parse sale return date
            Date saleReturnDate = null;
            if (saleReturnDateStr != null && !saleReturnDateStr.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                saleReturnDate = dateFormat.parse(saleReturnDateStr);
            }

            String purchaseEmailId = (String) session.getAttribute("userEmail");
            DataResult<Map<String, Object>> data = serviceDao.getUserDataByEmail(purchaseEmailId);
            int returnUserId = (int) data.getData().get("id");
            updateSaleReturnDataResult = stockService.updateStockCreate(sale,
                    purchaseData, purchaseSerialData, saleReturnDate, returnUserId, channelId);
            if (updateSaleReturnDataResult.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", updateSaleReturnDataResult.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", updateSaleReturnDataResult.getMessage());
            }
            return "redirect:/getSaleReturnById?saleId=" + (saleId != null ? saleId : "");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing the sale");
            return "redirect:/getSaleReturnById?saleId=" + (saleId != null ? saleId : "");
        }
    }

    @GetMapping("/saleReturn")
    public String stockTransfer(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Prepare the list of user types to search for
            List<String> userTypes = Arrays.asList(Constant.CUSTOMER, Constant.BOTH);
            // Call the service to get the list of customer names
            DataResult<List<Response>> customerNameList = this.stockService.customerNameList(userTypes);
            List<Response> responses = customerNameList.getData();
//            DataResult<Integer> saleReturnSeq = null;
//            String storeName = (String) session.getAttribute("selectedStoreName");
//            if (storeName != null && storeName.equals("AV MEDITECH PVT LTD KAITHAL")) {
//                saleReturnSeq = serviceDao.getAutoSaleReturnAvtSeq();
//            } else if (storeName != null && storeName.equals("LENSE HOME KAITHAL")) {
//                saleReturnSeq = serviceDao.getAutoSaleReturnLhtSeq();
//            } else if (storeName != null && storeName.equals("AV MEDITECH GURUGRAM")) {
//                saleReturnSeq = serviceDao.getAutoSaleReturnSeq();
//            } else if (storeName != null && storeName.equals("AV MEDITECH KAITHAL")) {
//                saleReturnSeq = serviceDao.getAutoSaleReturnAmkSeq();
//            }
            model.addAttribute("customerNameList", responses);
//            model.addAttribute("saleReturnSeqId", saleReturnSeq.getData());
            return "stockTransfer";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An unexpected error occurred while loading the sale creation page.");
            return "stockTransfer";
        }
    }

    @PostMapping("/importStockTransfer")
    @ResponseBody
    public ResponseEntity<?> importStockTransfer(Model model, @RequestParam("file1") MultipartFile file1, HttpSession session) {
        try {
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");
            // Process the purchase product
            PurchaseImportResponse purchaseProductResult = this.stockService.importSaleProduct(file1, channelId);
            return ResponseEntity.ok(purchaseProductResult);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return ResponseEntity.ok("");
        }
    }

    @GetMapping("/getSaleReturnById")
    public String getStockById(@RequestParam(value = "saleId", required = true) Integer saleId,
            Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {

            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Step 1: Retrieve sale data by ID
            int channelId = (int) session.getAttribute("selectedStoreId");
            DataResult<Response> saleByIdResult = this.stockService.getStockDetailsById(saleId, channelId);

            // Step 2: Check if the result was successful
            // Sale data retrieved successfully, add it to the model
            Response response = saleByIdResult.getData();
            model.addAttribute("response", response);

            // Retrieve customer names based on user types
            List<String> userTypes = Arrays.asList(Constant.CUSTOMER, Constant.BOTH);
            DataResult<List<Response>> customerNameListResult = this.saleService.customerNameList(userTypes);
            List<Response> customerNameList = customerNameListResult.getData();
            model.addAttribute("customerNameList", customerNameList);
            return "getStockById";

        } catch (Exception e) {
            // Step 3: Handle any exceptions that occur
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "getStockById";
        }
    }

    @GetMapping("/deleteSaleReturnById")
    public String deleteSaleReturnById(@RequestParam(value = "saleId", required = true) Integer saleId,
            HttpSession session, Model model, @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "productName", required = false) String productName,
            RedirectAttributes redirectAttributes) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Assuming there is a service method to get purchase by ID
            int channelId = (int) session.getAttribute("selectedStoreId");

            Result deleteStockDetailsById = stockService.deleteStockDetailsById(saleId, channelId);
            if (deleteStockDetailsById.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", deleteStockDetailsById.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", deleteStockDetailsById.getMessage());
            }
            redirectAttributes.addAttribute("startDate", startDateStr);
            redirectAttributes.addAttribute("endDate", endDateStr);
            redirectAttributes.addAttribute("companyName", companyName);
            redirectAttributes.addAttribute("productName", productName);
            return "redirect:/saleReturnList";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing the purchase.");
            return "redirect:/saleReturnList";
        }
    }

    @GetMapping("/saleReturnDeliveryChallan")
    public String saleReturnDeliveryChallan(@RequestParam(value = "saleReturnId", required = true) Integer saleReturnId, Model model, HttpSession session) {
        try {
            // Step 1: Retrieve user detail by purchase ID
            DataResult<Response> userDetailBysaleReturnIdResult = this.challanService.getUserDetailBySaleReturnId(saleReturnId);
            Response userDetailResponse = userDetailBysaleReturnIdResult.getData();
            model.addAttribute("response", userDetailResponse);

            // Step 2: Retrieve delivery challan details by purchase ID
            DataResult<List<Response>> deliveryChallanBysaleReturnIdResult = this.challanService.getDeliveryChallanBySaleReturnId(saleReturnId);
            List<Response> deliveryChallanResponses = deliveryChallanBysaleReturnIdResult.getData();
            model.addAttribute("responseList", deliveryChallanResponses);
            model.addAttribute("challan", "Sale Return");
            model.addAttribute("to", "Supplier");

            int totalQuantity = 0;
            for (int i = 0; i < deliveryChallanResponses.size(); i++) {
                totalQuantity = totalQuantity + deliveryChallanResponses.get(i).getQuantity();
            }
            model.addAttribute("totalQuantity", totalQuantity);

            return "challan"; // Assuming "challan" is your view name

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create challan due to an unexpected error. Please try again later.");
            return "errorPage";
        }
    }

    @GetMapping("/saleReturnDeliveryChallanQuantity")
    public String saleReturnDeliveryChallanQuantity(@RequestParam(value = "saleReturnId", required = true) Integer saleReturnId, Model model, HttpSession session) {
        try {
            // Step 1: Retrieve user detail by purchase ID
            DataResult<Response> userDetailBysaleReturnIdResult = this.challanService.getUserDetailBySaleReturnId(saleReturnId);
            Response userDetailResponse = userDetailBysaleReturnIdResult.getData();
            model.addAttribute("response", userDetailResponse);

            // Step 2: Retrieve delivery challan details by purchase ID
            DataResult<List<Response>> deliveryChallanBysaleReturnIdResult = this.challanService.getDeliveryChallanQuantityBySaleReturnId(saleReturnId);
            List<Response> deliveryChallanResponses = deliveryChallanBysaleReturnIdResult.getData();
            model.addAttribute("responseList", deliveryChallanResponses);
            model.addAttribute("challan", "Sale Return");
            model.addAttribute("to", "Supplier");

            int totalQuantity = 0;
            for (int i = 0; i < deliveryChallanResponses.size(); i++) {
                totalQuantity = totalQuantity + deliveryChallanResponses.get(i).getQuantity();
            }
            model.addAttribute("totalQuantity", totalQuantity);

            return "challanOne"; // Assuming "challan" is your view name

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create challan due to an unexpected error. Please try again later.");
            return "errorPage";
        }
    }

    @GetMapping("/saleReturnListExportToExcel")
    public void saleReturnListExportToExcel(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            HttpSession session, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            Integer pageNumber = null;
            Integer pageSize = null;
            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If endDate is not provided, use today's date
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            // Call the service to get the sale return list
            DataResult<List<Response>> saleReturnListResult = this.stockService.getSaleReturnList(channelId, startDate, endDate, customerName, productName, pageNumber, pageSize);
            List<Response> saleReturnList = saleReturnListResult.getData();

            // Prepare timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // Create an Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("sale_return_list_" + timestamp);

            // Create the header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Challan Number");
            headerRow.createCell(1).setCellValue("Customer Name");
            headerRow.createCell(2).setCellValue("Reference");
            headerRow.createCell(3).setCellValue("Date");
            headerRow.createCell(4).setCellValue("Remarks");
            headerRow.createCell(5).setCellValue("Quantity");

            // Fill data rows
            int rowNum = 1;
            for (Response saleReturn : saleReturnList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(saleReturn.getAutoId());
                row.createCell(1).setCellValue(saleReturn.getCompanyName());
                row.createCell(2).setCellValue(saleReturn.getReferenceNumber());
                row.createCell(3).setCellValue(saleReturn.getDate());
                row.createCell(4).setCellValue(saleReturn.getRemarks());
                row.createCell(5).setCellValue(saleReturn.getQuantity());

            }

            // Set the response headers and content type
            String fileName = "sale_return_list_" + timestamp + ".xlsx";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Write the workbook to the response output stream
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (Exception e) {
            handleExportError(redirectAttributes, e, response);
        }
    }

    private void handleExportError(RedirectAttributes redirectAttributes, Exception e, HttpServletResponse response) {
        redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while exporting purchase list: " + e.getMessage());
        redirectAttributes.addFlashAttribute("errorStackTrace", e.getStackTrace());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            response.sendRedirect("/saleReturnList");
        } catch (IOException ioException) {
            // Handle redirect exception
            ioException.printStackTrace();
        }
    }
}
