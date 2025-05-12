/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.ProductService;
import com.AvMeditechInventory.service.PurchaseService;
import com.AvMeditechInventory.service.SaleService;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ExcelReportUtil;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ServiceProductController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private ExcelReportUtil excelReportUtil;

    @GetMapping("/serviceProductList")
    public String getServiceProductListPage(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "serialNumber", required = false, defaultValue = "") String serialNumber,
            Model model,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            HttpSession session, RedirectAttributes redirectAttributes) throws ParseException {
        return handleServiceProductListRequest(startDateStr, endDateStr, customerName, productName, serialNumber, pageNumber, pageSize, model, session, redirectAttributes);
    }

    private String handleServiceProductListRequest(String startDateStr, String endDateStr, String customerName, String productName, String serialNumber, Integer pageNumber, Integer pageSize, Model model, HttpSession session, RedirectAttributes redirectAttributes) throws ParseException {

        String authToken = (String) session.getAttribute("token");
        if (authToken == null || authToken.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not logged in. Please log in to continue.");
            return "redirect:/";
        }
        // Retrieve customer names based on user types
        List<String> userTypes = Arrays.asList(Constant.CUSTOMER, Constant.BOTH);
        DataResult<List<Response>> customerNameListResult = this.saleService.customerNameList(userTypes);
        List<Response> customerNameList = customerNameListResult.getData();
        model.addAttribute("customerNameList", customerNameList);

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Parse dates or use today's date if not provided
        Date startDate = (startDateStr == null || startDateStr.isEmpty()) ? new Date() : dateFormat.parse(startDateStr);
        Date endDate = (endDateStr == null || endDateStr.isEmpty()) ? new Date() : dateFormat.parse(endDateStr);

        // Retrieve service product list based on filters
        DataResult<List<Response>> serviceProductListResult = productService.getServiceProductList(startDate, endDate, productName, customerName, serialNumber, pageNumber, pageSize);
        List<Response> serviceProductList = serviceProductListResult.getData();
        model.addAttribute("serviceProductList", serviceProductList);

        // Add attributes to the model
        model.addAttribute("startDate", dateFormat.format(startDate));
        model.addAttribute("endDate", dateFormat.format(endDate));
        model.addAttribute("customerName", customerName);
        model.addAttribute("productName", productName);
        model.addAttribute("serialNumber", serialNumber);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);

        // Return the view name
        return "serviceProductList";
    }

    @PostMapping("/updateWarrantyValidDate")
    public String updateWarrantyValidDate(
            @RequestParam(value = "itemSerialNumber", required = false, defaultValue = "") String itemSerialNumber,
            @RequestParam(value = "newWarrantyValidDate", required = false, defaultValue = "") String newWarrantyValidDate,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "serialNumber", required = false, defaultValue = "") String serialNumber,
            RedirectAttributes redirectAttributes) {
        try {
            // Check if required parameters are provided
            if (itemSerialNumber.isEmpty() || newWarrantyValidDate.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Item serial number and warranty valid date must be provided.");
                return "redirect:/serviceProductList?startDate=" + startDateStr
                        + "&endDate=" + endDateStr
                        + "&customerName=" + customerName
                        + "&productName=" + productName
                        + "&serialNumber=" + serialNumber;
            }

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date warrantyValidDate = dateFormat.parse(newWarrantyValidDate);

            // Update warranty valid date using the service method
            Result result = this.serviceDao.updateWarrantyValidDate(itemSerialNumber, warrantyValidDate);

            // Check the result of the update operation
            if (result.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
                return "redirect:/serviceProductList?startDate=" + startDateStr
                        + "&endDate=" + endDateStr
                        + "&customerName=" + customerName
                        + "&productName=" + productName
                        + "&serialNumber=" + serialNumber;
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", result.getMessage());
                return "redirect:/serviceProductList?startDate=" + startDateStr
                        + "&endDate=" + endDateStr
                        + "&customerName=" + customerName
                        + "&productName=" + productName
                        + "&serialNumber=" + serialNumber;
            }

        } catch (ParseException e) {
            // Handle date parsing errors
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format. Please use yyyy-MM-dd.");
            return "redirect:/serviceProductList?startDate=" + startDateStr
                    + "&endDate=" + endDateStr
                    + "&customerName=" + customerName
                    + "&productName=" + productName
                    + "&serialNumber=" + serialNumber;
        } catch (Exception e) {
            // Handle other exceptions
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/serviceProductList?startDate=" + startDateStr
                    + "&endDate=" + endDateStr
                    + "&customerName=" + customerName
                    + "&productName=" + productName
                    + "&serialNumber=" + serialNumber;
        }
    }

    @PostMapping("/updateAmcValidDate")
    public String updateAmcValidDate(
            @RequestParam(value = "itemSerialNumberAmc", required = false, defaultValue = "") String itemSerialNumber,
            @RequestParam(value = "newAmcValidDate", required = false, defaultValue = "") String newAmcValidDate,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "serialNumber", required = false, defaultValue = "") String serialNumber,
            RedirectAttributes redirectAttributes) {
        try {
            // Check if required parameters are provided
            if (itemSerialNumber.isEmpty() || newAmcValidDate.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Item serial number and AMC valid date must be provided.");
                return "redirect:/serviceProductList?startDate=" + startDateStr
                        + "&endDate=" + endDateStr
                        + "&customerName=" + customerName
                        + "&productName=" + productName
                        + "&serialNumber=" + serialNumber;
            }

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date amcValidDate = dateFormat.parse(newAmcValidDate);

            // Update AMC valid date using the service method
            Result result = this.serviceDao.updateAmcValidDate(itemSerialNumber, amcValidDate);

            // Check the result of the update operation
            if (result.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
                return "redirect:/serviceProductList?startDate=" + startDateStr
                        + "&endDate=" + endDateStr
                        + "&customerName=" + customerName
                        + "&productName=" + productName
                        + "&serialNumber=" + serialNumber;
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", result.getMessage());
                return "redirect:/serviceProductList?startDate=" + startDateStr
                        + "&endDate=" + endDateStr
                        + "&customerName=" + customerName
                        + "&productName=" + productName
                        + "&serialNumber=" + serialNumber;
            }

        } catch (ParseException e) {
            // Handle date parsing errors
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid date format. Please use yyyy-MM-dd.");
            return "redirect:/serviceProductList?startDate=" + startDateStr
                    + "&endDate=" + endDateStr
                    + "&customerName=" + customerName
                    + "&productName=" + productName
                    + "&serialNumber=" + serialNumber;
        } catch (Exception e) {
            // Handle other exceptions
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/serviceProductList?startDate=" + startDateStr
                    + "&endDate=" + endDateStr
                    + "&customerName=" + customerName
                    + "&productName=" + productName
                    + "&serialNumber=" + serialNumber;
        }
    }

    @GetMapping("/products/isProductServiceTrue")
    @ResponseBody
    public ResponseEntity<?> getProductListIsProductServiceTrue(
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "pageNumber", defaultValue = "100") Integer pageNumber,
            HttpSession session) {
        try {
            String authToken = (String) session.getAttribute("token");
            if (authToken == null || authToken.isEmpty()) {
                return ResponseEntity.ok("Unauthorized: No valid authentication token found.");
            }

            List<ProductDto> serviceProductDtos = new ArrayList<>();
            DataResult<List<ProductDto>> productListResult = this.purchaseService.retrieveProductListByProductNameOrSku(productName);

            if (productListResult != null && productListResult.getData() != null) {
                List<ProductDto> productList = productListResult.getData();

                for (ProductDto productDto : productList) {
                    boolean isProductService = productDto.isIsProductService();
                    if (isProductService) {
                        serviceProductDtos.add(productDto);
                    }
                }
            }

            return ResponseEntity.ok(serviceProductDtos);
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing your request.";
            // Log the exception (e.g., using a logger)
            return ResponseEntity.ok(errorMessage);
        }
    }

    @PostMapping("/addPerformService")
    public String addPerformService(
            @RequestParam(value = "serviceDate", required = false, defaultValue = "") String serviceDateStr,
            @RequestParam(value = "remarks", required = false, defaultValue = "") String remarks,
            @RequestParam(value = "solution", required = false, defaultValue = "") String solution,
            @RequestParam(value = "saleId", required = false, defaultValue = "") Integer saleId,
            @RequestParam(value = "attachment", required = false) MultipartFile multipartFile,
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "serialNumber", required = false, defaultValue = "") String serialNumber,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request, HttpSession session) {
        try {
            String authToken = (String) session.getAttribute("token");
            if (authToken == null || authToken.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not logged in. Please log in to continue.");
                return "redirect:/";
            }

            // Define the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date serviceDate;
            try {
                serviceDate = dateFormat.parse(serviceDateStr);
            } catch (ParseException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid service date format. Please use 'yyyy-MM-dd'.");
                return "redirect:/serviceProductList?startDate=" + startDateStr
                        + "&endDate=" + endDateStr
                        + "&customerName=" + customerName
                        + "&productName=" + productName
                        + "&serialNumber=" + serialNumber;
            }

            String attachmentName = null;

            if (multipartFile != null && !multipartFile.isEmpty()) {
                String requestUrl = request.getSession().getServletContext().getRealPath("/");
                String imageName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

                // Write image to the server
                boolean status = CommonUtil.writeImage(multipartFile, requestUrl, imageName);
                if (status) {
                    attachmentName = imageName;
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Error");
                    return "redirect:/serviceProductList?startDate=" + startDateStr
                            + "&endDate=" + endDateStr
                            + "&customerName=" + customerName
                            + "&productName=" + productName
                            + "&serialNumber=" + serialNumber;
                }

            }

            // Attempt to insert the perform service record
            Result insertResult = this.serviceDao.insert_sale_service_master(serviceDate, remarks, solution, attachmentName, saleId);
            if (!insertResult.isSuccess()) {
                redirectAttributes.addFlashAttribute("errorMessage", insertResult.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "Service successfully added.");
            }

            return "redirect:/serviceProductList?startDate=" + startDateStr
                    + "&endDate=" + endDateStr
                    + "&customerName=" + customerName
                    + "&productName=" + productName
                    + "&serialNumber=" + serialNumber;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing the request. Please try again.");
            return "redirect:/serviceProductList?startDate=" + startDateStr
                    + "&endDate=" + endDateStr
                    + "&customerName=" + customerName
                    + "&productName=" + productName
                    + "&serialNumber=" + serialNumber;
        }
    }

    @GetMapping("/productServiceDetails")
    @ResponseBody
    public ResponseEntity<?> getServiceDetailsBySaleId(@RequestParam(value = "saleId", defaultValue = "", required = true) Integer saleId) {

        try {
            // Call the service method to get the product service details
            DataResult<List<Response>> result = this.productService.getServiceDetailsBySaleId(saleId);

            // Check if the operation was successful
            if (!result.isSuccess()) {
                // Return an error response
                return ResponseEntity.ok("Error: " + result.getMessage());
            }

            // Retrieve the data from the result
            List<Response> productServiceDetails = result.getData();

            // Return the successful response with the product service details
            return ResponseEntity.ok(productServiceDetails);

        } catch (Exception e) {
            // Handle exceptions by returning an error response
            return ResponseEntity.ok("An error occurred while retrieving product service details: " + e.getMessage());
        }
    }

    @GetMapping("/retrieveImage")
    public ResponseEntity<Resource> retrieveImage(@RequestParam(value = "saleServiceId") Integer saleServiceId, HttpServletRequest request) {
        try {
            // Retrieve the image resource using the service
            Resource file = this.productService.retrieveImage(saleServiceId, request);

            // Check if the image file is null or doesn't exist
            if (file == null || !file.exists()) {
                // Return a custom error message if the image is not found
                return ResponseEntity.status(HttpStatus.OK).body(new ByteArrayResource(("Image not found for saleServiceId: " + saleServiceId).getBytes()));
            }

            // Determine the content type dynamically
            String contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());

            // Fallback to application/octet-stream if content type is not determinable
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Return the image resource with the appropriate content type
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(file);

        } catch (RuntimeException ex) {
            // Handle the case where the image is not found or any runtime exception occurs
            return ResponseEntity.status(HttpStatus.OK).body(new ByteArrayResource(("Success: The image name exists in the database, but the image file is missing from the server.").getBytes()));

        } catch (Exception ex) {
            // Handle unexpected exceptions and return a proper error response
            return ResponseEntity.status(HttpStatus.OK).body(new ByteArrayResource(("An unexpected error occurred while retrieving the image: " + ex.getMessage()).getBytes()));
        }
    }

    @GetMapping("/exportProductServiceToExcel")
    public void exportProductServiceToExcel(
            @RequestParam(value = "startDate", required = false, defaultValue = "") String startDateStr,
            @RequestParam(value = "endDate", required = false, defaultValue = "") String endDateStr,
            @RequestParam(value = "customerName", required = false, defaultValue = "") String customerName,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(value = "serialNumber", required = false, defaultValue = "") String itemSerialNumber,
            HttpServletResponse response, HttpSession session, Model model,
            RedirectAttributes redirectAttributes) throws IOException {
        Integer pageNumber = null;
        Integer pageSize = null;
        this.excelReportUtil.handleProductServiceReportExportToExcel(startDateStr, endDateStr, customerName, productName,
                itemSerialNumber, pageNumber, pageSize, session, model, redirectAttributes, response);
    }

}
