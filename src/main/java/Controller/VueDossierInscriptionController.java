package Controller;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VueDossierInscriptionController {
    public Button btnRetour;
    public Button editer;
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
    public void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/EspaceProfesseur.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}