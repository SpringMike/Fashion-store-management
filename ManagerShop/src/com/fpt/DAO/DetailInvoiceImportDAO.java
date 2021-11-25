/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.DetailInvoiceImport;
import com.fpt.entity.InvoiceImport;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DetailInvoiceImportDAO extends ShopDAO<DetailInvoiceImport, Integer> {

    @Override
    public void insert(DetailInvoiceImport e) {
        String sql = "INSERT INTO dbo.detailsInvoiceImportPr(idInvoice,idPrDeltails,quatity,priceImport)"
                + "values((SELECT TOP 1 idInvoice FROM dbo.InvoiceImportPr ORDER BY idInvoice DESC),?,?,?)";

        jdbcHelper.update(sql, e.getIdProductItem(), e.getQuantity(), e.getPrice());
    }

    @Override
    public void update(DetailInvoiceImport e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DetailInvoiceImport> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DetailInvoiceImport selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DetailInvoiceImport> selectBySql(String sql, Object... args) {
        List<DetailInvoiceImport> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                DetailInvoiceImport de = new DetailInvoiceImport();
                de.setId(rs.getInt("detailsInvoice"));
                de.setNameProduct(rs.getString("nameProduct"));
                de.setValueSize(rs.getString("valueSize"));
                de.setValueColor(rs.getString("valueColor"));
                de.setValueMaterial(rs.getString("valueMaterial"));
                de.setQuantity(rs.getInt("quatity"));
                de.setPrice(rs.getInt("price"));
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailInvoiceImport> selectByIdInvoice(Integer idInvoice) {
        String sql = "select D.detailsInvoice, P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,D.quatity,De.price from detailsInvoiceImportPr D\n"
                + "join detailsProduct De on De.idPrDeltails = D.idPrDeltails\n"
                + "join Products P on De.idProduct = P.idProduct\n"
                + "join Size S on S.idSize = De.idSize\n"
                + "join Color C on C.idColor = De.idColor\n"
                + "join Material M on M.idMaterial = De.idMaterial\n"
                + "where D.idInvoice = ?";
        return selectBySql(sql, idInvoice);

    }

}
