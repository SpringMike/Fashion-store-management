/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.Category;
import com.fpt.entity.Supplier;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CategoryDAO extends ShopDAO<Category, Integer> {

    String INSERT_SQL = "INSERT INTO dbo.List(nameList) VALUES (?)";

    @Override
    public void insert(Category e) {
        jdbcHelper.update(INSERT_SQL, e.getName());

    }

    public void insert(String categoryName) {
        jdbcHelper.update(INSERT_SQL, categoryName);
    }

    @Override
    public void update(Category e) {
        String sql = "UPDATE dbo.List SET nameList = ? WHERE idList = ?";
        jdbcHelper.update(sql, e.getName(), e.getId());
    }

    @Override
    public void delete(Integer k) {
        String sql = "DELETE FROM List WHERE idList = ?";
        jdbcHelper.update(sql, k);
    }

    @Override
    public List<Category> selectAll() {
        String sql = "select * from List";
        return selectBySql(sql);
    }

    @Override
    public Category selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<Category> selectBySql(String sql, Object... args) {
        List<Category> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("idList"));
                c.setName(rs.getString("nameList"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
