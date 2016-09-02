/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.dao.RelatorioMaquinaDAO;
import br.com.AluMil.model.entity.RelatorioMaquina;
import br.com.AluMil.model.util.Formatter;
import br.com.AluMil.model.util.RelatorioMaquinaModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioGerencial02Controller implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @FXML
    private TableView<RelatorioMaquinaModel> tvRelatorioMaquina;
    @FXML
    private TableColumn<RelatorioMaquinaModel, Date> tcData;
    @FXML
    private TableColumn<RelatorioMaquinaModel,Integer> tcTotal;

    private ObservableList<RelatorioMaquinaModel> relatorioMaquinaModels = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcData.setCellValueFactory(new PropertyValueFactory("data"));
        tcData.setCellFactory((TableColumn<RelatorioMaquinaModel, Date> param) -> new TableCell<RelatorioMaquinaModel, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(Formatter.toDate(item) + " " + Formatter.toHour(item));
                }
            }
        });
        tcTotal.setCellValueFactory((TableColumn.CellDataFeatures<RelatorioMaquinaModel, Integer> param) -> new SimpleObjectProperty<>(param.getValue().getSoma().stream().mapToInt(Integer::intValue).sum()));
        List<String> maquinas = new RelatorioMaquinaDAO().pegarMaquinas();
        for (String maquina : maquinas) {
            TableColumn<RelatorioMaquinaModel, Integer> tableColumn = new TableColumn(maquina);
            tableColumn.setCellValueFactory(new InserirValor(maquinas.indexOf(maquina)));
            tvRelatorioMaquina.getColumns().add(tableColumn);
        }
        tvRelatorioMaquina.setItems(relatorioMaquinaModels);
        new Thread(() -> {
            relatorioMaquinaModels.clear();
            List<RelatorioMaquina> relatorioMaquinas = new RelatorioMaquinaDAO().pegarTodos();
            Date registroFim=relatorioMaquinas.get(relatorioMaquinas.size() - 1).getData();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(relatorioMaquinas.get(0).getData());
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            while (calendar.getTime().before(registroFim)) {
                Calendar fim = Calendar.getInstance();
                fim.setTime(calendar.getTime());
                fim.add(Calendar.HOUR, 1);
                relatorioMaquinas = new RelatorioMaquinaDAO().pegarEntreData(calendar.getTime(), fim.getTime());
                if (!relatorioMaquinas.isEmpty()) {
                    RelatorioMaquinaModel relatorioMaquinaModel = new RelatorioMaquinaModel();
                    relatorioMaquinaModel.setData(calendar.getTime());
                    relatorioMaquinaModel.setSoma(new ArrayList<>());
                    for (String maquina : maquinas) {
                        relatorioMaquinas = new RelatorioMaquinaDAO().pegarEntreDataMaquina(calendar.getTime(), fim.getTime(), maquina);
                        relatorioMaquinaModel.getSoma().add(relatorioMaquinas.stream().mapToInt(RelatorioMaquina::getScan).sum());
                    }
                    relatorioMaquinaModels.add(relatorioMaquinaModel);
                }
                calendar.add(Calendar.HOUR, 1);
            }
        }).start();
    }

    public class InserirValor implements Callback<TableColumn.CellDataFeatures<RelatorioMaquinaModel, Integer>, ObservableValue<Integer>> {

        private int posicao;

        public InserirValor(int posicao) {
            this.posicao = posicao;
        }

        @Override
        public ObservableValue<Integer> call(TableColumn.CellDataFeatures<RelatorioMaquinaModel, Integer> param) {
            return new SimpleObjectProperty<>(param.getValue().getSoma().get(posicao));
        }

    }
}
