/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.PurchaseImportResponse;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.Sale;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface StockService {

    public DataResult<List<Response>> customerNameList(List<String> userTypes);

    public DataResult<Response> getBarcodeFromItemSerialNumber(String itemBarCode, String customerId, int channelId);
    
    public DataResult<String> checkSaleReturn(String[] addedRowsData);

    public DataResult<Integer> stockTransfer(Sale sale, String[] addedRowsData, String formattedDate, String[] purchaseSerialData, HttpSession session);
    
    public DataResult<Integer> updateStockCreate(Sale sale, String[] addedRowsData, String[] purchaseSerialData, 
            Date saleDate, int returnUserId, int channelId);

    public PurchaseImportResponse importSaleProduct(MultipartFile file1, int channelId);

    public DataResult<List<Response>> getSaleReturnList(int channelId, Date startDate, Date endDate, String customerName, String productName, Integer pageNumber, Integer pageSize);

    public DataResult<Response> getStockDetailsById(Integer saleId, int channelId);
    
    public Result deleteStockDetailsById(Integer purchaseId, int channelId);

}
