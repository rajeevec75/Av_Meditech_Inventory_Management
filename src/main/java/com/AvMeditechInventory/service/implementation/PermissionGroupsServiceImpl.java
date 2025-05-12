/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.entities.PermissionGroups;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.service.PermissionGroupsService;
import com.AvMeditechInventory.util.CommonUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class PermissionGroupsServiceImpl implements PermissionGroupsService {

    @Override
    public DataResult<List<PermissionGroups>> permissionGroupsListQuery(Integer first, String authToken, HttpServletRequest request) {
        try {

            if (authToken == null) {
                return new ErrorDataResult<>("Failed to fetch permission groups: Authentication token is missing");
            }

            DataResult<List<PermissionGroups>> permissionGroupsResult = CommonUtil.permissionGroupsListQuery(first, authToken, request);
            if (permissionGroupsResult.isSuccess()) {
                return new SuccessDataResult<>(permissionGroupsResult.getData(), "Permission groups fetched successfully");
            } else {
                return new ErrorDataResult<>("Failed to fetch permission groups: " + permissionGroupsResult.getMessage());
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to fetch permission groups: An unexpected error occurred");
        }
    }

}
