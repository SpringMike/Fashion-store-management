/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fpt.DAO;

import com.fpt.entity.Customer;
import com.fpt.entity.Empolyee;
import com.fpt.helper.jdbcHelper;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CustomerDAO extends ShopDAO<Customer, Integer> {

    String INSERT_SQL = "INSERT INTO Customer (name, phoneNumber, gender, address) VALUES (?, ?, ?, ?)";
    String SELECT_ALL_SQL = "SELECT * FROM Customer ORDER BY idCustomer Desc";
    String SELECT_BY_ID_SQL = "SELECT * FROM Customer WHERE idCustomer=?";
    String DELETE_SQL = "DELETE FROM Customer WHERE idCustomer=?";
    String UPDATE_SQL = "UPDATE Customer SET name=?, phoneNumber=?, gender=?, address=? WHERE idCustomer=?";

    @Override
    public void insert(Customer e) {
        jdbcHelper.update(INSERT_SQL, e.getName(), e.getPhoneNumber(), e.getGender(), e.getAddress());
    }

    @Override
    public void update(Customer e) {
        jdbcHelper.update(UPDATE_SQL, e.getName(), e.getPhoneNumber(), e.getGender(), e.getAddress(), e.getId());
    }

    @Override
    public void delete(Integer k) {
        jdbcHelper.update(DELETE_SQL, k);
    }

    @Override
    public List<Customer> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Customer selectById(Integer k) {
        List<Customer> list = selectBySql(SELECT_BY_ID_SQL, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<Customer> selectBySql(String sql, Object... args) {
        List<Customer> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("idCustomer"));
                c.setName(rs.getString("name"));
                c.setAddress(rs.getString("address"));
                c.setPhoneNumber(rs.getString("phoneNumber"));
                c.setGender(rs.getBoolean("gender"));

                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Customer> selectByKeyWord(String keyword) {
        String sql = "SELECT * from Customer where name LIKE ? ORDER BY idCustomer Desc";
        return selectBySql(sql, "%" + keyword + "%");
    }

}
