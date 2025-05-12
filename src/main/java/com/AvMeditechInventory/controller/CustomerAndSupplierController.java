/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.entities.AccountUser;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.AccountUserService;
import com.AvMeditechInventory.util.ServiceDao;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.AvMeditechInventory.service.CustomerAndSupplierService;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.PaginationUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Controller
public class CustomerAndSupplierController {

    @Autowired
    private CustomerAndSupplierService customerAndSupplierService;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private AccountUserService accountUserService;

    @GetMapping("/addUser")
    public String customerAndSupplierCreatePage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            return "customerAndSupplierCreate";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while loading the create customer page: ");
            return "customerAndSupplierCreate";
        }

    }

    @PostMapping("/processCustomerAndSupplier")
    public String processCustomerAndSupplier(@ModelAttribute("customerAndSupplierDto") CustomerAndSupplierDto customerAndSupplierDto,
            RedirectAttributes redirectAttributes, HttpServletRequest request, HttpSession session) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Call the service to create the customer or supplier
            DataResult<String> addCustomerAndSupplierResult = this.customerAndSupplierService.addCustomerAndSupplier(customerAndSupplierDto, authToken, request);

            if (!addCustomerAndSupplierResult.isSuccess()) {
                // If creation fails, add an error message to the model and redirect to the customer creation page
                redirectAttributes.addFlashAttribute("errorMessage", addCustomerAndSupplierResult.getMessage());
                return "redirect:/addUser";
            }

            // Determine the user type and redirect accordingly
            String userType = customerAndSupplierDto.getUserType();
            if (userType != null) {
                if (userType.equals(Constant.CUSTOMER)) {
                    redirectAttributes.addFlashAttribute("successMessage", addCustomerAndSupplierResult.getMessage());
                    return "redirect:/customerList";
                } else if (userType.equals(Constant.SUPPLIER) || userType.equals(Constant.BOTH)) {
                    redirectAttributes.addFlashAttribute("successMessage", addCustomerAndSupplierResult.getMessage());
                    return "redirect:/supplierList";
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "User type is not recognized.");
                    return "redirect:/addUser";
                }
            }

            // If userType is null, add an error message
            redirectAttributes.addFlashAttribute("errorMessage", "User type is not recognized.");
            return "redirect:/addUser";

        } catch (Exception e) {
            // Handle any unexpected errors
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while creating the customer or supplier.");
            return "redirect:/addUser";
        }
    }

    @GetMapping("/updateUser")
    public String customerAndSupplierUpdatePage(
            @RequestParam(value = "customerId", required = false) String customerId,
            @RequestParam(value = "supplierId", required = false) String supplierId,
            HttpSession session, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            DataResult<CustomerAndSupplierDto> customerResult = null;

            if (supplierId == null && customerId != null) {
                customerResult = this.customerAndSupplierService.getCustomerById(customerId, authToken, request);
            } else if (customerId == null && supplierId != null) {
                customerResult = this.customerAndSupplierService.getCustomerById(supplierId, authToken, request);
            } else {
                model.addAttribute("errorMessage", "Both customerId and supplierId cannot be null.");
                return "customerAndSupplierUpdate";
            }

            if (!customerResult.isSuccess()) {
                // If fetching customer details fails, return an error page
                model.addAttribute("errorMessage", customerResult.getMessage());
                return "customerAndSupplierUpdate";

            }

            CustomerAndSupplierDto customerDto = customerResult.getData();
            String customerEmail = customerDto.getEmail();

            // Retrieve user data and company name by email
            DataResult<Map<String, Object>> userDataByEmail = serviceDao.getUserDataByEmail(customerEmail);
            DataResult<String> companyNameResult = serviceDao.getCompanyNameByEmail(customerEmail);

            if (!userDataByEmail.isSuccess() || !companyNameResult.isSuccess()) {
                // If fetching user data or company name fails, return an error page
                model.addAttribute("errorMessage", userDataByEmail.getMessage());
            }

            Map<String, Object> userData = userDataByEmail.getData();
            String companyName = companyNameResult.getData();

            // Populate the ModelAndView object with retrieved data
            model.addAttribute("customerDto", customerDto); // Customer data
            model.addAttribute("gender", userData.get("gender")); // Gender
            model.addAttribute("dateOfBirth", userData.get("date_of_birth")); // Date of birth
            model.addAttribute("panNumber", userData.get("pan_number")); // PAN number
            model.addAttribute("gstNumber", userData.get("gst_number")); // GST number
            model.addAttribute("tinNumber", userData.get("tin_number")); // TIN number
            model.addAttribute("userType", userData.get("user_type")); // userType
            model.addAttribute("userCode", userData.get("user_code")); // userCode
            model.addAttribute("companyName", companyName); // companyName

            return "customerAndSupplierUpdate";
        } catch (Exception e) {
            // If an unexpected error occurs, return an error page
            model.addAttribute("errorMessage", "An error occurred while loading the customer update page.");
            return "customerAndSupplierUpdate";
        }

    }

    @PostMapping("/processUpdate")
    public String processCustomerAndSupplierUpdate(HttpServletRequest request, HttpSession session, Model model,
            @ModelAttribute("customerAndSupplierDto") CustomerAndSupplierDto customerAndSupplierDto,
            RedirectAttributes redirectAttributes) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Update customer and supplier
            DataResult<String> updateCustomerAndSupplier = this.customerAndSupplierService.updateCustomerAndSupplier(customerAndSupplierDto, authToken, request);
            String userType = customerAndSupplierDto.getUserType();

            // Handle failure case
            if (!updateCustomerAndSupplier.isSuccess()) {
                redirectAttributes.addFlashAttribute("errorMessage", updateCustomerAndSupplier.getMessage());
                return handleRedirectionBasedOnUserType(userType, customerAndSupplierDto);
            }

            // Handle success case
            redirectAttributes.addFlashAttribute("successMessage", updateCustomerAndSupplier.getMessage());
            if (Constant.CUSTOMER.equals(userType)) {
                return "redirect:/customerList";
            } else if (Constant.SUPPLIER.equals(userType) || Constant.BOTH.equals(userType)) {
                return "redirect:/supplierList";
            } else {
                model.addAttribute("errorMessage", "User type is not recognized.");
                return handleRedirectionBasedOnUserType(userType, customerAndSupplierDto);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while updating customer/supplier information.");
            String userType = customerAndSupplierDto.getUserType();
            return handleRedirectionBasedOnUserType(userType, customerAndSupplierDto);
        }
    }

    // Helper method to handle redirection based on user type
    private String handleRedirectionBasedOnUserType(String userType, CustomerAndSupplierDto customerAndSupplierDto) {
        if (userType != null) {
            if (userType.contains("customer")) {
                return "redirect:/updateUser?customerId=" + customerAndSupplierDto.getId();
            } else if (userType.contains("supplier") || userType.contains("both")) {
                return "redirect:/updateUser?supplierId=" + customerAndSupplierDto.getId();
            }
        }
        return "redirect:/updateUser"; // Fallback redirection
    }

    @GetMapping("/customerList")
    public String customerListPage(
            @RequestParam(value = "pageNumber", defaultValue = "50") Integer pageNumber,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "isAsc", defaultValue = "next") String isAsc,
            Model model, HttpSession session, RedirectAttributes redirectAttributes,
            @RequestParam(value = "mobileNo", required = false, defaultValue = "") String mobileNo,
            @RequestParam(value = "userCode", required = false, defaultValue = "") String userCode,
            @RequestParam(value = "companyName", required = false, defaultValue = "") String companyName,
            HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");

            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Retrieve all users
            List<String> userTypeList = new ArrayList<>();
            userTypeList.add("customer");
            userTypeList.add("both");

            DataResult<List<AccountUser>> retrieveAllUsers = this.accountUserService.getAccountsByUserType(userTypeList);
            model.addAttribute("retrieveAllUsers", retrieveAllUsers.getData());

            List<CustomerAndSupplierDto> customerListResult = this.paginationUtil.fetchAllCustomers(authToken, pageNumber, mobileNo, userCode, companyName, request);

            model.addAttribute("customerList", customerListResult);
            model.addAttribute("mobileNo", mobileNo);
            model.addAttribute("userCode", userCode);
            model.addAttribute("companyName", companyName);

            return "customerList";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while loading the customer list: ");
            return "customerList";
        }
    }

    @GetMapping("/supplierList")
    public String supplierList(
            @RequestParam(value = "pageNumber", defaultValue = "50") Integer pageNumber,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "isAsc", defaultValue = "next") String isAsc,
            Model model, HttpSession session, RedirectAttributes redirectAttributes,
            @RequestParam(value = "mobileNo", required = false, defaultValue = "") String mobileNo,
            @RequestParam(value = "userCode", required = false, defaultValue = "") String userCode,
            @RequestParam(value = "companyName", required = false, defaultValue = "") String companyName, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Retrieve all users
            List<String> userTypeList = new ArrayList<>();
            userTypeList.add("supplier");
            userTypeList.add("both");
            DataResult<List<AccountUser>> retrieveAllUsers = this.accountUserService.getAccountsByUserType(userTypeList);
            model.addAttribute("retrieveAllUsers", retrieveAllUsers.getData());

            List<CustomerAndSupplierDto> supplierListResult = this.paginationUtil.fetchAllSuppliers(authToken, pageNumber, mobileNo, userCode, companyName, request);

            model.addAttribute("supplierList", supplierListResult);
            model.addAttribute("mobileNo", mobileNo);
            model.addAttribute("userCode", userCode);
            model.addAttribute("companyName", companyName);

            return "supplierList";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while retrieving the supplier list: ");
            return "supplierList";
        }
    }

    @GetMapping("/deleteUser")
    public String customerDelete(
            @RequestParam(value = "customerId", required = false) String customerId,
            @RequestParam(value = "supplierId", required = false) String supplierId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Ensure that either customerId or supplierId is provided, but not both
            if (customerId != null && supplierId == null) {
                Result customerDeleteResult = this.customerAndSupplierService.customerDelete(customerId, authToken, request);
                if (customerDeleteResult.isSuccess()) {
                    redirectAttributes.addFlashAttribute("successMessage", customerDeleteResult.getMessage());
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete customer.");
                }
                return "redirect:/customerList";
            }

            if (supplierId != null && customerId == null) {
                Result supplierDeleteResult = this.customerAndSupplierService.customerDelete(supplierId, authToken, request);
                if (supplierDeleteResult.isSuccess()) {
                    redirectAttributes.addFlashAttribute("successMessage", supplierDeleteResult.getMessage());
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete supplier.");
                }
                return "redirect:/supplierList";
            }
            return null;

        } catch (Exception e) {
            if (customerId != null && supplierId == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while deleting the customer.");
                return "redirect:/customerList";
            }

            if (supplierId != null && customerId == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while deleting the supplier.");
                return "redirect:/supplierList";
            }
            return null;
        }
    }

    @GetMapping("/customerListExportToExcel")
    public void customerListExportToExcel(
            @RequestParam(value = "pageNumber", defaultValue = "50") Integer pageNumber,
            @RequestParam(value = "mobileNo", required = false, defaultValue = "") String mobileNo,
            @RequestParam(value = "userCode", required = false, defaultValue = "") String userCode,
            @RequestParam(value = "companyName", required = false, defaultValue = "") String companyName,
            RedirectAttributes redirectAttributes, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException {

        // Check authentication token
        String authToken = (String) session.getAttribute("token");
        if (authToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
            response.sendRedirect("/");
            return;
        }

        try {
            // Fetch all customers
            List<CustomerAndSupplierDto> customerList = paginationUtil.fetchAllCustomers(authToken, pageNumber,
                    mobileNo, userCode, companyName, request);
            if (customerList == null || customerList.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No customers found for the given criteria.");
                response.sendRedirect("/customerList");
                return;
            }

            // Prepare timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // Prepare filename with timestamp
            String filename = "customer_list_" + timestamp + ".xlsx";

            // Set response headers
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            // Create Excel workbook
            try ( Workbook workbook = new XSSFWorkbook()) {
                // Create sheet with timestamp in the name
                String sheetName = "Customer List " + timestamp;
                Sheet sheet = workbook.createSheet(sheetName);

                // Define headers
                List<String> headers = List.of("Company Name", "Mobile Number", "User Code");

                // Create header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers.get(i));
                }

                // Populate data rows
                int rowNum = 1;
                for (CustomerAndSupplierDto customer : customerList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(customer.getCompanyName());
                    row.createCell(1).setCellValue(customer.getMobileNumber());
                    row.createCell(2).setCellValue(customer.getUserCode());
                }

                // Write workbook to response output stream
                workbook.write(response.getOutputStream());
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while exporting the customer list. Please try again.");
            response.sendRedirect("/customerList");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            response.sendRedirect("/customerList");
        }
    }

    @GetMapping("/supplierListExportToExcel")
    public void supplierListExportToExcel(
            @RequestParam(value = "pageNumber", defaultValue = "50") Integer pageNumber,
            @RequestParam(value = "mobileNo", required = false, defaultValue = "") String mobileNo,
            @RequestParam(value = "userCode", required = false, defaultValue = "") String userCode,
            @RequestParam(value = "companyName", required = false, defaultValue = "") String companyName,
            RedirectAttributes redirectAttributes, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException {

        // Check authentication token
        String authToken = (String) session.getAttribute("token");
        if (authToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
            response.sendRedirect("/");
            return;
        }

        try {
            // Fetch all suppliers
            List<CustomerAndSupplierDto> supplierList = this.paginationUtil.fetchAllSuppliers(authToken, pageNumber,
                    mobileNo, userCode, companyName, request);
            if (supplierList == null || supplierList.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No suppliers found for the given criteria.");
                response.sendRedirect("/supplierList");
                return;
            }

            // Prepare timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // Prepare filename with timestamp
            String filename = "supplier_list_" + timestamp + ".xlsx";

            // Set response headers
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            // Create Excel workbook
            try ( Workbook workbook = new XSSFWorkbook()) {
                // Create sheet with timestamp in the name
                String sheetName = "Supplier List " + timestamp;
                Sheet sheet = workbook.createSheet(sheetName);

                // Define headers
                List<String> headers = List.of("Company Name", "Mobile Number", "User Code");

                // Create header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers.get(i));
                }

                // Populate data rows
                int rowNum = 1;
                for (CustomerAndSupplierDto supplier : supplierList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(supplier.getCompanyName());
                    row.createCell(1).setCellValue(supplier.getMobileNumber());
                    row.createCell(2).setCellValue(supplier.getUserCode());
                }

                // Write workbook to response output stream
                workbook.write(response.getOutputStream());
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while exporting the supplier list. Please try again.");
            response.sendRedirect("/supplierList");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            response.sendRedirect("/supplierList");
        }
    }

}
