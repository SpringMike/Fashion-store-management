/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import com.fpt.DAO.CustomerDAO;
import com.fpt.DAO.InvoiceChangeDAO;
import com.fpt.entity.Customer;
import com.fpt.entity.InvoiceChange;
import com.fpt.utils.Excel;
import com.fpt.utils.MsgBox;
import com.raven.JFrame.FormDetailChangeProduct;
import java.io.IOException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class FormInvoiceChangeProduct extends javax.swing.JPanel {

    /**
     * Creates new form FormInvoiceChangeProduct
     */
    public FormInvoiceChangeProduct() {
        initComponents();
        setOpaque(false);
        lblSearchId.setVisible(false);
        fillPagination();
    }
    DefaultTableModel model;
    InvoiceChangeDAO invoiceChangeProductDAO = new InvoiceChangeDAO();

    int page = 1;
    int rowCountPerPage = 5;
    int totalPage = 1;
    Integer totalData = 0;
    boolean flag = false;

    public void edit() {
        if (page == 1) {
            btnFirst.setEnabled(false);
            btnBack.setEnabled(false);
        } else {
            btnFirst.setEnabled(true);
            btnBack.setEnabled(true);
        }

        if (page == totalPage) {
            btnLast.setEnabled(false);
            btnNext.setEnabled(false);
        } else {
            btnLast.setEnabled(true);
            btnNext.setEnabled(true);
        }

        if (page > totalPage) {
            page = 1;
        }
    }

    public void fillPagination() {
        totalData = invoiceChangeProductDAO.totalPage("");
        rowCountPerPage = Integer.valueOf(cbbPagination.getSelectedItem().toString());
        Double totalPageD = Math.ceil(totalData.doubleValue() / rowCountPerPage);
        totalPage = totalPageD.intValue();

        edit();
        model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);

        List<InvoiceChange> list = invoiceChangeProductDAO.pagingPage(page, rowCountPerPage, "");
        CustomerDAO cDao = new CustomerDAO();
        List<Customer> listC = cDao.selectAll();
        String phone = "";
        for (InvoiceChange i : list) {
            for (int j = 0; j < listC.size(); j++) {
                if (i.getIdCustomer() == listC.get(j).getId()) {
                    phone = listC.get(j).getPhoneNumber();
                }
            }
            model.addRow(new Object[]{
                i.getId(), i.getIdInvoiceSell(), i.getDateCreateInvoiceReturn(), i.getNameCustomer(), phone, i.getDescription()
            });
        }
        lblCount.setText("Page " + page + " for " + totalPage);

    }

    public void searchDateFillTable() {
        totalData = invoiceChangeProductDAO.totalPage(txtDate.getText());
        rowCountPerPage = Integer.valueOf(cbbPagination.getSelectedItem().toString());
        Double totalPageD = Math.ceil(totalData.doubleValue() / rowCountPerPage);
        totalPage = totalPageD.intValue();

        if (totalData == 0) {
            MsgBox.alert(this, "Ngày bạn chọn không có hóa đơn nào");
            return;
        }

        edit();
        model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        List<InvoiceChange> list = invoiceChangeProductDAO.pagingPage(page, rowCountPerPage, txtDate.getText());
        CustomerDAO cDao = new CustomerDAO();
        List<Customer> listC = cDao.selectAll();
        String phone = "";
        for (InvoiceChange i : list) {
            for (int j = 0; j < listC.size(); j++) {
                if (i.getIdCustomer() == listC.get(j).getId()) {
                    phone = listC.get(j).getPhoneNumber();
                }
            }
            model.addRow(new Object[]{
                i.getId(), i.getIdInvoiceSell(), i.getDateCreateInvoiceReturn(), i.getNameCustomer(), phone, i.getDescription()
            });
        }
        lblCount.setText("Page " + page + " for " + totalPage);
    }

    public void excelReturn() throws IOException {
        Excel.outExcel((DefaultTableModel) tableShow.getModel());
        MsgBox.alert(this, "Xuất file thành công");
    }

    public void fillSearch() {
        if (txtSearchId.getText().trim().equals("")) {
            return;
        }
        model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        int id = Integer.parseInt(txtSearchId.getText());
        InvoiceChange i = invoiceChangeProductDAO.selectById(id);
        if (i == null) {
            lblSearchId.setVisible(true);
            lblSearchId.setText("Không có mặt hàng có bằng " + id);
            return;
        }
        CustomerDAO cDao = new CustomerDAO();
        List<Customer> listC = cDao.selectAll();
        String phone = "";
        for (int j = 0; j < listC.size(); j++) {
            if (i.getIdCustomer() == listC.get(j).getId()) {
                phone = listC.get(j).getPhoneNumber();
            }
        }
        model.addRow(new Object[]{
            i.getId(), i.getIdInvoiceSell(), i.getDateCreateInvoiceReturn(), i.getNameCustomer(), phone, i.getDescription()
        });

        lblSearchId.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser2 = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSearchId = new com.raven.suportSwing.TextField();
        btnSearch = new com.raven.suportSwing.MyButton();
        btnSearch2 = new com.raven.suportSwing.MyButton();
        txtDate = new com.raven.suportSwing.TextField();
        btnFillDate = new com.raven.suportSwing.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShow = new com.raven.suportSwing.TableColumn();
        btnReset = new com.raven.suportSwing.MyButton();
        lblSearchId = new javax.swing.JLabel();
        btnFirst = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        cbbPagination = new javax.swing.JComboBox<>();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblCount = new javax.swing.JLabel();
        scrollBarCustom1 = new com.raven.suportSwing.ScrollBarCustom();

        dateChooser2.setDateFormat("yyyy-MM-dd");
        dateChooser2.setTextRefernce(txtDate);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Hoá đơn đổi hàng");

        txtSearchId.setLabelText("Tìm theo hoá đơn đổi hàng");
        txtSearchId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchIdFocusGained(evt);
            }
        });

        btnSearch.setText("Tìm kiếm");
        btnSearch.setRadius(20);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnSearch2.setText("Xuất");
        btnSearch2.setRadius(20);
        btnSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch2ActionPerformed(evt);
            }
        });

        txtDate.setLabelText("Thời gian");

        btnFillDate.setText("Lọc");
        btnFillDate.setRadius(20);
        btnFillDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFillDateActionPerformed(evt);
            }
        });

        tableShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Trả hàng", "Mã thanh toán", "Thời gian", "Khách hàng", "SDT", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        btnReset.setText("Reset");
        btnReset.setRadius(20);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblSearchId.setForeground(new java.awt.Color(225, 0, 0));
        lblSearchId.setText("jLabel1");

        btnFirst.setText("<<");
        btnFirst.setToolTipText("");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnBack.setText("<");
        btnBack.setToolTipText("");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        cbbPagination.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5", "10", "15", "20", "25", "30" }));
        cbbPagination.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbPaginationItemStateChanged(evt);
            }
        });

        btnNext.setText(">");
        btnNext.setToolTipText("");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">>");
        btnLast.setToolTipText("");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblCount.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnFillDate, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnFirst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbPagination, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLast))
                            .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSearchId, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSearchId, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 990, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addComponent(scrollBarCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(scrollBarCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearchId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSearchId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnFillDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(92, 92, 92)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnBack)
                                    .addComponent(cbbPagination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNext)
                                    .addComponent(btnLast)
                                    .addComponent(btnFirst))
                                .addGap(34, 34, 34)
                                .addComponent(lblCount))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchIdFocusGained
        lblSearchId.setVisible(false);
    }//GEN-LAST:event_txtSearchIdFocusGained

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        fillSearch();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch2ActionPerformed
        // TODO add your handling code here:
        try {
            excelReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSearch2ActionPerformed

    private void btnFillDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFillDateActionPerformed
        // TODO add your handling code here:
        searchDateFillTable();
        flag = true;
    }//GEN-LAST:event_btnFillDateActionPerformed

    private void tableShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableShowMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int row = tableShow.getSelectedRow();
            int id = (int) tableShow.getValueAt(row, 0);
            new FormDetailChangeProduct(id).setVisible(true);
        }
    }//GEN-LAST:event_tableShowMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        fillPagination();
        flag = false;
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        page = 1;
        if (flag) {
            searchDateFillTable();
            return;
        }
        fillPagination();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        page--;
        if (flag) {
            searchDateFillTable();
            return;
        }
        fillPagination();
    }//GEN-LAST:event_btnBackActionPerformed

    private void cbbPaginationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbPaginationItemStateChanged
        if (flag) {
            searchDateFillTable();
            return;
        }
        fillPagination();
    }//GEN-LAST:event_cbbPaginationItemStateChanged

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        page++;
        if (flag) {
            searchDateFillTable();
            return;
        }
        fillPagination();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        page = totalPage;
        if (flag) {
            searchDateFillTable();
            return;
        }
        fillPagination();
    }//GEN-LAST:event_btnLastActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private com.raven.suportSwing.MyButton btnFillDate;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private com.raven.suportSwing.MyButton btnReset;
    private com.raven.suportSwing.MyButton btnSearch;
    private com.raven.suportSwing.MyButton btnSearch2;
    private javax.swing.JComboBox<String> cbbPagination;
    private com.raven.datechooser.DateChooser dateChooser2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCount;
    private javax.swing.JLabel lblSearchId;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom1;
    private com.raven.suportSwing.TableColumn tableShow;
    private com.raven.suportSwing.TextField txtDate;
    private com.raven.suportSwing.TextField txtSearchId;
    // End of variables declaration//GEN-END:variables
}
