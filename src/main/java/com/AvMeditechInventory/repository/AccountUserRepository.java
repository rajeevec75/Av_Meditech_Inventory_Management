/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.repository;

import com.AvMeditechInventory.entities.AccountUser;
import java.util.List;
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
public interface AccountUserRepository extends JpaRepository<AccountUser, Integer> {

    @Query("SELECT au FROM AccountUser au WHERE au.email = :email AND au.userType = :userType")
    Optional<AccountUser> findByEmailAndUserType(@Param("email") String email, @Param("userType") String userType);

    @Query("SELECT au FROM AccountUser au WHERE au.email = :email")
    Optional<AccountUser> findByEmail(@Param("email") String email);

    @Query("SELECT au FROM AccountUser au WHERE au.userType = :userType AND au.mobile = :mobile")
    Optional<AccountUser> findByUserTypeAndMobile(@Param("userType") String userType, @Param("mobile") String mobile);

    @Query("SELECT u FROM AccountUser u WHERE u.email = :email AND u.password = :password")
    Optional<AccountUser> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM AccountUser u WHERE LOWER(u.companyName) LIKE LOWER(CONCAT('%', :companyName, '%'))")
    List<AccountUser> findByCompanyNameContainingIgnoreCase(@Param("companyName") String companyName);

    @Query("SELECT u FROM AccountUser u WHERE u.userCode = :userCode")
    Optional<AccountUser> findAccountUserByUserCode(@Param("userCode") String userCode);

    @Query("SELECT u FROM AccountUser u WHERE u.email = :email AND u.userCode = :userCode")
    Optional<AccountUser> findByEmailAndUserCode(@Param("email") String email, @Param("userCode") String userCode);

}
