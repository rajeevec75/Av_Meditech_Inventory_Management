/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
//import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Entity
@Table(name = "account_user")
public class AccountUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 255)
    private Integer userId;

    @Column(name = "is_superuser", columnDefinition = "boolean default false")
    private boolean superuser = false;

    @Column(name = "email")
    private String email;

    @Column(name = "is_staff", columnDefinition = "boolean default false")
    private boolean staff = false;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private boolean active = true;

    @Column(name = "password")
    private String password;

    @Column(name = "date_joined")
    private Timestamp dateJoined;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "default_billing_address_id")
    private Integer defaultBillingAddressId;

    @Column(name = "default_shipping_address_id")
    private Integer defaultShippingAddressId;

    @Column(name = "note")
    private String TextNote;

    @Column(name = "first_name", length = 256)
    private String firstName;

    @Column(name = "last_name", length = 256)
    private String lastName;

    @Column(name = "avatar", length = 100)
    private String avatar;

    @Column(name = "private_metadata", columnDefinition = "jsonb")
    private String privateMetaData;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metaData;

    @Column(name = "jwt_token_key", length = 12)
    private String jwtTokenKey;

    @Column(name = "language_code", length = 35)
    private String languageCode;

    @Column(name = "search_document")
    private String searchDocument;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "external_reference", length = 250)
    private String externalReference;

    @Column(name = "last_password_reset_request")
    private Timestamp lastPasswordResetRequest;

    @Column(name = "age", length = 512)
    private String age;

    @Column(name = "country_code", length = 20)
    private String countryCode;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "gender", length = 512)
    private String gender;

    @Column(name = "language_preference", length = 512)
    private String languagePreference;

    @Column(name = "mobile_no")
    private String mobile;

    @Column(name = "mobile_verified")
    private boolean mobileVerified;

    @Column(name = "user_type", length = 50)
    private String userType;

    @Column(name = "company_name", length = 255)
    private String companyName;

    @Column(name = "user_code", length = 256)
    private String userCode;

    // Getters and setters
    public AccountUser() {
    }

    public AccountUser(final int userId, final String email, final String mobile, final String password, boolean superuser, boolean staff,
            Timestamp dateJoined, String firstName, String lastName, String languageCode, String jwtTokenKey, String searchDocument,
            Timestamp updatedAt, UUID uuid) {
        this.userId = userId;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.superuser = superuser;
        this.staff = staff;
        this.dateJoined = dateJoined;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jwtTokenKey = jwtTokenKey;
        this.languageCode = languageCode;
        this.searchDocument = searchDocument;
        this.updatedAt = updatedAt;
        this.uuid = uuid;

    }

    // Modified getter for updatedAt to return a formatted date and time string
    public String getUpdatedAt() {
        if (updatedAt != null) {
            Date date = new Date(updatedAt.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } else {
            return null; // or an appropriate default value
        }
    }

    // Modified getter for dateJoined to return only the date as a formatted string
    public String getDateJoined() {
        if (dateJoined != null) {
            Date date = new Date(dateJoined.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } else {
            return null; // or an appropriate default value
        }
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isSuperuser() {
        return superuser;
    }

    public void setSuperuser(boolean superuser) {
        this.superuser = superuser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateJoined(Timestamp dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getDefaultBillingAddressId() {
        return defaultBillingAddressId;
    }

    public void setDefaultBillingAddressId(Integer defaultBillingAddressId) {
        this.defaultBillingAddressId = defaultBillingAddressId;
    }

    public Integer getDefaultShippingAddressId() {
        return defaultShippingAddressId;
    }

    public void setDefaultShippingAddressId(Integer defaultShippingAddressId) {
        this.defaultShippingAddressId = defaultShippingAddressId;
    }

    public String getTextNote() {
        return TextNote;
    }

    public void setTextNote(String textNote) {
        TextNote = textNote;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPrivateMetaData() {
        return privateMetaData;
    }

    public void setPrivateMetaData(String privateMetaData) {
        this.privateMetaData = privateMetaData;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getJwtTokenKey() {
        return jwtTokenKey;
    }

    public void setJwtTokenKey(String jwtTokenKey) {
        this.jwtTokenKey = jwtTokenKey;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getSearchDocument() {
        return searchDocument;
    }

    public void setSearchDocument(String searchDocument) {
        this.searchDocument = searchDocument;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public Timestamp getLastPasswordResetRequest() {
        return lastPasswordResetRequest;
    }

    public void setLastPasswordResetRequest(Timestamp lastPasswordResetRequest) {
        this.lastPasswordResetRequest = lastPasswordResetRequest;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    
    

}
