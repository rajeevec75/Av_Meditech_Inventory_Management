/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.CategoryService;
import com.AvMeditechInventory.util.CommonUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public Result categoryCreateMutation(String categoryName, String authToken, Map<String, Object> metadata, HttpServletRequest request) {
        try {
            // Create category
            DataResult<String> categoryCreateMutation = CommonUtil.categoryCreateMutation(categoryName, authToken, request);

            // Check if the category creation was successful
            if (!categoryCreateMutation.isSuccess()) {
                return new ErrorResult("Failed to create category: ");
            }

            // Get the created category ID
            String categoryId = categoryCreateMutation.getData();

            // Update metadata
            DataResult<?> updateMetadataMutation = CommonUtil.updateMetadataMutation(categoryId, metadata, authToken, request);

            // Check if metadata update was successful
            if (!updateMetadataMutation.isSuccess()) {
                return new ErrorResult("Category created, but failed to update metadata: ");
            }

            // Both operations were successful
            return new SuccessResult("Category created successfully");
        } catch (Exception e) {
            return new ErrorResult("An error occurred while creating the category: ");
        }
    }

    @Override
    public Result categoryDeleteMutation(String categoryId, String authToken, HttpServletRequest request) {
        try {

            DataResult<?> categoryDeleteResult = CommonUtil.categoryDeleteMutation(categoryId, authToken, request);

            if (categoryDeleteResult.isSuccess()) {
                return new SuccessResult("Category deleted successfully");
            } else {
                return new ErrorResult("Failed to delete category: ");
            }
        } catch (Exception e) {
            return new ErrorResult("An error occurred while deleting the category: ");
        }
    }

    @Override
    public DataResult<List<Category>> categoryListQuery(Integer first, String authToken, String after, String isAsc, HttpServletRequest request) {
        try {
            DataResult<List<Category>> categoryListQuery = CommonUtil.categoryListQuery(first, authToken, after, isAsc, request);
            if (!categoryListQuery.isSuccess()) {
                return new ErrorDataResult<>("Failed to fetch categories: The query was not successful");
            }
            List<Category> list = categoryListQuery.getData();
            return new SuccessDataResult<>(list, "Categories fetched successfully");
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to fetch categories: An unexpected error occurred");
        }
    }

    @Override
    public Result categoryUpdateMutation(String categoryId, String categoryName, Map<String, Object> metadata, 
            String authToken, HttpServletRequest request) {
        try {
            // Perform the category update mutation
            DataResult<?> categoryUpdateMutation = CommonUtil.categoryUpdateMutation(categoryId, categoryName, 
                    authToken, request);

            if (!categoryUpdateMutation.isSuccess()) {
                // Return error message if the category update was not successful
                return new ErrorResult("Failed to update category: ");
            }

            // Perform the metadata update mutation
            DataResult<?> metadataUpdateMutation = CommonUtil.updateMetadataMutation(categoryId, metadata, authToken, request);

            if (!metadataUpdateMutation.isSuccess()) {
                // Return error message if the metadata update was not successful
                return new ErrorResult("Failed to update metadata: ");
            }

            // Return success message if both updates were successful
            return new SuccessResult("Category  updated successfully");
        } catch (Exception e) {
            // Return a generic error message if an exception occurred during the update process
            return new ErrorResult("Failed to update category: ");
        }
    }

}
