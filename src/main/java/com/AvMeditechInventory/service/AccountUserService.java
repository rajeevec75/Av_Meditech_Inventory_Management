/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.service;

import java.util.Map;
import com.AvMeditechInventory.dtos.AccountUserDto;
import com.AvMeditechInventory.entities.AccountUser;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.LoginResponse;
import com.AvMeditechInventory.results.Result;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface AccountUserService {

    public DataResult<LoginResponse> accountUserLogin(AccountUserDto accountUserDto, HttpSession session, HttpServletRequest request);

    public Map<String, Object> accountUserChangePassword(AccountUserDto accountUserDto, String userVerifyEmail,
            String accessToken, HttpServletRequest request);

    public Result userLogout(HttpSession session);

    public DataResult<List<AccountUser>> retrieveAllUsersByCompanyName(String companyName);

    public DataResult<List<AccountUser>> retrieveAllUsers();

    public DataResult<List<AccountUser>> getAccountsByUserType(List<String> userTypes);

}
