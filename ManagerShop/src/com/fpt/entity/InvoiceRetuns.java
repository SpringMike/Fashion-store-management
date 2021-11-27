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
    private int idInvoiceRetuns, idInvoiceSell;
    private Date dateCreateInvoiceReturn;
    private String note, UserName;
    private double price;

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

    public Date getDateCreateInvoiceReturn() {
        return dateCreateInvoiceReturn;
    }

    public void setDateCreateInvoiceReturn(Date dateCreateInvoiceReturn) {
        this.dateCreateInvoiceReturn = dateCreateInvoiceReturn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
