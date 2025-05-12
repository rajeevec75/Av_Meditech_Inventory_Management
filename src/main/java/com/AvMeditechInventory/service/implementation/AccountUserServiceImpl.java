/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.service.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.AvMeditechInventory.dtos.AccountUserDto;
import com.AvMeditechInventory.entities.AccountUser;
import com.AvMeditechInventory.repository.AccountUserRepository;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.LoginResponse;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.AccountUserService;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.ServiceDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class AccountUserServiceImpl implements AccountUserService {

    private final AccountUserRepository accountUserRepository;

    @Autowired
    public AccountUserServiceImpl(AccountUserRepository accountUserRepository) {
        this.accountUserRepository = accountUserRepository;
    }

    @Autowired
    private ServiceDao serviceDao;

    // send userEmail, userPassword and generate token
    @Override
    public DataResult<LoginResponse> accountUserLogin(AccountUserDto accountUserDto, HttpSession session, HttpServletRequest request) {
        try {
            String userEmail = accountUserDto.getUserEmail();
            String userPassword = accountUserDto.getUserPassword();

            // Validate email format
            if (!CommonUtil.isValidEmail(userEmail)) {
                return new ErrorDataResult<>("Invalid email format.");
            }

            Optional<AccountUser> accountUser = accountUserRepository.findByEmail(userEmail);
            if (accountUser.isPresent()) {
                AccountUser user = accountUser.get();
                DataResult<LoginResponse> tokenGenerateUserAndSendApiRequest = CommonUtil.tokenGenerateUserAndSendApiRequest(userEmail, userPassword, user,
                        session, request);
                if (tokenGenerateUserAndSendApiRequest.isSuccess()) {
                    // Token generation successful
                    return new SuccessDataResult<>(tokenGenerateUserAndSendApiRequest.getData(), "Login successful.");
                } else {
                    // Token generation failed
                    return new ErrorDataResult<>("Failed to generate token.");
                }
            } else {
                // User not found
                return new ErrorDataResult<>("User with email " + userEmail + " not found.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred.");
        }
    }

    @Override
    public Map<String, Object> accountUserChangePassword(AccountUserDto accountUserDto, String userVerifyEmail,
            String authToken, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String newPassword = accountUserDto.getNewPassword();
            String oldPassword = accountUserDto.getOldPassword();
            String confirmPassword = accountUserDto.getConfirmPassword();

            Optional<AccountUser> optionalAccountUser = this.accountUserRepository.findByEmail(userVerifyEmail);
            if (optionalAccountUser.isEmpty()) {
                response.put("api_status", false);
                response.put("message", "User not found.");
                return response;
            }

            if (!newPassword.equals(confirmPassword)) {
                response.put("api_status", false);
                response.put("message", "New password and confirm password do not match.");
                return response;
            }

            Result result = CommonUtil.changePasswordMutation(oldPassword, newPassword, authToken, request);

            if (result.isSuccess()) {
                response.put("api_status", true);
                response.put("message", "Password changed successfully.");
            } else {
                response.put("api_status", false);
                response.put("message", "Failed to change password.");
            }
        } catch (Exception e) {
            response.put("api_status", false);
            response.put("message", "An unexpected error occurred while changing the password.");
        }

        return response;
    }

    @Override
    public Result userLogout(HttpSession session) {
        try {
            if (session == null) {
                return new ErrorResult("User is already logged out.");
            } else {
                session.invalidate();
                return new SuccessResult("Logout successful");
            }
        } catch (Exception e) {
            // Log the exception or handle it as needed
            return new ErrorResult("Logout failed: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<AccountUser>> retrieveAllUsersByCompanyName(String companyName) {
        try {
            List<AccountUser> users = accountUserRepository.findByCompanyNameContainingIgnoreCase(companyName);

            if (users.isEmpty()) {
                return new ErrorDataResult<>("No users found with the given company name.");
            } else {
                return new SuccessDataResult<>(users, "Users retrieved successfully.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while retrieving users.");
        }
    }

    @Override
    public DataResult<List<AccountUser>> retrieveAllUsers() {
        try {
            List<AccountUser> users = this.accountUserRepository.findAll(Sort.by(Sort.Direction.ASC, "userId"));

            // Filter out users with null company name
            users = users.stream()
                    .filter(user -> user.getCompanyName() != null)
                    .collect(Collectors.toList());

            if (users == null || users.isEmpty()) {
                return new ErrorDataResult<>(null, "No users found.");
            }

            return new SuccessDataResult<>(users, "Users retrieved successfully.");
        } catch (Exception exception) {
            return new ErrorDataResult<>(null, "Failed to retrieve users: " + exception.getMessage());
        }
    }

    @Override
    public DataResult<List<AccountUser>> getAccountsByUserType(List<String> userTypes) {
        try {
            // Retrieve accounts by user types from the DAO layer
            DataResult<List<Map<String, Object>>> accountsByUserTypeResult = this.serviceDao.getAccountsByUserType(userTypes);

            // Check if the DAO returned a successful result
            if (!accountsByUserTypeResult.isSuccess() || accountsByUserTypeResult.getData() == null) {
                return new ErrorDataResult<>(null, "No accounts found for the given user types.");
            }

            // Convert the result to AccountUser objects using a for loop
            List<AccountUser> users = new ArrayList<>();
            for (Map<String, Object> map : accountsByUserTypeResult.getData()) {
                String companyName = (String) map.get("company_name");
                String userCode = (String) map.get("user_code");

                // Filter only those with a non-null company name
                if (companyName != null) {
                    AccountUser user = new AccountUser();
                    user.setCompanyName(companyName);
                    user.setUserCode(userCode);

                    // Map other necessary fields if applicable
                    user.setUserId((Integer) map.get("id"));
                    user.setCompanyName((String) map.get("company_name"));
                    user.setUserType((String) map.get("user_type"));

                    users.add(user);
                }
            }

            if (users.isEmpty()) {
                return new ErrorDataResult<>(null, "No users found with non-null company names.");
            }

            return new SuccessDataResult<>(users, "Users retrieved successfully.");
        } catch (Exception exception) {
            // Handle unexpected exceptions and provide a meaningful error message
            return new ErrorDataResult<>(null, "Failed to retrieve users: " + exception.getMessage());
        }
    }

}
