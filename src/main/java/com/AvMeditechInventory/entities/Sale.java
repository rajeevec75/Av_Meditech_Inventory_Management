/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limuted)
 */
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saleId;
    private Integer customerId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date saleDate;
    private BigDecimal subTotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal gstAmount;
    private BigDecimal shippingAmount;
    private BigDecimal grandTotal;
    private String referenceNo;
    private String salestatus;
    private String remarks;
    private Integer saleSeqId;
    private Integer saleReturnSeqId;
    private Integer channelId1;
    private String saleType;

    // Additional Field
    private String name;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(BigDecimal subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
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

    public String getSalestatus() {
        return salestatus;
    }

    public void setSalestatus(String salestatus) {
        this.salestatus = salestatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSaleSeqId() {
        return saleSeqId;
    }

    public void setSaleSeqId(Integer saleSeqId) {
        this.saleSeqId = saleSeqId;
    }

    public Integer getSaleReturnSeqId() {
        return saleReturnSeqId;
    }

    public void setSaleReturnSeqId(Integer saleReturnSeqId) {
        this.saleReturnSeqId = saleReturnSeqId;
    }

    public Integer getChannelId1() {
        return channelId1;
    }

    public void setChannelId1(Integer channelId1) {
        this.channelId1 = channelId1;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

}
