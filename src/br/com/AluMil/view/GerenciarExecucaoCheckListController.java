/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.dao.MaquinaDAO;
import br.com.AluMil.control.dao.OperacaoOrdemExecucaoDAO;
import br.com.AluMil.control.dao.OperacaoOrdemExecucaoDiaDAO;
import br.com.AluMil.control.dao.OrdemExecucaoDAO;
import br.com.AluMil.model.entity.Maquina;
import br.com.AluMil.model.entity.Operacao;
import static br.com.AluMil.model.entity.Operacao.Tipo.MEDICAOVALORES;
import br.com.AluMil.model.entity.OperacaoOrdemExecucao;
import br.com.AluMil.model.entity.OperacaoOrdemExecucaoDia;
import br.com.AluMil.model.entity.OperacaoOrdemExecucaoDia.OperacaoOrdemExecucaoDiaId;
import br.com.AluMil.model.entity.OrdemExecucao;
import br.com.AluMil.model.entity.OrdemExecucao.Periodicidade;
import br.com.AluMil.model.util.Formatter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarExecucaoCheckListController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private TextField tfNome;
    @FXML
    private ComboBox<OrdemExecucao> cbOrdemProducao;
    @FXML
    private ComboBox<Maquina> cbEquipamento;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private ComboBox<OrdemExecucao.Periodicidade> cbPeriodicidade;
    @FXML
    private GridPane gpOperacoes;
    private ObservableList<OrdemExecucao> ordemExecucaos = FXCollections.observableArrayList();
    private ObservableList<OrdemExecucao.Periodicidade> periodicidades = FXCollections.observableArrayList();
    private ObservableList<Maquina> maquinas = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEquipamento.setItems(maquinas);
        cbOrdemProducao.setItems(ordemExecucaos);
        cbPeriodicidade.setItems(periodicidades);
        ordemExecucaos.setAll(new OrdemExecucaoDAO().pegarTodos());
        maquinas.setAll(new MaquinaDAO().pegarPorAtivo(true));
        periodicidades.setAll(Periodicidade.values());

    }

    @FXML
    private void cbOrdemProducaoActionEvent(ActionEvent actionEvent) {
        OrdemExecucao ordemExecucao = cbOrdemProducao.getSelectionModel().getSelectedItem();
        gpOperacoes.getChildren().clear();
        if (ordemExecucao != null) {
            dpInicio.setValue(Formatter.toLocalDate(ordemExecucao.getInicio()));
            dpFim.setValue(Formatter.toLocalDate(ordemExecucao.getFim()));
            cbEquipamento.getSelectionModel().select(ordemExecucao.getCheckList().getMaquina());
            cbPeriodicidade.getSelectionModel().select(ordemExecucao.getPeriodicidade());
            tfNome.setText(ordemExecucao.getCheckList().getDescricao());
            gpOperacoes.add(new Label("COD"), 0, 0);
            gpOperacoes.add(new Label("DESCRIÇÃO DAS OPERAÇÕES DO CHECKLIST"), 1, 0);
            for (int i = 0; i < ordemExecucao.getQuantidade(); i++) {
                gpOperacoes.add(new Label(String.valueOf(i + 1)), i + 2, 0);
            }
            int linha = 1;
            for (OperacaoOrdemExecucao operacaoOrdemExecucao : new OperacaoOrdemExecucaoDAO().pegarPorOrdemExecucao(ordemExecucao)) {
                gpOperacoes.add(new Label(String.valueOf(operacaoOrdemExecucao.getId().getOperacao().getId())), 0, linha);
                gpOperacoes.add(new Label(operacaoOrdemExecucao.getId().getOperacao().getDescricao()), 1, linha);
                for (int i = 0; i < ordemExecucao.getQuantidade(); i++) {
                    final int dia = i + 1;
                    Operacao operacao = operacaoOrdemExecucao.getId().getOperacao();
                    OperacaoOrdemExecucaoDia operacaoOrdemExecucaoDia = new OperacaoOrdemExecucaoDiaDAO().pegarPorId(new OperacaoOrdemExecucaoDiaId(operacaoOrdemExecucao, dia));
                    switch (operacao.getTipo()) {
                        case MEDICAOVALORES:
                            TextField textField = new TextField();
                            if (operacaoOrdemExecucaoDia != null) {
                                textField.setText(operacaoOrdemExecucaoDia.getValor());
                            }
                            textField.setOnKeyReleased((KeyEvent event) -> {
                                System.out.println(dia);
                                OperacaoOrdemExecucaoDia operacaoOrdemExecucaoDiaNovo = new OperacaoOrdemExecucaoDiaDAO().pegarPorId(new OperacaoOrdemExecucaoDiaId(operacaoOrdemExecucao, dia));
                                if (operacaoOrdemExecucaoDiaNovo != null) {
                                    operacaoOrdemExecucaoDiaNovo.setValor(textField.getText());
                                    new OperacaoOrdemExecucaoDiaDAO().editar(operacaoOrdemExecucaoDiaNovo);
                                } else {
                                    operacaoOrdemExecucaoDiaNovo = new OperacaoOrdemExecucaoDia();
                                    operacaoOrdemExecucaoDiaNovo.setId(new OperacaoOrdemExecucaoDiaId(operacaoOrdemExecucao, dia));
                                    operacaoOrdemExecucaoDiaNovo.setValor(textField.getText());
                                    new OperacaoOrdemExecucaoDiaDAO().cadastrar(operacaoOrdemExecucaoDiaNovo);
                                }
                            });
                            gpOperacoes.add(textField, i + 2, linha);
                            break;
                        default:
                            ObservableList<String> items = FXCollections.observableArrayList();
                            items.setAll(operacao.getTipo().getValues());
                            ComboBox<String> comboBox = new ComboBox();
                            comboBox.setItems(items);
                            if (operacaoOrdemExecucaoDia != null) {
                                comboBox.getSelectionModel().select(operacaoOrdemExecucaoDia.getValor());
                            }
                            comboBox.setOnAction((ActionEvent ae) -> {
                                System.out.println(dia);
                                OperacaoOrdemExecucaoDia operacaoOrdemExecucaoDiaNovo = new OperacaoOrdemExecucaoDiaDAO().pegarPorId(new OperacaoOrdemExecucaoDiaId(operacaoOrdemExecucao, dia));
                                if (operacaoOrdemExecucaoDiaNovo != null) {
                                    operacaoOrdemExecucaoDiaNovo.getId().setDia(dia);
                                    operacaoOrdemExecucaoDiaNovo.setValor(comboBox.getValue());
                                    new OperacaoOrdemExecucaoDiaDAO().editar(operacaoOrdemExecucaoDiaNovo);
                                } else {
                                    operacaoOrdemExecucaoDiaNovo = new OperacaoOrdemExecucaoDia();
                                    operacaoOrdemExecucaoDiaNovo.setId(new OperacaoOrdemExecucaoDiaId(operacaoOrdemExecucao, dia));
                                    operacaoOrdemExecucaoDiaNovo.setValor(comboBox.getValue());
                                    new OperacaoOrdemExecucaoDiaDAO().cadastrar(operacaoOrdemExecucaoDiaNovo);
                                }
                            });
                            gpOperacoes.add(comboBox, i + 2, linha);
                    }
                }
                linha++;
            }
        } else {
            dpInicio.setValue(null);
            dpFim.setValue(null);
            cbEquipamento.getSelectionModel().clearSelection();
            cbPeriodicidade.getSelectionModel().clearSelection();
            tfNome.clear();
        }
    }

}
