/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.DetailInvoiceImportDAO;
import com.fpt.DAO.InvoiceImportDAO;
import com.fpt.DAO.ProductItemDAO;
import com.fpt.DAO.SupplierDao;
import com.fpt.Validate.Validate;
import com.fpt.entity.DetailInvoiceImport;
import com.fpt.entity.InvoiceImport;
import com.fpt.entity.ProductItem;
import com.fpt.entity.Supplier;
import static com.fpt.utils.Auth.user;
import com.fpt.utils.MsgBox;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormImportProducts extends javax.swing.JPanel {

    /**
     * Creates new form FormProducts
     */
    public FormImportProducts() {
        initComponents();
        fillComboboxSupplier();
        fillTableProductItem();
        lblPrice.setVisible(false);
        lblQuantity.setVisible(false);
        btnAddTemp.setEnabled(false);
        btnDeleteTemp.setEnabled(false);

    }
    SupplierDao supDAO = new SupplierDao();
    ProductItemDAO prDAO = new ProductItemDAO();
    DetailInvoiceImportDAO detailInvoiceDAO = new DetailInvoiceImportDAO();
    InvoiceImportDAO invoiceDAO = new InvoiceImportDAO();

    public void fillComboboxSupplier() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbSupplier.getModel();
        cbbSupplier.removeAllItems();
        List<Supplier> list = supDAO.selectAll();
        for (Supplier s : list) {
            model.addElement(s);
        }
    }

    public void fillTableProductItem() {
        DefaultTableModel model = (DefaultTableModel) tableProductItem.getModel();
        model.setRowCount(0);
        List<ProductItem> list = prDAO.selectAll();
        for (ProductItem p : list) {
            model.addRow(new Object[]{
                p.getId(), p.getProductName(), p.getCategoryName(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity()
            });
        }
    }

    public InvoiceImport getIvoice() {
        InvoiceImport in = new InvoiceImport();
        Calendar calendar = Calendar.getInstance();
        in.setDateCreate(calendar.getTime());
        in.setStatusPay(false);
        in.setIdUser(user.getIdUser());
        in.setDesc(txtAreaDesc.getText());
        Supplier s = (Supplier) cbbSupplier.getSelectedItem();
        in.setIdSupplier(s.getIdSupplier());
        return in;
    }

    public void insertInvoice() {
        int count = tableTemp.getRowCount();
        if (count <= 0) {
            MsgBox.alert(this, "Bạn chưa lưu sản phẩm nào");
        } else {
            InvoiceImport invoice = getIvoice();
            invoiceDAO.insert(invoice);
            // lặp list để insert từng hóa đơn chi tiết vào db
            for (int i = 0; i < list.size(); i++) {
                DetailInvoiceImport de = list.get(i);
                // hàm insert hóa đơn chi tiết
                detailInvoiceDAO.insert(de);
                // hàm cập nhập số lượng tồn kho trong bảng sản phẩm chi tiết
                prDAO.importProductItem(de.getQuantity(), de.getIdProductItem());
            }
            MsgBox.alert(this, "Thêm " + list.size() + " mặt hàng vào hóa đơn thành công thành công");
            DefaultTableModel model = (DefaultTableModel) tableTemp.getModel();
            model.setRowCount(0);
            list.clear();
            fillTableProductItem();
        }

    }

    List<DetailInvoiceImport> list = new ArrayList<>();
    //list lưu những hóa đơn chi tiết

    public void deleteRowInTableTemp() {
        int row = tableTemp.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tableTemp.getModel();
        int id = (int) tableTemp.getValueAt(row, 0);
        model.removeRow(row);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdProductItem() == id) {
                list.remove(list.get(i));
                MsgBox.alert(this, "Xóa mặt hàng thành công !");
                btnDeleteTemp.setEnabled(false);
                return;
            }
        }
    }

    //hàm đổ dữ liệu từ bảng mặt hàng xuống bảng tạm
    public void fillTabelTemp() {
        if (!Validate.checkNumber(lblPrice, txtPrice, "Giá tiền phải hợp lệ")) {
            lblPrice.setVisible(true);
            return;
        } else if (!Validate.checkNumber(lblQuantity, txtQuantity, "Số lượng nhập phải hợp lệ")) {
            lblQuantity.setVisible(true);
            return;
        } else {
            int row = tableProductItem.getSelectedRow();
            if (row == -1) {
                //check xem ng dùng có chọn row nào trên bảng không
                MsgBox.alert(this, "Bạn chưa chọn mặt hàng nào");
            } else {
                //lấy dữ liệu từ bảng mặt hàng
                int id = (int) tableProductItem.getValueAt(row, 0);
                String name = (String) tableProductItem.getValueAt(row, 1);
                String categoryName = (String) tableProductItem.getValueAt(row, 2);
                String size = (String) tableProductItem.getValueAt(row, 3);
                String color = (String) tableProductItem.getValueAt(row, 4);
                String material = (String) tableProductItem.getValueAt(row, 5);
                int quantity = Integer.parseInt(txtQuantity.getText());
                Float price = Float.parseFloat(txtPrice.getText());
                // đổ dữ liệu vào bảng tạm
                DefaultTableModel model = (DefaultTableModel) tableTemp.getModel();
                Object[] obj = new Object[]{id, name, categoryName, size, color, material, quantity};
                model.addRow(obj);
                DetailInvoiceImport de = new DetailInvoiceImport();
                de.setPrice(price);
                de.setIdProductItem(id);
                de.setQuantity(quantity);
                //add vào list
                list.add(de);
                //clear form
                tableProductItem.clearSelection();
                txtPrice.setText("");
                txtQuantity.setText("");
            }

        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProductItem = new com.raven.suportSwing.TableColumn();
        scrollBar1 = new com.raven.suportSwing.ScrollBar();
        jPanel2 = new javax.swing.JPanel();
        cbbSupplier = new com.raven.suportSwing.Combobox();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaDesc = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new com.raven.suportSwing.MyButton();
        txtQuantity = new com.raven.suportSwing.TextField();
        txtPrice = new com.raven.suportSwing.TextField();
        btnAddTemp = new com.raven.suportSwing.MyButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableTemp = new com.raven.suportSwing.TableColumn();
        scrollBar2 = new com.raven.suportSwing.ScrollBar();
        lblQuantity = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        btnDeleteTemp = new com.raven.suportSwing.MyButton();
        jPanel3 = new javax.swing.JPanel();
        myButton1 = new com.raven.suportSwing.MyButton();
        textField1 = new com.raven.suportSwing.TextField();
        jLabel2 = new javax.swing.JLabel();
        myButton4 = new com.raven.suportSwing.MyButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jScrollPane1.setVerticalScrollBar(scrollBar1);

        tableProductItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Mặt hàng", "Tên Sản Phẩm", "Loại", "Size", "Màu Sắc", "Chất liệu", "Số lượng trong kho"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableProductItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProductItemMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableProductItem);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(scrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        cbbSupplier.setLabeText("Nhà cung cấp");
        cbbSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSupplierActionPerformed(evt);
            }
        });

        txtAreaDesc.setColumns(20);
        txtAreaDesc.setRows(5);
        jScrollPane4.setViewportView(txtAreaDesc);

        jLabel1.setText("Ghi Chú");

        btnAdd.setText("Hoàn thành");
        btnAdd.setRadius(20);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtQuantity.setLabelText("Số lượng nhập");
        txtQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQuantityFocusGained(evt);
            }
        });

        txtPrice.setLabelText("Giá Nhập");
        txtPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPriceFocusGained(evt);
            }
        });

        btnAddTemp.setText("Lưu tạm");
        btnAddTemp.setRadius(10);
        btnAddTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTempActionPerformed(evt);
            }
        });

        jScrollPane5.setVerticalScrollBar(scrollBar2);

        tableTemp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã mặt hàng", "Tên SP", "Loại", "Size", "Màu Sắc", "Chất liệu", "Số lượng nhập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTemp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTempMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableTemp);

        lblQuantity.setForeground(new java.awt.Color(225, 0, 0));
        lblQuantity.setText("jLabel3");

        lblPrice.setForeground(new java.awt.Color(225, 0, 0));
        lblPrice.setText("jLabel4");

        btnDeleteTemp.setText("Xóa tạm");
        btnDeleteTemp.setRadius(10);
        btnDeleteTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTempActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddTemp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteTemp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(scrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblQuantity)
                            .addComponent(lblPrice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        myButton1.setText("Tìm kiếm");
        myButton1.setRadius(10);

        textField1.setLabelText("Tìm theo tên hoặc mã sản phẩm");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Nhập Hàng");

        myButton4.setText("Import");
        myButton4.setRadius(10);
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(892, Short.MAX_VALUE)
                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2)
                    .addGap(33, 33, 33)
                    .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(27, 27, 27)
                    .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(481, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(myButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(360, 360, 360)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSupplierActionPerformed

    private void btnAddTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTempActionPerformed
        fillTabelTemp();
    }//GEN-LAST:event_btnAddTempActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        insertInvoice();
        btnDeleteTemp.setEnabled(false);
    }//GEN-LAST:event_btnAddActionPerformed

    private void tableProductItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProductItemMouseClicked
        btnAddTemp.setEnabled(true);
    }//GEN-LAST:event_tableProductItemMouseClicked

    private void txtQuantityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantityFocusGained
        lblQuantity.setVisible(false);
    }//GEN-LAST:event_txtQuantityFocusGained

    private void txtPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusGained
        lblPrice.setVisible(false);
    }//GEN-LAST:event_txtPriceFocusGained

    private void myButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_myButton4ActionPerformed

    private void btnDeleteTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTempActionPerformed
        deleteRowInTableTemp();
    }//GEN-LAST:event_btnDeleteTempActionPerformed

    private void tableTempMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTempMouseClicked
        btnDeleteTemp.setEnabled(true);
    }//GEN-LAST:event_tableTempMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnAdd;
    private com.raven.suportSwing.MyButton btnAddTemp;
    private com.raven.suportSwing.MyButton btnDeleteTemp;
    private com.raven.suportSwing.Combobox cbbSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblQuantity;
    private com.raven.suportSwing.MyButton myButton1;
    private com.raven.suportSwing.MyButton myButton4;
    private com.raven.suportSwing.ScrollBar scrollBar1;
    private com.raven.suportSwing.ScrollBar scrollBar2;
    private com.raven.suportSwing.TableColumn tableProductItem;
    private com.raven.suportSwing.TableColumn tableTemp;
    private com.raven.suportSwing.TextField textField1;
    private javax.swing.JTextArea txtAreaDesc;
    private com.raven.suportSwing.TextField txtPrice;
    private com.raven.suportSwing.TextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
