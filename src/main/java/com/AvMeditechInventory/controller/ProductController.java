/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.entities.ProductVariant;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.ProductService;
import com.AvMeditechInventory.util.PaginationUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
 * @author Rajeev kumar (QMM Technologies Private Limited)
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PaginationUtil paginationUtil;

    @GetMapping("/productCreate")
    public String productCreatePage(
            @RequestParam(value = "first", required = false, defaultValue = "100") Integer first,
            @RequestParam(value = "source", required = false, defaultValue = "web") String source,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            RedirectAttributes redirectAttributes, HttpSession session, Model model, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            // Retrieve categories
            List<Category> fetchAllCategories = this.paginationUtil.fetchAllCategories(first, authToken, request);
            model.addAttribute("categoryList", fetchAllCategories);

            // Retrieve product types
            List<ProductType> fetchAllProductTypes = this.paginationUtil.fetchAllProductTypes(first, authToken, request);
            model.addAttribute("productTypeList", fetchAllProductTypes);

            // Add product categories and product types to the model
            model.addAttribute("source", source);
            model.addAttribute("name", name);
            return "productCreate";

        } catch (Exception e) {
            // Handle any other unexpected exceptions
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while loading the product creation page: ");
            return "productCreate";
        }
    }

    @PostMapping("/processProductCreate")
    public String processProductCreate(
            @ModelAttribute("productDto") ProductDto productDto,
            @RequestParam("productType") String productTypeId,
            @RequestParam("category") String categoryId,
            @RequestParam("sku") String sku,
            @RequestParam(value = "key", required = false, defaultValue = "") String[] keys,
            @RequestParam(value = "value", required = false, defaultValue = "") Object[] values,
            @RequestParam(value = "hiddenInputContainer", required = false) String[] hiddenInputContainer,
            RedirectAttributes redirectAttributes, Model model, HttpSession session, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            Map<String, Object> metadata = new HashMap<>();
            // Populate the metadata map with key-value pairs
            if (keys != null && values != null && keys.length == values.length) {
                for (int i = 0; i < keys.length; i++) {
                    metadata.put(keys[i], values[i]);
                }
            } else {
                // Handle the case where lengths are unequal or arrays are null
                redirectAttributes.addFlashAttribute("errorMessage", "Attribute keys and values array length mismatch or null values provided. Please try again.");
                return "redirect:/productCreate";
            }

            // Set the productType in the ProductDto object
            productDto.setProductType(productTypeId, "");
            productDto.setCategory(categoryId, "");
            redirectAttributes.addFlashAttribute("source", productDto.getSource());

            // Call the service method to create the product
            Result addProduct = this.productService.productCreate(productDto, sku, metadata, 5, 
                    productDto.getSnoPattern(), hiddenInputContainer, authToken, request);
            if (addProduct.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", addProduct.getMessage());
                return "redirect:/productList";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", addProduct.getMessage());
                return "redirect:/productCreate";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing the request.");
            return "redirect:/productCreate";
        }
    }

    @GetMapping("/productList")
    public String productList(
            @RequestParam(value = "pageNumber", defaultValue = "50") Integer pageNumber,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "isAsc", defaultValue = "next") String isAsc,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "productType", required = false) String productType,
            @RequestParam(value = "productName", required = false, defaultValue = "") String productName,
            RedirectAttributes redirectAttributes, HttpSession session, HttpSession httpSession, Model model, 
            HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            model.addAttribute("selectedBrandId", brand);
            model.addAttribute("selectedProductTypeId", productType);
            model.addAttribute("productName", productName);

            // Retrieve categories
            List<Category> fetchAllCategories = this.paginationUtil.fetchAllCategories(pageNumber, authToken, request);
            model.addAttribute("categoryList", fetchAllCategories);

            // Retrieve product types
            List<ProductType> fetchAllProductTypes = this.paginationUtil.fetchAllProductTypes(pageNumber, authToken, request);
            model.addAttribute("productTypeList", fetchAllProductTypes);
            // Retrieve  product list
            DataResult<List<ProductDto>> productList = this.productService.productList(authToken, pageNumber, after, 
                    isAsc, brand, productType, productName, request);
            List<ProductDto> productDtoList = productList.getData();
            if (productDtoList == null || productDtoList.isEmpty()) {
                model.addAttribute("errorMessage", "No products found for the specified criteria.");
                return "productList";
            }
            model.addAttribute("productList", productDtoList);
            model.addAttribute("firstCursor", productDtoList.get(0).getCursor());
            model.addAttribute("lastCursor", productDtoList.get(productDtoList.size() - 1).getCursor());
            model.addAttribute("currentCursor", after);
            model.addAttribute("hasNextPage", productDtoList.get(0).isHasNextPage());
            model.addAttribute("hasPreviousPage", productDtoList.get(0).isHasPreviousPage());
            

            return "productList";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while fetching the product list: " + e.getMessage());
            return "productList";
        }

    }

    @PostMapping("/importProductFromCsv")
    public String importProductFromCsv(
            @RequestParam("importProductCsvFile") MultipartFile multipartFile,
            @RequestParam(value = "pageNumber", defaultValue = "100") Integer pageNumber,
            Model model, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
        try {

            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Read products from the CSV file
            List<ProductDto> readProductsFromCsv = this.productService.readProductsFromCsv(multipartFile);
            if (readProductsFromCsv.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "The CSV file is empty. Please provide a valid CSV file with product data.");
                return "redirect:/productList";
            }
            DataResult<List<String>> addProductFromCsv = this.productService.addProductFromCsv(readProductsFromCsv, 
                    pageNumber, authToken, request);
            if (addProductFromCsv.isSuccess()) {
                List<String> list = addProductFromCsv.getData();
                if (list.isEmpty()) {
                    redirectAttributes.addFlashAttribute("successMessage", "Products imported successfully from CSV file.");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Following products could not be imported. Please check SKU duplication: " + String.join(", ", list));
                }
                return "redirect:/productList";
            }
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to import product from csv:" + addProductFromCsv.getMessage());
            return "redirect:/productList";

        } catch (Exception e) {
            // Return an error message
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while importing products from the CSV file.");
            return "redirect:/productList";
        }
    }

    @GetMapping("/productUpdate")
    public String productUpdatePage(
            @RequestParam(value = "productId", required = true) String productId,
            @RequestParam(value = "first", defaultValue = "100") Integer first,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "isAsc", defaultValue = "next") String isAsc,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "productType", required = false) String productType,
            HttpSession session, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {

            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Retrieve categories
            List<Category> fetchAllCategories = this.paginationUtil.fetchAllCategories(first, authToken, request);
            model.addAttribute("categoryList", fetchAllCategories);

            // Retrieve product types
            List<ProductType> fetchAllProductTypes = this.paginationUtil.fetchAllProductTypes(first, authToken, request);
            model.addAttribute("productTypeList", fetchAllProductTypes);

            // Get product information
            DataResult<ProductDto> productResult = this.productService.getProductById(productId, authToken, request);

            ProductDto product = productResult.getData();
            List<ProductVariant> productVariantResult = product.getProductVariant();
            for (ProductVariant productVariant : productVariantResult) {
                String productVariantSku = productVariant.getProductVariantSku();
                String productVariantId = productVariant.getProductVariantId();
                model.addAttribute("productVariantId", productVariantId);
                model.addAttribute("sku", productVariantSku);
            }

            Map<String, Object> metadata = product.getMetadata();
            model.addAttribute("metadata", metadata);
            model.addAttribute("metadataSize", metadata.size());

            String productDescription = product.getProductDescription();
            JSONObject jsonObject = new JSONObject(productDescription);
            String version = jsonObject.getString("version");

            // Extract data from the "blocks" array
            JSONArray blocksArray = jsonObject.getJSONArray("blocks");
            JSONObject blockObject = blocksArray.getJSONObject(0);

            // Extract data from the "data" object inside the "blocks" array
            JSONObject dataObject = blockObject.getJSONObject("data");
            String text = dataObject.getString("text");

            model.addAttribute("text", text);
            model.addAttribute("product", product);
            model.addAttribute("after", after);
            model.addAttribute("isAsc", isAsc);
            model.addAttribute("selectedBrandId", brand);
            model.addAttribute("selectedProductTypeId", productType);

            return "productUpdate";

        } catch (JSONException jsonException) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing the JSON data:");
            return "productUpdate";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while loading the product update page: ");
            return "productUpdate";
        }
    }

    @PostMapping("/processProductUpdate")
    public String processProductUpdate(@ModelAttribute("productDto") ProductDto productDto,
            @RequestParam("productType") String productTypeId,
            @RequestParam("category") String categoryId,
            @RequestParam(value = "sku", required = false) String sku,
            @RequestParam(value = "productVarientId", required = false) String productVarientId,
            @RequestParam(value = "key", required = false, defaultValue = "") String[] keys,
            @RequestParam(value = "value", required = false, defaultValue = "") Object[] values,
            @RequestParam(value = "hiddenInputContainer", required = false) String[] hiddenInputContainer,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "isAsc", defaultValue = "next") String isAsc,
            @RequestParam(value = "Urlbrand", required = false) String Urlbrand,
            @RequestParam(value = "UrlproductType", required = false) String UrlproductType,
            RedirectAttributes redirectAttributes, HttpSession session, Model model, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Set the productType in the ProductDto object
            productDto.setProductType(productTypeId, "");
            productDto.setCategory(categoryId, "");

            Map<String, Object> metadata = new HashMap<>();

            // Populate the metadata map with key-value pairs
            if (keys != null && values != null && keys.length == values.length) {
                for (int i = 0; i < keys.length; i++) {
                    metadata.put(keys[i], values[i]);
                }
            } else {
                // Add a proper error message to redirect attributes
                redirectAttributes.addFlashAttribute("errorMessage", "Attribute keys and values array length mismatch or null values provided. Please try again.");
                return "redirect:/productUpdate?productId=" + productDto.getProductId() + "&after=" + after + "&brand=" + Urlbrand + "&productType=" + UrlproductType;
            }

            Result productUpdate = this.productService.productUpdate(productDto, sku, productVarientId, 5, 
                    productDto.getSnoPattern(), metadata, hiddenInputContainer, authToken, request);

            if (productUpdate.isSuccess()) {
                String message = productUpdate.getMessage();
                redirectAttributes.addFlashAttribute("successMessage", message);
                return "redirect:/productList?after=" + after + "&brand=" + Urlbrand + "&productType=" + UrlproductType;
            } else {
                String message = productUpdate.getMessage();
                redirectAttributes.addFlashAttribute("errorMessage", message);
                return "redirect:/productUpdate?productId=" + productDto.getProductId() + "&after=" + after + "&brand=" + Urlbrand + "&productType=" + UrlproductType;
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing the request: ");
            return "redirect:/productUpdate?productId=" + productDto.getProductId() + "&after=" + after + "&brand=" + Urlbrand + "&productType=" + UrlproductType;
        }
    }

    @GetMapping("/productDelete")
    public String productDelete(
            @RequestParam(value = "productId", required = true) String productId,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "isAsc", defaultValue = "next") String isAsc,
            @RequestParam(value = "brand", required = false) String Urlbrand,
            @RequestParam(value = "productType", required = false) String UrlproductType,
            HttpSession session, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            Result productDelete = this.productService.deleteProductByProductId(productId, authToken, request);
            if (productDelete.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", productDelete.getMessage());
                return "redirect:/productList?after=" + after + "&brand=" + Urlbrand + "&productType=" + UrlproductType;
            }
            redirectAttributes.addFlashAttribute("errorMessage", productDelete.getMessage());
            return "redirect:/productList?after=" + after + "&brand=" + Urlbrand + "&productType=" + UrlproductType;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return "redirect:/productList?after=" + after + "&brand=" + Urlbrand + "&productType=" + UrlproductType;
        }
    }

    @GetMapping("/productListExportToExcel")
    public void productListExportToExcel(
            @RequestParam(value = "pageNumber", defaultValue = "50") Integer pageNumber,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "isAsc", defaultValue = "next") String isAsc,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "productType", required = false) String productType,
            HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {

        // Check authentication token
        String authToken = (String) session.getAttribute("token");
        if (authToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
            response.sendRedirect("/");
            return;
        }

        try {
            // Fetch all products
            List<ProductDto> fetchAllProducts = this.paginationUtil.fetchAllProducts(pageNumber, authToken, brand, productType, request);

            if (fetchAllProducts == null || fetchAllProducts.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "No products found for the given criteria.");
                response.sendRedirect("/productList");
                return;
            }

            // Prepare timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

            // Prepare filename with timestamp
            String filename = "product_list_" + timestamp + ".xlsx";

            // Set response headers
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            // Create Excel workbook
            try ( Workbook workbook = new XSSFWorkbook()) {
                // Create sheet with timestamp in the name
                String sheetName = "Product List " + timestamp;
                Sheet sheet = workbook.createSheet(sheetName);

                // Define headers
                List<String> headers = List.of("Product Name", "Product Type", "Brand", "Sku");

                // Create header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers.get(i));
                }

                // Populate data rows
                int rowNum = 1;
                for (ProductDto product : fetchAllProducts) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(product.getProductName());
                    row.createCell(1).setCellValue(product.getCategory().getCategoryName());
                    row.createCell(2).setCellValue(product.getProductType().getProductTypeName());
                    row.createCell(3).setCellValue(product.getProductSku());
                }

                // Write workbook to response output stream
                workbook.write(response.getOutputStream());
            }
        } catch (IOException e) {
            // Handle I/O errors
            redirectAttributes.addFlashAttribute("errorMessage", "There was an error generating the Excel file. Please try again.");
            response.sendRedirect("/productList");
        } catch (Exception e) {
            // Handle unexpected errors
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again.");
            response.sendRedirect("/productList");
        }
    }

    @PostMapping("/deleteProductsByProductIds")
    @ResponseBody
    public ResponseEntity<Result> deleteProductsByProductIds(
            @RequestParam(value = "productIds", required = true) List<String> productIds,
            @RequestParam(value = "after", defaultValue = "", required = false) String after,
            @RequestParam(value = "isAsc", defaultValue = "next", required = false) String isAsc,
            @RequestParam(value = "brand", defaultValue = "", required = false) String Urlbrand,
            @RequestParam(value = "productType", defaultValue = "", required = false) String UrlproductType,
            HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return ResponseEntity.ok(new ErrorDataResult("Unauthorized access"));
            }

            // Attempt to delete the products
            Result productDelete = this.productService.deleteProductsByProductIds(productIds, authToken, request);
            if (productDelete.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", productDelete.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", productDelete.getMessage());
            }

            // Return the result as a response entity with HTTP 200 status
            return ResponseEntity.ok(productDelete);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
            return ResponseEntity.ok(new ErrorDataResult("An unexpected error occurred"));
        }
    }

}
