package Controller;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class FicheEtudiantController {

    public Button btnRetour;
    @FXML
    public TextField nom_eField;
    @FXML
    public TextField prenom_eField;
    @FXML
    public TextField dernier_diplomeField;
    @FXML
    public TextField email_eField;
    @FXML
    public TextField telephone_eField;
    @FXML
    public TextField ville_eField;
    @FXML
    public TextField cp_eField;
    @FXML
    public TextField rue_eField;
    @FXML
    public Button creer;
    @FXML
    private Label welcomeText;

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
    protected void onCreerButtonClick() {
        enregistrerEtudiant();
    }
    private void enregistrerEtudiant() {
        String sql = "INSERT INTO ficheetudiant (nom_e, prenom_e, dernier_diplome, email_e, telephone_e, ville_e, cp_e, rue_e) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nom_eField.getText());
            statement.setString(2, prenom_eField.getText());
            statement.setString(3, dernier_diplomeField.getText());
            statement.setString(4, email_eField.getText());
            statement.setString(5, telephone_eField.getText());
            statement.setString(6, ville_eField.getText());
            statement.setString(7, cp_eField.getText());
            statement.setString(8, rue_eField.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Étudiant ajouté avec succès !");
            } else {
                System.out.println("Échec de l'ajout.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/EspaceSecretaire.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
