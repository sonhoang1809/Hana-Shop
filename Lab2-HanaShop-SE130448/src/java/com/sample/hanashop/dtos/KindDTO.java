/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.dtos;

/**
 *
 * @author sonho
 */
public class KindDTO {

    private String kindID;
    private String kindFood;
    private int totalNumFood;

    public KindDTO(String kindID, String kindFood) {
        this.kindID = kindID;
        this.kindFood = kindFood;
    }

    public String getKindID() {
        return kindID;
    }

    public void setKindID(String kindID) {
        this.kindID = kindID;
    }

    public String getKindFood() {
        return kindFood;
    }

    public void setKindFood(String kindFood) {
        this.kindFood = kindFood;
    }

    public int getTotalNumFood() {
        return totalNumFood;
    }

    public void setTotalNumFood(int totalNumFood) {
        this.totalNumFood = totalNumFood;
    }

}
