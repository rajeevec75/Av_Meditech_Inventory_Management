/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.service.ChallanService;
import com.AvMeditechInventory.util.ServiceDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class ChallanServiceImpl implements ChallanService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public DataResult<Response> getUserDetailBySaleId(Integer saleId) {
        try {
            // Initialize response object
            Response response = new Response();

            // Retrieve user details by sale ID
            DataResult<Map<String, Object>> userDetailsBySaleIdResult = this.serviceDao.getUserDetailsBySaleId(saleId);

            // Check if user details retrieval was successful
            if (!userDetailsBySaleIdResult.isSuccess()) {
                return new ErrorDataResult<>(null, "Failed to retrieve user details for sale ID: " + saleId);
            }

            // Get user details data
            Map<String, Object> userData = userDetailsBySaleIdResult.getData();
            if (userData == null || userData.isEmpty()) {
                return new ErrorDataResult<>(null, "No user details found for sale ID: " + saleId);
            }

            // Populate the response object with user details
            response.setFirstName((String) userData.get("first_name"));
            response.setLastName((String) userData.get("last_name"));
            response.setEmail((String) userData.get("email"));
            response.setMobileNo((String) userData.get("mobile_no"));
            response.setCompanyName((String) userData.get("company_name"));
            response.setUserCompanyName((String) userData.get("cn"));
            response.setSeqId((Integer) userData.get("auto_sale_id"));
            response.setSaleDate((Date) userData.get("sale_date"));
            response.setRemarks((String) userData.get("remarks"));

            // Extract userId from the retrieved data
            Integer userId = (Integer) userData.get("id");
            String type = (String) userData.get("sale_type");
            response.setSaleType(type);
            if (type.equalsIgnoreCase("Internal Transfer")) {

                Optional<Integer> channelIdBySaleId = this.serviceDao.getChannelIdBySaleId(saleId);

                if (channelIdBySaleId.isPresent()) {
                    Integer channelId1 = channelIdBySaleId.get();
                    Optional<String> channelNameByChannelId = this.serviceDao.getChannelNameByChannelId(channelId1);
                    if (channelNameByChannelId.isPresent()) {
                        String channelName = channelNameByChannelId.get();
                        response.setCompanyName(channelName);
                    }

                }
            }

            // Retrieve user address details based on userId
            DataResult<Map<String, Object>> userAddressDetailsResult = this.serviceDao.getUserAddressDetails(userId);

            // Get user address data if available
            Map<String, Object> userAddressDetails = userAddressDetailsResult.getData();
            if (userAddressDetails != null && !userAddressDetails.isEmpty()) {
                // Populate the response object with address details
                response.setStreetAddress1((String) userAddressDetails.get("street_address_1"));
                response.setStreetAddress2((String) userAddressDetails.get("street_address_2"));
                response.setCity((String) userAddressDetails.get("city"));
                response.setPostalCode((String) userAddressDetails.get("postal_code"));
                response.setCountry((String) userAddressDetails.get("country"));
                response.setCountryArea((String) userAddressDetails.get("country_area"));
            } else {
                // Address is missing, but return user data
                response.setStreetAddress1(null);
                response.setStreetAddress2(null);
                response.setCity(null);
                response.setPostalCode(null);
                response.setCountry(null);
                response.setCountryArea(null);
            }

            // Return success with user details, even if address is null
            return new SuccessDataResult<>(response, "User details retrieved successfully for sale ID: " + saleId);

        } catch (Exception e) {
            // Return error message with exception details
            return new ErrorDataResult<>(null, "An error occurred while retrieving user details for sale ID: " + saleId + ". Error: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Response>> getDeliveryChallanBySaleId(Integer saleId) {
        try {
            DataResult<List<Map<String, Object>>> deliveryChallanBySaleIdResult = this.serviceDao.getDeliveryChallanBySaleId(saleId);
            if (deliveryChallanBySaleIdResult.getData().isEmpty()) {
                deliveryChallanBySaleIdResult = this.serviceDao.getDeliveryChallanBySale1Id(saleId);
                if (deliveryChallanBySaleIdResult.getData().isEmpty()) {
                    return new ErrorDataResult<>(null, "Failed to retrieve delivery challan details.");
                }
            }

            List<Map<String, Object>> data = deliveryChallanBySaleIdResult.getData();
            if (data == null || data.isEmpty()) {
                return new ErrorDataResult<>(null, "No delivery challan details found for the given sale ID.");
            }

            List<Response> responseList = new ArrayList<>();
            int quantity = 1;
            for (int i = 0; i < data.size(); i++) {
                Response response = new Response();
                response.setSaleId((Integer) data.get(i).get("purchase_item_serial_master_id"));
                response.setProductName((String) data.get(i).get("name"));
                response.setItemDescription((String) data.get(i).get("product_description"));
                response.setItemBarCode((String) data.get(i).get("item_barcode"));
                response.setItemSerialExpiry((Date) data.get(i).get("item_serial_expiry_date"));
                response.setSku((String) data.get(i).get("sku"));
                if (null != (String) data.get(i).get("item_serial_number")) {
                    response.setSerialNumber((String) data.get(i).get("item_serial_number"));
                    response.setQuantity(1);
                } else {
                    if (i != data.size() - 1) {
                        String batchNo = (String) data.get(i).get("item_batch");
                        String batchNo1 = (String) data.get(i + 1).get("item_batch");
                        if (null == batchNo) {
                            quantity = 1;
                        } else {
                            if (batchNo.equals(batchNo1)) {
                                quantity = quantity + 1;
                                continue;
                            }
                        }
                    }
                    response.setSerialNumber((String) data.get(i).get("item_batch"));
                    response.setQuantity(quantity);
                    quantity = 1;
                }
                responseList.add(response);
            }

            return new SuccessDataResult<>(responseList, "Delivery challan details retrieved successfully.");
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving delivery challan details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving delivery challan details.");
        }
    }

    @Override
    public DataResult<Response> getUserDetailByPurchaseId(Integer purchaseId) {
        try {
            // Retrieve user details from the DAO layer
            DataResult<Map<String, Object>> userDetailsBySaleIdResult = this.serviceDao.getUserDetailsByPurchaseId(purchaseId);

            // Check if the retrieval was successful
            if (!userDetailsBySaleIdResult.isSuccess()) {
                return new ErrorDataResult<>(null, "Failed to retrieve user details.");
            }

            // Extract the data map containing user details
            Map<String, Object> data = userDetailsBySaleIdResult.getData();
            if (data == null || data.isEmpty()) {
                return new ErrorDataResult<>(null, "No user details found for the given purchase ID.");
            }

            // Create a Response object and set each field from the data map
            Response response = new Response();
            response.setFirstName((String) data.get("first_name"));
            response.setLastName((String) data.get("last_name"));
            response.setEmail((String) data.get("email"));
            response.setMobileNo((String) data.get("mobile_no"));
            response.setCompanyName((String) data.get("company_name"));
            response.setUserCompanyName((String) data.get("cn"));
            response.setSeqId((Integer) data.get("auto_purchase_id"));  // Common use for challan page
            response.setSaleDate((Date) data.get("purchase_date")); // Common use for challan page
            response.setRemarks((String) data.get("remarks"));

            // Extract userId from the retrieved data
            Integer userId = (Integer) data.get("id");

            // Retrieve user address details based on userId
            DataResult<Map<String, Object>> userAddressDetailsResult = this.serviceDao.getUserAddressDetails(userId);

            // Get user address data if available
            Map<String, Object> userAddressDetails = userAddressDetailsResult.getData();
            if (userAddressDetails != null && !userAddressDetails.isEmpty()) {
                // Populate the response object with address details
                response.setStreetAddress1((String) userAddressDetails.get("street_address_1"));
                response.setStreetAddress2((String) userAddressDetails.get("street_address_2"));
                response.setCity((String) userAddressDetails.get("city"));
                response.setPostalCode((String) userAddressDetails.get("postal_code"));
                response.setCountry((String) userAddressDetails.get("country"));
                response.setCountryArea((String) userAddressDetails.get("country_area"));
            } else {
                // Address is missing, but return user data
                response.setStreetAddress1(null);
                response.setStreetAddress2(null);
                response.setCity(null);
                response.setPostalCode(null);
                response.setCountry(null);
                response.setCountryArea(null);
            }

            // Return a SuccessDataResult with the Response object
            return new SuccessDataResult<>(response, "User details retrieved successfully.");

        } catch (Exception e) {
            // Log the error message for debugging purposes
            System.err.println("An error occurred while retrieving user details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving user details.");
        }
    }

    @Override
    public DataResult<List<Response>> getDeliveryChallanByPurchaseId(Integer purchaseId) {
        try {
            // Retrieve delivery challan details from serviceDao
            DataResult<List<Map<String, Object>>> deliveryChallanByPurchaseIdResult = this.serviceDao.getDeliveryChallanByPurchaseId(purchaseId);

            // Check if retrieval was successful
            if (!deliveryChallanByPurchaseIdResult.isSuccess()) {
                return new ErrorDataResult<>(null, "Failed to retrieve delivery challan details.");
            }

            // Extract data from the result
            List<Map<String, Object>> data = deliveryChallanByPurchaseIdResult.getData();

            // Handle case where no data is found
            if (data == null || data.isEmpty()) {
                return new ErrorDataResult<>(null, "No delivery challan details found for the given purchase ID.");
            }

            // Map retrieved data to Response objects
            List<Response> responseList = new ArrayList<>();
            int quantity = 1;
            for (int i = 0; i < data.size(); i++) {
                Response response = new Response();
                response.setSaleId((Integer) data.get(i).get("purchase_item_serial_master_id"));
                response.setProductName((String) data.get(i).get("name"));
                response.setItemDescription((String) data.get(i).get("product_description"));
                response.setItemBarCode((String) data.get(i).get("item_barcode"));
                response.setItemSerialExpiry((Date) data.get(i).get("item_serial_expiry_date"));
                response.setSku((String) data.get(i).get("sku"));
                if (null != (String) data.get(i).get("item_serial_number")) {
                    response.setSerialNumber((String) data.get(i).get("item_serial_number"));
                    response.setQuantity(1);
                } else {
                    if (i != data.size() - 1) {
                        String batchNo = (String) data.get(i).get("item_batch");
                        String batchNo1 = (String) data.get(i + 1).get("item_batch");
                        if (null == batchNo) {
                            quantity = 1;
                        } else {
                            if (batchNo.equals(batchNo1)) {
                                quantity = quantity + 1;
                                continue;
                            }
                        }
                    }
                    response.setSerialNumber((String) data.get(i).get("item_batch"));
                    response.setQuantity(quantity);
                    quantity = 1;
                }
                responseList.add(response);
            }

            // Return success result with mapped Response objects
            return new SuccessDataResult<>(responseList, "Delivery challan details retrieved successfully.");
        } catch (Exception e) {
            // Log the error and return an error result
            System.err.println("An error occurred while retrieving delivery challan details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving delivery challan details.");
        }
    }

    @Override
    public DataResult<Response> getUserDetailBySaleReturnId(Integer saleReturnId) {
        try {
            // Retrieve user details from the DAO layer
            DataResult<Map<String, Object>> userDetailsBySaleIdResult = this.serviceDao.getUserDetailsBySaleReturnId(saleReturnId);

            // Check if the retrieval was successful
            if (!userDetailsBySaleIdResult.isSuccess()) {
                return new ErrorDataResult<>(null, "Failed to retrieve user details.");
            }

            // Extract the data map containing user details
            Map<String, Object> data = userDetailsBySaleIdResult.getData();
            if (data == null || data.isEmpty()) {
                return new ErrorDataResult<>(null, "No user details found for the given purchase ID.");
            }

            // Create a Response object and set each field from the data map
            Response response = new Response();
            response.setFirstName((String) data.get("first_name"));
            response.setLastName((String) data.get("last_name"));
            response.setEmail((String) data.get("email"));
            response.setMobileNo((String) data.get("mobile_no"));
            response.setCompanyName((String) data.get("company_name"));
            response.setUserCompanyName((String) data.get("cn"));
            response.setSeqId((Integer) data.get("auto_sale_return_id"));  // Common use for challan page
            response.setSaleDate((Date) data.get("return_date")); // Common use for challan page
            response.setRemarks((String) data.get("remarks"));

            // Extract userId from the retrieved data
            Integer userId = (Integer) data.get("id");

            // Retrieve user address details based on userId
            DataResult<Map<String, Object>> userAddressDetailsResult = this.serviceDao.getUserAddressDetails(userId);

            // Get user address data if available
            Map<String, Object> userAddressDetails = userAddressDetailsResult.getData();
            if (userAddressDetails != null && !userAddressDetails.isEmpty()) {
                // Populate the response object with address details
                response.setStreetAddress1((String) userAddressDetails.get("street_address_1"));
                response.setStreetAddress2((String) userAddressDetails.get("street_address_2"));
                response.setCity((String) userAddressDetails.get("city"));
                response.setPostalCode((String) userAddressDetails.get("postal_code"));
                response.setCountry((String) userAddressDetails.get("country"));
                response.setCountryArea((String) userAddressDetails.get("country_area"));
            } else {
                // Address is missing, but return user data
                response.setStreetAddress1(null);
                response.setStreetAddress2(null);
                response.setCity(null);
                response.setPostalCode(null);
                response.setCountry(null);
                response.setCountryArea(null);
            }

            // Return a SuccessDataResult with the Response object
            return new SuccessDataResult<>(response, "User details retrieved successfully.");

        } catch (Exception e) {
            // Log the error message for debugging purposes
            System.err.println("An error occurred while retrieving user details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving user details.");
        }
    }

    @Override
    public DataResult<List<Response>> getDeliveryChallanBySaleReturnId(Integer saleReturnId) {
        try {
            // Retrieve delivery challan details from serviceDao
            DataResult<List<Map<String, Object>>> deliveryChallanByPurchaseIdResult = this.serviceDao.getDeliveryChallanBySaleReturnId(saleReturnId);

            // Check if retrieval was successful
            if (!deliveryChallanByPurchaseIdResult.isSuccess()) {
                return new ErrorDataResult<>(null, "Failed to retrieve delivery challan details.");
            }

            // Extract data from the result
            List<Map<String, Object>> data = deliveryChallanByPurchaseIdResult.getData();

            // Handle case where no data is found
            if (data == null || data.isEmpty()) {
                return new ErrorDataResult<>(null, "No delivery challan details found for the given purchase ID.");
            }

            // Map retrieved data to Response objects
            List<Response> responseList = new ArrayList<>();
            int quantity = 1;
            for (int i = 0; i < data.size(); i++) {
                Response response = new Response();
                response.setSaleId((Integer) data.get(i).get("purchase_item_serial_master_id"));
                response.setProductName((String) data.get(i).get("name"));
                response.setItemDescription((String) data.get(i).get("product_description"));
                response.setItemBarCode((String) data.get(i).get("item_barcode"));
                response.setItemSerialExpiry((Date) data.get(i).get("item_serial_expiry_date"));
                response.setSku((String) data.get(i).get("sku"));
                if (null != (String) data.get(i).get("item_serial_number")) {
                    response.setSerialNumber((String) data.get(i).get("item_serial_number"));
                    response.setQuantity(1);
                } else {
                    if (i != data.size() - 1) {
                        String batchNo = (String) data.get(i).get("item_batch");
                        String batchNo1 = (String) data.get(i + 1).get("item_batch");
                        if (null == batchNo) {
                            quantity = 1;
                        } else {
                            if (batchNo.equals(batchNo1)) {
                                quantity = quantity + 1;
                                continue;
                            }
                        }
                    }
                    response.setSerialNumber((String) data.get(i).get("item_batch"));
                    response.setQuantity(quantity);
                    quantity = 1;
                }
                responseList.add(response);
            }

            // Return success result with mapped Response objects
            return new SuccessDataResult<>(responseList, "Delivery challan details retrieved successfully.");
        } catch (Exception e) {
            // Log the error and return an error result
            System.err.println("An error occurred while retrieving delivery challan details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving delivery challan details.");
        }
    }

    @Override
    public DataResult<List<Response>> getDeliveryChallanQuantityBySaleId(Integer saleId) {
        try {
            DataResult<List<Map<String, Object>>> deliveryChallanQuantityBySaleId = this.serviceDao.getDeliveryChallanQuantityBySaleId(saleId);
            List<Map<String, Object>> data = deliveryChallanQuantityBySaleId.getData();

            List<Response> responseList = data.stream().map(this::mapToResponse).collect(Collectors.toList());

            return new SuccessDataResult<>(responseList, "Delivery challan details retrieved successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the delivery challan: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Response>> getDeliveryChallanQuantityBySaleReturnId(Integer saleReturnId) {
        try {
            DataResult<List<Map<String, Object>>> deliveryChallanQuantityBySaleReturnId = this.serviceDao.getDeliveryChallanQuantityBySaleReturnId(saleReturnId);
            List<Map<String, Object>> data = deliveryChallanQuantityBySaleReturnId.getData();

            List<Response> responseList = data.stream().map(this::mapToResponse).collect(Collectors.toList());

            return new SuccessDataResult<>(responseList, "Delivery challan details retrieved successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the delivery challan: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Response>> getDeliveryChallanQuantityByPurchaseId(Integer purchaseId) {
        try {
            DataResult<List<Map<String, Object>>> deliveryChallanQuantityByPurchaseId = this.serviceDao.getDeliveryChallanQuantityByPurchaseId(purchaseId);
            List<Map<String, Object>> data = deliveryChallanQuantityByPurchaseId.getData();

            List<Response> responseList = data.stream().map(this::mapToResponse).collect(Collectors.toList());

            return new SuccessDataResult<>(responseList, "Delivery challan details retrieved successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the delivery challan: " + e.getMessage());
        }
    }

    private Response mapToResponse(Map<String, Object> data) {
        Response response = new Response();

        if (data.containsKey("sale_id")) {
            response.setId((Integer) data.get("sale_id"));
            response.setSku((String) data.get("sku"));
        } else if (data.containsKey("id")) {
            response.setId((Integer) data.get("id"));
            response.setSku((String) data.get("sku"));
        } else if (data.containsKey("purchase_id")) {
            response.setId((Integer) data.get("purchase_id"));
            response.setSku((String) data.get("sku"));
        }

        try {
            response.setQuantity((Integer) data.get("quantity"));
        } catch (Exception e) {
            response.setQuantity(((Long) data.get("quantity")).intValue());
        }

        response.setProductName((String) data.get("name"));
        response.setItemDescription((String) data.get("description_plaintext"));

        return response;
    }

}
