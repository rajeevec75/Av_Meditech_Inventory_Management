/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface CustomerAndSupplierService {

    public DataResult<String> addCustomerAndSupplier(CustomerAndSupplierDto customerAndSupplierDto, String authToken, HttpServletRequest request);

    public DataResult<String> updateCustomerAndSupplier(CustomerAndSupplierDto customerAndSupplierDto, String authToken, HttpServletRequest request);

    public DataResult<List<CustomerAndSupplierDto>> customerList(String authToken, Integer pageNumber, String after, 
            String isAsc, String mobileNo, String userCode, String companyName, HttpServletRequest request);

    public DataResult<CustomerAndSupplierDto> getCustomerById(String customerId, String authToken, HttpServletRequest request);

    public Result customerDelete(String customerId, String authToken, HttpServletRequest request);

    public DataResult<List<CustomerAndSupplierDto>> supplierList(String authToken, Integer pageNumber, String after, 
            String isAsc, String mobileNo, String userCode, String companyName, HttpServletRequest request);

}
