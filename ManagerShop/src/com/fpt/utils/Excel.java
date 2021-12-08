/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ducit
 */
public class Excel {

    public static void outExcel(DefaultTableModel model) throws FileNotFoundException, IOException {
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("D:\\Excel");
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("Excel Files", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTABLE sheet");
                XSSFRow row = excelSheet.createRow(0);
                XSSFCell h;
                for (int i = 0; i < model.getColumnCount(); i++) {
                    h = row.createCell(i);
                    h.setCellValue(model.getColumnName(i));
                }
                for (int i = 0; i < model.getRowCount(); i++) {
                    row = excelSheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        h = row.createCell(j);
                        if (model.getValueAt(i, j) != null) {
                            h.setCellValue(model.getValueAt(i, j).toString());
                        }
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }

                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
