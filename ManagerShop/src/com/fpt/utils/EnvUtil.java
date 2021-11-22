/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.utils;

import io.github.cdimascio.dotenv.Dotenv;


public class EnvUtil {

    private static Dotenv dotenv;

    public static Dotenv dotenv() {
        if (dotenv == null) {
            dotenv = Dotenv.configure().filename(".env").load();
        }
        return dotenv;
    }

    public static String get(String key) {
        return dotenv().get(key);
    }
}
