/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.PurchaseImportResponse;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.PurchaseMaster;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.ChallanService;
import com.AvMeditechInventory.service.PurchaseService;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private ChallanService challanService;

    @GetMapping("/purchaseList")
    public String getPurchaseListPage(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            Model model, HttpSession session,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            RedirectAttributes redirectAttributes) {

        return getPurchaseList(startDateStr, endDateStr, model, session, companyName, productName, pageNumber, pageSize, redirectAttributes);
    }

    @PostMapping("/purchaseListByDateRangeAndFilters")
    public String getPurchaseListByDateRangeAndFilters(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        return getPurchaseList(startDateStr, endDateStr, model, session, companyName, productName, pageNumber, pageSize, redirectAttributes);
    }

    private String getPurchaseList(String startDateStr, String endDateStr, Model model, HttpSession session,
            String companyName, String productName, Integer pageNumber, Integer pageSize, RedirectAttributes redirectAttributes) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // User Result
            DataResult<List<CustomerAndSupplierDto>> userDataResult = purchaseService.getUserDataByUserType();
            List<CustomerAndSupplierDto> customerAndSupplierDtos = userDataResult.getData();
            model.addAttribute("userDataList", customerAndSupplierDtos);

            // Product Result
            DataResult<List<ProductDto>> productListResult = purchaseService.getProductList();
            List<ProductDto> productDtos = productListResult.getData();
            model.addAttribute("productList", productDtos);

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

            int channelId = (int) session.getAttribute("selectedStoreId");

            // Call the service to get the purchase list
            DataResult<List<Response>> purchaseListResult = this.purchaseService.getPurchaseList(channelId, pageNumber, pageSize,
                    startDate, endDate, companyName, productName);
            List<Response> purchaseList = purchaseListResult.getData();

            model.addAttribute("purchaseList", purchaseList);

            int totalQuantity = 0;
            for (int i = 0; i < purchaseList.size(); i++) {
                totalQuantity = totalQuantity + purchaseList.get(i).getQuantity();
            }
            model.addAttribute("totalQuantity", totalQuantity);

            // Convert dates to strings
            String startDateString = dateFormat.format(startDate);
            String endDateString = dateFormat.format(endDate);
            model.addAttribute("startDate", startDateString);
            model.addAttribute("endDate", endDateString);
            model.addAttribute("companyName", companyName);
            model.addAttribute("productName", productName);

            return "purchaseList";
        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format.");
            return "purchaseList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while retrieving the purchase list page.");
            return "purchaseList";
        }
    }

    @GetMapping("/purchaseProduct")
    public String purchaseProductPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {

            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            DataResult<List<CustomerAndSupplierDto>> userDataResult = this.purchaseService.getUserDataByUserType();
            List<CustomerAndSupplierDto> customerAndSupplierDtos = userDataResult.getData();
            model.addAttribute("userDataList", customerAndSupplierDtos);

            DataResult<List<ProductDto>> productListResult = this.purchaseService.getProductList();
            List<ProductDto> productDtos = productListResult.getData();
            model.addAttribute("productList", productDtos);

//            DataResult<Integer> purSeq = null;
//            String storeName = (String) session.getAttribute("selectedStoreName");
//            if (storeName != null && storeName.equals("AV MEDITECH PVT LTD KAITHAL")) {
//                purSeq = serviceDao.getAutoPurchaseAvtSeq();
//            } else if (storeName != null && storeName.equals("LENSE HOME KAITHAL")) {
//                purSeq = serviceDao.getAutoPurchaseLhtSeq();
//            } else if (storeName != null && storeName.equals("AV MEDITECH GURUGRAM")) {
//                purSeq = serviceDao.getAutoPurchaseSeq();
//            } else if (storeName != null && storeName.equals("AV MEDITECH KAITHAL")) {
//                purSeq = serviceDao.getAutoPurchaseAmkSeq();
//            }
//
//            model.addAttribute("purSeqId", purSeq.getData());
            return "purchaseCreate";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to retrieve the purchase product page.");
            return "purchaseCreate";
        }
    }

    @PostMapping("/processPurchaseProductCreate")
    public String processPurchaseProduct(
            @ModelAttribute("purchase") PurchaseMaster purchase,
            @RequestParam(value = "purchaseData", required = false) String[] purchaseData,
            @RequestParam(value = "purchaseSerialData", required = false) String[] purchaseSerialData,
            @RequestParam(value = "formattedDate", required = false) String formattedDate,
            HttpSession session, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            int channelId = (int) session.getAttribute("selectedStoreId");
            String purchaseEmailId = (String) session.getAttribute("userEmail");
            DataResult<Map<String, Object>> data = serviceDao.getUserDataByEmail(purchaseEmailId);
            int purchaseUserId = (int) data.getData().get("id");
            // Process the purchase product
            DataResult<Integer> processPurchaseProductCreate = this.purchaseService.processPurchaseProductCreate(purchase, 
                    purchaseData, purchaseSerialData, formattedDate, authToken, channelId, purchaseUserId, request, session);

            // Check if the purchase operation was successful
            if (processPurchaseProductCreate.isSuccess()) {
                Integer purchaseId = processPurchaseProductCreate.getData();
                redirectAttributes.addFlashAttribute("successMessage", processPurchaseProductCreate.getMessage());
                return "redirect:/getPurchaseById?purchaseId=" + purchaseId;
            } else {
                // Purchase operation failed
                redirectAttributes.addFlashAttribute("errorMessage", processPurchaseProductCreate.getMessage());
                return "redirect:/purchaseProduct";
            }
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/purchaseProduct";
        }
    }

    @PostMapping("/updatePurchaseCreate")
    public String updatePurchaseCreate(@ModelAttribute("purchase") PurchaseMaster purchase,
            @RequestParam(value = "user_id", required = false) Integer userId,
            @RequestParam(value = "purchase_date", required = false) String purchaseDateStr,
            HttpSession session, RedirectAttributes redirectAttributes) {
        Integer purchaseId = null;

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Parse purchase date
            Date purchaseDate = null;
            if (purchaseDateStr != null && !purchaseDateStr.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                purchaseDate = dateFormat.parse(purchaseDateStr);
            }

            // Update purchase data
            String purchaseEmailId = (String) session.getAttribute("userEmail");
            DataResult<Map<String, Object>> data = serviceDao.getUserDataByEmail(purchaseEmailId);
            int purchaseUserId = (int) data.getData().get("id");
            DataResult<Integer> updateResult = serviceDao.updatePurchaseData(
                    purchase.getPurchaseId().intValue(),
                    purchase.getReferenceNo(),
                    purchase.getRemarks(),
                    userId,
                    purchaseDate, purchaseUserId
            );

            if (updateResult.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", updateResult.getMessage());
                purchaseId = updateResult.getData();
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", updateResult.getMessage());
            }

        } catch (ParseException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing the purchase update.");
        }

        return "redirect:/getPurchaseById?purchaseId=" + (purchaseId != null ? purchaseId : "");
    }

    @PostMapping("/importPurchaseProduct")
    @ResponseBody
    public ResponseEntity<?> importPurchaseProduct(Model model, @RequestParam("file1") MultipartFile file1,
            RedirectAttributes redirectAttributes, HttpSession session,
            @RequestParam(value = "pageNumber", defaultValue = "100") Integer pageNumber, HttpServletRequest request) {
        try {
            // Process the purchase product
            PurchaseImportResponse purchaseProductResult = this.purchaseService.importPurchaseProduct(file1, session, pageNumber, request);
            return ResponseEntity.ok(purchaseProductResult);
        } catch (Exception e) {
            String errorMessage = "Error occurred while importing purchase product: " + e.getMessage();
            model.addAttribute("errorMessage", e.getMessage());
            return ResponseEntity.ok(errorMessage);
        }
    }

    @GetMapping("/productList/json")
    @ResponseBody
    public ResponseEntity<?> getProductList(@RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "pageNumber", defaultValue = "100") Integer pageNumber, HttpSession session, HttpServletRequest request) {
        try {
            String authToken = (String) session.getAttribute("token");
            DataResult<List<ProductDto>> productList = this.purchaseService.productList(productName, authToken, pageNumber, request);
            if (productList.isSuccess()) {
                List<ProductDto> productDtoList = productList.getData();
                return ResponseEntity.ok(productDtoList);
            } else {
                String errorMessage = "An error occurred while processing your request.";
                return ResponseEntity.ok(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing your request.";
            return ResponseEntity.ok(errorMessage);
        }
    }

    @GetMapping("/productList1/json")
    @ResponseBody
    public ResponseEntity<?> retrieveProductListByProductNameOrSku(
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            HttpSession session) {
        try {
            // Call service to retrieve product list
            DataResult<List<ProductDto>> productList = this.purchaseService.retrieveProductListByProductNameOrSku(productName);

            if (!productList.isSuccess()) {
                // Return an error response with status code 404 if the retrieval was unsuccessful
                return ResponseEntity.ok(new ErrorDataResult<>(null, "No products found."));
            }

            List<ProductDto> data = productList.getData();
            // Return a successful response with status code 200
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            // Return an error response with status code 500 if an exception occurs
            return ResponseEntity.ok(new ErrorDataResult<>(null, "An error occurred while fetching the product list: " + e.getMessage()));
        }
    }

    @GetMapping("/calculateSerialNo")
    @ResponseBody
    public ResponseEntity<?> calculateSerialNo(@RequestParam(value = "itemSerialNumber", required = false) String itemSerialNumber,
            @RequestParam(value = "productSerialPattern", required = false) String productSerialPattern) {
        try {
            // Call the service to get the barcode for the item serial number
            HashMap<String, Object> data = CommonUtil.splitExpSr(itemSerialNumber, productSerialPattern);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            // Handle exceptions
            String errorMessage = "An error occurred while processing your request.";
            return ResponseEntity.ok(errorMessage);
        }
    }

    @GetMapping("/getPurchaseById")
    public String getPurchaseDetailsById(
            @RequestParam(value = "purchaseId", required = true) Integer purchaseId,
            HttpSession session, RedirectAttributes redirectAttributes, Model model) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Assuming there is a service method to get purchase by ID
            int channelId = (int) session.getAttribute("selectedStoreId");
            DataResult<Response> purchaseByIdResult = purchaseService.get_purchase_details_by_id(purchaseId, channelId);
            Response response = purchaseByIdResult.getData();
            model.addAttribute("purchase", response);
            // User Result
            DataResult<List<CustomerAndSupplierDto>> userDataResult = purchaseService.getUserDataByUserType();
            List<CustomerAndSupplierDto> customerAndSupplierDtos = userDataResult.getData();
            model.addAttribute("userDataList", customerAndSupplierDtos);
            return "showPurchaseById";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while retrieving the purchase");
            return "showPurchaseById";
        }

    }

    @GetMapping("/deletePurchaseById")
    public String deletePurchaseDetailsByPurchaseId(@RequestParam(value = "purchaseId", required = true) Integer purchaseId,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "productName", required = false) String productName,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Assuming there is a service method to get purchase by ID

            int channelId = (int) session.getAttribute("selectedStoreId");

            Result deletePurchaseDetailsByIdResult = purchaseService.delete_purchase_details_by_id(purchaseId, channelId);

            if (deletePurchaseDetailsByIdResult.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", deletePurchaseDetailsByIdResult.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", deletePurchaseDetailsByIdResult.getMessage());
            }
            redirectAttributes.addAttribute("startDate", startDateStr);
            redirectAttributes.addAttribute("endDate", endDateStr);
            redirectAttributes.addAttribute("companyName", companyName);
            redirectAttributes.addAttribute("productName", productName);
            return "redirect:/purchaseList";

        } catch (Exception exception) {
            model.addAttribute("errorMessage", "An error occurred while processing the purchase." + exception.getMessage());
            return "redirect:/purchaseList";
        }
    }

    @GetMapping("/companyNameList/json")
    @ResponseBody
    public ResponseEntity<?> getCompanyNameListByUserTypesAndCompanyName(@RequestParam(value = "companyName", required = false) String companyName, HttpSession session) {
        try {
            String authToken = (String) session.getAttribute("token");
            if (authToken == null || authToken.isEmpty()) {
                return ResponseEntity.ok("Unauthorized access: Missing or invalid token.");
            }

            // Assuming you have a list of user types defined in Constant class
            List<String> userTypes = Arrays.asList(Constant.SUPPLIER, Constant.BOTH);
            DataResult<List<CustomerAndSupplierDto>> userDataResult = this.purchaseService.getCompanyNameListByUserTypesAndCompanyName(userTypes, companyName);

            if (userDataResult.isSuccess()) {
                List<CustomerAndSupplierDto> customerAndSupplierList = userDataResult.getData();
                return ResponseEntity.ok(customerAndSupplierList);
            } else {
                String errorMessage = "Failed to retrieve the company name list.";
                return ResponseEntity.ok(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing your request: " + e.getMessage();
            return ResponseEntity.ok(errorMessage);
        }
    }

    @GetMapping("/purchaseDeliveryChallan")
    public String purchaseDeliveryChallan(@RequestParam(value = "purchaseId", required = true) Integer purchaseId, Model model, HttpSession session) {
        try {
            // Step 1: Retrieve user detail by purchase ID
            DataResult<Response> userDetailByPurchaseIdResult = this.challanService.getUserDetailByPurchaseId(purchaseId);
            Response userDetailResponse = userDetailByPurchaseIdResult.getData();
            model.addAttribute("response", userDetailResponse);

            // Step 2: Retrieve delivery challan details by purchase ID
            DataResult<List<Response>> deliveryChallanByPurchaseIdResult = this.challanService.getDeliveryChallanByPurchaseId(purchaseId);
            List<Response> deliveryChallanResponses = deliveryChallanByPurchaseIdResult.getData();
            model.addAttribute("responseList", deliveryChallanResponses);
            model.addAttribute("challan", "Purchase");
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

    @GetMapping("/purchaseDeliveryChallanQuantity")
    public String purchaseDeliveryChallanQuantity(@RequestParam(value = "purchaseId", required = true) Integer purchaseId, Model model, HttpSession session) {
        try {
            // Step 1: Retrieve user detail by purchase ID
            DataResult<Response> userDetailByPurchaseIdResult = this.challanService.getUserDetailByPurchaseId(purchaseId);
            Response userDetailResponse = userDetailByPurchaseIdResult.getData();
            model.addAttribute("response", userDetailResponse);

            // Step 2: Retrieve delivery challan details by purchase ID
            DataResult<List<Response>> deliveryChallanByPurchaseIdResult = this.challanService.getDeliveryChallanQuantityByPurchaseId(purchaseId);
            List<Response> deliveryChallanResponses = deliveryChallanByPurchaseIdResult.getData();
            model.addAttribute("responseList", deliveryChallanResponses);
            model.addAttribute("challan", "Purchase");
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

    @GetMapping("/purchaseListExportToExcel")
    public void purchaseListExportToExcel(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "companyName", required = false, defaultValue = "") String companyName,
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

            // Call the service to get the purchase list
            DataResult<List<Response>> purchaseListResult
                    = this.purchaseService.getPurchaseList(channelId, pageNumber, pageSize, startDate, endDate,
                            companyName, productName);

            List<Response> purchaseList = purchaseListResult.getData();

            // Prepare timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            try ( // Create an Excel workbook
                     Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("purchase_list_" + timestamp);

                // Create the header row
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Challan Number");
                headerRow.createCell(1).setCellValue("Company Name");
                headerRow.createCell(2).setCellValue("Purchase Date");
                headerRow.createCell(3).setCellValue("Reference");
                headerRow.createCell(4).setCellValue("Remarks");
                headerRow.createCell(5).setCellValue("Quantity");

                // Fill data rows
                int rowNum = 1;
                for (Response purchase : purchaseList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(purchase.getAutoId());
                    row.createCell(1).setCellValue(purchase.getCompanyName());
                    row.createCell(2).setCellValue(purchase.getDate());
                    row.createCell(3).setCellValue(purchase.getReferenceNumber());
                    row.createCell(4).setCellValue(purchase.getRemarks());
                    row.createCell(5).setCellValue(purchase.getQuantity());

                }

                // Set the response headers and content type
                String fileName = "purchase_list_" + timestamp + ".xlsx";
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

                // Write the workbook to the response output stream
                workbook.write(response.getOutputStream());
            }

        } catch (IOException | ParseException e) {
            handleExportError(redirectAttributes, e, response);
        }
    }

    private void handleExportError(RedirectAttributes redirectAttributes, Exception e, HttpServletResponse response) {
        redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while exporting purchase list: " + e.getMessage());
        redirectAttributes.addFlashAttribute("errorStackTrace", e.getStackTrace());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try {
            response.sendRedirect("/purchaseList");
        } catch (IOException ioException) {
            // Handle redirect exception

        }
    }

}
