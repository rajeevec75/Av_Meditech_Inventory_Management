/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.service.SaleService;
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
 * @author Rajeev kumar
 */
@Controller
public class DayBookWithItemsController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ExcelReportUtil excelReportUtil;

    @GetMapping("/dayBookWithItems")
    public String dayBookWithItemsPage(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            HttpSession session, Model model, RedirectAttributes redirectAttributes,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(value = "saleType", required = false, defaultValue = "") String saleType) {
        return getDayBookWithItems(startDateStr, endDateStr, customerName, productName, session, model, redirectAttributes, pageNumber, pageSize, saleType);
    }

    @PostMapping("/getDayBookWithItems")
    public String getDayBookWithItemsByDateFilters(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "productName", required = false) String productName,
            HttpSession session, Model model, RedirectAttributes redirectAttributes,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(value = "saleType", required = false, defaultValue = "") String saleType) {
        return getDayBookWithItems(startDateStr, endDateStr, customerName, productName, session, model, redirectAttributes, pageNumber, pageSize, saleType);
    }

    private String getDayBookWithItems(String startDateStr, String endDateStr, String customerName, String productName, HttpSession session, Model model, RedirectAttributes redirectAttributes, Integer pageNumber, Integer pageSize, String saleType) {
        try {
            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // Date endDate
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            // Call the service to get the sale list
            DataResult<List<Response>> saleListResult = this.saleService.saleList(channelId, pageNumber, pageSize, startDate, endDate, customerName, productName, saleType, 0);

            List<Response> saleList = saleListResult.getData();
            model.addAttribute("saleList", saleList);

            // Convert dates to strings
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);
            return "dayBookWithItems";
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format: ");
            return "dayBookWithItems"; // Redirect to the error page

        } catch (Exception e) {
            // Redirect to the same URL to display the error message
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while loading the day book with items. Please try again later.");
            return "dayBookWithItems";
        }
    }

    @GetMapping("/dayBookWithItem")
    @ResponseBody
    public ResponseEntity<?> saleItemDetails(@RequestParam(value = "saleId", required = true) Integer saleId, HttpSession session) {
        try {
            int channelId = (int) session.getAttribute("selectedStoreId");
            DataResult<List<Response>> saleItemDetailList = this.saleService.getSaleItemDetails(saleId, channelId);
            List<Response> responses = saleItemDetailList.getData();
            return ResponseEntity.ok(responses);

        } catch (Exception e) {
            return ResponseEntity.ok(new ErrorDataResult<>("An error occurred while retrieving sale item details."));
        }
    }

    @GetMapping("/dayBookWithItemsExportToExcel")
    public void dayBookWithItemsExportToExcel(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletResponse response,
            @RequestParam(value = "saleType", required = false, defaultValue = "") String saleType) throws IOException {
        this.excelReportUtil.handleDayBookWithItemsReport(startDateStr, endDateStr, customerName, productName, pageNumber, pageSize, session, model, redirectAttributes, response, saleType);

    }

}
