package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button bt_connexion;
    @FXML
    private Button bt_rd_inscription;
    @FXML
    private TextField tf_mail;
    @FXML
    private TextField tf_mdp;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bt_connexion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.loginUtilisateur(event, tf_mail.getText(), tf_mdp.getText());
            }
        });

        bt_rd_inscription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/appli/lprsjavafx/signup.fxml", "Inscription!", null, null, null, null, null);

            }
        });
    }
}
