/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.repository;

import com.AvMeditechInventory.entities.AccountOTP;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Repository
public interface AccountOTPRepository extends JpaRepository<AccountOTP, Integer> {

    @Query("SELECT a FROM AccountOTP a WHERE a.mobileEmail = :mobileEmail AND a.otpValue = :otpValue AND a.otpStatus = :otpStatus")
    AccountOTP findByMobileEmailAndOtpValueAndOtpStatus(@Param("mobileEmail") String mobileEmail, @Param("otpValue") Integer otpValue,
            @Param("otpStatus") Integer otpStatus);

    @Query("SELECT a FROM AccountOTP a WHERE a.mobileEmail = :mobileEmail AND a.otpValue = :otpValue")
    Optional<AccountOTP> findByMobileEmailAndOtpValueOptional(@Param("mobileEmail") String mobileEmail, @Param("otpValue") Integer otpValue);

    @Query("SELECT a FROM AccountOTP a WHERE a.mobileEmail = :mobileEmail AND a.otpValue = :otpValue")
    AccountOTP findByMobileEmailAndOtpValue(@Param("mobileEmail") String mobileEmail, @Param("otpValue") Integer otpValue);
}
