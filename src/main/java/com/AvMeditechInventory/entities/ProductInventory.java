/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Entity
@Table(name = "product_inventory")
public class ProductInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_inventory_id")
    private Integer productInventoryId;

    @Column(name = "item_description", length = 255)
    private String itemDescription;

    @Column(name = "product_name", length = 255)
    private String productName;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "power", precision = 10, scale = 2)
    private BigDecimal power;

    @Column(name = "sales_quantity")
    private Integer salesQuantity;

    @Column(name = "serial_number", length = 50)
    private String serialNumber;

    @Column(name = "expiry_date")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    // Constructors, getters, and setters
    public Integer getProductInventoryId() {
        return productInventoryId;
    }

    public void setProductInventoryId(Integer productInventoryId) {
        this.productInventoryId = productInventoryId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public Integer getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
