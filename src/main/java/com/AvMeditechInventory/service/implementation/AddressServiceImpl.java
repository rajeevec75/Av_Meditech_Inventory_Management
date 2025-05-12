/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.AddressDto;
import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.AddressService;
import com.AvMeditechInventory.util.CommonUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Override
    public Result addressCreate(CustomerAndSupplierDto customerDto, String authToken, String id, HttpServletRequest request) {
        try {

            String streetAddress1 = customerDto.getStreetAddress1();
            String streetAddress2 = customerDto.getStreetAddress2();
            String city = customerDto.getCity();
            String country = customerDto.getCountry();
            String countryArea = customerDto.getCountryArea();
            String postalCode = customerDto.getPostalCode();
            DataResult<?> addressCreateMutation = CommonUtil.addressCreateMutation(id, streetAddress1, streetAddress2, 
                    city, country, countryArea, postalCode, authToken, request);
            if (addressCreateMutation.isSuccess()) {
                return new SuccessResult("Address created successfully.");
            } else {
                return new ErrorResult("Failed to create address.");
            }
        } catch (Exception e) {
            return new ErrorResult("An error occurred while creating the address.");
        }
    }

    @Override
    public DataResult<List<AddressDto>> getAddressesByCustomerId(String customerId, String authToken, 
            Integer pageNumber, HttpServletRequest request) {
        try {
            if (customerId == null || customerId.isEmpty()) {
                return new ErrorDataResult<>("Customer ID cannot be empty");
            }

            DataResult<List<AddressDto>> addressesByCustomerId = CommonUtil.getAddressesByCustomerId(customerId, 
                    authToken, pageNumber, request);
            if (addressesByCustomerId.isSuccess()) {
                List<AddressDto> addressList = addressesByCustomerId.getData();
                if (addressList.isEmpty()) {
                    return new ErrorDataResult<>("No addresses found for the customer ID: " + customerId);
                } else {

                    return new SuccessDataResult<>(addressList, "Customer address fetched successfully");
                }
            } else {
                return new ErrorDataResult<>("Failed to fetch customer details.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while fetching customer details.");
        }
    }

    @Override
    public Result addressUpdate(AddressDto addressDto, String authToken, HttpServletRequest request) {
        try {
            // Extracting fields from AddressDto

            String addressId = addressDto.getAddressId();
            String streetAddress1 = addressDto.getStreetAddress1();
            String streetAddress2 = addressDto.getStreetAddress2();
            String city = addressDto.getCity();
            String country = addressDto.getCountry();
            String countryArea = addressDto.getCountryArea();
            String postalCode = addressDto.getPostalCode();

            // Call the addressUpdateMutation method
            DataResult<?> addressUpdateMutation = CommonUtil.addressUpdateMutation(addressId, streetAddress1, 
                    streetAddress2, city, country, countryArea, postalCode, authToken, request);

            // Check if the mutation was successful
            if (addressUpdateMutation.isSuccess()) {
                // Return a success message
                return new SuccessResult("Address updated successfully");
            } else {
                // Return an error message with details of the failure
                return new ErrorResult("Failed to update address.");
            }
        } catch (Exception e) {
            // Return an error message in case of any unexpected exception
            return new ErrorResult("Failed to update address: An unexpected error occurred");
        }
    }

    @Override
    public Result addressDelete(String addressId, String authToken, HttpServletRequest request) {
        try {
            // Call the method to delete the address
            DataResult<String> addressDeleteResult = CommonUtil.addressDelete(addressId, authToken, request);

            // Check if the operation was successful
            if (addressDeleteResult.isSuccess()) {
                return new SuccessResult("Address deleted successfully.");
            } else {
                return new ErrorResult("Failed to delete address. Please try again later.");
            }
        } catch (Exception e) {
            return new ErrorResult("An unexpected error occurred while deleting the address. Please contact support for assistance.");
        }
    }

}
