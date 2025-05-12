/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.dtos;

import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.entities.ProductVariant;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class ProductDto {

    private String productId;
    private String productName;
    private String productSku;
    private String productDescription;
    private Category category;
    private ProductType productType;
    private List<ProductVariant> productVariant;
    private boolean chargeTaxes;
    private double weight;
    private int rating;
    private float price;
    private float costPrice;
    // Other Field
    private String gst;
    private String snoPattern;
    private Map<String, Object> metadata;
    private String slug;
    private boolean trackingSerialNo;
    private boolean batchSerialNo;
    private int quantity;
    private String source;
    private String barCode;
    private String serialNo;
    private String expiryDate;
    private String cursor;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private boolean isProductService = false;
    private ProductVariant productVariant1;

    public ProductDto() {
        // Default constructor
    }

    public ProductDto(String productId, String productName, String productDescription, float price) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;

    }

    public ProductDto(String productId, String productName, String productDescription, float price, float costPrice, String gst, String snoPattern) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.costPrice = costPrice;
        this.gst = gst;
        this.snoPattern = snoPattern;

    }

    public ProductDto(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    // Getters and setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public boolean isChargeTaxes() {
        return chargeTaxes;
    }

    public void setChargeTaxes(boolean chargeTaxes) {
        this.chargeTaxes = chargeTaxes;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(String categoryId, String categoryName) {
        this.category = new Category(categoryId, categoryName);
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(String productTypeId, String productTypeName) {
        this.productType = new ProductType(productTypeId, productTypeName);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "ProductDto{"
                + "productId='" + productId + '\''
                + ", productName='" + productName + '\''
                + ", productDescription='" + productDescription + '\''
                + ", category=" + category
                + ", productType=" + productType
                + ", chargeTaxes=" + chargeTaxes
                + ", weight=" + weight
                + ", rating=" + rating
                + ", price=" + price
                + ", costPrice=" + costPrice
                + '}';
    }

    public List<ProductVariant> getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(List<ProductVariant> productVariant) {
        this.productVariant = productVariant;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getSnoPattern() {
        return snoPattern;
    }

    public void setSnoPattern(String snoPattern) {
        this.snoPattern = snoPattern;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean isTrackingSerialNo() {
        return trackingSerialNo;
    }

    public void setTrackingSerialNo(boolean trackingSerialNo) {
        this.trackingSerialNo = trackingSerialNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isBatchSerialNo() {
        return batchSerialNo;
    }

    public void setBatchSerialNo(boolean batchSerialNo) {
        this.batchSerialNo = batchSerialNo;
    }

    public boolean isIsProductService() {
        return isProductService;
    }

    public void setIsProductService(boolean isProductService) {
        this.isProductService = isProductService;
    }

    public ProductVariant getProductVariant1() {
        return productVariant1;
    }

    public void setProductVariant1(ProductVariant productVariant1) {
        this.productVariant1 = productVariant1;
    }

}
