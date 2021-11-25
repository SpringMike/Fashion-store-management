/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.entity;

/**
 *
 * @author Administrator
 */
public class DetailInvoiceImport {
    private int id;
    private int idInvoice;
    private int idProductItem;
    private int quantity;

    private float price;
    
    private String nameProduct;
    private String valueSize;
    private String valueColor;
    private String valueMaterial;

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
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
    
    
    public DetailInvoiceImport(int id, int idInvoice, int idProductItem, int quantity, boolean status, float price) {
        this.id = id;
        this.idInvoice = idInvoice;
        this.idProductItem = idProductItem;
        this.quantity = quantity;
        this.price = price;
    }

    public DetailInvoiceImport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public int getIdProductItem() {
        return idProductItem;
    }

    public void setIdProductItem(int idProductItem) {
        this.idProductItem = idProductItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    
    
}
