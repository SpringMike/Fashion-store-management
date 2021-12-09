/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.main;

import com.fpt.DAO.AccountDao;
import com.fpt.DAO.UserDAO;
import com.fpt.Validate.Validate;
import com.fpt.entity.Account;
import com.fpt.entity.User;
import com.fpt.utils.Auth;
import com.fpt.utils.MsgBox;
import com.fpt.utils.XImage;
import com.raven.dialog.Message;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.border.LineBorder;

/**
 *
 * @author ducit
 */
public class Login extends javax.swing.JPanel {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        lbAlertPassword.setText("");
        lbAlertUsername.setText("");
        txtUser.grabFocus();

    }

    AccountDao dao = new AccountDao();

    public void backLogin() {
        txtUser.grabFocus();
    }

    public void clickLogin() {
        txtUser.setBackground(Color.white);
        txtPassWord.setBackground(Color.white);
        if (Validate.checkEmpty(lbAlertUsername, txtUser, "Không được để trống Username!") == false && Validate.checkEmpty(lbAlertPassword, txtPassWord, "Không được để trống password!") == false) {
            return;
        } else if (Validate.checkEmpty(lbAlertUsername, txtUser, "Không được để trống password!") == false) {
            return;
        } else if (Validate.checkEmpty(lbAlertPassword, txtPassWord, "Không được để trống password!") == false) {
            return;
        } else {
            login();
        }
    }

    public boolean login() {
        String userName = txtUser.getText();
        String passWord = new String(txtPassWord.getPassword());
        UserDAO uDao = new UserDAO();

        try {
            Account account = dao.selectById(userName);
            if (account == null) {    //nếu user sai
                lbAlertUsername.setText("Sai tên đăng nhập!");
                return false;
            } else {
                String passwordSystem = account.getPassWord();
                if (passWord.equals(passwordSystem)) {
                    MsgBox.alert(this, "Đăng nhập thành công!");
                    User user = uDao.selectById(account.getIdUser());
                    Auth.user = user;
                    new Main().setVisible(true);
                    this.setVisible(false);
                    return true;
                } else {
                    lbAlertPassword.setText("Sai mật khẩu!");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
        }
        return true;

    }

    public void addEventRegister(ActionListener event) {
        btnFogot.addActionListener(event);
    }

    public void addEventLogin(ActionListener event) {
        btnLogin.addActionListener(event);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtUser = new com.raven.suportSwing.TextField();
        btnLogin = new com.raven.suportSwing.MyButton();
        txtPassWord = new com.raven.suportSwing.PasswordField();
        btnFogot = new com.raven.suportSwing.MyButton();
        lbAlertUsername = new javax.swing.JLabel();
        lbAlertPassword = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(69, 68, 68));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Login");

        txtUser.setLabelText("Usename");
        txtUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserFocusGained(evt);
            }
        });
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });

        btnLogin.setText("Login");
        btnLogin.setBorderColor(new java.awt.Color(51, 153, 255));
        btnLogin.setColorClick(new java.awt.Color(255, 102, 204));
        btnLogin.setColorOver(new java.awt.Color(51, 153, 255));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        txtPassWord.setLabelText("Password");
        txtPassWord.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassWordFocusGained(evt);
            }
        });
        txtPassWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassWordActionPerformed(evt);
            }
        });

        btnFogot.setText("Fogot Password");
        btnFogot.setBorderColor(new java.awt.Color(51, 153, 255));
        btnFogot.setColorClick(new java.awt.Color(255, 102, 204));
        btnFogot.setColorOver(new java.awt.Color(51, 153, 255));
        btnFogot.setContentAreaFilled(true);
        btnFogot.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        lbAlertUsername.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lbAlertUsername.setForeground(new java.awt.Color(255, 51, 0));
        lbAlertUsername.setText("jLabel1");

        lbAlertPassword.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lbAlertPassword.setForeground(new java.awt.Color(255, 51, 0));
        lbAlertPassword.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbAlertUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassWord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addComponent(btnFogot, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbAlertPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(51, 51, 51)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAlertUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPassWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAlertPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnFogot, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserFocusGained
        lbAlertUsername.setText("");
    }//GEN-LAST:event_txtUserFocusGained

    private void txtPassWordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassWordFocusGained
        lbAlertPassword.setText("");
    }//GEN-LAST:event_txtPassWordFocusGained

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserActionPerformed

    private void txtPassWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassWordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassWordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnFogot;
    private com.raven.suportSwing.MyButton btnLogin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbAlertPassword;
    private javax.swing.JLabel lbAlertUsername;
    private com.raven.suportSwing.PasswordField txtPassWord;
    private com.raven.suportSwing.TextField txtUser;
    // End of variables declaration//GEN-END:variables

}
