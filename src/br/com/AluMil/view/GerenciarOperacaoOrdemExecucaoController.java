/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.dao.OperacaoDAO;
import br.com.AluMil.control.dao.OperacaoOrdemExecucaoDAO;
import br.com.AluMil.control.dao.OrdemExecucaoDAO;
import br.com.AluMil.model.entity.Operacao;
import br.com.AluMil.model.entity.OperacaoOrdemExecucao;
import br.com.AluMil.model.entity.OperacaoOrdemExecucao.OperacaoOrdemExecucaoId;
import br.com.AluMil.model.entity.OrdemExecucao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarOperacaoOrdemExecucaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
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

    @FXML
    private TableView<Operacao> tvOperacao;
    @FXML
    private TableColumn<Operacao, Operacao> tcHabilitado;
    @FXML
    private TableColumn tcOperacao;

    private ObservableList<OrdemExecucao> ordemExecucaos = FXCollections.observableArrayList();
    private ObservableList<Operacao> operacoes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tcId.setCellValueFactory(new PropertyValueFactory("id"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory("checkList"));
        tcInicio.setCellValueFactory(new PropertyValueFactory("inicio"));
        tcFim.setCellValueFactory(new PropertyValueFactory("fim"));
        tcPeriodicidade.setCellValueFactory(new PropertyValueFactory("periodicidade"));
        tvOrdemExecucao.setItems(ordemExecucaos);
        tvOperacao.setItems(operacoes);
        ordemExecucaos.setAll(new OrdemExecucaoDAO().pegarTodos());
        tvOrdemExecucao.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends OrdemExecucao> observable, OrdemExecucao oldValue, OrdemExecucao newValue) -> {
            if (newValue != null) {
                operacoes.setAll(new OperacaoDAO().pegarPorMaquina(newValue.getCheckList().getMaquina()));
            }
        });
        tcHabilitado.setCellValueFactory((TableColumn.CellDataFeatures<Operacao, Operacao> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcOperacao.setCellValueFactory(new PropertyValueFactory("descricao"));
        tcHabilitado.setCellFactory((TableColumn<Operacao, Operacao> param) -> new TableCell<Operacao, Operacao>() {
            @Override
            protected void updateItem(Operacao item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                    setText("");
                } else {
                    CheckBox checkBox = new CheckBox();
                    OperacaoOrdemExecucao operacaoOrdemExecucao = new OperacaoOrdemExecucaoDAO().pegarPorId(new OperacaoOrdemExecucaoId(item, tvOrdemExecucao.getSelectionModel().getSelectedItem()));
                    checkBox.setSelected(operacaoOrdemExecucao != null);
                    checkBox.setOnAction((ActionEvent ae) -> {
                        if (operacaoOrdemExecucao != null) {
                            new OperacaoOrdemExecucaoDAO().excluir(operacaoOrdemExecucao);
                        } else {
                            OperacaoOrdemExecucao novoOperacaoOrdemExecucao = new OperacaoOrdemExecucao();
                            novoOperacaoOrdemExecucao.setId(new OperacaoOrdemExecucaoId(item, tvOrdemExecucao.getSelectionModel().getSelectedItem()));
                            new OperacaoOrdemExecucaoDAO().cadastrar(novoOperacaoOrdemExecucao);
                        }
                        operacoes.setAll(new OperacaoDAO().pegarPorMaquina(tvOrdemExecucao.getSelectionModel().getSelectedItem().getCheckList().getMaquina()));
                    });
                    setGraphic(checkBox);
                }
            }
        });
    }

}
