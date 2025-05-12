/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.CategoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categoryList")
    public String categoryListPage(
            @RequestParam(value = "pageNumber", defaultValue = "50") Integer pageNumber,
            @RequestParam(value = "after", defaultValue = "") String after,
            @RequestParam(value = "isAsc", defaultValue = "next") String isAsc,
            Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            DataResult<List<Category>> categoryResult = categoryService.categoryListQuery(pageNumber, authToken, after, isAsc, request);

            if (!categoryResult.isSuccess()) {
                model.addAttribute("errorMessage", categoryResult.getMessage());
                return "categoryList";
            }

            List<Category> categoryList = categoryResult.getData();
            model.addAttribute("categories", categoryList);

            if (!categoryList.isEmpty()) {
                model.addAttribute("firstCursor", categoryList.get(0).getCursor());
                model.addAttribute("lastCursor", categoryList.get(categoryList.size() - 1).getCursor());
                model.addAttribute("currentCursor", after);
                model.addAttribute("hasNextPage", categoryList.get(0).isHasNextPage());
                model.addAttribute("hasPreviousPage", categoryList.get(0).isHasPreviousPage());
            }

            return "categoryList";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An unexpected error occurred while processing your request: ");
            return "categoryList";
        }
    }

    @PostMapping("/processCategoryCreate")
    public String processCategoryCreate(@ModelAttribute("category") Category category,
            @RequestParam(value = "key", defaultValue = "serialNumberPattern") String[] keys,
            @RequestParam(value = "value") Object[] values,
            Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            Map<String, Object> metadata = new HashMap<>();
            // Populate the metadata map with key-value pairs
            for (int i = 0; i < keys.length; i++) {
                metadata.put(keys[i], values[i]);
            }

            Result categoryCreateMutation = this.categoryService.categoryCreateMutation(category.getCategoryName(), authToken, metadata, request);

            if (categoryCreateMutation.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", categoryCreateMutation.getMessage());
                return "redirect:/categoryList";
            }
            redirectAttributes.addFlashAttribute("errorMessage", categoryCreateMutation.getMessage());
            return "redirect:/categoryList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while processing your request.");
            return "redirect:/categoryList";
        }

    }

    @GetMapping("/categoryDelete")
    public String categoryDelete(@RequestParam(value = "categoryId", required = true) String categoryId,
            Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            Result categoryDeleteMutation = this.categoryService.categoryDeleteMutation(categoryId, authToken, request);
            if (categoryDeleteMutation.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", categoryDeleteMutation.getMessage());
                return "redirect:/categoryList";
            }
            redirectAttributes.addFlashAttribute("errorMessage", categoryDeleteMutation.getMessage());
            return "redirect:/categoryList";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while processing your request.");
            return "redirect:/categoryList";
        }
    }

    @PostMapping("/processCategoryUpdate")
    public String processCategoryUpdate(@ModelAttribute("category") Category category,
            @RequestParam(value = "key", defaultValue = "serialNumberPattern") String[] keys,
            @RequestParam(value = "value", required = false) Object[] values,
            HttpSession session, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            Map<String, Object> metadata = new HashMap<>();

            // Populate the metadata map with key-value pairs
            if (keys != null && values != null) {
                for (int i = 0; i < keys.length; i++) {
                    metadata.put(keys[i], values[i]);
                }
            }
            Result categoryUpdateMutation = this.categoryService.categoryUpdateMutation(category.getCategoryId(), category.getCategoryName(), metadata, authToken, request);
            if (categoryUpdateMutation.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", categoryUpdateMutation.getMessage());
                return "redirect:/categoryList";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", categoryUpdateMutation.getMessage());
                return "redirect:/categoryList";
            }
        } catch (Exception e) {
            // Exception occurred, set an error message
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while processing your request.");
            return "redirect:/categoryList";
        }
    }

}
