/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.MaquinaDAO;
import br.com.AluMil.model.entity.Maquina;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class GerenciarEquipamentoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TableView<Maquina> tvMaquina;
    @FXML
    private TableColumn tcId;
    @FXML
    private TableColumn tcNome;
    @FXML
    private TextField tfNome;

    private ObservableList<Maquina> maquinas = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvMaquina.setItems(maquinas);
        maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
        tcId.setCellValueFactory(new PropertyValueFactory("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory("nome"));
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        Maquina maquina = new Maquina();
        maquina.setAtivo(true);
        maquina.setNome(tfNome.getText());
        if (maquina.getNome().isEmpty()) {
            Message.showMessage("Campo nome obrigatório", Alert.AlertType.ERROR);
        } else {
            new MaquinaDAO().cadastrar(maquina);
            tfNome.clear();
            maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
            Message.showMessage("Equipamento cadastrado com sucesso", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        if (tvMaquina.getSelectionModel().getSelectedItem() != null) {
            Maquina maquina = tvMaquina.getSelectionModel().getSelectedItem();
            maquina.setAtivo(false);
            new MaquinaDAO().excluir(maquina);
            maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
        }
    }

}
