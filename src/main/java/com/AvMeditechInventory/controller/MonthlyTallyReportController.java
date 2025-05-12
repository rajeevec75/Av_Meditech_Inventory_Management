/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Controller
public class MonthlyTallyReportController {

    @GetMapping("/monthlyTallyReportList")
    public String monthlyTallyReportListPage(Model model) {
        try {
            // Return the view name if everything is successful
            return "monthlyTallyReportList";
        } catch (Exception exception) {
            model.addAttribute("errorMessage", "An error occurred while processing your request. Please try again later.");
            return "errorPage";
        }
    }

}
