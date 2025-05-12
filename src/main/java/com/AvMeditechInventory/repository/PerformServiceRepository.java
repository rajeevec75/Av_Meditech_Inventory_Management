/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.repository;

import com.AvMeditechInventory.entities.PerformService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Repository
public interface PerformServiceRepository extends JpaRepository<PerformService, Integer> {

}
