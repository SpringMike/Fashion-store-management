/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.entity;

/**
 *
 * @author Đặng Đình Vũ
 */
public class DetailInvoiceReturn {
    private int idDetailInvoiceReturn, idInvoiceReturn, idPrDetails, quatity;
    private double price;
    private String valueSize, valueColor, valueMaterial, nameProduct, nameCustomer;

    public DetailInvoiceReturn() {
    }

    public int getIdDetailInvoiceReturn() {
        return idDetailInvoiceReturn;
    }

    public void setIdDetailInvoiceReturn(int idDetailInvoiceReturn) {
        this.idDetailInvoiceReturn = idDetailInvoiceReturn;
    }

    public int getIdInvoiceReturn() {
        return idInvoiceReturn;
    }

    public void setIdInvoiceReturn(int idInvoiceReturn) {
        this.idInvoiceReturn = idInvoiceReturn;
    }

    public int getIdPrDetails() {
        return idPrDetails;
    }

    public void setIdPrDetails(int idPrDetails) {
        this.idPrDetails = idPrDetails;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
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

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }
    
    
}
