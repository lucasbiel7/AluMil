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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LixeiraMaquinaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TableView<Maquina> tvMaquina;
    @FXML
    private TableColumn<Maquina, String> tcNome;

    private ObservableList<Maquina> maquinas = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvMaquina.setItems(maquinas);
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        maquinas.setAll(new MaquinaDAO().pegarPorAtivo(false));
        tvMaquina.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void btRestaurarActionEvent(ActionEvent actionEvent) {
        for (Maquina maquina : tvMaquina.getSelectionModel().getSelectedItems()) {
            if (new MaquinaDAO().pegarPorNome(maquina.getNome()).isEmpty()) {
                maquina.setAtivo(true);
                new MaquinaDAO().editar(maquina);
            } else {
                do {
                    maquina.setNome(Message.showInputDialog("Digite um novo nome para a máquina"));
                } while (!new MaquinaDAO().pegarPorNome(maquina.getNome()).isEmpty() || maquina.getNome().isEmpty());
                maquina.setAtivo(true);
                new MaquinaDAO().editar(maquina);
            }
        }
        maquinas.setAll(new MaquinaDAO().pegarPorAtivo(false));
    }

}
