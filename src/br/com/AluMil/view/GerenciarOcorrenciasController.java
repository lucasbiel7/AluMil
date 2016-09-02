/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.CicloDAO;
import br.com.AluMil.control.dao.ManutencaoDAO;
import br.com.AluMil.control.dao.OcorrenciaDAO;
import br.com.AluMil.control.dao.UsuarioDAO;
import br.com.AluMil.model.entity.Ciclo;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Ocorrencia;
import br.com.AluMil.model.entity.Usuario;
import br.com.AluMil.model.util.Formatter;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarOcorrenciasController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNomeManutencao;
    @FXML
    private DatePicker dpDataRodada;
    @FXML
    private ComboBox<Usuario> cbFuncionario;
    @FXML
    private DatePicker dpDataProgramada;
    @FXML
    private DatePicker dpDataExecucao;
    @FXML
    private TextArea taOcorrencia;
    @FXML
    private TextArea taDescricaoCiclo;
    @FXML
    private TableView<Ocorrencia> tvOcorrencia;
    @FXML
    private TableColumn<Ocorrencia, Integer> tcCodigo;
    @FXML
    private TableColumn<Ocorrencia, Date> tcDataExecucao;
    @FXML
    private TableColumn<Ocorrencia, String> tcOcorrencia;
    @FXML
    private TableColumn<Ocorrencia, Date> tcDataOcorrencia;
    @FXML
    private TableColumn<Ocorrencia, Usuario> tcFuncionario;

    private ObservableList<Ocorrencia> ocorrencias = FXCollections.observableArrayList();

    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    private Dispositivo dispositivo;
    private Ocorrencia ocorrencia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            dispositivo = (Dispositivo) apPrincipal.getUserData();
            ocorrencias.setAll(new OcorrenciaDAO().pegarPorDispositivo(dispositivo));
        });
        tvOcorrencia.setItems(ocorrencias);
        cbFuncionario.setItems(usuarios);
        usuarios.setAll(new UsuarioDAO().pegarTodos());
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcDataExecucao.setCellValueFactory((TableColumn.CellDataFeatures<Ocorrencia, Date> param) -> new SimpleObjectProperty<>(param.getValue().getManutencao().getDataPrevista()));
        tcDataOcorrencia.setCellValueFactory(new PropertyValueFactory<>("dataRealizada"));
        tcFuncionario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        tcOcorrencia.setCellValueFactory(new PropertyValueFactory<>("ocorrencia"));
        tcDataExecucao.setCellFactory((TableColumn<Ocorrencia, Date> param) -> new TableCell<Ocorrencia, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(Formatter.toDate(item));
                }
            }

        });
        tcDataOcorrencia.setCellFactory((TableColumn<Ocorrencia, Date> param) -> new TableCell<Ocorrencia, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(Formatter.toDate(item));
                }
            }

        });
    }

    @FXML
    private void taOcorrenciaKeyRelease(KeyEvent keyEvent) {
        if (taOcorrencia.getLength() >= 100) {
            taOcorrencia.setText(taOcorrencia.getText().substring(0, 100));
            taOcorrencia.selectRange(taOcorrencia.getText().length(), taOcorrencia.getText().length());
        }
        if (taDescricaoCiclo.getLength() >= 100) {
            taDescricaoCiclo.setText(taDescricaoCiclo.getText().substring(0, 100));
            taDescricaoCiclo.selectRange(taDescricaoCiclo.getText().length(), taDescricaoCiclo.getText().length());
        }
    }

    @FXML
    private void tvOcorrenciaMouseReleased(MouseEvent mouseEvent) {
        ocorrencia = tvOcorrencia.getSelectionModel().getSelectedItem();
        if (ocorrencia != null) {
            dpDataRodada.setValue(Formatter.toLocalDate(ocorrencia.getManutencao().getCiclo().getInicio()));
            dpDataProgramada.setValue(Formatter.toLocalDate(ocorrencia.getManutencao().getDataPrevista()));
            dpDataExecucao.setValue(Formatter.toLocalDate(ocorrencia.getDataRealizada()));
            taOcorrencia.setText(ocorrencia.getOcorrencia());
            taDescricaoCiclo.setText(ocorrencia.getManutencao().getCiclo().getObservacao());
            cbFuncionario.getSelectionModel().select(ocorrencia.getUsuario());
        } else {
            dpDataExecucao.setValue(null);
            dpDataProgramada.setValue(null);
            dpDataExecucao.setValue(null);
            tfNomeManutencao.clear();
            taOcorrencia.clear();
            taDescricaoCiclo.clear();
            cbFuncionario.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void btAtualizarOcorrenciaActionEvent(ActionEvent actionEvent) {
        if (ocorrencia != null) {
            Ciclo ciclo = ocorrencia.getManutencao().getCiclo();
            ciclo.setObservacao(taDescricaoCiclo.getText());
            ocorrencia.getManutencao().setDataPrevista(Formatter.toDate(dpDataProgramada.getValue()));
            ocorrencia.setDataRealizada(Formatter.toDate(dpDataExecucao.getValue()));
            ocorrencia.setOcorrencia(taOcorrencia.getText());
            ocorrencia.setUsuario(cbFuncionario.getSelectionModel().getSelectedItem());
            if (ocorrencia.getDataRealizada() == null) {
                Message.showMessage("Data de execução não pode ser nulla ou vazia", Alert.AlertType.ERROR);
            } else if (ocorrencia.getOcorrencia().isEmpty()) {
                Message.showMessage("Ocorrência não pode ser em branco/vazia", Alert.AlertType.ERROR);
            } else if (ocorrencia.getManutencao().getDataPrevista() == null) {
                Message.showMessage("Data de programada não pode ser nulla ou vazia", Alert.AlertType.ERROR);
            } else if (ocorrencia.getUsuario() == null) {
                Message.showMessage("Usuário não pode ser nulla ou vazia", Alert.AlertType.ERROR);
            } else {
                long dataProgramada = Formatter.toDate(dpDataProgramada.getValue()).getTime();
                long dataExecucao = Formatter.toDate(dpDataExecucao.getValue()).getTime();
                if (ciclo.getInicio().getTime() <= dataExecucao && ciclo.getFim().getTime() >= dataExecucao) {
                    if (ciclo.getInicio().getTime() <= dataProgramada && ciclo.getFim().getTime() >= dataProgramada) {
                        new CicloDAO().editar(ciclo);
                        new ManutencaoDAO().editar(ocorrencia.getManutencao());
                        new OcorrenciaDAO().editar(ocorrencia);
                        ocorrencias.setAll(new OcorrenciaDAO().pegarPorDispositivo(dispositivo));
                    }else{
                        Message.showMessage("Data programada fora do ciclo", Alert.AlertType.ERROR);
                    }
                } else {
                    Message.showMessage("Data execução fora do ciclo", Alert.AlertType.ERROR);
                }

            }
        } else {
            Message.showMessage("Selecione a ocorrência", Alert.AlertType.ERROR);
        }
    }
}
