package Controller;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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

        String sql = "UPDATE utilisateur SET nom=:nom,prenom=:prenom,mail=:mail,mdp=:mdp WHERE id_utilisateur=:id_utilisateur;";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Étudiant modifié avec succès !");
            } else {
                System.out.println("Échec.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
