/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import java.util.List;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface ChallanService {

    public DataResult<Response> getUserDetailBySaleId(Integer saleId);

    public DataResult<List<Response>> getDeliveryChallanBySaleId(Integer saleId);

    public DataResult<Response> getUserDetailByPurchaseId(Integer purchaseId);

    public DataResult<List<Response>> getDeliveryChallanByPurchaseId(Integer purchaseId);

    public DataResult<Response> getUserDetailBySaleReturnId(Integer saleReturnId);

    public DataResult<List<Response>> getDeliveryChallanBySaleReturnId(Integer saleReturnId);

    // print challan one 
    public DataResult<List<Response>> getDeliveryChallanQuantityBySaleId(Integer saleId);

    public DataResult<List<Response>> getDeliveryChallanQuantityBySaleReturnId(Integer saleReturnId);

    public DataResult<List<Response>> getDeliveryChallanQuantityByPurchaseId(Integer purchaseId);

}
