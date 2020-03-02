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
public class BillDTO implements Serializable {

    private String idBill;
    private String userID;
    private float subTotal;
    private float tax;
    private float total;
    private String date;
    private int statusBillCode;
    private String statusBill;
    private int billNum;
    private String userName;

    public BillDTO(String idBill, String userID, float total, String date, int statusBillCode) {
        this.idBill = idBill;
        this.userID = userID;
        this.total = total;
        this.date = date;
        this.statusBillCode = statusBillCode;
    }

    public BillDTO(String idBill, String userID, float subTotal, float tax, float total, String date, int statusBillCode) {
        this.idBill = idBill;
        this.userID = userID;
        this.subTotal = subTotal;
        this.tax = tax;
        this.total = total;
        this.date = date;
        this.statusBillCode = statusBillCode;
        if (statusBillCode == 1) {
            statusBill = "Payed";
        } else if (statusBillCode == 0) {
            statusBill = "Not Payed Yet";
        }
    }

    public BillDTO(String idBill, String userID, String date, int idStatusBill, int billNum) {
        this.idBill = idBill;
        this.userID = userID;
        this.date = date;
        this.statusBillCode = idStatusBill;
        this.billNum = billNum;
    }

    public BillDTO(String idBill, String userID, float total) {
        this.idBill = idBill;
        this.userID = userID;
        this.total = total;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatusBillCode() {
        return statusBillCode;
    }

    public void setStatusBillCode(int statusBillCode) {
        this.statusBillCode = statusBillCode;
    }

    public int getBillNum() {
        return billNum;
    }

    public void setBillNum(int billNum) {
        this.billNum = billNum;
    }

    public String getStatusBill() {
        return statusBill;
    }

    public void setStatusBill(String statusBill) {
        this.statusBill = statusBill;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
