/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.service.CombinedStockReportService;
import com.AvMeditechInventory.util.ServiceDao;
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
public class CombinedReportServiceImpl implements CombinedStockReportService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public DataResult<List<Response>> getStockReportProductWise(Integer pageNumber, Integer pageSize, String productName, String productType, String brand, List<Integer> channelIds) {
        try {
            DataResult<List<Map<String, Object>>> stockReportProductWise = this.serviceDao.getCombinedStockReportProductWise(pageNumber, pageSize, productName, productType, brand, channelIds);
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

}
