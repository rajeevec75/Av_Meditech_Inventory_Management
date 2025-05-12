/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.entities;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
import java.math.BigDecimal;
import java.util.Date;

public class InventoryTransactionReport {

    private Integer id;
    private String voucherType;
    private Date voucherDate;
    private String voucherNumber;
    private String particulars;
    private String stockNumber;
    private String itemDescription;
    private String productName;
    private String productTypeName;
    private String brandName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal amount;
    private String itemSerialNumber;
    private Date itemSerialExpiryDate;
    // Extra Field
    private String actionType;
    private String itemBarcode;
    private String mobileNumber;
    private Integer autoId;
    private String companyName;
    private String referenceNumber;
    private String remarks;

    private Date warrantyValidDate;
    private Date amcValidDate;
    private Integer purchaseItemSerialMasterId;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getItemSerialNumber() {
        return itemSerialNumber;
    }

    public void setItemSerialNumber(String itemSerialNumber) {
        this.itemSerialNumber = itemSerialNumber;
    }

    public Date getItemSerialExpiryDate() {
        return itemSerialExpiryDate;
    }

    public void setItemSerialExpiryDate(Date itemSerialExpiryDate) {
        this.itemSerialExpiryDate = itemSerialExpiryDate;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getWarrantyValidDate() {
        return warrantyValidDate;
    }

    public void setWarrantyValidDate(Date warrantyValidDate) {
        this.warrantyValidDate = warrantyValidDate;
    }

    public Date getAmcValidDate() {
        return amcValidDate;
    }

    public void setAmcValidDate(Date amcValidDate) {
        this.amcValidDate = amcValidDate;
    }

    public Integer getPurchaseItemSerialMasterId() {
        return purchaseItemSerialMasterId;
    }

    public void setPurchaseItemSerialMasterId(Integer purchaseItemSerialMasterId) {
        this.purchaseItemSerialMasterId = purchaseItemSerialMasterId;
    }

}
