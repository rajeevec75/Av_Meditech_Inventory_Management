package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.service.CombinedStockReportService;
import com.AvMeditechInventory.util.PaginationUtil;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for generating combined stock report.
 */
@Controller
public class CombinedStockReportController {

    private final ServiceDao serviceDao;
    private final PaginationUtil paginationUtil;
    private final CombinedStockReportService combinedStockReportService;

    @Autowired
    public CombinedStockReportController(ServiceDao serviceDao, PaginationUtil paginationUtil, CombinedStockReportService combinedStockReportService) {
        this.serviceDao = serviceDao;
        this.paginationUtil = paginationUtil;
        this.combinedStockReportService = combinedStockReportService;

    }

    @GetMapping("/combinedStockReport")
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

            List<Map<String, Object>> channelList = (List<Map<String, Object>>) session.getAttribute("channelList");

            List<Integer> channelIds = new ArrayList<>(); // List to store extracted channel IDs

            if (channelList != null) {
                for (Map<String, Object> map : channelList) {
                    Integer channelId = (Integer) map.get("id"); // Extract channel_id
                    if (channelId != null) {
                        channelIds.add(channelId); // Add to list
                    }
                }
            }

            // Retrieve categories
            List<Category> fetchAllCategories = this.paginationUtil.fetchAllCategories(pageNumber, authToken, request);
            model.addAttribute("categoryList", fetchAllCategories);

            // Retrieve product types
            List<ProductType> fetchAllProductTypes = this.paginationUtil.fetchAllProductTypes(pageNumber, authToken, request);
            model.addAttribute("productTypeList", fetchAllProductTypes);

            // Retrieve stock report data
            DataResult<List<Response>> stockReportProductWise = combinedStockReportService.getStockReportProductWise(pageNumber, pageSize, productName, productType, brand, channelIds);

            List<Response> responseList = stockReportProductWise.getData();

            // Retrieve total items count
            int totalItems = this.serviceDao.countTotalStockReportProductWiseByChannelId();
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

            return "combinedStockReport";

        } catch (Exception exception) {
            model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "combinedStockReport";
        }
    }

    @GetMapping("/combinedReportProductWiseExportToExcel")
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

        List<Map<String, Object>> channelList = (List<Map<String, Object>>) session.getAttribute("channelList");

        List<Integer> channelIds = new ArrayList<>(); // List to store extracted channel IDs

        if (channelList != null) {
            for (Map<String, Object> map : channelList) {
                Integer channelId = (Integer) map.get("id"); // Extract channel_id
                if (channelId != null) {
                    channelIds.add(channelId); // Add to list
                }
            }
        }

        DataResult<List<Response>> stockReportProductWise;
        stockReportProductWise = this.combinedStockReportService.getStockReportProductWise(pageNumber, pageSize, productName, productType, brand, channelIds);
        List<Response> responses = stockReportProductWise.getData();

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=stock_report_product_wise_" + timestamp + ".xlsx");

        try ( Workbook workbook = new XSSFWorkbook()) {
            String sheetName = "Combined Stock Report " + timestamp;
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
}
