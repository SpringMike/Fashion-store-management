/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.ProductItem;
import com.fpt.entity.Products;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ProductItemDAO extends ShopDAO<ProductItem, Integer> {

    @Override
    public void insert(ProductItem e) {
        String sql = "INSERT INTO dbo.detailsProduct(idProduct,idSize,idColor,idMaterial,price,quatity,status)values(?,?,?,?,?,?,?)";
        jdbcHelper.update(sql, e.getIdProduct(), e.getIdSize(), e.getIdColor(), e.getIdMaterial(), e.getPrice(), e.getQuantity(), e.isStatus());
    }

    @Override
    public void update(ProductItem e) {
        String sql = "UPDATE dbo.[detailsProduct] SET idSize = ?, idColor = ?, idMaterial = ?, price = ? WHERE idPrDeltails = ?";
        jdbcHelper.update(sql, e.getIdSize(), e.getIdColor(), e.getIdMaterial(), e.getPrice(), e.getId());
    }

    @Override
    public void delete(Integer k) {
        String sql = "{call PRDelete(?)}";
        jdbcHelper.update(sql, k);
    }

    @Override
    public List<ProductItem> selectAll() {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + " INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "                 INNER JOIN Color C on C.idColor = D.idColor\n"
                + "                 INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "                 INNER JOIN List L  on L.idList = P.idList\n"
                + "                 where D.status = 1";
        return selectBySql(sql);
    }

    public List<ProductItem> selectAllSell() {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + " INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "                 INNER JOIN Color C on C.idColor = D.idColor\n"
                + "                 INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "                 INNER JOIN List L  on L.idList = P.idList\n"
                + "                 where D.status = 1 and D.quatity > 0";
        return selectBySql(sql);
    }

    @Override
    public ProductItem selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<ProductItem> selectBySql(String sql, Object... args) {
        List<ProductItem> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                ProductItem p = new ProductItem();
                p.setId(rs.getInt("idPrDeltails"));
                p.setIdProduct(rs.getInt("idProduct"));
                p.setIdSize(rs.getInt("idSize"));
                p.setIdColor(rs.getInt("idColor"));
                p.setIdMaterial(rs.getInt("idMaterial"));
                p.setPrice(rs.getFloat("price"));
                p.setQuantity(rs.getInt("quatity"));
                p.setStatus(rs.getBoolean("status"));
                p.setSize(rs.getString("valueSize"));
                p.setColor(rs.getString("valueColor"));
                p.setMaterial(rs.getString("valueMaterial"));
                p.setProductName(rs.getString("nameProduct"));
                p.setCategoryName(rs.getString("nameList"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ProductItem> selectByKeyWord(String keyword) {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial from detailsProduct D\n"
                + "INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial INNER JOIN Color C on C.idColor = D.idColor\n"
                + "INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "where D.status = 1 and P.nameProduct like ?";
        return selectBySql(sql, "%" + keyword + "%");
    }

    public void importProductItem(Integer quantity, Integer id) {
        String sql = "UPDATE detailsProduct\n"
                + "SET quatity= quatity + ? \n"
                + "WHERE idPrDeltails = ?;";
        jdbcHelper.update(sql, quantity, id);
    }

    public void sellProductItem(Integer quantity, Integer id) {
        String sql = "UPDATE detailsProduct\n"
                + "SET quatity= quatity - ? \n"
                + "WHERE idPrDeltails = ?;";
        jdbcHelper.update(sql, quantity, id);
    }

}
