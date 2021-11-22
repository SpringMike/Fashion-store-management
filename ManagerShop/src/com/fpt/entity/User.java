/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.entity;

import java.util.Date;

/**
 *
 * @author ducit
 */
public class User {

    private int idUser;
    private String Fullname;
    private boolean Role;
    private boolean Gender;
    private Date DateOfBirth;
    private String Adress;
    private String PhoneNumber;
    private String Email;
    private Double Salary;
    private boolean Status;

    public User() {
    }

    public User(int idUser, String Fullname, boolean Role, boolean Gender, Date DateOfBirth, String Adress, String PhoneNumber, String Email, Double Salary, boolean Status) {
        this.idUser = idUser;
        this.Fullname = Fullname;
        this.Role = Role;
        this.Gender = Gender;
        this.DateOfBirth = DateOfBirth;
        this.Adress = Adress;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.Salary = Salary;
        this.Status = Status;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String Fullname) {
        this.Fullname = Fullname;
    }

    public boolean isRole() {
        return Role;
    }

    public void setRole(boolean Role) {
        this.Role = Role;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean Gender) {
        this.Gender = Gender;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double Salary) {
        this.Salary = Salary;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }


}
