/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.PurchaseImportResponse;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.Channels;
import com.AvMeditechInventory.entities.Sale;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.ChallanService;
import com.AvMeditechInventory.service.PurchaseService;
import com.AvMeditechInventory.service.SaleService;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Controller
public class SalesController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ChallanService challanService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ServiceDao serviceDao;

    @GetMapping("/saleList")
    public String getSaleListPage(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "productName", required = false) String productName,
            HttpSession session, Model model, RedirectAttributes redirectAttributes,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(value = "saleType", required = false, defaultValue = "") String saleType,
            @RequestParam(value = "channelId1", required = false, defaultValue = "") String channelId1) {
        return handleSaleListRequest(startDateStr, endDateStr, customerName, productName, session, model, redirectAttributes, pageNumber, pageSize, saleType, channelId1);
    }

    @PostMapping("/saleListByDateRangeAndFilters")
    public String getSaleListByDateRangeAndFilters(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "customerName", required = false) String customerName,
            @RequestParam(value = "productName", required = false) String productName,
            HttpSession session, Model model, RedirectAttributes redirectAttributes,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(value = "saleType", required = false, defaultValue = "") String saleType,
            @RequestParam(value = "channelId1", required = false, defaultValue = "0") String channelId1) {
        return handleSaleListRequest(startDateStr, endDateStr, customerName, productName, session, model, redirectAttributes, pageNumber, pageSize, saleType, channelId1);
    }

    private String handleSaleListRequest(String startDateStr, String endDateStr, String customerName, String productName,
            HttpSession session, Model model, RedirectAttributes redirectAttributes,
            Integer pageNumber, Integer pageSize, String saleType, String channelId1) {
        try {
            // Retrieve customer names based on user types
            List<String> userTypes = Arrays.asList(Constant.CUSTOMER, Constant.BOTH);
            DataResult<List<Response>> customerNameListResult = saleService.customerNameList(userTypes);
            List<Response> customerNameList = customerNameListResult.getData();
            model.addAttribute("customerNameList", customerNameList);

            // Retrieve product list
            DataResult<List<ProductDto>> productListResult = purchaseService.getProductList();
            List<ProductDto> productList = productListResult.getData();
            model.addAttribute("productList", productList);

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Parse dates or use today's date if not provided
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);
            Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            if (channelId1.equals("") || channelId == Integer.parseInt(channelId1)) {
                channelId1 = "0";
            }

            // Call the service to get the sale list
            DataResult<List<Response>> saleListResult = saleService.saleList(channelId, pageNumber, pageSize, startDate,
                    endDate, customerName, productName, saleType, Integer.parseInt(channelId1));

            List<Response> saleList = saleListResult.getData();
            model.addAttribute("saleList", saleList);

            int totalQuantity = 0;
            for (int i = 0; i < saleList.size(); i++) {
                totalQuantity = totalQuantity + saleList.get(i).getQuantity();
            }
            model.addAttribute("totalQuantity", totalQuantity);

            // Retrieve the list of channels as a List<Map<String, Object>>
            String userEmail = (String) session.getAttribute("userEmail");
            List<Map<String, Object>> channelDataResult = serviceDao.channelList().getData();

            // Prepare a list to store Channels objects
            List<Channels> channels = new ArrayList<>();

            // Iterate through the map and populate the Channels list
            for (Map<String, Object> channelMap : channelDataResult) {
                Channels channel = new Channels();

                // Assuming Channels class has setters for channelId and channelName
                channel.setChannel_id((Integer) channelMap.get("id"));
                channel.setChannelName((String) channelMap.get("name"));

                // Add the channel object to the list
                channels.add(channel);
            }

            // Add the list of channels to the model
            model.addAttribute("channels", channels);
            // Add pagination and date attributes to the model
            model.addAttribute("startDate", dateFormat.format(startDate));
            model.addAttribute("endDate", dateFormat.format(endDate));
            model.addAttribute("customerName", customerName);
            model.addAttribute("productName", productName);
            model.addAttribute("saleType", saleType);
            model.addAttribute("channelId1", channelId1);

            return "saleList";
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format: ");
            return "saleList"; // Redirect to the error page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing your request: ");
            return "saleList"; // Redirect to the error page
        }
    }

    @GetMapping("/createChallan")
    public String createChallan(@RequestParam(value = "saleId", required = true) Integer saleId, Model model, HttpSession session) {
        try {
            // Step 1: Retrieve sale data by ID
            DataResult<Response> userDetailBySaleIdResult = this.challanService.getUserDetailBySaleId(saleId);
            Response response = userDetailBySaleIdResult.getData();
            model.addAttribute("response", response);

            // Step 2: Retrieve delivery challan data by Sale ID
            DataResult<List<Response>> deliveryChallanBySaleId = this.challanService.getDeliveryChallanBySaleId(saleId);
            List<Response> responses = deliveryChallanBySaleId.getData();
            model.addAttribute("responseList", responses);
            model.addAttribute("challan", "SALE CHALLAN");
            model.addAttribute("to", "Customer");

            int totalQuantity = 0;
            for (int i = 0; i < responses.size(); i++) {
                totalQuantity = totalQuantity + responses.get(i).getQuantity();
            }
            model.addAttribute("totalQuantity", totalQuantity);

            return "challan";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create challan due to an unexpected error. Please try again later.");
            return "errorPage";
        }
    }

    @GetMapping("/saleDeliveryChallanQuantity")
    public String deliveryChallanQuantity(@RequestParam(value = "saleId", required = true) Integer saleId, Model model, HttpSession session) {
        try {
            // Step 1: Retrieve sale data by ID
            DataResult<Response> userDetailBySaleIdResult = this.challanService.getUserDetailBySaleId(saleId);
            Response response = userDetailBySaleIdResult.getData();
            model.addAttribute("response", response);

            // Step 2: Retrieve delivery challan data by Sale ID
            DataResult<List<Response>> deliveryChallanBySaleId = this.challanService.getDeliveryChallanQuantityBySaleId(saleId);
            List<Response> responses = deliveryChallanBySaleId.getData();
            model.addAttribute("responseList", responses);
            model.addAttribute("challan", "SALE CHALLAN");
            model.addAttribute("to", "Customer");

            int totalQuantity = 0;
            for (int i = 0; i < responses.size(); i++) {
                totalQuantity = totalQuantity + responses.get(i).getQuantity();
            }
            model.addAttribute("totalQuantity", totalQuantity);

            return "challanOne";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create challan due to an unexpected error. Please try again later.");
            return "errorPage";
        }
    }

    @GetMapping("/saleCreate/json")
    @ResponseBody
    public ResponseEntity<?> getSaleCreateJson(@RequestParam(value = "itemSerialNumber", required = false) String itemBarCode,
            HttpSession session) {
        try {
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            // Call the service to get the barcode for the item serial number
            DataResult<Response> barcodeFromItemSerialNumber = this.saleService.getBarcodeFromItemSerialNumber(itemBarCode, channelId);

            // Check if barcodeFromItemSerialNumber is null or unsuccessful
            if (barcodeFromItemSerialNumber == null || !barcodeFromItemSerialNumber.isSuccess()) {
                return ResponseEntity.ok(null);
            }

            Response response = barcodeFromItemSerialNumber.getData();
            String productName = response.getProductName();
            Date itemSerialExpiry = response.getItemSerialExpiry();

            if (barcodeFromItemSerialNumber.getData().isTrackingSerialNo()) {
                // Check if another product with a nearer expiry is available
                DataResult<Map<String, Object>> nearestExpiryResult = this.serviceDao.findBarcodeByProductAndChannelId(productName, channelId);

                if (nearestExpiryResult.isSuccess()) {
                    Map<String, Object> nearestExpiryData = nearestExpiryResult.getData();
                    Date nearestExpiryDate = (Date) nearestExpiryData.get("item_serial_expiry_date");
                    String nearestBarCode = (String) nearestExpiryData.get("item_barcode");

                    // Truncate milliseconds from both dates
                    itemSerialExpiry = truncateMilliseconds(itemSerialExpiry);
                    nearestExpiryDate = truncateMilliseconds(nearestExpiryDate);

                    if (nearestExpiryDate.before(itemSerialExpiry)) {
//                        response.setProductName(null);
                        response.setWarning("Warning: There is another S No available in the inventory that is nearing expiry - " + nearestBarCode + " ");

                    }
                }
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            String errorMessage = "An error occurred while processing your request.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    private Date truncateMilliseconds(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @PostMapping("/checkSaleCreate")
    @ResponseBody
    public ResponseEntity<?> checkSaleCreate(@RequestParam(value = "purchaseData", required = false) String[] purchaseData) {

        DataResult<String> saleCreate = this.saleService.checkSaleCreate(purchaseData);
        if (saleCreate.isSuccess()) {
            return ResponseEntity.ok(saleCreate.getMessage() + "|" + "true");
        } else {
            return ResponseEntity.ok(saleCreate.getMessage() + "|" + "false");
        }
    }

    @PostMapping("/processSaleCreate")
    public String processSaleCreate(
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
            DataResult<Integer> saleCreate = this.saleService.saleCreate(sale, purchaseData, formattedDate, purchaseSerialData, session);
            if (saleCreate.isSuccess()) {

                Integer saleId = saleCreate.getData();
                redirectAttributes.addFlashAttribute("successMessage", saleCreate.getMessage());
                return "redirect:/getSaleById?saleId=" + saleId;
            }
            redirectAttributes.addFlashAttribute("errorMessage", saleCreate.getMessage());
            return "redirect:/saleCreate";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/saleCreate";
        }
    }

    @PostMapping("/updateSaleCreate")
    public String updateSaleCreate(@ModelAttribute("sale") Sale sale,
            @RequestParam(value = "sale_date", required = false) String saleDateStr,
            @RequestParam(value = "purchaseData", required = false) String[] purchaseData,
            @RequestParam(value = "purchaseSerialData", required = true) String[] purchaseSerialData,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        DataResult<Integer> saleCreate;
        Integer saleId = sale.getSaleId();
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Validate and parse sale date
            Date saleDate = null;
            if (saleDateStr != null && !saleDateStr.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                saleDate = dateFormat.parse(saleDateStr);
            }

            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");

            String purchaseEmailId = (String) session.getAttribute("userEmail");
            DataResult<Map<String, Object>> data = serviceDao.getUserDataByEmail(purchaseEmailId);
            int saleUserId = (int) data.getData().get("id");
            saleCreate = saleService.updateSaleCreate(sale, purchaseData, purchaseSerialData, saleDate, saleUserId, channelId);
            if (saleCreate.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", saleCreate.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", saleCreate.getMessage());
            }
        } catch (ParseException e) {
            // Handle date parsing exception
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format for sale date.");
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            System.err.println("Error processing sale: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing the sale.");
        }

        // Redirect to the sale details page
        return "redirect:/getSaleById?saleId=" + (saleId != null ? saleId : "");
    }

    @GetMapping("/saleCreate")
    public String saleCreatePage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            List<String> userTypes = Arrays.asList(Constant.CUSTOMER, Constant.BOTH);
            DataResult<List<Response>> customerNameList = this.saleService.customerNameList(userTypes);
            List<Response> responses = customerNameList.getData();
            model.addAttribute("customerNameList", responses);

            // Retrieve the list of channels as a List<Map<String, Object>>
            String userEmail = (String) session.getAttribute("userEmail");
            List<Map<String, Object>> channelDataResult = serviceDao.channelList().getData();

            // Prepare a list to store Channels objects
            List<Channels> channels = new ArrayList<>();

            // Iterate through the map and populate the Channels list
            for (Map<String, Object> channelMap : channelDataResult) {
                Channels channel = new Channels();

                // Assuming Channels class has setters for channelId and channelName
                channel.setChannel_id((Integer) channelMap.get("id"));
                channel.setChannelName((String) channelMap.get("name"));

                // Add the channel object to the list
                channels.add(channel);
            }

            // Add the list of channels to the model
            model.addAttribute("channels", channels);

//            DataResult<Integer> saleSeq = null;
//            String storeName = (String) session.getAttribute("selectedStoreName");
//            if (storeName != null && storeName.equals("AV MEDITECH PVT LTD KAITHAL")) {
//                saleSeq = serviceDao.getAutoSaleAvtSeq();
//            } else if (storeName != null && storeName.equals("LENSE HOME KAITHAL")) {
//                saleSeq = serviceDao.getAutoSaleLhtSeq();
//            } else if (storeName != null && storeName.equals("AV MEDITECH GURUGRAM")) {
//                saleSeq = serviceDao.getAutoSaleSeq();
//            } else if (storeName != null && storeName.equals("AV MEDITECH KAITHAL")) {
//                saleSeq = serviceDao.getAutoSaleAmkSeq();
//            }
//            model.addAttribute("saleSeqId", saleSeq.getData());
            String startDateStr = null;

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // If start date is not provided, use today's date
            Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);

            // Convert dates to strings
            String startDateString = dateFormat.format(startDate);
            model.addAttribute("startDate", startDateString);

            Integer pageNumber = 1;
            Integer pageSize = 100;
            String productType = "";
            String brand = "";
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("selectedBrandId", brand);
            model.addAttribute("selectedProductTypeId", productType);
            return "saleCreate";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An unexpected error occurred while loading the sale creation page.");
            return "saleCreate";
        }
    }

    @PostMapping("/importSaleProduct")
    @ResponseBody
    public ResponseEntity<?> importSaleProduct(Model model, @RequestParam("file1") MultipartFile file1, HttpSession session) {
        try {
            // Retrieve the channel ID from the session
            int channelId = (int) session.getAttribute("selectedStoreId");
            // Process the purchase product
            PurchaseImportResponse purchaseProductResult = this.saleService.importSaleProduct(file1, channelId);
            return ResponseEntity.ok(purchaseProductResult);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return ResponseEntity.ok("");
        }
    }

    @GetMapping("/getSaleById")
    public String getSaleDetailsById(
            @RequestParam(value = "saleId", required = true) Integer saleId,
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
            DataResult<Response> saleByIdResult = this.saleService.getSaleDetailsById(saleId, channelId);
            Response response = saleByIdResult.getData();
            model.addAttribute("response", response);
            model.addAttribute("saleType", response.getSale().getSaleType());
            System.out.println("saleType" + response.getSale().getSaleType());
            model.addAttribute("channelId1FromDatabase", response.getSale().getChannelId1());
            System.out.println("channelId1FromDatabase" + response.getSale().getChannelId1());

            // Retrieve the list of channels as a List<Map<String, Object>>
            String userEmail = (String) session.getAttribute("userEmail");

            List<Map<String, Object>> channelDataResult = serviceDao.channelList().getData();

            // Prepare a list to store Channels objects
            List<Channels> channels = new ArrayList<>();

            // Iterate through the map and populate the Channels list
            for (Map<String, Object> channelMap : channelDataResult) {
                Channels channel = new Channels();

                // Assuming Channels class has setters for channelId and channelName
                channel.setChannel_id((Integer) channelMap.get("id"));
                channel.setChannelName((String) channelMap.get("name"));

                // Add the channel object to the list
                channels.add(channel);
            }

            // Add the list of channels to the model
            model.addAttribute("channels", channels);

            // Retrieve customer names based on user types
            List<String> userTypes = Arrays.asList(Constant.CUSTOMER, Constant.BOTH);
            DataResult<List<Response>> customerNameListResult = saleService.customerNameList(userTypes);
            List<Response> customerNameList = customerNameListResult.getData();
            model.addAttribute("customerNameList", customerNameList);

            return "getSaleById";

        } catch (Exception e) {
            // Step 3: Handle any exceptions that occur
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            return "getSaleById";
        }
    }

    @GetMapping("/deleteSaleById")
    public String deleteSaleDetailsBySaleId(@RequestParam(value = "saleId", required = true) Integer saleId,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "productName", required = false) String productName,
            RedirectAttributes redirectAttributes, HttpSession session, Model model) {
        try {

            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Assuming there is a service method to get purchase by ID
            int channelId = (int) session.getAttribute("selectedStoreId");
            Result deleteSaleDetailsById = this.saleService.deleteSaleDetailsById(saleId, channelId);

            if (deleteSaleDetailsById.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", deleteSaleDetailsById.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", deleteSaleDetailsById.getMessage());
            }

            redirectAttributes.addAttribute("startDate", startDateStr);
            redirectAttributes.addAttribute("endDate", endDateStr);
            redirectAttributes.addAttribute("companyName", companyName);
            redirectAttributes.addAttribute("productName", productName);
            return "redirect:/saleList";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while processing the purchase: " + e.getMessage());
            return "redirect:/saleList";
        }
    }

    @GetMapping("/customerNameList/json")
    public ResponseEntity<?> getCustomerNameList(@RequestParam(value = "customerName", required = true) String customerName,
            HttpSession session) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                String errorMessage = "Unauthorized access: Authentication token not found.";
                return ResponseEntity.ok(errorMessage);
            }

            // Assuming you have a list of user types defined in Constant class
            List<String> userTypes = Arrays.asList(Constant.CUSTOMER, Constant.BOTH);

            // Call your service method to fetch customer names based on user types
            DataResult<List<Response>> customerNameListResult = this.saleService.getCustomerNameListByCustomerName(userTypes, customerName);

            if (customerNameListResult.isSuccess()) {
                List<Response> customerNameList = customerNameListResult.getData();
                return ResponseEntity.ok(customerNameList); // Return list of customer names as JSON response
            } else {
                String errorMessage = "Failed to retrieve customer names.";
                return ResponseEntity.ok(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing your request: " + e.getMessage();
            return ResponseEntity.ok(errorMessage);
        }
    }

    @GetMapping("/saleListExportToExcel")
    public void saleListExportToExcel(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            HttpSession session, HttpServletResponse response, RedirectAttributes redirectAttributes,
            @RequestParam(value = "saleType", required = false, defaultValue = "") String saleType,
            @RequestParam(value = "channelId1", required = false, defaultValue = "0") String channelId1) {
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

            if (channelId1.equals("") || channelId == Integer.parseInt(channelId1)) {
                channelId1 = "0";
            }

            // Call the service to get the sale list
            DataResult<List<Response>> saleListResult = this.saleService.saleList(channelId, pageNumber, pageSize,
                    startDate, endDate, customerName, productName, saleType, Integer.parseInt(channelId1));

            List<Response> saleList = saleListResult.getData();

            // Prepare timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // Create an Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("sale_list_" + timestamp);

            // Create the header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Challan Number");
            headerRow.createCell(1).setCellValue("Customer Name");
            headerRow.createCell(2).setCellValue("Reference");
            headerRow.createCell(3).setCellValue("Remarks");
            headerRow.createCell(4).setCellValue("Date");
            headerRow.createCell(5).setCellValue("Quantity");

            // Fill data rows
            int rowNum = 1;
            for (Response sale : saleList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sale.getAutoId());
                row.createCell(1).setCellValue(sale.getFullName());
                row.createCell(2).setCellValue(sale.getReferenceNumber());
                row.createCell(3).setCellValue(sale.getRemarks());
                row.createCell(4).setCellValue(sale.getDate());
                row.createCell(5).setCellValue(sale.getQuantity());

            }

            // Set the response headers and content type
            String fileName = "sales_list_" + timestamp + ".xlsx";
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
            response.sendRedirect("/saleList");
        } catch (IOException ioException) {
            // Handle redirect exception
            ioException.printStackTrace();
        }
    }

}
