/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.IntegerSpinner;
import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.OcorrenciaDAO;
import br.com.AluMil.control.dao.UsuarioDAO;
import br.com.AluMil.model.entity.Manutencao;
import br.com.AluMil.model.entity.Ocorrencia;
import br.com.AluMil.model.entity.Usuario;
import br.com.AluMil.model.util.Formatter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class NovoOcorrenciaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<Usuario> cbUsuario;
    @FXML
    private DatePicker dpDataOcorrencia;
    @FXML
    private Spinner<Integer> spTempo;
    @FXML
    private TextArea taOcorrencia;

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    private Manutencao manutencao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            manutencao = (Manutencao) apPrincipal.getUserData();
        });
        cbUsuario.setItems(usuarios);
        usuarios.setAll(new UsuarioDAO().pegarTodos());
        dpDataOcorrencia.setValue(LocalDate.now());
        IntegerSpinner integerSpinner = new IntegerSpinner(1, 60);
        spTempo.setValueFactory(integerSpinner);
        spTempo.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            if (spTempo.getValue() < 5) {
                integerSpinner.setStep(1);
            } else {
                integerSpinner.setStep(5);
            }
        });

    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    @FXML
    private void btConfirmarActionEvent(ActionEvent actionEvent) {
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setDataRealizada(Formatter.toDate(dpDataOcorrencia.getValue()));
        ocorrencia.setManutencao(manutencao);
        ocorrencia.setOcorrencia(taOcorrencia.getText());
        ocorrencia.setTempoGasto(spTempo.getValue());
        ocorrencia.setUsuario(cbUsuario.getSelectionModel().getSelectedItem());
        if (ocorrencia.getUsuario() != null) {
            if (!ocorrencia.getOcorrencia().isEmpty()) {
                new OcorrenciaDAO().cadastrar(ocorrencia);
                ((Stage)apPrincipal.getScene().getWindow()).close();
            } else {
                Message.showMessage("Ocorrência não pode ser vazia", Alert.AlertType.ERROR);
            }
        } else {
            Message.showMessage("Selecione o funcionário", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btLimparActionEvent(ActionEvent actionEvent) {
        taOcorrencia.clear();
    }

    @FXML
    private void taOcorrenciaKeyRelease(KeyEvent keyEvent) {
        if (taOcorrencia.getLength() >= 100) {
            taOcorrencia.setText(taOcorrencia.getText().substring(0, 100));
        }
    }
}
