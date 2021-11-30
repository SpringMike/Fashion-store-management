/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author ducit
 */
public class XImage {

//    public static Image getAppImage() {
//        URL url = XImage.class.getResource("src\\com\\raven\\icon\\shop(5).png");
//        return new ImageIcon(url).getImage();
//    }

    public static void save(File src) {
        File dst = new File("Logos", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon read(String fileName) {
        File file = new File("logos", fileName);
        return new ImageIcon(new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(100, 90, Image.SCALE_DEFAULT));
    }
}
