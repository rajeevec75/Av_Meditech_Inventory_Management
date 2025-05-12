/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.entities.Channels;
import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.ChannelsService;
import com.AvMeditechInventory.service.ProductService;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ChannelsService channelsService;

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public Result productCreate(ProductDto productDto, String sku, Map<String, Object> metadata, Integer gst, 
            String snoPattern, String[] hiddenInputContainer, String authToken, HttpServletRequest request) {
        try {
            // Create the product
            DataResult<?> productCreateMutation = CommonUtil.productCreateMutation(productDto, gst, snoPattern, 
                    authToken, request);
            if (!productCreateMutation.isSuccess()) {
                return new ErrorResult("Failed to create the product: ");
            }
            String productId = (String) productCreateMutation.getData();

            // Update product channel listing
            DataResult<?> channelListingUpdateResult = CommonUtil.productChannelListingUpdateMutation(productDto, 
                    authToken, productId, hiddenInputContainer, request);
            if (!channelListingUpdateResult.isSuccess()) {
                return new ErrorResult("Failed to update product channel listing: ");
            }

            // Create product variant
            DataResult<?> variantCreateResult = CommonUtil.productVariantCreateMutation(sku, productId, authToken, request);
            if (!variantCreateResult.isSuccess()) {
                DataResult<String> productDelete = CommonUtil.productDelete(productId, authToken, request);
                if (productDelete.isSuccess()) {
                    return new ErrorResult("SKU is already in use. Please use a different SKU for the product.");

                }
            }
            String varientId = (String) variantCreateResult.getData();

            // Update product variant channel listing
            DataResult<?> variantChannelListingUpdateResult = CommonUtil.productVariantChannelListingUpdateMutation(
                    productDto.getPrice(), productDto.getCostPrice(), varientId, hiddenInputContainer, authToken, request);
            if (!variantChannelListingUpdateResult.isSuccess()) {
                return new ErrorResult("Failed to update product variant channel listing: ");
            }

            DataResult<?> updateMetadataMutationResult = CommonUtil.updateMetadataMutation(productId, metadata, authToken, request);
            if (!updateMetadataMutationResult.isSuccess()) {
                return new ErrorDataResult<>("Failed to update metadata: ");
            }

            // Return success if all operations were successful
            return new SuccessResult("Product creation successful.");
        } catch (Exception e) {
            return new ErrorResult("An error occurred while creating the product.");
        }
    }

    @Override
    public DataResult<List<ProductDto>> productList(String authToken, Integer pageNumber, String after, String isAsc,
            String brand, String productType, String productName, HttpServletRequest request) {

        try {
            DataResult<List<ProductDto>> productListQuery = CommonUtil.productListQuery(pageNumber, authToken, 
                    after, isAsc, brand, productType, productName, request);
            if (productListQuery.isSuccess()) {
                List<ProductDto> productList = productListQuery.getData();
                // Process productList here if needed
                return new SuccessDataResult<>(productList, "Product list fetched successfully");
            } else {
                // If the query was not successful, return an error message
                return new ErrorDataResult<>("Failed to fetch product list: ");
            }
        } catch (Exception e) {
            // If an unexpected exception occurs, return an error message
            return new ErrorDataResult<>("An error occurred while fetching the product list: ");
        }
    }

    @Override
    public Result productUpdate(ProductDto productDto, String sku, String productVariantId, Integer gst, String snoPattern, 
            Map<String, Object> metadata, String[] hiddenInputContainer, String authToken, HttpServletRequest request) {
        try {
            DataResult<?> productUpdateMutation = CommonUtil.productUpdateMutation(productDto, gst, snoPattern, authToken, request);

            if (!productUpdateMutation.isSuccess()) {
                // If update was not successful, return the error message
                return new ErrorResult("Failed to update product: ");
            }

            DataResult<?> productVariantUpdateMutation = CommonUtil.productVariantUpdateMutation(productVariantId, sku, authToken, request);
            if (!productVariantUpdateMutation.isSuccess()) {
                // If product variant update was not successful, return an error message
                return new ErrorResult("SKU is already in use. Please use a different SKU for the product.");
            }

            DataResult<?> updateMetadataMutation = CommonUtil.updateMetadataMutation(productDto.getProductId(), metadata, authToken, request);
            if (!updateMetadataMutation.isSuccess()) {
                return new ErrorDataResult<>("Failed to update metadata: ");
            }

            DataResult<?> productVariantChannelListingUpdateMutation = CommonUtil.productVariantChannelListingUpdateMutation(
                    productDto.getPrice(), productDto.getCostPrice(), productVariantId, hiddenInputContainer, authToken, request);
            if (!productVariantChannelListingUpdateMutation.isSuccess()) {
                // If the mutation failed, return an error message or result
                return new ErrorResult("Failed to update product variant channel listing.");
            }

            // Return a success result if both product and product variant updates were successful
            return new SuccessResult("Product  updated successfully");

        } catch (Exception e) {
            // If an exception occurs during the update process, return an error message
            return new ErrorResult("An error occurred while updating the product: ");
        }
    }

    @Override
    public DataResult<ProductDto> getProductById(String productId, String authToken, HttpServletRequest request) {
        try {
            DataResult<ProductDto> productById = CommonUtil.getProductById(productId, authToken, request);
            if (productById.isSuccess()) {
                ProductDto product = productById.getData();
                // Product fetched successfully
                return new SuccessDataResult<>(product, "Product fetched successfully");
            } else {
                // Failed to fetch product, return error message
                return new ErrorDataResult<>(productById.getMessage());
            }
        } catch (Exception e) {
            // An unexpected error occurred
            return new ErrorDataResult<>("Failed to fetch product: An unexpected error occurred");
        }
    }

    @Override
    public Result deleteProductByProductId(String productId, String authToken, HttpServletRequest request) {
        try {
            DataResult<String> productDelete = CommonUtil.productDelete(productId, authToken, request);
            if (productDelete.isSuccess()) {
                return new SuccessResult("Product deletion was successful.");
            } else {
                return new ErrorResult("Failed to delete product.");
            }
        } catch (Exception e) {
            return new ErrorResult("An unexpected error occurred while deleting the product.");
        }
    }

    @Override
    public List<ProductDto> readProductsFromCsv(MultipartFile multipartFile) throws Exception {
        List<ProductDto> productDtos = new ArrayList<>();
        try ( Reader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));  CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String productName = csvRecord.get("Product Name").trim();
                if (productName.isEmpty()) {
                    // If productName is empty, skip this record
                    continue;
                }
                System.out.println("Product Name: " + productName); // Print the product name for debugging

                ProductDto productDto = new ProductDto();
                productDto.setProductName(productName);

                try {
                    String categoryName = csvRecord.get("Brand Name");
                    productDto.setCategory(null, categoryName);

                    String productTypeName = csvRecord.get("Product Type Name");
                    productDto.setProductType(null, productTypeName);

                    productDto.setGst("5");
                    productDto.setProductSku(csvRecord.get("Product Code/sku"));
                    productDto.setProductDescription(csvRecord.get("Description"));
                    productDto.setPrice(Float.parseFloat("0"));
                    productDto.setCostPrice(Float.parseFloat("0"));
                    productDto.setSnoPattern(csvRecord.get("SNO Pattern"));
                    productDto.setTrackingSerialNo(Boolean.parseBoolean(csvRecord.get("Tracking Serial no")));
                    productDto.setBatchSerialNo(Boolean.parseBoolean(csvRecord.get("Batch Serial no")));

                    // Set weight, size, and power as metadata
                    Map<String, Object> metadata = new HashMap<>();
                    metadata.put("Power", csvRecord.get("Power"));
                    productDto.setMetadata(metadata);

                    productDtos.add(productDto);
                } catch (NumberFormatException e) {
                    // Log the parsing error
                    System.err.println("Error parsing price or cost price: " + e.getMessage());
                    // Optionally, you can skip the record here
                }
            }
        }
        return productDtos;
    }

    @Override
    public DataResult<List<String>> addProductFromCsv(List<ProductDto> productDtos, Integer pageNumber, 
            String authToken, HttpServletRequest request) {

        List<String> deletedProductNames = new ArrayList<>();

        DataResult<List<Channels>> channelsListQuery = this.channelsService.channelsListQuery(authToken, request);
        if (!channelsListQuery.isSuccess()) {
            return new ErrorDataResult<>("Failed to retrieve channels.");
        }

        List<Channels> channelList = channelsListQuery.getData();
        String[] hiddenInputContainer = new String[channelList.size()]; // Initialize the array with the size of the channelList

        for (int i = 0; i < channelList.size(); i++) {
            Channels channels = channelList.get(i);

            hiddenInputContainer[i] = channels.getChannelId() + "|" + channels.getChannelName() + ",";
        }

        try {
            for (int i = 0; i < productDtos.size(); i++) {
                ProductDto productDto = productDtos.get(i);
                Integer gst;
                try {
                    gst = 5;
                } catch (NumberFormatException e) {
                    return new ErrorDataResult<>("Invalid GST value for product: ");
                }
                DataResult<Category> searchCategoryResult = CommonUtil.searchCategoryByName(pageNumber, authToken, productDto.getCategory().getCategoryName(), request);

                if (!searchCategoryResult.isSuccess()) {
                    return new ErrorDataResult<>("Failed to fetch category.");
                }
                Category category = searchCategoryResult.getData();
                String categoryId = category.getCategoryId();
                String categoryName = category.getCategoryName();
                productDto.setCategory(categoryId, categoryName);

                DataResult<ProductType> searchProductTypeResult = CommonUtil.searchProductTypeByName(pageNumber, authToken, productDto.getProductType().getProductTypeName(), request);

                if (!searchProductTypeResult.isSuccess()) {
                    return new ErrorDataResult<>("Failed to fetch product type: ");
                }
                ProductType productType = searchProductTypeResult.getData();
                String productTypeId = productType.getProductTypeId();
                String productTypeName = productType.getProductTypeName();
                productDto.setProductType(productTypeId, productTypeName);

                // Create the product
                DataResult<?> productCreateMutation = CommonUtil.productCreateMutation(productDto, gst, productDto.getSnoPattern(), authToken, request);
                if (!productCreateMutation.isSuccess()) {
                    return new ErrorDataResult<>("Failed to create the product: ");
                }

                String productId = (String) productCreateMutation.getData();

                // Update product channel listing
                DataResult<?> channelListingUpdateResult = CommonUtil.productChannelListingUpdateMutation(productDto, 
                        authToken, productId, hiddenInputContainer, request);
                if (!channelListingUpdateResult.isSuccess()) {
                    return new ErrorDataResult<>("Failed to update product channel listing: ");
                }
                // Create product variant
                DataResult<?> variantCreateResult = CommonUtil.productVariantCreateMutation(productDto.getProductSku(), 
                        productId, authToken, request);
                if (!variantCreateResult.isSuccess()) {
                    DataResult<String> productDelete = CommonUtil.productDelete(productId, authToken, request);

                    if (productDelete.isSuccess()) {
                        deletedProductNames.add(productDto.getProductName()); // Add deleted product name to the list
                        continue;
                    }
                }
                String varientId = (String) variantCreateResult.getData();

                // Update product variant channel listing
                double adjustedPrice = 0;
                double adjustedCostPrice = 0;
                DataResult<?> variantChannelListingUpdateResult = CommonUtil.productVariantChannelListingUpdateMutation(
                        adjustedPrice, adjustedCostPrice, varientId, hiddenInputContainer, authToken, request);
                if (!variantChannelListingUpdateResult.isSuccess()) {
                    return new ErrorDataResult<>("Failed to update product variant channel listing: ");
                }

                DataResult<?> updateMetadataMutationResult = CommonUtil.updateMetadataMutation(productId, productDto.getMetadata(), authToken, request);
                if (!updateMetadataMutationResult.isSuccess()) {
                    return new ErrorDataResult<>("Failed to update metadata: " + updateMetadataMutationResult.getMessage());
                }
            }
            return new SuccessDataResult<>(deletedProductNames, "All products have been successfully added.");
        } catch (Exception e) {
            // Log the exception (optional, based on your logging framework)
            return new ErrorDataResult<>("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Response>> getServiceProductList(Date startDate, Date endDate, String productName, String customerName, String serialNumber, Integer pageNumber, Integer pageSize) {
        try {
            DataResult<List<Map<String, Object>>> serviceProductListResult = this.serviceDao.getServiceProductList(startDate, endDate, customerName, productName, serialNumber, pageNumber, pageSize);

            if (!serviceProductListResult.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve service product list.");
            }

            List<Map<String, Object>> dataList = serviceProductListResult.getData();
            List<Response> responseList = new ArrayList<>();

            for (Map<String, Object> data : dataList) {
                Response response = new Response();

                // Handle null values using helper methods
                response.setPurchaseItemSerialMasterId(data.get("purchase_item_serial_master_id") != null ? (Integer) data.get("purchase_item_serial_master_id") : null);
                response.setSaleId(data.get("sale_id") != null ? (Integer) data.get("sale_id") : null);
                response.setSaleDateStr(getDateValue(data, "sale_date"));
                // Check if the item is part of a batch
                boolean isBatch = (boolean) data.get("is_batch");
                if (isBatch) {
                    response.setSerialNumber((String) data.get("item_batch"));
                } else {
                    response.setSerialNumber((String) data.get("item_serial_number"));
                }
                response.setItemSerialExpiry(data.get("item_serial_expiry_date") != null ? (Date) data.get("item_serial_expiry_date") : null);
                response.setWarrantyValidDateStr(getDateValue(data, "warranty_valid_date"));
                response.setAmcValidDateStr(getDateValue(data, "amc_valid_date"));
                response.setCompanyName(data.get("company_name") != null ? (String) data.get("company_name") : null);
                response.setProductName(data.get("name") != null ? (String) data.get("name") : null);

                responseList.add(response);
            }

            return new SuccessDataResult<>(responseList, "Successfully retrieved service product list.");
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("Failed to retrieve service product list due to data access error: " + dataAccessException.getMessage());
        } catch (Exception exception) {
            return new ErrorDataResult<>("Failed to retrieve service product list due to an unexpected error: " + exception.getMessage());
        }
    }

    // Helper method to safely retrieve a date value and convert to string or handle null
    private String getDateValue(Map<String, Object> data, String key) {
        Date date = data.get(key) != null ? (Date) data.get(key) : null;
        return date != null ? date.toString() : "No Date";
    }

    @Override
    public DataResult<List<Response>> getServiceDetailsBySaleId(Integer saleId) {
        try {
            // Call the DAO method to retrieve the product service details
            DataResult<List<Map<String, Object>>> productServiceDetailsResult = this.serviceDao.getServiceDetailsBySaleId(saleId);

            // Check if the DAO result is successful
            if (!productServiceDetailsResult.isSuccess()) {
                return new ErrorDataResult<>("No product service details found.");
            }

            // Get the data from the DataResult
            List<Map<String, Object>> productServiceDetails = productServiceDetailsResult.getData();

            // Null check for the retrieved data
            if (productServiceDetails == null || productServiceDetails.isEmpty()) {
                return new ErrorDataResult<>("No product service details found.");
            }

            // Create a list to hold the response objects
            List<Response> responses = new ArrayList<>();

            // Iterate over the list of maps and create a list of Response objects
            for (Map<String, Object> detail : productServiceDetails) {
                Response response = new Response();
                response.setSaleServiceId((Integer) detail.get("sale_service_id"));
                response.setServiceDate((Date) detail.get("service_date"));
                response.setRemarks((String) detail.get("remarks"));
                response.setSolution((String) detail.get("solution"));
                response.setAttachment((String) detail.get("attachment"));
                response.setSaleId((Integer) detail.get("sale_id"));

                responses.add(response);
            }

            // Return a successful DataResult with the list of responses
            return new SuccessDataResult<>(responses, "Successfully retrieved product service details.");

        } catch (Exception e) {
            // Handle any exceptions and return an error result
            return new ErrorDataResult<>("An error occurred while retrieving product service details: " + e.getMessage());
        }
    }

    @Override
    public Resource retrieveImage(Integer saleServiceId, HttpServletRequest request) {
        try {
            // Get the image name from the DAO using saleServiceId
            String imageName = this.serviceDao.getImageNameBySaleServiceId(saleServiceId);

            // Check if the image name is null
            if (imageName == null) {
                throw new RuntimeException("Image not found for saleServiceId: " + saleServiceId);
            }

            // Get the real path of the web application root
            String realPath = request.getSession().getServletContext().getRealPath("/");

            // Construct the image file path
            Path file = Paths.get(realPath, imageName); // Adjust "images" as needed
            Resource resource = new UrlResource(file.toUri());

            // Check if the file exists and is readable
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + imageName);
            }

        } catch (IOException iOException) {
            throw new RuntimeException("Could not read file for saleServiceId: " + saleServiceId, iOException);
        }
    }

    @Override
    public Result deleteProductsByProductIds(List<String> productIds, String authToken, HttpServletRequest request) {
        try {
            // Check if product IDs list is empty
            if (productIds == null || productIds.isEmpty()) {
                return new ErrorResult("No product IDs provided.");
            }

            // Variable to track if any deletion fails
            boolean hasError = false;
            StringBuilder errorMessages = new StringBuilder();

            // Loop through each product ID and attempt deletion
            for (String productId : productIds) {
                System.out.println("productId " + productId);
                Result deleteResult = CommonUtil.productDelete(productId, authToken, request);

                // If deletion fails, record the error
                if (!deleteResult.isSuccess()) {
                    hasError = true;
                    errorMessages.append("Failed to delete product with ID ").append(productId).append(": ").append(deleteResult.getMessage()).append("\n");
                }
            }

            // Check if any deletion failed and return appropriate result
            if (hasError) {
                return new ErrorResult(errorMessages.toString());
            } else {
                return new SuccessResult("All products deleted successfully.");
            }
        } catch (Exception e) {
            // Return an error result if an exception occurs
            return new ErrorResult("An error occurred while deleting products: " + e.getMessage());
        }
    }

}
