/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.service.ItemMovementReportService;
import com.AvMeditechInventory.service.QuickSearchWithSerialNumberService;
import com.AvMeditechInventory.util.ExcelReportUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class ItemMovementReportController {

    @Autowired
    private ItemMovementReportService itemMovementReportService;

    @Autowired
    private QuickSearchWithSerialNumberService quickSearchWithSerialNumberService;

    @Autowired
    private ExcelReportUtil excelReportUtil;

    @GetMapping("/itemMovementReport")
    public String itemMovementReportPage(
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        return handleItemMovementReport(productName, startDateStr, endDateStr, session, model, redirectAttributes);
    }

    @PostMapping("/itemMovementReportByProductNameAndDateRange")
    public String itemMovementReportByProductNameAndDateRange(
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        // Assuming no session needed for this POST method
        return handleItemMovementReport(productName, startDateStr, endDateStr, session, model, redirectAttributes);
    }

    private String handleItemMovementReport(String productName, String startDateStr, String endDateStr, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");
            // Check authorization

            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                model.addAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If endDate is not provided, use today's date
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

            DataResult<List<InventoryTransactionReport>> itemMovementReportByItemBarCode
                    = this.itemMovementReportService.getItemMovementReportByProductNameAndDateRange(productName, startDate, endDate, channelId);

            List<InventoryTransactionReport> itemMovementReports = itemMovementReportByItemBarCode.getData();

            model.addAttribute("itemMovementReportList", itemMovementReports);

            model.addAttribute("productName", productName);

            // Convert dates to strings
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);

            return "itemMovementReport";
        } catch (ParseException parseException) {
            model.addAttribute("errorMessage", "An unexpected error occurred while loading the item movement report. Please try again later.");
            return "redirect:/itemMovementReport";
        }
    }

    @GetMapping("/itemMovementReportsExportToExcel")
    public void itemMovementReportsExportToExcel(
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            HttpServletResponse response, HttpSession session) throws IOException {
        this.excelReportUtil.handleItemMovementReportExport(productName, startDateStr, endDateStr, response, session);
    }

    @GetMapping("/quickSearch")
    public String quickSearchWithSerialNumberPage(Model model) {
        try {
            return "quickSearchWithSerialNumber";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An unexpected error occurred while loading the item movement report. Please try again later.");
            return "errorPage";
        }
    }

    @PostMapping("/quickSearchWithSerialNumber")
    public String quickSearchWithSerialNumber(@RequestParam(value = "searchValue", required = true) String searchValue,
            Model model, HttpSession session) {
        try {
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");
            DataResult<List<InventoryTransactionReport>> quickSearchWithSerialNumber = this.quickSearchWithSerialNumberService.reteriveDataQuickSearchWithSerialNumber(searchValue, channelId);
            List<InventoryTransactionReport> quicksearch = quickSearchWithSerialNumber.getData();
            model.addAttribute("quickSearchWithSerialNumberList", quicksearch);
            model.addAttribute("searchValue", searchValue);
            return "quickSearchWithSerialNumber";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An unexpected error occurred while loading the item movement report. Please try again later.");
            return "errorPage";
        }
    }

    @GetMapping("/quickSearchExportToExcel")
    public void quickSearchExportToExcel(@RequestParam(value = "searchValue", required = true) String searchValue,
            HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) throws IOException {
        try {
            // Check if authToken exists in session
            String authToken = (String) session.getAttribute("token");
            if (authToken == null || authToken.isEmpty()) {
                // Handle case where authToken is not present
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized access: AuthToken not found.");
                return;
            }

            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            // Retrieve the search results
            DataResult<List<InventoryTransactionReport>> quickSearchResult
                    = this.quickSearchWithSerialNumberService.reteriveDataQuickSearchWithSerialNumber(searchValue, channelId);

            // Get the list from the result
            List<InventoryTransactionReport> quickSearchList = quickSearchResult.getData();

            // Prepare timestamp for filename and sheet name
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // Set the content type and header for the response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=quick_search_report_" + timestamp + ".xlsx");

            try ( Workbook workbook = new XSSFWorkbook()) {
                // Create the Excel workbook and sheet
                Sheet sheet = workbook.createSheet("Quick Search Report " + timestamp);

                // Create the header row
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Voucher Number");
                headerRow.createCell(1).setCellValue("Voucher Type");
                headerRow.createCell(2).setCellValue("Voucher Date");
                headerRow.createCell(3).setCellValue("Serial/Batch Number");
                headerRow.createCell(4).setCellValue("Expiry Date");
                headerRow.createCell(5).setCellValue("Particulars");
                headerRow.createCell(6).setCellValue("Mobile Number");
                headerRow.createCell(7).setCellValue("Product Name");
                headerRow.createCell(8).setCellValue("Product Type");
                headerRow.createCell(9).setCellValue("Brand");

                // Check if the list is empty or null
                if (quickSearchList != null && !quickSearchList.isEmpty()) {
                    // Populate the sheet with data
                    int rowNum = 1;
                    for (InventoryTransactionReport report : quickSearchList) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(report.getId());
                        row.createCell(1).setCellValue(report.getVoucherType());
                        row.createCell(2).setCellValue(report.getVoucherDate() != null ? report.getVoucherDate().toString() : "");
                        row.createCell(3).setCellValue(report.getItemSerialNumber());

                        row.createCell(4).setCellValue(report.getItemSerialExpiryDate() != null ? report.getItemSerialExpiryDate().toString() : "");

                        row.createCell(5).setCellValue(report.getCompanyName());
                        row.createCell(6).setCellValue(report.getMobileNumber());
                        row.createCell(7).setCellValue(report.getProductName());
                        row.createCell(8).setCellValue(report.getBrandName());
                        row.createCell(9).setCellValue(report.getProductTypeName());
                    }
                } else {
                    // Optionally add a message indicating no data found in the Excel sheet
                    Row row = sheet.createRow(1);
                    row.createCell(0).setCellValue("No data available for the given search value.");
                }

                // Write the workbook to the response's output stream
                workbook.write(response.getOutputStream());
            }

            // Set success message
            redirectAttributes.addFlashAttribute("successMessage", "Quick search report exported successfully.");
        } catch (IOException e) {
            // Handle any errors that occur during the export process
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An error occurred while exporting the quick search report to Excel: " + e.getMessage());
            response.sendRedirect("/quickSearch");
        }
    }

    @GetMapping("/fetchItemMovementReport")
    @ResponseBody
    public ResponseEntity<?> fetchItemMovementReport(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "transactionType") String transactionType,
            @RequestParam(value = "productName", required = false) String productName) {
        try {
            // Fetch report data from service
            DataResult<List<Response>> inventoryTransactionReport = this.itemMovementReportService.getItemMovementReportById(id, transactionType);
            List<Response> existingResult = inventoryTransactionReport.getData();

            // If productName is provided, filter the result
            List<Response> filteredResult = new ArrayList<>();
            if (productName != null && !productName.trim().isEmpty()) {
                for (Response response : existingResult) {
                    if (productName.equalsIgnoreCase(response.getProductName())) {
                        filteredResult.add(response);
                    }
                }
            } else {
                filteredResult = existingResult;
            }

            return ResponseEntity.ok(filteredResult);
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrorDataResult<>("An error occurred while fetching the report"));
        }
    }

}
