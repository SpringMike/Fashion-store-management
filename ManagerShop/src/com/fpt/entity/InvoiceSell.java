/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.entity;

import java.util.Date;

/**
 *
 * @author ducit
 */
public class InvoiceSell {

    private int idInvoiceSell, idCustomer, idHumanSell, idVoucher;
    private String description, nameUser, nameCustomer;
    private Date dateCreateInvoice;
    private boolean statusPay, statusInvoice;

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
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

    public int getIdHumanSell() {
        return idHumanSell;
    }

    public void setIdHumanSell(int idHumanSell) {
        this.idHumanSell = idHumanSell;
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreateInvoice() {
        return dateCreateInvoice;
    }

    public void setDateCreateInvoice(Date dateCreateInvoice) {
        this.dateCreateInvoice = dateCreateInvoice;
    }

    public boolean isStatusPay() {
        return statusPay;
    }

    public void setStatusPay(boolean statusPay) {
        this.statusPay = statusPay;
    }

    public boolean isStatusInvoice() {
        return statusInvoice;
    }

    public void setStatusInvoice(boolean statusInvoice) {
        this.statusInvoice = statusInvoice;
    }

}
