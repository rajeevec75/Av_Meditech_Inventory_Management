package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.util.ServiceDao;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAJEEV KUMAR - QMM Technologies Private Limited
 */
@Service
public class BatchWiseReportService {

    @Autowired
    private ServiceDao serviceDao;

    public DataResult<List<Response>> fetchBatchWiseReport(
            int channelId, Integer pageNumber, Integer pageSize, String brand, String productType,
            java.util.Date startDate, java.util.Date endDate, String productName, String batchNumber,
            Integer purchaseItemSerialMasterId) {

        try {
            // Fetch stock report data from the DAO
            DataResult<List<Map<String, Object>>> stockReportResult = serviceDao.getBatchTrueProducts(channelId, pageNumber, pageSize, brand, productType, startDate, endDate, productName, batchNumber, purchaseItemSerialMasterId);

            if (stockReportResult.isSuccess()) {
                List<Map<String, Object>> stockDataList = stockReportResult.getData();
                List<Response> responseList = new ArrayList<>();

                for (Map<String, Object> stockData : stockDataList) {
                    Response response = mapStockDataToResponse(stockData);
                    responseList.add(response);
                }

                return new SuccessDataResult<>(responseList, "Batch wise report retrieved successfully.");
            } else {
                return new ErrorDataResult<>("Unable to retrieve batch wise report: " + stockReportResult.getMessage());
            }

        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while retrieving the batch wise report: " + e.getMessage());
        }
    }

    private Response mapStockDataToResponse(Map<String, Object> stockData) {
        Response response = new Response();

        // Retrieve fields using getOrDefault to handle missing values
        response.setProductName((String) stockData.getOrDefault("product_name", "N/A"));
        response.setPurchaseId((Integer) stockData.getOrDefault("purchase_id", 0));
        response.setPurchaseMasterItemsId((Integer) stockData.getOrDefault("purchase_master_items_id", 0));
        response.setPurchaseItemSerialMasterId((Integer) stockData.getOrDefault("purchase_item_serial_master_id", 0));
        response.setItemDescription((String) stockData.getOrDefault("description", "N/A"));
        response.setCategoryName((String) stockData.getOrDefault("categoryName", "N/A"));
        response.setProductTypeName((String) stockData.getOrDefault("productType", "N/A"));

        // Format and set the creation date
        Timestamp createdAtTimestamp = (Timestamp) stockData.getOrDefault("createdAt", null);
        String formattedDate = createdAtTimestamp != null ? createdAtTimestamp.toInstant().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A";
        response.setDate(formattedDate);

        // Set optional fields with fallback values
        response.setQuantity(((Number) stockData.getOrDefault("quantity", 1L)).intValue());
        response.setSku((String) stockData.getOrDefault("sku", "N/A"));
        response.setBatchNo((String) stockData.getOrDefault("item_batch", "N/A"));

        // Format and set the expiry date
        Date expiryDateObj = (Date) stockData.getOrDefault("item_serial_expiry_date", null);
        response.setExpiryDate(expiryDateObj != null ? new SimpleDateFormat("yyyy-MM-dd").format(expiryDateObj) : "N/A");

        return response;
    }

    public DataResult<Response> fetchBatchProductDetails(String brand, String productType, String batchNumber, String expiryDate, Integer channelId) {
        try {
            // Fetch the product details from the service DAO
            DataResult<List<Map<String, Object>>> fetchResult = serviceDao.fetchBatchProductDetails(brand, productType, batchNumber, expiryDate, channelId);

            // Check if the data retrieval was successful
            if (fetchResult.isSuccess()) {
                Map<String, Object> data = fetchResult.getData().get(0);

                // Map all data to the Response object
                Response response = new Response();
                response.setProductName((String) data.getOrDefault("productName", ""));
                response.setProductTypeName((String) data.getOrDefault("productType", ""));
                response.setCategoryName((String) data.getOrDefault("categoryName", ""));
                response.setQuantity(((Long) data.getOrDefault("quantity", 0L)).intValue()); // Default to 0L (Long)
                response.setSku((String) data.getOrDefault("sku", ""));
                response.setBatchNo((String) data.getOrDefault("item_batch", ""));
                // Format and set the expiry date
                Date expiryDateObj = (Date) data.getOrDefault("item_serial_expiry_date", null);
                response.setExpiryDate(expiryDateObj != null ? new SimpleDateFormat("yyyy-MM-dd").format(expiryDateObj) : "N/A");

                return new SuccessDataResult<>(response, "Product details fetched successfully.");
            } else {
                return new ErrorDataResult<>(null, "Failed to fetch product details: " + fetchResult.getMessage());
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while fetching batch product details: " + e.getMessage());
        }
    }

}
