/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.ProductTypeService;
import java.util.List;
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
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping("/productTypeList")
    public String productTypeListPage(
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
            // Assuming you want to fetch the first 5 product types by default
            DataResult<List<ProductType>> productTypeListQuery = this.productTypeService.productTypeListQuery(pageNumber, 
                    authToken, after, isAsc, request);

            if (!productTypeListQuery.isSuccess()) {
                redirectAttributes.addFlashAttribute("errorMessage", productTypeListQuery.getMessage());
                return "productTypeList";
            }

            List<ProductType> productTypeList = productTypeListQuery.getData();
            model.addAttribute("productTypeList", productTypeList);

            if (!productTypeList.isEmpty()) {
                model.addAttribute("firstCursor", productTypeList.get(0).getCursor());
                model.addAttribute("lastCursor", productTypeList.get(productTypeList.size() - 1).getCursor());
                model.addAttribute("currentCursor", after);
                model.addAttribute("hasNextPage", productTypeList.get(0).isHasNextPage());
                model.addAttribute("hasPreviousPage", productTypeList.get(0).isHasPreviousPage());
            }

            return "productTypeList";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while processing your request: ");
            return "productTypeList";
        }
    }

    @PostMapping("/processProductTypeCreate")
    public String processProductTypeCreate(
            @ModelAttribute("productType") ProductType productType,
            Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            Result productTypeCreateMutation = this.productTypeService.productTypeCreateMutation(productType.getProductTypeName(), authToken, request);
            if (productTypeCreateMutation.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", productTypeCreateMutation.getMessage());
                return "redirect:/productTypeList";
            }
            redirectAttributes.addFlashAttribute("errorMessage", productTypeCreateMutation.getMessage());
            return "redirect:/productTypeList";
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/productTypeList";
        }

    }

    @GetMapping("/productTypeDelete")
    public String categoryDelete(@RequestParam(value = "productTypeId", required = true) String productTypeId,
            Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            Result productTypeDeleteMutation = this.productTypeService.productTypeDeleteMutation(productTypeId, authToken, request);
            if (productTypeDeleteMutation.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", productTypeDeleteMutation.getMessage());
                return "redirect:/productTypeList";
            }
            redirectAttributes.addFlashAttribute("errorMessage", productTypeDeleteMutation.getMessage());
            return "redirect:/productTypeList";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while processing your request." + e.getMessage());
            return "redirect:/productTypeList";
        }
    }

    @PostMapping("/processProductTypeUpdate")
    public String productTypeUpdate(@ModelAttribute("productType") ProductType productType,
            Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            Result productTypeUpdateMutation = this.productTypeService.productTypeUpdateMutation(productType.getProductTypeId(), productType.getProductTypeName(), authToken, request);
            if (productTypeUpdateMutation.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", productTypeUpdateMutation.getMessage());
                return "redirect:/productTypeList";
            }
            redirectAttributes.addFlashAttribute("errorMessage", productTypeUpdateMutation.getMessage());
            return "redirect:/productTypeList";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred while processing your request." + e.getMessage());
            return "redirect:/productTypeList";
        }

    }
}
