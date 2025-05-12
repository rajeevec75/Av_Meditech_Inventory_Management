/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.service.InventoryTransactionReportService;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class InventoryTransactionReportServiceImpl implements InventoryTransactionReportService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public DataResult<List<InventoryTransactionReport>> getInventoryTransactionReportByDateRangeAndTransactionName(
            Date startDate, Date endDate, String transactionName, String productName, String productTypeName, 
            String categoryName, int channelId) {
        try {
            // Validate parameters
            if (startDate == null || endDate == null || transactionName == null) {
                return new ErrorDataResult<>(null, "Invalid input parameters: startDate, endDate, or transactionName cannot be null.");
            }

            DataResult<List<Map<String, Object>>> result = null;

            // Retrieve data based on transactionName
            switch (transactionName.toLowerCase()) {
                case Constant.ACTION_TYPE_PURCHASE:
                    result = this.serviceDao.getInventoryTransactionReportByPurchase(startDate, endDate, 
                            productName, productTypeName, categoryName, channelId);
                    break;
                case Constant.ACTION_TYPE_SALE:
                    result = this.serviceDao.getInventoryTransactionReportBySale(startDate, endDate, productName, 
                            productTypeName, categoryName, "Sale Create", channelId);
                    break;
                case Constant.ACTION_TYPE_TRANSFER:
                    result = this.serviceDao.getInventoryTransactionReportBySale1(startDate, endDate, productName, 
                            productTypeName, categoryName, "Internal Transfer", channelId);
                    break;
                case Constant.ACTION_TYPE_TRANSFER_OUT:
                    result = this.serviceDao.getInventoryTransactionReportBySale(startDate, endDate, productName, 
                            productTypeName, categoryName, "Internal Transfer", channelId);
                    break;
                case Constant.ACTION_TYPE_SALE_RETURN:
                    result = this.serviceDao.getInventoryTransactionReportBySaleReturn(startDate, endDate, 
                            productName, productTypeName, categoryName, channelId);
                    break;
                default:
                    return new ErrorDataResult<>(null, "Invalid transaction name provided: " + transactionName);
            }

            if (result != null && result.getData() != null && !result.getData().isEmpty()) {
                // Map retrieved data to InventoryTransactionReport objects based on transaction type
                List<InventoryTransactionReport> reports = result.getData().stream()
                        .map(data -> mapToInventoryTransactionReport(data, transactionName))
                        .filter(report -> report != null) // Ensure no null reports are included
                        .collect(Collectors.toList());
                return new SuccessDataResult<>(reports, "Inventory transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No inventory transaction details found for the given date range and transaction name.");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "Failed to retrieve inventory transaction report: " + e.getMessage());
        }
    }

// Method to map data to InventoryTransactionReport based on transaction type
    private InventoryTransactionReport mapToInventoryTransactionReport(Map<String, Object> data, String transactionType) {
        InventoryTransactionReport report = new InventoryTransactionReport();
        switch (transactionType.toLowerCase()) {
            case Constant.ACTION_TYPE_PURCHASE:
                report.setAutoId((Integer) data.get("auto_purchase_id"));
                report.setId((Integer) data.get("purchase_id"));
                report.setVoucherDate((Date) data.get("purchase_date"));
                report.setReferenceNumber((String) data.get("reference_no"));
                report.setRemarks((String) data.get("remarks"));
                report.setCompanyName((String) data.get("company_name"));
                break;
            case Constant.ACTION_TYPE_SALE:
                report.setAutoId((Integer) data.get("auto_sale_id"));
                report.setId((Integer) data.get("sale_id"));
                report.setVoucherDate((Date) data.get("sale_date"));
                report.setReferenceNumber((String) data.get("reference"));
                report.setRemarks((String) data.get("remarks"));
                report.setCompanyName((String) data.get("company_name"));
                break;
            case Constant.ACTION_TYPE_TRANSFER:
                report.setAutoId((Integer) data.get("auto_sale_id"));
                report.setId((Integer) data.get("sale_id"));
                report.setVoucherDate((Date) data.get("sale_date"));
                report.setReferenceNumber((String) data.get("reference"));
                report.setRemarks((String) data.get("remarks"));
                report.setCompanyName((String) data.get("name"));
                break;
            case Constant.ACTION_TYPE_TRANSFER_OUT:
                report.setAutoId((Integer) data.get("auto_sale_id"));
                report.setId((Integer) data.get("sale_id"));
                report.setVoucherDate((Date) data.get("sale_date"));
                report.setReferenceNumber((String) data.get("reference"));
                report.setRemarks((String) data.get("remarks"));
                report.setCompanyName((String) data.get("name"));
                break;
            case Constant.ACTION_TYPE_SALE_RETURN:
                report.setAutoId((Integer) data.get("auto_sale_return_id"));
                report.setId((Integer) data.get("id"));
                report.setVoucherDate((Date) data.get("return_date"));
                report.setReferenceNumber((String) data.get("reference_no"));
                report.setRemarks((String) data.get("remarks"));
                report.setCompanyName((String) data.get("company_name"));
                break;
            default:
                // Handle unknown transaction types (though this case should not occur here)
                break;
        }
        return report;
    }

    @Override
    public DataResult<List<Response>> getInventoryTransactionReport(Integer id, String transactionName) {
        try {
            DataResult<List<Map<String, Object>>> result = null;

            // Retrieve data based on transactionName
            switch (transactionName.toLowerCase()) {
                case Constant.ACTION_TYPE_PURCHASE:
                    result = this.serviceDao.fetchInventoryTransactionReportDetailsByPurchase(id);
                    break;
                case Constant.ACTION_TYPE_SALE:
                    result = this.serviceDao.fetchInventoryTransactionReportDetailsBySale(id);
                    break;
                case Constant.ACTION_TYPE_TRANSFER:
                    result = this.serviceDao.fetchInventoryTransactionReportDetailsBySale(id);
                    break;
                case Constant.ACTION_TYPE_TRANSFER_OUT:
                    result = this.serviceDao.fetchInventoryTransactionReportDetailsBySale(id);
                    break;
                case Constant.ACTION_TYPE_SALE_RETURN:
                    result = this.serviceDao.fetchInventoryTransactionDetailsBySaleReturnId(id);
                    break;
                default:
                    return new ErrorDataResult<>(null, "Invalid transaction name provided: " + transactionName);
            }

            // Check if the result is successful
            if (result != null && result.isSuccess() && result.getData() != null) {
                List<Response> responseList = new ArrayList<>();
                for (Map<String, Object> data : result.getData()) {
                    responseList.add(mapToFetchInventoryTransactionReport(data, transactionName));
                }
                return new SuccessDataResult<>(responseList);
            } else {
                return new ErrorDataResult<>(null, "No data found for the provided transaction name and ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the inventory transaction report.");
        }
    }

    private Response mapToFetchInventoryTransactionReport(Map<String, Object> data, String transactionType) {
        Response report = new Response();

        // Common fields mapping
        report.setRemarks((String) data.get("remarks"));
        report.setCompanyName((String) data.get("company_name"));
        report.setProductName((String) data.get("product_name"));
        report.setCategoryName((String) data.get("category_name"));
        report.setProductTypeName((String) data.get("product_type_name"));
        report.setSku((String) data.get("sku"));

        // Check if the item is part of a batch
        boolean isBatch = (boolean) data.get("is_batch");
        if (isBatch) {
            report.setSerialNumber((String) data.get("item_batch"));
        } else {
            report.setSerialNumber((String) data.get("item_serial_number"));
        }

        report.setItemSerialExpiry((Date) data.get("item_serial_expiry_date"));
        Long quantityCount = (Long) data.get("quantity_count");
        report.setQuantity(quantityCount.intValue());

        // Transaction-specific fields mapping
        switch (transactionType.toLowerCase()) {
            case Constant.ACTION_TYPE_PURCHASE:
                mapPurchaseTransactionFields(report, data);
                break;
            case Constant.ACTION_TYPE_SALE:
                mapSaleTransactionFields(report, data);
                break;
            case Constant.ACTION_TYPE_TRANSFER:
                mapSaleTransactionFields(report, data);
                break;
            case Constant.ACTION_TYPE_TRANSFER_OUT:
                mapSaleTransactionFields(report, data);
                break;
            case Constant.ACTION_TYPE_SALE_RETURN:
                mapSaleReturnTransactionFields(report, data);
                break;
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + transactionType);
        }

        return report;
    }

    private void mapPurchaseTransactionFields(Response report, Map<String, Object> data) {
        report.setAutoId((Integer) data.get("auto_purchase_id"));
        report.setId((Integer) data.get("purchase_id"));
        report.setReferenceNumber((String) data.get("reference_no"));
    }

    private void mapSaleTransactionFields(Response report, Map<String, Object> data) {
        report.setAutoId((Integer) data.get("auto_sale_id"));
        report.setId((Integer) data.get("sale_id"));
        report.setReferenceNumber((String) data.get("reference"));
    }

    private void mapSaleReturnTransactionFields(Response report, Map<String, Object> data) {
        report.setAutoId((Integer) data.get("auto_sale_return_id"));
        report.setId((Integer) data.get("id"));
        report.setReferenceNumber((String) data.get("reference_no"));
    }

}
