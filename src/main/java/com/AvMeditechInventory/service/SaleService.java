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
public interface SaleService {

    public DataResult<List<Response>> customerNameList(List<String> userTypes);

    public DataResult<Response> getBarcodeFromItemSerialNumber(String itemBarCode, int channelId);

    public DataResult<String> checkSaleCreate(String[] addedRowsData);

    public DataResult<Integer> saleCreate(Sale sale, String[] addedRowsData, String formattedDate, String[] purchaseSerialData, HttpSession session);

    public DataResult<Integer> updateSaleCreate(Sale sale, String[] addedRowsData, String[] purchaseSerialData, Date saleDate, int saleUserId, int channelId);

    public PurchaseImportResponse importSaleProduct(MultipartFile file1, int channelId);

    public DataResult<List<Response>> saleList(int channelId, Integer pageNumber, Integer pageSize, Date startDate, 
            Date endDate, String customerName, String productName, String saleType, int channelId1);

    public DataResult<Response> getSaleDetailsById(Integer saleId, int channelId);

    public Result deleteSaleDetailsById(Integer id, int channelId);

    public DataResult<List<Response>> getCustomerNameListByCustomerName(List<String> userTypes, String customerName);

    public DataResult<List<Response>> getSaleItemDetails(Integer saleId, int channelId);

}
