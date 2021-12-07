/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.JFrame;

import com.fpt.DAO.DetailInvoiceReturnDAO;
import com.fpt.entity.DetailInvoiceReturn;
import com.fpt.utils.MsgBox;
import static com.fpt.utils.convertEng.removeAccent;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ducit
 */
public class FormDetailInvoiceReturn extends javax.swing.JFrame {

    /**
     * Creates new form FormDetailInvoiceReturn
     */
    DefaultTableModel model;
    int row;
    List<DetailInvoiceReturn> list;
    public FormDetailInvoiceReturn(int id, DefaultTableModel model, int row) {
        this.model = model;
        this.row = row;
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        fillTable(id);
    }

    DetailInvoiceReturnDAO dDao = new DetailInvoiceReturnDAO();
    Locale lc = new Locale("nv", "VN");
    NumberFormat nf = NumberFormat.getInstance(lc);
    public void fillTable(int id) {
        DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
        model.setRowCount(0);
        list = dDao.selectByIdInvoice(id);
        for (DetailInvoiceReturn d : list) {
            model.addRow(new Object[]{
                d.getIdDetailInvoiceReturn(), d.getNameProduct(), d.getNameCustomer(), d.getValueSize(), d.getValueColor(), d.getValueMaterial(), d.getQuatity(), nf.format(d.getPrice()) + " đ"
            });
        }
    }

    public void outputPDF() throws IOException, BadElementException {

        String path = "D:\\InvoiceReturn.pdf";
        com.itextpdf.kernel.pdf.PdfWriter pdfWriter = new com.itextpdf.kernel.pdf.PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDocument);
        float col = 280f;
        float columnWidth[] = {col, col};
        com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(columnWidth);
        table.setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE);
        String file = "D:\\Fall2021\\DuAn1_FPOLY\\ManagerShop\\src\\com\\raven\\icon\\shop (2).png";
        ImageData date = ImageDataFactory.create(file);
        com.itextpdf.layout.element.Image image = new com.itextpdf.layout.element.Image(date);
//        doc.add(image);
        table.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("IT SHOP").setFontSize(30f).setBorder(Border.NO_BORDER));

        table.addCell(new Cell().add("68 Nguyen Trai \n SĐT: 0332429178 - 03324287654")
                .setTextAlignment(TextAlignment.RIGHT).setMarginTop(30f).setMarginBottom(30f).setBorder(Border.NO_BORDER).setMarginRight(10f)
        );

        float colWidth[] = {80, 250, 80, 150};

        com.itextpdf.layout.element.Table customerInfor = new com.itextpdf.layout.element.Table(colWidth);
        customerInfor.addCell(new Cell(0, 4).add("Hoa Don Tra Hang").setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

        customerInfor.addCell(new Cell(0, 4).add("Thong tin").setBold().setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add("Khach Hang: ").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add(removeAccent(tableShow.getValueAt(0, 2).toString())).setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add("Ma Hoa Don: ").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add(model.getValueAt(row, 0) + "").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add("SDT: ").setBorder(Border.NO_BORDER)); //
        customerInfor.addCell(new Cell().add(removeAccent(model.getValueAt(row, 4).toString())).setBorder(Border.NO_BORDER)); //

//        customerInfor.addCell(new Cell().add("Thu Ngan: ").setBorder(Border.NO_BORDER)); //
//        customerInfor.addCell(new Cell().add(removeAccent(model.getValueAt(row, 3).toString())).setBorder(Border.NO_BORDER)); //
        customerInfor.addCell(new Cell().add("Date: ").setBorder(Border.NO_BORDER));
        customerInfor.addCell(new Cell().add(model.getValueAt(row, 2) + "").setBorder(Border.NO_BORDER));

        float iteamInforColWidth[] = {140, 140, 140, 140};
        com.itextpdf.layout.element.Table itemInforTable = new com.itextpdf.layout.element.Table(iteamInforColWidth);
        itemInforTable.addCell(new Cell().add("San Pham").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
        itemInforTable.addCell(new Cell().add("So luong").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
        itemInforTable.addCell(new Cell().add("Gia").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT));
        itemInforTable.addCell(new Cell().add("Thanh Tien").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT));

        int total = 0;
        int quantitySum = 0;
        for (DetailInvoiceReturn detailReturn : list) {
            String id = detailReturn.getIdDetailInvoiceReturn()+"";
            String nameProduct = detailReturn.getNameProduct();
            String nameCustomer = detailReturn.getNameCustomer();
            String Size = detailReturn.getValueSize();
            String Color = detailReturn.getValueColor();
            String Material = detailReturn.getValueMaterial();
            int quantity = (int) detailReturn.getQuatity();
            double price = (double) detailReturn.getPrice();
            itemInforTable.addCell(new Cell().add(removeAccent(nameProduct)));
            itemInforTable.addCell(new Cell().add(quantity + ""));
            itemInforTable.addCell(new Cell().add(nf.format(price)+" đ").setTextAlignment(TextAlignment.RIGHT));
            itemInforTable.addCell(new Cell().add(price * quantity + " đ").setTextAlignment(TextAlignment.RIGHT));
            total += price * quantity;
            quantitySum += quantity;
        }

        itemInforTable.addCell(new Cell().add("Tong So Luong").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
        itemInforTable.addCell(new Cell().add(quantitySum + "").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
        itemInforTable.addCell(new Cell().add("Tong Tien").setTextAlignment(TextAlignment.RIGHT).setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER).setFontColor(Color.WHITE));
        itemInforTable.addCell(new Cell().add(nf.format(total) + " đ").setTextAlignment(TextAlignment.RIGHT).setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER).setFontColor(Color.WHITE));

        float colWidthNote[] = {560};

        com.itextpdf.layout.element.Table customerInforNote = new com.itextpdf.layout.element.Table(colWidthNote);
//        customerInforNote.addCell(new Cell().add("Luu y: Quy khach vui long kiem tra hang truoc khi roi khoi shop \n Giu hoa don khi tra hang trong vong 2 ngay").
//                setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setItalic().setFontColor(Color.RED));
        customerInforNote.addCell(new Cell().add("Xin cam on quy khach !!!").
                setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setItalic().setFontColor(Color.BLACK));
        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(customerInfor);
        document.add(new Paragraph("\n"));
        document.add(itemInforTable);
        document.add(new Paragraph("\n"));
        document.add(customerInforNote);
        document.close();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tableShow = new com.raven.suportSwing.TableColumn();
        myButton6 = new com.raven.suportSwing.MyButton();
        myButton7 = new com.raven.suportSwing.MyButton();
        scrollBarCustom1 = new com.raven.suportSwing.ScrollBarCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Hoá đơn trả chi tiết");

        jScrollPane1.setVerticalScrollBar(scrollBarCustom1);

        tableShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên sản phẩm", "Tên khách hàng", "Size", "Màu", "Chất liệu", "Số lượng", "Tiền hoàn trả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableShow);

        myButton6.setText("Huỷ");
        myButton6.setRadius(20);
        myButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton6ActionPerformed(evt);
            }
        });

        myButton7.setText("Xuất Hoá Đơn");
        myButton7.setRadius(20);
        myButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(691, Short.MAX_VALUE)
                .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollBarCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(scrollBarCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void myButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton6ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_myButton6ActionPerformed

    private void myButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton7ActionPerformed
        try {
            // TODO add your handling code here:
            outputPDF();
            MsgBox.alert(this, "Xuất hoá đơn thành công");
        } catch (IOException ex) {
            Logger.getLogger(FormDetailInvoiceReturn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadElementException ex) {
            Logger.getLogger(FormDetailInvoiceReturn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_myButton7ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.suportSwing.MyButton myButton6;
    private com.raven.suportSwing.MyButton myButton7;
    private com.raven.suportSwing.ScrollBarCustom scrollBarCustom1;
    private com.raven.suportSwing.TableColumn tableShow;
    // End of variables declaration//GEN-END:variables
}
