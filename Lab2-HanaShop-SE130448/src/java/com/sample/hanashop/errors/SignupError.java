/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.errors;

/**
 *
 * @author sonho
 */
public class SignupError {

    private String errorUserID;
    private String errorEmail;
    private String errorName;
    private String errorPassword;
    private String errorConfirm;
    private String errorConfirmPassword;

    public SignupError() {
    }

    public String getErrorEmail() {
        return errorEmail;
    }

    public void setErrorEmail(String errorEmail) {
        this.errorEmail = errorEmail;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorPassword() {
        return errorPassword;
    }

    public void setErrorPassword(String errorPassword) {
        this.errorPassword = errorPassword;
    }

    public String getErrorConfirm() {
        return errorConfirm;
    }

    public void setErrorConfirm(String errorConfirm) {
        this.errorConfirm = errorConfirm;
    }

    public String getErrorConfirmPassword() {
        return errorConfirmPassword;
    }

    public void setErrorConfirmPassword(String errorConfirmPassword) {
        this.errorConfirmPassword = errorConfirmPassword;
    }

    public String getErrorUserID() {
        return errorUserID;
    }

    public void setErrorUserID(String errorUserID) {
        this.errorUserID = errorUserID;
    }

}
