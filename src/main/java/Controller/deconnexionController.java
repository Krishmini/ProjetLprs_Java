package Controller;

import database.Database;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class deconnexionController {
    public Button logoutButton;
    public HBox scenePane;

    Stage stage;
    public void logout(ActionEvent event){
    stage = (Stage) scenePane.getScene().getWindow();
        System.out.println("You successfully logged out!");
        stage.close();
    }
}