/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.MaquinaDAO;
import br.com.AluMil.control.dao.OperacaoDAO;
import br.com.AluMil.model.entity.Maquina;
import br.com.AluMil.model.entity.Operacao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarOperacaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfDescricao;
    @FXML
    private ComboBox<Operacao.Tipo> cbTipoOperacao;
    @FXML
    private ComboBox<Maquina> cbEquipamento;
    @FXML
    private TableView<Operacao> tvOperacao;
    @FXML
    private TableColumn tcId;
    @FXML
    private TableColumn tcDescricao;
    @FXML
    private TableColumn tcTipo;
    @FXML
    private TableColumn tcEquipamento;

    private ObservableList<Operacao> operacoes = FXCollections.observableArrayList();
    private ObservableList<Maquina> maquinas = FXCollections.observableArrayList();
    private ObservableList<Operacao.Tipo> tiposOperacao = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Platform.runLater(() -> {
//            cbEquipamento.setItems(maquinas);
//            cbTipoOperacao.setItems(tiposOperacao);
//            tvOperacao.setItems(operacoes);
//            operacoes.setAll(new OperacaoDAO().pegarTodos());
//            maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
//            tiposOperacao.setAll(Operacao.Tipo.values());
//            tcId.setCellValueFactory(new PropertyValueFactory("id"));
//            tcDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
//            tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
//            tcEquipamento.setCellValueFactory(new PropertyValueFactory("maquina"));
//        });
        cbEquipamento.setItems(maquinas);
        cbTipoOperacao.setItems(tiposOperacao);
        tvOperacao.setItems(operacoes);
        operacoes.setAll(new OperacaoDAO().pegarTodos());
        maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
        tiposOperacao.setAll(Operacao.Tipo.values());
        tcId.setCellValueFactory(new PropertyValueFactory("id"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        tcEquipamento.setCellValueFactory(new PropertyValueFactory("maquina"));
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        Operacao operacao = new Operacao();
        operacao.setDescricao(tfDescricao.getText());
        operacao.setMaquina(cbEquipamento.getSelectionModel().getSelectedItem());
        operacao.setTipo(cbTipoOperacao.getSelectionModel().getSelectedItem());
        if (operacao.getDescricao().isEmpty()) {
            Message.showMessage("Descrição não pode estar vazia!", Alert.AlertType.ERROR);
        } else if (operacao.getTipo() == null) {
            Message.showMessage("Selecione o tipo de operação", Alert.AlertType.ERROR);
        } else if (operacao.getMaquina() == null) {
            Message.showMessage("Selecione o equipamento", Alert.AlertType.ERROR);
        } else {
            new OperacaoDAO().cadastrar(operacao);
            tfDescricao.clear();
            cbEquipamento.getSelectionModel().clearSelection();
            cbTipoOperacao.getSelectionModel().clearSelection();
            Message.showMessage("Operação cadastrada com sucesso!", Alert.AlertType.INFORMATION);
        }
        operacoes.setAll(new OperacaoDAO().pegarTodos());
    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        Operacao operacao = tvOperacao.getSelectionModel().getSelectedItem();
        if (operacao != null) {
            new OperacaoDAO().excluir(operacao);
        } else {
            Message.showMessage("Selecione a operação", Alert.AlertType.ERROR);
        }
        operacoes.setAll(new OperacaoDAO().pegarTodos());
    }

}
