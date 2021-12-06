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
                + "                 INNER JOIN List L  on L.idList = P.idList ORDER BY idPrDeltails Desc";
        return selectBySql(sql);
    }

    public ProductItem selectImprotProductID(int id) {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + "		INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "             INNER JOIN Color C on C.idColor = D.idColor\n"
                + "             INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "             INNER JOIN List L  on L.idList = P.idList where D.idPrDeltails = ?";
        List<ProductItem> list = selectBySql(sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<ProductItem> selectImprotProductKey(String key) {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + "				  INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "                               INNER JOIN Color C on C.idColor = D.idColor\n"
                + "                               INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "                               INNER JOIN List L  on L.idList = P.idList where P.nameProduct LIKE ?";
        return selectBySql(sql, "%" + key + "%");
    }

    public List<ProductItem> selectAllSell() {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + " INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "                 INNER JOIN Color C on C.idColor = D.idColor\n"
                + "                 INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "                 INNER JOIN List L  on L.idList = P.idList\n"
                + "                 where D.status = 1 and D.quatity > 0 ORDER BY idPrDeltails Desc";
        return selectBySql(sql);
    }

    @Override
    public ProductItem selectById(Integer k) {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + "                INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "                INNER JOIN Color C on C.idColor = D.idColor\n"
                + "                INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "                INNER JOIN List L  on L.idList = P.idList\n"
                + "                where D.idPrDeltails = ?";
        List<ProductItem> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<ProductItem> selectByKey(String k) {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + "                INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "                INNER JOIN Color C on C.idColor = D.idColor\n"
                + "                INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "                INNER JOIN List L  on L.idList = P.idList\n"
                + "                where P.nameProduct like ?";
        return selectBySql(sql, "%" + k + "%");
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

    public void returnProductItem(Integer quantity, Integer id) {
        String sql = "UPDATE detailsProduct\n"
                + "SET quatity= quatity + ? \n"
                + "WHERE idPrDeltails = ?;";
        jdbcHelper.update(sql, quantity, id);
    }

    public List<ProductItem> selectByKeyWordSell(String keyword) {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + "                 INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial\n"
                + "                 INNER JOIN Color C on C.idColor = D.idColor\n"
                + "                 INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "                 INNER JOIN List L  on L.idList = P.idList\n"
                + "                 where D.status = 1 and D.quatity > 0 AND P.nameProduct like ?";
        return selectBySql(sql, "%" + keyword + "%");
    }

    public List<ProductItem> selectByPropertieProductItem(int quantity, String keyword) {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + "INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial \n"
                + "INNER JOIN Color C on C.idColor = D.idColor \n"
                + "INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "INNER JOIN List L  on L.idList = P.idList ";
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        switch (keyword) {
            case "Above":
                sb.append("where D.quatity >= ?");
                break;
            case "Below":
                sb.append("where D.quatity <= ?");
                break;
            case "StillRemailProductItem":
                sb.append("where D.quatity > 0");
                return selectBySql(sb.toString());
            case "OutOfStock":
                sb.append("where D.quatity = 0");
                return selectBySql(sb.toString());
            case "OderByPriceDesc":
                sb.append("order by D.price desc");
                return selectBySql(sb.toString());
            case "OderByPriceAsc":
                sb.append("order by D.price asc");
                return selectBySql(sb.toString());
            case "StatusTrue":
                sb.append("where D.status = 1");
                return selectBySql(sb.toString());
            case "StatusFalse":
                sb.append("where D.status = 0");
                return selectBySql(sb.toString());
            case "ByProduct":
                sb.append("where D.idProduct = ?");
                break;
        }
        return selectBySql(sb.toString(), quantity);
    }

    public List<ProductItem> selectByRemainQuantity(int k) {
        String sql = "select D.*,P.nameProduct,S.valueSize,C.valueColor,M.valueMaterial,nameList,quatity from detailsProduct D\n"
                + "INNER JOIN Size S on D.idSize = S.idSize INNER JOIN Material M on M.idMaterial = D.idMaterial \n"
                + "INNER JOIN Color C on C.idColor = D.idColor \n"
                + "INNER JOIN Products P on P.idProduct = D.idProduct\n"
                + "INNER JOIN List L  on L.idList = P.idList\n"
                + "where D.quatity = 0";
        return selectBySql(sql, k);
    }

}
