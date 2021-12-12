/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.DetailInvoiceReturn;
import com.fpt.entity.DetailsChangeProducts;
import com.fpt.entity.DetailsInvoiceChange;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
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
        String sql = "select * from DetailsInvoiceChange De\n"
                + "JOIN InvoiceChangeProducts I on I.idInvoiceChangeProducts = De.idInvoiceChangeProducts\n"
                + "INNER JOIN detailsProduct D on De.idDetailsPr = D.idPrDeltails\n"
                + "INNER JOIN Size S on D.idSize = S.idSize\n"
                + "INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "INNER JOIN Color C on C.idColor = D.idColor\n"
                + "INNER JOIN Products P on P.idProduct = D.idProduct\n";
        return selectBySql(sql);
    }

    @Override
    public DetailsInvoiceChange selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailsInvoiceChange> selectBySql(String sql, Object... args) {
        List<DetailsInvoiceChange> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailsInvoiceChange de = new DetailsInvoiceChange();
                de.setIdInvoiceChange(rs.getInt("idInvoiceChangeProducts"));
                de.setQuantity(rs.getInt("quantity"));
                de.setPrice(rs.getInt("price"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setValueSize(rs.getString("valueSize"));
                de.setId(rs.getInt("id"));
                de.setIdProductItem(rs.getInt("idDetailsPr"));
                de.setNameProduct(rs.getString("nameProduct"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailsInvoiceChange> selectByIdInvoiceChange(Integer k) {
        String sql = "select * from DetailsInvoiceChange De\n"
                + "JOIN InvoiceChangeProducts I on I.idInvoiceChangeProducts = De.idInvoiceChangeProducts\n"
                + "INNER JOIN detailsProduct D on De.idDetailsPr = D.idPrDeltails\n"
                + "INNER JOIN Size S on D.idSize = S.idSize\n"
                + "INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "INNER JOIN Color C on C.idColor = D.idColor\n"
                + "INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "where De.idInvoiceChangeProducts = ?";
        return selectBySql(sql, k);
    }

}
