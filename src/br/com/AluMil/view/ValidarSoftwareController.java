/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.Sistema;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ValidarSoftwareController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextArea taInformacoes;
    @FXML
    private TextField tfValidar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        taInformacoes.setText("Nome do usuário:\n"
                + Sistema.getUsuario()+"\n"
                + "Modelo do HD: \n"
                + Sistema.getModeloHD());
        System.out.println(Sistema.gerarCodigoAtivacao());
        
    }

    @FXML
    private void btValidarActionEvent(ActionEvent actionEvent) {
        if (tfValidar.getText().equals(Sistema.gerarCodigoAtivacao())) {
            List<String> dados = new ArrayList<>();
            dados.add(tfValidar.getText());
            try {
                Files.write(Paths.get("conf/registro.conf"), dados, Charset.defaultCharset());
            } catch (IOException ex) {
                Logger.getLogger(ValidarSoftwareController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Message.showMessage("Software ativado com sucesso!", Alert.AlertType.INFORMATION);
            ((Stage) apPrincipal.getScene().getWindow()).close();
        } else {
            Message.showMessage("Serial inválido", Alert.AlertType.ERROR);
        }
    }

}
