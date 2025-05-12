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
import com.AvMeditechInventory.service.PartyReportService;
import com.AvMeditechInventory.util.Constant;
import com.AvMeditechInventory.util.ServiceDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class PartyReportServiceImpl implements PartyReportService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public DataResult<List<InventoryTransactionReport>> getPartyReportByDateRangeAndReportName(Date startDate, 
            Date endDate, String userType, String companyName, int channelId) {
        try {
            List<InventoryTransactionReport> reports = new ArrayList<>();

            if (companyName != null && !companyName.isEmpty()) {
                // Only retrieve reports based on companyName, ignoring userType
                DataResult<List<Map<String, Object>>> purchaseDetails = this.serviceDao.retrievePurchaseReportByUserType(
                        startDate, endDate, "", companyName, channelId);
                if (purchaseDetails != null && purchaseDetails.getData() != null && !purchaseDetails.getData().isEmpty()) {
                    reports.addAll(processPurchaseData(purchaseDetails.getData()));
                }

                DataResult<List<Map<String, Object>>> saleDetails = this.serviceDao.retrieveSaleReportByUserType(
                        startDate, endDate, "", companyName, channelId);
                if (saleDetails != null && saleDetails.getData() != null && !saleDetails.getData().isEmpty()) {
                    reports.addAll(processSaleData(saleDetails.getData()));
                }

                DataResult<List<Map<String, Object>>> salesReturnDetails = this.serviceDao.retrieveSaleReturnReportByUserType(
                        startDate, endDate, "", companyName, channelId);
                if (salesReturnDetails != null && salesReturnDetails.getData() != null && !salesReturnDetails.getData().isEmpty()) {
                    reports.addAll(processSaleReturnData(salesReturnDetails.getData()));
                }

            } else {
                // Handle cases where companyName is null, based on userType
                if (userType.equals(Constant.SUPPLIER)) {
                    DataResult<List<Map<String, Object>>> purchaseDetails = this.serviceDao.retrievePurchaseReportByUserType(
                            startDate, endDate, userType, "", channelId);
                    if (purchaseDetails != null && purchaseDetails.getData() != null && !purchaseDetails.getData().isEmpty()) {
                        reports.addAll(processPurchaseData(purchaseDetails.getData()));
                    }
                } else if (userType.equals(Constant.CUSTOMER)) {
                    DataResult<List<Map<String, Object>>> saleDetails = this.serviceDao.retrieveSaleReportByUserType(
                            startDate, endDate, userType, "", channelId);
                    if (saleDetails != null && saleDetails.getData() != null && !saleDetails.getData().isEmpty()) {
                        reports.addAll(processSaleData(saleDetails.getData()));
                    }

                    DataResult<List<Map<String, Object>>> salesReturnDetails = this.serviceDao.retrieveSaleReturnReportByUserType(
                            startDate, endDate, userType, "", channelId);
                    if (salesReturnDetails != null && salesReturnDetails.getData() != null && !salesReturnDetails.getData().isEmpty()) {
                        reports.addAll(processSaleReturnData(salesReturnDetails.getData()));
                    }

                } else if (userType.equals(Constant.BOTH)) {
                    DataResult<List<Map<String, Object>>> purchaseDetails = this.serviceDao.retrievePurchaseReportByUserType(
                            startDate, endDate, userType, "", channelId);
                    if (purchaseDetails != null && purchaseDetails.getData() != null && !purchaseDetails.getData().isEmpty()) {
                        reports.addAll(processPurchaseData(purchaseDetails.getData()));
                    }

                    DataResult<List<Map<String, Object>>> saleDetails = this.serviceDao.retrieveSaleReportByUserType(
                            startDate, endDate, userType, "", channelId);
                    if (saleDetails != null && saleDetails.getData() != null && !saleDetails.getData().isEmpty()) {
                        reports.addAll(processSaleData(saleDetails.getData()));
                    }

                    DataResult<List<Map<String, Object>>> salesReturnDetails = this.serviceDao.retrieveSaleReturnReportByUserType(
                            startDate, endDate, userType, "", channelId);
                    if (salesReturnDetails != null && salesReturnDetails.getData() != null && !salesReturnDetails.getData().isEmpty()) {
                        reports.addAll(processSaleReturnData(salesReturnDetails.getData()));
                    }
                }
            }

            if (!reports.isEmpty()) {
                return new SuccessDataResult<>(reports, "Inventory transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No inventory transaction details found for the given date range and user type.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "Failed to retrieve inventory transaction report: " + e.getMessage());
        }
    }

    // Separate method for processing purchase data
    private List<InventoryTransactionReport> processPurchaseData(List<Map<String, Object>> purchaseData) {
        List<InventoryTransactionReport> reports = new ArrayList<>();
        for (Map<String, Object> row : purchaseData) {
            InventoryTransactionReport report = new InventoryTransactionReport();
            report.setAutoId((Integer) row.get("auto_purchase_id"));
            report.setId((Integer) row.get("purchase_id"));
            report.setReferenceNumber((String) row.get("reference_no"));
            report.setRemarks((String) row.get("remarks"));
            report.setVoucherDate((Date) row.get("purchase_date"));
            report.setCompanyName((String) row.get("company_name"));
            report.setParticulars((String) row.get("full_name"));
            report.setVoucherType("Purchase");

            reports.add(report);
        }
        return reports;
    }

    // Separate method for processing sale data
    private List<InventoryTransactionReport> processSaleData(List<Map<String, Object>> saleData) {
        List<InventoryTransactionReport> reports = new ArrayList<>();
        for (Map<String, Object> row : saleData) {
            InventoryTransactionReport report = new InventoryTransactionReport();
            report.setAutoId((Integer) row.get("auto_sale_id"));
            report.setId((Integer) row.get("sale_id"));
            report.setVoucherDate((Date) row.get("sale_date"));
            report.setReferenceNumber((String) row.get("reference"));
            report.setRemarks((String) row.get("remarks"));
            report.setCompanyName((String) row.get("company_name"));
            report.setParticulars((String) row.get("full_name"));
            report.setVoucherType("Sale");
            reports.add(report);
        }
        return reports;
    }

    // Separate method for processing sale return data
    private List<InventoryTransactionReport> processSaleReturnData(List<Map<String, Object>> saleReturnData) {
        List<InventoryTransactionReport> reports = new ArrayList<>();
        for (Map<String, Object> row : saleReturnData) {
            InventoryTransactionReport report = new InventoryTransactionReport();
            report.setAutoId((Integer) row.get("auto_sale_return_id"));
            report.setId((Integer) row.get("id"));
            report.setVoucherDate((Date) row.get("return_date"));
            report.setReferenceNumber((String) row.get("reference_no"));
            report.setRemarks((String) row.get("remarks"));
            report.setCompanyName((String) row.get("company_name"));
            report.setParticulars((String) row.get("full_name"));
            report.setVoucherType("Sale Return");
            reports.add(report);
        }
        return reports;
    }

    @Override
    public DataResult<List<Response>> reterivePartyReportById(Integer id, String userType) {
        try {
            DataResult<List<Map<String, Object>>> result = null;

            // Retrieve data based on transactionType
            switch (userType) {
                case "Purchase":
                    result = this.serviceDao.fetchInventoryTransactionReportDetailsByPurchase(id);
                    break;
                case "Sale":
                    result = this.serviceDao.fetchInventoryTransactionReportDetailsBySale(id);
                    if (result.getData() == null) {
                        result = this.serviceDao.fetchInventoryTransactionDetailsBySaleId(id);
                    }
                    break;
                case "Sale Return":
                    result = this.serviceDao.fetchInventoryTransactionDetailsBySaleReturnId(id);
                    break;
                default:
                    return new ErrorDataResult<>(null, "Invalid transaction type provided: " + userType);
            }

            // Check if the result is successful
            if (result != null && result.isSuccess() && result.getData() != null) {
                List<Response> responseList = new ArrayList<>();
                for (Map<String, Object> data : result.getData()) {
                    responseList.add(mapToFetchInventoryTransactionReport(data, userType));
                }
                return new SuccessDataResult<>(responseList);
            } else {
                return new ErrorDataResult<>(null, "No data found for the provided transaction name and ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the inventory transaction report.");
        }
    }

    private Response mapToFetchInventoryTransactionReport(Map<String, Object> data, String userType) {
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
        switch (userType) {
            case "Purchase":
                mapPurchaseTransactionFields(report, data);
                break;
            case "Sale":
                mapSaleTransactionFields(report, data);
                break;
            case "Sale Return":
                mapSaleReturnTransactionFields(report, data);
                break;
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + userType);
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
