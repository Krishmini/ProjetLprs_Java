package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    @FXML
    private Button bt_deconnexion;

    @FXML
    private Label label_bienvenue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bt_deconnexion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "logged-in.fxml", "Hello!", null, null, null, null, null);

            }
        });

    }

    public void setLabelBienvenue(String nom, String prenom) {
        label_bienvenue.setText("Bienvenue " + nom + " " + prenom);
    }
}
