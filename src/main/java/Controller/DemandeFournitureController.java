package Controller;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class DemandeFournitureController {
    @FXML
    public TextField articleField;
    @FXML
    public TextField quantiteField;
    @FXML
    public TextField raisonField;
    public Button demander;

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
























































    protected void onDemanderButtonClick(){DemanderFourniture();}

    private void DemanderFourniture(){
        String sql = "INSERT INTO demandefourniture (article, quantite, raison) VALUES (?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, articleField.getText());
            statement.setString(2, quantiteField.getText());
            statement.setString(3, raisonField.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Demande de fourniture ajouté avec succès !");
            } else {
                System.out.println("Échec de l'ajout.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    }