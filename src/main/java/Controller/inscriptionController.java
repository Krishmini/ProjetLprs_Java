package Controller;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.sql.Connection;
public class inscriptionController {
    @FXML
    private RadioButton rb_Professeur;
    @FXML
    private RadioButton rb_Secretaire;
    @FXML
    private RadioButton rb_Gestionnaire;
    @FXML
    public RadioButton role;
    @FXML
    public TextField nomField;
    @FXML
    public TextField prenomField;
    @FXML
    public TextField mailField;
    @FXML
    public TextField mdpField;
    @FXML
    public TextField ref_demandeFourniture;
    @FXML
    public TextField ref_dossierInscription;
    @FXML
    public Button inscrire;
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
    protected void onInscrireButtonClick() {
        ajouterEtudiant();
    }
    private void ajouterEtudiant() {
        String roleSelectionne = "";

        if (rb_Professeur.isSelected()) {
            roleSelectionne = "Professeur";
        } else if (rb_Secretaire.isSelected()) {
            roleSelectionne = "Secretaire";
        } else if (rb_Gestionnaire.isSelected()) {
            roleSelectionne = "Gestionnaire";
        }

        String sql = "INSERT INTO utilisateur (role, nom, prenom, mail, mdp) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, roleSelectionne);
            statement.setString(2, nomField.getText());
            statement.setString(3, prenomField.getText());
            statement.setString(4, mailField.getText());
            statement.setString(5, mdpField.getText());

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