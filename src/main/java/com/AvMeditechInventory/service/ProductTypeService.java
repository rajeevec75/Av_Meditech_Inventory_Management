/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface ProductTypeService {

    public Result productTypeCreateMutation(String productTypeName, String authToken, HttpServletRequest request);

    public Result productTypeDeleteMutation(String productTypeId, String authToken, HttpServletRequest request);

    public DataResult<List<ProductType>> productTypeListQuery(Integer first, String authToken, String after, String isAsc, HttpServletRequest request);

    public Result productTypeUpdateMutation(String productTypeId, String productTypeName, String authToken, HttpServletRequest request);
}
