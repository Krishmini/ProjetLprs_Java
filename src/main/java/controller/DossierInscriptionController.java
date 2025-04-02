package controller;

import Model.DossierInscription;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class DossierInscriptionController {
    public Button btnRetour;
    @FXML
    public DatePicker date;
    @FXML
    public TextField heure;
    @FXML
    public TextField filiere_interet;

    @FXML
    public TextField motivation_etudiant;

    @FXML
    public Button Creer1;
    public TextField nomm;
    public TextField prenomm;
    public TableColumn addDossier_row_nomm;
    public TableColumn addDossier_row_prenomm;
    public TableColumn addDossier_row_date;
    public TableColumn addDossier_row_heure;
    public TableColumn addDossier_row_filiere_interet;
    public TableColumn addDossier_row_motivation_etudiant;
    public Label logout;
    public Button logoutButton;
    public HBox Main;
    @FXML
    private TableView<DossierInscription>addDossier_tableView;

    private ObservableList<DossierInscription> addDossierInscription;
    @FXML
    private Label welcomeText;
    private Stage stage;
    private ResultSet result;
    private PreparedStatement prepare;
    private Connection connect;
    private Database database = new Database();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Database db = new Database();
            Connection connection = (Connection) db.getConnexion();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Méthode initialize() appelée !");
        addDossierInscriptionShow();
    }

    @FXML
    protected void onCreer1ButtonClick() {
        enregistrerDossier();
    }

    private void enregistrerDossier(){
        String sql = "INSERT INTO dossierinscription (nomm, prenomm, date, heure, filiere_interet, motivation_etudiant) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nomm.getText());
            statement.setString(2, prenomm.getText());
            statement.setString(3, date.getValue().toString());
            statement.setString(4, heure.getText());
            statement.setString(5, filiere_interet.getText());
            statement.setString(6, motivation_etudiant.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Dossier ajouté avec succès !");
                addDossierInscriptionShow();
                addDossier_tableView.getItems().clear();
                addDossier_tableView.getItems().addAll(addDossierInscriptionData());

                nomm.clear();
                prenomm.clear();
                date.getEditor().clear();
                heure.clear();
                filiere_interet.clear();
                motivation_etudiant.clear();

            } else {
                System.out.println("Échec de l'ajout.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<DossierInscription> addDossierInscriptionData() {

        ObservableList<DossierInscription> dossierInscription = FXCollections.observableArrayList();

        String sql = "SELECT * FROM dossierinscription";

        connect = database.getConnexion();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            System.out.println("Requête SQL exécutée : " + sql);

            while (result.next()) {
                System.out.println("Chargement dossier : " + result.getString("nomm") + " " + result.getString("prenomm"));
                DossierInscription dossier = new DossierInscription(
                        result.getInt("id_dossierInscription"),
                        result.getString("nomm"),
                        result.getString("prenomm"),
                        result.getString("date"),
                        result.getString("heure"),
                        result.getString("filiere_interet"),
                        result.getString("motivation_etudiant")
                );

                dossierInscription.add(dossier);
            }

            System.out.println("Nombre de dossiers chargés : " + dossierInscription.size());


        }catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dossierInscription;
    }

    public void addDossierInscriptionShow() {
        addDossierInscription = addDossierInscriptionData();

        addDossier_row_nomm.setCellValueFactory(new PropertyValueFactory<>("nomm"));
        addDossier_row_prenomm.setCellValueFactory(new PropertyValueFactory<>("prenomm"));
        addDossier_row_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        addDossier_row_heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
        addDossier_row_filiere_interet.setCellValueFactory(new PropertyValueFactory<>("filiere_interet"));
        addDossier_row_motivation_etudiant.setCellValueFactory(new PropertyValueFactory<>("motivation_etudiant"));

        addDossier_tableView.layout();
        addDossier_tableView.setItems(addDossierInscription);
    }

    public void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/EspaceSecretaire.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logout1(ActionEvent event){
        stage = (Stage) Main.getScene().getWindow();
        System.out.println("You successfully logged out!");
        stage.close();
    }
}
