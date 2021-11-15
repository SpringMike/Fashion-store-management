/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.entity;

/**
 *
 * @author minht
 */
public class Account {
    private String idAcount,idUser,username,password;

    public Account() {
    }

    public Account(String idAcount, String idUser, String username, String password) {
        this.idAcount = idAcount;
        this.idUser = idUser;
        this.username = username;
        this.password = password;
    }

    public String getIdAcount() {
        return idAcount;
    }

    public void setIdAcount(String idAcount) {
        this.idAcount = idAcount;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
