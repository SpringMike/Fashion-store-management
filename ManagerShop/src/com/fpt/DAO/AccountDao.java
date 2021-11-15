/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.Account;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author minht
 */
public class AccountDao {

    //đọc 1 Account từ 1 bản ghi (1 ResultSet)
    public Account readFromResultSet(ResultSet rs) throws SQLException {
        Account model = new Account();
        model.setIdUser(rs.getString("idUser"));
        model.setIdAcount(rs.getString("idAccount"));
        model.setUsername(rs.getString("username"));
        model.setPassword(rs.getString("password"));
        return model;
    }

    //thực hiện truy vấn lấy về 1 tập ResultSet rồi điền tập ResultSet đó vào 1 List 
    public List<Account> select(String sql, Object... args) {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.query(sql, args);
                while (rs.next()) {
                    Account model = readFromResultSet(rs);
                    list.add(model);
                }
                
            } finally {
                rs.getStatement().getConnection().close();      //đóng kết nối từ resultSet
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }

    /**
     * Truy vấn tất cả các các thực thể trả về danh sách các thực thể
     */
    public List<Account> select() {
        String sql = "SELECT * FROM dbo.Account";
        return select(sql);             //trong 1 class có thể có 2 method trùng tên (nhưng param khác nhau)
    }

    /**
     * Truy vấn thực thể theo username truyền vào username của bản ghi được truy
     * vấn trả về thực thể chứa thông tin của bản ghi
     */
    public Account findByUsername(String username) {
        String sql = "SELECT * FROM dbo.Account WHERE username like ?";
        List<Account> list = select(sql, username);
        return list.size() > 0 ? list.get(0) : null;               //có thể trả về là null
    }
}
