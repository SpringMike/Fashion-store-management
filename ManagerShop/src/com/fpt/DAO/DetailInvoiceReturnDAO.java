/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.DetailInvoiceReturn;
import com.fpt.helper.jdbcHelper;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author ducit
 */
public class DetailInvoiceReturnDAO extends ShopDAO<DetailInvoiceReturn, Integer> {

    @Override
    public void insert(DetailInvoiceReturn e) {
        String sql = "INSERT INTO dbo.DetailInvoiceReturn\n"
                + "(idInvoiceReturn,idPrDetails,quatity,price)\n"
                + "VALUES\n"
                + "((SELECT TOP 1 idInvoiceReturn FROM dbo.InvoiceReturn ORDER BY idInvoiceReturn DESC),?,?,?)";
        jdbcHelper.update(sql, e.getIdPrDetails(), e.getQuatity(), e.getPrice());
    }

    @Override
    public void update(DetailInvoiceReturn e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetailInvoiceReturn> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DetailInvoiceReturn selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailInvoiceReturn> selectBySql(String sql, Object... args) {
        List<DetailInvoiceReturn> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailInvoiceReturn de = new DetailInvoiceReturn();
                de.setIdDetailInvoiceReturn(rs.getInt("idDetailInvoiceReturn"));
//                de.setIdProduct(rs.getInt("idProduct"));
//                de.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
                de.setQuatity(rs.getInt("quatity"));
                de.setPrice(rs.getInt("price"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setValueSize(rs.getString("valueSize"));
                de.setNameProduct(rs.getString("nameProduct"));
                de.setNameCustomer(rs.getString("name"));
//                de.setValueVoucher(rs.getInt("value"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailInvoiceReturn> selectByIdInvoice(int id) {
        String sql = "SELECT idDetailInvoiceReturn, nameProduct, name, valueSize, valueColor, valueMaterial, DetailInvoiceReturn.quatity, detailsProduct.price * DetailInvoiceReturn.quatity AS N'price' FROM dbo.DetailInvoiceReturn JOIN dbo.InvoiceReturn ON InvoiceReturn.idInvoiceReturn = DetailInvoiceReturn.idDetailInvoiceReturn\n"
                + "JOIN dbo.Customer ON Customer.idCustomer = InvoiceReturn.idCustomer\n"
                + "JOIN dbo.detailsProduct ON detailsProduct.idPrDeltails = DetailInvoiceReturn.idPrDetails\n"
                + "JOIN dbo.Products ON Products.idProduct = detailsProduct.idProduct\n"
                + "JOIN dbo.Size ON Size.idSize = detailsProduct.idSize JOIN dbo.Color ON Color.idColor = detailsProduct.idColor\n"
                + "JOIN dbo.Material ON Material.idMaterial = detailsProduct.idMaterial \n"
                + "WHERE dbo.DetailInvoiceReturn.idInvoiceReturn = ?";
        return selectBySql(sql, id);
    }

}
