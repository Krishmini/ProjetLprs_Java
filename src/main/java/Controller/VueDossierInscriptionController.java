package Controller;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VueDossierInscriptionController {
    private Connection connection;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField heureField;
    @FXML
    public TextField filiere_interetField;

    @FXML
    public TextField motivation_etudiantField;

    @FXML
    public Button creer1;

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
    protected void onCreer1ButtonClick() {
        montrerDossier();
    }

    private void montrerDossier() {
        String sql = "SELECT * FROM `dossierinscription` WHERE id_dossierInscription = id_dossierInscription;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, String.valueOf(datePicker));
            preparedStatement.setString(2, String.valueOf(heureField));
            preparedStatement.setString(2, String.valueOf(filiere_interetField));
            preparedStatement.setString(2, String.valueOf(motivation_etudiantField));
            ResultSet resultSet = preparedStatement.executeQuery();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
