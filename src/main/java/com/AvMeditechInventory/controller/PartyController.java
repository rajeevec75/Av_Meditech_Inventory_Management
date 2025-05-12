/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.AccountUser;
import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.service.AccountUserService;
import com.AvMeditechInventory.service.PartyReportService;
import com.AvMeditechInventory.util.ExcelReportUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class PartyController {

    @Autowired
    private PartyReportService partyReportService;

    @Autowired
    private AccountUserService accountUserService;

    @Autowired
    private ExcelReportUtil excelReportUtil;

    @GetMapping("/partyReport")
    public String getPartyReportPage(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "reportName", defaultValue = "") String reportName,
            @RequestParam(value = "companyName", defaultValue = "") String companyName,
            Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        return getPartyReport(startDateStr, endDateStr, reportName, companyName, model, redirectAttributes, session);
    }

    @PostMapping("/getPartyReportByDateRangeAndReportName")
    public String getPartyReportByDateRangeAndReportName(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "reportName", required = false, defaultValue = "") String reportName,
            @RequestParam(value = "companyName", required = false, defaultValue = "") String companyName,
            Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        return getPartyReport(startDateStr, endDateStr, reportName, companyName, model, redirectAttributes, session);
    }

    private String getPartyReport(String startDateStr, String endDateStr, String reportName, String companyName, 
            Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // If end date is not provided, use today's date
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

            // Retrieve all users
            DataResult<List<AccountUser>> retrieveAllUsers = this.accountUserService.retrieveAllUsers();
            model.addAttribute("retrieveAllUsers", retrieveAllUsers.getData());

            // Retrieve party reports
            DataResult<List<InventoryTransactionReport>> partyReportResult = this.partyReportService.getPartyReportByDateRangeAndReportName(
                    startDate, endDate, reportName, companyName, channelId);

            List<InventoryTransactionReport> partyReports = partyReportResult.getData();
            model.addAttribute("partyReportList", partyReports);

            // Convert dates to strings for display
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);
            model.addAttribute("reportName", reportName);
            model.addAttribute("companyName", companyName);

            return "partyReport";
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format. Please provide dates in the format yyyy-MM-dd.");
            return "partyReport"; // Redirect to the error page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while retrieving the party report.");
            return "partyReport"; // Redirect to the error page
        }
    }

    @GetMapping("/partyReportExportToExcel")
    public void getPartyReportExportToExcel(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "reportName", required = false, defaultValue = "") String reportName,
            @RequestParam(value = "companyName", required = false, defaultValue = "") String companyName,
            HttpServletResponse response,
            HttpSession session,
            RedirectAttributes redirectAttributes) throws IOException {

        this.excelReportUtil.handlePartyReportExport(startDateStr, endDateStr, reportName, companyName, response, session);

    }

    @GetMapping("/partyReportDetails")
    @ResponseBody
    public ResponseEntity<?> reterivePartyReport(
            @RequestParam(value = "id", required = true, defaultValue = "") Integer id,
            @RequestParam(value = "userType", required = true, defaultValue = "") String userType) {
        try {
            // Call the service method to get the report data
            DataResult<List<Response>> inventoryTransactionReport = this.partyReportService.reterivePartyReportById(id, userType);
            List<Response> result = inventoryTransactionReport.getData();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return ResponseEntity.ok(new ErrorDataResult<>("An error occurred while fetching the report"));
        }
    }

}
