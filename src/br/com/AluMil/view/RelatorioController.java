/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import br.com.AluMil.control.dao.MaquinaDAO;
import br.com.AluMil.control.dao.OcorrenciaDAO;
import br.com.AluMil.model.entity.Maquina;
import br.com.AluMil.model.entity.Ocorrencia;
import br.com.AluMil.model.util.Formatter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ListView<Maquina> lvEquipamento;
    @FXML
    private ListView<String> lvStatus;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private ScrollPane spRelatorio;
    private SwingNode snRelatorio;

    private ObservableList<Maquina> maquinas = FXCollections.observableArrayList();
    private ObservableList<String> status = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvEquipamento.setItems(maquinas);
        lvStatus.setItems(status);
        status.setAll("Ok", "Atrasada", "Adiantada");
        maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
        snRelatorio = new SwingNode();

        spRelatorio.setContent(snRelatorio);
        lvEquipamento.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvStatus.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvEquipamento.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Maquina> observable, Maquina oldValue, Maquina newValue) -> {
            relatorio();
        });
        lvStatus.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            relatorio();
        });
    }

    @FXML
    private void dpAtualizarRelatorioActionEvent(ActionEvent actionEvent) {
        relatorio();
    }

    @FXML
    private void lvAtualizarRelatorioMouseReleased(MouseEvent mouseEvent) {
        relatorio();
    }

    private void relatorio() {
        if (dpInicio.getValue() != null && dpFim.getValue() != null) {
            Date inicio = Formatter.toDate(dpInicio.getValue());
            Date fim = Formatter.toDate(dpFim.getValue());
            Map<String, Object> parametro = new HashMap<>();
            try {
                DefaultTableModel dtmTabela = new DefaultTableModel(new Object[][]{}, new Object[]{"Programada", "Executada", "Status", "Equipamento - dispositivo", "responsável", "ocorrência", "T(m)"});
                List<Ocorrencia> ocorrencias = new OcorrenciaDAO().pegarPorMaquinas(lvEquipamento.getSelectionModel().getSelectedItems(), inicio, fim);
                boolean ok = false, adiantado = false, atrasado = false;
                for (String statusSelecionados : lvStatus.getSelectionModel().getSelectedItems()) {
                    switch (statusSelecionados) {
                        case "Ok":
                            ok = true;
                            break;
                        case "Atrasada":
                            atrasado = true;
                            break;
                        case "Adiantada":
                            adiantado = true;
                            break;
                    }
                }
                if (!ok) {
                    ocorrencias.removeIf((Ocorrencia t) -> {
                        return t.getDataRealizada().equals(t.getManutencao().getDataPrevista());
                    });
                }
                if (!atrasado) {
                    ocorrencias.removeIf((Ocorrencia t) -> {
                        return t.getDataRealizada().after(t.getManutencao().getDataPrevista());
                    });
                }
                if (!adiantado) {
                    ocorrencias.removeIf((Ocorrencia t) -> {
                        return t.getDataRealizada().before(t.getManutencao().getDataPrevista());
                    });
                }
                for (Ocorrencia ocorrencia : ocorrencias) {
                    String status = ocorrencia.getDataRealizada().equals(ocorrencia.getManutencao().getDataPrevista()) ? "Ok" : (ocorrencia.getDataRealizada().after(ocorrencia.getManutencao().getDataPrevista()) ? "Atrasada" : "Adiantada");
                    dtmTabela.addRow(new Object[]{
                        Formatter.toDate(ocorrencia.getManutencao().getDataPrevista()),
                        Formatter.toDate(ocorrencia.getDataRealizada()),
                        status,
                        ocorrencia.getManutencao().getCiclo().getDispositivo().getMaquina().toString() + "\n"
                        + ocorrencia.getManutencao().getCiclo().getDispositivo().toString() + " - Linha " + ocorrencia.getManutencao().getNumero(),
                        ocorrencia.getUsuario().toString(),
                        ocorrencia.getOcorrencia(),
                        String.valueOf(ocorrencia.getTempoGasto())
                    });
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream(FxMananger.VIEW + "report/relatorio.jasper"), parametro, new JRTableModelDataSource(dtmTabela));
                JRViewer jRViewer = new JRViewer(jasperPrint);
                snRelatorio.setContent(jRViewer);
            } catch (JRException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
