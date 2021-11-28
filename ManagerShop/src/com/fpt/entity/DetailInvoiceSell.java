/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.entity;

/**
 *
 * @author ducit
 */
public class DetailInvoiceSell {

    private int idDetailsInvoiceSell, idProduct, idInvoiceSell, idPrDetails, quantity, valueVoucher;
    private double price;
    private String valueSize, valueColor, valueMaterial, nameProduct, nameCustomer;

    public String getNameCustomer() {
        return nameCustomer;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getValueVoucher() {
        return valueVoucher;
    }

    public void setValueVoucher(int valueVoucher) {
        this.valueVoucher = valueVoucher;
    }

    
    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getIdDetailsInvoiceSell() {
        return idDetailsInvoiceSell;
    }

    public void setIdDetailsInvoiceSell(int idDetailsInvoiceSell) {
        this.idDetailsInvoiceSell = idDetailsInvoiceSell;
    }

    public int getIdInvoiceSell() {
        return idInvoiceSell;
    }

    public void setIdInvoiceSell(int idInvoiceSell) {
        this.idInvoiceSell = idInvoiceSell;
    }

    public int getIdPrDetails() {
        return idPrDetails;
    }

    public void setIdPrDetails(int idPrDetails) {
        this.idPrDetails = idPrDetails;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getValueSize() {
        return valueSize;
    }

    public void setValueSize(String valueSize) {
        this.valueSize = valueSize;
    }

    public String getValueColor() {
        return valueColor;
    }

    public void setValueColor(String valueColor) {
        this.valueColor = valueColor;
    }

    public String getValueMaterial() {
        return valueMaterial;
    }

    public void setValueMaterial(String valueMaterial) {
        this.valueMaterial = valueMaterial;
    }

}
