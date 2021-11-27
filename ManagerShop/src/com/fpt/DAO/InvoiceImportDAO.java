/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.InvoiceImport;
import com.fpt.entity.InvoiceSell;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class InvoiceImportDAO extends ShopDAO<InvoiceImport, Integer> {

    @Override
    public void insert(InvoiceImport e) {
        String sql = "INSERT INTO dbo.InvoiceImportPr(dateCreateInvoice,statusPay,idAdmin,idSupplier,description)values(?,?,?,?,?)";
        jdbcHelper.update(sql, e.getDateCreate(), e.isStatusPay(), e.getIdUser(), e.getIdSupplier(), e.getDesc());
    }

    @Override
    public void update(InvoiceImport e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InvoiceImport> selectAll() {
        String sql = "select I.*,name,S.nameMaterial from InvoiceImportPr I join [User] U on U.idUser = I.idAdmin \n"
                + "join Supplier S on S.idSupplier = I.idSupplier";
        return selectBySql(sql);
    }

    @Override
    public InvoiceImport selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<InvoiceImport> selectBySql(String sql, Object... args) {
        List<InvoiceImport> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                InvoiceImport i = new InvoiceImport();
                i.setDateCreate(rs.getDate("dateCreateInvoice"));
                i.setStatusPay(rs.getBoolean("statusPay"));
                i.setIdUser(rs.getInt("idAdmin"));
                i.setIdSupplier(rs.getInt("idSupplier"));
                i.setDesc(rs.getString("description"));
                i.setId(rs.getInt("idInvoice"));
                i.setNameUser(rs.getString("name"));
                i.setNameSupplier(rs.getString("nameMaterial"));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Float getTotalMoney(Integer idInvoice) {
        String sql = "select idInvoice,SUM(D.quatity * D.priceImport) as \"totalCash\" from detailsInvoiceImportPr D\n"
                + "group by idInvoice\n"
                + "having idInvoice = ?";
        try {
            ResultSet rs = jdbcHelper.query(sql, idInvoice);
            while (rs.next()) {
                return rs.getFloat("totalCash");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<InvoiceImport> fillDate(java.util.Date date) {
        String sql = " select I.*,name,S.nameMaterial from InvoiceImportPr I join [User] U on U.idUser = I.idAdmin \n"
                + "join Supplier S on S.idSupplier = I.idSupplier where dateCreateInvoice =?";
        return selectBySql(sql, date);
    }
}
