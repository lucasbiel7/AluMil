/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import br.com.AluMil.control.FxMananger.Tipo;
import br.com.AluMil.control.Message;
import br.com.AluMil.control.Sistema;
import br.com.AluMil.control.dao.MaquinaDAO;
import br.com.AluMil.model.entity.Maquina;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GestaoMaquinaController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private GridPane gpMaquinas;
    @FXML
    private Button btValidar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listarMaquinas();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent ae) -> {
            List<String> texto = new ArrayList<>();
            if (new File("conf/backup.conf").isFile()) {
                try {
                    texto = Files.readAllLines(Paths.get("conf/backup.conf"), Charset.defaultCharset());
                    if (texto.isEmpty()) {
                    } else {
                        Time time = Time.valueOf(LocalTime.now());
                        if (time.equals(Time.valueOf(texto.get(0)))) {
                            try {
                                new File("backup/").mkdir();
                                Date date = new Date();
                                Runtime.getRuntime().exec("cmd /c mysqldump -uroot -pOC2015 --database alumil> backup/" + new SimpleDateFormat("ddMMyyyy").format(date) + "_" + new SimpleDateFormat("HHmmss").format(date) + ".sql");
                            } catch (IOException ex) {
                                Logger.getLogger(GerenciarBackupController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GerenciarBackupController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        validar();
    }

    @FXML
    private void btNovaMaquinaActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("Autenticacao", new Object[]{"NovaMaquina", 1}), "Confirmação de usuário administrador", FxMananger.Tipo.MODAL).showAndWait();
        listarMaquinas();
    }

    @FXML
    private void btLixeiraActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("LixeiraMaquina"), "Confirmação de usuário administrador", FxMananger.Tipo.MODAL).showAndWait();
        listarMaquinas();
    }

    @FXML
    private void btSairActionEvent(ActionEvent actionEvent) {
        if (Message.showConfirm("Você deseja sair do sistema")) {
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    private void btLixeiraDragEventOver(DragEvent de) {
        if (de.getGestureSource() instanceof Button) {
            de.acceptTransferModes(TransferMode.MOVE);
        }
    }

    @FXML
    private void btLixeiraDragEventDropped(DragEvent de) {
        if (de.getGestureSource() instanceof Button) {
            Button button = (Button) de.getGestureSource();
            if (button.getUserData() instanceof Maquina) {
                Maquina maquina = (Maquina) button.getUserData();
                if (Message.showConfirm("Você realmente deseja excluir a máquina " + maquina.getNome() + "?")) {
                    maquina.setAtivo(false);
                    new MaquinaDAO().editar(maquina);
                    listarMaquinas();
                }
            }
        }
    }

    @FXML
    private void btBackupActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("GerenciarBackup"), "Gerenciamento de backup", FxMananger.Tipo.MODAL).showAndWait();
    }

    @FXML
    private void btGraficoActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("Grafico"), "TMP- Tempo total de manutenção", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.MODAL).showAndWait();
    }

    @FXML
    private void btRelatorioActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("Relatorio"), "Gestão visual TMP - Relatório", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.MODAL).showAndWait();
    }

    @FXML
    private void btCheckListActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("GerenciarCheckList"), "Gerenciar Check list", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.MODAL).showAndWait();
        listarMaquinas();
    }

    @FXML
    private void btGerenciarUsuarioActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("GerenciarUsuario"), "Funcionários", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.MODAL).showAndWait();
    }

    @FXML
    private void btControleDeAcessoActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("ControleDeAcesso"), "Controle de acesso", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.MODAL).showAndWait();
    }

    @FXML
    private void btValidarActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("ValidarSoftware"), "Registro de software", FxMananger.Tipo.MODAL).showAndWait();
        validar();
    }

    public void validar() {
        desabilitarBotoes(true, apPrincipal.getChildren().toArray(new Node[]{}));
        btValidar.setDisable(false);
        new File("conf/").mkdir();
        if (new File("conf/registro.conf").isFile()) {
            try {
                List<String> dados = Files.readAllLines(Paths.get("conf/registro.conf"), Charset.defaultCharset());
                if (!dados.isEmpty()) {
                    if (dados.get(0).equals(Sistema.gerarCodigoAtivacao())) {
                        desabilitarBotoes(false, apPrincipal.getChildren().toArray(new Node[]{}));
                        btValidar.setVisible(false);
                    }
                }
            } catch (IOException ex) {
            }
        }
    }

    public void desabilitarBotoes(boolean desabilitar, Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof Button) {
                node.setDisable(desabilitar);
            } else if (node instanceof AnchorPane) {
                AnchorPane componente = (AnchorPane) node;
                desabilitarBotoes(desabilitar, componente.getChildren().toArray(new Node[]{}));
            } else if (node instanceof VBox) {
                VBox componente = (VBox) node;
                desabilitarBotoes(desabilitar, componente.getChildren().toArray(new Node[]{}));
            } else if (node instanceof HBox) {
                HBox componente = (HBox) node;
                desabilitarBotoes(desabilitar, componente.getChildren().toArray(new Node[]{}));
            } else if (node instanceof GridPane) {
                GridPane componente = (GridPane) node;
                desabilitarBotoes(desabilitar, componente.getChildren().toArray(new Node[]{}));
            } else if (node instanceof ScrollPane) {
                ScrollPane componente = (ScrollPane) node;
                desabilitarBotoes(desabilitar, componente.getContent());
            }
        }
    }

    @FXML
    private void btRelatoriosGerenciasActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("RelatorioGerencial"), "Relatorios gerencias", Tipo.MAXIMIZADO, Tipo.MODAL).showAndWait();
    }

    private void listarMaquinas() {
        gpMaquinas.getChildren().clear();
        int linha = 0;
        int coluna = 0;
        gpMaquinas.setAlignment(Pos.TOP_LEFT);
        for (Maquina maquina : new MaquinaDAO().pegarPorAtivo(true)) {
            Button button = new Button(maquina.getNome());
            ImageView imageView = new ImageView();
            if (maquina.getFoto() != null) {
                imageView.setImage(new Image(new ByteArrayInputStream(maquina.getFoto())));
            }
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);
            imageView.setPreserveRatio(false);
            imageView.setSmooth(true);
            button.setContentDisplay(ContentDisplay.TOP);
            button.setUserData(maquina);
            button.setOnDragDetected((MouseEvent event) -> {
                Dragboard dragboard = button.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putImage(button.snapshot(new SnapshotParameters(), null));
                dragboard.setContent(clipboardContent);
                button.setVisible(false);
            });
            button.setOnDragDone((DragEvent event) -> {
                button.setVisible(true);
            });
            button.setOnAction((ActionEvent event) -> {
                FxMananger.showWindow(FxMananger.carregarComponente("GestaoDispositivos", maquina), "Dispositivos por Máquina/Equipamentos", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.MODAL).showAndWait();
            });
            button.setGraphic(imageView);
            gpMaquinas.add(button, coluna, linha);
            coluna++;
            if (coluna > 6) {
                coluna = 0;
                linha++;
            }
        }
    }
}
