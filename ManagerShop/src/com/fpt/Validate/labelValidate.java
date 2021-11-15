/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.Validate;

import static com.fpt.Validate.Validate.checkEmpty;
import com.fpt.utils.MsgBox;
import java.awt.Color;
import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author ducit
 */
public class labelValidate {

    public static boolean checkEmpty(JLabel lbl, JTextField txtString, String mess) {
        if (txtString.getText().isEmpty()) {
            MsgBox.labelAlert(lbl, txtString, mess);
            return false;
        }
        return true;
    }

    public static boolean checkEmail(JLabel lbl, JTextField field, String sb) {
        boolean flag = true;
        if (!checkEmpty(lbl, field, "Email Chưa Nhập")) {
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
}
