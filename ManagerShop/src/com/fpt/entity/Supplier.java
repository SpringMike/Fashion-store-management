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
public class Supplier {

    private String nameMaterial, phoneNumber, address;
    private int idSupplier;

    public Supplier() {
    }

    public Supplier(String nameMaterial, String phoneNumber, String address, int idSupplier) {
        this.nameMaterial = nameMaterial;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.idSupplier = idSupplier;
    }

    @Override
    public String toString() {
        return nameMaterial;
    }

    public String getNameMaterial() {
        return nameMaterial;
    }

    public void setNameMaterial(String nameMaterial) {
        this.nameMaterial = nameMaterial;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

}
