/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.form;

import com.fpt.DAO.CustomerDAO;
import com.fpt.DAO.DetailInvoiceSellDAO;
import com.fpt.DAO.InvoiceSellDAO;
import com.fpt.DAO.ProductItemDAO;
import com.fpt.DAO.SupplierDao;
import com.fpt.DAO.VoucherDAO;
import com.fpt.Validate.Validate;
import com.fpt.entity.Customer;
import com.fpt.entity.DetailInvoiceSell;
import com.fpt.entity.InvoiceSell;
import com.fpt.entity.ProductItem;
import com.fpt.entity.Supplier;
import com.fpt.entity.Voucher;
import com.fpt.utils.Auth;
import com.fpt.utils.MsgBox;
import java.text.DecimalFormat;
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
public class FormSell extends javax.swing.JPanel {

    /**
     * Creates new form FormProducts
     */
    public FormSell() {
        initComponents();
        setOpaque(false);
        fillTableProductItem();
        fillComboxCustomer();
        fillComboxVoucher();
        jcheckVoucher.setSelected(false);
        cbbVoucher.setVisible(false);
        txtReturn.setEditable(false);
        txtTotal.setEditable(false);

    }

    ProductItemDAO prDAO = new ProductItemDAO();
    CustomerDAO cDao = new CustomerDAO();
    VoucherDAO vDao = new VoucherDAO();
    InvoiceSellDAO inDao = new InvoiceSellDAO();
    DetailInvoiceSellDAO deDao = new DetailInvoiceSellDAO();

    // Locale lc = new Locale("nv", "VN");
    // NumberFormat formatter = NumberFormat.getIntegerInstance(lc);
    public void fillTableProductItem() {
        DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        List<ProductItem> list = prDAO.selectAllSell();
        for (ProductItem p : list) {
            model.addRow(new Object[] { p.getId(), p.getProductName(), p.getCategoryName(), p.getSize(), p.getColor(),
                    p.getMaterial(), p.getPrice(), p.getQuantity() });
        }
    }

    public void fillComboxCustomer() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbCustomer.getModel();
        cbbCustomer.removeAllItems();
        List<Customer> list = cDao.selectAll();
        for (Customer s : list) {
            model.addElement(s);
        }
    }

    public void fillComboxVoucher() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbVoucher.getModel();
        cbbVoucher.removeAllItems();
        List<Voucher> list = vDao.selectAll();
        for (Voucher s : list) {
            model.addElement(s);
        }
    }

    List<DetailInvoiceSell> list = new ArrayList<>();

    public void fillTableTemp() {
        if (!Validate.checkEmpty(lblQuantity, txtQuantity, "Số lượng không bỏ trống")) {
            return;
        } else if (!Validate.checkNumber(lblQuantity, txtQuantity, "Số lượng nhập phải hợp lệ")) {
            return;
        } else {
            int row = tableShow.getSelectedRow();
            if (row == -1) {
                MsgBox.alert(this, "Bạn chưa chọn mặt hàng nào");
            } else if (Integer.parseInt(txtQuantity.getText()) > (int) tableShow.getValueAt(row, 7)) {
                MsgBox.labelAlert(lblQuantity, txtQuantity, "Số lượng bán lớn hơn số lượng trong kho");
                return;
            } else {
                int id = (int) tableShow.getValueAt(row, 0);
                String name = (String) tableShow.getValueAt(row, 1);
                String categoryName = (String) tableShow.getValueAt(row, 2);
                String size = (String) tableShow.getValueAt(row, 3);
                String color = (String) tableShow.getValueAt(row, 4);
                String material = (String) tableShow.getValueAt(row, 5);
                float price = (float) tableShow.getValueAt(row, 6);
                int quantity = Integer.parseInt(txtQuantity.getText());

                DefaultTableModel model = (DefaultTableModel) tableSellTemp.getModel();
                model.addRow(new Object[] { id, name, categoryName, size, color, material, price, quantity });

                // fillTableProductItem();
                DetailInvoiceSell de = new DetailInvoiceSell();
                de.setPrice(price);
                de.setIdPrDetails(id);
                de.setQuantity(quantity);
                list.add(de);
                tableShow.clearSelection();
                txtQuantity.setText("");
                int i = ((int) tableShow.getValueAt(row, 7)) - quantity;
                tableShow.setValueAt(i, row, 7);
                System.out.println(i);
                // de.setQuantity(i);

                // fillTableProductItem();
            }
        }
    }

    public float TotalBuy() {
        float price = 0;
        int index = tableSellTemp.getRowCount();
        for (int i = 0; i < index; i++) {
            price += (float) tableSellTemp.getValueAt(i, 6) * (int) tableSellTemp.getValueAt(i, 7);
        }
        return price;
    }

    public float MoneyVoucher() {
        Voucher v = (Voucher) cbbVoucher.getSelectedItem();
        float voucher = v.getValue();
        return (int) TotalBuy() - (TotalBuy() * (float) (voucher / 100));
    }

    InvoiceSell getInvoiceSell() {
        InvoiceSell in = new InvoiceSell();
        Calendar calendar = Calendar.getInstance();
        in.setDateCreateInvoice(calendar.getTime());
        in.setStatusInvoice(false);
        in.setStatusPay(false);
        in.setIdHumanSell(Auth.user.getIdUser());
        in.setDescription(txtDes.getText());
        in.setPrice(Double.parseDouble(txtTotal.getText()));
        Customer s = (Customer) cbbCustomer.getSelectedItem();
        in.setIdCustomer(s.getId());
        if (!jcheckVoucher.isSelected()) {
            in.setIdVoucher(null);
        } else {
            Voucher v = (Voucher) cbbVoucher.getSelectedItem();
            in.setIdVoucher(v.getIdVoucher());
        }
        return in;
    }

    public void insertInvoiceSell() {
        int count = tableSellTemp.getRowCount();
        if (count <= 0) {
            MsgBox.alert(this, "bạn chưa thanh toán sản phẩm nào");
            // return;
        } else {
            if (!Validate.checkEmpty(lblMoneyCustomer, txtMoneyCustomer, "Khổng bỏ trống tiền khách đưa")) {
                return;
            } else if (!Validate.checkNumber(lblMoneyCustomer, txtMoneyCustomer, "Tiền không hợp lệ")) {
                return;
            } else if (Double.parseDouble(txtReturn.getText()) < 0) {
                MsgBox.alert(this, "Nhậm lại số tiền khách đưa ????");
                return;
            } else {
                InvoiceSell in = getInvoiceSell();
                inDao.insert(in);
                int row = tableSellTemp.getRowCount();
                for (int i = 0; i < list.size(); i++) {
                    DetailInvoiceSell de = list.get(i);
                    System.out.println(de.getQuantity());
                    deDao.insert(de);
                    prDAO.sellProductItem(de.getQuantity(), de.getIdPrDetails());
                }
                in.setPrice(Double.parseDouble(txtTotal.getText()));
                MsgBox.alert(this, "Bán " + list.size() + " vào hóa đơn thành công thành công");
                // Voucher v = (Voucher) cbbVoucher.getSelectedItem();
                // vDao.updateVoucher(v.getIdVoucher());
                if (jcheckVoucher.isSelected()) {
                    Voucher v = (Voucher) cbbVoucher.getSelectedItem();
                    vDao.updateVoucher(v.getIdVoucher());
                }
                DefaultTableModel model = (DefaultTableModel) tableSellTemp.getModel();
                model.setRowCount(0);
                list.clear();
                fillTableProductItem();
            }
        }
    }

    public void searchFillTable() {
        DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        List<ProductItem> list = prDAO.selectByKeyWordSell(txtSearch.getText());
        if (list.isEmpty()) {
            lblSearch.setText("Không tìm thấy mặt hàng " + txtSearch.getText() + " để bán");
            return;
        }
        for (ProductItem p : list) {
            model.addRow(new Object[] { p.getId(), p.getProductName(), p.getCategoryName(), p.getSize(), p.getColor(),
                    p.getMaterial(), p.getPrice(), p.getQuantity() });
        }
        lblSearch.setText("");
    }

    public void delete() {
        DefaultTableModel model = (DefaultTableModel) tableSellTemp.getModel();
        int row = tableShow.getSelectedRow();
        int row2 = tableSellTemp.getSelectedRow();

        // List<DetailInvoiceSell> list = new ArrayList<>();
        if (tableSellTemp.getSelectedRowCount() == 1) {
            for (int i = 0; i < tableShow.getRowCount(); i++) {
                if (tableShow.getValueAt(i, 0) == tableSellTemp.getValueAt(row2, 0)) {
                    int ii = (int) tableShow.getValueAt(i, 7) + (int) tableSellTemp.getValueAt(row2, 7);
                    tableShow.setValueAt(ii, i, 7);
                }
            }
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getIdPrDetails() == (int) tableSellTemp.getValueAt(row2, 0)) {
                    model.removeRow(tableSellTemp.getSelectedRow());
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new com.raven.suportSwing.TextField();
        myButton5 = new com.raven.suportSwing.MyButton();
        lblSearch = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShow = new com.raven.suportSwing.TableColumn();
        scrollBar1 = new com.raven.suportSwing.ScrollBar();
        jPanel4 = new javax.swing.JPanel();
        cbbCustomer = new com.raven.suportSwing.Combobox();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDes = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        myButton2 = new com.raven.suportSwing.MyButton();
        myButton3 = new com.raven.suportSwing.MyButton();
        myButton4 = new com.raven.suportSwing.MyButton();
        txtMoneyCustomer = new com.raven.suportSwing.TextField();
        txtReturn = new com.raven.suportSwing.TextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableSellTemp = new com.raven.suportSwing.TableColumn();
        scrollBar2 = new com.raven.suportSwing.ScrollBar();
        txtTotal = new com.raven.suportSwing.TextField();
        cbbVoucher = new com.raven.suportSwing.Combobox();
        jcheckVoucher = new javax.swing.JCheckBox();
        lblMoneyCustomer = new javax.swing.JLabel();
        txtQuantity = new com.raven.suportSwing.TextField();
        myButton1 = new com.raven.suportSwing.MyButton();
        lblQuantity = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Thánh toán bán hàng");

        txtSearch.setLabelText("Tìm theo tên or mã");
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        myButton5.setText("Tìm");
        myButton5.setRadius(20);
        myButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton5ActionPerformed(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                    .addComponent(lblSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(193, 193, 193))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                            .addComponent(myButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setVerticalScrollBar(scrollBar1);

        tableShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Loại", "Size", "Màu", "Chất Liệu", "Đơn giá", "Số lượng tồn kho"
            }
        ));
        jScrollPane1.setViewportView(tableShow);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1074, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(scrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        cbbCustomer.setLabeText("Khách hàng");

        txtDes.setColumns(20);
        txtDes.setRows(5);
        jScrollPane3.setViewportView(txtDes);

        jLabel1.setText("Ghi Chú");

        myButton2.setText("Xoá");
        myButton2.setRadius(20);
        myButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton2ActionPerformed(evt);
            }
        });

        myButton3.setText("Hoàn thành");
        myButton3.setRadius(20);
        myButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton3ActionPerformed(evt);
            }
        });

        myButton4.setText("Tìm");
        myButton4.setRadius(20);
        myButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton4ActionPerformed(evt);
            }
        });

        txtMoneyCustomer.setLabelText("Tiền khách đưa");
        txtMoneyCustomer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMoneyCustomerFocusGained(evt);
            }
        });
        txtMoneyCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMoneyCustomerKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMoneyCustomerKeyReleased(evt);
            }
        });

        txtReturn.setLabelText("Tiền trả lại");
        txtReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReturnActionPerformed(evt);
            }
        });

        jScrollPane4.setVerticalScrollBar(scrollBar2);

        tableSellTemp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Loại", "Size", "Màu", "Chất Liệu", "Đơn giá", "Số lượng bán"
            }
        ));
        jScrollPane4.setViewportView(tableSellTemp);

        txtTotal.setLabelText("Tổng tiền");

        cbbVoucher.setLabeText("Mã giảm giá");
        cbbVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbVoucherActionPerformed(evt);
            }
        });

        jcheckVoucher.setText("ÁP mã voucher ?");
        jcheckVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcheckVoucherActionPerformed(evt);
            }
        });

        lblMoneyCustomer.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblMoneyCustomer.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMoneyCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(scrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMoneyCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(cbbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jcheckVoucher)
                                .addGap(38, 38, 38)
                                .addComponent(cbbVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(19, 19, 19)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcheckVoucher)
                            .addComponent(cbbVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMoneyCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblMoneyCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtReturn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtQuantity.setLabelText("Số lượng bán");
        txtQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQuantityFocusGained(evt);
            }
        });
        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQuantityKeyPressed(evt);
            }
        });

        myButton1.setText("Lưu tạm");
        myButton1.setRadius(20);
        myButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton1ActionPerformed(evt);
            }
        });

        lblQuantity.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblQuantity.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtQuantityActionPerformed

    private void myButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myButton5ActionPerformed
        // TODO add your handling code here:
        searchFillTable();
    }// GEN-LAST:event_myButton5ActionPerformed

    private void myButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myButton4ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_myButton4ActionPerformed

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myButton2ActionPerformed
        // TODO add your handling code here:

        delete();

    }// GEN-LAST:event_myButton2ActionPerformed

    private void myButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myButton1ActionPerformed
        // TODO add your handling code here:
        fillTableTemp();
        txtTotal.setText(TotalBuy() + "");
    }// GEN-LAST:event_myButton1ActionPerformed

    private void myButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_myButton3ActionPerformed
        // TODO add your handling code here:
        insertInvoiceSell();
    }// GEN-LAST:event_myButton3ActionPerformed

    private void cbbVoucherActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbbVoucherActionPerformed
        // TODO add your handling code here:
        txtTotal.setText(MoneyVoucher() + "");
        // txtMoneyVoucher.setText(MoneyVoucher() + "");
    }// GEN-LAST:event_cbbVoucherActionPerformed

    private void txtMoneyCustomerKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtMoneyCustomerKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtMoneyCustomerKeyPressed

    private void jcheckVoucherActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcheckVoucherActionPerformed
        // TODO add your handling code here:
        if (jcheckVoucher.isSelected()) {
            cbbVoucher.setVisible(true);
            cbbVoucher.setSelectedIndex(0);
        } else {
            cbbVoucher.setVisible(false);
            txtTotal.setText(TotalBuy() + "");
        }
    }// GEN-LAST:event_jcheckVoucherActionPerformed

    private void txtReturnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtReturnActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtReturnActionPerformed

    private void txtMoneyCustomerKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtMoneyCustomerKeyReleased
        // TODO add your handling code here:
        if (txtMoneyCustomer.getText().isEmpty()) {
            txtReturn.setText("");
        } else {
            txtReturn.setText(Float.valueOf(txtMoneyCustomer.getText()) - Float.valueOf(txtTotal.getText()) + "");
        }
    }// GEN-LAST:event_txtMoneyCustomerKeyReleased

    private void txtQuantityKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtQuantityKeyPressed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtQuantityKeyPressed

    private void txtQuantityFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtQuantityFocusGained
        // TODO add your handling code here:
        lblQuantity.setText("");
    }// GEN-LAST:event_txtQuantityFocusGained

    private void txtMoneyCustomerFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtMoneyCustomerFocusGained
        // TODO add your handling code here:
        lblMoneyCustomer.setText("");
    }// GEN-LAST:event_txtMoneyCustomerFocusGained

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        searchFillTable();
    }// GEN-LAST:event_txtSearchKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.Combobox cbbCustomer;
    private com.raven.suportSwing.Combobox cbbVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JCheckBox jcheckVoucher;
    private javax.swing.JLabel lblMoneyCustomer;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblSearch;
    private com.raven.suportSwing.MyButton myButton1;
    private com.raven.suportSwing.MyButton myButton2;
    private com.raven.suportSwing.MyButton myButton3;
    private com.raven.suportSwing.MyButton myButton4;
    private com.raven.suportSwing.MyButton myButton5;
    private com.raven.suportSwing.ScrollBar scrollBar1;
    private com.raven.suportSwing.ScrollBar scrollBar2;
    private com.raven.suportSwing.TableColumn tableSellTemp;
    private com.raven.suportSwing.TableColumn tableShow;
    private javax.swing.JTextArea txtDes;
    private com.raven.suportSwing.TextField txtMoneyCustomer;
    private com.raven.suportSwing.TextField txtQuantity;
    private com.raven.suportSwing.TextField txtReturn;
    private com.raven.suportSwing.TextField txtSearch;
    private com.raven.suportSwing.TextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
