/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.dtos;

import java.util.Date;

/**
 *
 * @author Rahul
 */
public class PurchaseItemSerialMasterDTO {

    private Integer purchaseItenSerialId;
    private Integer productMasterId;
    private String status;
    private Integer serialNo;
    private String barcode;
    private Date expiryDate;

    public PurchaseItemSerialMasterDTO(Integer purchaseItenSerialId, Integer productMasterId, String status, Integer serialNo, String barcode, Date expiryDate) {
        this.purchaseItenSerialId = purchaseItenSerialId;
        this.productMasterId = productMasterId;
        this.status = status;
        this.serialNo = serialNo;
        this.barcode = barcode;
        this.expiryDate = expiryDate;
    }

    public Integer getPurchaseItenSerialId() {
        return purchaseItenSerialId;
    }

    public void setPurchaseItenSerialId(Integer purchaseItenSerialId) {
        this.purchaseItenSerialId = purchaseItenSerialId;
    }

    public Integer getProductMasterId() {
        return productMasterId;
    }

    public void setProductMasterId(Integer productMasterId) {
        this.productMasterId = productMasterId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
