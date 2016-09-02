/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import br.com.AluMil.control.dao.CicloDAO;
import br.com.AluMil.control.dao.ManutencaoDAO;
import br.com.AluMil.model.entity.Ciclo;
import br.com.AluMil.model.entity.Dispositivo;
import br.com.AluMil.model.entity.Manutencao;
import br.com.AluMil.model.util.Formatter;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class NovoCicloController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private DatePicker dpInicio;

    private Dispositivo dispositivo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            dispositivo = (Dispositivo) apPrincipal.getUserData();
        });
    }

    @FXML
    private void btAdicionarActionEvent(ActionEvent actionEvent) {
        Ciclo ciclo = new Ciclo();
        if (dpInicio.getValue() != null) {
            ciclo.setInicio(Formatter.toDate(dpInicio.getValue()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(ciclo.getInicio());
            calendar.add(Calendar.DAY_OF_MONTH, dispositivo.getPeriodicidade() * dispositivo.getPecas());
            ciclo.setFim(calendar.getTime());
            ciclo.setDispositivo(dispositivo);
            if (new CicloDAO().pegarPorDispositivoInicioFim(dispositivo, ciclo.getInicio(), ciclo.getFim()).isEmpty()) {
                new CicloDAO().cadastrar(ciclo);
                calendar.setTime(ciclo.getInicio());
                for (int i = 0; i < dispositivo.getPecas(); i++) {
                    Manutencao manutencao = new Manutencao();
                    manutencao.setDataPrevista(calendar.getTime());
                    manutencao.setNumero(i + 1);
                    manutencao.setCiclo(ciclo);
                    new ManutencaoDAO().cadastrar(manutencao);
                    calendar.add(Calendar.DAY_OF_MONTH, dispositivo.getPeriodicidade());
                }
                Message.showMessage("Ciclo foi cadastrado com sucesso!", Alert.AlertType.INFORMATION);
                dpInicio.setValue(null);
            } else {
                Message.showMessage("Já existe um ciclo rodando nesse intervalo de tempo!", Alert.AlertType.ERROR);
            }
        } else {
            Message.showMessage("A data da nova rodada não pode ser vazia", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) apPrincipal.getScene().getWindow()).close();
    }
}
