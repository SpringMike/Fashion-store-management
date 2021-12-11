/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.InvoiceChange;
import com.fpt.entity.InvoiceRetuns;
import com.fpt.helper.jdbcHelper;
import com.fpt.utils.XDate;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class InvoiceChangeDAO extends ShopDAO<InvoiceChange, Integer> {

    @Override
    public void insert(InvoiceChange e) {
        String sql = "INSERT Into dbo.InvoiceChangeProducts(idInvoiceSell,idCustomer, description, idUser, dateCreateInvoice) VALUES(?,?,?,?,?)";
        jdbcHelper.update(sql, e.getIdInvoiceSell(), e.getIdCustomer(), e.getDescription(), e.getIdUser(), e.getDateCreateInvoiceReturn());
    }

    @Override
    public void update(InvoiceChange e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InvoiceChange> selectAll() {
        String sql = "SELECT * FROM dbo.InvoiceChangeProducts JOIN dbo.Customer ON Customer.idCustomer = InvoiceChangeProducts.idCustomer ORDER BY idInvoiceChangeProducts Desc";
        return selectBySql(sql);
    }

    @Override
    public InvoiceChange selectById(Integer k) {
        String sql = "SELECT * FROM dbo.InvoiceChangeProducts JOIN dbo.Customer ON Customer.idCustomer = InvoiceChangeProducts.idCustomer\n"
                + " where InvoiceChangeProducts.idInvoiceChangeProducts = ?";
        List<InvoiceChange> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<InvoiceChange> selectBySql(String sql, Object... args) {
        List<InvoiceChange> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                InvoiceChange p = new InvoiceChange();
                p.setId(rs.getInt("idInvoiceChangeProducts"));
                p.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                p.setDateCreateInvoiceReturn(rs.getString("dateCreateInvoice"));
                p.setIdCustomer(rs.getInt("idCustomer"));
                p.setDescription(rs.getString("description"));
                p.setNameCustomer(rs.getString("name"));
                p.setIdUser(rs.getInt("idUser"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int totalPage(String Stringdate) {
        ResultSet rs;
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = " select count(*) as soLuong from InvoiceChangeProducts WHERE  dateCreateInvoice BETWEEN '" + new java.sql.Date(date.getTime()) + " 00:00:00.000'" + "AND '" + new java.sql.Date(date.getTime()) + " 23:59:59.000' ";
            try {
                rs = jdbcHelper.query(sql);
                while (rs.next()) {
                    return rs.getInt("soLuong");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        String sql = "select count(*) as soLuong from InvoiceChangeProducts";
        try {
            rs = jdbcHelper.query(sql);
            while (rs.next()) {
                return rs.getInt("soLuong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<InvoiceChange> pagingPage(int page, int pageSize, String Stringdate) {
        if (!Stringdate.isEmpty()) {
            java.util.Date date = XDate.toDate(Stringdate, "yyyy-MM-dd");
            String sql = "SELECT * FROM dbo.InvoiceChangeProducts JOIN dbo.Customer ON Customer.idCustomer = InvoiceChangeProducts.idCustomer \n"
                    + "WHERE  dateCreateInvoice BETWEEN '" + new java.sql.Date(date.getTime()) + " 00:00:00.000'" + "AND '" + new java.sql.Date(date.getTime()) + " 23:59:59.000' \n"
                    + "order by idInvoiceChangeProducts desc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
            return selectBySql(sql, (page - 1) * pageSize, pageSize);
        }
        String sql = "SELECT * FROM dbo.InvoiceChangeProducts JOIN dbo.Customer ON Customer.idCustomer = InvoiceChangeProducts.idCustomer \n"
                + "order by idInvoiceChangeProducts desc OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
        return selectBySql(sql, (page - 1) * pageSize, pageSize);
    }
}
