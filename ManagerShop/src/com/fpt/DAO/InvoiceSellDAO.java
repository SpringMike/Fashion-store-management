/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.InvoiceSell;
import com.fpt.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author ducit
 */
public class InvoiceSellDAO extends ShopDAO<InvoiceSell, Integer> {

    @Override
    public void insert(InvoiceSell e) {
        String sql = "INSERT INTO dbo.InvoiceSell\n"
                + "(idCustomer,idHumanSell,idVoucher,dateCreateInvoice,description,statusPay,statusInvoice, totalMoney)\n"
                + "VALUES(?, ?, ?, ?,?,?,?,?)";
        jdbcHelper.update(sql, e.getIdCustomer(), e.getIdHumanSell(), e.getIdVoucher(), e.getDateCreateInvoice(), e.getDescription(), e.isStatusPay(), e.isStatusInvoice(), e.getPrice());
    }

    @Override
    public void update(InvoiceSell e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InvoiceSell> selectAll() {
        String sql = "SELECT * FROM dbo.InvoiceSell JOIN dbo.[User] ON [User].idUser = InvoiceSell.idHumanSell JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer";
        return selectBySql(sql);
    }

    @Override
    public InvoiceSell selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<InvoiceSell> selectBySql(String sql, Object... args) {
        List<InvoiceSell> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                InvoiceSell i = new InvoiceSell();
                i.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                i.setIdCustomer(rs.getInt("idCustomer"));
                i.setIdHumanSell(rs.getInt("idHumanSell"));
                i.setIdVoucher(rs.getInt("idVoucher"));
                i.setDateCreateInvoice(rs.getDate("dateCreateInvoice"));
                i.setDescription(rs.getString("description"));
                i.setStatusPay(rs.getBoolean("statusPay"));
                i.setStatusInvoice(rs.getBoolean("statusInvoice"));
                i.setNameCustomer(rs.getString("name"));
                i.setNameUser(rs.getString("name"));
                i.setPrice(rs.getDouble("totalMoney"));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Float getTotalMoney(Integer idInvoice) {
        String sql = "SELECT idInvoiceSell, SUM(detailsInvoiceSELL.quatity * price)\n"
                + "AS N'Total'\n"
                + "FROM dbo.detailsInvoiceSELL\n"
                + "GROUP BY idInvoiceSell\n"
                + "HAVING idInvoiceSell = ?";
        try {
            ResultSet rs = jdbcHelper.query(sql, idInvoice);
            while (rs.next()) {
                return rs.getFloat("Total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<InvoiceSell> fillDate(java.util.Date date) {
        String sql = " SELECT * FROM dbo.InvoiceSell JOIN dbo.[User] ON [User].idUser = InvoiceSell.idHumanSell JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer\n"
                + "WHERE dateCreateInvoice = ?";
        return selectBySql(sql, date);
    }

//    public List<Integer> selectByMonths(int month) {
//        String sql = "SELECT * FROM dbo.InvoiceSell WHERE MONTH(dateCreateInvoice) = ?";
//        return selectBySql(sql, month);
//    }

}
