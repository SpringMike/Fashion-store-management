/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.DAO;

import com.fpt.entity.Employee;
import com.fpt.helper.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author truon
 */
public class EmployeeDAO extends EduSysDAO<Employee, Integer>{

    String DELETE_SQL = "DELETE FROM User WHERE idUser = ?";
    String SELECT_ALL_SQL = "SELECT * FROM User";
    
    @Override
    public void insert(Employee e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Employee e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Employee> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Employee selectById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<Employee> selectBySql(String sql, Object... args) {
        List<Employee> list = new ArrayList<Employee>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {                
                Employee entity = new Employee();
                entity.setIdUser(rs.getInt("idUser"));
                entity.setName(rs.getString("name"));
                entity.setBirthday(rs.getDate("birthday"));
                entity.setGender(rs.getBoolean("gender"));
                entity.setPhoneNumber(rs.getString("phoneNumber"));
                entity.setAddress(rs.getString("address"));
                entity.setSalary(rs.getDouble("salary"));
                entity.setRole(rs.getBoolean("role"));
                entity.setStatus(rs.getBoolean("status"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
