/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.UsuarioDAO;
import br.com.AluMil.model.entity.Usuario;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarUsuarioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfMatricula;
    @FXML
    private ComboBox<String> cbEquipe;
    @FXML
    private ComboBox<String> cbCargo;
    @FXML
    private ComboBox<String> cbSetor;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private PasswordField pfConfirmaSenha;
    @FXML
    private TableView<Usuario> tvUsuario;
    @FXML
    private TableColumn<Usuario, byte[]> tcFoto;
    @FXML
    private TableColumn tcId;
    @FXML
    private TableColumn tcNome;
    @FXML
    private TableColumn<Usuario, Integer> tcEquipe;
    @FXML
    private TableColumn tcMatricula;
    @FXML
    private TableColumn<Usuario, Boolean> tcAtivo;
    @FXML
    private TableColumn tcSetor;
    @FXML
    private ImageView ivFoto;
    @FXML
    private Button btConfirmar;
    @FXML
    private Button btCancelar;
    @FXML
    private RadioButton rbAtivoSim;
    @FXML
    private RadioButton rbAtivoNao;

    private ObservableList<String> equipes = FXCollections.observableArrayList();
    private ObservableList<String> cargos = FXCollections.observableArrayList();
    private ObservableList<String> setores = FXCollections.observableArrayList();
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();

    private FileChooser fcImagem;
    private Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEquipe.setItems(equipes);
        cbCargo.setItems(cargos);
        cbSetor.setItems(setores);
        tvUsuario.setItems(usuarios);
        usuarios.setAll(new UsuarioDAO().pegarTodos());
        equipes.setAll("Comun", "Administrador");
        setores.setAll("Aprendiz", "Técnico", "Engenheiro", "Supervisor", "Gerente");
        cargos.setAll("Produção", "Manutenção", "Administração", "Gerência");
        fcImagem = new FileChooser();
        fcImagem.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.jpg", "*.png", "*.jpeg", "*.gif"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));
        tcAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        tcEquipe.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        tcFoto.setCellFactory((TableColumn<Usuario, byte[]> param) -> new TableCell<Usuario, byte[]>() {
            @Override
            protected void updateItem(byte[] item, boolean empty) {
                if (empty) {
                    setText("");
                    setGraphic(null);
                } else if (item != null) {
                    ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(item)));
                    imageView.setFitHeight(50);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);
                    setGraphic(imageView);
                    setText("");
                } else {
                    setText("Sem foto");
                    setGraphic(null);
                }

            }
        });
        tcEquipe.setCellFactory((TableColumn<Usuario, Integer> param) -> new TableCell<Usuario, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(item == 1 ? "Comun" : "Administrador");
                }
            }
        });
        tcAtivo.setCellFactory((TableColumn<Usuario, Boolean> param) -> new TableCell<Usuario, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    checkBox.setDisable(true);
                    setGraphic(checkBox);
                }
            }
        });

    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        tfNome.clear();
        tfMatricula.clear();
        cbEquipe.getSelectionModel().clearSelection();
        cbCargo.getSelectionModel().clearSelection();
        cbSetor.getSelectionModel().clearSelection();
        pfSenha.clear();
        pfConfirmaSenha.clear();
        ivFoto.setImage(null);
        usuario = new Usuario();
        btConfirmar.setDisable(false);
        btCancelar.setDisable(false);
    }

    @FXML
    private void btAlterarActionEvent(ActionEvent actionEvent) {
        if (tvUsuario.getSelectionModel().getSelectedItem() != null) {
            usuario = tvUsuario.getSelectionModel().getSelectedItem();
            tfNome.setText(usuario.getNome());
            tfMatricula.setText(usuario.getMatricula());
            cbEquipe.getSelectionModel().select(usuario.getNivel() == 1 ? "Comun" : "Administrador");
            cbCargo.getSelectionModel().select(usuario.getCargo());
            cbSetor.getSelectionModel().select(usuario.getSetor());
            pfSenha.setText(usuario.getSenha());
            pfConfirmaSenha.setText(usuario.getSenha());
            rbAtivoSim.setSelected(usuario.isAtivo());
            rbAtivoNao.setSelected(!usuario.isAtivo());
            if (usuario.getFoto() != null) {
                ivFoto.setImage(new Image(new ByteArrayInputStream(usuario.getFoto())));
            }
            btConfirmar.setDisable(false);
            btCancelar.setDisable(false);
        } else {
            Message.showMessage("Selecione um funcionario", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btConfirmarActionEvent(ActionEvent actionEvent) {
        usuario.setNome(tfNome.getText());
        usuario.setAtivo(rbAtivoSim.isSelected());
        usuario.setCargo(cbCargo.getSelectionModel().getSelectedItem());
        usuario.setMatricula(tfMatricula.getText());
        usuario.setNivel(cbEquipe.getSelectionModel().getSelectedIndex() + 1);
        usuario.setSenha(pfSenha.getText());
        usuario.setSetor(cbSetor.getSelectionModel().getSelectedItem());
        if (usuario.getSenha().matches("^((\\d){6})$")) {
            if (usuario.getSenha().equals(pfConfirmaSenha.getText())) {
                if (usuario.getNome().isEmpty() || usuario.getMatricula().isEmpty() || usuario.getCargo() == null || usuario.getSetor() == null || usuario.getFoto() == null) {
                    Message.showMessage("Todos campos são obrigatorios", Alert.AlertType.ERROR);
                } else if (usuario.getId() == null) {
                    new UsuarioDAO().cadastrar(usuario);
                    Message.showMessage("Usuário cadastrado com sucesso!", Alert.AlertType.INFORMATION);
                } else {
                    new UsuarioDAO().editar(usuario);
                    Message.showMessage("Usuário editado com sucesso!", Alert.AlertType.INFORMATION);
                }
                usuarios.setAll(new UsuarioDAO().pegarTodos());
                btConfirmar.setDisable(true);
                btCancelar.setDisable(true);
            } else {
                Message.showMessage("Confirmação da senha deve ser identica a sua senha de acesso", Alert.AlertType.ERROR);
            }
        } else {
            Message.showMessage("A senha deve conter 6 digitos(0-9)", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        if (tvUsuario.getSelectionModel().getSelectedItem() != null) {
            new UsuarioDAO().excluir(tvUsuario.getSelectionModel().getSelectedItem());
            usuarios.setAll(new UsuarioDAO().pegarTodos());
            Message.showMessage("Excluido com sucesso", Alert.AlertType.INFORMATION);
        } else {
            Message.showMessage("Selecione um funcionario", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        btConfirmar.setDisable(true);
        btCancelar.setDisable(true);
    }

    @FXML
    private void btAdicionarFotoActionEvent(ActionEvent actionEvent) {
        if (usuario != null) {
            File file = fcImagem.showOpenDialog(apPrincipal.getScene().getWindow());

            if (file != null) {
                try {
                    usuario.setFoto(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                    ivFoto.setImage(new Image(new ByteArrayInputStream(usuario.getFoto())));
                } catch (IOException ex) {
                    Logger.getLogger(GerenciarUsuarioController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                ivFoto.setImage(null);
                usuario = null;
            }
        } else {
            Message.showMessage("Você deve selecionar editar ou novo para adicionar/editar foto", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btAlterarFotoActionEvent(ActionEvent actionEvent) {
        if (usuario != null) {
            File file = fcImagem.showOpenDialog(apPrincipal.getScene().getWindow());

            if (file != null) {
                try {
                    usuario.setFoto(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                    ivFoto.setImage(new Image(new ByteArrayInputStream(usuario.getFoto())));
                } catch (IOException ex) {
                    Logger.getLogger(GerenciarUsuarioController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                ivFoto.setImage(null);
                usuario = null;
            }
        } else {
            Message.showMessage("Você deve selecionar editar ou novo para adicionar/editar foto", Alert.AlertType.ERROR);
        }
    }

}
