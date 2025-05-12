/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.dtos;

import com.AvMeditechInventory.entities.Channels;
import com.AvMeditechInventory.entities.PermissionGroups;
import java.util.List;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class StaffDto {

    private String staffId;
    private String staffFirstName;
    private String staffLastName;
    private String staffEmail;
    private String staffMobileNo;
    private String addGroups;
    private List<PermissionGroups> permissions;
    private List<Channels> channelses;

    private String cursor;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    public StaffDto() {
    }

    public StaffDto(String staffId, String staffFirstName, String staffLastName, String staffEmail, String staffMobileNo,
            String addGroups) {
        this.staffId = staffId;
        this.staffFirstName = staffFirstName;
        this.staffLastName = staffLastName;
        this.staffEmail = staffEmail;
        this.staffMobileNo = staffMobileNo;
        this.addGroups = addGroups;

    }

    public StaffDto(String staffId, String staffFirstName, String staffLastName, String staffEmail, String staffMobileNo,
            List<PermissionGroups> permissions) {
        this.staffId = staffId;
        this.staffFirstName = staffFirstName;
        this.staffLastName = staffLastName;
        this.staffEmail = staffEmail;
        this.staffMobileNo = staffMobileNo;
        this.permissions = permissions;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffMobileNo() {
        return staffMobileNo;
    }

    public void setStaffMobileNo(String staffMobileNo) {
        this.staffMobileNo = staffMobileNo;
    }

    public String getStaffFirstName() {
        return staffFirstName;
    }

    public void setStaffFirstName(String staffFirstName) {
        this.staffFirstName = staffFirstName;
    }

    public String getStaffLastName() {
        return staffLastName;
    }

    public void setStaffLastName(String staffLastName) {
        this.staffLastName = staffLastName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public String getAddGroups() {
        return addGroups;
    }

    public void setAddGroups(String addGroups) {
        this.addGroups = addGroups;
    }

    public List<PermissionGroups> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionGroups> permissions) {
        this.permissions = permissions;
    }

    public List<Channels> getChannelses() {
        return channelses;
    }

    public void setChannelses(List<Channels> channelses) {
        this.channelses = channelses;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

}
