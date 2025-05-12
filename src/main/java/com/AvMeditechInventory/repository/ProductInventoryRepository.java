/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.repository;

import com.AvMeditechInventory.entities.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Integer> {

}
