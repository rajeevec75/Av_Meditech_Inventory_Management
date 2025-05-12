/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Entity
@Table(name = "purchase_item_serial_master")
public class PurchaseItemSerialMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PurchaseItemSerialMasterId;
    private int PurchaseMasterId;
    private int productId;
    private String status;
    private String productName;
    private String itemSerialNumber;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date itemSerialExpiryDate;
    private String expiryDate;
    private String itemBarcode;
    private Integer saleItemsId;

    public PurchaseItemSerialMaster() {
    }

    public int getPurchaseItemSerialMasterId() {
        return PurchaseItemSerialMasterId;
    }

    public void setPurchaseItemSerialMasterId(int PurchaseItemSerialMasterId) {
        this.PurchaseItemSerialMasterId = PurchaseItemSerialMasterId;
    }

    public int getPurchaseMasterId() {
        return PurchaseMasterId;
    }

    public void setPurchaseMasterId(int PurchaseMasterId) {
        this.PurchaseMasterId = PurchaseMasterId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSaleItemsId() {
        return saleItemsId;
    }

    public void setSaleItemsId(Integer saleItemsId) {
        this.saleItemsId = saleItemsId;
    }

}
