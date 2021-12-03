package com.fpt.Validate;

import com.fpt.DAO.UserDAO;
import com.fpt.entity.User;
import com.fpt.utils.MsgBox;
import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ducit
 */
public class Validate {

    public static boolean checkEmpty(Component component, JTextField txtString, String mess) {
        if (txtString.getText().trim().isEmpty()) {
            MsgBox.warring(component, mess);
            txtString.setBorder(new LineBorder(Color.red));
            return false;
        } else {
            txtString.setBorder(new LineBorder(Color.green));
        }
        return true;
    }

    public static boolean checkEmpty(JLabel lbl, JTextField txtString, String mess) {
        if (txtString.getText().trim().isEmpty()) {
            MsgBox.labelAlert(lbl, txtString, mess);
            return false;
        }
        return true;
    }

    public static boolean checkLength(JLabel component, JTextField txtString, String mess, int numberLength) {
        if (txtString.getText().length() < numberLength || txtString.getText().length() > numberLength) {
            MsgBox.labelAlert(component, txtString, mess);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkEmpty(Component component, JTextArea txtString, String mess) {
        if (txtString.getText().trim().isEmpty()) {
            MsgBox.warring(component, mess);
            txtString.setBorder(new LineBorder(Color.red));
            return false;
        } else {
            txtString.setBorder(new LineBorder(Color.green));
        }
        return true;
    }

    public static boolean checkDate(Component component, JLabel lbl, JTextField txtString, String mess) {
        try {
            if (!checkEmpty(component, txtString, lbl.getText() + " chưa nhập!!!")) {
                return false;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String fmatDate = sdf.format(sdf.parse(txtString.getText()));
            if (fmatDate.equals(txtString.getText())) {
                return true;
            } else {
                MsgBox.warring(component, mess);
            }
        } catch (Exception e) {
            MsgBox.warring(txtString, "Không được nhập chữ");
        }
        return false;
    }

    public static boolean checkImg(Component component, JLabel lbl, String mess) {
        try {
            if (lbl.getIcon() == null) {
                MsgBox.warring(component, mess);
                lbl.setBorder(new LineBorder(Color.red));
                return false;
            } else {
                lbl.setBorder(new LineBorder(Color.green));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean checkNumber(JLabel lbl, JTextField field, String mess) {
        boolean check = true;
        if (!checkEmpty(lbl, field, "Chưa nhập!!!")) {
            return false;
        }
        try {
            int intNumber = Integer.parseInt(field.getText());
            double doubleNumber = Double.parseDouble(field.getText());
            if (intNumber <= 0 || doubleNumber <= 0) {
                MsgBox.labelAlert(lbl, field, mess);
            }
        } catch (Exception e) {
            MsgBox.labelAlert(lbl, field, "Không được nhập chữ!!!");
            check = false;
        }
        return check;
    }
    
    public static boolean checkNumber(JComponent component, JTextField field, String mess) {
        boolean check = true;
        if (!checkEmpty(component, field, "Chưa nhập!!!")) {
            return false;
        }
        try {
            int intNumber = Integer.parseInt(field.getText());
            double doubleNumber = Double.parseDouble(field.getText());
            if (intNumber <= 0 || doubleNumber <= 0) {
                MsgBox.alert( field, mess);
            }
        } catch (Exception e) {
            MsgBox.alert(field, "Không được nhập chữ!!!");
            check = false;
        }
        return check;
    }

    public static boolean checkEmail(Component component, JTextField field, String sb) {
        boolean flag = true;
        if (!checkEmpty(component, field, "Email Chưa Nhập\n")) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\w+@\\w+(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(field.getText());
        if (!matcher.find()) {
            MsgBox.warring(component, "Email không hợp lệ không hợp lệ\n");
            field.setBorder(new LineBorder(Color.RED));
            flag = false;
        }
        if (flag) {
            field.setBorder(new LineBorder(Color.GREEN));
        }
        return flag;
    }

    public static boolean checkPhoneNumber(Component component, JTextField field, String sb) {
        Pattern pattern = Pattern.compile("^0\\d{9,10}");
        Matcher matcher = pattern.matcher(field.getText());
        boolean flag = true;
        if (!checkEmpty(component, field, "Số điện thoại Chưa Nhập\n")) {
            return false;
        } else if (!matcher.find()) {
            MsgBox.warring(component, "Số điện thoại nhập không hợp lệ!!!");
            field.setBorder(new LineBorder(Color.RED));
            flag = false;
        }
        if (flag) {
            field.setBorder(new LineBorder(Color.GREEN));
        }
        return flag;
    }

    public static boolean duplicateCheckEmailUser(JLabel lbl, JTextField txtString, String email, String mess) {
        UserDAO dao = new UserDAO();
        List<User> list = dao.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getEmail().trim().equals(email)) {
                MsgBox.labelAlert(lbl, txtString, mess);
                return true;
            }
        }
        return false;
    }

    public static boolean duplicateCheckPhoneUser(JLabel lbl, JTextField txtString, String phone, String mess) {
        UserDAO dao = new UserDAO();
        List<User> list = dao.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPhoneNumber().trim().equals(phone)) {
                MsgBox.labelAlert(lbl, txtString, mess);
                return true;
            }
        }
        return false;
    }

//    public static void checkPoin(String text, String text0, StringBuilder sb, String số_tiền_không_hợp_lệ) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
