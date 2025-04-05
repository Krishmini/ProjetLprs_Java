package controller;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class inscriptionController {
    public Button bt_rd_connexion;
    public Button logout;
    public HBox Main;
    public TextField emailField;
    public Button bt_inscription;

    @FXML
    private RadioButton rb_Professeur, rb_Secretaire, rb_Gestionnaire;
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

    private Connection connection;
    @FXML
    public void initialize() {
        try {
            Database db = new Database();
            connection = db.getConnexion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onInscrireButtonClick() {
        if (isEmailValid(emailField.getText())) {
        ajouterEtudiant();
        } else {
            showAlert("Erreur", "L'email doit être au format test@test.fr !");
        }
    }

    private boolean isEmailValid(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.fr$";
        return email.matches(emailPattern);
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
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, roleSelectionne);
            statement.setString(2, nomField.getText());
            statement.setString(3, prenomField.getText());
            statement.setString(4, emailField.getText());
            statement.setString(5, mdpField.getText());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Étudiant ajouté avec succès !");
                int utilisateurId = obtenirUtilisateurId(emailField.getText());
                enregistrerAction(utilisateurId, "Création", "utilisateur", "Ajout d'un nouvel étudiant");

                reloadScene(new ActionEvent(bt_inscription,null));
            } else {
                System.out.println("Échec de l'ajout.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int obtenirUtilisateurId(String email) {
        String sql = "SELECT id_utilisateur FROM utilisateur WHERE mail = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_utilisateur");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void enregistrerAction(int utilisateurId, String actionType, String objetModifie, String detailsAction) {
        String sql = "INSERT INTO action_logs (utilisateur_id, action_type, objet_modifie, details_action, date_heure_action) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, utilisateurId);
            statement.setString(2, actionType);
            statement.setString(3, objetModifie);
            statement.setString(4, detailsAction);
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reloadScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/Inscription.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleconnexion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/connexion.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}