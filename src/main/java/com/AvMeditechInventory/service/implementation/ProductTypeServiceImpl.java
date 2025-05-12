/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.ProductTypeService;
import com.AvMeditechInventory.util.CommonUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Override
    public Result productTypeCreateMutation(String productTypeName, String authToken, HttpServletRequest request) {
        try {
            DataResult<?> productTypeCreateMutation = CommonUtil.productTypeCreateMutation(productTypeName, authToken, request);
            if (productTypeCreateMutation.isSuccess()) {
                return new SuccessResult("Product type created successfully");
            } else {
                // Error occurred during product type creation, return error message
                return new ErrorResult("Failed to create product type: " + productTypeCreateMutation.getMessage());
            }
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorResult("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public Result productTypeDeleteMutation(String productTypeId, String authToken, HttpServletRequest request) {
        try {

            DataResult<?> productTypeDeleteMutation = CommonUtil.productTypeDeleteMutation(productTypeId, authToken, request);
            if (productTypeDeleteMutation.isSuccess()) {
                return new SuccessResult("Product type deleted successfully");
            } else {
                // Error occurred during product type deletion, return error message
                return new ErrorResult("Failed to delete product type: " + productTypeDeleteMutation.getMessage());
            }
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorResult("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<ProductType>> productTypeListQuery(Integer first, String authToken, String after, String isAsc, HttpServletRequest request) {
        try {
            DataResult<List<ProductType>> productTypeListQuery = CommonUtil.productTypeListQuery(first, authToken, after, isAsc, request);

            if (!productTypeListQuery.isSuccess()) {
                return new ErrorDataResult<>("Failed to fetch product types: " + productTypeListQuery.getMessage());
            }

            List<ProductType> productTypes = productTypeListQuery.getData();
            return new SuccessDataResult<>(productTypes, "Product types fetched successfully");

        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to fetch product types: An unexpected error occurred - " + e.getMessage());
        }
    }

    @Override
    public Result productTypeUpdateMutation(String productTypeId, String productTypeName, String authToken, HttpServletRequest request) {
        try {

            DataResult<?> productTypeUpdateMutation = CommonUtil.productTypeUpdateMutation(productTypeId, productTypeName, authToken, request);
            if (productTypeUpdateMutation.isSuccess()) {
                return new SuccessResult("Product type updated successfully");
            } else {
                // Handle the case where the mutation encountered errors
                String errorMessage = productTypeUpdateMutation.getMessage(); // Get the error message
                return new ErrorResult("Failed to update product type: " + errorMessage);
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions
            return new ErrorResult("An unexpected error occurred while updating the product type");
        }
    }

}
