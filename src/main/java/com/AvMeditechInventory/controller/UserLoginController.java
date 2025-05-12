/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.AvMeditechInventory.dtos.AccountUserDto;
import com.AvMeditechInventory.entities.AccountUser;
import com.AvMeditechInventory.entities.PermissionGroups;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.LoginResponse;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.AccountUserService;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Rajeev kumar(QMM TECHNOLOGIES PRIVATE LIMITED)
 */
@Controller
public class UserLoginController {

    @Autowired
    private AccountUserService accountUserService;

    @Autowired
    private ServiceDao serviceDao;

    @GetMapping("/")
    public String userLoginPage(HttpSession session, Model model) {
        try {
            return "userLogin";

        } catch (Exception exception) {
            model.addAttribute("errorMessage", "An unexpected error occurred during login.");
            return "userLogin";
        }
    }

    @PostMapping("/processUserLogin")
    public String processUserLogin(
            HttpServletRequest request, @ModelAttribute("accountUserDto") AccountUserDto accountUserDto,
            HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        try {
            // Attempt to log in the user
            DataResult<LoginResponse> accountUserLoginResult = this.accountUserService.accountUserLogin(accountUserDto, session, request);

            if (accountUserLoginResult == null || !accountUserLoginResult.isSuccess()) {
                String errorMessage = "Login failed. Please try valid credentials.";
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/";
            }

            // If login is successful, process login response
            LoginResponse loginResponse = accountUserLoginResult.getData();
            if (loginResponse != null) {
                List<PermissionGroups> permissionGroups = loginResponse.getUser().getPermissionGroups();
                session.setAttribute("permissionGroups", permissionGroups);
                redirectAttributes.addFlashAttribute("successMessage", accountUserLoginResult.getMessage());
                return "redirect:/dashboard";
            } else {
                // Handle case where login response is null
                redirectAttributes.addFlashAttribute("errorMessage", accountUserLoginResult.getMessage());
                return "redirect:/";
            }
        } catch (Exception exception) {
            // Handle any exceptions that occur during login
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred during login.");
            return "redirect:/";
        }
    }

    @GetMapping("/userChangePassword")
    public String userChangePasswordPage(Model model) {
        try {
            // Assuming "/userChangePassword" is the URL to redirect to
            return "userChangePassword";
        } catch (Exception exception) {
            model.addAttribute("errorMessage", "An unexpected error occurred: " + exception.getMessage());
            return "userChangePassword";
        }
    }

    @PostMapping("/processUserChangePassword")
    public String processUserChangePassword(HttpServletRequest request, HttpSession session,
            @ModelAttribute("accountUserDto") AccountUserDto accountUserDto, RedirectAttributes redirectAttributes) {

        try {
            String accessToken = (String) session.getAttribute("token");
            String userVerifyEmail = (String) session.getAttribute("userEmail");
            Map<String, Object> accountUserChangePassword = this.accountUserService.accountUserChangePassword(accountUserDto, userVerifyEmail, accessToken, request);

            // Check the response from the service
            boolean apiStatus = (boolean) accountUserChangePassword.get("api_status");
            if (apiStatus) {
                redirectAttributes.addFlashAttribute("successMessage", accountUserChangePassword.get("message"));
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", accountUserChangePassword.get("message"));
                return "redirect:/userChangePassword";
            }
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("errorMessage", "An unexpected error occurred.");
            return "redirect:/userChangePassword";
        }

    }

    @GetMapping("/userLogout")
    public ModelAndView userLogout(HttpSession session) {
        try {
            // Perform user logout logic, such as invalidating session or updating user status
            Result userLogout = this.accountUserService.userLogout(session);
            if (userLogout.isSuccess()) {
                // Redirect to login page after logout
                return new ModelAndView("redirect:/");
            } else {
                ModelAndView errorModelAndView = new ModelAndView("errorPage");
                return errorModelAndView;
            }

        } catch (Exception e) {
            // If an exception occurs, handle it gracefully
            ModelAndView errorModelAndView = new ModelAndView("errorPage");
            errorModelAndView.addObject("errorMessage", "Error occurred while logging out.");
            return errorModelAndView;
        }
    }

    @GetMapping("/dashboard")
    public ModelAndView userDashboardPage(Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String userEmail = (String) session.getAttribute("userEmail");
            List<Map<String, Object>> channelList = serviceDao.userChannelList(userEmail).getData();
            session.setAttribute("channelList", channelList);
            int channelId;
            if (session.getAttribute("selectedStoreId") == null && channelList != null && !channelList.isEmpty()) {
                Map<String, Object> firstStore = channelList.get(0);
                session.setAttribute("selectedStoreId", (int) firstStore.get("id"));
                session.setAttribute("selectedStoreName", (String) firstStore.get("name"));
                channelId = (int) firstStore.get("id");
            } else {
                channelId = (int) session.getAttribute("selectedStoreId");
            }

            String startDate = "2024-04-01";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = simpleDateFormat.parse(startDate);
            Date toDate = new Date();
//            DataResult<BigDecimal> totalSaleAmountResult = serviceDao.calculateTotalSaleAmount(fromDate, toDate, channelId);
//            BigDecimal totalSaleAmount = totalSaleAmountResult.getData();

            DataResult<Integer> totalCustomerResult = serviceDao.calculateUser(Constant.CUSTOMER, fromDate, toDate);
            Integer totalCustomerCount = totalCustomerResult.getData();

            DataResult<Integer> totalSupplierResult = serviceDao.calculateUser(Constant.SUPPLIER, fromDate, toDate);
            Integer totalSupplierCount = totalSupplierResult.getData();

            DataResult<Integer> totalPurchaseResult = serviceDao.calculateTotalPurchase(fromDate, toDate, channelId);
            Integer totalPurchaseCount = totalPurchaseResult.getData();

            DataResult<Integer> totalSaleResult = serviceDao.calculateTotalSale(fromDate, toDate, channelId);
            Integer totalSaleCount = totalSaleResult.getData();

//            DataResult<BigDecimal> totalPurchaseAmountResult = serviceDao.calculateTotalPurchaseAmount(fromDate, toDate, channelId);
//            BigDecimal totalPurchaseAmount = totalPurchaseAmountResult.getData();
//            DataResult<List<Map<String, Object>>> purchaseMonthRecord = serviceDao.purchaseMonthRecord(fromDate, toDate, channelId);
//            List<Map<String, Object>> purchaseData = purchaseMonthRecord.getData();
//            
//            DataResult<List<Map<String, Object>>> saleMonthRecord = serviceDao.saleMonthRecord(fromDate, toDate, channelId);
//            List<Map<String, Object>> saleData = saleMonthRecord.getData();
//            List<String> saleMonth = new ArrayList<>();
//            List<String> saleList = new ArrayList<>();
//            List<String> purchaseList = new ArrayList<>();
//            
//            for (int i = 0; i < purchaseData.size(); i++) {
//                purchaseList.add(((BigDecimal) purchaseData.get(i).get("total_amount")).toString());
//            }
//            
//            for (int i = 0; i < saleData.size(); i++) {
//                saleMonth.add((String) saleData.get(i).get("month1"));
//                saleList.add(((BigDecimal) saleData.get(i).get("total_amount")).toString());
//            }
            Map<String, Object> response = new HashMap<>();
//            response.put("totalSaleAmount", totalSaleAmount);
            response.put("totalCustomerCount", totalCustomerCount);
            response.put("totalSupplierCount", totalSupplierCount);
            response.put("totalPurchaseCount", totalPurchaseCount);
            response.put("totalSaleCount", totalSaleCount);
//            response.put("totalPurchaseAmount", totalPurchaseAmount);
            model.addAttribute("dashboardData", response);
            model.addAttribute("fromDate", startDate);
            model.addAttribute("toDate", simpleDateFormat.format(toDate));
//            model.addAttribute("purchaseList", purchaseList);
//            model.addAttribute("saleMonth", saleMonth);
//            model.addAttribute("saleList", saleList);
            // Here you would typically add data to the model or perform other operations related to the dashboard
            modelAndView.setViewName("userDashboard");
        } catch (Exception e) {
            // Handle any exceptions that might occur
            modelAndView.setViewName("errorPage"); // Redirect to an error page
            modelAndView.addObject("errorMessage", "An error occurred while loading the dashboard."); // Add error message to be displayed on the error page
        }
        return modelAndView;
    }

    @PostMapping("/startDateAndEndDate/json")
    @ResponseBody
    public ResponseEntity<?> calculateTotalPurchaseAmountByStartDateAndEndDate(
            @RequestParam(value = "startDate", required = true) String startDate,
            @RequestParam(value = "endDate", required = true) String endDate, HttpSession session) {

        try {
            int channelId = (int) session.getAttribute("selectedStoreId");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = simpleDateFormat.parse(startDate);
            Date toDate = simpleDateFormat.parse(endDate);

            DataResult<BigDecimal> totalSaleAmountResult = serviceDao.calculateTotalSaleAmount(fromDate, toDate, channelId);
            BigDecimal totalSaleAmount = totalSaleAmountResult.getData();

            DataResult<Integer> totalCustomerResult = serviceDao.calculateUser(Constant.CUSTOMER, fromDate, toDate);
            Integer totalCustomerCount = totalCustomerResult.getData();

            DataResult<Integer> totalSupplierResult = serviceDao.calculateUser(Constant.SUPPLIER, fromDate, toDate);
            Integer totalSupplierCount = totalSupplierResult.getData();

            DataResult<Integer> totalPurchaseResult = serviceDao.calculateTotalPurchase(fromDate, toDate, channelId);
            Integer totalPurchaseCount = totalPurchaseResult.getData();

            DataResult<Integer> totalSaleResult = serviceDao.calculateTotalSale(fromDate, toDate, channelId);
            Integer totalSaleCount = totalSaleResult.getData();

            DataResult<BigDecimal> totalPurchaseAmountResult = serviceDao.calculateTotalPurchaseAmount(fromDate, toDate, channelId);
            BigDecimal totalPurchaseAmount = totalPurchaseAmountResult.getData();

            Map<String, Object> response = new HashMap<>();
            response.put("totalSaleAmount", totalSaleAmount);
            response.put("totalCustomerCount", totalCustomerCount);
            response.put("totalSupplierCount", totalSupplierCount);
            response.put("totalPurchaseCount", totalPurchaseCount);
            response.put("totalSaleCount", totalSaleCount);
            response.put("totalPurchaseAmount", totalPurchaseAmount);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.ok("An error occurred while calculating the totals: " + e.getMessage());
        }
    }

    @GetMapping("/autoLogin")
    public ModelAndView autoLogin(@RequestParam(value = "refresh_token", defaultValue = "") String refreshToken,
            @RequestParam(value = "next_url", defaultValue = "") String nextUrl, HttpSession session, HttpServletRequest request) {
        try {
            Result resultRefreshToken = CommonUtil.autoLogin(refreshToken, session, request);
            if (resultRefreshToken.isSuccess()) {
                String userEmail = (String) session.getAttribute("userEmail");
                List<Map<String, Object>> channelList = serviceDao.userChannelList(userEmail).getData();
                session.setAttribute("channelList", channelList);
                if (session.getAttribute("selectedStoreId") == null && channelList != null && !channelList.isEmpty()) {
                    Map<String, Object> firstStore = channelList.get(0);
                    session.setAttribute("selectedStoreId", (int) firstStore.get("id"));
                    session.setAttribute("selectedStoreName", (String) firstStore.get("name"));
                }
                return new ModelAndView("redirect:/" + nextUrl);
            } else {
                return new ModelAndView("redirect:/");
            }
        } catch (Exception exception) {
            // Handle exceptionsn
            ModelAndView modelAndView = new ModelAndView("errorPage");
            modelAndView.addObject("errorMessage", "An unexpected error occurred: " + exception.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/retrieveAllUsersByCompanyName")
    @ResponseBody
    public ResponseEntity<?> retrieveAllUsersByCompanyName(@RequestParam(value = "companyName", required = false, defaultValue = "") String companyName) {
        try {
            DataResult<List<AccountUser>> retrieveAllUsers = this.accountUserService.retrieveAllUsersByCompanyName(companyName);

            // Check if the operation was successful and return the appropriate response
            if (retrieveAllUsers.isSuccess()) {
                List<AccountUser> retrieveAllUser = retrieveAllUsers.getData();
                return ResponseEntity.ok(retrieveAllUser);  // Return the successful result with HTTP 200 OK
            } else {
                return ResponseEntity.badRequest().body(retrieveAllUsers);  // Return the error result with HTTP 400 Bad Request
            }
        } catch (Exception e) {
            // Handle exceptions and return an error response with HTTP 500 Internal Server Error
            return ResponseEntity.ok(new ErrorDataResult<>("An error occurred while fetching the users: " + e.getMessage()));
        }
    }
}
