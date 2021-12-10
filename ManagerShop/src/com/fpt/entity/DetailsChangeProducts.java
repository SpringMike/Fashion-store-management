/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.entity;

/**
 *
 * @author Administrator
 */
public class DetailsChangeProducts {

    private int id, idDetailsInvoiceChange;
    private int idProductItem;
    private int quantity;
    private float price;

    private String nameProduct;
    private String valueSize;
    private String valueColor;
    private String valueMaterial;

    public DetailsChangeProducts() {
    }

    public DetailsChangeProducts(int id, int idDetailsInvoiceChange, int idProductItem, int quantity, float price, String nameProduct, String valueSize, String valueColor, String valueMaterial) {
        this.id = id;
        this.idDetailsInvoiceChange = idDetailsInvoiceChange;
        this.idProductItem = idProductItem;
        this.quantity = quantity;
        this.price = price;
        this.nameProduct = nameProduct;
        this.valueSize = valueSize;
        this.valueColor = valueColor;
        this.valueMaterial = valueMaterial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDetailsInvoiceChange() {
        return idDetailsInvoiceChange;
    }

    public void setIdDetailsInvoiceChange(int idDetailsInvoiceChange) {
        this.idDetailsInvoiceChange = idDetailsInvoiceChange;
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
    
}
