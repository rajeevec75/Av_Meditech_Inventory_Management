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
public interface ItemMovementReportService {

    public DataResult<List<InventoryTransactionReport>> getItemMovementReportByProductNameAndDateRange(
            String productName, Date startDate, Date endDate, int channelId);

    public DataResult<List<Response>> getItemMovementReportById(Integer id, String transactionType);

}
