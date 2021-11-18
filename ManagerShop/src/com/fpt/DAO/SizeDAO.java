/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.Size;
import com.fpt.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author ducit
 */
public class SizeDAO extends ShopDAO<Size, Integer> {

    @Override
    public void insert(Size e) {
        String sql = "insert into Size(valueSize) values(?)";
        jdbcHelper.update(sql, e.getValueSize());
    }

    @Override
    public void update(Size e) {
        String sql = "UPDATE size SET valueSize = ? WHERE idSize = ?";
        jdbcHelper.update(sql, e.getValueSize(), e.getIdSize());
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Size> selectAll() {
        String sql = "select * from Size";
        return selectBySql(sql);
    }

    @Override
    public Size selectById(Integer k) {
        String sql = "select * from size where idSize = ?";
        List<Size> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    @Override
    protected List<Size> selectBySql(String sql, Object... args) {
        List<Size> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Size s = new Size();
                s.setIdSize(rs.getInt("idSize"));
                s.setValueSize(rs.getString("valueSize"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
