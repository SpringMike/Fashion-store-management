/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.DetailInvoiceReturnDAO;
import com.fpt.DAO.ProductItemDAO;
import com.fpt.DAO.ReturnProductDAO;
import com.fpt.entity.DetailInvoiceReturn;
import com.fpt.entity.InvoiceRetuns;
import com.fpt.entity.ProductItem;
import com.fpt.utils.Auth;
import com.fpt.utils.MsgBox;
import com.fpt.utils.XDate;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormReturnProducts extends javax.swing.JPanel {

    /**
     * Creates new form FormReturnProducts
     */
    DefaultTableModel model = null;
    DefaultTableModel modelList = null;

    public FormReturnProducts() {
        initComponents();
        model = new DefaultTableModel();
        modelList = new DefaultTableModel();
    }

    ReturnProductDAO reDao = new ReturnProductDAO();

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

    public boolean checkDayReturn() {
        LocalDate today = LocalDate.now();
        LocalDate date = LocalDate.parse(XDate.toString(listPr.get(0).getDateCreateInvoice(), "dd-MM-yyy"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int day = Period.between(date, today).getDays();
        int month = Period.between(date, today).getMonths();
        int year = Period.between(date, today).getYears();

        if (day > 2 || month > 0 || year > 0) {
            MsgBox.labelAlert(lblSearch, txtShearchInvoice, "Ngày trả hoá đơn đã quá hạn");
            System.out.println(day);
            return false;
        }
        return true;
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

    List<DetailInvoiceReturn> list = new ArrayList<>();

    public void fillTableIn4Invoice() {
        try {
            int quatity = Integer.valueOf(MsgBox.prompt(this, "Nhập số lượng cần hoàn trả"));
            int row = tableIn4Invoice.getSelectedRow();
            int idinvoiceSell = (int) tableIn4Invoice.getValueAt(row, 0);
            int idProduct = (int) tableIn4Invoice.getValueAt(row, 1);
            String name = (String) tableIn4Invoice.getValueAt(row, 2);
            int quantity = (int) tableIn4Invoice.getValueAt(row, 3);
            String size = (String) tableIn4Invoice.getValueAt(row, 4);
            String color = (String) tableIn4Invoice.getValueAt(row, 5);
            String material = (String) tableIn4Invoice.getValueAt(row, 6);
            float price = (float) tableIn4Invoice.getValueAt(row, 7);

            if (quatity > (int) tableIn4Invoice.getValueAt(row, 3) || quatity < 0) {
                MsgBox.warring(this, "Số lượng trả hàng không hợp lệ!!!");
            } else {
                modelList = (DefaultTableModel) tableListProduct.getModel();
                modelList.addRow(new Object[]{
                    idProduct, name, quatity, size, color, material, price
                });

                int i = ((int) tableIn4Invoice.getValueAt(row, 3)) - quatity;
                tableIn4Invoice.setValueAt(i, row, 3);
                lblMoneyRetun.setText(TotalBuy() + "");

                DetailInvoiceReturn dir = new DetailInvoiceReturn();
                dir.setPrice(price);
                dir.setIdPrDetails(idProduct);
                dir.setQuatity(quatity);
                list.add(dir);
                tableIn4Invoice.clearSelection();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Chưa chọn số lượng hoàn trả ???");
        }

    }

    ProductItemDAO prDAO = new ProductItemDAO();
    DetailInvoiceReturnDAO dDao = new DetailInvoiceReturnDAO();

    public void insertInvoiceReturn() {
        InvoiceRetuns ir = getInvoiceReturn();
        reDao.insert(ir);
        MsgBox.alert(this, "Thêm thành công!!!");
        int row = tableListProduct.getRowCount();
        for (int i = 0; i < list.size(); i++) {
            DetailInvoiceReturn de = list.get(i);
            System.out.println(de.getQuatity());
            dDao.insert(de);
            prDAO.returnProductItem(de.getQuatity(), de.getIdPrDetails());
        }
    }

    public float TotalBuy() {
        float price = 0;
        int index = tableListProduct.getRowCount();
        for (int i = 0; i < index; i++) {
            price += (int) tableListProduct.getValueAt(i, 2) * (float) tableListProduct.getValueAt(i, 6);
        }
        return price;
    }

    public InvoiceRetuns getInvoiceReturn() {
        InvoiceRetuns ir = new InvoiceRetuns();
        Calendar calendar = Calendar.getInstance();
        ir.setDateCreateInvoiceReturn(calendar.getTime());
        ir.setDescription(txtNote.getText());
        ir.setIdInvoiceSell(Integer.valueOf(txtShearchInvoice.getText()));
        ir.setTotalReturn(Double.valueOf(lblMoneyRetun.getText()));
        ir.setIdUser(Auth.user.getIdUser());
        List<ProductItem> items = reDao.selectByIdInvoiceReturn(Integer.valueOf(txtShearchInvoice.getText()));
        for (ProductItem p : items) {
            ir.setIdCustomer(p.getIdCustomer());
            ir.setNameCustomer(p.getNameCustomer());
            break;
        }

        return ir;
    }

    public void deleteTemp() {
        DefaultTableModel model = (DefaultTableModel) tableListProduct.getModel();
        int row = tableIn4Invoice.getSelectedRow();
        int rowTemp = tableListProduct.getSelectedRow();

        if (tableListProduct.getSelectedRowCount() == 1) {
            for (int i = 0; i < tableIn4Invoice.getRowCount(); i++) {
                if (tableIn4Invoice.getValueAt(i, 1) == tableListProduct.getValueAt(rowTemp, 0)) {
                    int ii = (int) tableIn4Invoice.getValueAt(i, 3) + (int) tableListProduct.getValueAt(rowTemp, 2);
                    tableIn4Invoice.setValueAt(ii, i, 3);
                }
            }

            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getIdPrDetails() == (int) tableListProduct.getValueAt(rowTemp, 0)) {
                    model.removeRow(tableListProduct.getSelectedRow());
                    list.remove(list.get(j));
                    return;
                }
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableListProduct = new com.raven.suportSwing.TableColumn();
        jLabel2 = new javax.swing.JLabel();
        lblIDCustomer = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblIDInvoice = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        lblMoneyRetun = new javax.swing.JLabel();
        btnAddEmployee = new com.raven.suportSwing.MyButton();
        btnAddEmployee1 = new com.raven.suportSwing.MyButton();
        scrollBarCustom2 = new com.raven.suportSwing.ScrollBarCustom();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableIn4Invoice = new com.raven.suportSwing.TableColumn();
        scrollBarCustom1 = new com.raven.suportSwing.ScrollBarCustom();
        txtShearchInvoice = new com.raven.suportSwing.TextField();
        jLabel9 = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1000, 1000));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Trả Hàng");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jScrollPane2.setVerticalScrollBar(scrollBarCustom2);

        tableListProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng trả", "Size", "Màu ", "Chất liệu", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableListProduct);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Khách hàng");

        lblIDCustomer.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblIDCustomer.setForeground(new java.awt.Color(255, 51, 51));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Mã Hoá đơn");

        lblIDInvoice.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblIDInvoice.setForeground(new java.awt.Color(255, 51, 51));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Ghi Chú");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane3.setViewportView(txtNote);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Tiền hoàn trả");

        lblMoneyRetun.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMoneyRetun.setForeground(new java.awt.Color(255, 0, 0));

        btnAddEmployee.setText("Trả hàng");
        btnAddEmployee.setRadius(10);
        btnAddEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployeeActionPerformed(evt);
            }
        });

        btnAddEmployee1.setText("Xoá");
        btnAddEmployee1.setRadius(10);
        btnAddEmployee1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddEmployee1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(678, 678, 678)
                        .addComponent(btnAddEmployee1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollBarCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lblIDCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMoneyRetun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(32, 32, 32))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblIDInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIDCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIDInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(80, 80, 80)
                        .addComponent(jLabel7))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMoneyRetun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(scrollBarCustom2, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddEmployee1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hoá đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jScrollPane1.setVerticalScrollBar(scrollBarCustom1);

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
        jScrollPane1.setViewportView(tableIn4Invoice);
        if (tableIn4Invoice.getColumnModel().getColumnCount() > 0) {
            tableIn4Invoice.getColumnModel().getColumn(0).setResizable(false);
            tableIn4Invoice.getColumnModel().getColumn(1).setResizable(false);
            tableIn4Invoice.getColumnModel().getColumn(3).setResizable(false);
            tableIn4Invoice.getColumnModel().getColumn(4).setResizable(false);
            tableIn4Invoice.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(1161, Short.MAX_VALUE)
                .addComponent(scrollBarCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(27, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(scrollBarCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        txtShearchInvoice.setLabelText("Tìm kiếm hoá đơn");
        txtShearchInvoice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtShearchInvoiceFocusGained(evt);
            }
        });
        txtShearchInvoice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtShearchInvoiceKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtShearchInvoiceKeyReleased(evt);
            }
        });

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
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(172, 172, 172)
                        .addComponent(jLabel9)
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtShearchInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtShearchInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployeeActionPerformed
        insertInvoiceReturn();

    }//GEN-LAST:event_btnAddEmployeeActionPerformed

    private void txtShearchInvoiceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtShearchInvoiceKeyReleased
        // TODO add your handling code here:
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
            if (checkReturn() == false) {
                lblSearch.setText("Hoá đơn đã trả hàng");
                return;
            }
            if (checkDayReturn() == false) {
                return;
            }
        } catch (Exception e) {
            MsgBox.labelAlert(lblSearch, txtShearchInvoice, "Vui lòng nhập lại -.-");
        }
    }//GEN-LAST:event_txtShearchInvoiceKeyReleased

    private void tableIn4InvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableIn4InvoiceMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            if (checkDayReturn() == false) {
                MsgBox.alert(this, "Hoá đơn đã quá hạn trả");
                return;
            } else if (checkReturn() == false) {
                MsgBox.alert(this, "Hoá đơn đã trả hàng");
                return;
            } else {
                fillTableIn4Invoice();
            }
        }

    }//GEN-LAST:event_tableIn4InvoiceMouseClicked

    private void txtShearchInvoiceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtShearchInvoiceFocusGained
        // TODO add your handling code here:
        lblSearch.setText("");
    }//GEN-LAST:event_txtShearchInvoiceFocusGained

    private void txtShearchInvoiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtShearchInvoiceKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtShearchInvoiceKeyPressed

    private void btnAddEmployee1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddEmployee1ActionPerformed
        // TODO add your handling code here:
        deleteTemp();
    }//GEN-LAST:event_btnAddEmployee1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnAddEmployee;
    private com.raven.suportSwing.MyButton btnAddEmployee1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblIDCustomer;
    private javax.swing.JLabel lblIDInvoice;
    private javax.swing.JLabel lblMoneyRetun;
    private javax.swing.JLabel lblSearch;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom1;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom2;
    private com.raven.suportSwing.TableColumn tableIn4Invoice;
    private com.raven.suportSwing.TableColumn tableListProduct;
    private javax.swing.JTextArea txtNote;
    private com.raven.suportSwing.TextField txtShearchInvoice;
    // End of variables declaration//GEN-END:variables
}
