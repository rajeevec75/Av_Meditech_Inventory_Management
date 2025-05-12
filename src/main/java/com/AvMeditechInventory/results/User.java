/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.results;

import com.AvMeditechInventory.entities.PermissionGroups;
import java.util.List;

/**
 * @version 1.0
 * @since Aug 01, 2022
 * @author Rahul(QMM Technologies Pvt. Ltd.)
 */
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String userType;
    private boolean isUserSubscribed;
    private List<PermissionGroups> permissionGroups;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isIsUserSubscribed() {
        return isUserSubscribed;
    }

    public void setIsUserSubscribed(boolean isUserSubscribed) {
        this.isUserSubscribed = isUserSubscribed;
    }

    public List<PermissionGroups> getPermissionGroups() {
        return permissionGroups;
    }

    public void setPermissionGroups(List<PermissionGroups> permissionGroups) {
        this.permissionGroups = permissionGroups;
    }

}
