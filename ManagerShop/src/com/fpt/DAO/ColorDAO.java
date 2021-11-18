/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.Color;
import com.fpt.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author Đặng Đình Vũ
 */
public class ColorDAO extends ShopDAO<Color, Integer>{

    @Override
    public void insert(Color e) {
        String sql = "INSERT dbo.Color( valueColor) values(?)";
        jdbcHelper.update(sql, e.getValueColor());
    }

    @Override
    public void update(Color e) {
        String sql = "update dbo.Color set valueColor = ? where idColor = ?";
        jdbcHelper.update(sql, e.getValueColor(), e.getIdColor());
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Color> selectAll() {
        String sql = "SELECT * FROM dbo.Color";
        return selectBySql(sql);
    }

    @Override
    public Color selectById(Integer k) {
        String sql = "SELECT * FROM dbo.Color where idColor = ?";
        List<Color> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Color> selectBySql(String sql, Object... args) {
        List<Color> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Color c = new Color();
                c.setIdColor(rs.getInt("idColor"));
                c.setValueColor(rs.getString("valueColor"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
