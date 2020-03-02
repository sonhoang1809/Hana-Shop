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
public class FoodDTO implements Comparable<FoodDTO>, Serializable {

    String idFood;
    String imgFood;
    String nameFood;
    String description;
    String shortDescription;
    int quantity;
    int maxQuantity;
    float price;
    String categoryID;
    String createDate;
    int statusCode;
    String status;
    String kind;
    String categoryName;
    int orderTime;
    String updateDate;
    String updateUser;

    public FoodDTO(String idFood, int orderTime) {
        this.idFood = idFood;
        this.orderTime = orderTime;
    }

    public FoodDTO(String idFood, String imgFood, String nameFood, String createDate, String updateDate, String updateUser) {
        this.idFood = idFood;
        this.imgFood = imgFood;
        this.nameFood = nameFood;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.updateUser = updateUser;
    }

    public FoodDTO(String idFood, String imgFood, String nameFood, String description, String shortDescription, int quantity, float price, String categoryID, String createDate, int statusCode) {
        this.idFood = idFood;
        this.imgFood = imgFood;
        this.nameFood = nameFood;
        this.description = description;
        this.shortDescription = shortDescription;
        this.quantity = quantity;
        this.price = price;
        this.categoryID = categoryID;
        this.createDate = createDate;
        this.statusCode = statusCode;
    }

    public FoodDTO(String idFood, String imgFood, String nameFood, String description, int quantity, float price, String categoryID, String createDate, int statusCode) {
        this.idFood = idFood;
        this.imgFood = imgFood;
        this.nameFood = nameFood;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.categoryID = categoryID;
        this.createDate = createDate;
        this.statusCode = statusCode;
    }

    public FoodDTO(String idFood, String imgFood, String nameFood, String description, int quantity, float price, String categoryID, String createDate, String updateDate, String updateUser, int statusCode) {
        this.idFood = idFood;
        this.imgFood = imgFood;
        this.nameFood = nameFood;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.categoryID = categoryID;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.updateUser = updateUser;
        this.statusCode = statusCode;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getImgFood() {
        return imgFood;
    }

    public void setImgFood(String imgFood) {
        this.imgFood = imgFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(int orderTime) {
        this.orderTime = orderTime;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    //so sanh theo orderTime
    @Override
    public int compareTo(FoodDTO foodDTO) {
        if (foodDTO.getOrderTime() <= this.orderTime) {
            return -1;
        } else {
            return 1;
        }
    }

}
