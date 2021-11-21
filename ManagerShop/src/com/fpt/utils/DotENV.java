/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.utils;

import io.github.cdimascio.dotenv.Dotenv;

/**
 *
 * @author minht
 */
public class DotENV {
    public static void loadENV(){
         Dotenv dotenv = Dotenv.configure().load();
    }
    public static void getKey(String key){
        Dotenv.configure().load().get(key);
    }
}
