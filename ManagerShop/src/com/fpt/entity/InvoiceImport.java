/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.entity;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class InvoiceImport {

    private int id;
    private String dateCreate;

    private boolean statusPay;
    private int idUser;
    private int idSupplier;
    private String desc;
    
    private String nameUser;
    private String nameSupplier;
    
    

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    public InvoiceImport() {
    }

    public InvoiceImport(int id, String dateCreate, boolean statusPay, int idUser, int idSupplier, String desc) {
        this.id = id;
        this.dateCreate = dateCreate;
      
        this.statusPay = statusPay;
        this.idUser = idUser;
        this.idSupplier = idSupplier;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

  

    public boolean isStatusPay() {
        return statusPay;
    }

    public void setStatusPay(boolean statusPay) {
        this.statusPay = statusPay;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    
}
