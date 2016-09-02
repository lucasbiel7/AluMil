/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.MaquinaDAO;
import br.com.AluMil.model.entity.Maquina;
import com.sun.javafx.scene.control.behavior.TextBinding;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class NovaMaquinaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNomeEquipamento;
    @FXML
    private ImageView ivFoto;

    private Maquina maquina;

    private FileChooser fcImagem;

    @FXML
    private Button btAdicionar;
    @FXML
    private Button btCancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apPrincipal.getScene().addMnemonic(new Mnemonic(btAdicionar, new TextBinding.MnemonicKeyCombination("A")));
            apPrincipal.getScene().addMnemonic(new Mnemonic(btCancelar, new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_ANY)));
            btAdicionar.setTooltip(new Tooltip("(Alt+A)"));
            btCancelar.setTooltip(new Tooltip("(Alt+C)"));
            btAdicionar.setMnemonicParsing(true);
            btCancelar.setMnemonicParsing(true);
        });
        maquina = new Maquina();
        fcImagem = new FileChooser();
        fcImagem.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.jpg", "*.png", "*.gif", "*.jpeg"));
    }

    @FXML
    private void btAdicionarFotoActionEvent(ActionEvent actionEvent) {
        File file = fcImagem.showOpenDialog(apPrincipal.getScene().getWindow());
        if (file != null) {
            try {
                maquina.setFoto(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                ivFoto.setImage(new Image(new ByteArrayInputStream(maquina.getFoto())));
            } catch (IOException ex) {
                Logger.getLogger(NovaMaquinaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btRemoverFotoActionEvent(ActionEvent actionEvent) {
        maquina.setFoto(null);
        ivFoto.setImage(null);
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        if (new MaquinaDAO().pegarPorNome(tfNomeEquipamento.getText()).isEmpty()) {
            if (!tfNomeEquipamento.getText().isEmpty()) {
                if (maquina.getFoto() != null) {
                    maquina.setAtivo(true);
                    maquina.setNome(tfNomeEquipamento.getText());
                    new MaquinaDAO().cadastrar(maquina);
                    Message.showMessage("Cadastrado com sucesso!", Alert.AlertType.INFORMATION);
                    tfNomeEquipamento.clear();
                    ivFoto.setImage(null);
                    maquina = new Maquina();
                } else {
                    Message.showMessage("Foto do equipamento não pode ser vazio!", Alert.AlertType.ERROR);
                }
            } else {
                Message.showMessage("Nome do equipamento não pode ser vazio!", Alert.AlertType.ERROR);
            }
        } else {
            Message.showMessage("Já há uma máquina cadastrada com esse nome", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }
}
