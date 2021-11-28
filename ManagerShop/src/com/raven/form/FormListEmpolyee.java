/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.UserDAO;
import com.fpt.entity.User;
import com.fpt.utils.Excel;
import com.fpt.utils.MsgBox;
import com.fpt.utils.XDate;
import com.raven.JFrame.FormImportEmpolyeeJFrame;
import com.raven.swing.table.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormListEmpolyee extends javax.swing.JPanel {

    UserDAO user = new UserDAO();
    FormImportEmpolyeeJFrame formImportEmpolyeeJFrame = new FormImportEmpolyeeJFrame();
    FormImportEmpolyeeJFrame formUpdateEmpolyeeJFrame;

    /**
     * Creates new form FormProducts
     */
    public FormListEmpolyee() {
        initComponents();
        setOpaque(false);
        fillTable();

        formImportEmpolyeeJFrame.addEvenFillTable(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formImportEmpolyeeJFrame.insert();
                fillTable();
            }
        });

    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        List<User> list = user.selectAll();
        for (User u : list) {
            model.addRow(new Object[]{
                u.getIdUser(), u.getFullname(), u.isRole() ? "Quản lý" : "Nhân viên", u.isGender() ? "Nam" : "Nữ",
                XDate.toString(u.getDateOfBirth(), "dd-MM-yyyy"), u.getAdress(), u.getPhoneNumber(), u.getEmail(), u.getSalary()
            });
        }
        System.out.println("Hello");
    }

    public void fillSearch() {
        DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        String keyString = txtSearch.getText();
        List<User> list = user.selectByKey(keyString);
        if (list.isEmpty()) {
            lblSearch.setText("Không có nhân viên " + keyString);
            return;
        }
        for (User u : list) {
            model.addRow(new Object[]{
                u.getIdUser(), u.getFullname(), u.isRole() ? "Quản lý" : "Nhân viên", u.isGender() ? "Nam" : "Nữ",
                XDate.toString(u.getDateOfBirth(), "dd-MM-yyyy"), u.getAdress(), u.getPhoneNumber(), u.getEmail(), u.getSalary()
            });
        }
        lblSearch.setText("");
    }

    public void delete() {
        int index = tableShow.getSelectedRow();
        int idUser = (int) tableShow.getValueAt(index, 0);
        user.delete(idUser);
        fillTable();
        MsgBox.alert(this, "Xoá OK");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        myButton5 = new com.raven.suportSwing.MyButton();
        myButton6 = new com.raven.suportSwing.MyButton();
        myButton8 = new com.raven.suportSwing.MyButton();
        txtSearch = new com.raven.suportSwing.TextField();
        lblSearch = new javax.swing.JLabel();
        myButton7 = new com.raven.suportSwing.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShow = new com.raven.suportSwing.TableColumn();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Nhân Viên");

        myButton5.setText("Tìm");
        myButton5.setRadius(20);
        myButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton5ActionPerformed(evt);
            }
        });

        myButton6.setText("Thêm Nhân Viên");
        myButton6.setRadius(20);
        myButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton6ActionPerformed(evt);
            }
        });

        myButton8.setText("Xóa Nhân Viên");
        myButton8.setRadius(20);
        myButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton8ActionPerformed(evt);
            }
        });

        txtSearch.setLabelText("Tìm theo tên or mã");
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
        });
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 51, 0));

        myButton7.setText("Xuất");
        myButton7.setRadius(20);
        myButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                        .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tableShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhân Viên", "Tên Nhân Viên", "Chức Vụ", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "Số Điện thoại", "Email", "Lương"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableShowMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableShow);
        if (tableShow.getColumnModel().getColumnCount() > 0) {
            tableShow.getColumnModel().getColumn(0).setResizable(false);
            tableShow.getColumnModel().getColumn(1).setResizable(false);
            tableShow.getColumnModel().getColumn(2).setResizable(false);
            tableShow.getColumnModel().getColumn(3).setResizable(false);
            tableShow.getColumnModel().getColumn(4).setResizable(false);
            tableShow.getColumnModel().getColumn(5).setResizable(false);
            tableShow.getColumnModel().getColumn(6).setResizable(false);
            tableShow.getColumnModel().getColumn(7).setResizable(false);
            tableShow.getColumnModel().getColumn(8).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void myButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton5ActionPerformed
        // TODO add your handling code here:
        fillSearch();
    }//GEN-LAST:event_myButton5ActionPerformed

    private void myButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton6ActionPerformed
        // TODO add your handling code here:
        formImportEmpolyeeJFrame.setVisible(true);
    }//GEN-LAST:event_myButton6ActionPerformed

    private void myButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton8ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_myButton8ActionPerformed

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
        fillSearch();

    }//GEN-LAST:event_txtSearchActionPerformed

    private void tableShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableShowMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int index = tableShow.getSelectedRow();
            int idUser = (int) tableShow.getValueAt(index, 0);
            String fullname = tableShow.getValueAt(index, 1).toString();
            String role = tableShow.getValueAt(index, 2).toString();
            String gender = tableShow.getValueAt(index, 3).toString();
            String birth = tableShow.getValueAt(index, 4).toString();
            String address = tableShow.getValueAt(index, 5).toString();
            String phone = tableShow.getValueAt(index, 6).toString();
            String email = tableShow.getValueAt(index, 7).toString();
            String salary = tableShow.getValueAt(index, 8).toString();
            formUpdateEmpolyeeJFrame = new FormImportEmpolyeeJFrame(fullname, role, gender, birth, address, phone, email, salary, idUser);
            formUpdateEmpolyeeJFrame.setVisible(true);
        }

        if (formUpdateEmpolyeeJFrame == null) {
            return;
        } else {
            formUpdateEmpolyeeJFrame.addEvenUpdate(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    formUpdateEmpolyeeJFrame.update();
                    fillTable();
                    System.out.println("update");
                }
            });

        }


    }//GEN-LAST:event_tableShowMouseClicked

    public void excelEmpolyee() throws IOException {
        Excel.outputFile((DefaultTableModel) tableShow.getModel());
        MsgBox.alert(this, "Xuất file thành công");
    }
    private void myButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton7ActionPerformed
        // TODO add your handling code here:
        try {
            excelEmpolyee();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_myButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private com.raven.suportSwing.MyButton myButton5;
    private com.raven.suportSwing.MyButton myButton6;
    private com.raven.suportSwing.MyButton myButton7;
    private com.raven.suportSwing.MyButton myButton8;
    private com.raven.suportSwing.TableColumn tableShow;
    private com.raven.suportSwing.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
