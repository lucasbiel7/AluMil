/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.dao.RelatorioGerencialDAO;
import br.com.AluMil.model.entity.RelatorioGerencial;
import br.com.AluMil.model.util.Formatter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RelatorioGerencial01Controller implements Initializable {

    @FXML
    private TableView<RelatorioGerencial> tvRelatorioGerencial;
    @FXML
    private TableColumn<RelatorioGerencial, Date> tcData;
    @FXML
    private TableColumn<RelatorioGerencial, Integer> tcGrandTotal;
    @FXML
    private TableColumn tcInfo01;
    @FXML
    private TableColumn tcInfo02;
    @FXML
    private TableColumn tcInfo03;
    @FXML
    private TableColumn tcInfo04;
    @FXML
    private TableColumn tcInfo05;
    @FXML
    private TableColumn tcInfo06;
    @FXML
    private TableColumn tcInfo07;
    @FXML
    private TableColumn tcInfo08;
    @FXML
    private TableColumn tcInfo09;
    @FXML
    private TableColumn tcInfo10;
    @FXML
    private TableColumn tcInfo11;
    @FXML
    private TableColumn tcInfo12;
    @FXML
    private TableColumn tcInfo13;
    @FXML
    private TableColumn tcInfo14;
    @FXML
    private TableColumn tcInfo15;
    @FXML
    private TableColumn tcInfo16;
    @FXML
    private TableColumn tcInfo17;
    @FXML
    private TableColumn tcInfo18;
    @FXML
    private TableColumn tcInfo19;
    @FXML
    private TableColumn tcInfo20;
    @FXML
    private TableColumn tcInfo21;
    @FXML
    private TableColumn tcInfo22;
    @FXML
    private TableColumn tcInfo23;
    @FXML
    private TableColumn tcInfo24;

    private ObservableList<RelatorioGerencial> relatorioGerencials = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvRelatorioGerencial.setItems(relatorioGerencials);
        carregarLista();
        List<TableColumn> colunas = new ArrayList<>();
        colunas.add(tcInfo01);
        colunas.add(tcInfo02);
        colunas.add(tcInfo03);
        colunas.add(tcInfo04);
        colunas.add(tcInfo05);
        colunas.add(tcInfo06);
        colunas.add(tcInfo07);
        colunas.add(tcInfo08);
        colunas.add(tcInfo09);
        colunas.add(tcInfo10);
        colunas.add(tcInfo11);
        colunas.add(tcInfo12);
        colunas.add(tcInfo13);
        colunas.add(tcInfo14);
        colunas.add(tcInfo15);
        colunas.add(tcInfo16);
        colunas.add(tcInfo17);
        colunas.add(tcInfo18);
        colunas.add(tcInfo19);
        colunas.add(tcInfo20);
        colunas.add(tcInfo21);
        colunas.add(tcInfo22);
        colunas.add(tcInfo23);
        colunas.add(tcInfo24);
        int numero = 1;
        tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tcGrandTotal.setCellValueFactory((TableColumn.CellDataFeatures<RelatorioGerencial, Integer> param) -> {
            RelatorioGerencial relatorioGerencial = param.getValue();
            return new SimpleObjectProperty<>(
                    relatorioGerencial.getInfo01()
                    + relatorioGerencial.getInfo02()
                    + relatorioGerencial.getInfo03()
                    + relatorioGerencial.getInfo04()
                    + relatorioGerencial.getInfo05()
                    + relatorioGerencial.getInfo06()
                    + relatorioGerencial.getInfo07()
                    + relatorioGerencial.getInfo08()
                    + relatorioGerencial.getInfo09()
                    + relatorioGerencial.getInfo10()
                    + relatorioGerencial.getInfo11()
                    + relatorioGerencial.getInfo12()
                    + relatorioGerencial.getInfo13()
                    + relatorioGerencial.getInfo14()
                    + relatorioGerencial.getInfo15()
                    + relatorioGerencial.getInfo16()
                    + relatorioGerencial.getInfo17()
                    + relatorioGerencial.getInfo18()
                    + relatorioGerencial.getInfo19()
                    + relatorioGerencial.getInfo20()
                    + relatorioGerencial.getInfo21()
                    + relatorioGerencial.getInfo22()
                    + relatorioGerencial.getInfo23()
                    + relatorioGerencial.getInfo24()
            );
        });
        tcData.setCellFactory((TableColumn<RelatorioGerencial, Date> param) -> new TableCell<RelatorioGerencial, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(Formatter.toDate(item) + " " + Formatter.toHour(item));
                }
            }
        });
        for (TableColumn coluna : colunas) {
            coluna.setCellValueFactory(new PropertyValueFactory<>("info" + (numero < 10 ? "0" + numero : numero)));
            numero++;
        }
    }

    private void carregarLista() {
        relatorioGerencials.clear();
        new Thread(() -> {
            System.out.println("carregando lista");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new RelatorioGerencialDAO().pegarTodos().get(0).getData());
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            while (calendar.getTime().before(new Date())) {
                Calendar fim = Calendar.getInstance();
                fim.setTime(calendar.getTime());
                fim.add(Calendar.HOUR, 1);
                List<RelatorioGerencial> relatorioGerencials = new RelatorioGerencialDAO().pegarEntreData(calendar.getTime(), fim.getTime());
                if (!relatorioGerencials.isEmpty()) {
                    RelatorioGerencial relatorioGerencial = new RelatorioGerencial();
                    relatorioGerencial.setData(calendar.getTime());
                    relatorioGerencial.setInfo01(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo01).sum());
                    relatorioGerencial.setInfo02(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo02).sum());
                    relatorioGerencial.setInfo03(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo03).sum());
                    relatorioGerencial.setInfo04(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo04).sum());
                    relatorioGerencial.setInfo05(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo05).sum());
                    relatorioGerencial.setInfo06(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo06).sum());
                    relatorioGerencial.setInfo07(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo07).sum());
                    relatorioGerencial.setInfo08(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo08).sum());
                    relatorioGerencial.setInfo09(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo09).sum());
                    relatorioGerencial.setInfo10(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo10).sum());
                    relatorioGerencial.setInfo11(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo11).sum());
                    relatorioGerencial.setInfo12(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo12).sum());
                    relatorioGerencial.setInfo13(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo13).sum());
                    relatorioGerencial.setInfo14(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo14).sum());
                    relatorioGerencial.setInfo15(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo15).sum());
                    relatorioGerencial.setInfo16(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo16).sum());
                    relatorioGerencial.setInfo17(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo17).sum());
                    relatorioGerencial.setInfo18(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo18).sum());
                    relatorioGerencial.setInfo19(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo19).sum());
                    relatorioGerencial.setInfo20(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo20).sum());
                    relatorioGerencial.setInfo21(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo21).sum());
                    relatorioGerencial.setInfo22(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo22).sum());
                    relatorioGerencial.setInfo23(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo23).sum());
                    relatorioGerencial.setInfo24(relatorioGerencials.stream().mapToInt(RelatorioGerencial::getInfo24).sum());
                    this.relatorioGerencials.add(relatorioGerencial);
                }
                calendar.add(Calendar.HOUR, 1);
            }
            System.out.println("Termino de carregar");
        }).start();
    }
}
