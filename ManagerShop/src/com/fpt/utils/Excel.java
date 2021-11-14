/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ducit
 */
public class Excel {

//    public static void XuatFile(DefaultTableModel model) throws FileNotFoundException, IOException {
//        JFileChooser fchoChooser = new JFileChooser();
//        int result = fchoChooser.showSaveDialog(null);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            try {
//                if (!fchoChooser.getSelectedFile().toString().endsWith(".xlsx")) {
//                    MsgBox.alert(null, "File của bạn phải là excel");
//                    return;
//                }
//                File newFile = new File(fchoChooser.getSelectedFile().toString());
//                FileOutputStream file = new FileOutputStream(newFile.getAbsoluteFile().getPath());
//                XSSFWorkbook wb = new XSSFWorkbook();
//                XSSFSheet wsheet = wb.createSheet("name");
//                XSSFRow row = wsheet.createRow(0);
//                XSSFCell h;
//                for (int i = 0; i < model.getColumnCount(); i++) {
//                    h = row.createCell(i);
//                    h.setCellValue(model.getColumnName(i));
//                }
//                XSSFRow row1;
//                XSSFCell a1;
//                for (int i = 0; i < model.getRowCount(); i++) {
//                    row1 = wsheet.createRow(i + 1);
//                    for (int j = 0; j < model.getColumnCount(); j++) {
//                        a1 = row1.createCell(j);
//                        a1.setCellValue(model.getValueAt(i, j).toString());
//                    }
//                }
//                wb.write(file);
//                wb.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
