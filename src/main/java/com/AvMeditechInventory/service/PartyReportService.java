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
public interface PartyReportService {

    public DataResult<List<InventoryTransactionReport>> getPartyReportByDateRangeAndReportName(Date startDate, 
            Date endate, String reportName, String companyName, int channelId);

    public DataResult<List<Response>> reterivePartyReportById(Integer id, String userType);

}
