/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface CategoryService {

    public Result categoryCreateMutation(String categoryName, String authToken, Map<String, Object> metadata, HttpServletRequest request);

    public Result categoryDeleteMutation(String categoryId, String authToken, HttpServletRequest request);

    public DataResult<List<Category>> categoryListQuery(Integer first, String authToken, String after, String isAsc, HttpServletRequest request);

    public Result categoryUpdateMutation(String categoryId, String categoryName, Map<String, Object> metadata, String authToken, HttpServletRequest request);

}
