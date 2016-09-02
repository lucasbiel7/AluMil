/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.model.entity.Dispositivo;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class VisualizarDispositivoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbDescricao;

    private Dispositivo dispositivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            dispositivo = (Dispositivo) apPrincipal.getUserData();
            if (dispositivo.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(dispositivo.getFoto())));
            }
            lbDescricao.setText(""
                    + "Nome: " + dispositivo.getNome() + "\n"
                    + "N° de peças: " + dispositivo.getPecas() + "\n"
                    + "Periodicidade: " + dispositivo.getPeriodicidade() + "\n"
                    + "Máquina: " + dispositivo.getMaquina() + "\n"
                    + "");

        });
    }

}
