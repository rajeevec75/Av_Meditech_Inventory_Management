/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.service.QuickSearchWithSerialNumberService;
import com.AvMeditechInventory.util.ServiceDao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class QuickSearchWithSerialNumberServiceImpl implements QuickSearchWithSerialNumberService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public DataResult<List<InventoryTransactionReport>> reteriveDataQuickSearchWithSerialNumber(String itemBarCode, int channelId) {
        try {
            // Fetch details from the service methods
            DataResult<List<Map<String, Object>>> purchaseDetails = this.serviceDao.retrievePurchaseByItemBarcode(itemBarCode, channelId);
            DataResult<List<Map<String, Object>>> saleDetails = this.serviceDao.retrieveSaleByItemBarcode(itemBarCode, channelId);
            DataResult<List<Map<String, Object>>> salesReturnDetails = this.serviceDao.retrieveSaleReturnByItemBarcode(itemBarCode, channelId);

            // Combine results into a single list
            List<Map<String, Object>> allDetails = new ArrayList<>();

            if (purchaseDetails.getData() != null) {
                for (Map<String, Object> map : purchaseDetails.getData()) {
                    if (map != null) {
                        allDetails.add(map);
                    }
                }
            }
            if (saleDetails.getData() != null) {
                for (Map<String, Object> map : saleDetails.getData()) {
                    if (map != null) {
                        allDetails.add(map);
                    }
                }
            }
            if (salesReturnDetails.getData() != null) {
                for (Map<String, Object> map : salesReturnDetails.getData()) {
                    if (map != null) {
                        allDetails.add(map);
                    }
                }
            }

            // Process combined results into InventoryTransactionReport objects
            List<InventoryTransactionReport> transactionReports = new ArrayList<>();
            for (Map<String, Object> map : allDetails) {
                if (map == null) {
                    continue; // Skip null maps
                }

                InventoryTransactionReport report = new InventoryTransactionReport();

                // Determine the source of data to identify transaction type
                if (salesReturnDetails.getData() != null && salesReturnDetails.getData().contains(map)) {
                    mapSaleReturnDetails(report, map);
                } else if (saleDetails.getData() != null && saleDetails.getData().contains(map)) {
                    mapSaleDetails(report, map);
                } else if (purchaseDetails.getData() != null && purchaseDetails.getData().contains(map)) {
                    mapPurchaseDetails(report, map);
                }

                transactionReports.add(report);
            }

            // Return results
            if (!transactionReports.isEmpty()) {
                return new SuccessDataResult<>(transactionReports, "Inventory transaction reports retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No inventory transaction details found for the given item serial number.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "Failed to retrieve inventory transaction reports: " + e.getMessage());
        }
    }

    // Method to map Purchase details
    private void mapPurchaseDetails(InventoryTransactionReport report, Map<String, Object> map) {
        report.setCompanyName((String) map.get("company_name"));
        report.setParticulars((String) map.get("full_name"));
        report.setMobileNumber((String) map.get("mobile_no"));
        report.setProductName((String) map.get("product_name"));
        report.setItemDescription((String) map.get("description_plaintext"));
        report.setItemBarcode((String) map.get("item_barcode"));
        report.setProductTypeName((String) map.get("product_type_name"));
        report.setBrandName((String) map.get("category_name"));
        report.setQuantity((Integer) map.get("quantity"));

        report.setItemSerialExpiryDate((Date) map.get("item_serial_expiry_date"));
        report.setAmount((BigDecimal) map.get("grand_total"));
        report.setId((Integer) map.get("uniqueId"));
        report.setVoucherDate((Date) map.get("purchase_date"));

        if (map.get("item_serial_number") == null) {
            report.setItemSerialNumber((String) map.get("item_batch") + " (" + (Integer) map.get("quantity") + ")");
        } else {
            report.setItemSerialNumber((String) map.get("item_serial_number"));
        }

        report.setVoucherType("Purchase");
    }

    // Method to map Sale details
    private void mapSaleDetails(InventoryTransactionReport report, Map<String, Object> map) {
        report.setCompanyName((String) map.get("company_name"));
        report.setParticulars((String) map.get("full_name"));
        report.setMobileNumber((String) map.get("mobile_no"));
        report.setProductName((String) map.get("product_name"));
        report.setItemDescription((String) map.get("description_plaintext"));
        report.setItemBarcode((String) map.get("item_barcode"));
        report.setProductTypeName((String) map.get("product_type_name"));
        report.setBrandName((String) map.get("category_name"));
        report.setQuantity((Integer) map.get("quantity"));

        report.setItemSerialExpiryDate((Date) map.get("item_serial_expiry_date"));
        report.setAmount((BigDecimal) map.get("grand_total"));
        report.setId((Integer) map.get("uniqueId"));
        report.setVoucherDate((Date) map.get("sale_date"));

        if (map.get("item_serial_number") == null) {
            report.setItemSerialNumber((String) map.get("item_batch") + " (" + (Integer) map.get("quantity") + ")");
        } else {
            report.setItemSerialNumber((String) map.get("item_serial_number"));
        }

        report.setVoucherType("Sale");
        String type = (String) map.get("sale_type");
        int id = (int) map.get("sale_id");

        // start channelId 1
        if (type.equalsIgnoreCase("Internal Transfer")) {

            Optional<Integer> channelIdBySaleId = this.serviceDao.getChannelIdBySaleId(id);

            if (channelIdBySaleId.isPresent()) {
                Integer channelId1 = channelIdBySaleId.get();
                Optional<String> channelNameByChannelId = this.serviceDao.getChannelNameByChannelId(channelId1);
                if (channelNameByChannelId.isPresent()) {
                    String channelName = channelNameByChannelId.get();
                    report.setVoucherType("Internal Transfer");
                    report.setCompanyName(channelName);
                }

            }
        }
    }

    // Method to map Sale Return details
    private void mapSaleReturnDetails(InventoryTransactionReport report, Map<String, Object> map) {
        report.setCompanyName((String) map.get("company_name"));
        report.setParticulars((String) map.get("full_name"));
        report.setMobileNumber((String) map.get("mobile_no"));
        report.setProductName((String) map.get("product_name"));
        report.setItemDescription((String) map.get("description_plaintext"));
        report.setItemBarcode((String) map.get("item_barcode"));
        report.setProductTypeName((String) map.get("product_type_name"));
        report.setBrandName((String) map.get("category_name"));
        report.setQuantity((Integer) map.get("quantity"));

        report.setItemSerialExpiryDate((Date) map.get("item_serial_expiry_date"));
        report.setAmount((BigDecimal) map.get("grand_total"));
        report.setId((Integer) map.get("uniqueId"));
        report.setVoucherDate((Date) map.get("return_date"));

        if (map.get("item_serial_number") == null) {
            report.setItemSerialNumber((String) map.get("item_batch") + " (" + (Integer) map.get("quantity") + ")");
        } else {
            report.setItemSerialNumber((String) map.get("item_serial_number"));
        }

        report.setVoucherType("Sale Return");
    }
}
