/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.entities.AccountUser;
import com.AvMeditechInventory.repository.AccountUserRepository;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.AvMeditechInventory.service.CustomerAndSupplierService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class CustomerAndSupplierServiceImpl implements CustomerAndSupplierService {

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private AccountUserRepository accountUserRepository;

    @Override
    public DataResult<String> addCustomerAndSupplier(CustomerAndSupplierDto customerAndSupplierDto, String authToken, HttpServletRequest request) {
        try {
            String userType = customerAndSupplierDto.getUserType();

            if (userType == null || userType.trim().isEmpty()) {
                return new ErrorDataResult<>("User type must not be null or empty!");
            }

            Optional<AccountUser> accountUserOptional = this.accountUserRepository.findAccountUserByUserCode(customerAndSupplierDto.getUserCode());

            if (accountUserOptional.isPresent()) {
                return new ErrorDataResult<>("User with code " + customerAndSupplierDto.getUserCode() + " is already present. Please try another.");
            }

            DataResult<List<Map<String, Object>>> checkExistingUser = serviceDao.checkExistingUser(customerAndSupplierDto.
                    getMobileNumber(), customerAndSupplierDto.getUserCode());
            if (checkExistingUser.isSuccess()) {
                return new ErrorDataResult<>("Please enter unique mobile number and user code!");
            }

            if (null == customerAndSupplierDto.getEmail() || customerAndSupplierDto.getEmail().equals("")) {
                customerAndSupplierDto.setEmail(customerAndSupplierDto.getMobileNumber() + Constant.EMAIL_DOMAIN);
            }
            // Call accountRegisterMutation method to create customer
            DataResult<String> accountRegisterMutation = CommonUtil.accountRegisterMutation(customerAndSupplierDto.getEmail(),
                    Constant.AV_MEDITECH_INVENTORY_PASSWORD, customerAndSupplierDto.getFirstName(), customerAndSupplierDto.getLastName(),
                    customerAndSupplierDto.getMobileNumber(), customerAndSupplierDto.getCompanyName(), customerAndSupplierDto.getUserCode(),
                    customerAndSupplierDto.getUserType(), request);

            // Update user type with customer
            Result updateUserTypeWithCustomer = serviceDao.updateUserTypeAndCompanyName(customerAndSupplierDto.getEmail(), customerAndSupplierDto.getCompanyName(), customerAndSupplierDto.getUserType());

            // Update gender, dateOfBirth, panCardNumber, GstNumber, TinNumber
            Result updateGenderAndDateOfBirthAndPanCardNumberAndGstNumberAndTinNumber = serviceDao.updateGenderAndDateOfBirthAndPanCardNumberAndGstNumberAndTinNumber(customerAndSupplierDto.getEmail(),
                    customerAndSupplierDto.getPanNumber(), customerAndSupplierDto.getGstNumber(),
                    customerAndSupplierDto.getTinNumber(), customerAndSupplierDto.getUserCode());

            String customerId = accountRegisterMutation.getData();

            // Create address mutation
            DataResult<?> addressCreateMutation = CommonUtil.addressCreateMutation(customerId, 
                    customerAndSupplierDto.getStreetAddress1(), customerAndSupplierDto.getStreetAddress2(), 
                    customerAndSupplierDto.getCity(), customerAndSupplierDto.getCountry(), customerAndSupplierDto.getCountryArea(), 
                    customerAndSupplierDto.getPostalCode(), authToken, request);

            // Check each step's success and return appropriate messages
            if (accountRegisterMutation.isSuccess() && updateUserTypeWithCustomer.isSuccess()
                    && addressCreateMutation.isSuccess() && updateGenderAndDateOfBirthAndPanCardNumberAndGstNumberAndTinNumber.isSuccess()) {
                return new SuccessDataResult<>(customerId, "Customer created successfully");
            } else {
                // Check which step failed and return the corresponding error message
                if (!accountRegisterMutation.isSuccess()) {
                    return new ErrorDataResult<>(accountRegisterMutation.getMessage());
                } else if (!updateUserTypeWithCustomer.isSuccess()) {
                    return new ErrorDataResult<>("Failed to update user type with customer: ");
                } else {
                    return new ErrorDataResult<>("Failed to create address: ");
                }
            }
        } catch (Exception e) {
            // Return an error message for unexpected exceptions
            return new ErrorDataResult<>("An unexpected error occurred: ");
        }
    }

    @Override
    public DataResult<String> updateCustomerAndSupplier(CustomerAndSupplierDto customerAndSupplierDto, 
            String authToken, HttpServletRequest request) {
        try {

            String userType = customerAndSupplierDto.getUserType();
            if (userType == null || userType.trim().isEmpty()) {
                return new ErrorDataResult(null, "User type must not be null or empty");
            }

            // Use descriptive variable names
            Optional<AccountUser> existingUserByEmailAndCode = this.accountUserRepository
                    .findByEmailAndUserCode(customerAndSupplierDto.getEmail(), customerAndSupplierDto.getUserCode());

            if (!existingUserByEmailAndCode.isPresent()) {
                // If user is not found by email and user code, check by user code only
                Optional<AccountUser> existingUserByCode = this.accountUserRepository
                        .findAccountUserByUserCode(customerAndSupplierDto.getUserCode());

                if (existingUserByCode.isPresent()) {
                    // Return a descriptive error message
                    return new ErrorDataResult<>(
                            "A user with the code '" + customerAndSupplierDto.getUserCode() + "' already exists. Please use a different code or verify the details."
                    );
                }
            }

            String customerId = customerAndSupplierDto.getId();
            String customerFirstName = customerAndSupplierDto.getFirstName();
            String customerLastName = customerAndSupplierDto.getLastName();
            String customerEmail = customerAndSupplierDto.getEmail();
            String panCardNumber = customerAndSupplierDto.getPanNumber();
            String gstNumber = customerAndSupplierDto.getGstNumber();
            String tinNumber = customerAndSupplierDto.getTinNumber();

            // Perform customer update
            DataResult<?> customerUpdateMutation = CommonUtil.customerUpdateMutation(customerId, customerFirstName, 
                    customerLastName, customerEmail, authToken, request);
            List<HashMap<String, Object>> data = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            map.put("key", "mobileNo");
            map.put("value", customerAndSupplierDto.getMobileNumber());
            data.add(map);
            map = new HashMap<>();
            map.put("key", "userCode");
            map.put("value", customerAndSupplierDto.getUserCode());
            data.add(map);
            map = new HashMap<>();
            map.put("key", "userType");
            map.put("value", customerAndSupplierDto.getUserType());
            data.add(map);
            map = new HashMap<>();
            map.put("key", "companyName");
            map.put("value", customerAndSupplierDto.getCompanyName());
            data.add(map);
            CommonUtil.updateMetadata(data, customerId, authToken, request);
            Result updateMobileNumberWithStaff = serviceDao.updateMobileNumberWithStaff(customerEmail, customerAndSupplierDto.getMobileNumber());
            Result updateGenderAndDateOfBirthAndPanCardNumberAndGstNumberAndTinNumber = serviceDao.updateGenderAndDateOfBirthAndPanCardNumberAndGstNumberAndTinNumber(customerEmail,
                    panCardNumber, gstNumber, tinNumber, customerAndSupplierDto.getUserCode());
            Result updateCompanyName = serviceDao.updateCompanyNameAndUserType(customerEmail, customerAndSupplierDto.getCompanyName(), customerAndSupplierDto.getUserType());

            // Check if the update was successful
            if (customerUpdateMutation.isSuccess() && updateMobileNumberWithStaff.isSuccess() && updateCompanyName.isSuccess()
                    && updateGenderAndDateOfBirthAndPanCardNumberAndGstNumberAndTinNumber.isSuccess()) {
                return new SuccessDataResult<>(customerAndSupplierDto.getUserType(), "Customer information updated successfully.");
            } else {
                return new ErrorDataResult<>(null, "Failed to update customer information.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while updating customer information.");
        }
    }

    @Override
    public DataResult<List<CustomerAndSupplierDto>> customerList(String authToken, Integer pageNumber,
            String after, String isAsc, String mobileNo, String userCode, String companyName, HttpServletRequest request) {
        try {
            List<HashMap<String, Object>> data = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            if (!mobileNo.equals("")) {
                map.put("key", "mobileNo");
                map.put("value", mobileNo);
                data.add(map);
            }
            if (!userCode.equals("")) {
                map = new HashMap<>();
                map.put("key", "userCode");
                map.put("value", userCode);
                data.add(map);
            }
            if (!companyName.equals("")) {
                map = new HashMap<>();
                map.put("key", "companyName");
                map.put("value", companyName);
                data.add(map);
            }
            // Retrieve customer list
            DataResult<List<CustomerAndSupplierDto>> customerListQuery = CommonUtil.customerListQueryWithPaginated(pageNumber, 
                    authToken, after, isAsc, data, request);
            if (!customerListQuery.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve supplier list: " + customerListQuery.getMessage());
            }

            List<CustomerAndSupplierDto> customerAndSupplierDtoList = customerListQuery.getData();
            for (int i = 0; i < customerAndSupplierDtoList.size(); i++) {
                if (customerAndSupplierDtoList.get(i).getDealerType().equals(Constant.SUPPLIER)) {
                    customerAndSupplierDtoList.remove(i);
                    i = i - 1;
                }
            }

            // Retrieve company and email list
            DataResult<List<Map<String, Object>>> companyAndEmailListResult = this.serviceDao.getCompanyAndEmailList();
            if (!companyAndEmailListResult.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve company and email list: ");
            }

            List<Map<String, Object>> companyAndEmailList = companyAndEmailListResult.getData();

            // Assign company names to customers/suppliers based on email matching
            for (CustomerAndSupplierDto customerAndSupplierDto : customerAndSupplierDtoList) {
                String customerEmail = customerAndSupplierDto.getEmail();
                for (Map<String, Object> companyData : companyAndEmailList) {
                    String companyEmail = (String) companyData.get("email");
                    if (customerEmail != null && customerEmail.equals(companyEmail)) {
                        String companyName1 = (String) companyData.get("company_name");
                        customerAndSupplierDto.setCompanyName(companyName1);
                        customerAndSupplierDto.setUserCode((String) companyData.get("user_code"));
                        break; // Exit the loop once a match is found
                    }
                }
            }

            return new SuccessDataResult<>(customerAndSupplierDtoList, "Supplier list retrieved successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while retrieving the supplier list: ");
        }
    }

    @Override
    public DataResult<CustomerAndSupplierDto> getCustomerById(String customerId, String authToken, HttpServletRequest request) {
        try {

            DataResult<CustomerAndSupplierDto> userById = CommonUtil.getUserById(customerId, authToken, request);
            if (userById.isSuccess()) {
                Map<String, Object> map = serviceDao.getUserDataByEmail(userById.getData().getEmail()).getData();
                userById.getData().setSupplierId(String.valueOf((int) map.get("id")));
                return userById;
            } else {
                return new ErrorDataResult<>("Failed to fetch customer: ");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while fetching customer: ");
        }
    }

    @Override
    public Result customerDelete(String customerId, String authToken, HttpServletRequest request) {
        try {
            DataResult<String> customerDeleteResult = CommonUtil.customerDelete(customerId, authToken, request);
            if (customerDeleteResult.isSuccess()) {
                return new SuccessResult("Customer deleted successfully.");
            } else {
                return new ErrorResult("Failed to delete customer.");
            }
        } catch (Exception e) {
            return new ErrorResult("An unexpected error occurred while deleting the customer: ");
        }
    }

    @Override
    public DataResult<List<CustomerAndSupplierDto>> supplierList(String authToken, Integer pageNumber, String after,
            String isAsc, String mobileNo, String userCode, String companyName, HttpServletRequest request) {
        try {
            List<HashMap<String, Object>> data = new ArrayList<>();
            HashMap<String, Object> map = new HashMap<>();
            if (!mobileNo.equals("")) {
                map.put("key", "mobileNo");
                map.put("value", mobileNo);
                data.add(map);
            }
            if (!userCode.equals("")) {
                map = new HashMap<>();
                map.put("key", "userCode");
                map.put("value", userCode);
                data.add(map);
            }
            if (!companyName.equals("")) {
                map = new HashMap<>();
                map.put("key", "companyName");
                map.put("value", companyName);
                data.add(map);
            }
            // Retrieve customer list
            DataResult<List<CustomerAndSupplierDto>> customerListQuery = CommonUtil.customerListQueryWithPaginated(
                    pageNumber, authToken, after, isAsc, data, request);
            if (!customerListQuery.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve supplier list: ");
            }

            List<CustomerAndSupplierDto> customerAndSupplierDtoList = customerListQuery.getData();
            for (int i = 0; i < customerAndSupplierDtoList.size(); i++) {
                if (customerAndSupplierDtoList.get(i).getDealerType().equals(Constant.CUSTOMER)) {
                    customerAndSupplierDtoList.remove(i);
                    i = i - 1;
                }
            }

            // Retrieve company and email list
            DataResult<List<Map<String, Object>>> companyAndEmailListResult = this.serviceDao.getCompanyAndEmailList();
            if (!companyAndEmailListResult.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve company and email list: ");
            }

            List<Map<String, Object>> companyAndEmailList = companyAndEmailListResult.getData();

            // Assign company names to customers/suppliers based on email matching
            for (CustomerAndSupplierDto customerAndSupplierDto : customerAndSupplierDtoList) {
                String customerEmail = customerAndSupplierDto.getEmail();
                for (Map<String, Object> companyData : companyAndEmailList) {
                    String companyEmail = (String) companyData.get("email");
                    if (customerEmail != null && customerEmail.equals(companyEmail)) {
                        String companyName1 = (String) companyData.get("company_name");
                        customerAndSupplierDto.setCompanyName(companyName1);
                        customerAndSupplierDto.setUserCode((String) companyData.get("user_code"));
                        break; // Exit the loop once a match is found
                    }
                }
            }

            return new SuccessDataResult<>(customerAndSupplierDtoList, "Supplier list retrieved successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while retrieving the supplier list: ");
        }
    }

}
