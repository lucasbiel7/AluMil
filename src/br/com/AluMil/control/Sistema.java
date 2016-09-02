/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author OCTI01
 */
public class Sistema {

    public static String gerarCodigoAtivacao() {
        String modelo = getModeloHD();
        if (modelo != null) {
            return String.valueOf(modelo.charAt(0)) + String.valueOf(modelo.charAt(1)) + String.valueOf(modelo.charAt(1)) + String.valueOf(modelo.charAt(0)) + "-" + String.valueOf(modelo.charAt(modelo.length() - 1)) + String.valueOf(modelo.charAt(modelo.length() - 2)) + String.valueOf(modelo.charAt(modelo.length() - 2)) + String.valueOf(modelo.charAt(modelo.length() - 1));
        }
        return "0000-0000";
    }

    public static String getModeloHD() {
        try {
            Process process = Runtime.getRuntime().exec("cmd /c wmic diskdrive get interfacetype,model");
            String codigo = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linha = bufferedReader.readLine();
            String modelo = null;
            do {
                if (linha.contains("IDE")) {
                    modelo = linha;
                }
                linha = bufferedReader.readLine();
            } while (linha != null);
            if (modelo != null) {
                modelo = modelo.replace("IDE", "");
                String[] nomesModelo = modelo.split(" ");
                modelo = "";
                for (String string : nomesModelo) {
                    if (string.matches(".*\\w{1,}.*")) {
                        modelo += string + " ";
                    }
                }
                modelo = modelo.substring(0, modelo.length() - 1);
                return modelo;
            }
        } catch (IOException ex) {
            System.out.println("Erro");
        }
        return null;
    }

    public static String getUsuario() {
        return System.getProperty("user.name");
    }
}
