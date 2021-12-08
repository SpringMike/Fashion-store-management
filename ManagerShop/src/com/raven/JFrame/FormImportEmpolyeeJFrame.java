/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.JFrame;

import com.fpt.DAO.AccountDao;
import com.fpt.DAO.EmpolyeeDao;
import com.fpt.DAO.UserDAO;
import com.fpt.Validate.labelValidate;
import com.fpt.entity.Account;
import com.fpt.entity.User;
import com.fpt.utils.MsgBox;
import com.fpt.utils.XDate;
import com.raven.form.FormListEmpolyee;
import com.raven.form.MainForm;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormImportEmpolyeeJFrame extends javax.swing.JFrame {

    /**
     * Creates new form FormImportEmpolyeeJFrame
     */
    UserDAO daoE = new UserDAO();
    AccountDao daoA = new AccountDao();

    public FormImportEmpolyeeJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnUpdate.setEnabled(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("src\\com\\raven\\icon\\shop (6).png");
        this.setIconImage(icon);
//        txtPassWord.setVisible(false);
//        txtUsername.setVisible(false);
//        lblPass.setVisible(false);
//        lblUser.setVisible(false);
    }

    public void addEvenUpdate(ActionListener evt) {
        btnUpdate.addActionListener(evt);
    }

    public FormImportEmpolyeeJFrame(String fullname, String role, String gender, String birth, String address, String phone, String email, String salary, int idUser, int status) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnAddEmployee.setEnabled(false);
        btnAddEmployee.setVisible(false);

        txtName.setText(fullname);
        txtBirth.setText(birth);
        txtAdress.setText(address);
        txtPhone.setText(phone);
        txtEmail.setText(email);
        txtSalary.setText(salary);
        txtPassWord.setVisible(false);
        txtUsername.setVisible(false);
        lblUser.setText("");
        lblPass.setText("");
        txtAdress.setEditable(false);
        txtBirth.setEditable(false);
        dateChooser1.setVisible(false);
        rdoFeMale.setEnabled(false);
        rdoMale.setEnabled(false);
        txtEmail.setEditable(false);
        txtName.setEditable(false);
        txtPhone.setEditable(false);

        if (gender.equalsIgnoreCase("Nam")) {
            rdoMale.setSelected(true);
        } else if (gender.equalsIgnoreCase("Nữ")) {
            rdoFeMale.setSelected(true);
        }
        if (role.equalsIgnoreCase("Quản lý")) {
            rdoManage.setSelected(true);
        } else if (role.equalsIgnoreCase("Nhân viên")) {
            rdoEmpolyee.setSelected(true);
        }

        if (status == 0) {
            rdoWorking.setSelected(true);
        } else {
            rdoLeave.setSelected(true);
        }
        lblIDUser.setText(idUser + "");

    }

    public void update() {
        User e = new User();
        e.setFullname(txtName.getText());
        e.setRole(rdoManage.isSelected());
        e.setGender(rdoMale.isSelected());
        e.setDateOfBirth(XDate.toDate(txtBirth.getText(), "dd-MM-yyyy"));
        e.setAdress(txtAdress.getText());
        e.setPhoneNumber(txtPhone.getText());
        e.setEmail(txtEmail.getText());
        e.setSalary(Double.parseDouble(txtSalary.getText()));
        e.setIdUser(Integer.parseInt(lblIDUser.getText()));
        e.setStatus(rdoWorking.isSelected());
        daoE.update(e);
        MsgBox.alert(this, "Cập nhật thành công !!!");
        this.dispose();
    }

    public boolean checkDate() {
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.parse(txtBirth.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int years = Period.between(date, today).getYears();
        if (years < 18) {
            MsgBox.labelAlert(lblBirth, txtBirth, "Trên 18 tuổi");
            System.out.println(years);
            return false;
        }
        System.out.println(years);
        return true;
    }

    EmpolyeeDao emDao = new EmpolyeeDao();

    public boolean checkUser(String acc) {
        for (int i = 0; i < emDao.selectAll().size(); i++) {
            if (emDao.selectAll().get(i).getUsername().equals(acc.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEmail(String acc) {
        for (int i = 0; i < emDao.selectAll().size(); i++) {
            if (emDao.selectAll().get(i).getEmail().trim().equals(acc.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPhoneNumber(String acc) {
        for (int i = 0; i < emDao.selectAll().size(); i++) {
            if (emDao.selectAll().get(i).getPhoneNumber().trim().equals(acc.trim())) {
                return true;
            }
        }
        return false;
    }

    public void insert() {
        try {
            if (labelValidate.checkEmpty(lblName, txtName, "Tên chưa nhập!!!") == false) {
                return;
            } else if (labelValidate.checkPhoneNumber(lblPhone, txtPhone, "") == false) {
                return;
            } else if (checkPhoneNumber(txtPhone.getText()) == true) {
                MsgBox.labelAlert(lblPhone, txtPhone, "Trùng số điện thoại");
                return;
            } else if (labelValidate.checkEmpty(lblBirth, txtBirth, "Ngày sinh chưa nhập!!!") == false) {
                return;
            } else if (checkDate() == false) {
                return;
            } else if (labelValidate.checkEmpty(lblSalary, txtSalary, "Lương chưa nhập!!!") == false) {
                return;
            } else if (labelValidate.checkNumber(lblSalary, txtSalary, "Lương không hợp lệ!!!") == false) {
                return;
            } else if (labelValidate.checkEmail(lblEmail, txtEmail, "") == false) {
                return;
            } else if (checkEmail(txtEmail.getText()) == true) {
                MsgBox.labelAlert(lblEmail, txtEmail, "Trùng email");
                return;
            } else if (labelValidate.checkEmpty(lblUser, txtUsername, "Tài khoản chưa nhập!!!") == false) {
                return;
            } else if (checkUser(txtUsername.getText()) == true) {
                MsgBox.labelAlert(lblUser, txtUsername, "Trùng user");
                return;
            } else if (labelValidate.checkEmpty(lblPass, txtPassWord, "Mật khẩu chưa nhập!!!") == false) {
                return;
            } else {
                User e = getFormE();
                daoE.insert(e);
                Account a = getFormA();
                daoA.insert(a);
                new MainForm().showForm(new FormListEmpolyee());
                MsgBox.alert(this, "Thêm mới thành công..^^..");
                this.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.warring(this, "Thêm mới thất bại!!");
        }
    }

    public User getFormE() {
        User e = new User();
        e.setFullname(txtName.getText());
        e.setRole(rdoManage.isSelected());
        e.setGender(rdoMale.isSelected());
        e.setDateOfBirth(XDate.toDate(txtBirth.getText(), "dd-MM-yyyy"));
        e.setAdress(txtAdress.getText());
        e.setPhoneNumber(txtPhone.getText());
        e.setEmail(txtEmail.getText());
        e.setStatus(rdoWorking.isSelected());
        e.setSalary(Double.parseDouble(txtSalary.getText()));

        return e;
    }

    public Account getFormA() {
        Account a = new Account();
        a.setUserName(txtUsername.getText());
        a.setPassWord(String.valueOf(txtPassWord.getText()));
        return a;
    }

    public void clear() {
        txtName.setText("");
        txtBirth.setText("");
        txtAdress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
        rdoManage.setSelected(true);
        rdoMale.setSelected(true);
        rdoWorking.setSelected(true);
    }

    public void addEvenFillTable(ActionListener evt) {
        btnAddEmployee.addActionListener(evt);
    }
//    
//    public void addEvenUpdate(ActionListener evt) {
//        btnUpdate.addActionListener(evt);
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        GroupGender = new javax.swing.ButtonGroup();
        GroupStatus = new javax.swing.ButtonGroup();
        GroupRole = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPhone = new com.raven.suportSwing.TextField();
        txtName = new com.raven.suportSwing.TextField();
        txtEmail = new com.raven.suportSwing.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAdress = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        txtSalary = new com.raven.suportSwing.TextField();
        jLabel3 = new javax.swing.JLabel();
        rdoMale = new com.raven.suportSwing.RadioButtonCustom();
        rdoFeMale = new com.raven.suportSwing.RadioButtonCustom();
        rdoManage = new com.raven.suportSwing.RadioButtonCustom();
        rdoEmpolyee = new com.raven.suportSwing.RadioButtonCustom();
        jLabel4 = new javax.swing.JLabel();
        txtBirth = new com.raven.suportSwing.TextField();
        jLabel5 = new javax.swing.JLabel();
        rdoWorking = new com.raven.suportSwing.RadioButtonCustom();
        rdoLeave = new com.raven.suportSwing.RadioButtonCustom();
        txtUsername = new com.raven.suportSwing.TextField();
        txtPassWord = new com.raven.suportSwing.PasswordField();
        btnAddEmployee = new com.raven.suportSwing.MyButton();
        btnClear = new com.raven.suportSwing.MyButton();
        lblBirth = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblSalary = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        lblIDUser = new javax.swing.JLabel();
        btnUpdate = new com.raven.suportSwing.MyButton();
        btnClear1 = new com.raven.suportSwing.MyButton();

        dateChooser1.setTextRefernce(txtBirth);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Thêm Nhân Viên");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtPhone.setLabelText("Điện thoại");
        txtPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPhoneFocusGained(evt);
            }
        });

        txtName.setLabelText("Tên");
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameFocusGained(evt);
            }
        });

        txtEmail.setLabelText("Email");
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
        });

        txtAdress.setColumns(20);
        txtAdress.setRows(5);
        jScrollPane1.setViewportView(txtAdress);

        jLabel2.setText("Địa chỉ");

        txtSalary.setLabelText("Lương");
        txtSalary.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSalaryFocusGained(evt);
            }
        });

        jLabel3.setText("Giới tính");

        GroupGender.add(rdoMale);
        rdoMale.setSelected(true);
        rdoMale.setText("Nam");

        GroupGender.add(rdoFeMale);
        rdoFeMale.setText("Nữ");

        GroupRole.add(rdoManage);
        rdoManage.setText("Quản lý");

        GroupRole.add(rdoEmpolyee);
        rdoEmpolyee.setSelected(true);
        rdoEmpolyee.setText("Nhân Viên");

        jLabel4.setText("Chức vụ");

        txtBirth.setLabelText("Ngày sinh");
        txtBirth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBirthFocusGained(evt);
            }
        });

        jLabel5.setText("Tình trạng");

        GroupStatus.add(rdoWorking);
        rdoWorking.setSelected(true);
        rdoWorking.setText("Đang làm việc");

        GroupStatus.add(rdoLeave);
        rdoLeave.setText("Nghỉ làm");

        txtUsername.setLabelText("Username");
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsernameFocusGained(evt);
            }
        });

        txtPassWord.setLabelText("Password");
        txtPassWord.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassWordFocusGained(evt);
            }
        });

        btnAddEmployee.setText("Thêm ");
        btnAddEmployee.setRadius(10);
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });

        btnClear.setText("Làm mới");
        btnClear.setRadius(10);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        lblBirth.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblBirth.setForeground(new java.awt.Color(255, 51, 51));

        lblName.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 51, 51));

        lblPhone.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblPhone.setForeground(new java.awt.Color(255, 51, 51));

        lblSalary.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblSalary.setForeground(new java.awt.Color(255, 51, 51));

        lblEmail.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 51, 51));

        lblUser.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblUser.setForeground(new java.awt.Color(255, 51, 51));

        lblPass.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblPass.setForeground(new java.awt.Color(255, 51, 51));

        lblIDUser.setFont(new java.awt.Font("Tahoma", 0, 1)); // NOI18N
        lblIDUser.setForeground(new java.awt.Color(255, 255, 255));
        lblIDUser.setText("jLabel6");

        btnUpdate.setText("Sửa");
        btnUpdate.setRadius(10);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear1.setText("Huỷ");
        btnClear1.setRadius(10);
        btnClear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(txtSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoWorking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(rdoLeave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)))
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(rdoFeMale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                            .addComponent(lblPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBirth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIDUser))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdoEmpolyee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(rdoManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblIDUser)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoFeMale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPass, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoEmpolyee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoWorking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoLeave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 715, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPhoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusGained
        // TODO add your handling code here:
        lblPhone.setText("");
    }//GEN-LAST:event_txtPhoneFocusGained

    private void txtNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusGained
        // TODO add your handling code here:
        lblName.setText("");
    }//GEN-LAST:event_txtNameFocusGained

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
        // TODO add your handling code here:
        lblEmail.setText("");
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtSalaryFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSalaryFocusGained
        // TODO add your handling code here:
        lblSalary.setText("");
    }//GEN-LAST:event_txtSalaryFocusGained

    private void txtBirthFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBirthFocusGained
        // TODO add your handling code here:
        lblBirth.setText("");
    }//GEN-LAST:event_txtBirthFocusGained

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
        // TODO add your handling code here:
        lblUser.setText("");
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtPassWordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassWordFocusGained
        // TODO add your handling code here:
        lblPass.setText("");
    }//GEN-LAST:event_txtPassWordFocusGained

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed

//        this.insert();
    }//GEN-LAST:event_btnAddEmployeeActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        this.clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
//        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnClear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnClear1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormImportEmpolyeeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormImportEmpolyeeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormImportEmpolyeeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormImportEmpolyeeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormImportEmpolyeeJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GroupGender;
    private javax.swing.ButtonGroup GroupRole;
    private javax.swing.ButtonGroup GroupStatus;
    private com.raven.suportSwing.MyButton btnAddEmployee;
    private com.raven.suportSwing.MyButton btnClear;
    private com.raven.suportSwing.MyButton btnClear1;
    private com.raven.suportSwing.MyButton btnUpdate;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBirth;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblIDUser;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblSalary;
    private javax.swing.JLabel lblUser;
    private com.raven.suportSwing.RadioButtonCustom rdoEmpolyee;
    private com.raven.suportSwing.RadioButtonCustom rdoFeMale;
    private com.raven.suportSwing.RadioButtonCustom rdoLeave;
    private com.raven.suportSwing.RadioButtonCustom rdoMale;
    private com.raven.suportSwing.RadioButtonCustom rdoManage;
    private com.raven.suportSwing.RadioButtonCustom rdoWorking;
    private javax.swing.JTextArea txtAdress;
    private com.raven.suportSwing.TextField txtBirth;
    private com.raven.suportSwing.TextField txtEmail;
    private com.raven.suportSwing.TextField txtName;
    private com.raven.suportSwing.PasswordField txtPassWord;
    private com.raven.suportSwing.TextField txtPhone;
    private com.raven.suportSwing.TextField txtSalary;
    private com.raven.suportSwing.TextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
