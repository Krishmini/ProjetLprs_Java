package controller;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class editerController {
    @FXML
    public TextField nomField;
    @FXML
    public TextField prenomField;
    @FXML
    public TextField mailField;
    @FXML
    public TextField mdpField;

    @FXML
    public Button editer;
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
    protected void onEditerButtonClick() {modifierEtudiant();
    }
    private void modifierEtudiant() {

        String sql = "UPDATE utilisateur SET nom=?, prenom=?, mail=?, mdp=? WHERE id_utilisateur=?";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nomField.getText());
            statement.setString(2, prenomField.getText());
            statement.setString(3, mailField.getText());
            statement.setString(4, mdpField.getText());


            int userId = 1;
            statement.setInt(5, userId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Étudiant modifié avec succès !");
            } else {
                System.out.println("Échec.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
