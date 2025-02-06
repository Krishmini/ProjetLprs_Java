package Controller;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class VueDossierInscriptionController {
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField heureField;
    @FXML
    public TextField filiere_interetField;

    @FXML
    public TextField motivation_etudiantField;

    @FXML
    public Button creer1;

    @FXML
    public void initialize(){
        try{
            Database db = new Database();
            Connection connection = (Connection) db.getConnexion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onCreer1ButtonClick() {
        montrerDossier();
    }

    private void montrerDossier(){
        String sql = "SELECT * FROM `dossierinscription` WHERE id_dossierInscription = id_dossierInscription;";

        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, datePicker.getValue().toString());
            statement.setString(2, heureField.getText());
            statement.setString(3, filiere_interetField.getText());
            statement.setString(4, motivation_etudiantField.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Dossier ajouté avec succès !");
            } else {
                System.out.println("Échec de l'ajout.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
