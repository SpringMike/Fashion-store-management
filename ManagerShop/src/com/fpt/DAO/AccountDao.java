/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.Account;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author minht
 */
public class AccountDao extends ShopDAO<Account, Integer> {

    private String INSERT_SQL_ACCOUNT = "INSERT INTO dbo.Account\n"
            + "(idUser,Username,password)\n"
            + "VALUES((SELECT TOP 1 idUser FROM dbo.[USER] ORDER BY idUser DESC), ?, ?)";
    private String UPDATE_SQL = "";
    private String DELETE_SQL = "";
    private String SELECT_ALL_SQL = "SELECT * FROM dbo.Account";
    private String SELECT_BY_ID = "SELECT * FROM dbo.Account WHERE idAccount = ?";

    @Override
    public void insert(Account e) {
        jdbcHelper.update(INSERT_SQL_ACCOUNT, e.getUserName(), e.getPassWord());
    }

    @Override
    public void update(Account e) {
        String sql = "UPDATE dbo.Account SET password = ? WHERE idUser = ?";
        jdbcHelper.update(sql, e.getPassWord(), e.getIdUser());
    }

    @Override
    public List<Account> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    public List<Account> selectAllUP(Integer i) {
        return this.selectBySql("select * from Account where idUser = ?", i);
    }

    @Override
    protected List<Account> selectBySql(String sql, Object... args) {
        List<Account> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                Account a = new Account();
                a.setIdAcount(rs.getInt("idAccount"));
                a.setIdUser(rs.getInt("idUser"));
                a.setUserName(rs.getString("username"));
                a.setPassWord(rs.getString("password"));
                list.add(a);
            }
            rs.getStatement().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account selectById(Integer k) {
        List<Account> list = this.selectBySql(SELECT_BY_ID, k);
        return list.size()>0?list.get(0):null;
    }

    public Account selectByIdUser(Integer k) {
        List<Account> list = this.selectBySql("select * from Account where idUser = ?", k);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public Account selectById(String k) {
        String sql = "SELECT dbo.Account.* FROM dbo.Account JOIN dbo.[User] ON [User].idUser = Account.idUser WHERE username = ? AND status = 1";
        List<Account> list = this.selectBySql(sql, k);
        return list.size()>0?list.get(0):null;
    }
}
