/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.entities.PermissionGroups;
import com.AvMeditechInventory.results.DataResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface PermissionGroupsService {

    public DataResult<List<PermissionGroups>> permissionGroupsListQuery(Integer first, String authToken, HttpServletRequest request);
}
