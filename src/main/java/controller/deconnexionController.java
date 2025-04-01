package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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