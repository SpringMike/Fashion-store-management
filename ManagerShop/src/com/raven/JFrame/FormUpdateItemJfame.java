/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.raven.JFrame;

import com.fpt.DAO.ColorDAO;
import com.fpt.DAO.MaterialDAO;
import com.fpt.DAO.ProductItemDAO;
import com.fpt.DAO.ProductsDAO;
import com.fpt.DAO.SizeDAO;
import com.fpt.Validate.Validate;
import com.fpt.entity.Color;
import com.fpt.entity.Material;
import com.fpt.entity.ProductItem;
import com.fpt.entity.Products;
import com.fpt.entity.Size;
import com.fpt.utils.MsgBox;
import com.raven.form.FormItems;
import com.raven.form.MainForm;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Administrator
 */
public class FormUpdateItemJfame extends javax.swing.JFrame {

    /**
     * Creates new form FormUpdateImtemJfame
     */
    public FormUpdateItemJfame() {
        initComponents();
    }
    SizeDAO sDao = new SizeDAO();
    ColorDAO cDao = new ColorDAO();
    MaterialDAO mDao = new MaterialDAO();
    ProductsDAO productDAO = new ProductsDAO();
    ProductItemDAO productItemDAO = new ProductItemDAO();

    public FormUpdateItemJfame(String nameProduct, String sizeValue, String colorValue, String materialValue, Float price, int idProductItem) {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        lblPrice.setVisible(false);

        fillComboboxColor();
        fillComboboxMaterial();
        fillComboboxProduct();
        fillComboboxSize();
        txtPrice.setText(Float.toString(price));
        txtId.setText(Integer.toString(idProductItem));
        txtId.setEditable(false);

        Size size = sDao.selectByName(sizeValue);
        Color c = cDao.selectByName(colorValue);
        Material m = mDao.selectByName(materialValue);

        Products p = productDAO.selectByName(nameProduct);

        cbbSize.getModel().setSelectedItem(size);
        cboColor.getModel().setSelectedItem(c);
        cbbMaterial.getModel().setSelectedItem(m);
        cbbProduct.getModel().setSelectedItem(p);
        cbbProduct.setEditable(false);
        cbbProduct.setEnabled(false);
    }

    public ProductItem getFormProductItem() {
        Material m = (Material) cbbMaterial.getSelectedItem();
        Size s = (Size) cbbSize.getSelectedItem();
        Color c = (Color) cboColor.getSelectedItem();
        Products p = (Products) cbbProduct.getSelectedItem();
        ProductItem pro = new ProductItem();
        pro.setId(Integer.parseInt(txtId.getText()));
        pro.setPrice(Float.parseFloat(txtPrice.getText()));
        pro.setIdSize(s.getIdSize());
        pro.setIdColor(c.getIdColor());
        pro.setIdMaterial(m.getIdMaterial());
        pro.setIdProduct(p.getIdProduct());
        pro.setQuantity(0);
        pro.setStatus(true);
        pro.setSize(s.getValueSize());
        pro.setColor(c.getValueColor());
        pro.setMaterial(m.getValueMaterial());
        pro.setProductName(p.getNameProduct());

        return pro;
    }

    public void update() {

        if (!Validate.checkEmpty(lblPrice, txtPrice, "Không bỏ trống giá tiền")) {
            lblPrice.setVisible(true);
            return;
        } else {
            ProductItem pro = getFormProductItem();
            productItemDAO.update(pro);
            MsgBox.alert(this, "Update mặt hàng thành công");
            new MainForm().showForm(new FormItems());
            this.dispose();
        }
    }

    public void addEvenUpdate(ActionListener evt) {
        btnUpdate.addActionListener(evt);
    }

    public void fillComboboxSize() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbSize.getModel();
        cbbSize.removeAllItems();
        List<Size> list = sDao.selectAll();
        for (Size s : list) {
            model.addElement(s);
        }
    }

    public void fillComboboxColor() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboColor.getModel();
        cboColor.removeAllItems();
        List<Color> list = cDao.selectAll();
        for (Color c : list) {
            model.addElement(c);
        }
    }

    public void fillComboboxMaterial() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbMaterial.getModel();
        cbbMaterial.removeAllItems();
        List<Material> list = mDao.selectAll();
        for (Material c : list) {
            model.addElement(c);
        }
    }

    public void fillComboboxProduct() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbProduct.getModel();
        cbbProduct.removeAllItems();
        List<Products> list = productDAO.selectAll();
        for (Products p : list) {
            model.addElement(p);
        }
    }

//    Size getFormSize() {
//        Size s = new Size();
//        s.setIdSize(cbbSize.getSelectedIndex() + 1);
//        s.setValueSize(txtSize.getText());
//        return s;
//    }
//
//    Color getFormColor() {
//        Color c = new Color();
//        c.setValueColor(txtColor.getText());
//        return c;
//    }
//
//    Material getFormMaterial() {
//        Material m = new Material();
//        m.setValueMaterial(txtMaterial.getText());
//        return m;
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txtId = new com.raven.suportSwing.TextField();
        jPanel6 = new javax.swing.JPanel();
        cboColor = new com.raven.suportSwing.Combobox();
        cbbSize = new com.raven.suportSwing.Combobox();
        cbbMaterial = new com.raven.suportSwing.Combobox();
        cbbProduct = new com.raven.suportSwing.Combobox();
        btnUpdate = new com.raven.suportSwing.MyButton();
        lblPrice = new javax.swing.JLabel();
        txtPrice = new com.raven.suportSwing.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sửa mặt hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        txtId.setLabelText("Id");
        txtId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdFocusGained(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        cboColor.setLabeText("");
        cboColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboColorActionPerformed(evt);
            }
        });

        cbbSize.setLabeText("");
        cbbSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbbSizeMouseClicked(evt);
            }
        });
        cbbSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSizeActionPerformed(evt);
            }
        });

        cbbMaterial.setLabeText("");
        cbbMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMaterialActionPerformed(evt);
            }
        });

        cbbProduct.setLabeText("");
        cbbProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbProductActionPerformed(evt);
            }
        });

        btnUpdate.setText("Hoàn Thành");
        btnUpdate.setRadius(20);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboColor, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cboColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        lblPrice.setForeground(new java.awt.Color(225, 0, 0));
        lblPrice.setText("jLabel3");

        txtPrice.setLabelText("Giá Bán");
        txtPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPriceFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPrice)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdFocusGained

    }//GEN-LAST:event_txtIdFocusGained

    private void cbbProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbProductActionPerformed

    }//GEN-LAST:event_cbbProductActionPerformed

    private void cbbMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMaterialActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbMaterialActionPerformed

    private void cbbSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSizeActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbSizeActionPerformed

    private void cbbSizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbbSizeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbSizeMouseClicked

    private void cboColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboColorActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboColorActionPerformed

    private void txtPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormUpdateItemJfame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormUpdateItemJfame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormUpdateItemJfame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormUpdateItemJfame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormUpdateItemJfame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.suportSwing.MyButton btnUpdate;
    private com.raven.suportSwing.Combobox cbbMaterial;
    private com.raven.suportSwing.Combobox cbbProduct;
    private com.raven.suportSwing.Combobox cbbSize;
    private com.raven.suportSwing.Combobox cboColor;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblPrice;
    private com.raven.suportSwing.TextField txtId;
    private com.raven.suportSwing.TextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
