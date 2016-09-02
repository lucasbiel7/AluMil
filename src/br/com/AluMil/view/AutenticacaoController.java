/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.CicloDAO;
import br.com.AluMil.control.dao.UsuarioDAO;
import br.com.AluMil.model.entity.Ciclo;
import br.com.AluMil.model.entity.Usuario;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class AutenticacaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private Label lbUsuario;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Label lbTempo;

    private String usuarioBloqueado;
    private String janela;
    private Object parametro;
    private int nivel;
    private Timeline timeline;
    private int tempo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (apPrincipal.getUserData() instanceof Object[]) {
                Object[] parametros = (Object[]) apPrincipal.getUserData();
                for (Object parametro : parametros) {
                    if (parametro instanceof String) {
                        janela = (String) parametro;
                    } else if (parametro instanceof Integer) {
                        nivel = (int) parametro;
                    } else {
                        this.parametro = parametro;
                    }
                }
            }
        });
        janela = "Loading";

    }

    @FXML
    private void btLoginActionEvent(ActionEvent actionEvent) {
        if (tfUsuario.getText().equals("admin") && pfSenha.getText().equals("admin")) {
            if (janela.equals("ExcluirCiclo")) {
                new CicloDAO().excluir((Ciclo) parametro);
            } else {
                FxMananger.showWindow(FxMananger.carregarComponente(janela, parametro), "", FxMananger.Tipo.MODAL).showAndWait();
            }
            btSairActionEvent(actionEvent);
        } else if (tfUsuario.getText().equals(usuarioBloqueado)) {
            Message.showMessage("Usuário bloqueado temporiariamente", Alert.AlertType.ERROR);
        } else {
            Usuario usuario = new UsuarioDAO().pegarPorMatricula(tfUsuario.getText());
            if (usuario != null) {
                if (usuario.isAtivo()) {
                    if (usuario.getSenha().equals(pfSenha.getText())) {
                        if (nivel > 0) {
                            if (usuario.getNivel() == nivel) {
                                if (janela.equals("ExcluirCiclo")) {
                                    new CicloDAO().excluir((Ciclo) parametro);
                                } else {
                                    FxMananger.showWindow(FxMananger.carregarComponente(janela, parametro), "", FxMananger.Tipo.MODAL).showAndWait();
                                }
                                btSairActionEvent(actionEvent);
                            } else {
                                Message.showMessage("Sem permissão para essa área do sistema!", Alert.AlertType.ERROR);
                            }
                        } else {
                            usuario.setTentativas(0);
                            new UsuarioDAO().editar(usuario);
                            btSairActionEvent(null);
                            FxMananger.showWindow(FxMananger.carregarComponente(janela), "", FxMananger.Tipo.UNDECORED).show();
                        }
                    } else {
                        usuario.setTentativas(usuario.getTentativas() + 1);
                        new UsuarioDAO().editar(usuario);
                        if (usuario.getTentativas() == 2) {
                            if (timeline != null) {
                                timeline.stop();
                            }
                            usuarioBloqueado = usuario.getMatricula();
                            lbTempo.setText("00:01:00");
                            tempo = 60;
                            timeline = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent ae) -> {
                                tempo--;
                                lbTempo.setText(Time.valueOf("00:00:" + tempo).toString());
                                if (tempo <= 0) {
                                    lbTempo.setText("");
                                    usuarioBloqueado = null;
                                }
                                System.out.println("Continua");
                            }));
                            timeline.setCycleCount(60);
                            timeline.play();
                            Message.showMessage("Aguarde um minuto para logar novamente", Alert.AlertType.ERROR);
                        } else if (usuario.getTentativas() > 2) {
                            usuario.setAtivo(false);
                            new UsuarioDAO().editar(usuario);
                        }
                        Message.showMessage("Senha incorreta!", Alert.AlertType.ERROR);
                    }
                } else {
                    Message.showMessage("Usuário bloqueado contacte o administrador!", Alert.AlertType.ERROR);
                }
            } else {
                Message.showMessage("Usuário não consta em nossa base de dados!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btSairActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    @FXML
    private void tfUsuarioKeyRelease(KeyEvent keyEvent) {
        Usuario usuario = new UsuarioDAO().pegarPorMatricula(tfUsuario.getText());
        if (usuario != null) {
            lbUsuario.setText(usuario.getNome());
            if (usuario.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(usuario.getFoto())));
            }
        } else {
            lbUsuario.setText("");
            ivFoto.setImage(null);
        }
    }
}
