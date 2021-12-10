/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.DetailInvoiceSell;
import com.fpt.entity.DetailsChangeProducts;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DetailsChangeProductDAO extends ShopDAO<DetailsChangeProducts, Integer> {

    @Override
    public void insert(DetailsChangeProducts e) {
        String sql = "INSERT INTO dbo.DetailsChangeProducts\n"
                + "(idDetailsInvoiceChange,idDetailsPr,quantity)\n"
                + "VALUES((SELECT TOP 1  idInvoiceSell FROM dbo.InvoiceSell ORDER BY idInvoiceSell DESC), ?, ?)";
        jdbcHelper.update(sql, e.getIdProductItem(), e.getQuantity());
    }

    @Override
    public void update(DetailsChangeProducts e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetailsChangeProducts> selectAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DetailsChangeProducts selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailsChangeProducts> selectBySql(String sql, Object... args) {
        List<DetailsChangeProducts> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailsChangeProducts de = new DetailsChangeProducts();
                de.setIdDetailsInvoiceChange(rs.getInt("idDetailsInvoiceChange"));
                de.setQuantity(rs.getInt("quantity"));
                de.setPrice(rs.getInt("price"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setValueSize(rs.getString("valueSize"));
                de.setNameProduct(rs.getString("nameProduct"));
                de.setId(rs.getInt("id"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
