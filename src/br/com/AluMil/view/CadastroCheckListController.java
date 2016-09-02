/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.CheckListDAO;
import br.com.AluMil.control.dao.MaquinaDAO;
import br.com.AluMil.model.entity.CheckList;
import br.com.AluMil.model.entity.Maquina;
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
public class CadastroCheckListController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TableView<CheckList> tvCheckList;
    @FXML
    private TableColumn tcId;
    @FXML
    private TableColumn tcNome;
    @FXML
    private TableColumn tcEquipamento;
    @FXML
    private ComboBox<Maquina> cbEquipamento;
    @FXML
    private TextField tfDescricao;

    private ObservableList<Maquina> maquinas = FXCollections.observableArrayList();
    private ObservableList<CheckList> checkLists = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvCheckList.setItems(checkLists);
        cbEquipamento.setItems(maquinas);
        maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
        checkLists.setAll(new CheckListDAO().pegarTodos());
        tcId.setCellValueFactory(new PropertyValueFactory("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory("descricao"));
        tcEquipamento.setCellValueFactory(new PropertyValueFactory("maquina"));
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        CheckList checkList = new CheckList();
        checkList.setDescricao(tfDescricao.getText());
        checkList.setMaquina(cbEquipamento.getSelectionModel().getSelectedItem());
        if (checkList.getDescricao().isEmpty()) {
            Message.showMessage("Descrição está vazia!", Alert.AlertType.ERROR);
        } else if (checkList.getMaquina() == null) {
            Message.showMessage("Selecione o dispositivo", Alert.AlertType.ERROR);
        } else {
            new CheckListDAO().cadastrar(checkList);
            tfDescricao.clear();
            cbEquipamento.getSelectionModel().clearSelection();
        }
        checkLists.setAll(new CheckListDAO().pegarTodos());
    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        CheckList checkList = tvCheckList.getSelectionModel().getSelectedItem();
        if (checkList != null) {
            new CheckListDAO().excluir(checkList);
        } else {
            Message.showMessage("Selecione o checklist na tabela para excluir!", Alert.AlertType.ERROR);
        }
        checkLists.setAll(new CheckListDAO().pegarTodos());
    }

}
