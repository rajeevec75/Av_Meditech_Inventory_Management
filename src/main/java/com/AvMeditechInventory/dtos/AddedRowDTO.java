/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.dtos;

import java.math.BigDecimal;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class AddedRowDTO {

    private Integer productId;
    private String productName;
    private int qty;
    private BigDecimal purchasePrice;
    private BigDecimal discount;
    private BigDecimal taxPercentage;
    private BigDecimal taxAmount;
    private BigDecimal unitCost;
    private BigDecimal totalCost;

    public AddedRowDTO() {

    }

    // Getters and Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Product{"
                + "product='" + productName + '\''
                + ", qty=" + qty
                + ", purchasePrice=" + purchasePrice
                + ", discount=" + discount
                + ", taxPercentage=" + taxPercentage
                + ", taxAmount=" + taxAmount
                + ", unitCost=" + unitCost
                + ", totalCost=" + totalCost
                + '}';
    }
}
