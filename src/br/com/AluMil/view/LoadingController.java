/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LoadingController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ProgressBar pbProgresso;
    @FXML
    private Label lbProgresso;
    private Timeline timeLine;
    private int progresso;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progresso = 0;
        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent ae) -> {
            lbProgresso.setText(progresso + "%");
            pbProgresso.setProgress(progresso / 100.0);
            progresso += 10;
        }));
        timeLine.setCycleCount(12);
        timeLine.play();
        timeLine.setOnFinished((ActionEvent event) -> {
            ((Stage) apPrincipal.getScene().getWindow()).close();
            FxMananger.showWindow(FxMananger.carregarComponente("GestaoMaquina"), "Gestão TPM", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.EXIT_ON_CLOSE).show();
        });
    }

}
