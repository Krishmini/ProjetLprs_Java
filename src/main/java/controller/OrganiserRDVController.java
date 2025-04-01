package controller;


import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrganiserRDVController {
    @FXML
    public RadioButton salle;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField heureField;
    public Button valider;
    public RadioButton salle1;
    public RadioButton salle2;
    public RadioButton salle3;
    public Button btnRetour;

    @FXML
    public void initialize() {
        try {
            Database db = new Database();
            Connection connection = (Connection) db.getConnexion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void onValiderButtonClick() {
        ajouterRdv();
    }

    private void ajouterRdv() {
        String salleSelectionne = "";

        if (salle1.isSelected()) {
            salleSelectionne = "Salle 1";
        } else if (salle2.isSelected()) {
            salleSelectionne = "Salle 2";
        } else if (salle3.isSelected()) {
            salleSelectionne = "Salle 3";
        }

        String sql = "INSERT INTO rendezvous (salle, date, heure) VALUES (?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, salleSelectionne);
            statement.setString(2, datePicker.getValue().toString());
            statement.setString(3, heureField.getText());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("RDV ajouté avec succès !");
            } else {
                System.out.println("Échec de l'ajout.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/EspaceProfesseur.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}