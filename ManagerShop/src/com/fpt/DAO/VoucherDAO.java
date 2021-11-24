/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.Voucher;
import com.fpt.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author ducit
 */
public class VoucherDAO extends ShopDAO<Voucher, Integer> {

    @Override
    public void insert(Voucher e) {
        String sql = "insert into Voucher values(?,?,?,?,?)";
        jdbcHelper.update(sql, e.getNameVoucher(), e.getValue(), e.getDateStart(), e.getDateEnd(), e.getQuatity());
    }

    @Override
    public void update(Voucher e) {
        String sql = "	UPDATE dbo.Voucher SET valueVoucher = ?, value = ?, dateStart = ?, dateEnd = ?, quatity = ? WHERE idVoucher = ?";
        jdbcHelper.update(sql, e.getNameVoucher(), e.getValue(), e.getDateStart(), e.getDateEnd(), e.getQuatity(), e.getIdVoucher());
    }

    @Override
    public void delete(Integer k) {
        String sql = "delete Voucher where idVoucher = ?";
        jdbcHelper.update(sql, k);
    }

    @Override
    public List<Voucher> selectAll() {
        String sql = "select * from Voucher";
        return selectBySql(sql);
    }

    @Override
    public Voucher selectById(Integer k) {
        String sql = "select * from Voucher where idVoucher = ?";
        List<Voucher> list = selectBySql(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Voucher> selectBySql(String sql, Object... args) {
        List<Voucher> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Voucher v = new Voucher();
                v.setIdVoucher(rs.getInt("idVoucher"));
                v.setDateEnd(rs.getDate("dateEnd"));
                v.setDateStart(rs.getDate("dateStart"));
                v.setNameVoucher(rs.getString("valueVoucher"));
                v.setValue(rs.getInt("value"));
                v.setQuatity(rs.getInt("quatity"));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

     public List<Voucher> selectByKeyWord(String keyword) {
        String sql = "SELECT * from Voucher where valueVoucher LIKE ?";
        return selectBySql(sql, "%" + keyword + "%");
    }
}
