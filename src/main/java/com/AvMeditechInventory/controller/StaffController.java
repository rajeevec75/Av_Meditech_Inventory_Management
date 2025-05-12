/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.StaffDto;
import com.AvMeditechInventory.entities.Channels;
import com.AvMeditechInventory.entities.PermissionGroups;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.ChannelsService;
import com.AvMeditechInventory.service.PermissionGroupsService;
import com.AvMeditechInventory.service.StaffService;
import com.AvMeditechInventory.util.PaginationUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private PermissionGroupsService permissionGroupsService;

    @Autowired
    private ChannelsService channelsService;

    @Autowired
    private PaginationUtil paginationUtil;

    @GetMapping("/staffCreate")
    public String staffCreatePage(
            @RequestParam(value = "first", required = false, defaultValue = "100") Integer first,
            HttpSession session, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            List<PermissionGroups> sessionPermissionGroups = (List<PermissionGroups>) session.getAttribute("permissionGroups");

            DataResult<List<PermissionGroups>> permissionGroupsListQuery = this.permissionGroupsService.permissionGroupsListQuery(first, authToken, request);
            List<PermissionGroups> allPermissionGroups = permissionGroupsListQuery.getData();
            List<PermissionGroups> addedPermissionGroups = new ArrayList<>();

            // Extract the names of the permission groups in the session
            List<String> sessionPermissionGroupNames = sessionPermissionGroups.stream()
                    .map(PermissionGroups::getPermissionGroupsName)
                    .collect(Collectors.toList());

            // Determine if the user has the "Manage All" permission
            boolean hasManageAllPermission = sessionPermissionGroupNames.contains("Manage All");

            // Filter permission groups based on the user's permissions
            List<PermissionGroups> filteredPermissionGroups;
            if (hasManageAllPermission) {
                // If the user has "Manage All", show all permission groups
                filteredPermissionGroups = allPermissionGroups;
            } else {
                // Otherwise, show only the permission groups that match the user's permissions
                filteredPermissionGroups = allPermissionGroups.stream()
                        .filter(pg -> sessionPermissionGroupNames.contains(pg.getPermissionGroupsName()))
                        .collect(Collectors.toList());
            }

            // Add filtered permission groups to the addedPermissionGroups list
            addedPermissionGroups.addAll(filteredPermissionGroups);

            // Add filtered permission groups to model
            model.addAttribute("permissionGroups", addedPermissionGroups);

            // Add all permission groups to model
            model.addAttribute("allPermissionGroups", allPermissionGroups);

            // Fetch channels list from service
            DataResult<List<Channels>> channelChannelList = this.channelsService.channel_channel_list();
            List<Channels> channelses = channelChannelList.getData();
            model.addAttribute("channelList", channelses);

            return "staffCreate";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error occurred while loading user information page.");
            return "staffCreate";
        }
    }

    @PostMapping("/processStaffCreate")
    public String processStaffCreate(
            @ModelAttribute("staffDto") StaffDto staffDto,
            @RequestParam(value = "stores", required = true) String stores,
            HttpSession session,
            Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            Result result = this.staffService.staffCreate(staffDto, stores, authToken, request);
            if (result.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
                return "redirect:/staffList";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", result.getMessage());
                return "redirect:/staffCreate";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while processing staff creation.");
            return "redirect:/staffCreate";
        }

    }

    @GetMapping("/staffUpdate")
    public String staffUpdatePage(
            @RequestParam(value = "staffId", required = true) String staffId,
            @RequestParam(value = "first", required = false, defaultValue = "100") Integer first,
            HttpSession session, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Retrieve staff data by ID
            DataResult<?> staffByIdResult = this.staffService.getStaffById(staffId, authToken, request);
            Object data = staffByIdResult.getData();
            StaffDto staffDto = (StaffDto) data;

            // Retrieve list of permission groups
            List<PermissionGroups> sessionPermissionGroups = (List<PermissionGroups>) session.getAttribute("permissionGroups");

            DataResult<List<PermissionGroups>> permissionGroupsListQuery = this.permissionGroupsService.permissionGroupsListQuery(first, authToken, request);
            List<PermissionGroups> allPermissionGroups = permissionGroupsListQuery.getData();
            List<PermissionGroups> addedPermissionGroups = new ArrayList<>();

            // Extract the names of the permission groups in the session
            List<String> sessionPermissionGroupNames = sessionPermissionGroups.stream()
                    .map(PermissionGroups::getPermissionGroupsName)
                    .collect(Collectors.toList());

            // Determine if the user has the "Manage All" permission
            boolean hasManageAllPermission = sessionPermissionGroupNames.contains("Manage All");

            // Filter permission groups based on the user's permissions
            List<PermissionGroups> filteredPermissionGroups;
            if (hasManageAllPermission) {
                // If the user has "Manage All", show all permission groups
                filteredPermissionGroups = allPermissionGroups;
            } else {
                // Otherwise, show only the permission groups that match the user's permissions
                filteredPermissionGroups = allPermissionGroups.stream()
                        .filter(pg -> sessionPermissionGroupNames.contains(pg.getPermissionGroupsName()))
                        .collect(Collectors.toList());
            }

            // Add filtered permission groups to the addedPermissionGroups list
            addedPermissionGroups.addAll(filteredPermissionGroups);

            // Add filtered permission groups to model
            model.addAttribute("permissionGroups", addedPermissionGroups);

            // Add all permission groups to model
            model.addAttribute("allPermissionGroups", allPermissionGroups);

            // Retrieve list of channels
            DataResult<List<Channels>> channel_channel_list = this.channelsService.channel_channel_list();
            List<Channels> channelses = channel_channel_list.getData();

            DataResult<List<Channels>> channelListByUserId = this.channelsService.getChannelListByUserId(staffDto.getStaffEmail());
            List<Channels> channelses1 = channelListByUserId.getData();
            staffDto.setChannelses(channelses1);
            model.addAttribute("channelses", channelses);
            model.addAttribute("staffDto", staffDto);
            return "staffUpdate";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error occurred while loading staff information page.");
            return "staffUpdate";
        }

    }

    @PostMapping("/processStaffUpdate")
    public String processStaffUpdate(HttpServletRequest request, HttpSession session,
            @ModelAttribute("staffDto") StaffDto staffDto,
            @RequestParam(value = "addStores", required = true) String addStores,
            Model model, RedirectAttributes redirectAttributes) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            // Update staff information
            Result staffUpdateResult = this.staffService.updateStaffDetails(staffDto, authToken, request);

            // Update channels for the staff member
            Result updateChannelResult = this.channelsService.updateChannelForUser(staffDto.getStaffEmail(), addStores);

            // Check if both updates were successful
            if (staffUpdateResult.isSuccess() && updateChannelResult.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", staffUpdateResult.getMessage());
                return "redirect:/staffList";
            } else {
                // If either update fails, redirect back to the staff update page
                redirectAttributes.addFlashAttribute("errorMessage", staffUpdateResult.getMessage());
                return "redirect:/staffUpdate?staffId=" + staffDto.getStaffId();
            }
        } catch (Exception e) {
            // If an exception occurs during the update process, show an error page
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while processing staff update.");
            return "redirect:/staffUpdate?staffId=" + staffDto.getStaffId();
        }
    }

    @GetMapping("/staffList")
    public String staffListPage(
            @RequestParam(value = "pageNumber", defaultValue = "5") Integer pageNumber,
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
            // Call paginationUtil to fetch staff list
            List<StaffDto> staffList = this.paginationUtil.fetchAllStaffs(pageNumber, authToken, request);

            model.addAttribute("staffList", staffList);

            return "staffList";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while loading staff list page. Please try again later.");
            return "staffList";
        }
    }

    @GetMapping("/staffDelete")
    public String staffMemberDelete(@RequestParam(value = "staffId", required = true) String staffId, @RequestParam(value = "email", required = true, defaultValue = "") String email, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            Result staffDelete = this.staffService.deleteStaff(staffId, authToken, email, request);
            if (staffDelete.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", staffDelete.getMessage());
                return "redirect:/staffList";
            }
            redirectAttributes.addFlashAttribute("errorMessage", staffDelete.getMessage());
            return "redirect:/staffList";

        } catch (Exception e) {
            // If an unexpected error occurs, handle it appropriately
            String errorMessage = "An unexpected error occurred while deleting staff member.";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/staffList";
        }
    }

}
