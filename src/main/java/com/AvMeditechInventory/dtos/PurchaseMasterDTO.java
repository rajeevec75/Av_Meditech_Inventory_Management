/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.dtos;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
import java.math.BigDecimal;
import java.util.Date;

public class PurchaseMasterDTO {

    private Integer purchaseId;
    private Integer userId;
    private Integer productId;
    private String purchaseStatus;
    private BigDecimal subTotalAmount;
    private BigDecimal gstAmount;
    private BigDecimal discountAmount;
    private BigDecimal shippingAmount;
    private BigDecimal grandTotal;
    private String referenceNo;
    private Date purchaseDate; // Add a field for purchase date

    // Constructors, getters, and setters
    public PurchaseMasterDTO() {
    }

    public PurchaseMasterDTO(Integer userId, Integer productId, String purchaseStatus, BigDecimal subTotalAmount,
            BigDecimal gstAmount, BigDecimal discountAmount, BigDecimal shippingAmount,
            BigDecimal grandTotal, String referenceNo, Date purchaseDate) {
        this.userId = userId;
        this.productId = productId;
        this.subTotalAmount = subTotalAmount;
        this.gstAmount = gstAmount;
        this.discountAmount = discountAmount;
        this.shippingAmount = shippingAmount;
        this.grandTotal = grandTotal;
        this.purchaseStatus = purchaseStatus;
        this.referenceNo = referenceNo;
        this.purchaseDate = purchaseDate;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(BigDecimal subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

}
