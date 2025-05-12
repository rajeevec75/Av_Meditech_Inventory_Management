/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.service.StockReportService;
import com.AvMeditechInventory.util.ServiceDao;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class StockReportServiceImpl implements StockReportService {
    
    @Autowired
    private ServiceDao serviceDao;
    
    @Override
    public DataResult<List<Response>> getStockReportProductWise(int channelId, Integer pageNumber, Integer pageSize, String productName, String productType, String brand) {
        try {
            DataResult<List<Map<String, Object>>> stockReportProductWise = this.serviceDao.getStockReportProductWise(channelId, pageNumber, pageSize, productName, productType, brand);
            if (stockReportProductWise.isSuccess()) {
                List<Map<String, Object>> stockReportData = stockReportProductWise.getData();
                List<Response> responses = new ArrayList<>();

                // Iterate over the list of map objects
                for (Map<String, Object> stockData : stockReportData) {
                    // Extract relevant data from the map and create a Response object
                    String productName1 = (String) stockData.get("productName");
                    String productDescription = (String) stockData.get("description");
                    String productTypeName = (String) stockData.get("productType");
                    String categoryName = (String) stockData.get("categoryName");
                    String sku = (String) stockData.get("sku");
                    
                    int purchaseQuantity = ((Long) stockData.get("quantity")).intValue();

                    // Create a Response object and set its properties
                    Response response = new Response();
                    response.setProductName(productName1);
                    response.setItemDescription(productDescription);
                    response.setProductTypeName(productTypeName);
                    response.setCategoryName(categoryName);
                    response.setQuantity(purchaseQuantity);
                    response.setSku(sku);

                    // Add the response to the list
                    responses.add(response);
                }
                return new SuccessDataResult<>(responses, "Stock report retrieved successfully");
            } else {
                // Handle the case when no data is found
                return new ErrorDataResult<>(null, stockReportProductWise.getMessage());
            }
        } catch (Exception e) {
            // Handle any exceptions that occur
            return new ErrorDataResult<>(null, "Failed to retrieve stock report: " + e.getMessage());
        }
    }
    
    @Override
    public DataResult<List<Response>> getStockReportSerialNumberWise(int channelId, Integer pageNumber, Integer pageSize, String brand, String productType,
            java.util.Date startDate, java.util.Date endDate, String productName, String itemBarCode, Integer purchaseItemSerialMasterId) {
        try {
            // Get the stock report serial number wise
            DataResult<List<Map<String, Object>>> stockReportSerialNumberWiseResult
                    = this.serviceDao.getStockReportSerialNumberWise(channelId, pageNumber, pageSize, brand, productType, startDate, endDate, productName, itemBarCode, purchaseItemSerialMasterId);
            
            if (stockReportSerialNumberWiseResult.isSuccess()) {
                // Extract the data from the DataResult
                List<Map<String, Object>> stockReportData = stockReportSerialNumberWiseResult.getData();

                // Process each item in the list
                List<Response> responses = new ArrayList<>();
                // Iterate over the list of map objects
                for (Map<String, Object> stockData : stockReportData) {
                    // Extract relevant data from the map and create a Response object
                    String productDescription = (String) stockData.get("description");
                    String productType1 = (String) stockData.get("productType");
                    String categoryName = (String) stockData.get("categoryName");
                    Timestamp createdAtTimestamp = (Timestamp) stockData.get("createdAt");
                    OffsetDateTime createdAt = null;
                    String formattedCreatedAt = null;
                    
                    if (createdAtTimestamp != null) {
                        createdAt = createdAtTimestamp.toInstant().atOffset(ZoneOffset.UTC);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        formattedCreatedAt = createdAt.format(formatter);
                    } else {
                        formattedCreatedAt = "N/A";
                    }

                    // Handle null values for other fields
                    Integer quantity = (Integer) stockData.get("quantity");
                    String sku = (String) stockData.get("sku");
                    String itemBarcode = (String) stockData.get("item_barcode");
                    String serialNumber = (String) stockData.get("item_serial_number");
                    Date expiryDateObj = (Date) stockData.get("item_serial_expiry_date");
                    String expiryDate = expiryDateObj != null ? new SimpleDateFormat("yyyy-MM-dd").format(expiryDateObj) : "N/A";
                    
                    String productNameFromDatabase = (String) stockData.get("product_name");
                    // Create a Response object and set its fields
                    Response response = new Response();
                    response.setProductName(productNameFromDatabase);
                    response.setPurchaseId((Integer) stockData.get("purchase_id"));
                    response.setPurchaseMasterItemsId((Integer) stockData.get("purchase_master_items_id"));
                    response.setPurchaseItemSerialMasterId((Integer) stockData.get("purchase_item_serial_master_id"));
                    response.setItemDescription(productDescription);
                    response.setCategoryName(categoryName);
                    response.setProductTypeName(productType1);
                    response.setDate(formattedCreatedAt);
                    response.setQuantity(quantity); // Set to 1 as requested
                    response.setSku(sku);
                    response.setItemBarCode(itemBarcode);
                    if (null != serialNumber) {
                        response.setSerialNumber(serialNumber);
                    } else {
                        response.setSerialNumber((String) stockData.get("item_batch"));
                    }
                    response.setExpiryDate(expiryDate);

                    // Add the Response object to the list
                    responses.add(response);
                }
                return new SuccessDataResult<>(responses, "Stock report retrieved successfully");
            } else {
                // If retrieving stock report failed, return an error message
                return new ErrorDataResult<>("Failed to retrieve stock report: " + stockReportSerialNumberWiseResult.getMessage());
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions here
            return new ErrorDataResult<>("Failed to retrieve stock report: An error occurred while processing your request.");
        }
    }
    
}
