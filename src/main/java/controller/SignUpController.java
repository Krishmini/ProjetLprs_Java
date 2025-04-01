package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    TextField tf_nom;
    @FXML
    TextField tf_prenom;
    @FXML
    TextField tf_mail;
    @FXML
    TextField tf_mdp;
    @FXML
    RadioButton rb_Professeur;
    @FXML
    RadioButton rb_Secretaire;
    @FXML
    RadioButton rb_Gestionnaire;
    @FXML
    Button bt_inscription;
    @FXML
    Button bt_rd_connexion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup tg = new ToggleGroup();
        rb_Professeur.setToggleGroup(tg);
        rb_Secretaire.setToggleGroup(tg);
        rb_Gestionnaire.setToggleGroup(tg);

        rb_Professeur.setSelected(true);

        bt_inscription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) tg.getSelectedToggle()).getText();
                if(!tf_nom.getText().trim().isEmpty() && !tf_prenom.getText().trim().isEmpty() && !tf_mail.getText().trim().isEmpty() && !tf_mdp.getText().trim().isEmpty() && !rb_Professeur.isSelected()){
                    DBUtils.inscriptionUtilisateur(event,tf_nom.getText(), tf_prenom.getText(), tf_mdp.getText(), tf_mail.getText(), toggleName);
                } else {
                    System.out.println("Veuilliez remplir toutes les informations");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Vous n'avez pas remplis toutes les information");
                    alert.show();
                }

            }
        });
        bt_rd_connexion.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/appli/lprsjavafx/connexion.fxml", "Log In!", null, null, null, null, null);
            }
        });

    }
}
