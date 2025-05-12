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
import com.AvMeditechInventory.service.StockService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class StockServiceImpl implements StockService {

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
                return new ErrorDataResult<>("Failed to retrieve customer names: " + userDataByUserType.getMessage());
            }
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to retrieve customer names: An error occurred while processing your request.");
        }
    }

    @Override
    public DataResult<Response> getBarcodeFromItemSerialNumber(String itemBarCode, String customerId, int channelId) {
        try {
            DataResult<Map<String, Object>> barcodeDataResult = this.serviceDao.getStockFromBarcodeNumber(
                    itemBarCode, Integer.parseInt(customerId), channelId);
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

                if (saleId == null) {
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
                        List<Map<String, Object>> data = serviceDao.getBatchFromItemSerialNumber1(itemBarcode, channelId);
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
                return new ErrorDataResult<>("Failed to retrieve barcode data: " + barcodeDataResult.getMessage());
            }
        } catch (Exception e) {
            // Exception occurred while processing the request
            return new ErrorDataResult<>("Failed to retrieve barcode data: An error occurred while processing your request.");
        }
    }

    @Override
    public DataResult<String> checkSaleReturn(String[] addedRowsData) {
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
                                int dbQuantity = serviceDao.countBatchReturnProducts(itemBarCode, batchSerialNo);
                                if (quantity > dbQuantity) {
                                    return new ErrorDataResult<>("Available quantity for " + product.getProductName() + " is " + dbQuantity);
                                }
                            } else {
                                int dbQuantity = serviceDao.countNotTrackingReturnProducts(itemBarCode);
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
    public DataResult<Integer> stockTransfer(Sale sale, String[] addedRowsData,
            String formattedDate, String[] purchaseSerialData, HttpSession session) {
        try {
            Integer customerId = sale.getCustomerId();
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
            int returnUserId = (int) data.getData().get("id");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(formattedDate);

            // Insert into sale
            DataResult<Integer> insertSaleResult = this.serviceDao.insert_sale_return(customerId, date, subTotalAmount,
                    gstAmount, grandTotal, referenceNo, channelId, remarks, returnUserId);
            if (!insertSaleResult.isSuccess()) {
                return new ErrorDataResult<>(insertSaleResult.getMessage());
            }
            Integer saleReturnId = insertSaleResult.getData();

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
                                DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getStockFromBarcodeNumber(serialData.getItemBarcode(), customerId, channelId);
                                int saleId = (int) getStockFromBarcodeNumber.getData().get("sale_id");
                                int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                // Insert into sale items
                                this.serviceDao.insert_sale_return_items(saleReturnId, productId, saleId, purchaseItemSerialId);
                                // Update into purchase item serial master
                                DataResult<Integer> updateSaleIdForPurchaseItemSerialMaster = this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(purchaseItemSerialId);
                                if (!updateSaleIdForPurchaseItemSerialMaster.isSuccess()) {
                                    return new ErrorDataResult<>(updateSaleIdForPurchaseItemSerialMaster.getMessage());
                                }
                                Integer purchaseItemSerialMasterId = updateSaleIdForPurchaseItemSerialMaster.getData();
                                DataResult<Integer> insertStockMovmentHistory = this.serviceDao.insertStockMovmentHistory(purchaseItemSerialMasterId, productId, Constant.ACTION_TYPE_SALE_RETURN, date, saleReturnId);
                                if (!insertStockMovmentHistory.isSuccess()) {
                                    return new ErrorDataResult<>(insertStockMovmentHistory.getMessage());
                                }
                            }
                        } else {
                            if (product.isBatchSerialNo()) {
                                String batchSerialNo = parts[9];
                                for (int i = 0; i < quantity; i++) {
                                    DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getStockFromBarcodeNumber(itemBarCode, customerId, batchSerialNo);
                                    int saleId = (int) getStockFromBarcodeNumber.getData().get("sale_id");
                                    int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                    // Insert into sale items
                                    this.serviceDao.insert_sale_return_items(saleReturnId, productId, saleId, purchaseItemSerialId);
                                    this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(purchaseItemSerialId);
                                }
                            } else {
                                for (int i = 0; i < quantity; i++) {
                                    DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getStockFromBarcodeNumber(itemBarCode, customerId, channelId);
                                    int saleId = (int) getStockFromBarcodeNumber.getData().get("sale_id");
                                    int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                    // Insert into sale items
                                    this.serviceDao.insert_sale_return_items(saleReturnId, productId, saleId, purchaseItemSerialId);
                                    this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(purchaseItemSerialId);
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
            grandTotal = grandTotal.add(shippingAmount);

            DataResult<Integer> saleReturnSeq = null;
            String storeName = (String) session.getAttribute("selectedStoreName");
            if (storeName != null && storeName.equals("AV MEDITECH PVT LTD KAITHAL")) {
                saleReturnSeq = serviceDao.getAutoSaleReturnAvtSeq();
            } else if (storeName != null && storeName.equals("LENSE HOME KAITHAL")) {
                saleReturnSeq = serviceDao.getAutoSaleReturnLhtSeq();
            } else if (storeName != null && storeName.equals("AV MEDITECH GURUGRAM")) {
                saleReturnSeq = serviceDao.getAutoSaleReturnSeq();
            } else if (storeName != null && storeName.equals("AV MEDITECH KAITHAL")) {
                saleReturnSeq = serviceDao.getAutoSaleReturnAmkSeq();
            }
            // Update the sale with calculated totals
            DataResult<Integer> updateSaleResult = this.serviceDao.update_sale_return(saleReturnId, subTotalAmount,
                    gstAmount, grandTotal, saleReturnSeq.getData());
            if (!updateSaleResult.isSuccess()) {
                return new ErrorDataResult<>("Failed to update sale: ");
            }

            return new SuccessDataResult<>(saleReturnId, "Sale Return created successfully.");
        } catch (ParseException e) {
            return new ErrorDataResult<>("Invalid date format");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while processing the sale.");
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
    public DataResult<List<Response>> getSaleReturnList(int channelId, Date startDate, Date endDate, String customerName, String productName, Integer pageNumber, Integer pageSize) {
        try {
            // Retrieve sale return data from the service layer
            DataResult<List<Map<String, Object>>> saleListResult = this.serviceDao.fetchSaleReturnList(channelId, pageNumber, pageSize, startDate, endDate, customerName, productName);

            // Check if the sale return data retrieval was successful
            if (!saleListResult.isSuccess()) {
                // If not successful, return an error data result with an appropriate message
                return new ErrorDataResult<>(null, "Failed to retrieve sale return data: " + saleListResult.getMessage());
            }

            // If sale return data retrieval was successful, proceed to process the data
            List<Map<String, Object>> productDataList = saleListResult.getData();
            List<Response> responseList = new ArrayList<>();

            // Process each map of sale return data into Response objects
            for (Map<String, Object> productData : productDataList) {
                // Extract data from the map
                Response response = new Response();
                response.setId((Integer) productData.get("id"));
                response.setAutoId((Integer) productData.get("auto_sale_return_id"));
                response.setCompanyName((String) productData.get("company_name"));
                response.setReferenceNumber((String) productData.get("reference_no"));
                response.setRemarks((String) productData.get("remarks"));
                try {
                    response.setQuantity(((Long) productData.get("total_quantity")).intValue());
                } catch (Exception e) {
                    response.setQuantity(0);
                }

                // Convert and set return_date as a String
                Date returnDate = (Date) productData.get("return_date");
                if (returnDate != null) {
                    response.setDate(returnDate.toString()); // or format it as per your requirement
                }

                // Add the response object to the list
                responseList.add(response);
            }

            // Return success data result with the processed response list
            return new SuccessDataResult<>(responseList, "Successfully retrieved and processed sale return data.");
        } catch (Exception e) {
            // If any unexpected error occurs, return an error data result with an appropriate message
            return new ErrorDataResult<>(null, "An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public DataResult<Response> getStockDetailsById(Integer saleId, int channelId) {
        try {
            // Retrieve sale master details
            DataResult<Map<String, Object>> saleMasterDetailsResult = this.serviceDao.getSaleReturnDetailsBySaleId(saleId, channelId);
            if (!saleMasterDetailsResult.isSuccess()) {
                // Return error if unable to retrieve sale master details
                return new ErrorDataResult<>(null, "Failed to retrieve sale master details: " + saleMasterDetailsResult.getMessage());
            }

            Sale saleMaster = mapToSaleMaster(saleMasterDetailsResult.getData());
            List<PurchaseItemSerialMaster> purchaseItemSerialMasters = new ArrayList<>();
            Integer saleItemId = saleMaster.getSaleId();

            // Retrieve sale items details
            DataResult<List<Map<String, Object>>> saleItemsDetailsResult = this.serviceDao.getSaleReturnItemsDetailsBySaleId(saleId);

            if (!saleItemsDetailsResult.isSuccess()) {
                // Return error if unable to retrieve sale items details
                return new ErrorDataResult<>(null, "Failed to retrieve sale items details: ");
            }

            List<SaleItemsMaster> saleItemsMasterList = mapToSaleItemsMasterList(saleItemsDetailsResult.getData());

            // Retrieve purchase item serial master details using the saleItemId
            DataResult<List<Map<String, Object>>> purchaseItemSerialMasterDetailsResult = this.serviceDao.getPurchaseItemSerialMasterDetailsBySaleReturnItemsId(saleItemId);
            List<Map<String, Object>> purchaseItemSerialMasterDetails = purchaseItemSerialMasterDetailsResult.getData();

            purchaseItemSerialMasterDetails.stream().map(detail -> {
                PurchaseItemSerialMaster purchaseItemSerialMaster = new PurchaseItemSerialMaster();
                purchaseItemSerialMaster.setItemBarcode((String) detail.get("item_barcode"));
                purchaseItemSerialMaster.setProductName((String) detail.get("name"));
                if (null != (String) detail.get("item_serial_number")) {
                    purchaseItemSerialMaster.setItemSerialNumber((String) detail.get("item_serial_number"));
                } else {
                    purchaseItemSerialMaster.setItemSerialNumber((String) detail.get("item_batch"));
                }

                purchaseItemSerialMaster.setProductId((Integer) detail.get("id"));
                // Ensure proper casting to java.sql.Date
                java.sql.Date itemSerialExpiryDate = (java.sql.Date) detail.get("item_serial_expiry_date");
                purchaseItemSerialMaster.setItemSerialExpiryDate(itemSerialExpiryDate);
                return purchaseItemSerialMaster;
            }).forEachOrdered(purchaseItemSerialMaster -> {
                purchaseItemSerialMasters.add(purchaseItemSerialMaster);
            });

            // Construct and return response
            Response response = new Response();
            response.setSale(saleMaster);
            response.setSaleItemsMasters(saleItemsMasterList);
            response.setPurchaseItemSerialMasters(purchaseItemSerialMasters);
            return new SuccessDataResult<>(response, "Sale details retrieved successfully.");

        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "An error occurred while retrieving sale details: " + e.getMessage());
        }
    }

    @Override
    public Result deleteStockDetailsById(Integer id, int channelId) {
        try {
            DataResult<List<Map<String, Object>>> itemData = this.serviceDao.get_item_return_data(id);
            if (itemData.isSuccess()) {
                List<Map<String, Object>> data = itemData.getData();
                for (int i = 0; i < data.size(); i++) {
                    int purchaseSerialId = (Integer) data.get(i).get("purchase_item_serial_id");
                    int saleId = (Integer) data.get(i).get("sale_id");
                    int updatePurchaseSaleReturn = serviceDao.updatePurchaseSaleReturn(purchaseSerialId, saleId);
                    // You might want to handle the result of updatePurchaseSaleReturn
                }
                int deleteSaleReturnItems = serviceDao.deleteSaleReturnItems(id);
                int deleteSaleReturnMaster = serviceDao.deleteSaleReturnMaster(id);

                if (deleteSaleReturnItems > 0 && deleteSaleReturnMaster > 0) {
                    return new SuccessResult("Stock details deleted successfully.");
                } else {
                    return new ErrorResult("Failed to delete some stock details. Please check the records.");
                }
            } else {
                return new ErrorResult("Failed to retrieve item return data.");
            }
        } catch (Exception e) {
            return new ErrorResult("An error occurred while deleting stock details.");
        }
    }

    @Override
    public DataResult<Integer> updateStockCreate(Sale sale, String[] purchaseData, String[] purchaseSerialData,
            Date saleReturnDate, int returnUserId, int channelId) {

        DataResult<Integer> updateSaleReturnDataResult;
        Integer saleReturnId = sale.getSaleId();

        int saleId1 = this.serviceDao.isSaleReturnPossible(saleReturnId);
        if (saleId1 != 0) {
            return new ErrorDataResult<>("This sale return is already associated with other sale");
        }
        // Update sale return data
        updateSaleReturnDataResult = this.serviceDao.updateSaleReturnData(sale.getSaleId(),
                sale.getReferenceNo(), sale.getRemarks(), sale.getCustomerId(), saleReturnDate, returnUserId);
        try {
            DataResult<List<Map<String, Object>>> itemData = this.serviceDao.get_item_return_data(sale.getSaleId());
            if (itemData.isSuccess()) {
                List<Map<String, Object>> data = itemData.getData();
                for (int i = 0; i < data.size(); i++) {
                    int purchaseSerialId = (Integer) data.get(i).get("purchase_item_serial_id");
                    int saleReturnId1 = (Integer) data.get(i).get("sale_id");
                    serviceDao.updatePurchaseSaleReturn(purchaseSerialId, saleReturnId1);
                    // You might want to handle the result of updatePurchaseSaleReturn
                }
                int deleteSaleReturnItems = serviceDao.deleteSaleReturnItems(sale.getSaleId());
                BigDecimal subTotalAmount = BigDecimal.ZERO;
                BigDecimal gstAmount = BigDecimal.ZERO;
                BigDecimal discountAmount = BigDecimal.ZERO;
                BigDecimal grandTotal = BigDecimal.ZERO;
                if (deleteSaleReturnItems > 0) {
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
                                        DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getStockFromBarcodeNumber(serialData.getItemBarcode(), sale.getCustomerId(), channelId);
                                        int saleId = (int) getStockFromBarcodeNumber.getData().get("sale_id");
                                        int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                        // Insert into sale items
                                        this.serviceDao.insert_sale_return_items(saleReturnId, productId, saleId, purchaseItemSerialId);
                                        // Update into purchase item serial master
                                        DataResult<Integer> updateSaleIdForPurchaseItemSerialMaster = this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(purchaseItemSerialId);
                                        if (!updateSaleIdForPurchaseItemSerialMaster.isSuccess()) {
                                            return new ErrorDataResult<>("Failed to update purchase items serial master.");
                                        }
                                        Integer purchaseItemSerialMasterId = updateSaleIdForPurchaseItemSerialMaster.getData();
                                        DataResult<Integer> insertStockMovmentHistory = this.serviceDao.insertStockMovmentHistory(purchaseItemSerialMasterId, productId, Constant.ACTION_TYPE_SALE_RETURN, saleReturnDate, saleReturnId);
                                        if (!insertStockMovmentHistory.isSuccess()) {
                                            return new ErrorDataResult<>("Failed to insert stock movment history.");
                                        }
                                    }
                                } else {
                                    if (product.isBatchSerialNo()) {
                                        String batchSerialNo = parts[9];
                                        for (int i = 0; i < quantity; i++) {
                                            DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getStockFromBarcodeNumber(itemBarCode, sale.getCustomerId(), batchSerialNo);
                                            int saleId = (int) getStockFromBarcodeNumber.getData().get("sale_id");
                                            int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                            // Insert into sale items
                                            this.serviceDao.insert_sale_return_items(saleReturnId, productId, saleId, purchaseItemSerialId);
                                            this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(purchaseItemSerialId);
                                        }
                                    } else {
                                        for (int i = 0; i < quantity; i++) {
                                            DataResult<Map<String, Object>> getStockFromBarcodeNumber = this.serviceDao.getStockFromBarcodeNumber(itemBarCode, sale.getCustomerId(), channelId);
                                            int saleId = (int) getStockFromBarcodeNumber.getData().get("sale_id");
                                            int purchaseItemSerialId = (int) getStockFromBarcodeNumber.getData().get("purchase_item_serial_master_id");
                                            // Insert into sale items
                                            this.serviceDao.insert_sale_return_items(saleReturnId, productId, saleId, purchaseItemSerialId);
                                            this.serviceDao.updateSaleIdForPurchaseItemSerialMaster(purchaseItemSerialId);
                                        }
                                    }
                                }
                            } catch (NumberFormatException e) {
                                return new ErrorDataResult<>("Invalid format for product ID, quantity, or discount.");
                            } catch (ParseException ex) {
                                Logger.getLogger(StockServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            return new ErrorDataResult<>("Invalid data format in row: " + rowData);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return updateSaleReturnDataResult;
    }
    // Helper method to map sale details to Sale object

    private Sale mapToSaleMaster(Map<String, Object> saleDetails) {
        Sale sale = new Sale();
        sale.setSaleId((Integer) saleDetails.get("id"));
        sale.setSaleReturnSeqId((Integer) saleDetails.get("auto_sale_return_id"));
        sale.setCustomerId((Integer) saleDetails.get("customer_id"));
        sale.setSaleDate((Date) saleDetails.get("return_date"));
        sale.setSubTotalAmount((BigDecimal) saleDetails.get("amount"));
        sale.setGstAmount((BigDecimal) saleDetails.get("gst_amount"));
        sale.setGrandTotal((BigDecimal) saleDetails.get("total_amount"));
        sale.setReferenceNo((String) saleDetails.get("reference_no"));
        sale.setSalestatus((String) saleDetails.get("remarks"));
        sale.setName((String) saleDetails.get("company_name"));
        return sale;
    }

    // Helper method to map sale items master details to a list of SaleItemsMaster objects
    private List<SaleItemsMaster> mapToSaleItemsMasterList(List<Map<String, Object>> saleItemsDetails) {
        List<SaleItemsMaster> saleItemsMasterList = new ArrayList<>();
        for (Map<String, Object> itemDetail : saleItemsDetails) {
            SaleItemsMaster saleItem = new SaleItemsMaster();
            saleItem.setProductId((Integer) itemDetail.get("id"));
            saleItem.setQuantity(((Long) itemDetail.get("quantity")).intValue());
            saleItem.setProductName((String) itemDetail.get("name"));
            saleItem.setTrackingSerialNo((boolean) itemDetail.get("tracking_serial_no"));
            saleItem.setIsBatch((boolean) itemDetail.get("is_batch"));
            saleItem.setItemBarCode((String) itemDetail.get("sku"));
            saleItem.setItemSerialNo((String) itemDetail.get("item_batch"));
            saleItemsMasterList.add(saleItem);
        }
        return saleItemsMasterList;
    }

    private List<PurchaseItemSerialMaster> getSerialDataForProduct(String[] purchaseSerialData, String productId) throws ParseException {
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
}
