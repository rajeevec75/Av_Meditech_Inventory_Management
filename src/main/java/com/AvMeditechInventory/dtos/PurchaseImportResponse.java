/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rahul
 */
public class PurchaseImportResponse {

    private List<ProductDto> productPurchase;
    private ArrayList<String> serialPurchase;
    private List<Response> productSale;
    private String serResp;

    public List<ProductDto> getProductPurchase() {
        return productPurchase;
    }

    public void setProductPurchase(List<ProductDto> productPurchase) {
        this.productPurchase = productPurchase;
    }

    public ArrayList<String> getSerialPurchase() {
        return serialPurchase;
    }

    public void setSerialPurchase(ArrayList<String> serialPurchase) {
        this.serialPurchase = serialPurchase;
    }

    public List<Response> getProductSale() {
        return productSale;
    }

    public void setProductSale(List<Response> productSale) {
        this.productSale = productSale;
    }

    public String getSerResp() {
        return serResp;
    }

    public void setSerResp(String serResp) {
        this.serResp = serResp;
    }
}
