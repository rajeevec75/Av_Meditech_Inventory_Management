/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.dtos;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class AddressDto {

    private String supplierId;
    private String customerId;
    private String addressId;
    private String streetAddress1;
    private String streetAddress2;
    private String countryArea;
    private String postalCode;
    private String city;
    private String country;

    public AddressDto(String customerId, String addressId, String streetAddress1, String streetAddress2, String countryArea, String postalCode, String city) {
        this.customerId = customerId;
        this.addressId = addressId;
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.countryArea = countryArea;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getSupplierId() {
        return supplierId;
    }

    // Getters and setters for all fields
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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

    public String getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
