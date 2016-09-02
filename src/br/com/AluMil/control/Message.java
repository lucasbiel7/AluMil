/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.control;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author OCTI01
 */
public class Message {

    public static boolean showConfirm(String mesagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(mesagem);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return alert.showAndWait().get().equals(ButtonType.YES);
    }

    public static void showMessage(String message, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo, message, ButtonType.OK);
        alert.show();
    }

    public static String showInputDialog(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(message);
        return dialog.showAndWait().get();
    }
}
