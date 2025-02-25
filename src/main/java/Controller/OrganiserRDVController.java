package Controller;


import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class OrganiserRDVController {
    @FXML
    public TextField salleField;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField heureField;
    public Button valider;

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

        String sql = "INSERT INTO rendezvous (salle, date, heure) VALUES (?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, salleField.getText());
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