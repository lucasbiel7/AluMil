/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.HorarioAcessoDAO;
import br.com.AluMil.control.dao.UsuarioDAO;
import br.com.AluMil.model.entity.HorarioAcesso;
import br.com.AluMil.model.entity.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ControleDeAcessoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ComboBox<Usuario> cbUsuario;
    @FXML
    private GridPane gpDias;
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbUsuario.setItems(usuarios);
        usuarios.setAll(new UsuarioDAO().pegarTodos());

    }

    @FXML
    private void cbUsuarioActionEvent(ActionEvent actionEvent) {
        gpDias.getChildren().clear();
        if (cbUsuario.getSelectionModel().getSelectedItem() != null) {
            Usuario usuario = cbUsuario.getSelectionModel().getSelectedItem();
            gpDias.add(new Label("Segunda-feira"), 0, 1);
            gpDias.add(new Label("Terça-feira"), 0, 2);
            gpDias.add(new Label("Quarta-feira"), 0, 3);
            gpDias.add(new Label("Quinta-feira"), 0, 4);
            gpDias.add(new Label("Sexta-feira"), 0, 5);
            gpDias.add(new Label("Sábado"), 0, 6);
            gpDias.add(new Label("Domingo"), 0, 7);
            gpDias.add(new Label("Feriado"), 0, 8);
            for (int i = 1; i < 25; i++) {
                gpDias.add(new Label(String.valueOf(i)), i, 0);
                for (int j = 1; j < 9; j++) {
                    Button button = new Button();
                    final int dia = j;
                    final int hora = i;
                    button.setOnAction((ActionEvent ae) -> {
                        HorarioAcesso horarioAcesso = new HorarioAcessoDAO().pegarPorUsuarioDiaHora(usuario, dia, hora);
                        if (horarioAcesso == null) {
                            horarioAcesso = new HorarioAcesso();
                            horarioAcesso.setDia(dia);
                            horarioAcesso.setHora(hora);
                            horarioAcesso.setUsuario(usuario);
                            horarioAcesso.setHabilitado(true);
                            new HorarioAcessoDAO().cadastrar(horarioAcesso);
                        } else {
                            horarioAcesso.setHabilitado(!horarioAcesso.isHabilitado());
                            new HorarioAcessoDAO().editar(horarioAcesso);
                        }
                        if (horarioAcesso.isHabilitado()) {
                            button.setBackground(new Background(new BackgroundFill(Color.LIME, CornerRadii.EMPTY, Insets.EMPTY)));
                        } else {
                            button.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    });
                    HorarioAcesso horarioAcesso = new HorarioAcessoDAO().pegarPorUsuarioDiaHora(usuario, dia, hora);
                    if (horarioAcesso == null) {
                        horarioAcesso = new HorarioAcesso();
                        horarioAcesso.setDia(dia);
                        horarioAcesso.setUsuario(usuario);
                        horarioAcesso.setHora(hora);
                        horarioAcesso.setHabilitado(false);
                        new HorarioAcessoDAO().cadastrar(horarioAcesso);
                    }
                    if (horarioAcesso.isHabilitado()) {
                        button.setBackground(new Background(new BackgroundFill(Color.LIME, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        button.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    }

                    button.setPrefSize(30, 30);
                    gpDias.add(button, i, j);
                }
            }
        }
    }

    @FXML
    private void btLiberarTudoActionEvent(ActionEvent actionEvent) {
        Usuario usuario = cbUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            for (HorarioAcesso horarioAcesso : new HorarioAcessoDAO().pegarPorUsuario(usuario)) {
                horarioAcesso.setHabilitado(true);
                new HorarioAcessoDAO().editar(horarioAcesso);
            }
            cbUsuarioActionEvent(actionEvent);
        } else {
            Message.showMessage("Selecione o usuário", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btBloquearTudoActionEvent(ActionEvent actionEvent) {
        Usuario usuario = cbUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            for (HorarioAcesso horarioAcesso : new HorarioAcessoDAO().pegarPorUsuario(usuario)) {
                horarioAcesso.setHabilitado(false);
                new HorarioAcessoDAO().editar(horarioAcesso);
            }
            cbUsuarioActionEvent(actionEvent);
        } else {
            Message.showMessage("Selecione o usuário", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btHorarioComercialActionEvent(ActionEvent actionEvent) {
        Usuario usuario = cbUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            for (HorarioAcesso horarioAcesso : new HorarioAcessoDAO().pegarPorUsuario(usuario)) {
                horarioAcesso.setHabilitado(horarioAcesso.getHora() >= 7 && horarioAcesso.getHora() < 18 && horarioAcesso.getDia() < 6);
                new HorarioAcessoDAO().editar(horarioAcesso);
            }
            cbUsuarioActionEvent(actionEvent);
        } else {
            Message.showMessage("Selecione o usuário", Alert.AlertType.ERROR);
        }
    }
}
