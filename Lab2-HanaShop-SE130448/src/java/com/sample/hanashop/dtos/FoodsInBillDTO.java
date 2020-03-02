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
public class FoodsInBillDTO implements Serializable {

    String idBill;
    String idFood;
    int quantity;
    int maxQuantity;
    int statusFoodID;
    float price;
    float total;
    String imgFood;
    String shortDescription;
    String nameFood;
    String kind;
    String categoryName;
    int orderTime;

    public FoodsInBillDTO(String idBill, String idFood) {
        this.idBill = idBill;
        this.idFood = idFood;
    }

    public FoodsInBillDTO(String idBill, String idFood, int quantity, int statusFoodID, float price, float total) {
        this.idBill = idBill;
        this.idFood = idFood;
        this.quantity = quantity;
        this.statusFoodID = statusFoodID;
        this.price = price;
        this.total = total;
    }

    public FoodsInBillDTO(String idBill, String idFood, int quantity, int statusFoodID, float price, float total, String nameFood) {
        this.idBill = idBill;
        this.idFood = idFood;
        this.quantity = quantity;
        this.statusFoodID = statusFoodID;
        this.price = price;
        this.total = total;
        this.nameFood = nameFood;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
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

    public int getStatusFoodID() {
        return statusFoodID;
    }

    public void setStatusFoodID(int statusFoodID) {
        this.statusFoodID = statusFoodID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getImgFood() {
        return imgFood;
    }

    public void setImgFood(String imgFood) {
        this.imgFood = imgFood;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
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

}
