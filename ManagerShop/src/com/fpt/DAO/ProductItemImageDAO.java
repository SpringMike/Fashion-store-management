/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.ProductItemImage;
import com.fpt.entity.Products;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ProductItemImageDAO extends ShopDAO<ProductItemImage, Integer> {

    @Override
    public void insert(ProductItemImage e) {
      
        String sql = "INSERT INTO dbo.ImageProducts (idPrDeltails,valueImage) "
                + "VALUES((SELECT TOP 1 idPrDeltails FROM dbo.detailsProduct ORDER BY idPrDeltails DESC), ?)";
        jdbcHelper.update(sql, e.getValue());
    }

    @Override
    public void update(ProductItemImage e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProductItemImage> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductItemImage selectById(Integer k) {
        String sql = "SELECT * FROM dbo.Products JOIN dbo.List ON List.idList = Products.idList WHERE idProduct = ?";
        List<ProductItemImage> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<ProductItemImage> selectBySql(String sql, Object... args) {
        List<ProductItemImage> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                ProductItemImage p = new ProductItemImage();
                p.setIdProductItem(rs.getInt("idPrDeltails"));
                p.setValue(rs.getString("valueImage"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
