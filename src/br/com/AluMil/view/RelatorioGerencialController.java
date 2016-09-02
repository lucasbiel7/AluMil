/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioGerencialController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btRelatorio1ActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("RelatorioGerencial01"));
    }

    @FXML
    private void btRelatorio2ActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("RelatorioGerencial02"));
    }

}
