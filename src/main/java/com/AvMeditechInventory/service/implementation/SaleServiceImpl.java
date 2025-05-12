/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.PurchaseImportResponse;
import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.PurchaseItemSerialMaster;
import com.AvMeditechInventory.entities.Sale;
import com.AvMeditechInventory.entities.SaleItemsMaster;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.SaleService;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
public class SaleServiceImpl implements SaleService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public DataResult<List<Response>> customerNameList(List<String> userTypes) {
        try {
            DataResult<List<Map<String, Object>>> userDataByUserType = this.serviceDao.getUserDataByUserType(userTypes);
            if (userDataByUserType.isSuccess()) {
                List<Map<String, Object>> userData = userDataByUserType.getData();
                List<Response> customerNames = new ArrayList<>();
                for (Map<String, Object> user : userData) {
                    Integer id = (Integer) user.get("id");
                    String firstName = (String) user.get("first_name");
                    String lastName = (String) user.get("last_name");

                    // Create a Response object with the customer name
                    Response response = new Response();
                    response.setId(id);
                    response.setFirstName(firstName);
                    response.setLastName(lastName);
                    response.setCompanyName((String) user.get("company_name"));
                    response.setUserCode((String) user.get("user_code"));
                    customerNames.add(response);
                }
                return new SuccessDataResult<>(customerNames, "Customer names retrieved successfully");
            } else {
                return new ErrorDataResult<>("Failed to retrieve customer names.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to retrieve customer names: An error occurred while processing your request.");
        }
    }

    @Override
    public DataResult<Response> getBarcodeFromItemSerialNumber(String itemBarCode, int channelId) {
        try {
            DataResult<Map<String, Object>> barcodeDataResult = this.serviceDao.getBarcodeFromItemSerialNumber(itemBarCode, channelId);
            if (barcodeDataResult.isSuccess()) {
                Map<String, Object> barcodeData = barcodeDataResult.getData();
                // Extracting data from barcodeData map
                Integer productId = (Integer) barcodeData.get("id");
                String productName = (String) barcodeData.get("name");
                Integer quantity = (Integer) barcodeData.get("quantity");
                String itemBarcode = (String) barcodeData.get("item_barcode");
                Integer saleId = (Integer) barcodeData.get("sale_id");
                BigDecimal discount = (BigDecimal) barcodeData.get("discount");
                BigDecimal gstPercent = (BigDecimal) barcodeData.get("gst_percent");
                BigDecimal gstAmount = (BigDecimal) barcodeData.get("gst_amount");
                BigDecimal totalAmount = (BigDecimal) barcodeData.get("total_amount");
                BigDecimal costPrice = (BigDecimal) barcodeData.get("cost_price");
                boolean trackingSerialNo = (boolean) barcodeData.get("tracking_serial_no");
                boolean batchSerialNo = (boolean) barcodeData.get("is_batch");
                String serialNo = (String) barcodeData.get("item_serial_number");
                Date itemExpiryDate = (Date) barcodeData.get("item_serial_expiry_date");

                if (saleId != null) {
                    // Sale ID is not null, indicating an error condition
                    return new ErrorDataResult<>("Failed to retrieve barcode data: Sale ID is not null");
                } else {
                    // Sale ID is null, proceed with creating response object
                    Response response = new Response();
                    response.setProductId(productId);
                    response.setProductName(productName);
                    response.setQuantity(quantity);
                    response.setItemBarCode(itemBarcode);
                    response.setSaleId(saleId);
                    response.setAmount(gstAmount);
                    response.setTotalAmount(totalAmount);
                    response.setDiscount(discount);
                    response.setGstPercent(gstPercent);
                    response.setCostPrice(costPrice);
                    response.setTrackingSerialNo(trackingSerialNo);
                    response.setBatchSerialNo(batchSerialNo);
                    response.setSerialNumber(serialNo);
                    response.setItemSerialExpiry(itemExpiryDate);

                    if (batchSerialNo) {
                        String batchNo = "";
                        List<Map<String, Object>> data = serviceDao.getBatchFromItemSerialNumber(itemBarcode, channelId);
                        if (!data.isEmpty()) {
                            for (int i = 0; i < data.size(); i++) {
                                Map<String, Object> map = data.get(i);
                                batchNo = batchNo + map.get("item_batch") + ", " + map.get("quantity") + " (" + map.get("item_serial_expiry_date") + ")" + "|";
                            }
                            batchNo = batchNo.substring(0, batchNo.length() - 1);
                        }
                        response.setBatchNo(batchNo);
                    }

                    return new SuccessDataResult<>(response, "Barcode data retrieved successfully");
                }
            } else {
                // Barcode data retrieval failed
                return new ErrorDataResult<>("Failed to retrieve barcode data");
            }
        } catch (Exception e) {
            // Exception occurred while processing the request
            return new ErrorDataResult<>("Failed to retrieve barcode data: An error occurred while processing your request.");
        }
    }

    @Override
    public DataResult<Integer> saleCreate(Sale sale, String[] addedRowsData, String formattedDate,
            String[] purchaseSerialData, HttpSession session) {
        try {
            Integer customerId = sale.getCustomerId();
            String saleStatus = "ACTIVE";
            BigDecimal subTotalAmount = BigDecimal.ZERO;
            BigDecimal gstAmount = BigDecimal.ZERO;
            BigDecimal discountAmount = BigDecimal.ZERO;
            BigDecimal shippingAmount = BigDecimal.ZERO;
            BigDecimal grandTotal = BigDecimal.ZERO;
            String referenceNo = sale.getReferenceNo();
            String remarks = sale.getRemarks();
            int channelId = (int) session.getAttribute("selectedStoreId");
            String purchaseEmailId = (String) session.getAttribute("userEmail");
            DataResult<Map<String, Object>> data = serviceDao.getUserDataByEmail(purchaseEmailId);
            int saleUserId = (int) data.getData().get("id");
            int transferChannelId = channelId;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(formattedDate);

            if (sale.getSaleType().equalsIgnoreCase("Internal Transfer")) {
                customerId = Constant.AV_MEDITECH_INVENTORY_USER_ID;
                transferChannelId = sale.getChannelId1();
            }

            // Insert into sale
            DataResult<Integer> insertSaleResult = this.serviceDao.insert_sale(customerId, date, subTotalAmount,
                    discountAmount, gstAmount, shippingAmount, grandTotal, referenceNo, saleStatus, channelId,
                    remarks, saleUserId, sale.getSaleType(), transferChannelId);
            if (!insertSaleResult.isSuccess()) {
                return new ErrorDataResult<>(insertSaleResult.getMessage());
            }
            Integer saleId = insertSaleResult.getData();

            for (String rowData : addedRowsData) {
                String[] parts = rowData.split("\\|");
                if (parts.length >= 3) {
                    try {
                        int productId = Integer.parseInt(parts[0]);
                        int quantity = Integer.parseInt(parts[1]);
                        BigDecimal salePriceonFronted = BigDecimal.ZERO;
                        BigDecimal discount = BigDecimal.ZERO;
                        String itemBarCode = parts[8];

                        // Retrieve product information by ID
                        DataResult<Response> productResult = this.serviceDao.getProductById(productId);
                        if (!productResult.isSuccess()) {
                            return new ErrorDataResult<>("Failed to retrieve product with ID: " + productId);
                        }
                        Response product = productResult.getData();
                        BigDecimal gstPercent = product.getGst();

                        // Calculate values
                        BigDecimal subTotal = salePriceonFronted.multiply(BigDecimal.valueOf(quantity));
                        BigDecimal totalAmountAfterDiscount = subTotal.subtract(discount);
                        BigDecimal gstAmountForItem = totalAmountAfterDiscount.multiply(gstPercent).divide(new BigDecimal("100"));
                        BigDecimal totalAmountIncludingGST = totalAmountAfterDiscount.add(gstAmountForItem);

                        // Update totals
                        subTotalAmount = subTotalAmount.add(subTotal);
                        discountAmount = discountAmount.add(discount);
                        gstAmount = gstAmount.add(gstAmountForItem);
                        grandTotal = grandTotal.add(totalAmountIncludingGST);

                        if (product.isTrackingSerialNo()) {
                            // Validate and process serial number data for the product
                            List<PurchaseItemSerialMaster> serialDataList = getSerialDataForProduct(purchaseSerialData, String.valueOf(productId));
                            if (serialDataList.size() != quantity) {
                                return new ErrorDataResult<>("Quantity does not match the number of serial numbers provided for product ID: " + productId);
                            }
                            // Insert serial data
                            for (PurchaseItemSerialMaster serialData : serialDataList) {
                                DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getBarcodeFromItemSerialNumber(serialData.getItemBarcode(), channelId);
                                int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                // Insert into sale items
                                DataResult<Integer> saleItemId = this.serviceDao.insert_sale_items(saleId, productId, purchaseItemSerialId);
                                // Update into purchase item serial master
                                DataResult<Integer> updateSaleIdForPurchaseItemSerialMaster;
                                if (sale.getSaleType().equalsIgnoreCase("Internal Transfer")) {
                                    updateSaleIdForPurchaseItemSerialMaster = this.serviceDao.updateChannelIdForPurchaseItemSerialMaster(
                                            sale.getChannelId1(), purchaseItemSerialId, channelId);
                                } else {
                                    updateSaleIdForPurchaseItemSerialMaster = this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(
                                            saleItemId.getData(), purchaseItemSerialId, channelId);
                                }
                                if (!updateSaleIdForPurchaseItemSerialMaster.isSuccess()) {
                                    return new ErrorDataResult<>("Failed to update purchase items serial master.");
                                }
                                Integer purchaseItemSerialMasterId = updateSaleIdForPurchaseItemSerialMaster.getData();
                                DataResult<Integer> insertStockMovmentHistory = this.serviceDao.insertStockMovmentHistory(purchaseItemSerialMasterId, productId, Constant.ACTION_TYPE_SALE, date, saleId);
                                if (!insertStockMovmentHistory.isSuccess()) {
                                    return new ErrorDataResult<>("Failed to insert stock movment history.");
                                }
                            }
                        } else {
                            if (product.isBatchSerialNo()) {
                                String batchSerialNo = parts[9];
                                for (int i = 0; i < quantity; i++) {
                                    DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getBarcodeFromItemBatchSerialNumber(itemBarCode, batchSerialNo, channelId);
                                    int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                    // Insert into sale items
                                    DataResult<Integer> saleItemId = this.serviceDao.insert_sale_items(saleId, productId, purchaseItemSerialId);
                                    if (sale.getSaleType().equalsIgnoreCase("Internal Transfer")) {
                                        this.serviceDao.updateChannelIdForPurchaseItemSerialMaster(sale.getChannelId1(), purchaseItemSerialId, channelId);
                                    } else {
                                        this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(saleItemId.getData(), purchaseItemSerialId, channelId);
                                    }
                                }
                            } else {
                                for (int i = 0; i < quantity; i++) {
                                    DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getBarcodeFromItemSerialNumber(itemBarCode, channelId);
                                    int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                    // Insert into sale items
                                    DataResult<Integer> saleItemId = this.serviceDao.insert_sale_items(saleId, productId, purchaseItemSerialId);
                                    if (sale.getSaleType().equalsIgnoreCase("Internal Transfer")) {
                                        this.serviceDao.updateChannelIdForPurchaseItemSerialMaster(sale.getChannelId1(), purchaseItemSerialId, channelId);
                                    } else {
                                        this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(saleItemId.getData(), purchaseItemSerialId, channelId);
                                    }
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        return new ErrorDataResult<>("Invalid format for product ID, quantity, or discount.");
                    }
                } else {
                    return new ErrorDataResult<>("Invalid data format in row: " + rowData);
                }
            }

            // Include shipping amount in the grand total
            // grandTotal = grandTotal.add(shippingAmount);
            DataResult<Integer> saleSeq = null;
            String storeName = (String) session.getAttribute("selectedStoreName");
            if (storeName != null && storeName.equals("AV MEDITECH PVT LTD KAITHAL")) {
                saleSeq = serviceDao.getAutoSaleAvtSeq();
            } else if (storeName != null && storeName.equals("LENSE HOME KAITHAL")) {
                saleSeq = serviceDao.getAutoSaleLhtSeq();
            } else if (storeName != null && storeName.equals("AV MEDITECH GURUGRAM")) {
                saleSeq = serviceDao.getAutoSaleSeq();
            } else if (storeName != null && storeName.equals("AV MEDITECH KAITHAL")) {
                saleSeq = serviceDao.getAutoSaleAmkSeq();
            }
            // Update the sale with calculated totals
            DataResult<Integer> updateSaleResult = this.serviceDao.update_sale(saleId, subTotalAmount, gstAmount,
                    discountAmount, shippingAmount, grandTotal, saleSeq.getData());
            if (!updateSaleResult.isSuccess()) {
                return new ErrorDataResult<>(updateSaleResult.getMessage());
            }

            return new SuccessDataResult<>(saleId, "Product sale created successfully");

        } catch (ParseException e) {
            return new ErrorDataResult<>("Invalid date format");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while processing the sale");
        }
    }

    @Override
    public DataResult<String> checkSaleCreate(String[] addedRowsData) {
        try {

            for (String rowData : addedRowsData) {
                String[] parts = rowData.split("\\|");
                if (parts.length >= 3) {
                    try {
                        int productId = Integer.parseInt(parts[0]);
                        int quantity = Integer.parseInt(parts[1]);
                        String itemBarCode = parts[8];

                        // Retrieve product information by ID
                        DataResult<Response> productResult = this.serviceDao.getProductById(productId);
                        if (!productResult.isSuccess()) {
                            return new ErrorDataResult<>("Failed to retrieve product with ID: " + productId);
                        }
                        Response product = productResult.getData();

                        if (product.isTrackingSerialNo()) {
                        } else {
                            if (product.isBatchSerialNo()) {
                                String batchSerialNo = parts[9];
                                int dbQuantity = serviceDao.countBatchProducts(itemBarCode, batchSerialNo);
                                if (quantity > dbQuantity) {
                                    return new ErrorDataResult<>("Available quantity for " + product.getProductName() + " is " + dbQuantity);
                                }
                            } else {
                                int dbQuantity = serviceDao.countNotTrackingProducts(itemBarCode);
                                if (quantity > dbQuantity) {
                                    return new ErrorDataResult<>("Available quantity for " + product.getProductName() + " is " + dbQuantity);
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        return new ErrorDataResult<>("Invalid format for product ID, quantity, or discount.");
                    }
                } else {
                    return new ErrorDataResult<>("Invalid data format in row: " + rowData);
                }
            }

            return new SuccessDataResult<>("", "Sale created successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while processing the sale");
        }
    }

    @Override
    public PurchaseImportResponse importSaleProduct(MultipartFile file1, int channelId) {
        PurchaseImportResponse resp = new PurchaseImportResponse();
        List<Response> productSale = new ArrayList<>();
        ArrayList<String> serialPurchase = new ArrayList<>();
        if (!file1.isEmpty()) {
            try {
                File convFile = File.createTempFile("temp", null);
                file1.transferTo(convFile);
                try ( BufferedReader reader = new BufferedReader(new FileReader(convFile));  CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
                    for (CSVRecord csvRecord : csvParser) {
//                        String productName = csvRecord.get("Product Name");
                        String productSku = csvRecord.get("Product Sku");
                        String barcode = csvRecord.get("Barcode");
                        String serialNo = csvRecord.get("Serial Number");
                        String expiryDate = csvRecord.get("Expiry Date");
                        DataResult<Map<String, Object>> barcodeDataResult = this.serviceDao.getBarcodeFromItemSerialNumber(barcode, channelId);
                        if (barcodeDataResult.isSuccess()) {
                            Map<String, Object> barcodeData = barcodeDataResult.getData();
                            Response response = new Response();
                            boolean isEntryNew = true;
                            if (!productSale.isEmpty()) {
                                for (int i = 0; i < productSale.size(); i++) {
                                    Response response1 = productSale.get(i);
                                    if (response.getProductId() == null ? response1.getProductId() == null : response.getProductId().equals(response1.getProductId())) {
                                        productSale.get(i).setQuantity(response1.getQuantity() + 1);
                                        isEntryNew = false;
                                    }
                                }
                            }

                            if (isEntryNew) {
                                response.setProductId((Integer) barcodeData.get("id"));
                                response.setProductName((String) barcodeData.get("name"));
                                response.setQuantity((Integer) barcodeData.get("quantity"));
                                response.setItemBarCode((String) barcodeData.get("item_barcode"));
                                response.setSaleId((Integer) barcodeData.get("sale_id"));
                                response.setAmount((BigDecimal) barcodeData.get("gst_amount"));
                                response.setTotalAmount((BigDecimal) barcodeData.get("total_amount"));
                                response.setDiscount((BigDecimal) barcodeData.get("discount"));
                                response.setGstPercent((BigDecimal) barcodeData.get("gst_percent"));
                                response.setCostPrice((BigDecimal) barcodeData.get("cost_price"));
                                response.setTrackingSerialNo((boolean) barcodeData.get("tracking_serial_no"));
                                response.setSerialNumber((String) barcodeData.get("item_serial_number"));
                                response.setItemSerialExpiry((Date) barcodeData.get("item_serial_expiry_date"));
                                productSale.add(response);
                            }
                            serialPurchase.add(response.getProductId() + "|" + serialNo + "|" + expiryDate);
                        }
                    }
                    resp.setProductSale(productSale);
                    resp.setSerialPurchase(serialPurchase);
                }
                convFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resp;
    }

    @Override
    public DataResult<List<Response>> saleList(int channelId, Integer pageNumber, Integer pageSize, Date startDate,
            Date endDate, String customerName, String productName, String saleType, int channelId2) {
        try {
            // Retrieve sale data from the service layer
            DataResult<List<Map<String, Object>>> saleListResult = this.serviceDao.fetchSaleList(channelId, pageNumber,
                    pageSize, startDate, endDate, customerName, productName, saleType, channelId2);

            // Check if the sale data retrieval was successful
            if (!saleListResult.isSuccess()) {
                // If not successful, return an error data result with an appropriate message
                return new ErrorDataResult<>(null, "Failed to retrieve sale data: " + saleListResult.getMessage());
            }

            // If sale data retrieval was successful, proceed to process the data
            List<Map<String, Object>> productDataList = saleListResult.getData();
            List<Response> responseList = new ArrayList<>();

            // Process each map of sale data into Response objects
            for (Map<String, Object> productData : productDataList) {
                // Create and populate a Response object
                Response response = new Response();
                // Extract data from the map
                Integer purchaseIdInteger = (Integer) productData.get("sale_id");
                String type = (String) productData.get("sale_type");

                // start channelId 1
                if (type.equalsIgnoreCase("Internal Transfer")) {

                    Optional<Integer> channelIdBySaleId = this.serviceDao.getChannelIdBySaleId(purchaseIdInteger);

                    if (channelIdBySaleId.isPresent()) {
                        Integer channelId1 = channelIdBySaleId.get();
                        Optional<String> channelNameByChannelId = this.serviceDao.getChannelNameByChannelId(channelId1);
                        if (channelNameByChannelId.isPresent()) {
                            String channelName = channelNameByChannelId.get();
                            response.setCompanyName(" Transfer To - " + channelName);
                        }

                    }
                }

                // close channelId 1
                String fullName = (String) productData.get("company_name");

                // Parse purchase date
                Date purchaseDate = null;
                String purchaseDateString = null;
                Object purchaseDateObj = productData.get("sale_date");
                if (purchaseDateObj != null) {
                    purchaseDate = (Date) purchaseDateObj;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    purchaseDateString = dateFormat.format(purchaseDate);
                }

                String referenceNo = (String) productData.get("reference");
                String remarks = (String) productData.get("remarks");

                response.setId(purchaseIdInteger);
                response.setAutoId((Integer) productData.get("auto_sale_id"));

                if (type.equals("Sale Create")) {
                    response.setFullName(fullName);
                } else {
                    response.setFullName(response.getCompanyName());
                }
                response.setDate(purchaseDateString);
                response.setReferenceNumber(referenceNo);
                response.setRemarks(remarks);
                response.setAmount(BigDecimal.ZERO);
                response.setGst(BigDecimal.ZERO);
                response.setShipping(BigDecimal.ZERO);
                response.setTotalAmount(BigDecimal.ZERO);
                response.setStatus("");
                response.setSaleType(type);
                try {
                    response.setQuantity(((Long) productData.get("total_quantity")).intValue());
                } catch (Exception e) {
                    response.setQuantity(0);
                }

                // Add the response object to the list
                responseList.add(response);
            }

            // Return success data result with the processed response list
            return new SuccessDataResult<>(responseList, "Successfully retrieved and processed sale data.");
        } catch (Exception e) {
            // If any unexpected error occurs, return an error data result with an appropriate message
            return new ErrorDataResult<>(null, "An unexpected error occurred.");
        }
    }

    @Override
    public DataResult<Response> getSaleDetailsById(Integer saleId, int channelId) {
        try {
            // Retrieve sale master details
            DataResult<Map<String, Object>> saleMasterDetailsResult = this.serviceDao.getSaleDetailsBySaleId(saleId, channelId);

            if (!saleMasterDetailsResult.isSuccess()) {
                // Return error if unable to retrieve sale master details
                return new ErrorDataResult<>(null, "Failed to retrieve sale master details: " + saleMasterDetailsResult.getMessage());
            }

            Sale saleMaster = mapToSaleMaster(saleMasterDetailsResult.getData());

            // Retrieve sale items details
            DataResult<List<Map<String, Object>>> saleItemsDetailsResult = this.serviceDao.getSaleItemsDetailsBySaleId(saleId);

            if (!saleItemsDetailsResult.isSuccess()) {
                // Return error if unable to retrieve sale items details
                return new ErrorDataResult<>(null, "Failed to retrieve sale items details: ");
            }

            List<SaleItemsMaster> saleItemsMasterList = mapToSaleItemsMasterList(saleItemsDetailsResult.getData());

            List<PurchaseItemSerialMaster> purchaseItemSerialMasters = new ArrayList<>();

            // Retrieve purchase item serial master details using the saleItemId
            DataResult<List<Map<String, Object>>> purchaseItemSerialMasterDetailsResult = this.serviceDao.getPurchaseItemSerialMasterDetailsBySaleItemsId(saleId);
            List<Map<String, Object>> purchaseItemSerialMasterDetails = purchaseItemSerialMasterDetailsResult.getData();

            for (Map<String, Object> detail : purchaseItemSerialMasterDetails) {
                PurchaseItemSerialMaster purchaseItemSerialMaster = new PurchaseItemSerialMaster();
                purchaseItemSerialMaster.setItemBarcode((String) detail.get("item_barcode"));
                if (null == (String) detail.get("item_serial_number")) {
                    purchaseItemSerialMaster.setItemSerialNumber((String) detail.get("item_batch"));
                } else {
                    purchaseItemSerialMaster.setItemSerialNumber((String) detail.get("item_serial_number"));
                }
                purchaseItemSerialMaster.setProductId((Integer) detail.get("product_id"));
                // Ensure proper casting to java.sql.Date
                java.sql.Date itemSerialExpiryDate = (java.sql.Date) detail.get("item_serial_expiry_date");
                purchaseItemSerialMaster.setItemSerialExpiryDate(itemSerialExpiryDate);

                purchaseItemSerialMasters.add(purchaseItemSerialMaster);
            }

            // Construct and return response
            Response response = new Response();
            response.setSale(saleMaster);
            response.setSaleItemsMasters(saleItemsMasterList);
            response.setPurchaseItemSerialMasters(purchaseItemSerialMasters);
            return new SuccessDataResult<>(response, "Sale details retrieved successfully.");

        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while retrieving sale details");
        }
    }

    // Helper method to map sale details to Sale object
    private Sale mapToSaleMaster(Map<String, Object> saleDetails) {
        Sale sale = new Sale();
        sale.setSaleId((Integer) saleDetails.get("sale_id"));
        sale.setSaleSeqId((Integer) saleDetails.get("auto_sale_id"));
        sale.setCustomerId((Integer) saleDetails.get("customer_id"));
        sale.setSaleDate((java.sql.Date) saleDetails.get("sale_date"));
        sale.setSubTotalAmount((BigDecimal) saleDetails.get("sub_total"));
        sale.setDiscountAmount((BigDecimal) saleDetails.get("discount"));
        sale.setGstAmount((BigDecimal) saleDetails.get("gst"));
        sale.setShippingAmount((BigDecimal) saleDetails.get("shipping"));
        sale.setGrandTotal((BigDecimal) saleDetails.get("grand_total"));
        sale.setReferenceNo((String) saleDetails.get("reference"));
        sale.setRemarks((String) saleDetails.get("remarks"));
        sale.setSalestatus((String) saleDetails.get("status"));
        sale.setRemarks((String) saleDetails.get("remarks"));
        sale.setName((String) saleDetails.get("company_name"));
        sale.setSaleType((String) saleDetails.get("sale_type"));
        sale.setChannelId1((Integer) saleDetails.get("channel_id"));
        return sale;
    }

    // Helper method to map sale items master details to a list of SaleItemsMaster objects
    private List<SaleItemsMaster> mapToSaleItemsMasterList(List<Map<String, Object>> saleItemsDetails) {
        List<SaleItemsMaster> saleItemsMasterList = new ArrayList<>();
        for (Map<String, Object> itemDetail : saleItemsDetails) {
            SaleItemsMaster saleItem = new SaleItemsMaster();
            saleItem.setSaleId((Integer) itemDetail.get("sale_id"));
            saleItem.setProductId((Integer) itemDetail.get("id"));
            saleItem.setQuantity(((Long) itemDetail.get("quantity")).intValue());
            saleItem.setCostPrice(BigDecimal.ZERO);
            saleItem.setDiscount(BigDecimal.ZERO);
            saleItem.setGstPercent(BigDecimal.ZERO);
            saleItem.setGstAmount(BigDecimal.ZERO);
            saleItem.setTotalAmount(BigDecimal.ZERO);
            saleItem.setProductName((String) itemDetail.get("name"));
            saleItem.setTrackingSerialNo((boolean) itemDetail.get("tracking_serial_no"));
            saleItem.setIsBatch((boolean) itemDetail.get("is_batch"));
            saleItem.setItemBarCode((String) itemDetail.get("sku"));
            saleItem.setItemSerialNo((String) itemDetail.get("item_batch"));
            saleItemsMasterList.add(saleItem);
        }
        return saleItemsMasterList;
    }

    public static List<PurchaseItemSerialMaster> getSerialDataForProduct(String[] purchaseSerialData, String productId) throws ParseException {
        List<PurchaseItemSerialMaster> serialDataList = new ArrayList<>();
        Set<String> uniqueSerialNumbers = new HashSet<>();  // To track unique serial numbers

        for (String serialNumberData : purchaseSerialData) {
            String[] serialNumberParts = serialNumberData.split("\\|");
            if (serialNumberParts.length >= 4 && serialNumberParts[0].equals(productId)) {
                String productbarCode = serialNumberParts[3];
                String itemSerialNumberInt = serialNumberParts[1];

                // Check if the serial number is unique
                if (uniqueSerialNumbers.contains(itemSerialNumberInt)) {
                    // If not unique, continue to the next iteration
                    continue;
                }

                String itemExpiryDate = serialNumberParts[2];
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = simpleDateFormat.parse(itemExpiryDate);

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
    public DataResult<List<Response>> getCustomerNameListByCustomerName(List<String> userTypes, String customerName) {
        try {
            // Fetch customer name data from DAO layer
            DataResult<List<Map<String, Object>>> customerNameListResult = this.serviceDao.getCustomerNameListByCustomerName(userTypes, customerName);

            if (!customerNameListResult.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve customer names.");
            }

            List<Map<String, Object>> userData = customerNameListResult.getData();

            List<Response> customerNames = new ArrayList<>(); // List to hold Response objects

            // Process each user data map to create Response objects
            for (Map<String, Object> user : userData) {
                Integer id = (Integer) user.get("id");
                String firstName = (String) user.get("first_name");
                String lastName = (String) user.get("last_name");

                // Create a Response object with the customer name
                Response response = new Response();
                response.setId(id);
                response.setFirstName(firstName);
                response.setLastName(lastName);
                customerNames.add(response); // Add Response object to the list
            }

            // Return success result with the list of Response objects
            return new SuccessDataResult<>(customerNames, "Customer names retrieved successfully");

        } catch (Exception e) {
            // Handle any exceptions that occur during processing
            return new ErrorDataResult<>("An error occurred while processing your request.");
        }
    }

    @Override
    public DataResult<Integer> updateSaleCreate(Sale sale, String[] purchaseData, String[] purchaseSerialData,
            Date saleDate, int saleUserId, int channelId) {

        DataResult<Integer> saleCreate;
        Integer saleId = sale.getSaleId();
        int saleReturnId = this.serviceDao.isSalePossible(saleId);
        if (saleReturnId != 0) {
            return new ErrorDataResult<>("This sale is already associated with that sale return: " + saleReturnId);
        }
        if (sale.getSaleType().equalsIgnoreCase("Internal Transfer")) {
            sale.setCustomerId(Constant.AV_MEDITECH_INVENTORY_USER_ID);
        }
        // Call service method to update sale data
        saleCreate = serviceDao.update_sale_data(sale.getSaleId(), sale.getReferenceNo(), sale.getRemarks(),
                sale.getCustomerId(), saleDate, saleUserId);
        if (sale.getSaleType().equals("Sale Create")) {
            try {
                DataResult<List<Map<String, Object>>> itemData = this.serviceDao.get_item_data(sale.getSaleId());
                if (itemData.isSuccess()) {
                    List<Map<String, Object>> data = itemData.getData();
                    for (int i = 0; i < data.size(); i++) {
                        int purchaseSerialId = (Integer) data.get(i).get("sale_item_id");
                        serviceDao.updatePurchaseSale(purchaseSerialId);
                    }
                }
                int deleteSaleItems = serviceDao.deleteSaleItems(sale.getSaleId());
                BigDecimal subTotalAmount = BigDecimal.ZERO;
                BigDecimal gstAmount = BigDecimal.ZERO;
                BigDecimal discountAmount = BigDecimal.ZERO;
                BigDecimal grandTotal = BigDecimal.ZERO;
                if (deleteSaleItems > 0) {
                    for (String rowData : purchaseData) {
                        String[] parts = rowData.split("\\|");
                        if (parts.length >= 3) {
                            try {
                                int productId = Integer.parseInt(parts[0]);
                                int quantity = Integer.parseInt(parts[1]);
                                BigDecimal salePriceonFronted = BigDecimal.ZERO;
                                BigDecimal discount = BigDecimal.ZERO;
                                String itemBarCode = parts[8];

                                // Retrieve product information by ID
                                DataResult<Response> productResult = this.serviceDao.getProductById(productId);
                                if (!productResult.isSuccess()) {
                                    return new ErrorDataResult<>("Failed to retrieve product with ID: " + productId);
                                }
                                Response product = productResult.getData();
                                BigDecimal gstPercent = product.getGst();

                                // Calculate values
                                BigDecimal subTotal = salePriceonFronted.multiply(BigDecimal.valueOf(quantity));
                                BigDecimal totalAmountAfterDiscount = subTotal.subtract(discount);
                                BigDecimal gstAmountForItem = totalAmountAfterDiscount.multiply(gstPercent).divide(new BigDecimal("100"));
                                BigDecimal totalAmountIncludingGST = totalAmountAfterDiscount.add(gstAmountForItem);

                                // Update totals
                                subTotalAmount = subTotalAmount.add(subTotal);
                                discountAmount = discountAmount.add(discount);
                                gstAmount = gstAmount.add(gstAmountForItem);
                                grandTotal = grandTotal.add(totalAmountIncludingGST);

                                if (product.isTrackingSerialNo()) {
                                    // Validate and process serial number data for the product
                                    List<PurchaseItemSerialMaster> serialDataList = getSerialDataForProduct(purchaseSerialData, String.valueOf(productId));
                                    if (serialDataList.size() != quantity) {
                                        return new ErrorDataResult<>("Quantity does not match the number of serial numbers provided for product ID: " + productId);
                                    }
                                    // Insert serial data
                                    for (PurchaseItemSerialMaster serialData : serialDataList) {
                                        DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getBarcodeFromItemSerialNumber(serialData.getItemBarcode(), channelId);
                                        int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                        // Insert into sale items
                                        DataResult<Integer> saleItemId = this.serviceDao.insert_sale_items(saleId, productId, purchaseItemSerialId);
                                        // Update into purchase item serial master
                                        DataResult<Integer> updateSaleIdForPurchaseItemSerialMaster = this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(
                                                saleItemId.getData(), purchaseItemSerialId, channelId);
                                        if (!updateSaleIdForPurchaseItemSerialMaster.isSuccess()) {
                                            return new ErrorDataResult<>("Failed to update purchase items serial master.");
                                        }
                                        Integer purchaseItemSerialMasterId = updateSaleIdForPurchaseItemSerialMaster.getData();
                                        DataResult<Integer> insertStockMovmentHistory = this.serviceDao.insertStockMovmentHistory(purchaseItemSerialMasterId, productId, Constant.ACTION_TYPE_SALE, saleDate, saleId);
                                        if (!insertStockMovmentHistory.isSuccess()) {
                                            return new ErrorDataResult<>("Failed to insert stock movment history.");
                                        }
                                    }
                                } else {
                                    if (product.isBatchSerialNo()) {
                                        String batchSerialNo = parts[9];
                                        for (int i = 0; i < quantity; i++) {
                                            DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getBarcodeFromItemBatchSerialNumber(itemBarCode, batchSerialNo, channelId);
                                            int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                            // Insert into sale items
                                            DataResult<Integer> saleItemId = this.serviceDao.insert_sale_items(saleId, productId, purchaseItemSerialId);
                                            this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(saleItemId.getData(), purchaseItemSerialId, channelId);
                                        }
                                    } else {
                                        for (int i = 0; i < quantity; i++) {
                                            DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getBarcodeFromItemSerialNumber(itemBarCode, channelId);
                                            int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                            // Insert into sale items
                                            DataResult<Integer> saleItemId = this.serviceDao.insert_sale_items(saleId, productId, purchaseItemSerialId);
                                            this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(saleItemId.getData(), purchaseItemSerialId, channelId);
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                return new ErrorDataResult<>("Invalid format for product ID, quantity, or discount.");
                            }
                        } else {
                            return new ErrorDataResult<>("Invalid data format in row: " + rowData);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return saleCreate;
    }

    @Override
    public DataResult<List<Response>> getSaleItemDetails(Integer saleId, int channelId) {
        try {
            // Retrieve sale item details from the DAO layer
            DataResult<List<Map<String, Object>>> saleItemDetailsResult = this.serviceDao.getSaleItemDetails(saleId, channelId);

            // Check if the retrieval was successful
            if (!saleItemDetailsResult.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve sale item details.");
            }

            // Extract the list of maps containing sale item details
            List<Map<String, Object>> saleItemDetailsList = saleItemDetailsResult.getData();
            List<Response> responseList = new ArrayList<>();

            // Iterate through each map and create Response objects
            for (Map<String, Object> saleItemDetails : saleItemDetailsList) {
                Response response = new Response();

                // Set the fields of the Response object based on the map values
//                response.setSaleItemsId((Integer) saleItemDetails.get("sale_item_id"));
                response.setSaleDate((Date) saleItemDetails.get("sale_date"));
                response.setQuantity(((Long) saleItemDetails.get("quantity")).intValue());
                response.setTotalAmount(BigDecimal.ZERO);
                response.setProductName((String) saleItemDetails.get("product_name"));
                response.setProductTypeName((String) saleItemDetails.get("product_type_name"));
                response.setCategoryName((String) saleItemDetails.get("category_name"));
                response.setItemDescription((String) saleItemDetails.get("description_plaintext"));
                response.setChannelName((String) saleItemDetails.get("channel_name"));

                // Add Response object to list
                responseList.add(response);
            }

            // Return a SuccessDataResult with the list of Response objects
            return new SuccessDataResult<>(responseList, "Sale item details retrieved successfully.");

        } catch (Exception e) {
            // Log the error message for debugging purposes
            System.err.println("An error occurred while retrieving sale item details: " + e.getMessage());
            return new ErrorDataResult<>("An error occurred while retrieving sale item details.");
        }
    }

    @Override
    public Result deleteSaleDetailsById(Integer id, int channelId) {

        try {

            int checkSaleReturnExists = this.serviceDao.checkSaleReturnExistsBySaleId(id);

            if (checkSaleReturnExists > 0) {
                // Return an error result with a concise message for sale return existence
                return new ErrorResult("Can't delete because your sale has a sale return.");
            }

            DataResult<List<Map<String, Object>>> itemData = this.serviceDao.get_item_data(id);
            if (itemData.isSuccess()) {
                List<Map<String, Object>> data = itemData.getData();
                for (int i = 0; i < data.size(); i++) {
                    int purchaseSerialId = (Integer) data.get(i).get("sale_item_id");
                    serviceDao.updatePurchaseSale(purchaseSerialId);
                }
            }
            int deleteSaleItems = serviceDao.deleteSaleItems(id);
            int deleteSaleMaster = serviceDao.deleteSaleMaster(id);

            // Assuming a successful operation if all DAO calls return a positive value
            if (deleteSaleItems > 0 && deleteSaleMaster > 0) {

                return new SuccessResult("Sale details deleted successfully.");
            } else {
                return new ErrorResult("Failed to delete some sale details. Please check the records.");
            }
        } catch (Exception e) {
            return new ErrorResult("An error occurred while deleting sale details.");
        }

    }

}
