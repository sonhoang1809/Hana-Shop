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
public class LoginError {

    private String errorNotLoginYet;
    private String errorExistUserID;
    private String errorExistEmail;
    private String errorPassword;

    public LoginError() {
    }

    public void setErrorNotLoginYet(String errorNotLoginYet) {
        this.errorNotLoginYet = errorNotLoginYet;
    }

    public String getErrorExistEmail() {
        return errorExistEmail;
    }

    public void setErrorExistEmail(String errorExistEmail) {
        this.errorExistEmail = errorExistEmail;
    }

    public String getErrorPassword() {
        return errorPassword;
    }

    public void setErrorPassword(String errorPassword) {
        this.errorPassword = errorPassword;
    }

    public String getErrorExistUserID() {
        return errorExistUserID;
    }

    public void setErrorExistUserID(String errorExistUserID) {
        this.errorExistUserID = errorExistUserID;
    }

    public String getErrorNotLoginYet() {
        return errorNotLoginYet;
    }

}
