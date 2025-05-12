/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.PurchaseImportResponse;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.PurchaseMaster;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface PurchaseService {

    public DataResult<List<String>> placeOfSupplyList();

    public DataResult<Integer> processPurchaseProductCreate(PurchaseMaster purchase, String[] rowData, 
            String[] purchaseSerialData, String formattedDate, String authToken, int channelId, int purchaseUserId, 
            HttpServletRequest request, HttpSession httpSession);

    public PurchaseImportResponse importPurchaseProduct(MultipartFile file1, HttpSession httpSession, Integer pageNumber, HttpServletRequest request);

    public DataResult<List<CustomerAndSupplierDto>> getUserDataByUserType();

    public DataResult<List<ProductDto>> getProductList();

    public DataResult<List<Response>> getPurchaseList(Integer channelId, Integer pageNumber, Integer pageSize,
            Date startDate, Date endDate, String companyName, String productName);

    public List<ProductDto> readPurchaseFromCsv(MultipartFile multipartFile) throws Exception;

    public DataResult<Response> get_purchase_details_by_id(Integer purchaseId, int channelId);

    public Result delete_purchase_details_by_id(Integer id, int channelId);
    
    public DataResult<List<ProductDto>> productList(String productName, String authToken, Integer pageNumber, HttpServletRequest request);

    public DataResult<List<ProductDto>> retrieveProductListByProductNameOrSku(String productNameOrSku);

    public DataResult<List<CustomerAndSupplierDto>> getCompanyNameListByUserTypesAndCompanyName(List<String> userTypes, String companyName);

}
