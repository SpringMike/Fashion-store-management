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

    private int idInvoiceSell, idCustomer, idHumanSell;
    private String description, nameUser, nameCustomer;
    private String dateCreateInvoice;
    private boolean statusPay, statusInvoice;
    private double price, moneyCustomer, moneyReturn;
    private Integer idVoucher;

    public double getMoneyCustomer() {
        return moneyCustomer;
    }

    public void setMoneyCustomer(double moneyCustomer) {
        this.moneyCustomer = moneyCustomer;
    }

    public double getMoneyReturn() {
        return moneyReturn;
    }

    public void setMoneyReturn(double moneyReturn) {
        this.moneyReturn = moneyReturn;
    }

    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

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

    public Integer getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Integer idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreateInvoice() {
        return dateCreateInvoice;
    }

    public void setDateCreateInvoice(String dateCreateInvoice) {
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
