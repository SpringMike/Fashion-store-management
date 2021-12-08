/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.CategoryDAO;
import com.fpt.DAO.ProductsDAO;
import com.fpt.Validate.Validate;
import com.fpt.Validate.labelValidate;
import com.fpt.entity.Category;
import com.fpt.entity.ProductItem;
import com.fpt.entity.Products;
import com.fpt.utils.Excel;
import com.fpt.utils.MsgBox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormProducts extends javax.swing.JPanel {

    /**
     * Creates new form FormProducts
     */
    public FormProducts() {
        initComponents();
        setOpaque(false);
        fillComboboxCategory();
        statusForm();
        fillTableProducts();
    }

    public void statusForm() {
        radiNowSell.setSelected(true);
        txtImportList.setVisible(false);
        btnDelete.setVisible(false);
        btnAddList.setVisible(false);
        btnUpdateList.setVisible(false);
        lblCategory.setVisible(false);
        btnDeleteProduct.setEnabled(false);
        btnUpdateProducts.setEnabled(false);
    }
    CategoryDAO cDAO = new CategoryDAO();
    ProductsDAO pDao = new ProductsDAO();

    public void fillComboboxCategory() {
        DefaultComboBoxModel cbModel = (DefaultComboBoxModel) cbbCategory.getModel();
        cbbCategory.removeAllItems();
        List<Category> list = cDAO.selectAll();
        for (Category c : list) {
            cbModel.addElement(c);
        }
    }

    // hien len ten cua category len txt khi chon o combobox
    public void showCategory() {
        Category c = (Category) cbbCategory.getSelectedItem();
        if (c == null) {
            return;
        } else {
            txtImportList.setText(c.getName());
        }
    }

    public void updateCategory() {
        Category c = (Category) cbbCategory.getSelectedItem();
        c.setName(txtImportList.getText());
        try {
            if (!Validate.checkEmpty(lblCategory, txtImportList, "Không được để trống tên danh mục!")) {
                return;
            }
            cDAO.update(c);
            MsgBox.alert(this, "Sửa đổi thành công");
            fillComboboxCategory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Category getForm() {
        Category c = new Category();
        c.setName(txtImportList.getText());
        c.setStatus(true);
        return c;
    }

    public void insertCategory() {
        Category cInsert = getForm();
        List<Category> list = cDAO.selectAll();
        try {
            if (!Validate.checkEmpty(lblCategory, txtImportList, "Không được để trống tên danh mục!")) {
                lblCategory.setVisible(true);
                return;
            }
            list = cDAO.selectAll();
            for (Category c : list) {
                if (txtImportList.getText().equalsIgnoreCase(c.getName())) {
                    lblCategory.setVisible(true);
                    lblCategory.setText("Tên danh mục đã có mời nhập lại!");
                    txtImportList.setText("");
                    return;
                }
            }

            cDAO.insert(cInsert);
            lblCategory.setVisible(false);
            txtImportList.setVisible(false);
            btnDelete.setVisible(false);
            btnAddList.setVisible(false);
            btnUpdateList.setVisible(false);
            MsgBox.alert(this, "Thêm thành công");
            fillComboboxCategory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory() {
        Category c = (Category) cbbCategory.getSelectedItem();
        int index = c.getId();
        int check = cDAO.deleteList(index);
        if (check == 0) {
            lblCategory.setVisible(true);
            lblCategory.setText("Danh mục có sản phẩm");
        } else {
            MsgBox.alert(this, "Xoá OK");
            fillComboboxCategory();
        }
    }

    public void fillTableProducts() {
        DefaultTableModel model = (DefaultTableModel) tableShowProducts.getModel();
        model.setRowCount(0);
        List<Products> list = pDao.selectAll();
        for (Products p : list) {
            model.addRow(new Object[]{
                p.getIdProduct(), p.getNameProduct(), p.getNameList(), p.getDescription(), p.isStatus() ? "Đang kinh doanh" : "Ngừng kinh doanh"
            });
        }
    }

    Products getFormProducts() {
        Products p = new Products();
        p.setDescription(txtDes.getText());
        List<Category> list = cDAO.selectAll();
        int id = 0;
        for (Category c : list) {
            String textCb = cbbCategory.getSelectedItem() + "";
            if (textCb.equals(c.getName())) {
                id = c.getId();
            }
        }
        p.setIdList(id);
        p.setNameProduct(txtNameProducts.getText());
        p.setStatus(radiNowSell.isSelected());
        return p;
    }

    public void setFormProducts(Products p) {
        txtDes.setText(p.getDescription());
        txtNameProducts.setText(p.getNameProduct());
        radiNowSell.setSelected(p.isStatus());
        radiOffSell.setSelected(!p.isStatus());
        cbbCategory.setSelectedItem(p.getNameList());
//        int index = cbbCategory.getSelectedIndex();
//        Category c = cDAO.selectAll().get(index);
//        String show = c.getName();
    }

    public void insertProducts() {
        Products p = getFormProducts();
        if (!Validate.checkEmpty(lblNameProduct, txtNameProducts, "Không bỏ trống tên sản phẩm")) {
            return;
        } else {
            pDao.insert(p);
            clearForm();
            fillTableProducts();
            MsgBox.alert(this, "Thêm sản phẩm thành công");
        }

    }

    public void editProducts() {
        int row = tableShowProducts.getSelectedRow();
        int code = (int) tableShowProducts.getValueAt(row, 0);
        Products p = pDao.selectById(code);
        setFormProducts(p);
        btnDeleteProduct.setEnabled(true);
        btnUpdateProducts.setEnabled(true);
        btnAddProducts.setEnabled(false);

    }

    public void updateProducts() {
        Products p = getFormProducts();
        int row = tableShowProducts.getSelectedRow();
        p.setIdProduct((int) tableShowProducts.getValueAt(row, 0));
        if (!Validate.checkEmpty(lblNameProduct, txtNameProducts, "Không bỏ trống tên sản phẩm")) {
            return;
        } else {
            pDao.update(p);
            clearForm();
            fillTableProducts();
            MsgBox.alert(this, "Cập nhập sản phẩm thành công");
        }
    }

    public void deleteProducts() {
        int row = tableShowProducts.getSelectedRow();
        int code = (int) tableShowProducts.getValueAt(row, 0);
        if (MsgBox.confirm(this, "Bạn có muốn xóa không?")) {
            try {
                pDao.delete(code);
                fillTableProducts();
                clearForm();
                MsgBox.alert(this, "Xoa thanh cong");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void searchProductsID() {
        DefaultTableModel model = (DefaultTableModel) tableShowProducts.getModel();
        model.setRowCount(0);
        int id = Integer.valueOf(txtSearch.getText());
        Products p = pDao.selectById(id);
        if (p == null) {
            MsgBox.labelAlert(lblSearch, txtSearch, "Không có sản phẩm " + id + "  ");
            return;
        }
        model.addRow(new Object[]{
            p.getIdProduct(), p.getNameProduct(), p.getNameList(), p.getDescription(), p.isStatus() ? "Đang kinh doanh" : "Ngừng kinh doanh"
        });
        lblSearch.setText("");
    }

    public void searchProducts() {
        DefaultTableModel model = (DefaultTableModel) tableShowProducts.getModel();
        model.setRowCount(0);
        List<Products> list = pDao.selectByKeyWord(txtSearch.getText());
        if (list.isEmpty()) {
            MsgBox.labelAlert(lblSearch, txtSearch, "Không có sản phẩm " + txtSearch.getText() + "  ");
            return;
        }
        for (Products p : list) {
            model.addRow(new Object[]{
                p.getIdProduct(), p.getNameProduct(), p.getNameList(), p.getDescription(), p.isStatus() ? "Đang kinh doanh" : "Ngừng kinh doanh"
            });
        }
        lblSearch.setText("");
    }

    public void clearForm() {
        txtDes.setText("");
        txtNameProducts.setText("");
        lblNameProduct.setText("");
        btnAddProducts.setEnabled(true);
        btnUpdateProducts.setEnabled(false);
        btnDeleteProduct.setEnabled(false);
//        lblNameProduct.setVisible(false);
        cbbCategory.setSelectedIndex(0);
    }

    public void excelProducts() throws IOException {
        Excel.outExcel((DefaultTableModel) tableShowProducts.getModel());
        MsgBox.alert(this, "Xuất file thành công");
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
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new com.raven.suportSwing.TextField();
        myButton2 = new com.raven.suportSwing.MyButton();
        lblSearch = new javax.swing.JLabel();
        myButton4 = new com.raven.suportSwing.MyButton();
        myButton5 = new com.raven.suportSwing.MyButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShowProducts = new com.raven.suportSwing.TableColumn();
        scrollBarCustom1 = new com.raven.suportSwing.ScrollBarCustom();
        jPanel3 = new javax.swing.JPanel();
        txtNameProducts = new com.raven.suportSwing.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDes = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        radiOffSell = new com.raven.suportSwing.RadioButtonCustom();
        jLabel3 = new javax.swing.JLabel();
        radiNowSell = new com.raven.suportSwing.RadioButtonCustom();
        myButton3 = new com.raven.suportSwing.MyButton();
        btnAddProducts = new com.raven.suportSwing.MyButton();
        btnUpdateProducts = new com.raven.suportSwing.MyButton();
        cbbCategory = new com.raven.suportSwing.Combobox();
        myButton6 = new com.raven.suportSwing.MyButton();
        txtImportList = new com.raven.suportSwing.TextField();
        btnDelete = new com.raven.suportSwing.MyButton();
        btnAddList = new com.raven.suportSwing.MyButton();
        btnUpdateList = new com.raven.suportSwing.MyButton();
        lblCategory = new javax.swing.JLabel();
        btnDeleteProduct = new com.raven.suportSwing.MyButton();
        lblNameProduct = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Sản Phẩm");

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
        lblSearch.setForeground(new java.awt.Color(255, 0, 0));

        myButton4.setText("Xuất");
        myButton4.setRadius(20);
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        myButton5.setText("Import");
        myButton5.setRadius(20);
        myButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 847, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(320, 320, 320))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setVerticalScrollBar(scrollBarCustom1);

        tableShowProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sản phẩm", "Tên Sản Phẩm", "Loại Sản Phẩm", "Mô Tả", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableShowProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableShowProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableShowProducts);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollBarCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(scrollBarCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Chi tiết sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        txtNameProducts.setLabelText("Tên Sản Phẩm");
        txtNameProducts.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameProductsFocusGained(evt);
            }
        });
        txtNameProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameProductsActionPerformed(evt);
            }
        });

        txtDes.setColumns(20);
        txtDes.setRows(5);
        jScrollPane2.setViewportView(txtDes);

        jLabel1.setText("Mô Tả");

        buttonGroup1.add(radiOffSell);
        radiOffSell.setText("Ngừng kinh doanh");
        radiOffSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiOffSellActionPerformed(evt);
            }
        });

        jLabel3.setText("Trạng Thái");

        buttonGroup1.add(radiNowSell);
        radiNowSell.setText("Đang kinh doanh");
        radiNowSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiNowSellActionPerformed(evt);
            }
        });

        myButton3.setText("Tạo Mới");
        myButton3.setRadius(20);
        myButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton3ActionPerformed(evt);
            }
        });

        btnAddProducts.setText("Thêm");
        btnAddProducts.setRadius(20);
        btnAddProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductsActionPerformed(evt);
            }
        });

        btnUpdateProducts.setText("Cập Nhật");
        btnUpdateProducts.setRadius(20);
        btnUpdateProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateProductsActionPerformed(evt);
            }
        });

        cbbCategory.setLabeText("Loại Sản Phẩm");
        cbbCategory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbCategoryItemStateChanged(evt);
            }
        });
        cbbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCategoryActionPerformed(evt);
            }
        });

        myButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/Create.png"))); // NOI18N
        myButton6.setRadius(20);
        myButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton6ActionPerformed(evt);
            }
        });

        txtImportList.setLabelText("Tên Danh Mục");
        txtImportList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtImportListFocusGained(evt);
            }
        });
        txtImportList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImportListActionPerformed(evt);
            }
        });

        btnDelete.setText("Xoá");
        btnDelete.setRadius(20);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAddList.setText("Thêm");
        btnAddList.setRadius(20);
        btnAddList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddListActionPerformed(evt);
            }
        });

        btnUpdateList.setText("Sửa");
        btnUpdateList.setRadius(20);
        btnUpdateList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateListActionPerformed(evt);
            }
        });

        lblCategory.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblCategory.setForeground(new java.awt.Color(255, 0, 0));
        lblCategory.setText("jLabel4");

        btnDeleteProduct.setText("Xoá");
        btnDeleteProduct.setRadius(20);
        btnDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductActionPerformed(evt);
            }
        });

        lblNameProduct.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNameProduct.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNameProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtImportList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(radiNowSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(radiOffSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblCategory, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(btnAddList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnUpdateList, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblNameProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtImportList, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblCategory)
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNameProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radiNowSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radiOffSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateProducts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void radiOffSellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiOffSellActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radiOffSellActionPerformed

    private void radiNowSellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiNowSellActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radiNowSellActionPerformed

    private void txtNameProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameProductsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameProductsActionPerformed

    private void myButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton6ActionPerformed
        // TODO add your handling code here:
        if (txtImportList.isVisible()) {
            txtImportList.setVisible(false);
            btnDelete.setVisible(false);
            btnAddList.setVisible(false);
            btnUpdateList.setVisible(false);
            lblCategory.setVisible(false);
        } else {
            txtImportList.setVisible(true);
            btnDelete.setVisible(true);
            btnAddList.setVisible(true);
            btnUpdateList.setVisible(true);
            showCategory();
        }

    }//GEN-LAST:event_myButton6ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
//        txtImportList.setVisible(false);
//        btnDelete.setVisible(false);
        deleteCategory();
        fillTableProducts();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddListActionPerformed
        insertCategory();
        fillComboboxCategory();
    }//GEN-LAST:event_btnAddListActionPerformed

    private void btnUpdateListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateListActionPerformed
        updateCategory();
        fillComboboxCategory();
    }//GEN-LAST:event_btnUpdateListActionPerformed

    private void cbbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbCategoryActionPerformed
//        showCategoryText();
    }//GEN-LAST:event_cbbCategoryActionPerformed

    private void txtImportListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImportListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImportListActionPerformed

    private void txtImportListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtImportListFocusGained
        lblCategory.setText("");
    }//GEN-LAST:event_txtImportListFocusGained

    private void cbbCategoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbCategoryItemStateChanged
        // TODO add your handling code here:
        showCategory();
    }//GEN-LAST:event_cbbCategoryItemStateChanged

    private void btnAddProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductsActionPerformed
        // TODO add your handling code here:
        insertProducts();
    }//GEN-LAST:event_btnAddProductsActionPerformed

    private void tableShowProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableShowProductsMouseClicked
        editProducts();
        // TODO add your handling code here:
    }//GEN-LAST:event_tableShowProductsMouseClicked

    private void btnUpdateProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateProductsActionPerformed
        // TODO add your handling code here:
        updateProducts();
    }//GEN-LAST:event_btnUpdateProductsActionPerformed

    private void btnDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductActionPerformed
        // TODO add your handling code here:
        deleteProducts();
    }//GEN-LAST:event_btnDeleteProductActionPerformed

    private void myButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton3ActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_myButton3ActionPerformed

    private void txtNameProductsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameProductsFocusGained
        // TODO add your handling code here:
        lblNameProduct.setText("");
    }//GEN-LAST:event_txtNameProductsFocusGained

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        // TODO add your handling code here:
        searchProducts();
    }//GEN-LAST:event_myButton2ActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyPressed

    private void myButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton4ActionPerformed
        try {
            excelProducts();
        } catch (IOException ex) {
            Logger.getLogger(FormProducts.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_myButton4ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        try {
            searchProductsID();
        } catch (Exception e) {
            searchProducts();
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    public static final int COLUMN_INDEX_ID = 0;
    public static final int COLUMN_INDEX_NAME = 1;
    public static final int COLUMN_INDEX_LIST = 2;
    public static final int COLUMN_INDEX_DESC = 3;
    public static final int COLUMN_INDEX_STATUS = 4;
    private void myButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton5ActionPerformed
        // TODO add your handling code here:
//        final String excelFilePath = "D:/Book3.xlsx";
//        final List<Products> books = readExcel(excelFilePath);
//        for (Book book : books) {
//            System.out.println(book);
//        }

    }//GEN-LAST:event_myButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnAddList;
    private com.raven.suportSwing.MyButton btnAddProducts;
    private com.raven.suportSwing.MyButton btnDelete;
    private com.raven.suportSwing.MyButton btnDeleteProduct;
    private com.raven.suportSwing.MyButton btnUpdateList;
    private com.raven.suportSwing.MyButton btnUpdateProducts;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.raven.suportSwing.Combobox cbbCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblNameProduct;
    private javax.swing.JLabel lblSearch;
    private com.raven.suportSwing.MyButton myButton2;
    private com.raven.suportSwing.MyButton myButton3;
    private com.raven.suportSwing.MyButton myButton4;
    private com.raven.suportSwing.MyButton myButton5;
    private com.raven.suportSwing.MyButton myButton6;
    private com.raven.suportSwing.RadioButtonCustom radiNowSell;
    private com.raven.suportSwing.RadioButtonCustom radiOffSell;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom1;
    private com.raven.suportSwing.TableColumn tableShowProducts;
    private javax.swing.JTextArea txtDes;
    private com.raven.suportSwing.TextField txtImportList;
    private com.raven.suportSwing.TextField txtNameProducts;
    private com.raven.suportSwing.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
