/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.entity;

/**
 *
 * @author Administrator
 */
public class InvoiceChange {

    private int id, idInvoiceSell, idCustomer, idUser, idDetailNew, idDetailOld;
    private String dateCreateInvoiceReturn;
    private String description, nameCustomer;

    public InvoiceChange(int id, int idInvoiceSell, int idCustomer, int idUser, int idDetailNew, int idDetailOld, String dateCreateInvoiceReturn, String description, String nameCustomer) {
        this.id = id;
        this.idInvoiceSell = idInvoiceSell;
        this.idCustomer = idCustomer;
        this.idUser = idUser;
        this.idDetailNew = idDetailNew;
        this.idDetailOld = idDetailOld;
        this.dateCreateInvoiceReturn = dateCreateInvoiceReturn;
        this.description = description;
        this.nameCustomer = nameCustomer;
    }

    public InvoiceChange() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInvoiceSell() {
        return idInvoiceSell;
    }

    public void setIdInvoiceSell(int idInvoiceSell) {
        this.idInvoiceSell = idInvoiceSell;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDetailNew() {
        return idDetailNew;
    }

    public void setIdDetailNew(int idDetailNew) {
        this.idDetailNew = idDetailNew;
    }

    public int getIdDetailOld() {
        return idDetailOld;
    }

    public void setIdDetailOld(int idDetailOld) {
        this.idDetailOld = idDetailOld;
    }

    public String getDateCreateInvoiceReturn() {
        return dateCreateInvoiceReturn;
    }

    public void setDateCreateInvoiceReturn(String dateCreateInvoiceReturn) {
        this.dateCreateInvoiceReturn = dateCreateInvoiceReturn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }
    
    
}
