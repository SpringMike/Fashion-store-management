/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.Material;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class MaterialDAO extends ShopDAO<Material, Integer> {

    @Override
    public void insert(Material e) {
        String sql = "INSERT INTO dbo.Material(valueMaterial) VALUES (?)";
        jdbcHelper.update(sql, e.getName());
    }

    @Override
    public void update(Material e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Material> selectAll() {
        String sql = "select * from Material";
        return selectBySql(sql);
    }

    @Override
    public Material selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<Material> selectBySql(String sql, Object... args) {
       List<Material> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Material m = new Material();
                m.setId(rs.getInt("idMaterial"));
                m.setName(rs.getString("valueMaterial"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
