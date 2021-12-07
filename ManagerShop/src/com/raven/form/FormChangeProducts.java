/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.InvoiceChangeDAO;
import com.fpt.DAO.InvoiceSellDAO;
import com.fpt.DAO.ProductItemDAO;
import com.fpt.DAO.ReturnProductDAO;
import com.fpt.entity.InvoiceChange;
import com.fpt.entity.InvoiceRetuns;
import com.fpt.entity.InvoiceSell;
import com.fpt.entity.ProductItem;
import com.fpt.utils.Auth;
import com.fpt.utils.MsgBox;
import com.fpt.utils.XDate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormChangeProducts extends javax.swing.JPanel {

    /**
     * Creates new form FormChangeProducts
     */
    DefaultTableModel model = null;
    DefaultTableModel modelList = null;

    public FormChangeProducts() {
        initComponents();
        model = new DefaultTableModel();
        modelList = new DefaultTableModel();
    }

    ReturnProductDAO reDao = new ReturnProductDAO();
    ProductItemDAO productItemDAO = new ProductItemDAO();
    InvoiceChangeDAO invoiceChangeDAO = new InvoiceChangeDAO();
    List<ProductItem> listPr;

    public boolean ShearchKeyFillTable(int id) {
        model = (DefaultTableModel) tableIn4Invoice.getModel();
        model.setRowCount(0);

        listPr = reDao.selectByIdInvoiceReturn(id);
        for (ProductItem d : listPr) {
            model.addRow(new Object[]{
                d.getIdInvoiceSell(), d.getId(), d.getProductName(), d.getQuantity(), d.getSize(), d.getColor(), d.getMaterial(), d.getPrice()
            });
            lblIDCustomer.setText(d.getNameCustomer());
            lblIDInvoice.setText(d.getIdInvoiceSell() + "");
        }
        if (listPr.size() > 0) {
            return true;
        } else {
            return false;
        }
//        if(list.get(0).getDateCreateInvoice())
    }

    public void fillTableByPrice() {
        int row = tableIn4Invoice.getSelectedRow();
        float price = (float) tableIn4Invoice.getValueAt(row, 7);
        int id = (int) tableIn4Invoice.getValueAt(row, 1);
        lblIdOld.setText(String.valueOf(id));
        modelList = (DefaultTableModel) tableListProduct.getModel();
        modelList.setRowCount(0);
        List<ProductItem> list = productItemDAO.selectByPrice(price, id);
        for (ProductItem p : list) {
            modelList.addRow(new Object[]{
                p.getId(), p.getProductName(), p.getSize(), p.getColor(), p.getMaterial(), p.getPrice()
            });
        }
    }

    public InvoiceChange getInvoiceChange() {
        InvoiceChange ir = new InvoiceChange();
        Calendar calendar = Calendar.getInstance();
        ir.setDateCreateInvoiceReturn(XDate.toString(calendar.getTime(), "hh:mm:ss aa yyyy-MM-dd"));
        ir.setDescription(txtNote.getText());
        ir.setIdInvoiceSell(Integer.valueOf(txtShearchInvoice.getText()));
        ir.setIdUser(Auth.user.getIdUser());
        if (lblIdOld.getText().trim().equals("") && lblIdOld.getText().trim().equals("")) {
            MsgBox.alert(this, "Không thể đổi hàng");
            return null;
        }
        int idOld = Integer.parseInt(lblIdOld.getText());
        int idNew = Integer.parseInt(lblIdNew.getText());
        ir.setIdDetailNew(idNew);
        ir.setIdDetailOld(idOld);
        List<ProductItem> items = reDao.selectByIdInvoiceReturn(Integer.valueOf(txtShearchInvoice.getText()));
        for (ProductItem p : items) {
            ir.setIdCustomer(p.getIdCustomer());
            ir.setNameCustomer(p.getNameCustomer());
            break;
        }
        return ir;
    }

    public void insertInvoiceChange() {
        InvoiceChange ir = getInvoiceChange();
        invoiceChangeDAO.insert(ir);
        productItemDAO.importProductItem(1, Integer.parseInt(lblIdOld.getText()));
        productItemDAO.sellProductItem(1, Integer.parseInt(lblIdNew.getText()));
        MsgBox.alert(this, "Thêm thành công!!!");
    }

    public boolean checkChange() {
        List<InvoiceChange> list = invoiceChangeDAO.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdInvoiceSell() == Integer.parseInt(txtShearchInvoice.getText())) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDayChange() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sDate = sdf.parse(listPr.get(0).getDateCreateInvoice());
            Date eDate = sdf.parse(XDate.toString(new Date(), "yyyy-MM-dd"));
            long sValue = sDate.getTime();
            long eValue = eDate.getTime();
            long tmp = Math.abs(sValue - eValue);
            long result = tmp / (24 * 60 * 60 * 1000);
            System.out.println(result);
            if (result > 2) {
                MsgBox.labelAlert(lblSearch, txtShearchInvoice, "Ngày đổi sản phẩm đã quá hạn");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
        txtShearchInvoice = new com.raven.suportSwing.TextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableIn4Invoice = new com.raven.suportSwing.TableColumn();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblIDCustomer = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        lblIDInvoice = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnAddEmployee = new com.raven.suportSwing.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableListProduct = new com.raven.suportSwing.TableColumn();
        scrollBarCustom2 = new com.raven.suportSwing.ScrollBarCustom();
        lblIdOld = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblIdNew = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtShearchInvoice.setLabelText("Tìm kiếm hóa đơn");
        txtShearchInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtShearchInvoiceKeyReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tableIn4Invoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Thanh toán", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Size", "Màu sắc", "Chất liệu", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableIn4Invoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableIn4InvoiceMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableIn4Invoice);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Khách hàng");

        lblIDCustomer.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblIDCustomer.setForeground(new java.awt.Color(255, 51, 51));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Mã Hoá đơn");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Ghi Chú");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane3.setViewportView(txtNote);

        lblIDInvoice.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblIDInvoice.setForeground(new java.awt.Color(255, 51, 51));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Sản phẩm trả");

        btnAddEmployee.setText("Đổi Hàng");
        btnAddEmployee.setRadius(10);
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });

        jScrollPane2.setVerticalScrollBar(scrollBarCustom2);

        tableListProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Size", "Màu ", "Chất liệu", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableListProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListProductMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableListProduct);

        lblIdOld.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblIdOld.setForeground(new java.awt.Color(255, 51, 51));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Sản phẩm đổi");

        lblIdNew.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblIdNew.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollBarCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIdOld, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIdNew, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblIDCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblIDInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(lblIDCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblIDInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel6))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(lblIdOld, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(lblIdNew, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addComponent(btnAddEmployee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scrollBarCustom2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Đổi hàng");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Tìm kiếm:");

        lblSearch.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtShearchInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtShearchInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableListProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListProductMouseClicked
        int row = tableListProduct.getSelectedRow();
        int id = (int) tableListProduct.getValueAt(row, 0);
        lblIdNew.setText(String.valueOf(id));
    }//GEN-LAST:event_tableListProductMouseClicked

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed
        insertInvoiceChange();
        model.setRowCount(0);
        modelList.setRowCount(0);
        lblSearch.setText("");
        lblIDCustomer.setText("");
        lblIDInvoice.setText("");
        txtNote.setText("");
        lblIdNew.setText("");
        lblIdOld.setText("");
        txtShearchInvoice.setText("");
    }//GEN-LAST:event_btnAddEmployeeActionPerformed

    private void tableIn4InvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableIn4InvoiceMouseClicked
        // TODO add your handling code here:
        if (checkDayChange() == false) {
            MsgBox.alert(this, "Hoá đơn đã quá hạn đổi");
            return;
        } else if (checkChange() == false) {
            MsgBox.alert(this, "Hoá đơn đã đổi hàng");
            return;
        } else if (checkReturn() == false) {
            MsgBox.alert(this, "Hoá đơn đã trả hàng");
            return;
        } else if (checkVoucher() == false) {
            MsgBox.alert(this, "Hoá đơn có voucher không thể đổi");
            return;
        } else {
            fillTableByPrice();
        }
    }//GEN-LAST:event_tableIn4InvoiceMouseClicked

    InvoiceSellDAO iDao = new InvoiceSellDAO();

    public boolean checkVoucher() {
        List<InvoiceSell> list = iDao.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdInvoiceSell() == Integer.parseInt(txtShearchInvoice.getText())) {
                if (list.get(i).getIdVoucher() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkReturn() {
        List<InvoiceRetuns> list = reDao.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdInvoiceSell() == Integer.parseInt(txtShearchInvoice.getText())) {
                return false;
            }
        }
        return true;
    }

    private void txtShearchInvoiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtShearchInvoiceKeyReleased
        if (txtShearchInvoice.getText().isEmpty()) {
            lblIDCustomer.setText("");
            lblIDInvoice.setText("");
            lblSearch.setText("");
            model.setRowCount(0);
            modelList.setRowCount(0);
            return;
        }
        try {
            if (ShearchKeyFillTable(Integer.valueOf(txtShearchInvoice.getText())) == false) {
                lblSearch.setText("Hoá đơn không tồn tại");
                return;
            } else {
                lblSearch.setText("");
            }
            if (checkChange() == false) {
                lblSearch.setText("Hoá đơn đã đổi hàng");
                return;
            }
            if (checkReturn() == false) {
                lblSearch.setText("Hoá đơn đã trả hàng");
                return;
            }
            if (checkVoucher() == false) {
                lblSearch.setText("Hoá đơn có voucher không thể đổi");
                return;
            }
            if (checkDayChange() == false) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.labelAlert(lblSearch, txtShearchInvoice, "Vui lòng nhập lại -.-");
        }
    }//GEN-LAST:event_txtShearchInvoiceKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnAddEmployee;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblIDCustomer;
    private javax.swing.JLabel lblIDInvoice;
    private javax.swing.JLabel lblIdNew;
    private javax.swing.JLabel lblIdOld;
    private javax.swing.JLabel lblSearch;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom2;
    private com.raven.suportSwing.TableColumn tableIn4Invoice;
    private com.raven.suportSwing.TableColumn tableListProduct;
    private javax.swing.JTextArea txtNote;
    private com.raven.suportSwing.TextField txtShearchInvoice;
    // End of variables declaration//GEN-END:variables
}
