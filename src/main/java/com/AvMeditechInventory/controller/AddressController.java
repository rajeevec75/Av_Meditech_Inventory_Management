/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.dtos.AddressDto;
import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.service.AddressService;
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
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/getAddressListBy")
    public String getCustomerDetailsById(
            @RequestParam(value = "customerId", required = false) String customerId,
            @RequestParam(value = "supplierId", required = false) String supplierId,
            HttpSession session, RedirectAttributes redirectAttributes, Model model,
            @RequestParam(value = "pageNumber", defaultValue = "100") Integer pageNumber, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            DataResult<List<AddressDto>> addressDataResult = null;

            // Check if supplierId is null and customerId is not null, fetch addresses by customerId
            if (supplierId == null && customerId != null && !customerId.equals("null")) {
                addressDataResult = this.addressService.getAddressesByCustomerId(customerId, authToken, pageNumber, request);
            } // Check if customerId is null and supplierId is not null, fetch addresses by supplierId
            else if (supplierId != null && (customerId == null || customerId.equals("null"))) {
                addressDataResult = this.addressService.getAddressesByCustomerId(supplierId, authToken, pageNumber, request);
            }

            List<AddressDto> addressDtoList = addressDataResult.getData();
            model.addAttribute("addressList", addressDtoList);
            return "addressList";

        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while fetching customer details.");
            return "addressList";
        }

    }

    @GetMapping("/addressCreate")
    public String addressCreate(
            @RequestParam(value = "customerId", required = false) String costomerId,
            @RequestParam(value = "supplierId", required = false) String supplierId,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            return "addressCreate";

        } catch (Exception e) {
            // If an exception occurs
            model.addAttribute("errorMessage", "An error occurred while creating the address: " + e.getMessage());
            return "addressCreate";
        }

    }

    @PostMapping("/processAddressCreate")
    public String processAddressCreate(
            @ModelAttribute("customerAndSupplierDto") CustomerAndSupplierDto customerAndSupplierDto,
            HttpSession session, RedirectAttributes redirectAttributes,
            @RequestParam(value = "customerId", required = false) String customerId,
            @RequestParam(value = "supplierId", required = false) String supplierId, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            if (customerId != null && supplierId != null && supplierId.equals("")) {
                Result customeraddressCreateResult = this.addressService.addressCreate(customerAndSupplierDto, authToken, customerId, request);
                if (customeraddressCreateResult.isSuccess()) {
                    redirectAttributes.addFlashAttribute("successMessage", customeraddressCreateResult.getMessage());
                    return "redirect:/getAddressListBy?customerId=" + customerId;
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", customeraddressCreateResult.getMessage());
                    return "redirect:/addressCreate?customerId=" + customerId;
                }
            } else if (supplierId != null && customerId != null && customerId.equals("")) {
                Result supplieraddressCreate = this.addressService.addressCreate(customerAndSupplierDto, authToken, supplierId, request);
                if (supplieraddressCreate.isSuccess()) {
                    redirectAttributes.addFlashAttribute("successMessage", supplieraddressCreate.getMessage());
                    return "redirect:/getAddressListBy?supplierId=" + supplierId;
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", supplieraddressCreate.getMessage());
                    return "redirect:/addressCreate?supplierId=" + supplierId;
                }
            } else {
                return null;
            }

        } catch (Exception e) {
            String errorMessage = "An error occurred while creating the address.";
            if (customerId != null && supplierId != null && supplierId.equals("")) {
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/addressCreate?customerId=" + customerId;
            } else if (supplierId != null && customerId != null && customerId.equals("")) {
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/addressCreate?supplierId=" + supplierId;
            } else {
                return null;
            }
        }
    }

    @GetMapping("/addressUpdate")
    public String addressUpdatePage(
            @RequestParam(value = "customerId", required = false) String customerId,
            @RequestParam(value = "supplierId", required = false) String supplierId,
            @RequestParam(value = "addressId", required = true) String addressId,
            @RequestParam(value = "pageNumber", defaultValue = "100") Integer pageNumber,
            HttpSession session, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }
            DataResult<List<AddressDto>> addressListResult = null;

            if (supplierId == null && customerId != null) {
                addressListResult = this.addressService.getAddressesByCustomerId(customerId, authToken, pageNumber, request);
            } else if (supplierId != null && customerId == null) {
                addressListResult = this.addressService.getAddressesByCustomerId(supplierId, authToken, pageNumber, request);
            }

            if (addressListResult != null && addressListResult.isSuccess()) {
                List<AddressDto> data = addressListResult.getData();
                for (AddressDto addressDto : data) {
                    if (addressDto.getAddressId().equals(addressId)) {
                        // Found the address with the specified addressId
                        model.addAttribute("addressDto", addressDto);
                        break;
                    }
                }
            } else {
                // If address list retrieval fails or is null, set an error message
                String errorMessage = "Failed to retrieve addresses: ";
                if (addressListResult != null) {
                    errorMessage += addressListResult.getMessage();
                } else {
                    errorMessage += "Address list is null.";
                }
                model.addAttribute("errorMessage", errorMessage);
                return "addressUpdate";
            }
        } catch (Exception e) {
            // If an unexpected exception occurs, set an error message
            model.addAttribute("errorMessage", "An error occurred while fetching addresses.");
            return "addressUpdate";
        }

        return "addressUpdate";

    }

    @PostMapping("/processAddressUpdate")
    public String processAddressUpdate(@ModelAttribute("addressDto") AddressDto addressDto, HttpSession session, 
            RedirectAttributes redirectAttributes, HttpServletRequest request) {

        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            if (addressDto.getCustomerId() != null) {
                Result addressUpdate = this.addressService.addressUpdate(addressDto, authToken, request);
                if (addressUpdate.isSuccess()) {
                    redirectAttributes.addFlashAttribute("successMessage", addressUpdate.getMessage());
                    return "redirect:/getAddressListBy?customerId=" + addressDto.getCustomerId();
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", addressUpdate.getMessage());
                    return "redirect:/addressUpdate?customerId=" + addressDto.getCustomerId() + "&addressId=" + addressDto.getAddressId();
                }
            } else if (addressDto.getSupplierId() != null) {
                Result addressUpdate = this.addressService.addressUpdate(addressDto, authToken, request);
                if (addressUpdate.isSuccess()) {
                    redirectAttributes.addFlashAttribute("successMessage", addressUpdate.getMessage());
                    return "redirect:/getAddressListBy?supplierId=" + addressDto.getSupplierId();
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", addressUpdate.getMessage());
                    return "redirect:/addressUpdate?supplierId=" + addressDto.getSupplierId() + "&addressId=" + addressDto.getAddressId();
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while updating the address.";
            if (addressDto.getCustomerId() != null) {
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/addressUpdate?customerId=" + addressDto.getCustomerId();
            } else if (addressDto.getSupplierId() != null) {
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/addressUpdate?supplierId=" + addressDto.getSupplierId();
            } else {
                return null;
            }
        }
    }

    @GetMapping("/addressDelete")
    public String addressDelete(@RequestParam(value = "addressId", required = true) String addressId,
            @RequestParam(value = "customerId", required = false) String customerId,
            @RequestParam(value = "supplierId", required = false) String supplierId,
            HttpSession session, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            // Check authentication token
            String authToken = (String) session.getAttribute("token");
            if (authToken == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to access this resource.");
                return "redirect:/";
            }

            Result addressDelete = this.addressService.addressDelete(addressId, authToken, request);
            String message = addressDelete.getMessage();

            if (!addressDelete.isSuccess()) {
                redirectAttributes.addFlashAttribute("errorMessage", message);
                if (customerId != null) {
                    return "redirect:/getAddressListBy?customerId=" + customerId;
                } else if (supplierId != null) {
                    return "redirect:/getAddressListBy?supplierId=" + supplierId;
                } else {
                    return null;
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", message);

            if (customerId != null) {
                return "redirect:/getAddressListBy?customerId=" + customerId;
            } else if (supplierId != null) {
                return "redirect:/getAddressListBy?supplierId=" + supplierId;
            } else {
                return null;
            }

        } catch (Exception e) {
            String errorMessage = "An error occurred while deleting the address.";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            if (customerId != null) {
                return "redirect:/getAddressListBy?customerId=" + customerId;
            } else if (supplierId != null) {
                return "redirect:/getAddressListBy?supplierId=" + supplierId;
            } else {
                return null;
            }
        }
    }

}
