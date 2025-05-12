/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.dtos;

import com.AvMeditechInventory.entities.PurchaseItemSerialMaster;
import com.AvMeditechInventory.entities.PurchaseMaster;
import com.AvMeditechInventory.entities.PurchaseMasterItems;
import com.AvMeditechInventory.entities.Sale;
import com.AvMeditechInventory.entities.SaleItemsMaster;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class Response {

    private Integer id;
    private Integer autoId;
    private String firstName;
    private String lastName;
    private String purchaseStatus;
    private BigDecimal totalAmount;
    private String categoryName;

    // Stock related fields
    private String stockNumber;
    private String itemDescription;
    private String productName;
    private String productTypeName;
    private String date;
    private int quantity;
    private String serialNumber;
    private String expiryDate;
    private String referenceNumber;
    private BigDecimal amount;
    private BigDecimal gst;
    private BigDecimal shipping;
    private String companyName;
    private String sku;
    private String itemBarCode;
    private Date itemSerialExpiry;
    private Integer saleId;
    private String fullName;
    private BigDecimal costPrice;
    private BigDecimal gstPercent;
    private BigDecimal discount;
    private Integer productId;
    private boolean trackingSerialNo;
    private boolean batchSerialNo;
    private BigDecimal gstAmount;
    private String status;
    private String userCompanyName;

    private BigDecimal sub_total_amount;
    private PurchaseMaster purchaseMasters;
    private List<PurchaseMasterItems> purchaseMasterItemses;
    private List<PurchaseItemSerialMaster> purchaseItemSerialMasters;
    private Sale sale;
    private List<SaleItemsMaster> saleItemsMasters;
    private String email;
    private String mobileNo;
    private Date saleDate;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String postalCode;
    private String country;
    private String countryArea;
    private SaleItemsMaster saleItemsMaster;
    private PurchaseItemSerialMaster purchaseItemSerialMaster;
    private String channelName;
    private String remarks;
    private Integer saleItemsId;
    private String userCode;
    private Integer seqId;
    private String batchNo;
    private String warrantyValidDateStr;
    private String amcValidDateStr;
    private String saleDateStr;
    private Integer saleServiceId;
    private Date serviceDate;
    private String solution;
    private String attachment;
    private Date warrantyValidDate;
    private Date amcValidDate;
    private Integer purchaseId;
    private Integer purchaseMasterItemsId;
    private Integer purchaseItemSerialMasterId;

    private String warning;

    private String saleType;

    public Response() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
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

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getGst() {
        return gst;
    }

    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public BigDecimal getShipping() {
        return shipping;
    }

    public void setShipping(BigDecimal shipping) {
        this.shipping = shipping;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getItemBarCode() {
        return itemBarCode;
    }

    public void setItemBarCode(String itemBarCode) {
        this.itemBarCode = itemBarCode;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getGstPercent() {
        return gstPercent;
    }

    public void setGstPercent(BigDecimal gstPercent) {
        this.gstPercent = gstPercent;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public boolean isTrackingSerialNo() {
        return trackingSerialNo;
    }

    public void setTrackingSerialNo(boolean trackingSerialNo) {
        this.trackingSerialNo = trackingSerialNo;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getItemSerialExpiry() {
        return itemSerialExpiry;
    }

    public void setItemSerialExpiry(Date itemSerialExpiry) {
        this.itemSerialExpiry = itemSerialExpiry;
    }

    public BigDecimal getSub_total_amount() {
        return sub_total_amount;
    }

    public void setSub_total_amount(BigDecimal sub_total_amount) {
        this.sub_total_amount = sub_total_amount;
    }

    public PurchaseMaster getPurchaseMasters() {
        return purchaseMasters;
    }

    public void setPurchaseMasters(PurchaseMaster purchaseMasters) {
        this.purchaseMasters = purchaseMasters;
    }

    public List<PurchaseMasterItems> getPurchaseMasterItemses() {
        return purchaseMasterItemses;
    }

    public void setPurchaseMasterItemses(List<PurchaseMasterItems> purchaseMasterItemses) {
        this.purchaseMasterItemses = purchaseMasterItemses;
    }

    public List<PurchaseItemSerialMaster> getPurchaseItemSerialMasters() {
        return purchaseItemSerialMasters;
    }

    public void setPurchaseItemSerialMasters(List<PurchaseItemSerialMaster> purchaseItemSerialMasters) {
        this.purchaseItemSerialMasters = purchaseItemSerialMasters;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public List<SaleItemsMaster> getSaleItemsMasters() {
        return saleItemsMasters;
    }

    public void setSaleItemsMasters(List<SaleItemsMaster> saleItemsMasters) {
        this.saleItemsMasters = saleItemsMasters;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea;
    }

    public SaleItemsMaster getSaleItemsMaster() {
        return saleItemsMaster;
    }

    public void setSaleItemsMaster(SaleItemsMaster saleItemsMaster) {
        this.saleItemsMaster = saleItemsMaster;
    }

    public PurchaseItemSerialMaster getPurchaseItemSerialMaster() {
        return purchaseItemSerialMaster;
    }

    public void setPurchaseItemSerialMaster(PurchaseItemSerialMaster purchaseItemSerialMaster) {
        this.purchaseItemSerialMaster = purchaseItemSerialMaster;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getSaleItemsId() {
        return saleItemsId;
    }

    public void setSaleItemsId(Integer saleItemsId) {
        this.saleItemsId = saleItemsId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public boolean isBatchSerialNo() {
        return batchSerialNo;
    }

    public void setBatchSerialNo(boolean batchSerialNo) {
        this.batchSerialNo = batchSerialNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getWarrantyValidDateStr() {
        return warrantyValidDateStr;
    }

    public void setWarrantyValidDateStr(String warrantyValidDateStr) {
        this.warrantyValidDateStr = warrantyValidDateStr;
    }

    public String getAmcValidDateStr() {
        return amcValidDateStr;
    }

    public void setAmcValidDateStr(String amcValidDateStr) {
        this.amcValidDateStr = amcValidDateStr;
    }

    public String getSaleDateStr() {
        return saleDateStr;
    }

    public void setSaleDateStr(String saleDateStr) {
        this.saleDateStr = saleDateStr;
    }

    public Integer getSaleServiceId() {
        return saleServiceId;
    }

    public void setSaleServiceId(Integer saleServiceId) {
        this.saleServiceId = saleServiceId;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Date getWarrantyValidDate() {
        return warrantyValidDate;
    }

    public void setWarrantyValidDate(Date warrantyValidDate) {
        this.warrantyValidDate = warrantyValidDate;
    }

    public Date getAmcValidDate() {
        return amcValidDate;
    }

    public void setAmcValidDate(Date amcValidDate) {
        this.amcValidDate = amcValidDate;
    }

    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getPurchaseMasterItemsId() {
        return purchaseMasterItemsId;
    }

    public void setPurchaseMasterItemsId(Integer purchaseMasterItemsId) {
        this.purchaseMasterItemsId = purchaseMasterItemsId;
    }

    public Integer getPurchaseItemSerialMasterId() {
        return purchaseItemSerialMasterId;
    }

    public void setPurchaseItemSerialMasterId(Integer purchaseItemSerialMasterId) {
        this.purchaseItemSerialMasterId = purchaseItemSerialMasterId;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }
    
    

}
