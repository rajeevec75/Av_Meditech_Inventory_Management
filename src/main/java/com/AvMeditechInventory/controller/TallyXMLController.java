package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.service.InventoryTransactionReportService;
import com.AvMeditechInventory.tally.xml.export.TallyXMLExporter;
import com.AvMeditechInventory.util.HTTPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.util.StreamUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class TallyXMLController {

    @Autowired
    private InventoryTransactionReportService inventoryTransactionReportService;

    @Autowired
    private TallyXMLExporter tallyXMLExporter;

    @GetMapping("/tallyExport")
    public void exportTallyXML(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "transactionName", defaultValue = "purchase") String transactionName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "productType", required = false, defaultValue = "") String productType,
            @RequestParam(value = "brand", required = false, defaultValue = "") String brand,
            HttpSession session,
            HttpServletResponse response) {

        try {
            // Log request details
            HTTPUtil.displayRequest("Received /tallyExport request with params: startDate=" + startDateStr
                    + ", endDate=" + endDateStr + ", transactionName=" + transactionName
                    + ", productName=" + productName + ", productType=" + productType + ", brand=" + brand);

            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorized to access this resource.");
                HTTPUtil.displayResponse("Error response sent: Unauthorized access.");
                return;
            }

            Object storeIdObj = session.getAttribute("selectedStoreId");
            if (!(storeIdObj instanceof Integer)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Store ID is missing or invalid.");
                HTTPUtil.displayResponse("Error response sent: Store ID is missing or invalid.");
                return;
            }
            Integer channelId = (Integer) storeIdObj;

            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = (startDateStr.isEmpty()) ? null : DATE_FORMAT.parse(startDateStr);
            Date endDate = (endDateStr.isEmpty()) ? null : DATE_FORMAT.parse(endDateStr);

            if (startDate != null && endDate != null && startDate.after(endDate)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Start date cannot be after end date.");
                HTTPUtil.displayResponse("Error response sent: Start date cannot be after end date.");
                return;
            }

            DataResult<List<InventoryTransactionReport>> inventoryTransReportResult = inventoryTransactionReportService.getInventoryTransactionReportByDateRangeAndTransactionName(startDate, endDate, transactionName, productName, productType, brand, channelId);

            List<InventoryTransactionReport> transactionReports = inventoryTransReportResult.getData();

            File xmlFile = this.tallyXMLExporter.exportToXML(transactionReports, transactionName);

            if (xmlFile != null && xmlFile.exists() && xmlFile.isFile()) {
                try ( FileInputStream inputStream = new FileInputStream(xmlFile)) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + xmlFile.getName() + "\"");
                    response.setContentLength((int) xmlFile.length());
                    StreamUtils.copy(inputStream, response.getOutputStream());
                    response.flushBuffer();

                    // Log successful response
                    HTTPUtil.displayResponse("Successfully exported XML file: " + xmlFile.getName());
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create the XML file.");
                HTTPUtil.displayResponse("Error response sent: Failed to create the XML file.");
            }
        } catch (ParseException e) {
            HTTPUtil.displayException(e);
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format. Please use 'yyyy-MM-dd'.");
            } catch (IOException ioException) {
                HTTPUtil.displayException(ioException);
            }
        } catch (Exception e) {
            HTTPUtil.displayException(e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
            } catch (IOException ioException) {
                HTTPUtil.displayException(ioException);
            }
        }
    }

}
