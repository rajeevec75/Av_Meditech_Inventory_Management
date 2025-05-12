/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.entities;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class ProductVariant {

    private String productVariantId;
    private String productVariantName;
    private String productVariantSku;

    public ProductVariant() {
        super();
    }

    public ProductVariant(String productVariantId, String productVariantName, String productVariantSku) {
        this.productVariantId = productVariantId;
        this.productVariantName = productVariantName;
        this.productVariantSku = productVariantSku;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    public String getProductVariantName() {
        return productVariantName;
    }

    public void setProductVariantName(String productVariantName) {
        this.productVariantName = productVariantName;
    }

    public String getProductVariantSku() {
        return productVariantSku;
    }

    public void setProductVariantSku(String productVariantSku) {
        this.productVariantSku = productVariantSku;
    }

}
