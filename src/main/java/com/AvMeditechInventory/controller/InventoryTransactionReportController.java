/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.service.InventoryTransactionReportService;
import com.AvMeditechInventory.util.ExcelReportUtil;
import com.AvMeditechInventory.util.PaginationUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Controller
public class InventoryTransactionReportController {

    @Autowired
    private InventoryTransactionReportService inventoryTransactionReportService;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private ExcelReportUtil excelReportUtil;

    @GetMapping("/inventoryTransactionReport")
    public String getInventoryTransactionReportPage(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "transactionName", defaultValue = "purchase") String transactionName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            Model model, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
        return handleInventoryTransactionReport(startDateStr, endDateStr, transactionName, productName, productType, brand, model, redirectAttributes, session, request);
    }

    @PostMapping("/inventoryTransactionReportByDateRangeAndTransactionName")
    public String getInventoryTransactionReportByDateRangeAndTransactionName(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "transactionName", defaultValue = "purchase") String transactionName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            Model model, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
        return handleInventoryTransactionReport(startDateStr, endDateStr, transactionName, productName, productType, brand, model, redirectAttributes, session, request);
    }

    private String handleInventoryTransactionReport(String startDateStr, String endDateStr, String transactionName,
            String productName, String productType, String brand, Model model, RedirectAttributes redirectAttributes,
            HttpSession session, HttpServletRequest request) {
        try {
            // Check authorization
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                model.addAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            Integer pageNumber = 100;
            // Retrieve categories
            List<Category> fetchAllCategories = this.paginationUtil.fetchAllCategories(pageNumber, authToken, request);
            model.addAttribute("categoryList", fetchAllCategories);

            // Retrieve product types
            List<ProductType> fetchAllProductTypes = this.paginationUtil.fetchAllProductTypes(pageNumber, authToken, request);
            model.addAttribute("productTypeList", fetchAllProductTypes);

            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");
            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If endDate is not provided, use today's date
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

            // Retrieve inventory transaction reports
            DataResult<List<InventoryTransactionReport>> inventoryTransReportResult = inventoryTransactionReportService
                    .getInventoryTransactionReportByDateRangeAndTransactionName(startDate, endDate, transactionName,
                            productName, productType, brand, channelId);

            List<InventoryTransactionReport> transactionReports = inventoryTransReportResult.getData();
            model.addAttribute("transactionReportList", transactionReports);

            // Convert dates to strings for display
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);
            model.addAttribute("transactionName", transactionName);
            model.addAttribute("productName", productName); // Add Model productName
            model.addAttribute("productType", productType); // Add Model productName
            model.addAttribute("brand", brand); // Add Model productName

            return "inventoryTransactionReport";
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format. Please provide dates in the format yyyy-MM-dd.");
            return "inventoryTransactionReport"; // Redirect to the error page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while loading the inventory transaction report. Please try again later.");
            return "inventoryTransactionReport"; // Redirect to the error page
        }
    }

    @GetMapping("/inventoryTransactionReportExportToExcel")
    public void exportInventoryTransactionReportToExcel(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "transactionName", defaultValue = "purchase") String transactionName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            HttpServletResponse response, HttpSession session) {
        this.excelReportUtil.handleInventoryTransactionReportExport(startDateStr, endDateStr, transactionName, productName, productType, brand, response, session);
    }

    @GetMapping("/inventoryTransactionBrandReport")
    public String getInventoryTransactionBrandReportPage(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "transactionName", defaultValue = "purchase") String transactionName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        return handleInventoryTransactionBrandReport(startDateStr, endDateStr, transactionName, productName,
                productType, brand, model, redirectAttributes, session);
    }

    @PostMapping("/inventoryTransactionBrandReportByDateRangeAndTransactionName")
    public String getInventoryTransactionBrandReportByDateRangeAndTransactionName(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "transactionName", defaultValue = "purchase") String transactionName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        return handleInventoryTransactionBrandReport(startDateStr, endDateStr, transactionName, productName, productType,
                brand, model, redirectAttributes, session);
    }

    private String handleInventoryTransactionBrandReport(String startDateStr, String endDateStr, String transactionName,
            String productName, String productType, String brand, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");
            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If endDate is not provided, use today's date
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

            // Retrieve inventory transaction reports
            DataResult<List<InventoryTransactionReport>> inventoryTransReportResult = inventoryTransactionReportService
                    .getInventoryTransactionReportByDateRangeAndTransactionName(startDate, endDate, transactionName,
                            productName, productType, brand, channelId);

            List<InventoryTransactionReport> transactionReports = inventoryTransReportResult.getData();
            model.addAttribute("transactionReportList", transactionReports);

            // Convert dates to strings for display
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);
            model.addAttribute("transactionName", transactionName);

            return "inventoryTransactionBrandReport";
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format. Please provide dates in the format yyyy-MM-dd.");
            return "inventoryTransactionBrandReport"; // Redirect to the error page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while loading the inventory transaction report. Please try again later.");
            return "inventoryTransactionBrandReport"; // Redirect to the error page
        }
    }

    @GetMapping("/inventoryTransactionBrandReportExportToExcel")
    public void inventoryTransactionBrandReportExportToExcel(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "transactionName", defaultValue = "purchase") String transactionName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            HttpServletResponse response, HttpSession session) {
        handleInventoryTransactionBrandReportExportToExcel(startDateStr, endDateStr, transactionName, productName,
                productType, brand, response, session);
    }

    private void handleInventoryTransactionBrandReportExportToExcel(String startDateStr, String endDateStr,
            String transactionName, String productName, String productType, String brand, HttpServletResponse response, HttpSession session) {
        try {
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication token is missing or invalid.");
                return;
            }
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If end date is not provided, use today's date
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

            // Retrieve inventory transaction reports
            DataResult<List<InventoryTransactionReport>> inventoryTransReportResult = inventoryTransactionReportService
                    .getInventoryTransactionReportByDateRangeAndTransactionName(startDate, endDate, transactionName,
                            productName, productType, brand, channelId);

            List<InventoryTransactionReport> transactionReports = inventoryTransReportResult.getData();

            if (transactionReports == null || transactionReports.isEmpty()) {
                response.sendError(HttpServletResponse.SC_NO_CONTENT, "No inventory transaction reports found for the given criteria.");
                return;
            }

            // Create a list to hold combined data
            List<Object> combinedData = new ArrayList<>();

            for (InventoryTransactionReport inventoryTransactionReport : transactionReports) {
                DataResult<List<Response>> inventoryTransactionReport1 = this.inventoryTransactionReportService.getInventoryTransactionReport(inventoryTransactionReport.getId(), transactionName);

                if (!inventoryTransactionReport1.isSuccess() || inventoryTransactionReport1.getData() == null) {
                    return;
                }

                // Combine the data from transactionReports and inventoryTransactionReport1 into combinedData
                combinedData.add(inventoryTransactionReport); // Add InventoryTransactionReport to the combined list
                combinedData.addAll(inventoryTransactionReport1.getData()); // Add all Responses to the combined list
            }

            // Prepare timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // Create a workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Inventory Transactions Brand Report" + timestamp);

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Voucher No");
            headerRow.createCell(1).setCellValue("Date");
            headerRow.createCell(2).setCellValue("Company Name");
            headerRow.createCell(3).setCellValue("Reference");
            headerRow.createCell(4).setCellValue("Remarks");

            // Populate the sheet with combined data
            int rowNum = 1;
            for (Object data : combinedData) {
                Row row = sheet.createRow(rowNum++);
                if (data instanceof InventoryTransactionReport) {
                    InventoryTransactionReport report = (InventoryTransactionReport) data;
                    row.createCell(0).setCellValue(report.getAutoId());
                    Date voucherDate = report.getVoucherDate();
                    String dateString = dateFormat.format(voucherDate);
                    row.createCell(1).setCellValue(dateString);
                    row.createCell(2).setCellValue(report.getCompanyName());
                    row.createCell(3).setCellValue(report.getReferenceNumber());
                    row.createCell(4).setCellValue(report.getRemarks());
                } else if (data instanceof Response) {
                    Response responseData = (Response) data;
                    // Assuming you have corresponding fields in Response class to populate the Excel row
                    row.createCell(0).setCellValue(responseData.getProductName()); // Replace with actual field
                    row.createCell(1).setCellValue(responseData.getCategoryName()); // Replace with actual field
                    // Add other cells as needed
                }
            }

            // Set the content type and header for the response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=inventory_transaction_brand_report_" + timestamp + ".xlsx");

            // Write the workbook to the response output stream
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().write("Invalid date format. Please provide dates in the format yyyy-MM-dd.");
            } catch (IOException ioException) {

            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("An unexpected error occurred while exporting the inventory transaction report. Please try again later.");
            } catch (IOException ioException) {

            }
        }
    }

    @GetMapping("/fetchInventoryTransactionReport")
    @ResponseBody
    public ResponseEntity<?> fetchInventoryTransactionReport(
            @RequestParam(value = "purchaseId") Integer purchaseId,
            @RequestParam(value = "transactionName") String transactionName,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "productType", required = false) String productType) {
        try {
            DataResult<List<Response>> inventoryTransactionReport
                    = inventoryTransactionReportService.getInventoryTransactionReport(purchaseId, transactionName);

            List<Response> existingResult = inventoryTransactionReport.getData();

            // Apply all non-null filters using stream
            List<Response> filteredResult = existingResult.stream()
                    .filter(response -> productName == null || productName.trim().isEmpty()
                    || productName.equalsIgnoreCase(response.getProductName()))
                    .filter(response -> brand == null || brand.trim().isEmpty()
                    || brand.equalsIgnoreCase(response.getCategoryName()))
                    .filter(response -> productType == null || productType.trim().isEmpty()
                    || productType.equalsIgnoreCase(response.getProductTypeName()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(filteredResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDataResult<>("An error occurred while fetching the report"));
        }
    }

}
