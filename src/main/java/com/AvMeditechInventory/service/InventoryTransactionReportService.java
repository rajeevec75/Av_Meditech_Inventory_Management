/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.results.DataResult;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface InventoryTransactionReportService {

    public DataResult<List<InventoryTransactionReport>> getInventoryTransactionReportByDateRangeAndTransactionName(
            Date startDate, Date endDate, String transactionName, String productName, String productTypeName, 
            String categoryName, int channelId);

    public DataResult<List<Response>> getInventoryTransactionReport(Integer purchaseId, String transactionName);
}
