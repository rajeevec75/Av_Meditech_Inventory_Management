/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.repository;

import com.AvMeditechInventory.entities.PurchaseMaster;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rajeev kumar
 */
@Repository
public interface PurchaseMasterRepository extends JpaRepository<PurchaseMaster, Long> {

    @Query(value = "INSERT INTO purchase_master (user_id, product_id, purchase_quantity, purchase_date, purchase_status, purchase_total_amount) "
            + "VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    public PurchaseMaster insertPurchase(Integer userId, Integer productId, Integer purchaseQuantity, Date purchaseDate, String purchaseStatus, BigDecimal purchaseTotalAmount);
}
