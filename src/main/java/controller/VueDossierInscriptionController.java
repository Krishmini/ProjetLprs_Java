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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class VueDossierInscriptionController {
    public Button btnRetour;
    public Button editer;
    public Label logout;
    public TableColumn addDossier_row_id_dossierInscription;
    public HBox Main;
    @FXML
    private TableView<DossierInscription>addDossier_tableView;
    public TableColumn<DossierInscription, String>addDossier_row_nomm;
    public TableColumn<DossierInscription, String>addDossier_row_prenomm;
    public TableColumn<DossierInscription, String>addDossier_row_date;
    public TableColumn<DossierInscription, String>addDossier_row_heure;
    public TableColumn<DossierInscription, String>addDossier_row_filiere_interet;
    public TableColumn<DossierInscription, String>addDossier_row_motivation_etudiant;
    private ObservableList<DossierInscription> addDossierInscription;

    private Connection connection;
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField heureField;
    @FXML
    public TextField filiere_interetField;

    @FXML
    public TextField motivation_etudiantField;
    @FXML
    public Button montrer;
    private Stage stage;
    private ResultSet result;
    private PreparedStatement prepare;
    private Connection connect;
    private Database database = new Database();
    @FXML
    public void initialize() {
        try {
            Database db = new Database();
            Connection connection = (Connection) db.getConnexion();

        } catch (Exception e) {
            e.printStackTrace();
        }
        addDossierInscriptionShow();
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

        addDossier_row_id_dossierInscription.setCellValueFactory(new PropertyValueFactory<>("id_dossierInscription"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/EspaceProfesseur.fxml"));
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