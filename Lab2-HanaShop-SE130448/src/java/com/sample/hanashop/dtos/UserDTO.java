/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.dtos;

import com.sample.hanashop.supports.Support;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sonho
 */
public class UserDTO implements Serializable {

    private String userID;
    private String email;
    private String avatar;
    private String name;
    private String password;
    private String roleID;
    private String dateCreate;
    private String description;
    private String idFoodRecentView;
    private List<String> listIdFoodRecentView;

    public UserDTO(String userID, String email, String avatar, String name, String roleID, String dateCreate, String description, String idFoodRecentView) {
        this.userID = userID;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.roleID = roleID;
        this.dateCreate = dateCreate;
        this.description = description;
        this.idFoodRecentView = idFoodRecentView;
        this.listIdFoodRecentView = Support.convertToListIdFoodsRecent(idFoodRecentView);
    }

    public UserDTO(String userID, String email, String avatar, String name, String roleID, String dateCreate, String description) {
        this.userID = userID;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.roleID = roleID;
        this.dateCreate = dateCreate;
        this.description = description;
    }

    public UserDTO(String userID, String email, String avatar, String name, String password, String roleID) {
        this.userID = userID;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.password = password;
        this.roleID = roleID;
    }

    public UserDTO(String userID, String email, String avatar, String name, String roleID) {
        this.userID = userID;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.roleID = roleID;
    }

    public UserDTO(String userID, String avatar, String name, String description) {
        this.userID = userID;
        this.avatar = avatar;
        this.name = name;
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdFoodRecentView() {
        return idFoodRecentView;
    }

    public void setIdFoodRecentView(String idFoodRecentView) {
        this.idFoodRecentView = idFoodRecentView;
    }

    public List<String> getListIdFoodRecentView() {
        return listIdFoodRecentView;
    }

    public void setListIdFoodRecentView(List<String> listIdFoodRecentView) {
        this.listIdFoodRecentView = listIdFoodRecentView;
    }

}
