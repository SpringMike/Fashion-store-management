/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ducit
 */
public class StatisticalDAO {

    List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getSalesStatisticalDAO(Integer year, Integer month) {
        String sql = "{call sp_statistical(?,?)}";
        String[] cols = {"idProduct", "nameProduct", "quantitySell"};
        return getListOfArray(sql, cols, year, month);
    }

    public List<Integer> selectYears() {
        String sql = "SELECT DISTINCT YEAR(dateCreateInvoice) FROM dbo.InvoiceSell ORDER BY YEAR(dateCreateInvoice) DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> selectMonths(int year) {
        String sql = "SELECT MONTH(dateCreateInvoice) FROM dbo.InvoiceSell WHERE YEAR(dateCreateInvoice) = ?\n"
                + "GROUP BY MONTH(dateCreateInvoice)";
        List<Integer> list = new ArrayList<>();
        try {

            ResultSet rs = jdbcHelper.query(sql, year);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }

            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

//     public List<Object[]> selectByMonths(int month){
//         String sql = "SELECT * FROM dbo.InvoiceSell WHERE MONTH(dateCreateInvoice) = ?";
//         return 
//     }
}
