/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.DispositivoDAO;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Maquina;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
public class LixeiraDispositivosController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TableView<Dispositivo> tvDispositivo;
    @FXML
    private TableColumn<Dispositivo, String> tcNome;

    private ObservableList<Dispositivo> dispositivos = FXCollections.observableArrayList();

    private Maquina maquina;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            maquina = (Maquina) apPrincipal.getUserData();
            dispositivos.setAll(new DispositivoDAO().pegarPorMaquinaAtivo(maquina, false));
        });
        tvDispositivo.setItems(dispositivos);
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tvDispositivo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void btRestaurarActionEvent(ActionEvent actionEvent) {
        for (Dispositivo dispositivo : tvDispositivo.getSelectionModel().getSelectedItems()) {
            if (new DispositivoDAO().pegarPorNomeMaquina(dispositivo.getNome(), maquina).isEmpty()) {
                dispositivo.setAtivo(true);
                new DispositivoDAO().editar(dispositivo);
            } else {
                do {
                    dispositivo.setNome(Message.showInputDialog("Digite o novo nome para o dispositivo " + dispositivo.getNome()));
                } while (new DispositivoDAO().pegarPorNomeMaquina(dispositivo.getNome(), maquina).isEmpty() || dispositivo.getNome().isEmpty());
                dispositivo.setAtivo(true);
                new DispositivoDAO().editar(dispositivo);
            }
        }
        dispositivos.setAll(new DispositivoDAO().pegarPorMaquinaAtivo(maquina, false));
    }

}
