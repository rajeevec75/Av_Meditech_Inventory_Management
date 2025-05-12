/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.PurchaseImportResponse;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.PurchaseItemSerialMaster;
import com.AvMeditechInventory.entities.PurchaseMaster;
import com.AvMeditechInventory.entities.PurchaseMasterItems;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.PurchaseService;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public DataResult<List<String>> placeOfSupplyList() {
        try {
            DataResult<List<Map<String, Object>>> allUsers = this.serviceDao.getALLUsers();
            if (allUsers.isSuccess()) {
                List<Map<String, Object>> userDataList = allUsers.getData();

                // Assuming each user has a "place_of_supply" field
                List<String> placeOfSupplyList = new ArrayList<>();
                for (Map<String, Object> userData : userDataList) {
                    String placeOfSupply = (String) userData.get("place_of_supply");
                    if (placeOfSupply != null) {
                        // Assuming place_of_supply is a comma-separated string, split it into a list
                        List<String> userPlaceOfSupplyList = Arrays.asList(placeOfSupply.split("\\s*,\\s*"));
                        placeOfSupplyList.addAll(userPlaceOfSupplyList);
                    }
                }

                // Return the list of place of supply data
                return new SuccessDataResult<>(placeOfSupplyList, "Place of supply list retrieved successfully");
            } else {
                // User data retrieval failed
                return new ErrorDataResult<>("Failed to retrieve place of supply list: " + allUsers.getMessage());
            }
        } catch (Exception e) {
            // Exception occurred
            return new ErrorDataResult<>("Failed to retrieve place of supply list: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<CustomerAndSupplierDto>> getUserDataByUserType() {
        try {
            // Prepare the list of user types to search for
            List<String> userTypes = Arrays.asList(Constant.SUPPLIER, Constant.BOTH);
            DataResult<List<Map<String, Object>>> userDataByUserTypeResult = this.serviceDao.getUserDataByUserType(userTypes);
            if (userDataByUserTypeResult.isSuccess()) {
                List<Map<String, Object>> userDataList = userDataByUserTypeResult.getData();

                List<CustomerAndSupplierDto> customerAndSupplierDtoList = new ArrayList<>();
                for (Map<String, Object> userData : userDataList) {
                    Integer userId = (Integer) userData.get("id");
                    String userIdString = userId != null ? String.valueOf(userId) : null;

                    String firstName = (String) userData.get("first_name");
                    String lastName = (String) userData.get("last_name");
                    String companyName = (String) userData.get("company_name");

                    if (userIdString != null && firstName != null && lastName != null) {
                        CustomerAndSupplierDto customerAndSupplierDto = new CustomerAndSupplierDto(userIdString, firstName, lastName, companyName);
                        customerAndSupplierDto.setUserCode((String) userData.get("user_code"));
                        customerAndSupplierDtoList.add(customerAndSupplierDto);
                    }
                }

                return new SuccessDataResult<>(customerAndSupplierDtoList, "User data retrieved successfully");
            } else {
                return new ErrorDataResult<>(null, "Failed to retrieve user data: " + userDataByUserTypeResult.getMessage());
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while retrieving user data: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<ProductDto>> getProductList() {
        try {
            DataResult<List<Map<String, Object>>> productListResult = this.serviceDao.getProductList();
            if (productListResult.isSuccess()) {
                List<Map<String, Object>> productDataList = productListResult.getData();

                List<ProductDto> productList = new ArrayList<>();
                for (Map<String, Object> productData : productDataList) {
                    Integer productIdInteger = (Integer) productData.get("id");
                    String productId = productIdInteger != null ? productIdInteger.toString() : null;

                    String productName = (String) productData.get("name");

                    if (productId != null && productName != null) {
                        ProductDto product = new ProductDto(productId, productName);
                        productList.add(product);
                    }
                }

                return new SuccessDataResult<>(productList, "Product list retrieved successfully");
            } else {
                return new ErrorDataResult<>("Failed to retrieve product list: " + productListResult.getMessage());
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while retrieving product list: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Response>> getPurchaseList(Integer channelId, Integer pageNumber, Integer pageSize,
            Date startDate, Date endDate, String companyName, String productName) {
        try {
            DataResult<List<Map<String, Object>>> purchaseDataResult = serviceDao.fetchPurchaseList(channelId, pageNumber, pageSize, startDate, endDate, companyName, productName);

            if (purchaseDataResult.isSuccess()) {
                List<Map<String, Object>> purchaseDataList = purchaseDataResult.getData();
                List<Response> responseList = new ArrayList<>();

                for (Map<String, Object> purchaseData : purchaseDataList) {
                    // Get values from the map
                    Integer purchaseId = (Integer) purchaseData.get("purchase_id");
                    Integer autoPurchaseId = (Integer) purchaseData.get("auto_purchase_id");
                    String company = (String) purchaseData.get("company_name");
                    String purchaseDate = purchaseData.get("purchase_date") != null
                            ? new SimpleDateFormat("yyyy-MM-dd").format((Date) purchaseData.get("purchase_date"))
                            : null;
                    String referenceNumber = (String) purchaseData.get("reference_no");
                    String remarks = (String) purchaseData.get("remarks");
                    Long totalQuantity = (Long) purchaseData.get("total_quantity");

                    // Set values to the Response object
                    Response response = new Response();
                    response.setId(purchaseId);
                    response.setAutoId(autoPurchaseId);
                    response.setCompanyName(company);
                    response.setDate(purchaseDate);
                    response.setReferenceNumber(referenceNumber);
                    response.setRemarks(remarks);
                    response.setAmount(BigDecimal.ZERO);
                    response.setGst(BigDecimal.ZERO);
                    response.setShipping(BigDecimal.ZERO);
                    response.setTotalAmount(BigDecimal.ZERO);
                    response.setQuantity(totalQuantity != null ? totalQuantity.intValue() : 0);

                    responseList.add(response);
                }

                return new SuccessDataResult<>(responseList, "Purchase list retrieved successfully.");
            } else {
                return new ErrorDataResult<>("Failed to retrieve purchase list: " + purchaseDataResult.getMessage());
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to retrieve purchase list: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Integer> processPurchaseProductCreate(PurchaseMaster purchase, String[] addedRowsData,
            String[] purchaseSerialData, String formattedDate, String authToken, int channelId, int purchaseUserId,
            HttpServletRequest request, HttpSession httpSession) {
        try {
            //Check Barcode
            // List to hold duplicate barcodes
            List<String> duplicateBarCodes = new ArrayList<>();

            // List to hold already seen barcodes (persisting across iterations)
            List<String> existingBarcodes = new ArrayList<>();
            String barcode = "'";
            for (String serialNumberData : purchaseSerialData) {
                String[] serialNumberParts = serialNumberData.split("\\|");
                if (serialNumberParts.length >= 4) {
                    barcode = barcode + serialNumberParts[1] + "', '";

                    // Check for duplicate barcode
                    String checkBarCode = serialNumberParts[1];

                    // Check for duplicate barcode
                    List<String> result = checkForDuplicateBarcode(checkBarCode, existingBarcodes);

                    // If the barcode is found, append it to the list
                    if (result != null && !result.isEmpty()) {
                        duplicateBarCodes.add(result.get(0));  // Add duplicate barcode to the list
                    }

                    String expiryDate = serialNumberParts[3];

                    // Check if expiryDate matches the pattern yyyy-MM-dd before parsing
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate.parse(expiryDate, formatter);
                    } catch (Exception e) {
                        return new ErrorDataResult("Invalid expiry date format: " + expiryDate + ". Expected format: yyyy-MM-dd.");
                    }
                }

            }
            // Return error if duplicates are found
            if (!duplicateBarCodes.isEmpty()) {
                return new ErrorDataResult<>("Duplicate barcode detected: " + duplicateBarCodes);
            }
            if (purchaseSerialData.length > 0) {
                // Truncate the barcode by removing the last 3 characters
                barcode = barcode.substring(0, barcode.length() - 3);

                // Check if the barcode already exists in the database
                DataResult<List<Map<String, Object>>> purData = serviceDao.checkBarcode(barcode);

                // If a matching barcode is found, return an error with a proper message
                if (!purData.getData().isEmpty()) {
                    List<Map<String, Object>> data = purData.getData();
                    StringBuilder duplicateBarcodes = new StringBuilder();

                    // Loop through the list of maps and extract barcode information
                    for (Map<String, Object> item : data) {
                        // Get the barcode from each map (adjust key according to your data structure)
                        String itemBarcode = (String) item.get("item_barcode");

                        // Append the barcode to the StringBuilder for error message
                        duplicateBarcodes.append(itemBarcode).append(", ");
                    }

                    // Remove the last comma and space
                    if (duplicateBarcodes.length() > 0) {
                        duplicateBarcodes.setLength(duplicateBarcodes.length() - 2);
                    }

                    // Return an error message with all duplicate barcodes found
                    return new ErrorDataResult<>("Failed to insert purchase master due to duplicate barcode(s): " + duplicateBarcodes.toString());
                }
            }

            // Initialization and parsing
            Integer userId = purchase.getUserId();
            String purchaseStatus = "ACTIVE";
            BigDecimal subTotalAmount = BigDecimal.ZERO;
            BigDecimal gstAmount = BigDecimal.ZERO;
            BigDecimal discountAmount = BigDecimal.ZERO;
            BigDecimal shippingAmount = BigDecimal.ZERO;
            BigDecimal grandTotal = BigDecimal.ZERO;
            String referenceNo = purchase.getReferenceNo();
            String remarks = purchase.getRemarks();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(formattedDate);

            // Insert into purchase_master
            DataResult<Integer> insertPurchaseMasterResult = this.serviceDao.insert_purchase_master(userId,
                    purchaseStatus, subTotalAmount, gstAmount, discountAmount, shippingAmount, grandTotal,
                    referenceNo, date, channelId, remarks, purchaseUserId);
            if (!insertPurchaseMasterResult.isSuccess()) {
                return new ErrorDataResult<>("Insertion of purchase master failed. Please check the input data");

            }
            Integer purchaseId = insertPurchaseMasterResult.getData();

            // Process each row in addedRowsData
            for (String rowData : addedRowsData) {
                String[] parts = rowData.split("\\|");
                if (parts.length < 3) {
                    return new ErrorDataResult<>("Invalid data format in addedRowsData.");
                }

                String productId = parts[0];
                BigDecimal costPrice = BigDecimal.ZERO;
                int quantity = Integer.parseInt(parts[2]);
                BigDecimal discount = BigDecimal.ZERO;

                ProductDto product = new ProductDto();

                try {
                    // Check if productId is an integer
                    int productIdInt = Integer.parseInt(productId);

                    // If parsing succeeds, fetch the product details from serviceDao
                    Map<String, Object> result = this.serviceDao.getProductDetails(productIdInt).getData().get(0);

                    // Call the helper method to set product details from a Map
                    setProductDetailsFromMap(product, result, productId);

                } catch (NumberFormatException e) {
                    // If parsing fails, assume productId is a string and handle it
                    DataResult<ProductDto> dataResultProduct = CommonUtil.getProductById(productId, authToken, request);

                    // Call the helper method to set product details from ProductDto
                    setProductDetailsFromDto(product, dataResultProduct.getData(), productId);
                }

                // Get product ID from slug
                DataResult<Integer> productBySlugResult = this.serviceDao.getProductBySlug(product.getSlug());
                if (!productBySlugResult.isSuccess()) {
                    return new ErrorDataResult<>(productBySlugResult.getMessage());
                }
                Integer productIdInteger = productBySlugResult.getData();

                // Calculate values
                BigDecimal unitPrice = costPrice;
                BigDecimal gstPercent = BigDecimal.ZERO;
                BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
                BigDecimal totalAmountAfterDiscount = (unitPrice.subtract(discount)).multiply(BigDecimal.valueOf(quantity));
                BigDecimal gstAmountForItem = totalAmountAfterDiscount.multiply(gstPercent).divide(new BigDecimal("100"));
                BigDecimal totalAmountIncludingGST = totalAmountAfterDiscount.add(gstAmountForItem);

                // Update totals for purchase_master
                subTotalAmount = subTotalAmount.add(subTotal);
                discountAmount = discountAmount.add(discount);
                gstAmount = gstAmount.add(gstAmountForItem);
                grandTotal = grandTotal.add(totalAmountIncludingGST);

                // Insert into purchase_master_items
                DataResult<Integer> insertPurchaseMasterItemsResult = this.serviceDao.insert_purchase_master_items(
                        purchaseId, productIdInteger, quantity, unitPrice, discount, gstPercent, gstAmountForItem, totalAmountIncludingGST
                );
                if (!insertPurchaseMasterItemsResult.isSuccess()) {
                    return new ErrorDataResult<>("Insertion of purchase master items failed. Please check the input data:");

                }
                Integer purchaseMasterItemsId = insertPurchaseMasterItemsResult.getData();

                if (product.isTrackingSerialNo()) {
                    // Validate and process serial number data for the product
                    List<PurchaseItemSerialMaster> serialDataList = getSerialDataForProduct(purchaseSerialData, productId);
                    if (serialDataList.size() != quantity) {
                        return new ErrorDataResult<>("Quantity does not match the number of serial numbers provided for product ID: " + productId);
                    }
                    // Insert serial data
                    for (PurchaseItemSerialMaster serialData : serialDataList) {
                        DataResult<Integer> insert_purchase_item_serial_master = this.serviceDao.insert_purchase_item_serial_master(
                                purchaseMasterItemsId, purchaseStatus, serialData.getItemSerialNumber(), serialData.getItemBarcode(),
                                serialData.getItemSerialExpiryDate(), null, null, channelId); // Assuming sale_id is null
                        if (!insert_purchase_item_serial_master.isSuccess()) {
                            return new ErrorDataResult<>("Insertion of purchase item serial master failed. Please check the input data:");
                        }
                        Integer purchaseItemSerialMasterId = insert_purchase_item_serial_master.getData();
                        DataResult<Integer> insertStockMovmentHistory = this.serviceDao.insertStockMovmentHistory(purchaseItemSerialMasterId, productIdInteger, Constant.ACTION_TYPE_PURCHASE, date, purchaseId);

                        if (!insertStockMovmentHistory.isSuccess()) {
                            return new ErrorDataResult<>("Failed to insert stock movment history.");
                        }
                    }
                } else {
                    if (product.isBatchSerialNo()) {
                        String batchExpiry = parts[9];
                        Date batchExpiryDate = simpleDateFormat.parse(batchExpiry);
                        String batchSerialNo = parts[10];
                        for (int i = 0; i < quantity; i++) {
                            this.serviceDao.insert_purchase_item_serial_master(purchaseMasterItemsId, purchaseStatus,
                                    null, product.getProductSku(), batchExpiryDate, null, batchSerialNo, channelId); // Assuming sale_id is null
                        }
                    } else {
                        for (int i = 0; i < quantity; i++) {
                            this.serviceDao.insert_purchase_item_serial_master(purchaseMasterItemsId, purchaseStatus,
                                    null, product.getProductSku(), null, null, null, channelId); // Assuming sale_id is null
                        }
                    }
                }
            }

            // Include shipping amount in the grand total
            grandTotal = grandTotal.add(shippingAmount);
            DataResult<Integer> purSeq = null;
            String storeName = (String) httpSession.getAttribute("selectedStoreName");
            if (storeName != null && storeName.equals("AV MEDITECH PVT LTD KAITHAL")) {
                purSeq = serviceDao.getAutoPurchaseAvtSeq();
            } else if (storeName != null && storeName.equals("LENSE HOME KAITHAL")) {
                purSeq = serviceDao.getAutoPurchaseLhtSeq();
            } else if (storeName != null && storeName.equals("AV MEDITECH GURUGRAM")) {
                purSeq = serviceDao.getAutoPurchaseSeq();
            } else if (storeName != null && storeName.equals("AV MEDITECH KAITHAL")) {
                purSeq = serviceDao.getAutoPurchaseAmkSeq();
            }

            // Update the purchase_master with calculated totals
            DataResult<Integer> updatePurchaseMaster = this.serviceDao.update_purchase_master(purchaseId, subTotalAmount, gstAmount,
                    discountAmount, shippingAmount, grandTotal, purSeq.getData());
            if (!updatePurchaseMaster.isSuccess()) {
                return new ErrorDataResult<>("Purchase master update failed. Ensure the record exists and data is valid.");
            }

            return new SuccessDataResult<>(purchaseId, "Products have been purchased and recorded successfully.");
        } catch (NumberFormatException e) {
            return new ErrorDataResult<>("Invalid format for quantity or discount");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while processing the purchase");
        }
    }

    @Override
    public PurchaseImportResponse importPurchaseProduct(MultipartFile file1, HttpSession httpSession, Integer pageNumber, HttpServletRequest request) {
        String authToken = (String) httpSession.getAttribute("token");
        PurchaseImportResponse resp = new PurchaseImportResponse();
        List<ProductDto> productPurchase = new ArrayList<>();
        ArrayList<String> serialPurchase = new ArrayList<>();
        if (!file1.isEmpty()) {
            try {
                File convFile = File.createTempFile("temp", null);
                file1.transferTo(convFile);
                String serResp = "";
                //DataResult<List<ProductDto>> productListQuery = CommonUtil.importPurchaseProductListQuery(pageNumber, authToken, "", "next");
                DataResult<List<ProductDto>> productListQuery;
                productListQuery = CommonUtil.importPurchaseProductListQuery(pageNumber, authToken, "", "next", request);
                List<ProductDto> productDtoList = productListQuery.getData();

                String lastCursor = null;

                while (true) {
                    // Break the loop if no more products are found
                    if (productDtoList.isEmpty()) {
                        break;
                    }

                    // Get the cursor of the last product in the current list
                    ProductDto lastProduct = productDtoList.get(productDtoList.size() - 1);
                    lastCursor = lastProduct.getCursor();

                    // Fetch the next page of products using the last product's cursor
                    DataResult<List<ProductDto>> importPurchaseProductListQuery = CommonUtil.importPurchaseProductListQuery(pageNumber, authToken, lastCursor, "next", request);
                    List<ProductDto> nextProductDtos = importPurchaseProductListQuery != null ? importPurchaseProductListQuery.getData() : null;

                    // Check if nextProductDtos is null or empty
                    if (nextProductDtos == null || nextProductDtos.isEmpty()) {
                        break;
                    }

                    // Add the new products to the main list
                    productDtoList.addAll(nextProductDtos);
                }

                // Print the cursor of the last product fetched
                System.out.println("Cursor of the last product: " + lastCursor);

                int count = 0;
                try ( BufferedReader reader = new BufferedReader(new FileReader(convFile));  CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
                    for (CSVRecord csvRecord : csvParser) {
                        // String productName = csvRecord.get("Product Name");
                        try {
                            String productSku = csvRecord.get("Product Sku");
                            String barcode = csvRecord.get("Barcode");
                            String serialNo = csvRecord.get("Serial Number");

                            String expiryDate = csvRecord.get("Expiry Date");

                            ProductDto product = new ProductDto();
                            boolean isEntry = false;
                            for (int i = 0; i < productListQuery.getData().size(); i++) {
                                if (productListQuery.getData().get(i).getProductSku().equals(productSku)) {
                                    product = productListQuery.getData().get(i);
                                    isEntry = true;
                                }
                            }
                            if (isEntry) {
                                boolean isEntryNew = true;
                                if (!productPurchase.isEmpty()) {
                                    for (int i = 0; i < productPurchase.size(); i++) {
                                        ProductDto product1 = productPurchase.get(i);
                                        if (product.getProductId() == null ? product1.getProductId() == null : product.getProductId().equals(product1.getProductId())) {
                                            productPurchase.get(i).setQuantity(product1.getQuantity() + 1);
                                            isEntryNew = false;
                                        }
                                    }
                                }

                                if (isEntryNew) {
                                    product.setQuantity(1);
                                    productPurchase.add(product);
                                }
                                if (product.isTrackingSerialNo()) {
                                    serialPurchase.add(product.getProductId() + "|" + barcode + "|" + serialNo + "|" + expiryDate + "|" + product.getSnoPattern() + "|" + count);
                                    count++;
                                }
                            } else {
                                serResp = serResp + serialNo + ", ";
                            }
                        } catch (Exception e) {
                        }
                    }
                    resp.setProductPurchase(productPurchase);
                    resp.setSerialPurchase(serialPurchase);
                    resp.setSerResp(serResp);
                }
                convFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resp;
    }

    private List<PurchaseItemSerialMaster> getSerialDataForProduct(String[] purchaseSerialData, String productId) throws ParseException {
        List<PurchaseItemSerialMaster> serialDataList = new ArrayList<>();
        Set<String> uniqueSerialNumbers = new HashSet<>();  // To track unique serial numbers

        for (String serialNumberData : purchaseSerialData) {
            String[] serialNumberParts = serialNumberData.split("\\|");
            if (serialNumberParts.length >= 4 && serialNumberParts[0].equals(productId)) {
                String productbarCode = serialNumberParts[1];
                String itemSerialNumberInt = serialNumberParts[2];

                // Check if the serial number is unique
                if (uniqueSerialNumbers.contains(itemSerialNumberInt)) {
                    // If not unique, continue to the next iteration
                    continue;
                }

                String itemExpiryDate = serialNumberParts[3];
                Date date = null;
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = simpleDateFormat.parse(itemExpiryDate);
                } catch (Exception e) {
                }

                PurchaseItemSerialMaster purchaseItemSerialMaster = new PurchaseItemSerialMaster();
                purchaseItemSerialMaster.setPurchaseMasterId(0);
                purchaseItemSerialMaster.setStatus("ACTIVE");
                purchaseItemSerialMaster.setItemBarcode(productbarCode); // Uncomment and set barcode if needed
                purchaseItemSerialMaster.setItemSerialNumber(itemSerialNumberInt);
                purchaseItemSerialMaster.setItemSerialExpiryDate(date);

                serialDataList.add(purchaseItemSerialMaster);
                uniqueSerialNumbers.add(itemSerialNumberInt);  // Add to the set of unique serial numbers
            }
        }

        return serialDataList;
    }

    @Override
    public DataResult<Response> get_purchase_details_by_id(Integer id, int channelId) {
        try {
            // Retrieve purchase master details
            DataResult<Map<String, Object>> purchaseMasterDetailsResult = this.serviceDao.get_purchase_master_details_by_purchase_id(id, channelId);

            if (!purchaseMasterDetailsResult.isSuccess()) {
                // Return error if unable to retrieve purchase master details
                return new ErrorDataResult<>(null, "Failed to retrieve purchase master details: " + purchaseMasterDetailsResult.getMessage());
            }

            PurchaseMaster purchaseMaster = mapToPurchaseMaster(purchaseMasterDetailsResult.getData());

            // Retrieve purchase master items details
            DataResult<List<Map<String, Object>>> purchaseMasterItemsDetailsResult = this.serviceDao.get_purchase_master_items_details_by_purchase_id(id);

            if (!purchaseMasterItemsDetailsResult.isSuccess()) {
                // Return error if unable to retrieve purchase master items details
                return new ErrorDataResult<>(null, "Failed to retrieve purchase master items details: " + purchaseMasterItemsDetailsResult.getMessage());
            }

            List<PurchaseMasterItems> purchaseMasterItemsList = mapToPurchaseMasterItemsList(purchaseMasterItemsDetailsResult.getData());

            List<PurchaseItemSerialMaster> purchaseItemSerialMasters = new ArrayList<>();
            for (PurchaseMasterItems purchaseMasterItem : purchaseMasterItemsList) {
                if (purchaseMasterItem.isTrackingSerialNo()) {
                    Integer purchaseMasterId = purchaseMasterItem.getId();

                    // Retrieve purchase item serial master details using the purchaseMasterId
                    DataResult<List<Map<String, Object>>> purchaseItemSerialMasterDetailsResult = this.serviceDao.get_purchase_item_serial_master_details_by_purchase_master_id(purchaseMasterId);

                    if (!purchaseItemSerialMasterDetailsResult.isSuccess()) {
                        // Log error and continue to the next iteration
                        System.err.println("Failed to retrieve purchase item serial master details: " + purchaseItemSerialMasterDetailsResult.getMessage());
                        continue;
                    }

                    List<Map<String, Object>> purchaseItemSerialMasterDetails = purchaseItemSerialMasterDetailsResult.getData();

                    purchaseItemSerialMasterDetails.stream().map(detail -> {
                        PurchaseItemSerialMaster purchaseItemSerialMaster = new PurchaseItemSerialMaster();
                        purchaseItemSerialMaster.setPurchaseItemSerialMasterId((Integer) detail.get("purchase_item_serial_master_id"));
                        purchaseItemSerialMaster.setPurchaseMasterId((Integer) detail.get("purchase_master_id"));
                        purchaseItemSerialMaster.setItemBarcode((String) detail.get("item_barcode"));
                        purchaseItemSerialMaster.setItemSerialNumber((String) detail.get("item_serial_number"));
                        purchaseItemSerialMaster.setProductId(purchaseMasterItem.getProductId());
                        try {
                            java.sql.Date itemSerialExpiryDate = (java.sql.Date) detail.get("item_serial_expiry_date");
                            purchaseItemSerialMaster.setItemSerialExpiryDate(itemSerialExpiryDate);
                            // Convert java.sql.Date to String using SimpleDateFormat
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = dateFormat.format(itemSerialExpiryDate);
                            purchaseItemSerialMaster.setExpiryDate(dateString);
                        } catch (Exception e) {
                        }
                        return purchaseItemSerialMaster;
                    }).forEachOrdered(purchaseItemSerialMaster -> {
                        purchaseItemSerialMasters.add(purchaseItemSerialMaster);
                    });
                }
            }

            // Construct and return response
            Response response = new Response();
            response.setPurchaseMasters(purchaseMaster);
            response.setPurchaseMasterItemses(purchaseMasterItemsList);
            response.setPurchaseItemSerialMasters(purchaseItemSerialMasters);
            return new SuccessDataResult<>(response, "Purchase details retrieved successfully");

        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "An error occurred while retrieving purchase details: " + e.getMessage());
        }
    }

    // Helper method to map purchase master details to PurchaseMaster object
    private PurchaseMaster mapToPurchaseMaster(Map<String, Object> purchaseMasterDetails) {
        PurchaseMaster purchaseMaster = new PurchaseMaster();
        Integer purchaseId = (Integer) purchaseMasterDetails.get("purchase_id");
        Long purchaseIdLong = Long.valueOf(purchaseId);
        purchaseMaster.setPurchaseId(purchaseIdLong);
        purchaseMaster.setUserId((Integer) purchaseMasterDetails.get("user_id"));
        purchaseMaster.setPurchaseStatus((String) purchaseMasterDetails.get("purchase_status"));
        purchaseMaster.setSubTotalAmount((BigDecimal) purchaseMasterDetails.get("sub_total_amount"));
        purchaseMaster.setGstAmount((BigDecimal) purchaseMasterDetails.get("gst_amount"));
        purchaseMaster.setDiscountAmount((BigDecimal) purchaseMasterDetails.get("discount_amount"));
        purchaseMaster.setShippingAmount((BigDecimal) purchaseMasterDetails.get("shipping_amount"));
        purchaseMaster.setGrandTotal((BigDecimal) purchaseMasterDetails.get("grand_total"));
        purchaseMaster.setReferenceNo((String) purchaseMasterDetails.get("reference_no"));
        purchaseMaster.setRemarks((String) purchaseMasterDetails.get("remarks"));
        java.sql.Date purchaseDate = (java.sql.Date) purchaseMasterDetails.get("purchase_date");
        purchaseMaster.setPurchaseDate(purchaseDate);
        purchaseMaster.setCompanyName((String) purchaseMasterDetails.get("company_name"));
        purchaseMaster.setPurSeqId((Integer) purchaseMasterDetails.get("auto_purchase_id"));
        return purchaseMaster;
    }

    // Helper method to map purchase master items details to a list of PurchaseMasterItems objects
    private List<PurchaseMasterItems> mapToPurchaseMasterItemsList(List<Map<String, Object>> purchaseMasterItemsDetails) {
        List<PurchaseMasterItems> purchaseMasterItemsList = new ArrayList<>();
        purchaseMasterItemsDetails.stream().map(itemDetail -> {
            PurchaseMasterItems purchaseMasterItems = new PurchaseMasterItems();
            purchaseMasterItems.setId((Integer) itemDetail.get("id"));
            purchaseMasterItems.setPurchaseId((Integer) itemDetail.get("purchase_id"));
            purchaseMasterItems.setProductId((Integer) itemDetail.get("product_id"));
            purchaseMasterItems.setQuantity((Integer) itemDetail.get("quantity"));
            purchaseMasterItems.setCostPrice((BigDecimal) itemDetail.get("cost_price"));
            purchaseMasterItems.setDiscount((BigDecimal) itemDetail.get("discount"));
            purchaseMasterItems.setGstPercent((BigDecimal) itemDetail.get("gst_percent"));
            purchaseMasterItems.setGstAmount((BigDecimal) itemDetail.get("gst_Amount"));
            purchaseMasterItems.setTotalAmount((BigDecimal) itemDetail.get("total_amount"));
            purchaseMasterItems.setProductName((String) itemDetail.get("name"));
            boolean trackingSerialNumber = (boolean) itemDetail.get("tracking_serial_no");
            purchaseMasterItems.setTrackingSerialNo(trackingSerialNumber);

            return purchaseMasterItems;
        }).forEachOrdered(purchaseMasterItems -> {
            purchaseMasterItemsList.add(purchaseMasterItems);
        });
        return purchaseMasterItemsList;
    }

    @Override
    public DataResult<List<ProductDto>> productList(String productName, String authToken, Integer pageNumber, HttpServletRequest request) {

        try {
            DataResult<List<ProductDto>> productListQuery = CommonUtil.getProductByProductName(productName, authToken, pageNumber, request);
            if (productListQuery.isSuccess()) {
                List<ProductDto> productList = productListQuery.getData();
                // Process productList here if needed
                return new SuccessDataResult<>(productList, "Product list fetched successfully");
            } else {
                // If the query was not successful, return an error message
                return new ErrorDataResult<>("Failed to fetch product list: " + productListQuery.getMessage());
            }
        } catch (Exception e) {
            // If an unexpected exception occurs, return an error message
            return new ErrorDataResult<>("An error occurred while fetching the product list: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<ProductDto>> retrieveProductListByProductNameOrSku(String productNameOrSku) {
        try {
            // Retrieve the list of product details from the DAO
            DataResult<List<Map<String, Object>>> retrievedData = this.serviceDao.retrieveProductListByproductNameOrSku(productNameOrSku);

            // Check if the retrieval was successful
            if (retrievedData.isSuccess()) {
                // Create a list to hold the mapped ProductDto objects
                List<ProductDto> productDtoList = new ArrayList<>();

                // Iterate over each map in the retrieved data
                retrievedData.getData().stream().map(map -> {
                    // Map each map entry to a ProductDto
                    ProductDto productDto = new ProductDto();
                    productDto.setProductId(String.valueOf(map.get("id")));
                    productDto.setProductName((String) map.get("product_name"));
                    productDto.setProductSku((String) map.get("sku"));
                    productDto.setSnoPattern((String) map.get("sno_pattern"));
                    productDto.setTrackingSerialNo((boolean) map.get("tracking_serial_no"));
                    productDto.setBatchSerialNo((boolean) map.get("is_batch"));
                    productDto.setIsProductService((boolean) map.get("is_product_service"));
                    return productDto;
                }).forEachOrdered(productDto -> {
                    // Add the mapped ProductDto to the list
                    productDtoList.add(productDto);
                });

                // Return the list wrapped in a SuccessDataResult
                return new SuccessDataResult<>(productDtoList, "Product list retrieved successfully.");
            } else {
                // If retrieval was not successful, return an error result
                return new ErrorDataResult<>("Failed to retrieve product list.");
            }
        } catch (Exception e) {
            // If an unexpected exception occurs, return an error message
            return new ErrorDataResult<>("An error occurred while fetching the product list: " + e.getMessage());
        }
    }

    @Override
    public List<ProductDto> readPurchaseFromCsv(MultipartFile multipartFile) throws Exception {
        List<ProductDto> productDtos = new ArrayList<>();
        try ( Reader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));  CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                ProductDto productDto = new ProductDto();

                try {
                    String productSku = csvRecord.get("Product Sku");
                    String barcode = csvRecord.get("Barcode");
                    String serialNo = csvRecord.get("Serial Number");
                    String expiryDate = csvRecord.get("Expiry Date");
                    productDto.setProductSku(productSku);
                    productDto.setBarCode(barcode);
                    productDto.setSerialNo(serialNo);
                    productDto.setExpiryDate(expiryDate);
                    productDtos.add(productDto);
                } catch (NumberFormatException e) {
                    // Log the parsing error
                    System.err.println("Error parsing price or cost price: " + e.getMessage());
                    // Optionally, you can skip the record here
                }
            }
        }
        return productDtos;
    }

    @Override
    public DataResult<List<CustomerAndSupplierDto>> getCompanyNameListByUserTypesAndCompanyName(List<String> userTypes, String companyName) {
        try {
            // Retrieve company names based on user types and company name
            DataResult<List<Map<String, Object>>> result = serviceDao.getCompanyNamesByUserTypesAndCompanyName(userTypes, companyName);

            if (!result.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve company names: " + result.getMessage());
            }

            List<Map<String, Object>> userDataList = result.getData();
            List<CustomerAndSupplierDto> customerAndSupplierDtoList = new ArrayList<>();

            for (Map<String, Object> userData : userDataList) {
                Integer userId = (Integer) userData.get("id");
                String userIdString = userId != null ? String.valueOf(userId) : null;

                String firstName = (String) userData.get("first_name");
                String lastName = (String) userData.get("last_name");
                String retrievedCompanyName = (String) userData.get("company_name");

                if (userIdString != null && firstName != null && lastName != null) {
                    CustomerAndSupplierDto customerAndSupplierDto = new CustomerAndSupplierDto(userIdString, firstName, lastName, retrievedCompanyName);
                    customerAndSupplierDtoList.add(customerAndSupplierDto);
                }
            }

            return new SuccessDataResult<>(customerAndSupplierDtoList, "Company names retrieved successfully");
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Helper method to set product details from a Map
    private void setProductDetailsFromMap(ProductDto product, Map<String, Object> result, String productId) {
        product.setProductId(productId);
        product.setProductName((String) result.get("name"));
        product.setSnoPattern((String) result.get("sno_pattern"));
        product.setProductSku((String) result.get("sku"));
        product.setSlug((String) result.get("slug"));
        product.setTrackingSerialNo((boolean) result.get("tracking_serial_no"));
        product.setBatchSerialNo((boolean) result.get("is_batch"));
        product.setIsProductService((boolean) result.get("is_product_service"));
    }

    // Helper method to set product details from ProductDto
    private void setProductDetailsFromDto(ProductDto product, ProductDto productDto, String productId) {
        product.setProductId(productId);
        product.setProductName(productDto.getProductName());
        product.setSnoPattern(productDto.getSnoPattern());
        product.setProductSku(productDto.getProductSku());
        product.setSlug(productDto.getSlug());
        product.setTrackingSerialNo(productDto.isTrackingSerialNo());
        product.setBatchSerialNo(productDto.isBatchSerialNo());
        product.setIsProductService(productDto.isIsProductService());
    }

    @Override
    public Result delete_purchase_details_by_id(Integer id, int channelId) {
        try {

            int checkSaleExists = this.serviceDao.checkSaleExistsByPurchaseId(id);

            if (checkSaleExists > 0) {
                // Return an error result with a concise message for sale existence
                return new ErrorResult("Can't delete because your purchase has been sale.");
            }

            int checkSaleReturnExists = this.serviceDao.checkSaleReturnExistsByPurchaseId(id);

            if (checkSaleReturnExists > 0) {
                // Return an error result with a concise message for sale return existence
                return new ErrorResult("Can't delete because your purchase has a sale return.");
            }

            // Fetch the purchase item data
            DataResult<List<Map<String, Object>>> itemData = this.serviceDao.get_item_purchase_data(id);
            if (itemData.isSuccess()) {
                List<Map<String, Object>> data = itemData.getData();
                // Delete each purchase item serial
                for (Map<String, Object> item : data) {
                    int purchaseSerialId = (Integer) item.get("id");
                    serviceDao.deletePurchaseItemSerial(purchaseSerialId);
                }
                // Delete the purchase items and purchase master
                int deletePurchaseItems = serviceDao.deletePurchaseItems(id);
                int deletePurchaseMaster = serviceDao.deletePurchaseMaster(id);

                // Check if deletions were successful
                if (deletePurchaseItems > 0 && deletePurchaseMaster > 0) {
                    return new SuccessResult("Purchase details successfully deleted.");
                } else {
                    return new ErrorResult("Failed to delete purchase details.");
                }
            } else {
                return new ErrorResult("No purchase item data found for the given ID.");
            }
        } catch (Exception e) {
            return new ErrorResult("An error occurred while deleting purchase details.");
        }
    }

    // Method to check for duplicate barcodes
    public List<String> checkForDuplicateBarcode(String barcode, List<String> existingBarcodes) {
        // Check if the barcode already exists in the list
        if (existingBarcodes.contains(barcode)) {
            // If found, return a list containing the duplicate barcode
            return Collections.singletonList(barcode);
        }

        // If no duplicate, add barcode to the list and return null
        existingBarcodes.add(barcode);
        return null;
    }

}
