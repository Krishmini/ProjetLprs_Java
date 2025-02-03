package Controller;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class FicheEtudiantController {
    @FXML
    public TextField nomField;
    @FXML
    public TextField prenomField;
    @FXML
    public TextField dernier_diplomeField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField telephoneField;
    @FXML
    public TextField villeField;
    @FXML
    public TextField cpField;
    @FXML
    public TextField rueField;
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
        String sql = "INSERT INTO ficheetudiant (nom, prenom, dernier_diplome, email, telephone, ville, cp, rue) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nomField.getText());
            statement.setString(2, prenomField.getText());
            statement.setString(3, dernier_diplomeField.getText());
            statement.setString(4, emailField.getText());
            statement.setString(5, telephoneField.getText());
            statement.setString(6, villeField.getText());
            statement.setString(7, cpField.getText());
            statement.setString(8, rueField.getText());

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
}
