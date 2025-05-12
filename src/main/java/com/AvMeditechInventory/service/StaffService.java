/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.dtos.StaffDto;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface StaffService {

    public Result staffCreate(StaffDto staffDto, String stores, String authToken, HttpServletRequest request);

    public Result updateStaffDetails(StaffDto staffDto, String authToken, HttpServletRequest request);

    public DataResult<List<StaffDto>> staffList(Integer pageNumber, String authToken, String after, String isAsc, HttpServletRequest request);

    public DataResult<?> getStaffById(String staffId, String authToken, HttpServletRequest request);

    public Result deleteStaff(String staffId, String authToken, String email, HttpServletRequest request);

}
