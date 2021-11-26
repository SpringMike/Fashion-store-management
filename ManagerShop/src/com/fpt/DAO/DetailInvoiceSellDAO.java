/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.DetailInvoiceSell;
import com.fpt.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author ducit
 */
public class DetailInvoiceSellDAO extends ShopDAO<DetailInvoiceSell, Integer> {

    @Override
    public void insert(DetailInvoiceSell e) {
        String sql = "INSERT INTO dbo.detailsInvoiceSELL\n"
                + "(idInvoiceSell,idPrDetails,quatity,price)\n"
                + "VALUES\n"
                + "((SELECT TOP 1  idInvoiceSell FROM dbo.InvoiceSell ORDER BY idInvoiceSell DESC),?, ?, ?)";
        jdbcHelper.update(sql, e.getIdPrDetails(), e.getQuantity(), e.getPrice());
    }

    @Override
    public void update(DetailInvoiceSell e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetailInvoiceSell> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DetailInvoiceSell selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailInvoiceSell> selectBySql(String sql, Object... args) {
        List<DetailInvoiceSell> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailInvoiceSell de = new DetailInvoiceSell();
                de.setIdDetailsInvoiceSell(rs.getInt("idDetailsInvoiceSell"));
//                de.setIdInvoiceSell(rs.getInt("idInvoiceSell"));
//                de.setIdPrDetails(rs.getInt("idPrDetails"));
                de.setQuantity(rs.getInt("quatity"));
                de.setPrice(rs.getInt("price"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setValueSize(rs.getString("valueSize"));
                de.setNameProduct(rs.getString("nameProduct"));
                de.setNameCustomer(rs.getString("name"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailInvoiceSell> selectByIdInvoice(int id) {
        String sql = "SELECT idDetailsInvoiceSELL, nameProduct, name, valueSize, valueColor, valueMaterial, detailsInvoiceSELL.quatity, detailsInvoiceSELL.price  FROM dbo.detailsInvoiceSELL \n"
                + "JOIN dbo.InvoiceSell ON InvoiceSell.idInvoiceSell = detailsInvoiceSELL.idInvoiceSell\n"
                + "JOIN dbo.Customer ON Customer.idCustomer = InvoiceSell.idCustomer\n"
                + "JOIN dbo.detailsProduct ON detailsProduct.idPrDeltails = detailsInvoiceSELL.idPrDetails\n"
                + "JOIN dbo.Products ON Products.idProduct = detailsProduct.idProduct JOIN dbo.Size ON Size.idSize = detailsProduct.idSize\n"
                + "JOIN dbo.Color ON Color.idColor = detailsProduct.idColor JOIN dbo.Material ON Material.idMaterial = detailsProduct.idMaterial\n"
                + "WHERE detailsInvoiceSELL.idInvoiceSell = ?";
        return selectBySql(sql, id);
    }
}
