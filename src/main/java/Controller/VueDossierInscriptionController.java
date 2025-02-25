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
    public Button montrer;

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
    protected void onMontrerButtonClick() {
        montrerDossier();
    }

    private void montrerDossier() {
        String sql = "SELECT * FROM `dossierinscription` WHERE id_dossierInscription = id_dossierInscription";
        try (Connection connection = new Database().getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Dossier trouvé !");
                System.out.println("Date : " + resultSet.getDate("date"));
                System.out.println("Heure : " + resultSet.getString("heure"));
                System.out.println("Filière d'intérêt : " + resultSet.getString("filiere_interet"));
                System.out.println("Motivation : " + resultSet.getString("motivation_etudiant"));
            } else {
                System.out.println("Aucun dossier trouvé.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}