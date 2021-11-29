/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.MyProfile;
import com.fpt.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author ducit
 */
public class MyProfileDAO extends ShopDAO<MyProfile, Integer> {

    @Override
    public void insert(MyProfile e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(MyProfile e) {
        String sql = "UPDATE dbo.[User] SET name = ?, birthday = ?, gender = ?, phoneNumber = ?, address = ?,\n"
                + "email = ? WHERE idUser = ?";
        jdbcHelper.update(sql, e.getName(), e.getBirDate(), e.isGender(), e.getPhoneNumber(), e.getAddress(), e.getEmail(), e.getId());
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MyProfile> selectAll() {
        String sql = "SELECT *FROM dbo.[User]";
        return selectBySql(sql);
    }

    @Override
    public MyProfile selectById(Integer k) {
        String sql = "SELECT *FROM dbo.[User] where idUser  = ?";
        List<MyProfile> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<MyProfile> selectBySql(String sql, Object... args) {
        List<MyProfile> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                MyProfile m = new MyProfile();
                m.setAddress(rs.getString("Address"));
                m.setBirDate(rs.getDate("birthday"));
                m.setEmail(rs.getString("email"));
                m.setGender(rs.getBoolean("gender"));
                m.setId(rs.getInt("idUser"));
                m.setName(rs.getString("name"));
                m.setPhoneNumber(rs.getString("phoneNumber"));
                m.setRole(rs.getBoolean("role"));
                m.setSalary(rs.getDouble("salary"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
