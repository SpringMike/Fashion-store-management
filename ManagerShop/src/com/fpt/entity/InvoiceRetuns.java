/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.entity;

import java.util.Date;

/**
 *
 * @author Đặng Đình Vũ
 */
public class InvoiceRetuns {

    private int idInvoiceRetuns, idInvoiceSell, idCustomer, idUser;
    private String dateCreateInvoiceReturn;
    private String description, nameCustomer;
    private double totalReturn;

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public InvoiceRetuns() {
    }

    public int getIdInvoiceRetuns() {
        return idInvoiceRetuns;
    }

    public void setIdInvoiceRetuns(int idInvoiceRetuns) {
        this.idInvoiceRetuns = idInvoiceRetuns;
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

    public String getDateCreateInvoiceReturn() {
        return dateCreateInvoiceReturn;
    }

    public void setDateCreateInvoiceReturn(String dateCreateInvoiceReturn) {
        this.dateCreateInvoiceReturn = dateCreateInvoiceReturn;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(double totalReturn) {
        this.totalReturn = totalReturn;
    }

}
