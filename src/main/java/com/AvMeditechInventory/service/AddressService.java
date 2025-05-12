/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.AddressDto;
import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface AddressService {

    public Result addressCreate(CustomerAndSupplierDto customerAndSupplierDto, String authToken, String id, HttpServletRequest request);

    public DataResult<List<AddressDto>> getAddressesByCustomerId(String customerId, String authToken, Integer pageNumber, HttpServletRequest request);

    public Result addressUpdate(AddressDto addressDto, String authToken, HttpServletRequest request);

    public Result addressDelete(String addressId, String authToken, HttpServletRequest request);
}
