/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.util;

import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.StaffDto;
import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.service.CategoryService;
import com.AvMeditechInventory.service.CustomerAndSupplierService;
import com.AvMeditechInventory.service.ProductService;
import com.AvMeditechInventory.service.ProductTypeService;
import com.AvMeditechInventory.service.StaffService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rajeev kumar
 */
@Component
public class PaginationUtil {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomerAndSupplierService customerAndSupplierService;

    public List<Category> fetchAllCategories(int first, String authToken, HttpServletRequest request) {
        // Initialize pagination variables
        DataResult<List<Category>> categoryListQueryResult;
        List<Category> categoryList;
        String lastCursor = null;
        List<Category> allCategories = new ArrayList<>();

        // Fetch the initial page of categories
        categoryListQueryResult = this.categoryService.categoryListQuery(first, authToken, "", "next", request);
        categoryList = categoryListQueryResult.getData();
        allCategories.addAll(categoryList);

        // Paginate through the results
        while (true) {
            // Break the loop if no more categories are found
            if (categoryList.isEmpty()) {
                break;
            }

            // Get the cursor of the last category in the current list
            Category lastCategory = categoryList.get(categoryList.size() - 1);
            lastCursor = lastCategory.getCursor();

            // Fetch the next page of categories using the last category's cursor
            categoryListQueryResult = this.categoryService.categoryListQuery(first, authToken, lastCursor, "next", request);
            categoryList = categoryListQueryResult != null ? categoryListQueryResult.getData() : null;

            // Check if categoryList is null or empty
            if (categoryList == null || categoryList.isEmpty()) {
                break;
            }

            // Add the new categories to the main list
            allCategories.addAll(categoryList);
        }

        // Print the cursor of the last category fetched
        System.out.println("Cursor of the last category: " + lastCursor);

        // Return the list of all fetched categories
        return allCategories;
    }

    public List<ProductType> fetchAllProductTypes(int first, String authToken, HttpServletRequest request) {
        // Initialize pagination variables
        DataResult<List<ProductType>> productTypeListQueryResult;
        List<ProductType> productTypeList;
        String lastCursor = null;
        List<ProductType> allProductTypes = new ArrayList<>();

        // Fetch the initial page of product types
        productTypeListQueryResult = this.productTypeService.productTypeListQuery(first, authToken, "", "next", request);
        productTypeList = productTypeListQueryResult.getData();
        allProductTypes.addAll(productTypeList);

        // Paginate through the results
        while (true) {
            // Break the loop if no more product types are found
            if (productTypeList.isEmpty()) {
                break;
            }

            // Get the cursor of the last product type in the current list
            ProductType lastProductType = productTypeList.get(productTypeList.size() - 1);
            lastCursor = lastProductType.getCursor();

            // Fetch the next page of product types using the last product type's cursor
            productTypeListQueryResult = this.productTypeService.productTypeListQuery(first, authToken, lastCursor, "next", request);
            productTypeList = productTypeListQueryResult != null ? productTypeListQueryResult.getData() : null;

            // Check if productTypeList is null or empty
            if (productTypeList == null || productTypeList.isEmpty()) {
                break;
            }

            // Add the new product types to the main list
            allProductTypes.addAll(productTypeList);
        }

        // Print the cursor of the last product type fetched
        System.out.println("Cursor of the last product type: " + lastCursor);

        // Return the list of all fetched product types
        return allProductTypes;
    }

    public List<ProductDto> fetchAllProducts(int first, String authToken, String brand, String productType, HttpServletRequest request) {
        // Initialize pagination variables
        DataResult<List<ProductDto>> productListQueryResult;
        List<ProductDto> productList;
        String lastCursor = null;
        List<ProductDto> allProducts = new ArrayList<>();

        // Fetch the initial page of products
        productListQueryResult = this.productService.productList(authToken, first, "", "next", brand, productType, "", request);
        productList = productListQueryResult.getData();
        allProducts.addAll(productList);

        // Paginate through the results
        while (true) {
            // Break the loop if no more products are found
            if (productList.isEmpty()) {
                break;
            }

            // Get the cursor of the last product in the current list
            ProductDto lastProduct = productList.get(productList.size() - 1);
            lastCursor = lastProduct.getCursor();

            // Fetch the next page of products using the last product's cursor
            productListQueryResult = this.productService.productList(authToken, first, lastCursor, "next", brand, productType, "", request);
            productList = productListQueryResult != null ? productListQueryResult.getData() : null;

            // Check if productList is null or empty
            if (productList == null || productList.isEmpty()) {
                break;
            }

            // Add the new products to the main list
            allProducts.addAll(productList);
        }

        // Print the cursor of the last product fetched
        System.out.println("Cursor of the last product: " + lastCursor);

        // Return the list of all fetched products
        return allProducts;
    }

    public List<StaffDto> fetchAllStaffs(int pageSize, String authToken, HttpServletRequest request) {
        // Initialize pagination variables
        DataResult<List<StaffDto>> staffListResult;
        List<StaffDto> currentStaffPage;
        String lastCursor = null;
        List<StaffDto> allStaff = new ArrayList<>();

        // Fetch the initial page of staff members
        staffListResult = this.staffService.staffList(pageSize, authToken, "", "next", request);
        currentStaffPage = staffListResult.getData();
        allStaff.addAll(currentStaffPage);

        // Paginate through the results
        while (true) {
            // Break the loop if no more staff members are found
            if (currentStaffPage.isEmpty()) {
                break;
            }

            // Get the cursor of the last staff member in the current list
            StaffDto lastStaffMember = currentStaffPage.get(currentStaffPage.size() - 1);
            lastCursor = lastStaffMember.getCursor();

            // Fetch the next page of staff members using the last staff member's cursor
            staffListResult = this.staffService.staffList(pageSize, authToken, lastCursor, "next", request);
            currentStaffPage = staffListResult != null ? staffListResult.getData() : null;

            // Check if currentStaffPage is null or empty
            if (currentStaffPage == null || currentStaffPage.isEmpty()) {
                break;
            }

            // Add the new staff members to the main list
            allStaff.addAll(currentStaffPage);
        }

        // Print the cursor of the last staff member fetched
        System.out.println("Cursor of the last staff member: " + lastCursor);

        // Return the list of all fetched staff members
        return allStaff;
    }

    public List<CustomerAndSupplierDto> fetchAllCustomers(String authToken, int pageSize, String mobileNo, String userCode, String companyName, HttpServletRequest request) {
        // Initialize pagination variables
        DataResult<List<CustomerAndSupplierDto>> customerListResult;
        List<CustomerAndSupplierDto> currentCustomerPage;
        String lastCursor = null;
        List<CustomerAndSupplierDto> allCustomers = new ArrayList<>();

        // Fetch the initial page of customers
        customerListResult = this.customerAndSupplierService.customerList(authToken, pageSize, "", "next", mobileNo, userCode, companyName, request);
        currentCustomerPage = customerListResult.getData();
        allCustomers.addAll(currentCustomerPage);

        // Paginate through the results
        while (true) {
            // Break the loop if no more customers are found
            if (currentCustomerPage.isEmpty()) {
                break;
            }

            // Get the cursor of the last customer in the current list
            CustomerAndSupplierDto lastCustomer = currentCustomerPage.get(currentCustomerPage.size() - 1);
            lastCursor = lastCustomer.getCursor();

            // Fetch the next page of customers using the last customer's cursor
            customerListResult = this.customerAndSupplierService.customerList(authToken, pageSize, lastCursor, "next",
                    mobileNo, userCode, companyName, request);
            currentCustomerPage = customerListResult != null ? customerListResult.getData() : null;

            // Check if currentCustomerPage is null or empty
            if (currentCustomerPage == null || currentCustomerPage.isEmpty()) {
                break;
            }

            // Add the new customers to the main list
            allCustomers.addAll(currentCustomerPage);
        }

        // Print the cursor of the last customer fetched
        System.out.println("Cursor of the last customer: " + lastCursor);

        // Return the list of all fetched customers
        return allCustomers;
    }

    public List<CustomerAndSupplierDto> fetchAllSuppliers(String authToken, int pageSize,
            String mobileNo, String userCode, String companyName, HttpServletRequest request) {
        // Initialize pagination variables
        DataResult<List<CustomerAndSupplierDto>> supplierListResult;
        List<CustomerAndSupplierDto> currentSupplierPage;
        String lastCursor = null;
        List<CustomerAndSupplierDto> allSuppliers = new ArrayList<>();

        // Fetch the initial page of suppliers
        supplierListResult = this.customerAndSupplierService.supplierList(authToken, pageSize, "", "next", mobileNo,
                userCode, companyName, request);
        currentSupplierPage = supplierListResult.getData();
        allSuppliers.addAll(currentSupplierPage);

        // Paginate through the results
        while (true) {
            // Break the loop if no more suppliers are found
            if (currentSupplierPage.isEmpty()) {
                break;
            }

            // Get the cursor of the last supplier in the current list
            CustomerAndSupplierDto lastSupplier = currentSupplierPage.get(currentSupplierPage.size() - 1);
            lastCursor = lastSupplier.getCursor();

            // Fetch the next page of suppliers using the last supplier's cursor
            supplierListResult = this.customerAndSupplierService.supplierList(authToken, pageSize, lastCursor,
                    "next", mobileNo, userCode, companyName, request);
            currentSupplierPage = supplierListResult != null ? supplierListResult.getData() : null;

            // Check if currentSupplierPage is null or empty
            if (currentSupplierPage == null || currentSupplierPage.isEmpty()) {
                break;
            }

            // Add the new suppliers to the main list
            allSuppliers.addAll(currentSupplierPage);
        }

        // Print the cursor of the last supplier fetched
        System.out.println("Cursor of the last supplier: " + lastCursor);

        // Return the list of all fetched suppliers
        return allSuppliers;
    }

}
