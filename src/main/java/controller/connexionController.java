package controller;

import Model.Utilisateur;
import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class connexionController {
    private Connection connection;

    @FXML
    public TextField mailField;
    @FXML
    public PasswordField mdpField;
    public Button logout;
    public HBox Main;
    public HBox co;
    public Button logoutButton;
    public TextField emailField1;
    public PasswordField mdpField1;
    public Button bt_connexion;

    @FXML
    private Button bt_rd_connexion;



    public Button inscription;
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
    protected void handleLogin(ActionEvent event) {
        String email = emailField1.getText();
        String password = mdpField1.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        String sql = "SELECT id_utilisateur, role FROM utilisateur WHERE mail = ? AND mdp = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int utilisateurId = resultSet.getInt("id_utilisateur");
                String role = resultSet.getString("role").toLowerCase();
                Model.SessionUtilisateur.setUtilisateurId(utilisateurId);
                enregistrerConnexion(utilisateurId, "Connexion réussie");


                String fxmlFile = "";

                switch (role) {
                    case "secretaire":
                        fxmlFile = "/appli/EspaceSecretaire.fxml";
                        break;
                    case "professeur":
                        fxmlFile = "/appli/EspaceProfesseur.fxml";
                        break;
                    case "gestionnaire":
                        fxmlFile = "/appli/EspaceGestionnaire.fxml";
                        break;
                    default:
                        showAlert("Erreur", "Rôle inconnu !");
                        return;
                }


                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert("Erreur", "Email ou mot de passe incorrect !");
                enregistrerConnexion(0, "Échec de connexion");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Problème de connexion à la base de données.");
        }
    }

    private void enregistrerConnexion(int utilisateurId, String action) {
        String sql = "INSERT INTO connexion_logs (utilisateur_id, date_heure_connexion, action) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, utilisateurId);
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            statement.setString(3, action);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleinscription(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/Inscription.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
