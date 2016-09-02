/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.IntegerSpinner;
import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.DispositivoDAO;
import br.com.AluMil.model.entity.Dispositivo;
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
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
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
public class NovoDispositivoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNomeMaquina;
    @FXML
    private Spinner<Integer> spNumeroPecas;
    @FXML
    private Spinner<Integer> spPeriodicidade;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Button btAdicionar;
    @FXML
    private Button btCancelar;
    @FXML
    private Label lbTitle;
    private Maquina maquina;
    private Dispositivo dispositivo;
    private FileChooser fcImagem;

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
            maquina = (Maquina) apPrincipal.getUserData();
            lbTitle.setText(lbTitle.getText() + " " + maquina.getNome());
        });
        dispositivo = new Dispositivo();
        fcImagem = new FileChooser();
        fcImagem.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.jpg", "*.png", "*.gif", "*.jpeg"));
        spNumeroPecas.setValueFactory(new IntegerSpinner(1, Integer.MAX_VALUE));
        spPeriodicidade.setValueFactory(new IntegerSpinner(1, Integer.MAX_VALUE));
    }

    @FXML
    private void btAdicionarFotoActionEvent(ActionEvent actionEvent) {
        File file = fcImagem.showOpenDialog(apPrincipal.getScene().getWindow());
        if (file != null) {
            try {
                dispositivo.setFoto(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                ivFoto.setImage(new Image(new ByteArrayInputStream(dispositivo.getFoto())));
            } catch (IOException ex) {
                Logger.getLogger(NovaMaquinaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btRemoverFotoActionEvent(ActionEvent actionEvent) {
        dispositivo.setFoto(null);
        ivFoto.setImage(null);
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        dispositivo.setNome(tfNomeMaquina.getText());
        dispositivo.setAtivo(true);
        dispositivo.setMaquina(maquina);
        dispositivo.setPecas(spNumeroPecas.getValue());
        dispositivo.setPeriodicidade(spPeriodicidade.getValue());
        if (dispositivo.getNome().isEmpty()) {
            Message.showMessage("Digite o nome do dispositivo", Alert.AlertType.ERROR);
        } else if (dispositivo.getFoto() == null) {
            Message.showMessage("Foto do dispositivo vazia", Alert.AlertType.ERROR);
        } else if (!new DispositivoDAO().pegarPorNomeMaquina(dispositivo.getNome(),maquina).isEmpty()) {
            Message.showMessage("Já existe um dispositivo com esse nome", Alert.AlertType.ERROR);
        } else {
            new DispositivoDAO().cadastrar(dispositivo);
            Message.showMessage("Cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            tfNomeMaquina.clear();
            spNumeroPecas.getValueFactory().setValue(1);
            spPeriodicidade.getValueFactory().setValue(1);
            ivFoto.setImage(null);
            dispositivo = new Dispositivo();
        }
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

}
