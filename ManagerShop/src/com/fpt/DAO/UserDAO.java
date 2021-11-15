/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.User;
import com.fpt.helper.jdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author Đặng Đình Vũ
 */
public class UserDAO extends ShopDAO<User, String> {

    private String INSERT_SQL_USER = "INSERT dbo.[User](name, birthday, gender, phoneNumber, address, salary, role, status) VALUES(?,?,?,?,?,?,?,?)";
    private String UPDATE_SQL = "";
    private String DELETE_SQL = "";
    private String SELECT_ALL_SQL = "SELECT * FROM dbo.[User]";
    private String SELECT_BY_ID = "";

    @Override
    public void insert(User e) {
        jdbcHelper.update(INSERT_SQL_USER, e.getFullname(), e.getDateOfBirth(), e.isGender(), e.getPhoneNumber(), e.getAdress(),
                e.getSalary(), e.isRole(), e.isStatus());
    }

    @Override
    public void update(User e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public User selectById(String k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<User> selectBySql(String sql, Object... args) {
        List<User> listE = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                User e = new User();
                e.setIdUser(rs.getInt("idUser"));
                e.setFullname(rs.getString("name"));
                e.setDateOfBirth(rs.getDate("birthday"));
                e.setGender(rs.getBoolean("gender"));
                e.setPhoneNumber(rs.getString("phoneNumber"));
                e.setAdress(rs.getString("address"));
                e.setSalary(rs.getDouble("salary"));
                e.setRole(rs.getBoolean("role"));
                e.setStatus(rs.getBoolean("status"));
                listE.add(e);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
