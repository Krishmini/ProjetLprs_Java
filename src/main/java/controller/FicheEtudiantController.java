package controller;

import Model.FicheEtudiant;
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

public class FicheEtudiantController {

    public Button btnRetour;
    @FXML
    public TextField nom_e;
    @FXML
    public TextField prenom_e;
    @FXML
    public TextField dernier_diplome;
    @FXML
    public TextField email_e;
    @FXML
    public TextField telephone_e;
    @FXML
    public TextField ville_e;
    @FXML
    public TextField cp_e;
    @FXML
    public TextField rue_e;
    @FXML
    public Button creer;
    public TableView addEtudiant_tableView;
    public TableColumn addEtudiant_row_nom_e;
    public TableColumn addEtudiant_row_prenom_e;
    public TableColumn addEtudiant_row_diplome;
    public TableColumn addEtudiant_row_email_e;
    public TableColumn addEtudiant_row_telephone_e;
    public TableColumn addEtudiant_row_ville_e;
    public TableColumn addEtudiant_row_cp_e;
    public TableColumn addEtudiant_row_rue_e;
    public Button logoutButton;
    public HBox Main;
    private ObservableList<FicheEtudiant> addFicheEtudiant;
    @FXML
    private Label welcomeText;
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
    }
    @FXML
    protected void onCreerButtonClick() {
        enregistrerEtudiant();
    }
    private void enregistrerEtudiant() {
        String sql = "INSERT INTO ficheetudiant (nom_e, prenom_e, dernier_diplome, email_e, telephone_e, ville_e, cp_e, rue_e) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nom_e.getText());
            statement.setString(2, prenom_e.getText());
            statement.setString(3, dernier_diplome.getText());
            statement.setString(4, email_e.getText());
            statement.setString(5, telephone_e.getText());
            statement.setString(6, ville_e.getText());
            statement.setString(7, cp_e.getText());
            statement.setString(8, rue_e.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Étudiant ajouté avec succès !");
                addFicheEtudiantShow();
                addEtudiant_tableView.getItems().clear();
                addEtudiant_tableView.getItems().addAll(addFicheEtudiantData());

                nom_e.clear();
                prenom_e.clear();
                dernier_diplome.clear();
                email_e.clear();
                telephone_e.clear();
                ville_e.clear();
                cp_e.clear();
                rue_e.clear();

            } else {
                System.out.println("Échec de l'ajout.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<FicheEtudiant> addFicheEtudiantData() {

        ObservableList<FicheEtudiant> ficheEtudiant = FXCollections.observableArrayList();

        String sql = "SELECT * FROM ficheetudiant";

        connect = database.getConnexion();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            System.out.println("Requête SQL exécutée : " + sql);

            while (result.next()) {
                System.out.println("Chargement dossier : " + result.getString("nom_e") + " " + result.getString("prenom_e"));
                FicheEtudiant dossier = new FicheEtudiant(
                        result.getString("nom_e"),
                        result.getString("prenom_e"),
                        result.getString("dernier_diplome"),
                        result.getString("email_e"),
                        result.getString("telephone_e"),
                        result.getString("ville_e"),
                        result.getString("cp_e"),
                        result.getString("rue_e")
                );

                ficheEtudiant.add(dossier);
            }

            System.out.println("Nombre de dossiers chargés : " + ficheEtudiant.size());


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

        return ficheEtudiant;
    }

    public void addFicheEtudiantShow() {
        addFicheEtudiant = addFicheEtudiantData();

        addEtudiant_row_nom_e.setCellValueFactory(new PropertyValueFactory<>("nom_e"));
        addEtudiant_row_prenom_e.setCellValueFactory(new PropertyValueFactory<>("prenom_e"));
        addEtudiant_row_diplome.setCellValueFactory(new PropertyValueFactory<>("dernier_diplome"));
        addEtudiant_row_email_e.setCellValueFactory(new PropertyValueFactory<>("email_e"));
        addEtudiant_row_telephone_e.setCellValueFactory(new PropertyValueFactory<>("telephone_e"));
        addEtudiant_row_ville_e.setCellValueFactory(new PropertyValueFactory<>("ville_e"));
        addEtudiant_row_cp_e.setCellValueFactory(new PropertyValueFactory<>("cp_e"));
        addEtudiant_row_rue_e.setCellValueFactory(new PropertyValueFactory<>("rue_e"));

        addEtudiant_tableView.layout();
        addEtudiant_tableView.setItems(addFicheEtudiant);
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
