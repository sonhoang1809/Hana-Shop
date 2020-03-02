/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.dtos;

import java.io.Serializable;

/**
 *
 * @author sonho
 */
public class RoleDTO implements Serializable {

    private String roleID;
    private String roleDetail;

    public RoleDTO(String roleID, String roleDetail) {
        this.roleID = roleID;
        this.roleDetail = roleDetail;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleDetail() {
        return roleDetail;
    }

    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }

}
