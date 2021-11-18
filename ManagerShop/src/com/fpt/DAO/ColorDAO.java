/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.Color;
import com.fpt.entity.Material;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author minht
 */
public class ColorDAO extends ShopDAO<Color, Integer>{

    @Override
    public void insert(Color e) {
        String sql = "INSERT INTO dbo.Color(valueColor)VALUES (?)";
        jdbcHelper.update(sql, e.getName());
    }

    @Override
    public void update(Color e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Color> selectAll() {
        String sql = "select * from dbo.Color";
        return selectBySql(sql);
    }

    @Override
    public Color selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<Color> selectBySql(String sql, Object... args) {
        List<Color> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Color cl = new Color();
                cl.setId(rs.getInt("idColor"));
                cl.setName(rs.getString("valueColor"));
                list.add(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
