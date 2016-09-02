/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.IntegerSpinner;
import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.CheckListDAO;
import br.com.AluMil.control.dao.OrdemExecucaoDAO;
import br.com.AluMil.model.entity.CheckList;
import br.com.AluMil.model.entity.OrdemExecucao;
import br.com.AluMil.model.entity.OrdemExecucao.Periodicidade;
import br.com.AluMil.model.util.Formatter;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarOrdemExecucaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<CheckList> cbCheckList;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private Spinner<Integer> spNumeroVezes;
    @FXML
    private ComboBox<OrdemExecucao.Periodicidade> cbPeriodicidade;
    @FXML
    private TableView<OrdemExecucao> tvOrdemExecucao;
    @FXML
    private TableColumn tcId;
    @FXML
    private TableColumn tcDescricao;
    @FXML
    private TableColumn tcPeriodicidade;
    @FXML
    private TableColumn tcInicio;
    @FXML
    private TableColumn tcFim;

    private ObservableList<OrdemExecucao> ordemExecucaos = FXCollections.observableArrayList();
    private ObservableList<OrdemExecucao.Periodicidade> periodicidades = FXCollections.observableArrayList();
    private ObservableList<CheckList> checkLists = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbCheckList.setItems(checkLists);
        cbPeriodicidade.setItems(periodicidades);
        tvOrdemExecucao.setItems(ordemExecucaos);
        checkLists.setAll(new CheckListDAO().pegarTodos());
        periodicidades.setAll(Periodicidade.values());
        ordemExecucaos.setAll(new OrdemExecucaoDAO().pegarTodos());
        tcId.setCellValueFactory(new PropertyValueFactory("id"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory("checkList"));
        tcInicio.setCellValueFactory(new PropertyValueFactory("inicio"));
        tcFim.setCellValueFactory(new PropertyValueFactory("fim"));
        tcPeriodicidade.setCellValueFactory(new PropertyValueFactory("periodicidade"));
        spNumeroVezes.setValueFactory(new IntegerSpinner(1, 1));
        spNumeroVezes.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            if (dpInicio.getValue() != null && cbPeriodicidade.getSelectionModel().getSelectedItem() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Formatter.toDate(dpInicio.getValue()));
                calendar.add(Calendar.DAY_OF_MONTH, spNumeroVezes.getValue() * cbPeriodicidade.getSelectionModel().getSelectedItem().getDias());
                dpFim.setValue(Formatter.toLocalDate(calendar.getTime()));
            }
        });

    }

    @FXML
    private void cbPeriodicidadeActionEvent(ActionEvent actionEvent) {
        if (cbPeriodicidade.getSelectionModel().getSelectedItem() != null) {
            Periodicidade periodicidade = cbPeriodicidade.getSelectionModel().getSelectedItem();
            switch (periodicidade) {
                case DIARIO:
                    spNumeroVezes.setValueFactory(new IntegerSpinner(1, 30));
                    break;
                case MENSAL:
                    spNumeroVezes.setValueFactory(new IntegerSpinner(1, 1));
                    break;
                case SEMANAL:
                    spNumeroVezes.setValueFactory(new IntegerSpinner(1, 4));
                    break;
            }
            if (dpInicio.getValue() != null && cbPeriodicidade.getSelectionModel().getSelectedItem() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Formatter.toDate(dpInicio.getValue()));
                calendar.add(Calendar.DAY_OF_MONTH, spNumeroVezes.getValue() * cbPeriodicidade.getSelectionModel().getSelectedItem().getDias());
                dpFim.setValue(Formatter.toLocalDate(calendar.getTime()));
            }
        }
        spNumeroVezes.setDisable(cbPeriodicidade.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        OrdemExecucao ordemExecucao = new OrdemExecucao();
        ordemExecucao.setInicio(Formatter.toDate(dpInicio.getValue()));
        ordemExecucao.setFim(Formatter.toDate(dpFim.getValue()));
        ordemExecucao.setCheckList(cbCheckList.getSelectionModel().getSelectedItem());
        ordemExecucao.setPeriodicidade(cbPeriodicidade.getSelectionModel().getSelectedItem());
        ordemExecucao.setQuantidade(spNumeroVezes.getValue());
        if (ordemExecucao.getCheckList() == null) {
            Message.showMessage("Selecione a manutenção", Alert.AlertType.ERROR);
        } else if (ordemExecucao.getInicio() == null) {
            Message.showMessage("Selecione a data de inicio", Alert.AlertType.ERROR);
        } else if (ordemExecucao.getPeriodicidade() == null) {
            Message.showMessage("Selecione a periodicidade", Alert.AlertType.ERROR);
        } else {
            new OrdemExecucaoDAO().cadastrar(ordemExecucao);
            dpInicio.setValue(null);
            dpFim.setValue(null);
            cbCheckList.getSelectionModel().clearSelection();
            cbPeriodicidade.getSelectionModel().clearSelection();

        }
        ordemExecucaos.setAll(new OrdemExecucaoDAO().pegarTodos());
    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        OrdemExecucao ordemExecucao = tvOrdemExecucao.getSelectionModel().getSelectedItem();
        if (ordemExecucao != null) {
            new OrdemExecucaoDAO().excluir(ordemExecucao);
        }
        ordemExecucaos.setAll(new OrdemExecucaoDAO().pegarTodos());
    }

}
