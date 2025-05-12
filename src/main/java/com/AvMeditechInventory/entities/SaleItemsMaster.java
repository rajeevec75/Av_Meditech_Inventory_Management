/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.entities;

import java.math.BigDecimal;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class SaleItemsMaster {

    private Integer saleItemsId;
    private Integer saleId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal costPrice;
    private BigDecimal discount;
    private BigDecimal gstPercent;
    private BigDecimal gstAmount;
    private BigDecimal totalAmount;
    private String status;
    // Additional field
    private String productName;
    private boolean trackingSerialNo;
    private boolean isBatch;
    private String itemBarCode;
    private String itemSerialNo;

    public Integer getSaleItemsId() {
        return saleItemsId;
    }

    public void setSaleItemsId(Integer saleItemsId) {
        this.saleItemsId = saleItemsId;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getGstPercent() {
        return gstPercent;
    }

    public void setGstPercent(BigDecimal gstPercent) {
        this.gstPercent = gstPercent;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isTrackingSerialNo() {
        return trackingSerialNo;
    }

    public void setTrackingSerialNo(boolean trackingSerialNo) {
        this.trackingSerialNo = trackingSerialNo;
    }

    public boolean isIsBatch() {
        return isBatch;
    }

    public void setIsBatch(boolean isBatch) {
        this.isBatch = isBatch;
    }

    public String getItemBarCode() {
        return itemBarCode;
    }

    public void setItemBarCode(String itemBarCode) {
        this.itemBarCode = itemBarCode;
    }

    public String getItemSerialNo() {
        return itemSerialNo;
    }

    public void setItemSerialNo(String itemSerialNo) {
        this.itemSerialNo = itemSerialNo;
    }

}
