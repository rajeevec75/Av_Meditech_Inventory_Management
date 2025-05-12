/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface StockReportService {

    public DataResult<List<Response>> getStockReportProductWise(int channelId, Integer pageNumber, Integer pageSize, String productName, String productType, String brand);

    public DataResult<List<Response>> getStockReportSerialNumberWise(int channelId, Integer pageNumber, 
            Integer pageSize, String brand, String productType, Date startDate, Date endDate, 
            String productName, String itemBarCode, Integer purchaseItemSerialMasterId);

}
