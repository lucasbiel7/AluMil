/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.view;

import br.com.AluMil.control.FxMananger;
import br.com.AluMil.control.dao.UsuarioDAO;
import br.com.AluMil.model.entity.Usuario;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author OCTI01
 */
public class Runner extends Application {

    @Override
    public void start(Stage primaryStage) {
//        try {
//            carregarFotosUsuarios();
//        } catch (IOException ex) {
//            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //Login
        FxMananger.showWindow(FxMananger.carregarComponente("Autenticacao"), "Gestão TPM", FxMananger.Tipo.EXIT_ON_CLOSE).show();
//Testes
//        FxMananger.showWindow(FxMananger.carregarComponente("GestaoMaquina"), "Gestão TPM", FxMananger.Tipo.MAXIMIZADO, FxMananger.Tipo.EXIT_ON_CLOSE).show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void carregarFotosUsuarios() throws IOException {
        for (Usuario usuario : new UsuarioDAO().pegarTodos()) {
            if (new File("funcionarios/" + usuario.getId() + ".jpg").isFile()) {
                usuario.setFoto(Files.readAllBytes(Paths.get("funcionarios/" + usuario.getId() + ".jpg")));
            } else if (new File("funcionarios/" + usuario.getId() + ".jpeg").isFile()) {
                usuario.setFoto(Files.readAllBytes(Paths.get("funcionarios/" + usuario.getId() + ".jpeg")));
            } else if (new File("funcionarios/" + usuario.getId() + ".JPG").isFile()) {
                usuario.setFoto(Files.readAllBytes(Paths.get("funcionarios/" + usuario.getId() + ".JPG")));
            }
            new UsuarioDAO().editar(usuario);
        }
    }
}
