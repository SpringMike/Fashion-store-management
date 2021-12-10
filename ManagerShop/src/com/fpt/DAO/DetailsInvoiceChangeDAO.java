/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.DetailsInvoiceChange;
import com.fpt.helper.jdbcHelper;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DetailsInvoiceChangeDAO extends ShopDAO<DetailsInvoiceChange, Integer> {

    @Override
    public void insert(DetailsInvoiceChange e) {
       String sql = "INSERT INTO dbo.DetailsInvoiceChange\n"
                + "(idInvoiceChangeProducts,idDetailsPr,quantity)\n"
                + "VALUES((SELECT TOP 1 idInvoiceChangeProducts FROM dbo.InvoiceChangeProducts ORDER BY idInvoiceChangeProducts DESC), ?, ?)";
        jdbcHelper.update(sql, e.getIdProductItem(), e.getQuantity());
    }

    @Override
    public void update(DetailsInvoiceChange e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetailsInvoiceChange> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DetailsInvoiceChange selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailsInvoiceChange> selectBySql(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
