/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.Validate;

import com.fpt.utils.MsgBox;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ducit =======
 *
 * /**
 *
 * @author Đặng Đình Vũ >>>>>>> Vudd
 */
public class labelValidate {

    public static boolean checkEmpty(JLabel lbl, JTextField txtString, String mess) {
        if (txtString.getText().trim().isEmpty()) {
            MsgBox.labelAlert(lbl, txtString, mess);
            return false;
        }
        return true;

    }
    
    public static boolean checkEmptyTextArea(JLabel lbl, JTextArea txtString, String mess) {
        if (txtString.getText().trim().isEmpty()) {
            MsgBox.labelAlertTextArea(lbl, txtString, mess);
            return false;
        }
        return true;

    }

    public static boolean checkNumber(JLabel lbl, JTextField txtString, String mess) {
        boolean check = true;
        try {
            double number = Double.parseDouble(txtString.getText());
            if (number < 0) {
                MsgBox.labelAlert(lbl, txtString, mess);
                check = false;
            } else {
                check = true;
            }
        } catch (Exception e) {
            MsgBox.labelAlert(lbl, txtString, "Lương không hợp lệ!!!");
            check = false;
        }
        return check;
    }

    public static boolean checkEmail(JLabel lbl, JTextField field, String sb) {
        boolean flag = true;
        if (!checkEmpty(lbl, field, "Email chưa nhập!!!")) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\w+@\\w+(\\.\\w+){1,2}");
        Matcher matcher = pattern.matcher(field.getText());
        if (!matcher.find()) {
            MsgBox.labelAlert(lbl, field, "Email không hợp lệ không hợp lệ\n");
            flag = false;
        }
        return flag;
    }

    public static boolean checkPhoneNumber(JLabel lbl, JTextField field, String sb) {
        boolean flag = true;
        if (!checkEmpty(lbl, field, "Số điện thoại chưa nhập!!!")) {
            return false;
        }

        Pattern pattern = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})");
        Matcher matcher = pattern.matcher(field.getText());
        if (!matcher.find()) {
            MsgBox.labelAlert(lbl, field, "Số điện thoại không hợp lệ không hợp lệ\n");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }
}
