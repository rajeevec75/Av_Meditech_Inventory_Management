/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.util;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.service.InventoryTransactionReportService;
import com.AvMeditechInventory.service.ItemMovementReportService;
import com.AvMeditechInventory.service.PartyReportService;
import com.AvMeditechInventory.service.ProductService;
import com.AvMeditechInventory.service.SaleService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author RAJEEV KUMAR(QMM Technologies Private Limited)
 */
@Service
public class ExcelReportUtil {

    private final InventoryTransactionReportService inventoryTransactionReportService;
    private final ItemMovementReportService itemMovementReportService;
    private final PartyReportService partyReportService;
    private final SaleService saleService;
    private final ProductService productService;

    @Autowired
    public ExcelReportUtil(InventoryTransactionReportService inventoryTransactionReportService, ItemMovementReportService itemMovementReportService, PartyReportService partyReportService, SaleService saleService, ProductService productService) {
        this.inventoryTransactionReportService = inventoryTransactionReportService;
        this.itemMovementReportService = itemMovementReportService;
        this.partyReportService = partyReportService;
        this.saleService = saleService;
        this.productService = productService;
    }

    public void handleInventoryTransactionReportExport(String startDateStr, String endDateStr,
            String transactionName, String productName, String productType, String brand,
            HttpServletResponse response, HttpSession session) {
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
            DataResult<List<InventoryTransactionReport>> inventoryTransReportResult = inventoryTransactionReportService.getInventoryTransactionReportByDateRangeAndTransactionName(startDate, endDate, transactionName, productName, productType, brand, channelId);

            List<InventoryTransactionReport> transactionReports = inventoryTransReportResult.getData();

            // Create a list to hold combined data
            List<Object> combinedData = new ArrayList<>();

            for (InventoryTransactionReport inventoryTransactionReport : transactionReports) {
                if (inventoryTransactionReport != null) {
                    combinedData.add(inventoryTransactionReport);
                }

                DataResult<List<Response>> inventoryTransactionReportResult = this.inventoryTransactionReportService.getInventoryTransactionReport(inventoryTransactionReport.getId(), transactionName);

                List<Response> responses = inventoryTransactionReportResult.getData();
                if (responses != null) {
                    for (Response responseData : responses) {
                        if (responseData != null) {
                            boolean matchesProductName = (productName == null || productName.trim().isEmpty()
                                    || productName.equalsIgnoreCase(responseData.getProductName()));

                            boolean matchesBrand = (brand == null || brand.trim().isEmpty()
                                    || brand.equalsIgnoreCase(responseData.getCategoryName()));

                            boolean matchesProductType = (productType == null || productType.trim().isEmpty()
                                    || productType.equalsIgnoreCase(responseData.getProductTypeName()));

                            if (matchesProductName && matchesBrand && matchesProductType) {
                                combinedData.add(responseData);
                            }
                        }
                    }
                }
            }

            // Prepare timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // Create a workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Inventory Transactions Report " + timestamp);

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Voucher Number");
            headerRow.createCell(1).setCellValue("Voucher Date");
            headerRow.createCell(2).setCellValue("Company Name");
            headerRow.createCell(3).setCellValue("Reference");
            headerRow.createCell(4).setCellValue("Remarks");
            headerRow.createCell(5).setCellValue("Item Description");
            headerRow.createCell(6).setCellValue("Product Type");
            headerRow.createCell(7).setCellValue("Brand");
            headerRow.createCell(8).setCellValue("Sku");
            headerRow.createCell(9).setCellValue("Serial Number");
            headerRow.createCell(10).setCellValue("Expiry Date");
            headerRow.createCell(11).setCellValue("Quantity");

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
                    row.createCell(5).setCellValue(responseData.getProductName());
                    row.createCell(6).setCellValue(responseData.getCategoryName());
                    row.createCell(7).setCellValue(responseData.getProductTypeName());
                    row.createCell(8).setCellValue(responseData.getSku());
                    row.createCell(9).setCellValue(responseData.getSerialNumber());
                    Date itemSerialExpiry = responseData.getItemSerialExpiry();
                    String expiryDateString = itemSerialExpiry != null ? dateFormat.format(itemSerialExpiry) : "";
                    row.createCell(10).setCellValue(expiryDateString);
                    row.createCell(11).setCellValue(responseData.getQuantity());
                }
            }

            // Set the content type and header for the response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=inventory_transaction_report_" + timestamp + ".xlsx");

            // Write the workbook to the response output stream
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try {
                response.getWriter().write("Invalid date format. Please provide dates in the format yyyy-MM-dd.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("An unexpected error occurred while exporting the inventory transaction report. Please try again later.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void handleItemMovementReportExport(String productName, String startDateStr, String endDateStr, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            // Check if authToken exists in session
            String authToken = (String) session.getAttribute("token");
            if (authToken == null || authToken.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access: AuthToken not found.");
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

            // Fetch item movement reports from service
            DataResult<List<InventoryTransactionReport>> itemMovementReportResult
                    = this.itemMovementReportService.getItemMovementReportByProductNameAndDateRange(productName, startDate, endDate, channelId);

            List<InventoryTransactionReport> itemMovementReports = itemMovementReportResult.getData();

            // Create a list to hold combined data
            List<Object> combinedData = new ArrayList<>();

            for (InventoryTransactionReport inventoryTransactionReport : itemMovementReports) {
                DataResult<List<Response>> inventoryTransactionReportResult
                        = this.itemMovementReportService.getItemMovementReportById(inventoryTransactionReport.getId(), inventoryTransactionReport.getVoucherType());

                // Add InventoryTransactionReport to the combined list if it's not null
                if (inventoryTransactionReport != null) {
                    combinedData.add(inventoryTransactionReport);
                }

                // Filter and add Responses based only on productName
                List<Response> responses = inventoryTransactionReportResult.getData();
                if (responses != null) {
                    for (Response responseData : responses) {
                        if (responseData != null) {
                            boolean matchesProductName = (productName == null || productName.trim().isEmpty()
                                    || productName.equalsIgnoreCase(responseData.getProductName()));

                            if (matchesProductName) {
                                combinedData.add(responseData);
                            }
                        }
                    }
                }
            }

            // Prepare timestamp for filename and sheet name
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String filename = "item_movement_report_" + timestamp + ".xlsx";
            String sheetName = "Item Movement Report " + timestamp;

            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // Define columns
            String[] columns = {"Voucher Number", "Voucher Type", "Voucher Date", "Company Name", "Reference", "Remarks",
                "Item Description", "Product Type", "Brand", "Sku", "Serial Number", "Expiry Date", "Quantity"};

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (Object data : combinedData) {
                Row row = sheet.createRow(rowNum++);

                if (data instanceof InventoryTransactionReport) {
                    InventoryTransactionReport report = (InventoryTransactionReport) data;

                    row.createCell(0).setCellValue(report.getAutoId());
                    row.createCell(1).setCellValue(report.getVoucherType());

                    Date voucherDate = report.getVoucherDate();
                    String dateStringVoucherDate = (voucherDate != null) ? dateFormat.format(voucherDate) : "";
                    row.createCell(2).setCellValue(dateStringVoucherDate);

                    row.createCell(3).setCellValue(report.getCompanyName());
                    row.createCell(4).setCellValue(report.getReferenceNumber());
                    row.createCell(5).setCellValue(report.getRemarks());

                } else if (data instanceof Response) {
                    Response responseData = (Response) data;

                    row.createCell(6).setCellValue(responseData.getProductName());
                    row.createCell(7).setCellValue(responseData.getCategoryName());
                    row.createCell(8).setCellValue(responseData.getProductTypeName());
                    row.createCell(9).setCellValue(responseData.getSku());
                    row.createCell(10).setCellValue(responseData.getSerialNumber());

                    Date itemSerialExpiry = responseData.getItemSerialExpiry();
                    String expiryDateString = (itemSerialExpiry != null) ? dateFormat.format(itemSerialExpiry) : "";
                    row.createCell(11).setCellValue(expiryDateString);

                    row.createCell(12).setCellValue(responseData.getQuantity());
                }
            }

            // Set content type and headers for response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            // Write workbook to response output stream
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format. Please provide dates in the format yyyy-MM-dd.");
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred while exporting the item movement report.");
        }
    }

    public void handlePartyReportExport(String startDateStr, String endDateStr, String reportName, String companyName, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            // Check if authToken exists in session
            String authToken = (String) session.getAttribute("token");
            if (authToken == null || authToken.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access: AuthToken not found.");
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

            // Fetch party reports from service
            DataResult<List<InventoryTransactionReport>> partyReportResult = this.partyReportService.getPartyReportByDateRangeAndReportName(
                    startDate, endDate, reportName, companyName, channelId);

            List<InventoryTransactionReport> partyReports = partyReportResult.getData();

            // Create a list to hold combined data
            List<Object> combinedData = new ArrayList<>();

            for (InventoryTransactionReport partyReport : partyReports) {
                DataResult<List<Response>> partyReportDetailResult = this.partyReportService.reterivePartyReportById(partyReport.getId(), partyReport.getVoucherType());

                // Add PartyReport to the combined list if it's not null
                if (partyReport != null) {
                    combinedData.add(partyReport);
                }

                // Add all non-null PartyDetails to the combined list
                List<Response> partyDetails = partyReportDetailResult.getData();
                if (partyDetails != null) {
                    for (Response partyDetail : partyDetails) {
                        if (partyDetail != null) {
                            combinedData.add(partyDetail);
                        }
                    }
                }
            }

            // Prepare timestamp for filename and sheet name
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String filename = "party_report_" + timestamp + ".xlsx";
            String sheetName = "Party Report " + timestamp;

            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // Define columns
            String[] columns = {"Voucher Number", "Voucher Type", "Voucher Date", "Company Name", "Reference Number", "Remarks",
                "Item Description", "Product Type", "Brand", "Sku", "Serial Number", "Expiry Date", "Quantity"};

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (Object data : combinedData) {
                Row row = sheet.createRow(rowNum++);

                if (data instanceof InventoryTransactionReport) {
                    InventoryTransactionReport report = (InventoryTransactionReport) data;

                    row.createCell(0).setCellValue(report.getAutoId());
                    row.createCell(1).setCellValue(report.getVoucherType());

                    Date reportDate = report.getVoucherDate();
                    String dateStringReportDate = (reportDate != null) ? dateFormat.format(reportDate) : "";
                    row.createCell(2).setCellValue(dateStringReportDate);

                    row.createCell(3).setCellValue(report.getCompanyName());
                    row.createCell(4).setCellValue(report.getReferenceNumber());
                    row.createCell(5).setCellValue(report.getRemarks());

                } else if (data instanceof Response) {
                    Response detail = (Response) data;

                    row.createCell(6).setCellValue(detail.getProductName() != null ? detail.getProductName() : "");
                    row.createCell(7).setCellValue(detail.getCategoryName() != null ? detail.getCategoryName() : "");
                    row.createCell(8).setCellValue(detail.getProductTypeName() != null ? detail.getProductTypeName() : "");
                    row.createCell(9).setCellValue(detail.getSku() != null ? detail.getSku() : "");
                    row.createCell(10).setCellValue(detail.getSerialNumber() != null ? detail.getSerialNumber() : "");

                    Date expiryDate = detail.getItemSerialExpiry();
                    String expiryDateString = (expiryDate != null) ? dateFormat.format(expiryDate) : "";
                    row.createCell(11).setCellValue(expiryDateString);

                    row.createCell(12).setCellValue(detail.getQuantity());

                }
            }

            // Set content type and headers for response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            // Write workbook to response output stream
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format. Please provide dates in the format yyyy-MM-dd.");
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred while exporting the party report.");
        }
    }

    public void handleDayBookWithItemsReport(String startDateStr, String endDateStr, String customerName, String productName,
            Integer pageNumber, Integer pageSize,
            HttpSession session, Model model, RedirectAttributes redirectAttributes,
            HttpServletResponse response, String saleType) throws IOException {

        try {
            // Check for authorization token in the session
            String authToken = (String) session.getAttribute("token");
            if (authToken == null || authToken.isEmpty()) {
                sendErrorRedirect(response, redirectAttributes, "Unauthorized access. Please login.", "/");
                return;
            }

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Parse the start and end dates
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? null : dateFormat.parse(startDateStr);
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? null : dateFormat.parse(endDateStr);

            // Retrieve the selected store ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            // Create a list to hold combined data
            List<Object> combinedData = new ArrayList<>();

            // Call the service to get the sales list
            DataResult<List<Response>> salesListResult = saleService.saleList(channelId, pageNumber, pageSize, startDate, endDate, customerName, productName, saleType, 0);

//            if (salesListResult.getData() == null || salesListResult.getData().isEmpty()) {
//                sendErrorRedirect(response, redirectAttributes, "No data found for the specified criteria.", "/dayBookWithItems");
//                return;
//            }
            List<Response> salesList = salesListResult.getData();

            List<InventoryTransactionReport> inventoryTransactionReports = new ArrayList<>();

            for (Response saleResponse : salesList) {
                InventoryTransactionReport report = convertToInventoryTransactionReport(saleResponse);
                inventoryTransactionReports.add(report);
            }

            for (InventoryTransactionReport sale : inventoryTransactionReports) {
                DataResult<List<Response>> saleItemDetailsResult = saleService.getSaleItemDetails(sale.getId(), channelId);

                // Add Sale Report to the combined list
                combinedData.add(sale);

                // Add all Sale Item Details to the combined list
                List<Response> saleItemDetails = saleItemDetailsResult.getData();
                if (saleItemDetails != null) {
                    for (Response saleItemDetail : saleItemDetails) {
                        if (saleItemDetail != null) {
                            combinedData.add(saleItemDetail);
                        }
                    }
                }
            }

            // Prepare a timestamp for the filename
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String filename = "day_book_with_items_" + timestamp + ".xlsx";
            String sheetName = "Day Book With Items " + timestamp;

            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // Define columns
            String[] columns = {"Voucher Number", "Voucher Date", "Company Name", "Remarks", "Item Date",
                "Item Description", "Brand", "Company Name", "Quantity"};

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (Object data : combinedData) {
                Row row = sheet.createRow(rowNum++);

                if (data instanceof InventoryTransactionReport) {
                    InventoryTransactionReport report = (InventoryTransactionReport) data;

                    // Check and set each cell value with null checks
                    Integer autoId = report.getAutoId();

                    if (autoId != null) {
                        row.createCell(0).setCellValue(autoId);
                    } else {
                        row.createCell(0).setCellValue("");  // Sets an empty string if autoId is null
                    }

                    Date reportDate = report.getVoucherDate();
                    String dateStringReportDate = (reportDate != null) ? dateFormat.format(reportDate) : "";
                    row.createCell(1).setCellValue(dateStringReportDate);

                    row.createCell(2).setCellValue(report.getCompanyName() != null ? report.getCompanyName() : "");
                    row.createCell(3).setCellValue(report.getRemarks() != null ? report.getRemarks() : "");

                } else if (data instanceof Response) {

                    Response saleItemDetail = (Response) data;

                    row.createCell(4).setCellValue(saleItemDetail.getSaleDate() != null ? saleItemDetail.getSaleDate().toString() : "");
                    row.createCell(5).setCellValue(saleItemDetail.getProductName() != null ? saleItemDetail.getProductName() : "");
                    row.createCell(6).setCellValue(saleItemDetail.getProductTypeName() != null ? saleItemDetail.getProductTypeName() : "");
                    row.createCell(7).setCellValue(saleItemDetail.getChannelName() != null ? saleItemDetail.getChannelName() : "");

                    Integer quantity = saleItemDetail.getQuantity();
                    row.createCell(8).setCellValue(quantity != null ? quantity.toString() : ""); // Convert quantity to String
                }
            }

            // Set content type and headers for response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            // Write the workbook to the response's output stream
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (ParseException e) {
            sendErrorRedirect(response, redirectAttributes, "Invalid date format. Please provide dates in the format yyyy-MM-dd.", "/dayBookWithItems");
        } catch (IOException e) {
            sendErrorRedirect(response, redirectAttributes, "An error occurred while exporting the day book with items: " + e.getMessage(), "/dayBookWithItems");
        }
    }

    private void sendErrorRedirect(HttpServletResponse response, RedirectAttributes redirectAttributes, String errorMessage, String redirectUrl) throws IOException {
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        response.sendRedirect(redirectUrl);
    }

    public InventoryTransactionReport convertToInventoryTransactionReport(Response response) {
        InventoryTransactionReport inventoryTransactionReport = new InventoryTransactionReport();

        // Set AutoId
        inventoryTransactionReport.setAutoId(response.getAutoId());

        // Set Id
        inventoryTransactionReport.setId(response.getId());

        // Convert and set date
        String dateStr = response.getDate();
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = targetFormat.parse(dateStr);
            inventoryTransactionReport.setVoucherDate(date);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            // Optionally, set a default date or handle error
        }

        // Set other fields
        inventoryTransactionReport.setCompanyName(response.getFullName());
        inventoryTransactionReport.setRemarks(response.getRemarks());

        return inventoryTransactionReport;
    }

    // Product Service Export to Excel
    public void handleProductServiceReportExportToExcel(String startDateStr, String endDateStr, String customerName, String productName,
            String itemSerialNumber, Integer pageNumber, Integer pageSize,
            HttpSession session, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {

        try {
            // Check for authorization token in the session
            String authToken = (String) session.getAttribute("token");
            if (authToken == null || authToken.isEmpty()) {
                sendErrorRedirect(response, redirectAttributes, "Unauthorized access. Please login.", "/");
                return;
            }

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Parse the start and end dates
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? null : dateFormat.parse(startDateStr);
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? null : dateFormat.parse(endDateStr);

            // Create a list to hold combined data
            List<Object> combinedData = new ArrayList<>();

            // Call the service to get the sales list
            DataResult<List<Response>> salesListResult = this.productService.getServiceProductList(startDate, endDate, productName, customerName, itemSerialNumber, pageNumber, pageSize);

            List<Response> salesList = salesListResult.getData();

            List<InventoryTransactionReport> inventoryTransactionReports = new ArrayList<>();

            // Check if salesList is null or empty before iterating
            if (salesList != null && !salesList.isEmpty()) {
                // Loop through salesList only if it is not null and has elements
                for (Response saleResponse : salesList) {
                    InventoryTransactionReport report = convertToInventoryTransactionReportData(saleResponse);
                    inventoryTransactionReports.add(report);
                }
            }

            // Check if inventoryTransactionReports is not empty before iterating
            if (!inventoryTransactionReports.isEmpty()) {
                for (InventoryTransactionReport sale : inventoryTransactionReports) {
                    DataResult<List<Response>> saleItemDetailsResult
                            = this.productService.getServiceDetailsBySaleId(sale.getPurchaseItemSerialMasterId());

                    // Add Sale Report to the combined list
                    combinedData.add(sale);

                    // Add all Sale Item Details to the combined list if they exist
                    List<Response> saleItemDetails = saleItemDetailsResult.getData();
                    if (saleItemDetails != null && !saleItemDetails.isEmpty()) {
                        for (Response saleItemDetail : saleItemDetails) {
                            if (saleItemDetail != null) {
                                combinedData.add(saleItemDetail);
                            }
                        }
                    }
                }
            }

            // Prepare a timestamp for the filename
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String filename = "product_service_excel_report_" + timestamp + ".xlsx";
            String sheetName = "Product Service Report " + timestamp;

            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // Define columns
            String[] columns = {"Challan Number", "Date", "Party Name", "Item Description", "Serial Number", "Expiry Date", "Warranty Valid Date", "AMC Valid Date",
                "Sale Service ID", "Service Date", "Remarks", "Solution"};

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (Object data : combinedData) {
                Row row = sheet.createRow(rowNum++);

                if (data instanceof InventoryTransactionReport) {
                    InventoryTransactionReport report = (InventoryTransactionReport) data;

                    // Check and set each cell value with null checks
                    Integer purchaseItemSerialMasterId = report.getPurchaseItemSerialMasterId();

                    if (purchaseItemSerialMasterId != null) {
                        row.createCell(0).setCellValue(purchaseItemSerialMasterId);
                    } else {
                        row.createCell(0).setCellValue("");  // Sets an empty string if autoId is null
                    }

                    Date reportDate = report.getVoucherDate();
                    String dateStringReportDate = (reportDate != null) ? dateFormat.format(reportDate) : "";
                    row.createCell(1).setCellValue(dateStringReportDate);

                    row.createCell(2).setCellValue(report.getCompanyName() != null ? report.getCompanyName() : "");

                    row.createCell(3).setCellValue(report.getProductName() != null ? report.getProductName() : "");

                    row.createCell(4).setCellValue(report.getItemSerialNumber() != null ? report.getItemSerialNumber() : "");

                    Date itemSerialExpiryDate = report.getItemSerialExpiryDate();
                    String dateitemSerialExpiryDate = (itemSerialExpiryDate != null) ? dateFormat.format(itemSerialExpiryDate) : "";
                    row.createCell(5).setCellValue(dateitemSerialExpiryDate);

                    Date warrantyValidDate = report.getWarrantyValidDate();
                    String datewarrantyValidDate = (warrantyValidDate != null) ? dateFormat.format(warrantyValidDate) : "";
                    row.createCell(6).setCellValue(datewarrantyValidDate);

                    Date amcValidDate = report.getAmcValidDate();
                    String dateamcValidDate = (amcValidDate != null) ? dateFormat.format(amcValidDate) : "";
                    row.createCell(7).setCellValue(dateamcValidDate);

                } else if (data instanceof Response) {

                    Response saleItemDetail = (Response) data;

                    Integer saleServiceId = saleItemDetail.getSaleServiceId();
                    if (saleServiceId != null) {
                        row.createCell(8).setCellValue(saleServiceId);
                    } else {
                        row.createCell(8).setCellValue("");  // Sets an empty string if id is null
                    }

                    Date serviceDate = saleItemDetail.getServiceDate();
                    String dateserviceDate = (serviceDate != null) ? dateFormat.format(serviceDate) : "";
                    row.createCell(9).setCellValue(dateserviceDate);

                    row.createCell(10).setCellValue(saleItemDetail.getRemarks() != null ? saleItemDetail.getRemarks() : "");
                    row.createCell(11).setCellValue(saleItemDetail.getSolution() != null ? saleItemDetail.getSolution() : "");

                }
            }

            // Set content type and headers for response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            // Write the workbook to the response's output stream
            workbook.write(response.getOutputStream());
            workbook.close();

        } catch (ParseException e) {
            sendErrorRedirect(response, redirectAttributes, "Invalid date format. Please provide dates in the format yyyy-MM-dd.", "/serviceProductList");
        } catch (IOException e) {
            sendErrorRedirect(response, redirectAttributes, "An error occurred while exporting the day book with items: " + e.getMessage(), "/serviceProductList");
        }
    }

    public InventoryTransactionReport convertToInventoryTransactionReportData(Response response) {
        InventoryTransactionReport inventoryTransactionReport = new InventoryTransactionReport();

        // Set non-date fields
        inventoryTransactionReport.setPurchaseItemSerialMasterId(response.getPurchaseItemSerialMasterId());
        inventoryTransactionReport.setItemSerialNumber(response.getSerialNumber());
        inventoryTransactionReport.setCompanyName(response.getCompanyName());
        inventoryTransactionReport.setProductName(response.getProductName());

        // Convert and set sale date
        String saleDateStr = response.getSaleDateStr();
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date saleDate = targetFormat.parse(saleDateStr);
            inventoryTransactionReport.setVoucherDate(saleDate);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception, possibly log it
            // Optionally, you can handle the invalid date scenario by setting a default date or null
        }

        // Set other fields like expiry date which might already be in Date format
        inventoryTransactionReport.setItemSerialExpiryDate(response.getItemSerialExpiry());

        // Convert and set AMC valid date
        String amcValidDateStr = response.getAmcValidDateStr();
        try {
            Date amcValidDate = targetFormat.parse(amcValidDateStr);
            inventoryTransactionReport.setAmcValidDate(amcValidDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Convert and set Warranty valid date
        String warrantyValidDateStr = response.getWarrantyValidDateStr();
        try {
            Date warrantyValidDate = targetFormat.parse(warrantyValidDateStr);
            inventoryTransactionReport.setWarrantyValidDate(warrantyValidDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return inventoryTransactionReport;
    }

}
