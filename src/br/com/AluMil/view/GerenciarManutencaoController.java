/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import br.com.AluMil.control.FxMananger.Tipo;
import br.com.AluMil.control.dao.CicloDAO;
import br.com.AluMil.control.dao.ManutencaoDAO;
import br.com.AluMil.control.dao.OcorrenciaDAO;
import br.com.AluMil.model.entity.Ciclo;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Manutencao;
import br.com.AluMil.model.entity.Ocorrencia;
import br.com.AluMil.model.util.Formatter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarManutencaoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbTitle;
    @FXML
    private Label lbPeriodicidade;
    @FXML
    private ComboBox<Ciclo> cbCiclo;
    @FXML
    private GridPane gpManutencao;
    @FXML
    private Label lbRodada;
    @FXML
    private Label lbAcumulado;
    private Dispositivo dispositivo;
    private ObservableList<Ciclo> ciclos = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            dispositivo = (Dispositivo) apPrincipal.getUserData();
            lbTitle.setText("Troca de " + dispositivo.getNome() + " - " + dispositivo.getMaquina());
            lbPeriodicidade.setText("Deverá ser realizado um em cada posição a cada 16 dias");
            ciclos.setAll(new CicloDAO().pegarPorDispositivo(dispositivo));
        });
        cbCiclo.setItems(ciclos);
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("NovoCiclo", dispositivo), "Nova rodada", Tipo.MODAL).showAndWait();
        ciclos.setAll(new CicloDAO().pegarPorDispositivo(dispositivo));
        listarCiclo();
    }

    @FXML
    private void btExcluirActionEvent(ActionEvent actionEvent) {
        Ciclo ciclo = cbCiclo.getSelectionModel().getSelectedItem();
        if (ciclo != null) {
            FxMananger.showWindow(FxMananger.carregarComponente("Autenticacao", new Object[]{"ExcluirCiclo", 1, ciclo}), "Confirmação de usuário administrador", FxMananger.Tipo.MODAL).showAndWait();
            ciclos.setAll(new CicloDAO().pegarPorDispositivo(dispositivo));
            cbCiclo.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void cbCicloActionEvent(ActionEvent actionEvent) {
        listarCiclo();
    }

    @FXML
    private void btOcorrenciasActionEvent(ActionEvent actionEvent) {
        FxMananger.showWindow(FxMananger.carregarComponente("GerenciarOcorrencias", dispositivo), "Ocorrências", FxMananger.Tipo.MODAL).showAndWait();
        listarCiclo();
    }

    @FXML
    private void btVoltarActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }

    private void listarCiclo() {
        Ciclo ciclo = cbCiclo.getSelectionModel().getSelectedItem();
        gpManutencao.getChildren().clear();
        if (ciclo != null) {
            int linha = 0;
            int coluna = 0;
            List<Manutencao> manutencaos = new ManutencaoDAO().pegarPorCiclo(ciclo);
            int prontas = 0;
            for (Manutencao manutencao : manutencaos) {
                Button button = new Button(String.valueOf(manutencao.getNumero()) + "\n"
                        + Formatter.toDate(manutencao.getDataPrevista()));
                Ocorrencia ocorrencia = new OcorrenciaDAO().pegarPorManutencao(manutencao);
                if (ocorrencia == null) {
                    button.setOnAction((ActionEvent ae) -> {
                        FxMananger.showWindow(FxMananger.carregarComponente("NovoOcorrencia", manutencao), "Ocorrência", Tipo.MODAL).showAndWait();
                        listarCiclo();
                    });
                    if (manutencao.getDataPrevista().before(new Date())) {
                        button.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        button.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                } else {
                    button.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                    prontas++;
                    if (ocorrencia.getDataRealizada().equals(manutencao.getDataPrevista())) {

                    } else {
                        long tempo = (manutencao.getDataPrevista().getTime() - ocorrencia.getDataRealizada().getTime()) / 1000 / 60 / 60 / 24;
                        button.setText(button.getText() + "\n"
                                + (tempo < 0 ? "AT: " : "AD: ") + tempo);
                    }
                }
                gpManutencao.add(button, coluna, linha);
                coluna++;
                if (coluna > 6) {
                    coluna = 0;
                    linha++;
                }
            }
            lbRodada.setText(new DecimalFormat("0.00").format(prontas * 100.0 / manutencaos.size()) + "%");
            manutencaos = new ManutencaoDAO().pegarPorDispositivoData(dispositivo, new Date());
            prontas = 0;
            for (Manutencao manutencao : manutencaos) {
                if (new OcorrenciaDAO().pegarPorManutencao(manutencao) != null) {
                    prontas++;
                }
            }
            lbAcumulado.setText(new DecimalFormat("0.00").format(prontas * 100.0 / manutencaos.size()) + "%");
        }
    }

}
