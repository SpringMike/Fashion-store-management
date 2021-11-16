/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.Customer;
import com.fpt.entity.Supplier;
import com.fpt.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author ducit
 */
public class SupplierDao extends ShopDAO<Supplier, Integer> {

    @Override
    public void insert(Supplier e) {
        String sql = "INSERT INTO dbo.Supplier\n"
                + "(nameMaterial,phoneNumber,address)\n"
                + "VALUES\n"
                + "(?,?,?)";
        jdbcHelper.update(sql, e.getNameMaterial(), e.getPhoneNumber(), e.getAddress());
    }

    @Override
    public void update(Supplier e) {
        String sql = "UPDATE dbo.Supplier SET nameMaterial = ?, phoneNumber = ?, address = ? WHERE idSupplier = ?";
        jdbcHelper.update(sql, e.getNameMaterial(), e.getPhoneNumber(), e.getAddress(), e.getIdSupplier());
    }

    @Override
    public void delete(Integer k) {
        String sql = "DELETE FROM Supplier WHERE idSupplier = ?";
        jdbcHelper.update(sql, k);
    }

    @Override
    public List<Supplier> selectAll() {
        String sql = "select * from Supplier";
        return selectBySql(sql);
    }

    @Override
    public Supplier selectById(Integer k) {
        String sql = "select * from Supplier where idSupplier = ?";
        List<Supplier> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Supplier> selectBySql(String sql, Object... args) {
        List<Supplier> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setIdSupplier(rs.getInt("idSupplier"));
                s.setNameMaterial(rs.getString("nameMaterial"));
                s.setPhoneNumber(rs.getString("phoneNumber"));
                s.setAddress(rs.getString("address"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Supplier> selectByKeyWord(String key) {
        String sql = "Select * from Supplier where nameMaterial like ?";
        return selectBySql(sql, "%" + key + "%");
    }
}
