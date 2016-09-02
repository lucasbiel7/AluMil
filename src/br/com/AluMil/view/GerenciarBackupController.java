/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.Message;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarBackupController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private FormattedTextField ftfHorario;
    @FXML
    private Label lbUltimoBackup;

    private FileChooser fcBackup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ftfHorario.getPatternVerifiers().put("h", new IntegerRangePatternVerifier(1, 23));
        ftfHorario.getPatternVerifiers().put("m", new IntegerRangePatternVerifier(0, 59));
        ftfHorario.getPatternVerifiers().put("s", new IntegerRangePatternVerifier(0, 59));
        ftfHorario.setPattern("h:m:s");
        fcBackup = new FileChooser();
        fcBackup.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo de backup mysqk", "*.sql"));
        List<String> texto = new ArrayList<>();
        if (new File("conf/backup.conf").isFile()) {
            try {
                texto = Files.readAllLines(Paths.get("conf/backup.conf"), Charset.defaultCharset());
                if (texto.isEmpty()) {
                    ftfHorario.setText("01:00:00");
                }else{
                    ftfHorario.setText(texto.get(0));
                }
            } catch (IOException ex) {
                Logger.getLogger(GerenciarBackupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                new File("conf/").mkdir();
                texto.add("00:00:00");
                Files.write(Paths.get("conf/backup.conf"), texto, Charset.defaultCharset());
            } catch (IOException ex) {
                Logger.getLogger(GerenciarBackupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btBackupManualActionEvent(ActionEvent actionEvent) {
        try {
            new File("backup/").mkdir();
            Date date = new Date();
            Runtime.getRuntime().exec("cmd /c mysqldump -uroot -pOC2015 --database alumil> backup/" + new SimpleDateFormat("ddMMyyyy").format(date) + "_" + new SimpleDateFormat("HHmmss").format(date) + ".sql");
            Message.showMessage("Backup manual realizado com sucesso", Alert.AlertType.INFORMATION);
            lbUltimoBackup.setText("Ultimo Backup: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date) + "\n"
                    + "Tipo Backup: Manual");
        } catch (IOException ex) {
            Logger.getLogger(GerenciarBackupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ftfHorarioKeyRelease(KeyEvent keyEvent) {
        try {
            List<String> texto = new ArrayList<>();
            texto.add(ftfHorario.getText());
            Files.write(Paths.get("conf/backup.conf"), texto, Charset.defaultCharset());
        } catch (IOException ex) {
            Logger.getLogger(GerenciarBackupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btRestaurarBackupActionEvent(ActionEvent actionEvent) {
        File file = fcBackup.showOpenDialog(apPrincipal.getScene().getWindow());
        if (file != null) {
            try {
                Runtime.getRuntime().exec("cmd /c mysql -uroot -pOC2015 <" + file.getAbsolutePath());
                Message.showMessage("Backup restaurado com sucesso", Alert.AlertType.INFORMATION);
            } catch (IOException ex) {
                Logger.getLogger(GerenciarBackupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
