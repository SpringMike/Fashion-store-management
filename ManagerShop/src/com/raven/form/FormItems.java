/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.ProductItemDAO;
import com.fpt.DAO.ProductsDAO;
import com.fpt.entity.Category;
import com.fpt.entity.ProductItem;
import com.fpt.entity.Products;
import com.fpt.entity.User;
import com.fpt.utils.Excel;
import com.fpt.utils.MsgBox;
import com.fpt.utils.XDate;
import com.raven.JFrame.FormImportEmpolyeeJFrame;
import com.raven.JFrame.FormImportItemJFrame;
import com.raven.JFrame.FormUpdateItemJfame;
import com.raven.component.Menu;
import com.raven.event.EventMenuSelected;
import com.raven.event.EventShowPopupMenu;
import com.raven.main.Main;
import com.raven.swing.MenuItem;
import com.raven.swing.PopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormItems extends javax.swing.JPanel {

    FormImportItemJFrame formImportItemJFrame = new FormImportItemJFrame();
    ProductItemDAO prDAO = new ProductItemDAO();
    FormUpdateItemJfame formUpdateItemJframe;
    DefaultTableModel model;
    ProductsDAO productDAO = new ProductsDAO();

    /**
     * Creates new form FormItems
     */
    public FormItems() {
        initComponents();
        setOpaque(false);
        fillComboboxProduct();
        fillTable();
        rdioSelectAll.setSelected(true);
        formImportItemJFrame.addEvenFillTable(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formImportItemJFrame.insertProductItem();
                fillTable();
            }
        });
    }

    public void fillTable() {
        model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        List<ProductItem> list = prDAO.selectAll();
        for (ProductItem p : list) {
            model.addRow(new Object[]{
                p.getId(), p.getProductName(), p.getPrice(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity()
            });
        }
    }

    public void deletePr() {
        int index = tableShow.getSelectedRow();
        int row = (int) tableShow.getValueAt(index, 0);
        MsgBox.confirm(this, "Bạn có muốn xoá không ?");
        prDAO.delete(row);
        fillTable();
        MsgBox.alert(this, "Xoá Thành công");
    }

    public void fillComboboxProduct() {
        DefaultComboBoxModel cbModel = (DefaultComboBoxModel) cbcProduct.getModel();
        cbcProduct.removeAllItems();
        List<Products> list = productDAO.selectAll();
        for (Products p : list) {
            cbModel.addElement(p);
        }
    }

    public void searchTable() {
        // tìm kiếm theo mã sản phẩm
        model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        int keyWord = Integer.parseInt(txtSearch.getText());
        if (txtSearch.getText().trim().equals("")) {
            return;
        }
        ProductItem p = prDAO.selectById(keyWord);
        if (p == null) {
            lblSearch.setText("Không có mặt hàng " + keyWord);
            return;
        }
        model.addRow(new Object[]{
            p.getId(), p.getProductName(), p.getPrice(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity()
        });
        lblSearch.setText("");
    }

    public void searchKeyFillTable() {
        // tìm kiếm theo tên sản phẩm
        String key = txtSearch.getText();
        model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        List<ProductItem> list = prDAO.selectByKey(key);
        if (list.isEmpty()) {
            lblSearch.setText("Không có mặt hàng " + key);
            return;
        }
        for (ProductItem p : list) {
            model.addRow(new Object[]{
                p.getId(), p.getProductName(), p.getPrice(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity()
            });
        }
        model.fireTableDataChanged();
    }

    public void fillTableByPropertieProductItem(String keyword) {
        model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        if (txtQuantity.getText().trim().equals("")) {
            return;
        }
        int quantity = Integer.valueOf(txtQuantity.getText());
        List<ProductItem> list = prDAO.selectByPropertieProductItem(quantity, keyword);
        for (ProductItem p : list) {
            model.addRow(new Object[]{
                p.getId(), p.getProductName(), p.getPrice(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity()
            });
        }
    }

    public void fillTableByProduct() {
        model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        Products pro = (Products) cbcProduct.getSelectedItem();
        List<ProductItem> list = prDAO.selectByPropertieProductItem(pro.getIdProduct(), "ByProduct");
        for (ProductItem p : list) {
            model.addRow(new Object[]{
                p.getId(), p.getProductName(), p.getPrice(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity()
            });
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        rdioSelectAll = new com.raven.suportSwing.RadioButtonCustom();
        jLabel1 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        rdioAbove = new com.raven.suportSwing.RadioButtonCustom();
        rdioBelow = new com.raven.suportSwing.RadioButtonCustom();
        rdioRemain = new com.raven.suportSwing.RadioButtonCustom();
        rdioOutOfProductItem = new com.raven.suportSwing.RadioButtonCustom();
        jPanel6 = new javax.swing.JPanel();
        rdioStatusFalse = new com.raven.suportSwing.RadioButtonCustom();
        rdioStatusTrue = new com.raven.suportSwing.RadioButtonCustom();
        jPanel7 = new javax.swing.JPanel();
        rdioPriceAsc = new com.raven.suportSwing.RadioButtonCustom();
        rdioPriceDesc = new com.raven.suportSwing.RadioButtonCustom();
        cbcProduct = new com.raven.suportSwing.Combobox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new com.raven.suportSwing.TextField();
        myButton2 = new com.raven.suportSwing.MyButton();
        myButton3 = new com.raven.suportSwing.MyButton();
        myButton4 = new com.raven.suportSwing.MyButton();
        btnDelete = new com.raven.suportSwing.MyButton();
        lblSearch = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShow = new com.raven.suportSwing.TableColumn();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tồn Kho"));

        buttonGroup1.add(rdioSelectAll);
        rdioSelectAll.setText("Tất cả");
        rdioSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioSelectAllActionPerformed(evt);
            }
        });

        jLabel1.setText("Định mức tồn");

        txtQuantity.setText("10");
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQuantityKeyReleased(evt);
            }
        });

        buttonGroup1.add(rdioAbove);
        rdioAbove.setText("Vượt định mức tồn");
        rdioAbove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioAboveActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdioBelow);
        rdioBelow.setText("Dưới định mức tồn");
        rdioBelow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioBelowActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdioRemain);
        rdioRemain.setText("Còn hàng trong kho");
        rdioRemain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioRemainActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdioOutOfProductItem);
        rdioOutOfProductItem.setText("Hết hàng trong kho");
        rdioOutOfProductItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioOutOfProductItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdioSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rdioAbove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdioRemain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdioOutOfProductItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdioBelow, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 41, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdioSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdioBelow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdioAbove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdioRemain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdioOutOfProductItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Trạng thái"));

        buttonGroup1.add(rdioStatusFalse);
        rdioStatusFalse.setText("Ngừng kinh doanh");
        rdioStatusFalse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioStatusFalseActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdioStatusTrue);
        rdioStatusTrue.setText("Đang kinh doanh");
        rdioStatusTrue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioStatusTrueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdioStatusFalse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdioStatusTrue, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdioStatusTrue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdioStatusFalse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Giá bán"));

        buttonGroup1.add(rdioPriceAsc);
        rdioPriceAsc.setText("Từ thấp đến cao");
        rdioPriceAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioPriceAscActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdioPriceDesc);
        rdioPriceDesc.setText("Từ cao đến thấp");
        rdioPriceDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdioPriceDescActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdioPriceAsc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdioPriceDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdioPriceDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdioPriceAsc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        cbcProduct.setLabeText("Sản phẩm");
        cbcProduct.setName(""); // NOI18N
        cbcProduct.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbcProductItemStateChanged(evt);
            }
        });
        cbcProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbcProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbcProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cbcProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Mặt Hàng");

        txtSearch.setLabelText("Tìm theo tên or mã");
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
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

        myButton3.setText("Thêm Mới");
        myButton3.setRadius(20);
        myButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton3ActionPerformed(evt);
            }
        });

        myButton4.setText("Xuất");
        myButton4.setRadius(20);
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        btnDelete.setText("Xoá");
        btnDelete.setRadius(20);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)))
                .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185)
                .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 26, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tableShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Giá Bán", "Size", "Màu Sắc", "Chất Liệu", "Số lượng tồn kho"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        // TODO add your handling code here:
        try {
            searchTable();
        } catch (Exception e) {
            searchKeyFillTable();
        }
    }//GEN-LAST:event_myButton2ActionPerformed


    private void myButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton3ActionPerformed
        // TODO add your handling code here:
//        this.setVisible(false);
//        new MainForm().setVisible(true);

        formImportItemJFrame.setVisible(true);


    }//GEN-LAST:event_myButton3ActionPerformed
    public void excelItems() throws IOException {
        Excel.outputFile((DefaultTableModel) tableShow.getModel());
        MsgBox.alert(this, "Xuất file thành công");
    }
    private void myButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton4ActionPerformed
        // TODO add your handling code here:
        try {
            excelItems();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_myButton4ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deletePr();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tableShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableShowMouseClicked
        if (evt.getClickCount() == 2) {
            int index = tableShow.getSelectedRow();
            int idProductItem = (int) tableShow.getValueAt(index, 0);
            String nameProduct = tableShow.getValueAt(index, 1).toString();
            Float price = (float) tableShow.getValueAt(index, 2);
            String size = tableShow.getValueAt(index, 3).toString();
            String color = tableShow.getValueAt(index, 4).toString();
            String material = tableShow.getValueAt(index, 5).toString();

            formUpdateItemJframe = new FormUpdateItemJfame(nameProduct, size, color, material, price, idProductItem);
            formUpdateItemJframe.addEvenUpdate(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    formUpdateItemJframe.update();
                    fillTable();
                }
            });
            formUpdateItemJframe.setVisible(true);
        }
    }//GEN-LAST:event_tableShowMouseClicked

    private void rdioSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioSelectAllActionPerformed
        fillTable();
    }//GEN-LAST:event_rdioSelectAllActionPerformed

    private void rdioAboveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioAboveActionPerformed
        fillTableByPropertieProductItem("Above");
    }//GEN-LAST:event_rdioAboveActionPerformed

    private void rdioBelowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioBelowActionPerformed
        fillTableByPropertieProductItem("Below");
    }//GEN-LAST:event_rdioBelowActionPerformed

    private void rdioRemainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioRemainActionPerformed
        fillTableByPropertieProductItem("StillRemailProductItem");
    }//GEN-LAST:event_rdioRemainActionPerformed

    private void rdioOutOfProductItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioOutOfProductItemActionPerformed
        fillTableByPropertieProductItem("OutOfStock");
    }//GEN-LAST:event_rdioOutOfProductItemActionPerformed

    private void rdioStatusFalseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioStatusFalseActionPerformed
        fillTableByPropertieProductItem("StatusFalse");
    }//GEN-LAST:event_rdioStatusFalseActionPerformed

    private void rdioStatusTrueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioStatusTrueActionPerformed
        fillTableByPropertieProductItem("StatusTrue");
    }//GEN-LAST:event_rdioStatusTrueActionPerformed

    private void rdioPriceAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioPriceAscActionPerformed
        fillTableByPropertieProductItem("OderByPriceAsc");
    }//GEN-LAST:event_rdioPriceAscActionPerformed

    private void rdioPriceDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdioPriceDescActionPerformed
        fillTableByPropertieProductItem("OderByPriceDesc");
    }//GEN-LAST:event_rdioPriceDescActionPerformed

    private void txtQuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyReleased

        if (rdioAbove.isSelected()) {
            fillTableByPropertieProductItem("Above");
        } else if (rdioBelow.isSelected()) {
            fillTableByPropertieProductItem("Below");
        }
    }//GEN-LAST:event_txtQuantityKeyReleased

    private void cbcProductItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbcProductItemStateChanged
        fillTableByProduct();
        rdioSelectAll.setSelected(true);
    }//GEN-LAST:event_cbcProductItemStateChanged

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        lblSearch.setText("");
    }//GEN-LAST:event_txtSearchFocusGained

    private void cbcProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbcProductActionPerformed
        fillTableByProduct();
        rdioSelectAll.setSelected(true);
    }//GEN-LAST:event_cbcProductActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnDelete;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.raven.suportSwing.Combobox cbcProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSearch;
    private com.raven.suportSwing.MyButton myButton2;
    private com.raven.suportSwing.MyButton myButton3;
    private com.raven.suportSwing.MyButton myButton4;
    private com.raven.suportSwing.RadioButtonCustom rdioAbove;
    private com.raven.suportSwing.RadioButtonCustom rdioBelow;
    private com.raven.suportSwing.RadioButtonCustom rdioOutOfProductItem;
    private com.raven.suportSwing.RadioButtonCustom rdioPriceAsc;
    private com.raven.suportSwing.RadioButtonCustom rdioPriceDesc;
    private com.raven.suportSwing.RadioButtonCustom rdioRemain;
    private com.raven.suportSwing.RadioButtonCustom rdioSelectAll;
    private com.raven.suportSwing.RadioButtonCustom rdioStatusFalse;
    private com.raven.suportSwing.RadioButtonCustom rdioStatusTrue;
    private com.raven.suportSwing.TableColumn tableShow;
    private javax.swing.JTextField txtQuantity;
    private com.raven.suportSwing.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
