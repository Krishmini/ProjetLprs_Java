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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class connexionController {
    @FXML
    public TextField mailField;
    @FXML
    public PasswordField mdpField;

    @FXML
    private Button bt_rd_connexion;

    private Connection connection;

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
        String email = mailField.getText();
        String password = mdpField.getText();

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
                String role = resultSet.getString("role").toLowerCase();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Problème de connexion à la base de données.");
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/inscription-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
