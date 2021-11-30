/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.SupplierDao;
import com.fpt.Validate.Validate;
import com.fpt.Validate.labelValidate;
import com.fpt.entity.Supplier;
import com.fpt.utils.Excel;
import com.fpt.utils.MsgBox;
import java.io.IOException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormSupplier extends javax.swing.JPanel {

    SupplierDao sDao = new SupplierDao();

    /**
     * Creates new form FormProducts
     */
    public FormSupplier() {
        initComponents();
        setOpaque(false);
        btnUpdate.setEnabled(false);
//        btnDelete.setEnabled(false);

        fillTable();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        List<Supplier> list = sDao.selectAll();
        for (Supplier s : list) {
            model.addRow(new Object[]{
                s.getIdSupplier(), s.getNameMaterial(), s.getAddress(), s.getPhoneNumber()
            });
        }
    }

    public void setForm(Supplier s) {
        txtAddress.setText(s.getAddress());
        txtPhoneNumber.setText(s.getPhoneNumber());
        txtnameSupplier.setText(s.getNameMaterial());
    }

    public Supplier getForm() {
        Supplier s = new Supplier();
        s.setAddress(txtAddress.getText());
        s.setNameMaterial(txtnameSupplier.getText());
        s.setPhoneNumber(txtPhoneNumber.getText());
        return s;
    }

    public void clearForm() {
        txtAddress.setText("");
        txtPhoneNumber.setText("");
        txtnameSupplier.setText("");
        lblAddress.setText("");
        lblPhoneNumber.setText("");
        lblnameSupplier.setText("");
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(true);
        tableShow.clearSelection();
//        btnDelete.setEnabled(true);
    }

    public void edit() {
        int row = tableShow.getSelectedRow();
        btnAdd.setEnabled(false);
//        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
        int code = (int) tableShow.getValueAt(row, 0);
        Supplier s = sDao.selectById(code);
        setForm(s);
    }

    public boolean checkPhoneNumber(String acc) {
        for (int i = 0; i < sDao.selectAll().size(); i++) {
            if (sDao.selectAll().get(i).getPhoneNumber().trim().equals(acc.trim())) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        Supplier s = getForm();
        int row = tableShow.getSelectedRow();
        s.setIdSupplier((int) tableShow.getValueAt(row, 0));
        try {
            if (labelValidate.checkEmpty(lblnameSupplier, txtnameSupplier, "Không để trống tên Nhà Cung cấp") == false) {
                return;
            } else if (labelValidate.checkEmpty(lblAddress, txtAddress, "không để trống địa chỉ") == false) {
                return;
            } else if (labelValidate.checkEmpty(lblPhoneNumber, txtPhoneNumber, "không để trống số điện thoại") == false) {
                return;
            } else if (labelValidate.checkNumber(lblPhoneNumber, txtPhoneNumber, "Số điện thoại không hợp lệ") == false) {
                return;
            } else {
                sDao.update(s);
                fillTable();
                clearForm();
                MsgBox.alert(this, "Cập nhập thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        Supplier s = getForm();
        try {
            if (labelValidate.checkEmpty(lblnameSupplier, txtnameSupplier, "Không để trống tên Nhà Cung cấp") == false) {
                return;
            } else if (labelValidate.checkEmpty(lblAddress, txtAddress, "không để trống địa chỉ") == false) {
                return;
            } else if (labelValidate.checkEmpty(lblPhoneNumber, txtPhoneNumber, "không để trống số điện thoại") == false) {
                return;
            } else if (labelValidate.checkNumber(lblPhoneNumber, txtPhoneNumber, "Số điện thoại không hợp lệ") == false) {
                return;
            } else {
                sDao.insert(s);
                fillTable();
                clearForm();
                MsgBox.alert(this, "Thêm mới thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchSupplier() {
        DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        String keyWord = txtSearch.getText();
        List<Supplier> list = sDao.selectByKeyWord(keyWord);
        if (list.isEmpty()) {
            lblSearch.setText("Không có nhà cung cấp " + keyWord);
            return;
        }
        for (Supplier s : list) {
            model.addRow(new Object[]{
                s.getIdSupplier(), s.getNameMaterial(), s.getAddress(), s.getPhoneNumber()
            });
        }
        lblSearch.setText("");
    }

    public void searchSupplierID() {
        DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        int keyWord = Integer.valueOf(txtSearch.getText());
        Supplier s = sDao.selectById(keyWord);
        if (s == null) {
            lblSearch.setText("Không có nhà cung cấp " + keyWord);
            return;
        }
        model.addRow(new Object[]{
            s.getIdSupplier(), s.getNameMaterial(), s.getAddress(), s.getPhoneNumber()
        });
        lblSearch.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new com.raven.suportSwing.TextField();
        myButton2 = new com.raven.suportSwing.MyButton();
        lblSearch = new javax.swing.JLabel();
        myButton4 = new com.raven.suportSwing.MyButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShow = new com.raven.suportSwing.TableColumn();
        jPanel3 = new javax.swing.JPanel();
        myButton3 = new com.raven.suportSwing.MyButton();
        btnAdd = new com.raven.suportSwing.MyButton();
        btnUpdate = new com.raven.suportSwing.MyButton();
        txtPhoneNumber = new com.raven.suportSwing.TextField();
        txtnameSupplier = new com.raven.suportSwing.TextField();
        txtAddress = new com.raven.suportSwing.TextField();
        lblnameSupplier = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblPhoneNumber = new javax.swing.JLabel();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Nhà cung cấp");

        txtSearch.setLabelText("Tìm theo tên or mã");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        myButton2.setText("Tìm");
        myButton2.setRadius(20);
        myButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton2ActionPerformed(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 51, 0));

        myButton4.setText("Xuất");
        myButton4.setRadius(20);
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                        .addGap(31, 31, 31)
                        .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(239, 239, 239)
                        .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tableShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID NCC", "Tên NCC", "Địa Chỉ", "SĐT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Chi tiết nhà cung cấp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        myButton3.setLabel("Xóa form");
        myButton3.setRadius(20);
        myButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton3ActionPerformed(evt);
            }
        });

        btnAdd.setText("Thêm");
        btnAdd.setRadius(20);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Cập Nhật");
        btnUpdate.setRadius(20);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        txtPhoneNumber.setLabelText("SĐT");
        txtPhoneNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPhoneNumberFocusGained(evt);
            }
        });
        txtPhoneNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneNumberActionPerformed(evt);
            }
        });

        txtnameSupplier.setLabelText("Tên NCC");
        txtnameSupplier.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtnameSupplierFocusGained(evt);
            }
        });
        txtnameSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnameSupplierActionPerformed(evt);
            }
        });

        txtAddress.setLabelText("Địa chỉ ");
        txtAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAddressFocusGained(evt);
            }
        });
        txtAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddressActionPerformed(evt);
            }
        });

        lblnameSupplier.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblnameSupplier.setForeground(new java.awt.Color(255, 51, 0));

        lblAddress.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(255, 51, 0));

        lblPhoneNumber.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblPhoneNumber.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblnameSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtnameSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtnameSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblnameSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(459, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(1207, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 361, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel3.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddressActionPerformed

    private void txtAddressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAddressFocusGained
        // TODO add your handling code here:
        lblAddress.setText("");
    }//GEN-LAST:event_txtAddressFocusGained

    private void txtnameSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnameSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnameSupplierActionPerformed

    private void txtnameSupplierFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtnameSupplierFocusGained
        // TODO add your handling code here:
        lblnameSupplier.setText("");
    }//GEN-LAST:event_txtnameSupplierFocusGained

    private void txtPhoneNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneNumberActionPerformed

    private void txtPhoneNumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneNumberFocusGained
        // TODO add your handling code here:
        lblPhoneNumber.setText("");
    }//GEN-LAST:event_txtPhoneNumberFocusGained

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void myButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton3ActionPerformed
        clearForm();
    }//GEN-LAST:event_myButton3ActionPerformed

    private void tableShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableShowMouseClicked
        // TODO add your handling code here:
        edit();
    }//GEN-LAST:event_tableShowMouseClicked

    private void myButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton4ActionPerformed
        // TODO add your handling code here:
        try {
            excelSupplier();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_myButton4ActionPerformed

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myButton2ActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        try {
            searchSupplierID();
        } catch (Exception e) {
            searchSupplier();
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    public void excelSupplier() throws IOException {
        Excel.outputFile((DefaultTableModel) tableShow.getModel());
        MsgBox.alert(this, "Xuất file thành công");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnAdd;
    private com.raven.suportSwing.MyButton btnUpdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblPhoneNumber;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblnameSupplier;
    private com.raven.suportSwing.MyButton myButton2;
    private com.raven.suportSwing.MyButton myButton3;
    private com.raven.suportSwing.MyButton myButton4;
    private com.raven.suportSwing.TableColumn tableShow;
    private com.raven.suportSwing.TextField txtAddress;
    private com.raven.suportSwing.TextField txtPhoneNumber;
    private com.raven.suportSwing.TextField txtSearch;
    private com.raven.suportSwing.TextField txtnameSupplier;
    // End of variables declaration//GEN-END:variables
}
