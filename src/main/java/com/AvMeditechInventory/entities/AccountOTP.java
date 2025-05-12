/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Entity
@Table(name = "account_otp")
public class AccountOTP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer otpId;

    @Column(name = "otp", length = 20)
    private Integer otpValue;

    @Column(name = "email", length = 50)
    private String mobileEmail;

    @Column(name = "status")
    private Integer otpStatus;

    // Constructors, getters, setters, and other methods can be added here
    public Integer getOtpId() {
        return otpId;
    }

    public void setOtpId(Integer otpId) {
        this.otpId = otpId;
    }

    public Integer getOtpValue() {
        return otpValue;
    }

    public void setOtpValue(Integer otpValue) {
        this.otpValue = otpValue;
    }

    public String getMobileEmail() {
        return mobileEmail;
    }

    public void setMobileEmail(String mobileEmail) {
        this.mobileEmail = mobileEmail;
    }

    public Integer getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(Integer otpStatus) {
        this.otpStatus = otpStatus;
    }

}
