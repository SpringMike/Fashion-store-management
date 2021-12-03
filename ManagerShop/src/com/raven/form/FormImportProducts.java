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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
    Locale lc = new Locale("nv", "VN");
    NumberFormat formatter = NumberFormat.getIntegerInstance(lc);
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
                p.getId(), p.getProductName(), p.getCategoryName(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity(), formatter.format(p.getPrice()) + " đ"
            });
        }
    }

    public void fillTableId() {
        DefaultTableModel model = (DefaultTableModel) tableProductItem.getModel();
        model.setRowCount(0);
        int id = Integer.valueOf(txtSearch.getText());
        ProductItem p = prDAO.selectImprotProductID(id);
        if (p == null) {
            lblSearch.setText("Không tìm thấy mặt hàng " + id);
            return;
        }
        model.addRow(new Object[]{
            p.getId(), p.getProductName(), p.getCategoryName(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity(), formatter.format(p.getPrice()) + " đ"
        });
    }

    public void fillTableKey() {
        DefaultTableModel model = (DefaultTableModel) tableProductItem.getModel();
        model.setRowCount(0);
        String key = txtSearch.getText();
        List<ProductItem> list = prDAO.selectImprotProductKey(key);
        if (list.isEmpty()) {
            lblSearch.setText("Không tìm thấy mặt hàng " + key);
            return;
        }
        for (ProductItem p : list) {
            model.addRow(new Object[]{
                p.getId(), p.getProductName(), p.getCategoryName(), p.getSize(), p.getColor(), p.getMaterial(), p.getQuantity(), formatter.format(p.getPrice()) + " đ"
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProductItem = new com.raven.suportSwing.TableColumn();
        scrollBarCustom1 = new com.raven.suportSwing.ScrollBarCustom();
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
        txtSearch = new com.raven.suportSwing.TextField();
        jLabel2 = new javax.swing.JLabel();
        myButton4 = new com.raven.suportSwing.MyButton();
        lblSearch = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jScrollPane1.setVerticalScrollBar(scrollBarCustom1);

        tableProductItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Mặt hàng", "Tên Sản Phẩm", "Loại", "Size", "Màu Sắc", "Chất liệu", "Số lượng trong kho", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollBarCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(scrollBarCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhập hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

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
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 866, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(scrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(btnAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddTemp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteTemp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantity)
                    .addComponent(lblPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)))
                .addContainerGap())
        );

        jScrollPane5.getAccessibleContext().setAccessibleName("");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        myButton1.setText("Tìm kiếm");
        myButton1.setRadius(10);
        myButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton1ActionPerformed(evt);
            }
        });

        txtSearch.setLabelText("Tìm theo tên hoặc mã sản phẩm");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Nhập Hàng");

        myButton4.setText("Import");
        myButton4.setRadius(10);
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Times New Roman", 3, 12)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 768, Short.MAX_VALUE)
                .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(132, 132, 132)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(27, 27, 27)
                    .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(474, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(myButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
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

    private void myButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton1ActionPerformed
        // TODO add your handling code here:
        try {
            fillTableId();
        } catch (Exception e) {
            fillTableKey();
        }
    }//GEN-LAST:event_myButton1ActionPerformed


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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblSearch;
    private com.raven.suportSwing.MyButton myButton1;
    private com.raven.suportSwing.MyButton myButton4;
    private com.raven.suportSwing.ScrollBar scrollBar2;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom1;
    private com.raven.suportSwing.TableColumn tableProductItem;
    private com.raven.suportSwing.TableColumn tableTemp;
    private javax.swing.JTextArea txtAreaDesc;
    private com.raven.suportSwing.TextField txtPrice;
    private com.raven.suportSwing.TextField txtQuantity;
    private com.raven.suportSwing.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
