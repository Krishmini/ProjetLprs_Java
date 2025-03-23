package controller;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.DossierInscription;
import Model.FicheEtudiant;

import javax.print.attribute.standard.JobStateReason;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EspaceSecretaire implements Initializable{


    public AnchorPane Main;
    public Button accueil;
    public Button ficheEtudiant;
    public Button dossierInsc;
    public Label logout;
    public FontAwesomeIconView logout1;
    public AnchorPane ins;
    public AnchorPane co;
    public TextField rue_e;
    public TextField cp_e;
    public TextField ville_e;
    public TextField telephone_e;
    public TextField email_e;
    public TextField dernier_diplome;
    public TextField prenom_e;
    public TextField nom_e;
    public AnchorPane ins1;
    public DatePicker date;
    public TextField heure;
    public TextField filiere_interet;
    public TextField motivation_etudiant;

    public Button creer1;
    public Button creer;
    public AnchorPane fe;
    public AnchorPane ac;
    public AnchorPane di;
    public Button logoutButton;
    public AnchorPane mo;
    public Button modifier;
    public TextField mdp;
    public TextField email;
    public TextField prenom1;
    public TextField nom1;
    public AnchorPane sup;
    public Button supprimer;
    public Button supprimerr;
    public Button modifierr;
    public TextField nomm;
    public TextField prenomm;
    public TextField emailActuel;
    public TextField emailActuel1;
    @FXML
    private TableView<DossierInscription>addDossier_tableView;
    public TableColumn<DossierInscription, String>addDossier_row_nomm;
    public TableColumn<DossierInscription, String>addDossier_row_prenomm;
    public TableColumn<DossierInscription, String>addDossier_row_date;
    public TableColumn<DossierInscription, String>addDossier_row_heure;
    public TableColumn<DossierInscription, String>addDossier_row_filiere_interet;
    public TableColumn<DossierInscription, String>addDossier_row_motivation_etudiant;
    private ObservableList<DossierInscription> addDossierInscription;

    @FXML
    private TableView<FicheEtudiant>addEtudiant_tableView;
    public TableColumn<FicheEtudiant, String>addEtudiant_row_nom_e;
    public TableColumn<FicheEtudiant, String>addEtudiant_row_prenom_e;
    public TableColumn<FicheEtudiant, String>addEtudiant_row_diplome;
    public TableColumn<FicheEtudiant, String>addEtudiant_row_email_e;
    public TableColumn<FicheEtudiant, String>addEtudiant_row_telephone_e;
    public TableColumn<FicheEtudiant, String>addEtudiant_row_ville_e;
    public TableColumn<FicheEtudiant, String>addEtudiant_row_cp_e;
    public TableColumn<FicheEtudiant, String>addEtudiant_row_rue_e;
    private ObservableList<FicheEtudiant> addFicheEtudiant;

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
        addFicheEtudiantShow();
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
    public void switchForm(ActionEvent event) {

        if (event.getSource() == accueil) {
            ac.setVisible(true);
            fe.setVisible(false);
            di.setVisible(false);
            mo.setVisible(false);
            sup.setVisible(false);
        }
        else if (event.getSource() == ficheEtudiant) {
            ac.setVisible(false);
            fe.setVisible(true);
            di.setVisible(false);
            mo.setVisible(false);
            sup.setVisible(false);
        }
        else if (event.getSource() == dossierInsc) {
            ac.setVisible(false);
            fe.setVisible(false);
            di.setVisible(true);
            mo.setVisible(false);
            sup.setVisible(false);
        }
        else if (event.getSource() == modifierr) {
            ac.setVisible(false);
            fe.setVisible(false);
            di.setVisible(false);
            mo.setVisible(true);
            sup.setVisible(false);
        }
        else if (event.getSource() == supprimerr) {
            ac.setVisible(false);
            fe.setVisible(false);
            di.setVisible(false);
            mo.setVisible(false);
            sup.setVisible(true);
        }
    }

    public void logout1(ActionEvent event){
        stage = (Stage) Main.getScene().getWindow();
        System.out.println("You successfully logged out!");
        stage.close();
    }

    @FXML
    protected void onModifierButtonClick(){
        modifierCompte();
    }

    private void modifierCompte(){
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, mail = ?, mdp = ? WHERE mail = ?";

        try (Connection connection = new Database().getConnexion();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nom1.getText());
            statement.setString(2, prenom1.getText());
            statement.setString(3, email.getText());
            statement.setString(4, mdp.getText());
            statement.setString(5, emailActuel.getText());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Compte modifié avec succès !");

                nom1.clear();
                prenom1.clear();
                email.clear();
                mdp.clear();
                emailActuel.clear();

            } else {
                System.out.println("Aucun compte trouvé avec cet email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onSupprimerButtonClick(ActionEvent event) {
        supprimerCompte();
    }
    private void supprimerCompte() {
        String emailUtilisateur = emailActuel1.getText();


        String sql = "DELETE FROM `utilisateur` WHERE mail = ?;";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, emailUtilisateur);


            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                Stage stage = (Stage) Main.getScene().getWindow();
                System.out.println("Étudiant supprimé avec succès");
                stage.close();
            } else {
                System.out.println("Échec.");
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

}
