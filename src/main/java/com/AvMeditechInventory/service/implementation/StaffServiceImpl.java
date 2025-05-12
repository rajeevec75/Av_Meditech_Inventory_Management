/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.StaffDto;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.StaffService;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public Result staffCreate(StaffDto staffDto, String stores, String authToken, HttpServletRequest request) {

        try {
            // Create staff mutation
            Result staffCreateMutation = CommonUtil.staffCreateMutation(staffDto.getStaffFirstName(), 
                    staffDto.getStaffLastName(), staffDto.getStaffEmail(), staffDto.getAddGroups(), authToken, request);

            // Update password and mobile number
            Result updatePasswordWithStaff = serviceDao.updatePasswordWithStaff(staffDto.getStaffEmail(), Constant.AV_MEDITECH_INVENTORY_ENC_PASSWORD, staffDto.getStaffMobileNo());
            if (!updatePasswordWithStaff.isSuccess()) {
                return new ErrorResult("Failed to update password and mobile number.");
            }

            DataResult<Map<String, Object>> userDataByEmail = serviceDao.getUserDataByEmail(staffDto.getStaffEmail());
            Map<String, Object> userData = userDataByEmail.getData();
            Integer user_id = (Integer) userData.get("id");
            // Insert user channels
            // Split the string into individual IDs based on comma delimiter
            String[] split = stores.split(",");
            for (String channelId : split) {
                // Convert string ID to integer
                int channel_id = Integer.parseInt(channelId.trim()); // trim() to remove any leading/trailing spaces
                // Pass the integer ID to the insert_user_channels method
                Result insert_user_channels = this.serviceDao.insertUserChannels(user_id, channel_id);
                if (!insert_user_channels.isSuccess()) {
                    return new ErrorResult("Failed to insert user channels.");
                }
            }

            // Check for success of all operations
            if (!staffCreateMutation.isSuccess()) {
                return new ErrorResult("Failed to create staff.");
            }

            // All operations successful
            return new SuccessResult("Staff created successfully");

        } catch (Exception exception) {
            return new ErrorResult("An unexpected error occurred during staff creation.");
        }
    }

    @Override
    public Result updateStaffDetails(StaffDto staffDto, String authToken, HttpServletRequest request) {
        try {
            // Perform the staff update mutation
            Result staffUpdateResult = CommonUtil.staffUpdateMutation(staffDto.getStaffId(), staffDto.getStaffFirstName(),
                    staffDto.getStaffLastName(), staffDto.getStaffEmail(), staffDto.getAddGroups(), authToken, request);

            Result mobileNumberUpdateResult = this.serviceDao.updateMobileNumberWithStaff(
                    staffDto.getStaffEmail(),
                    staffDto.getStaffMobileNo()
            );

            // Check if the staff update was successful
            if (staffUpdateResult.isSuccess() && mobileNumberUpdateResult.isSuccess()) {
                // Return a success message
                return new SuccessResult("Staff information updated successfully.");
            } else {
                // Return an error message
                return new ErrorResult("Failed to update staff information.");
            }
        } catch (Exception e) {
            // Return an error message if an exception occurs
            return new ErrorResult("An error occurred while updating staff information.");
        }
    }

    @Override
    public DataResult<List<StaffDto>> staffList(Integer pageNumber, String authToken, String after, String isAsc, HttpServletRequest request) {
        try {
            // Call the staffListQuery method to fetch staff list
            DataResult<List<StaffDto>> staffListQuery = CommonUtil.staffListQuery(pageNumber, authToken, after, isAsc, request);

            // Check if the staffListQuery was successful
            if (!staffListQuery.isSuccess()) {
                return new ErrorDataResult<>("Failed to fetch staff list.");
            }

            // Retrieve the data (list of StaffDto objects) from the query result
            List<StaffDto> data = staffListQuery.getData();

            // Return success result with the fetched data
            return new SuccessDataResult<>(data, "Staff list fetched successfully");

        } catch (Exception ex) {
            return new ErrorDataResult<>("Failed to fetch staff list: An unexpected error occurred. Please contact support.");
        }
    }

    @Override
    public DataResult<?> getStaffById(String staffId, String authToken, HttpServletRequest request) {
        try {
            DataResult<StaffDto> staffById = CommonUtil.getStaffById(staffId, authToken, request);
            if (staffById.isSuccess()) {
                return staffById;
            } else {
                // Handle failure case
                return new ErrorDataResult<>("Failed to get staff by ID");
            }
        } catch (Exception e) {
            // Handle unexpected exceptions
            return new ErrorDataResult<>("An unexpected error occurred while getting staff by ID");
        }
    }

    @Override
    public Result deleteStaff(String staffId, String authToken, String email, HttpServletRequest request) {
        try {
            Result userChannelsDeletedByEmail = this.serviceDao.userChannelsDeletedByEmail(email);

            if (!userChannelsDeletedByEmail.isSuccess()) {
                return new ErrorResult(userChannelsDeletedByEmail.getMessage());
            }

            DataResult<String> staffDeletionResult = CommonUtil.staffDelete(staffId, authToken, request);

            if (staffDeletionResult.isSuccess()) {
                return new SuccessResult("Staff member deleted successfully");
            } else {
                return new ErrorResult("Failed to delete staff member with ID: " + staffId);
            }
        } catch (Exception e) {
            return new ErrorResult("Failed to delete staff member: An unexpected error occurred. " + e.getMessage());
        }
    }

}
