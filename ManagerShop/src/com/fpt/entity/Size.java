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
public class Size {

    private int IdSize;
    private String valueSize;

    public int getIdSize() {
        return IdSize;
    }

    public void setIdSize(int IdSize) {
        this.IdSize = IdSize;
    }

    public String getValueSize() {
        return valueSize;
    }

    public void setValueSize(String valueSize) {
        this.valueSize = valueSize;
    }

    @Override
    public String toString() {
        return valueSize;
    }

}
