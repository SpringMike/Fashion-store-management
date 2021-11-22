/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.entity;

/**
 *
 * @author Administrator
 */
public class ProductItemImage {
    private int id;
    private int idProductItem;
    private String value;

    public ProductItemImage() {
    }

    public ProductItemImage(int id, int idProductItem, String value) {
        this.id = id;
        this.idProductItem = idProductItem;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProductItem() {
        return idProductItem;
    }

    public void setIdProductItem(int idProductItem) {
        this.idProductItem = idProductItem;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}
