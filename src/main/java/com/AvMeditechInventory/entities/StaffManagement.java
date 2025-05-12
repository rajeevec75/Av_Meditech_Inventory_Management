/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.entities;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "staff_management")
public class StaffManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_management_id")
    private Integer staffManagementId;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "role", length = 255)
    private String role;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    // Constructors, getters, and setters
    public Integer getStaffManagementId() {
        return staffManagementId;
    }

    public void setStaffManagementId(Integer staffManagementId) {
        this.staffManagementId = staffManagementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
