/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.dao.CicloDAO;
import br.com.AluMil.control.dao.DispositivoDAO;
import br.com.AluMil.control.dao.MaquinaDAO;
import br.com.AluMil.control.dao.OcorrenciaDAO;
import br.com.AluMil.model.entity.Ciclo;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Maquina;
import br.com.AluMil.model.entity.Ocorrencia;
import br.com.AluMil.model.util.EntidadeGrafico;
import java.awt.Color;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GraficoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ListView<Maquina> lvEquipamentos;
    @FXML
    private ListView<Ciclo> lvCiclos;
    @FXML
    private ScrollPane spGrafico;
    @FXML
    private TableView<EntidadeGrafico<Dispositivo>> tvDispositivo;
    @FXML
    private TableColumn<EntidadeGrafico<Dispositivo>, Dispositivo> tcDispositivo;
    @FXML
    private TableColumn<EntidadeGrafico<Dispositivo>, Double> tcTempo;

    private SwingNode snGrafico;

    private ObservableList<EntidadeGrafico<Dispositivo>> dispositivos = FXCollections.observableArrayList();
    private ObservableList<Maquina> maquinas = FXCollections.observableArrayList();
    private ObservableList<Ciclo> ciclos = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        snGrafico = new SwingNode();
        spGrafico.setContent(snGrafico);
        lvCiclos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvEquipamentos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvCiclos.setItems(ciclos);
        lvEquipamentos.setItems(maquinas);
        tvDispositivo.setItems(dispositivos);
        ciclos.setAll(new CicloDAO().pegarTodos());
        maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
        tcDispositivo.setCellValueFactory(new PropertyValueFactory<>("entidade"));
        tcTempo.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    @FXML
    private void lvListasMouseReleased(MouseEvent mouseEvent) {
        List<Dispositivo> dispositivos = new DispositivoDAO().pegarPorMaquinas(lvEquipamentos.getSelectionModel().getSelectedItems());
        this.dispositivos.clear();
        for (Dispositivo dispositivo : dispositivos) {
            List<Ocorrencia> ocorrencias = new OcorrenciaDAO().pegarPorDispositivo(dispositivo,lvCiclos.getSelectionModel().getSelectedItems());
            this.dispositivos.add(new EntidadeGrafico<>(dispositivo, ocorrencias.stream().mapToDouble(Ocorrencia::getTempoGasto).sum()));
        }
        this.dispositivos.sort((EntidadeGrafico<Dispositivo> o1, EntidadeGrafico<Dispositivo> o2) -> Double.compare(o1.getValue(), o2.getValue()) * -1);
        carregarGrafico();
    }

    private void carregarGrafico() {
        DefaultCategoryDataset dcdDados = new DefaultCategoryDataset();
        boolean maior50 = false;
        boolean maior20 = false;
        for (EntidadeGrafico<Dispositivo> dispositivo : dispositivos) {
            double value=(1-(new OcorrenciaDAO().pegarPorDispositivo(dispositivo.getEntidade(),lvCiclos.getSelectionModel().getSelectedItems()).size()/dispositivo.getValue()))*100;
            if (value >= 50) {
                maior50 = true;
                dcdDados.setValue(value, "Boa Eficiencia", dispositivo.toString());
            } else if (value >= 20) {
                maior20 = true;
                dcdDados.setValue(value, "Média Eficiencia", dispositivo.toString());
            } else {
                dcdDados.setValue(value, "Péssima Eficiencia", dispositivo.toString());
            }
        }
        JFreeChart jFreeChart = ChartFactory.createBarChart("Gráfico de tempo[min] por Manutenção", "", "", dcdDados, PlotOrientation.VERTICAL, false, false, false);
        if (maior50 && maior20) {
            jFreeChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.GREEN);
            jFreeChart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.BLUE);
            jFreeChart.getCategoryPlot().getRenderer().setSeriesPaint(2, Color.RED);
        } else if (maior50) {
            jFreeChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.GREEN);
            jFreeChart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.RED);
        } else if (maior20) {
            jFreeChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.BLUE);
            jFreeChart.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.RED);
        } else {
            jFreeChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.RED);
        }
        ChartPanel chartPanel = new ChartPanel(jFreeChart);
        snGrafico.setContent(chartPanel);
    }

}
