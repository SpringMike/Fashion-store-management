/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.DetailInvoiceSellDAO;
import com.fpt.DAO.DetailsChangeProductDAO;
import com.fpt.DAO.DetailsInvoiceChangeDAO;
import com.fpt.DAO.InvoiceChangeDAO;
import com.fpt.DAO.InvoiceSellDAO;
import com.fpt.DAO.ProductItemDAO;
import com.fpt.DAO.ReturnProductDAO;
import com.fpt.entity.DetailInvoiceSell;
import com.fpt.entity.DetailsChangeProducts;
import com.fpt.entity.DetailsInvoiceChange;
import com.fpt.entity.InvoiceChange;
import com.fpt.entity.InvoiceRetuns;
import com.fpt.entity.InvoiceSell;
import com.fpt.entity.ProductItem;
import com.fpt.utils.Auth;
import com.fpt.utils.MsgBox;
import com.fpt.utils.XDate;
import com.raven.JFrame.FormChangeProductJframe;
import com.raven.JFrame.FormDetailChangeProduct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    DetailInvoiceSellDAO deDao = new DetailInvoiceSellDAO();
    FormChangeProductJframe formChangeProductJframe = new FormChangeProductJframe();
    List<ProductItem> list2 = new ArrayList<>();

    public FormChangeProducts() {
        initComponents();
        model = new DefaultTableModel();
        modelList = new DefaultTableModel();
    }

    ReturnProductDAO reDao = new ReturnProductDAO();
    ProductItemDAO productItemDAO = new ProductItemDAO();
    InvoiceChangeDAO invoiceChangeDAO = new InvoiceChangeDAO();
    List<ProductItem> listPr;

    public void fillTableListProduct(List<ProductItem> list) {
        modelList = (DefaultTableModel) tableListProduct.getModel();
//
//        for (ProductItem p : list) {
//            modelList.addRow(new Object[]{
//                p.getId(), p.getProductName(), p.getSize(), p.getColor(), p.getMaterial(), p.getPrice(), p.getQuantity()
//            });
//        }
        int totalRow = tableListProduct.getRowCount();

        for (int i = 0; i < list.size(); i++) {
            ProductItem p = list.get(i);
            if (tableListProduct.getRowCount() <= 0) {
                for (ProductItem p2 : list) {
                    modelList.addRow(new Object[]{
                        p2.getId(), p2.getProductName(), p2.getSize(), p2.getColor(), p2.getMaterial(), p2.getPrice(), p2.getQuantity()
                    });
                }
            } else {
                for (int j = 0; j < totalRow; j++) {
                    if (p.getId() == (int) tableListProduct.getValueAt(j, 0)) {
                        p.setQuantity(p.getQuantity() + (int) tableListProduct.getValueAt(j, 6));
                        tableListProduct.setValueAt(p.getQuantity(), j, 6);
                    } else {
                        modelList.addRow(new Object[]{
                            p.getId(), p.getProductName(), p.getSize(), p.getColor(), p.getMaterial(), p.getPrice(), p.getQuantity()
                        });
                    }
                }
            }
        }
        for (int i = 0; i < tableListProduct.getRowCount(); i++) {
            for (int j = i + 1; j < tableListProduct.getRowCount(); j++) {
                if ((int) tableListProduct.getValueAt(i, 0) == (int) tableListProduct.getValueAt(j, 0)) {
                    modelList.removeRow(j);
                    j--;
                }
            }
        }
    }

    List<DetailsInvoiceChange> listDetailsInvoiceChange = new ArrayList<>();
    List<DetailsChangeProducts> listDetailsChangeProducts = new ArrayList<>();
    DetailsInvoiceChangeDAO detailsInvoiceChangeDAO = new DetailsInvoiceChangeDAO();
    DetailsChangeProductDAO detailsChangeProductDAO = new DetailsChangeProductDAO();

    public void getDetailInvoiceChange() {
        for (int i = 0; i < tableIn4Invoice.getRowCount(); i++) {
            if (!(listPr.get(i).getQuantity() == (int) tableIn4Invoice.getValueAt(i, 3))) {
                DetailsInvoiceChange de = new DetailsInvoiceChange();
                int idPr = (int) tableIn4Invoice.getValueAt(i, 1);
                int quanity = (int) tableIn4Invoice.getValueAt(i, 4);
                de.setIdProductItem(idPr);
                de.setQuantity(quanity);
                listDetailsInvoiceChange.add(de);
                ProductItem p = productItemDAO.selectById(idPr);
                productItemDAO.updateQuantity(p.getQuantity() + quanity, idPr);
            }
        }
    }

    public void getDetailChangeProduct() {
        for (int i = 0; i < tableListProduct.getRowCount(); i++) {
            DetailsChangeProducts de = new DetailsChangeProducts();
            int idPr = (int) tableListProduct.getValueAt(i, 0);
            int quanity = (int) tableListProduct.getValueAt(i, 6);
            de.setIdProductItem(idPr);
            de.setQuantity(quanity);
            listDetailsChangeProducts.add(de);
            ProductItem p = productItemDAO.selectById(idPr);
            productItemDAO.updateQuantity(p.getQuantity() - quanity, idPr);
        }
    }

    public boolean ShearchKeyFillTable(int id) {
        model = (DefaultTableModel) tableIn4Invoice.getModel();
        modelList = (DefaultTableModel) tableListProduct.getModel();
        modelList.setRowCount(0);
        list2.clear();
        model.setRowCount(0);

        listPr = reDao.selectByIdInvoiceReturn(id);
        for (ProductItem d : listPr) {
            model.addRow(new Object[]{
                d.getIdInvoiceSell(), d.getId(), d.getProductName(), d.getQuantity(), 0, d.getSize(), d.getColor(), d.getMaterial(), d.getPrice()
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

    public List<ProductItem> fillTableByPrice() {
        int row = tableIn4Invoice.getSelectedRow();
        float price = (float) tableIn4Invoice.getValueAt(row, 8);
        int id = (int) tableIn4Invoice.getValueAt(row, 1);

        return productItemDAO.selectByPrice(price, id);
    }

    public InvoiceChange getInvoiceChange() {
        InvoiceChange ir = new InvoiceChange();
        Calendar calendar = Calendar.getInstance();
        ir.setDateCreateInvoiceReturn(XDate.toString(calendar.getTime(), "hh:mm:ss aa yyyy-MM-dd"));
        ir.setDescription(txtNote.getText());
        ir.setIdInvoiceSell(Integer.valueOf(txtShearchInvoice.getText()));
        ir.setIdUser(Auth.user.getIdUser());
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
        getDetailInvoiceChange();
        getDetailChangeProduct();
        for (int i = 0; i < listDetailsInvoiceChange.size(); i++) {
            detailsInvoiceChangeDAO.insert(listDetailsInvoiceChange.get(i));
        }
        List<DetailsInvoiceChange> listDe = detailsInvoiceChangeDAO.selectAll();
//        for(int i = 0; i <)
        for (int i = 0; i < listDetailsChangeProducts.size(); i++) {
            detailsChangeProductDAO.insert(listDetailsChangeProducts.get(i));
        }
        MsgBox.alert(this, "Thêm thành công!!!");
    }

    public boolean checkChange() {
        List<InvoiceChange> list = invoiceChangeDAO.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdInvoiceSell() == Integer.parseInt(txtShearchInvoice.getText())) {
                return false;
            }
        }
        list2.clear();
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
        jLabel3 = new javax.swing.JLabel();
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
                "Mã Thanh toán", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Số lượng đổi", "Size", "Màu sắc", "Chất liệu", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản phẩm đổi"));
        jScrollPane2.setVerticalScrollBar(scrollBarCustom2);

        tableListProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Size", "Màu ", "Chất liệu", "Đơn giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollBarCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblIdOld, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
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
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIdOld, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addComponent(scrollBarCustom2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
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

    }//GEN-LAST:event_tableListProductMouseClicked

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed
        insertInvoiceChange();
        model.setRowCount(0);
        modelList.setRowCount(0);
        lblSearch.setText("");
        lblIDCustomer.setText("");
        lblIDInvoice.setText("");
        txtNote.setText("");
        txtShearchInvoice.setText("");
    }//GEN-LAST:event_btnAddEmployeeActionPerformed

    private void tableIn4InvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableIn4InvoiceMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() > 1) {
            return;
        }
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
            int quantity = Integer.valueOf(MsgBox.prompt(this, "Nhập số lượng cần hoàn trả"));
            int row = tableIn4Invoice.getSelectedRow();
            int quantityOnTable = (int) tableIn4Invoice.getValueAt(row, 3);
            float price = (float) tableIn4Invoice.getValueAt(row, 8);
            if (quantity > quantityOnTable || quantity < 0) {
                MsgBox.warring(this, "Bạn đã nhập sai số lượng");
                return;
            }
            List<ProductItem> list = fillTableByPrice();
            for (int i = 0; i < tableListProduct.getRowCount(); i++) {
                int id = (int) tableListProduct.getValueAt(i, 0);
                ProductItem pr = productItemDAO.selectById(id);
                int quantityList2 = (int) tableListProduct.getValueAt(i, 6);
                pr.setQuantity(quantityList2);
                list2.add(pr);
            }

            formChangeProductJframe = new FormChangeProductJframe(list, (float) quantity * price, list2);
            formChangeProductJframe.setVisible(true);
            formChangeProductJframe.addEvenFillTable(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    List<ProductItem> list = formChangeProductJframe.addToFormChangeProduct();
                    int iquantity = ((int) tableIn4Invoice.getValueAt(row, 3)) - quantity;
                    tableIn4Invoice.setValueAt(iquantity, row, 3);
                    tableIn4Invoice.setValueAt((int) tableIn4Invoice.getValueAt(row, 4) + quantity, row, 4);
                    fillTableListProduct(list);

                }
            });
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
    private javax.swing.JLabel lblIdOld;
    private javax.swing.JLabel lblSearch;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom2;
    private com.raven.suportSwing.TableColumn tableIn4Invoice;
    private com.raven.suportSwing.TableColumn tableListProduct;
    private javax.swing.JTextArea txtNote;
    private com.raven.suportSwing.TextField txtShearchInvoice;
    // End of variables declaration//GEN-END:variables
}
