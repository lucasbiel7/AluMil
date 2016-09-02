/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarCheckListController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btEquipamentoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarEquipamento"));
    }

    @FXML
    private void btCheckListActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("CadastroCheckList"));
    }

    @FXML
    private void btOperacaoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarOperacao"));
    }

    @FXML
    private void btOrdemExecucaoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarOrdemExecucao"));
    }

    @FXML
    private void btOperacaoOrdemExecucaoActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarOperacaoOrdemExecucao"));
    }

    @FXML
    private void btExecucaoCheckListActionEvent(ActionEvent actionEvent) {
        FxMananger.inserirPainel(spContainer, FxMananger.carregarComponente("GerenciarExecucaoCheckList"));
    }

}
