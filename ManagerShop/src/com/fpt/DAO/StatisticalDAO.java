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

    public List<Object[]> getSalesStatisticalRevenue(Integer year) {
        String sql = "{call sp_revenue(?)}";
        String[] cols = {"MonthDate", "quantity", "totalSell", "totalReturn", "revenue"};
        return getListOfArray(sql, cols, year);
    }

    public List<Object[]> getQuantityBuy() {
        String sql = "{call sp_Quantity}";
        String[] cols = {"name", "gender", "phoneNumber", "sumBuy"};
        return getListOfArray(sql, cols);
    }

    public int getSumCustomer() {
        ResultSet rs;
        String sql = "SELECT COUNT(idCustomer) SumCustomer FROM dbo.Customer ";
        try {
            rs = jdbcHelper.query(sql);
            while (rs.next()) {
                return rs.getInt("SumCustomer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getRevenueDate() {
        ResultSet rs;
        String sql = "	SELECT IIF(CAST(SUM(totalReturn) AS INT ) is NULL , CAST(SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) AS INT),  \n"
                + "	  (CAST(SUM(detailsInvoiceSELL.price * detailsInvoiceSELL.quatity) - SUM(totalReturn) AS INT)))\n"
                + "                revenue FROM dbo.detailsInvoiceSELL JOIN dbo.InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell\n"
                + "                LEFT JOIN dbo.InvoiceReturn ON InvoiceReturn.idInvoiceSell = InvoiceSell.idInvoiceSell WHERE \n"
                + "                YEAR(InvoiceSell.dateCreateInvoice) = YEAR(GETDATE()) AND\n"
                + "                MONTH(InvoiceSell.dateCreateInvoice) = MONTH(GETDATE()) AND DAY(dbo.InvoiceSell.dateCreateInvoice) = DAY(GETDATE())";
        try {
            rs = jdbcHelper.query(sql);
            while (rs.next()) {
                return rs.getInt("revenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getInventory() {
        ResultSet rs;
        String sql = "SELECT SUM(quatity) inventory FROM dbo.detailsProduct";
        try {
            rs = jdbcHelper.query(sql);
            while (rs.next()) {
                return rs.getInt("inventory");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getQuantityDate() {
        ResultSet rs;
        String sql = "    SELECT SUM(quatity) Quantity FROM dbo.InvoiceSell JOIN dbo.detailsInvoiceSELL ON detailsInvoiceSELL.idInvoiceSell = InvoiceSell.idInvoiceSell\n"
                + "WHERE YEAR(dateCreateInvoice) = YEAR(GETDATE()) AND MONTH(dateCreateInvoice) = MONTH(GETDATE()) AND DAY(dateCreateInvoice) = DAY(GETDATE())";
        String[] cols = {"Quantity"};
        try {
            rs = jdbcHelper.query(sql);
            while (rs.next()) {
                return rs.getInt("Quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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

    public int getSelectImport(int month, int year) throws Exception {
        String sql = "SELECT IIF(SUM(quatity *priceImport) IS NULL, 0, SUM(quatity *priceImport)) moneyImport FROM dbo.InvoiceImportPr JOIN dbo.detailsInvoiceImportPr ON detailsInvoiceImportPr.idInvoice = InvoiceImportPr.idInvoice\n"
                + "WHERE MONTH(dateCreateInvoice) = ? and YEAR(dateCreateInvoice) = ?\n"
                + "GROUP BY MONTH(dateCreateInvoice)";
        int moneyImport = 0;
        ResultSet rs = jdbcHelper.query(sql, month, year);
        while (rs.next()) {
            moneyImport = rs.getInt(1);
        }
        rs.getStatement().getConnection().close();
        return moneyImport;
    }

}
