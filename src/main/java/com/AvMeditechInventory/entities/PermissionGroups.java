/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.entities;

import java.util.List;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class PermissionGroups {

    private String PermissionGroupsId;
    private String PermissionGroupsName;
    private List<Permission> permissions;

    public PermissionGroups() {
        super();
    }

    public PermissionGroups(String PermissionGroupsId, String PermissionGroupsName) {
        this.PermissionGroupsId = PermissionGroupsId;
        this.PermissionGroupsName = PermissionGroupsName;
    }

    public String getPermissionGroupsId() {
        return PermissionGroupsId;
    }

    public void setPermissionGroupsId(String PermissionGroupsId) {
        this.PermissionGroupsId = PermissionGroupsId;
    }

    public String getPermissionGroupsName() {
        return PermissionGroupsName;
    }

    public void setPermissionGroupsName(String PermissionGroupsName) {
        this.PermissionGroupsName = PermissionGroupsName;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
