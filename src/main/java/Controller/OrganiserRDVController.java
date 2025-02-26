package Controller;


import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.EventObject;

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


}