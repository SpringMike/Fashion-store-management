/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.raven.JFrame;

import com.fpt.DAO.ProductItemDAO;
import com.fpt.entity.ProductItem;
import com.fpt.utils.MsgBox;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class FormChangeProductJframe extends javax.swing.JFrame {

    /**
     * Creates new form FormChangeProduct
     */
    DefaultTableModel modelList = null;
    DefaultTableModel model = null;

    float total = 0;
    List<ProductItem> list = new ArrayList<>();
    ProductItemDAO prDAO = new ProductItemDAO();

    public FormChangeProductJframe() {
        initComponents();
    }

    public String deleteLastKey(String str) {
        if (str.charAt(str.length() - 1) == 'đ') {
            str = str.replace(str.substring(str.length() - 1), "");
            return str;
        } else {
            return str;
        }
    }

    public String fomartFloat(String txt) {
        String pattern = deleteLastKey(txt);
        return pattern = pattern.replaceAll(",", "");
    }

    Locale lc = new Locale("nv", "VN");
    NumberFormat nf = NumberFormat.getInstance(lc);

    public FormChangeProductJframe(List<ProductItem> list, float totatMoney, List<ProductItem> list2) {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("src\\com\\raven\\icon\\shop (6).png");
        this.setIconImage(icon);
//        btnDelete.setEnabled(false);
        total = totatMoney;
        lbllTotalMoney.setText(nf.format(total) + " đ");
        modelList = (DefaultTableModel) tableListProduct.getModel();
        for (int i = 0; i < list.size(); i++) {
            ProductItem p = list.get(i);
            if (p.getQuantity() < 0) {
                modelList.addRow(new Object[]{
                    p.getId(), p.getProductName(), p.getSize(), p.getColor(), p.getMaterial(), nf.format(p.getPrice()) + " đ", p.getQuantity()
                });
            } else if ((list2.size() > 0)) {
                for (int j = 0; j < list2.size(); j++) {
                    if (p.getId() == list2.get(j).getId()) {
                        p.setQuantity(p.getQuantity() - list2.get(j).getQuantity());
                    }
                }
                modelList.addRow(new Object[]{
                    p.getId(), p.getProductName(), p.getSize(), p.getColor(), p.getMaterial(), nf.format(p.getPrice()) + " đ", p.getQuantity()
                });

            } else {
                modelList.addRow(new Object[]{
                    p.getId(), p.getProductName(), p.getSize(), p.getColor(), p.getMaterial(), nf.format(p.getPrice()) + " đ", p.getQuantity()
                });
            }
        }
    }

    public void fillTableIn4Invoice() {
        try {
            int quatity = Integer.valueOf(MsgBox.prompt(this, "Nhập số lượng"));
            int row = tableListProduct.getSelectedRow();
            int id = (int) tableListProduct.getValueAt(row, 0);
            String name = (String) tableListProduct.getValueAt(row, 1);
            String size = (String) tableListProduct.getValueAt(row, 2);
            String color = (String) tableListProduct.getValueAt(row, 3);
            String material = (String) tableListProduct.getValueAt(row, 4);
            float price = Float.parseFloat(fomartFloat((String) tableListProduct.getValueAt(tableListProduct.getSelectedRow(), 5)));
            if (quatity > (int) tableListProduct.getValueAt(row, 6) || quatity < 0) {
                MsgBox.warring(this, "Số lượng trả hàng không hợp lệ!!!");
            } else {
                if (total - (price * quatity) < 0) {
                    MsgBox.alert(this, "Bạn đã chọn quá số tiền được đổi");
                    return;
                }
                total = total - (price * quatity);
                lbllTotalMoney.setText(nf.format(total) + " đ");
                model = (DefaultTableModel) tableShow.getModel();
                model.addRow(new Object[]{
                    id, name, size, color, material, price, quatity
                });
                int i = ((int) tableListProduct.getValueAt(row, 6)) - quatity;
                tableListProduct.setValueAt(i, row, 6);
                tableListProduct.clearSelection();

                ProductItem pr = prDAO.selectById(id);
                pr.setQuantity(quatity);
                list.add(pr);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Chưa chọn số lượng hoàn trả ???");
        }
    }

    public List<ProductItem> addToFormChangeProduct() {
        System.out.println("hello");
        if (!list.isEmpty()) {
            model = (DefaultTableModel) tableShow.getModel();
            model.setRowCount(0);
            this.dispose();
            MsgBox.alert(this, "Thêm SP đổi thành công");
            return list;
        }
        MsgBox.alert(this, "Bạn chưa thêm SP nào");
        return null;
    }

    public void addEvenFillTable(ActionListener evt) {
        btnAdd.addActionListener(evt);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tableListProduct = new com.raven.suportSwing.TableColumn();
        scrollBarCustom2 = new com.raven.suportSwing.ScrollBarCustom();
        jLabel7 = new javax.swing.JLabel();
        lbllTotalMoney = new javax.swing.JLabel();
        btnAdd = new com.raven.suportSwing.MyButton();
        btnDelete = new com.raven.suportSwing.MyButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableShow = new com.raven.suportSwing.TableColumn();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tableListProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Size", "Màu ", "Chất liệu", "Đơn giá", "Số Lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableListProduct.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableListProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListProductMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableListProduct);
        if (tableListProduct.getColumnModel().getColumnCount() > 0) {
            tableListProduct.getColumnModel().getColumn(0).setResizable(false);
            tableListProduct.getColumnModel().getColumn(1).setResizable(false);
            tableListProduct.getColumnModel().getColumn(2).setResizable(false);
            tableListProduct.getColumnModel().getColumn(3).setResizable(false);
            tableListProduct.getColumnModel().getColumn(4).setResizable(false);
            tableListProduct.getColumnModel().getColumn(5).setResizable(false);
            tableListProduct.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Tổng tiền trả");

        lbllTotalMoney.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbllTotalMoney.setForeground(new java.awt.Color(255, 51, 51));

        btnAdd.setText("Hoàn thành");
        btnAdd.setRadius(20);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.setRadius(20);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tableShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Size", "Màu ", "Chất liệu", "Đơn giá", "Số Lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableShow.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableShowMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableShow);
        if (tableShow.getColumnModel().getColumnCount() > 0) {
            tableShow.getColumnModel().getColumn(0).setResizable(false);
            tableShow.getColumnModel().getColumn(1).setResizable(false);
            tableShow.getColumnModel().getColumn(2).setResizable(false);
            tableShow.getColumnModel().getColumn(3).setResizable(false);
            tableShow.getColumnModel().getColumn(4).setResizable(false);
            tableShow.getColumnModel().getColumn(5).setResizable(false);
            tableShow.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbllTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollBarCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(scrollBarCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lbllTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void delete() {
        if (tableShow.getSelectedRowCount() == 1) {

            for (int i = 0; i < tableListProduct.getRowCount(); i++) {
                if (tableListProduct.getValueAt(i, 0).equals(tableShow.getValueAt(tableShow.getSelectedRow(), 0))) {
                    tableListProduct.setValueAt((int) tableListProduct.getValueAt(i, 6) + (int) tableShow.getValueAt(tableShow.getSelectedRow(), 6), i, 6);
                }
            }

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == (int) tableShow.getValueAt(tableShow.getSelectedRow(), 0)) {
                    list.remove(list.get(i));
                    total = total + (float) tableShow.getValueAt(tableShow.getSelectedRow(), 5) * (int) tableShow.getValueAt(tableShow.getSelectedRow(), 6);
                    lbllTotalMoney.setText(nf.format(total) + " đ");
                    model.removeRow(tableShow.getSelectedRow());
                }
            }

        }
    }
    private void tableListProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListProductMouseClicked
        fillTableIn4Invoice();
    }//GEN-LAST:event_tableListProductMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tableShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableShowMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableShowMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FormChangeProductJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FormChangeProductJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FormChangeProductJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FormChangeProductJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FormChangeProductJframe().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnAdd;
    private com.raven.suportSwing.MyButton btnDelete;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbllTotalMoney;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom2;
    private com.raven.suportSwing.TableColumn tableListProduct;
    private com.raven.suportSwing.TableColumn tableShow;
    // End of variables declaration//GEN-END:variables
}
