
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.DispositivoDAO;
import br.com.AluMil.control.dao.ManutencaoDAO;
import br.com.AluMil.control.dao.OcorrenciaDAO;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Manutencao;
import br.com.AluMil.model.entity.Maquina;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GestaoDispositivosController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private GridPane gpDispositivos;
    @FXML
    private Label lbTitle;

    private Maquina maquina;

    private PopOver poDetalhes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            maquina = (Maquina) apPrincipal.getUserData();
            lbTitle.setText(lbTitle.getText() + maquina.getNome());
            listarDispositivos();
        });
        poDetalhes = new PopOver();
    }

    @FXML
    private void btVoltarActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    @FXML
    private void btAdicionarDispositivoActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("Autenticacao", new Object[]{"NovoDispositivo", 1, maquina}), "Confirmação de usuário administrador", FxMananger.Tipo.MODAL).showAndWait();
        listarDispositivos();
    }

    @FXML
    private void btLixeiraActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("LixeiraDispositivos", maquina), "Lixeira dos dispositivos da " + maquina.getNome(), FxMananger.Tipo.MODAL).showAndWait();
        listarDispositivos();
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
            if (button.getUserData() instanceof Dispositivo) {
                Dispositivo dispositivo = (Dispositivo) button.getUserData();
                if (Message.showConfirm("Você realmente deseja excluir o dispositivo " + dispositivo.getNome() + "?")) {
                    dispositivo.setAtivo(false);
                    new DispositivoDAO().editar(dispositivo);
                    listarDispositivos();
                }
            }
        }
    }

    private boolean vermelho;

    private void listarDispositivos() {
        gpDispositivos.getChildren().clear();
        int linha = 0;
        int coluna = 0;
        for (Dispositivo dispositivo : new DispositivoDAO().pegarPorMaquinaAtivo(maquina, true)) {
            Button button = new Button(dispositivo.getNome());
            ImageView imageView = new ImageView();
            imageView.setFitHeight(75);
            imageView.setFitWidth(75);
            imageView.setPreserveRatio(false);
            imageView.setSmooth(true);
            if (dispositivo.getFoto() != null) {
                imageView.setImage(new Image(new ByteArrayInputStream(dispositivo.getFoto())));
            }
            button.setOnAction((ActionEvent ae) -> {
                poDetalhes.setContentNode(FxMananger.carregarComponente("VisualizarDispositivo", dispositivo));
                poDetalhes.show(button);
            });
            button.setUserData(dispositivo);
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
            button.setOnMouseReleased((MouseEvent event) -> {
                if (event.isPopupTrigger()) {
                    FxMananger.showWindow(FxMananger.carregarComponente("GerenciarManutencao", dispositivo), dispositivo.getNome() + " " + dispositivo.getMaquina().getNome(), FxMananger.Tipo.MODAL, FxMananger.Tipo.MAXIMIZADO).showAndWait();
                    listarDispositivos();
                }
            });
            List<Manutencao> manutencao = new ManutencaoDAO().pegarPorDispositivoData(dispositivo, new Date());
            manutencao.removeIf((Manutencao t) -> new OcorrenciaDAO().pegarPorManutencao(t) != null);
            if (!manutencao.isEmpty()) {
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), (ActionEvent ae) -> {
                    if (vermelho) {
                        button.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(1), BorderWidths.DEFAULT)));
                    } else {
                        button.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.EMPTY)));
                    }
                    vermelho = !vermelho;
                }));
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
            }
            button.setGraphic(imageView);
            button.setContentDisplay(ContentDisplay.TOP);
            gpDispositivos.add(button, coluna, linha);
            coluna++;
            if (coluna > 6) {
                coluna = 0;
                linha++;
            }
        }
    }
}
