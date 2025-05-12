package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.BatchWiseReportService;
import com.AvMeditechInventory.service.PurchaseService;
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
import org.springframework.http.HttpStatus;
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
 * @author RAJEEV KUMAR - QMM Technologies Private Limited
 */
@Controller
public class BatchWiseReportController {

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private BatchWiseReportService batchWiseReportService;

    @Autowired
    private ServiceDao serviceDao;

    @GetMapping("/batchWiseReport")
    public String getBatchWiseReportPage(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateParam,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateParam,
            @RequestParam(value = "batchNumber", required = false, defaultValue = "") String batchNumber,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(required = true, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = true, defaultValue = "100") Integer pageSize,
            @RequestParam(value = "purchaseItemSerialMasterId", required = false, defaultValue = "") Integer purchaseItemSerialMasterId,
            HttpServletRequest request, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        try {
            // Check user authorization
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                model.addAttribute("errorMessage", "Unauthorized access. Please log in to continue.");
                return "redirect:/";
            }

            // Validate the store ID in session
            Integer channelId = (Integer) session.getAttribute("selectedStoreId");
            if (channelId == null) {
                model.addAttribute("errorMessage", "Store ID is missing. Please select a store and try again.");
                return "batchWiseReport";
            }

            // Fetch supporting data
            model.addAttribute("categoryList", paginationUtil.fetchAllCategories(pageNumber, authToken, request));
            model.addAttribute("productTypeList", paginationUtil.fetchAllProductTypes(pageNumber, authToken, request));
            model.addAttribute("productList", purchaseService.getProductList().getData());

            // Parse and validate dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = parseDateOrDefault(startDateParam, new Date(), dateFormat);
            Date endDate = parseDateOrDefault(endDateParam, getFutureDate(startDate, 5), dateFormat);

            model.addAttribute("startDate", dateFormat.format(startDate));
            model.addAttribute("endDate", dateFormat.format(endDate));
            model.addAttribute("productName", productName);
            model.addAttribute("selectedBrandId", brand);
            model.addAttribute("selectedProductTypeId", productType);
            model.addAttribute("batchNumber", batchNumber);

            // Fetch stock report
            DataResult<List<Response>> stockReportResult = batchWiseReportService.fetchBatchWiseReport(channelId, pageNumber, pageSize, brand, productType, startDate, endDate, productName, batchNumber, purchaseItemSerialMasterId);

            if (stockReportResult.isSuccess() || stockReportResult.getData() == null) {
                List<Response> stockReport = stockReportResult.getData();
                if (stockReport == null || stockReport.isEmpty()) {
                    model.addAttribute("infoMessage", "No stock report data available for the selected criteria.");
                    return "batchWiseReport";
                }

                int totalItems = serviceDao.countTotalStockReportSerialNumberWiseByChannelId(channelId, brand, productType, startDate, endDate, productName);
                int totalPages = (int) Math.ceil((double) totalItems / pageSize);
                int totalQuantity = stockReport.stream().mapToInt(Response::getQuantity).sum();

                model.addAttribute("stockReportList", stockReport);
                model.addAttribute("currentPage", pageNumber);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute("pageSize", pageSize);
                model.addAttribute("totalQuantity", totalQuantity);

                return "batchWiseReport";
            } else {
                model.addAttribute("errorMessage", stockReportResult.getMessage());
                return "batchWiseReport";
            }
        } catch (Exception exception) {
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later." + exception.getMessage());
            return "batchWiseReport";
        }
    }

    private Date parseDateOrDefault(String dateStr, Date defaultDate, SimpleDateFormat dateFormat) {
        try {
            return (dateStr == null || dateStr.isEmpty()) ? defaultDate : dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return defaultDate;
        }
    }

    private Date getFutureDate(Date date, int yearsToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, yearsToAdd);
        return calendar.getTime();
    }

    @GetMapping("/api/batch/wise/report/details")
    @ResponseBody
    public ResponseEntity<?> getBatchWiseStockReport(
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "batchNumber", required = false, defaultValue = "") String batchNumber,
            @RequestParam(value = "expiryDate", required = false, defaultValue = "") String expiryDateParam,
            HttpSession session) {
        try {
            // Retrieve the channel ID from the session
            Integer channelId = (Integer) session.getAttribute("selectedStoreId");
            if (channelId == null) {
                return ResponseEntity.badRequest().body("Store ID is not selected in the session.");
            }

            // Fetch batch product details from the service
            DataResult<Response> batchProductDetailsResult = this.batchWiseReportService.fetchBatchProductDetails(brand, productType, batchNumber, expiryDateParam, channelId);

            if (batchProductDetailsResult.isSuccess()) {
                Response response = batchProductDetailsResult.getData();
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve batch product details: " + batchProductDetailsResult.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the stock report: " + e.getMessage());
        }
    }

    @PostMapping("/updateBatchProductDetails")
    public String updateBatchProductDetails(
            @RequestParam(value = "oldBatchNumber", required = true, defaultValue = "") String oldBatchNumber,
            @RequestParam(value = "newBatchNumber", required = true, defaultValue = "") String newBatchNumber,
            @RequestParam(value = "oldExpiryDate", required = true, defaultValue = "") String oldExpiryDate,
            @RequestParam(value = "newExpiryDate", required = true, defaultValue = "") String newExpiryDate,
            @RequestParam(value = "startDate", required = true, defaultValue = "") String startDateParam,
            @RequestParam(value = "endDate", required = true, defaultValue = "") String endDateParam,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            RedirectAttributes redirectAttributes) {

        try {
            // Call the service method to update item details
            Result updatePurchaseItemDetails = this.serviceDao.updatePurchaseItemDetails(oldBatchNumber, newBatchNumber, oldExpiryDate, newExpiryDate);

            // Construct the redirect URL with multiple parameters
            String redirectUrl = "redirect:/batchWiseReport?startDate=" + startDateParam
                    + "&endDate=" + endDateParam
                    + "&batchNumber=" + newBatchNumber
                    + "&productName=" + productName
                    + "&productType=" + productType
                    + "&brand=" + brand;

            // Check if the update was successful
            if (updatePurchaseItemDetails instanceof SuccessResult) {
                redirectAttributes.addFlashAttribute("successMessage", updatePurchaseItemDetails.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", updatePurchaseItemDetails.getMessage());
            }

            // Redirect to batchWiseReport with appropriate message
            return redirectUrl;

        } catch (Exception e) {
            // Construct the redirect URL with multiple parameters
            String redirectUrl = "redirect:/batchWiseReport?startDate=" + startDateParam
                    + "&endDate=" + endDateParam
                    + "&batchNumber=" + newBatchNumber
                    + "&productName=" + productName
                    + "&productType=" + productType
                    + "&brand=" + brand;
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            // Redirect to batchWiseReport with appropriate message
            return redirectUrl;
        }
    }

    @GetMapping("/exportBatchReportToExcel")
    public void exportBatchReportToExcel(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "batchNumber", required = false, defaultValue = "") String batchNumber,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "purchaseItemSerialMasterId", required = false, defaultValue = "") Integer purchaseItemSerialMasterId,
            HttpServletResponse response, HttpSession session) throws IOException, ParseException {

        // Validate user authorization
        String authToken = (String) session.getAttribute("token");
        if (authToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization failed. Please log in to access this resource.");
            return;
        }

        // Validate channelId ID
        Integer channelId = (Integer) session.getAttribute("selectedStoreId");
        if (channelId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Store ID is missing from the session. Please select a store.");
            return;
        }

        // Parse and validate dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = parseDateOrDefault(startDateStr, new Date(), dateFormat);
        Date endDate = parseDateOrDefault(endDateStr, getFutureDate(startDate, 5), dateFormat);

        // Fetch batch-wise report data
        DataResult<List<Response>> batchReportResult = batchWiseReportService.fetchBatchWiseReport(channelId, null, null, brand, productType, startDate, endDate, productName, batchNumber, purchaseItemSerialMasterId);

        List<Response> reportData = batchReportResult.getData();

        // Set response headers for Excel file download
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=batch_report_" + timestamp + ".xlsx");

        // Create Excel file
        try ( Workbook workbook = new XSSFWorkbook()) {
            String sheetName = "Batch Report " + timestamp;
            Sheet sheet = workbook.createSheet(sheetName);

            // Define headers
            List<String> headers = List.of("Stock Number", "Brand", "Product Type", "Batch Number", "Expiry Date", "Quantity");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            // Populate data rows
            if (reportData == null || reportData.isEmpty()) {
                Row noDataRow = sheet.createRow(1);
                noDataRow.createCell(0).setCellValue("No Data Available");
            } else {
                int rowIndex = 1;
                for (Response product : reportData) {
                    Row dataRow = sheet.createRow(rowIndex++);
                    dataRow.createCell(0).setCellValue(product.getSku());
                    dataRow.createCell(1).setCellValue(product.getCategoryName());
                    dataRow.createCell(2).setCellValue(product.getProductTypeName());
                    dataRow.createCell(3).setCellValue(product.getBatchNo());
                    dataRow.createCell(4).setCellValue(product.getExpiryDate());
                    dataRow.createCell(5).setCellValue(product.getQuantity());
                }
            }

            // Write workbook to response output stream
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while generating the report.");
        }
    }

}
