package controller;

import Model.DemandeFourniture;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Model.DossierInscription;
import Model.RendezVous;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EspaceProfesseur implements Initializable {
    public AnchorPane Main;
    public Button accueil;
    public Button dossierInscription;
    public Button rdv;
    public Label logout;
    public Button logoutButton;
    public Button supprimerr;
    public Button modifierr;
    public Button demandeFourniture;
    public AnchorPane ac;
    public AnchorPane rv;
    public AnchorPane di;
    public Button creer;
    public TextField heure1;
    public TextField salle1;
    public DatePicker date;
    public AnchorPane df;
    public AnchorPane mo;
    public Button modifier;
    public TextField mdp;
    public TextField email;
    public TextField prenom1;
    public TextField nom1;
    public AnchorPane sup;
    public Button supprimer;
    public RadioButton Salle1;
    public RadioButton Salle2;
    public RadioButton Salle3;
    public TextField emailActuel;
    public TextField emailActuel1;
    public TableColumn addDossier_row_id_dossierInscription;
    public TextField ref;
    @FXML
    private TableView<DemandeFourniture> addFourniture_tableView;
    public TableColumn<DemandeFourniture, Integer> addFourniture_row_id;
    public TableColumn<DemandeFourniture, String> addFourniture_row_article;
    public TableColumn<DemandeFourniture, Integer> addFourniture_row_quantite;
    public TableColumn<DemandeFourniture, String> addFourniture_row_raison;
    public Spinner sp_quantite_demande;
    public TextField sp_raison;
    public Button bt_envoyer;
    public TextField sp_article;
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
    private TableView<RendezVous>addRDV_tableView;
    public TableColumn<RendezVous, String>addRDV_row_salle;
    public TableColumn<RendezVous, String>addRDV_row_date;
    public TableColumn<RendezVous, String>addRDV_row_heure;
    public TableColumn<RendezVous, String>addRDV_row_ref_dossierInscription;
    private ObservableList<RendezVous> addRendezVous;
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
        addDossierInscriptionShow();
        addRendezVousShow();
        addDemandeFournitureShow();

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(1);
        sp_quantite_demande.setValueFactory(valueFactory);
    }

    private boolean isSalleDisponible(String salle, String date, String heure) {
        String sql = "SELECT COUNT(*) FROM rendezvous WHERE salle = ? AND date = ? AND heure = ?";
        boolean isDisponible = true;

        try (Connection connect = new Database().getConnexion();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setString(1, salle);
            prepare.setString(2, date);
            prepare.setString(3, heure);

            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                int count = result.getInt(1);
                if (count > 0) {
                    isDisponible = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDisponible;
    }

    @FXML
    protected void onValiderButtonClick() {
        ajouterRdv();
    }

    private void ajouterRdv() {
        String salleSelectionne = "";

        if (Salle1.isSelected()) {
            salleSelectionne = "Salle 1";
        } else if (Salle2.isSelected()) {
            salleSelectionne = "Salle 2";
        } else if (Salle3.isSelected()) {
            salleSelectionne = "Salle 3";
        }

        String dateChoisie = date.getValue().toString();
        String heureChoisie = heure1.getText();
        String refChoisie = ref.getText();

        if (isSalleDisponible(salleSelectionne, dateChoisie, heureChoisie)) {
            String sql = "INSERT INTO rendezvous (salle, date, heure, ref_dossierInscription) VALUES (?, ?, ?, ?)";
            try (Connection connection = new Database().getConnexion();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, salleSelectionne);
                statement.setString(2, dateChoisie);
                statement.setString(3, heureChoisie);
                statement.setString(4, refChoisie);



                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("RDV ajouté avec succès !");
                    addRendezVousShow();
                    addRDV_tableView.getItems().clear();
                    addRDV_tableView.getItems().addAll(addRendezVousData());

                    date.getEditor().clear();
                    heure1.clear();
                    ref.clear();

                } else {
                    System.out.println("Échec de l'ajout.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Cette salle est déjà prise à cette date et heure !");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void switchForm(ActionEvent event) {

        if (event.getSource() == accueil) {
            ac.setVisible(true);
            di.setVisible(false);
            rv.setVisible(false);
            df.setVisible(false);
            mo.setVisible(false);
            sup.setVisible(false);
        }
        else if (event.getSource() == dossierInscription) {
            ac.setVisible(false);
            di.setVisible(true);
            rv.setVisible(false);
            df.setVisible(false);
            mo.setVisible(false);
            sup.setVisible(false);
        }
        else if (event.getSource() == rdv) {
            ac.setVisible(false);
            di.setVisible(false);
            rv.setVisible(true);
            df.setVisible(false);
            mo.setVisible(false);
            sup.setVisible(false);
        }
        else if (event.getSource() == demandeFourniture) {
            ac.setVisible(false);
            di.setVisible(false);
            rv.setVisible(false);
            df.setVisible(true);
            mo.setVisible(false);
            sup.setVisible(false);
        }
        else if (event.getSource() == modifierr) {
            ac.setVisible(false);
            di.setVisible(false);
            rv.setVisible(false);
            df.setVisible(false);
            mo.setVisible(true);
            sup.setVisible(false);
        }
        else if (event.getSource() == supprimerr) {
            ac.setVisible(false);
            di.setVisible(false);
            rv.setVisible(false);
            df.setVisible(false);
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


    public ObservableList<RendezVous> addRendezVousData() {

        ObservableList<RendezVous> rendezVous = FXCollections.observableArrayList();

        String sql = "SELECT * FROM rendezvous";

        connect = database.getConnexion();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            System.out.println("Requête SQL exécutée : " + sql);

            while (result.next()) {
                System.out.println("Chargement dossier : " + result.getString("salle") + " " + result.getString("date"));
                RendezVous rdv = new RendezVous(
                        result.getString("salle"),
                        result.getString("date"),
                        result.getString("heure"),
                        result.getInt("ref_dossierInscription")
                );

                rendezVous.add(rdv);
            }
            System.out.println("Nombre de dossiers chargés : " + rendezVous.size());

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

        return rendezVous;
    }

    public void addRendezVousShow() {
        addRendezVous = addRendezVousData();

        addRDV_row_salle.setCellValueFactory(new PropertyValueFactory<>("salle"));
        addRDV_row_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        addRDV_row_heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
        addRDV_row_ref_dossierInscription.setCellValueFactory(new PropertyValueFactory<>("ref_dossierInscription"));

        addRDV_tableView.layout();
        addRDV_tableView.setItems(addRendezVous);
    }

    @FXML
    protected void onDemanderButtonClick(){DemanderFourniture();}

    private void DemanderFourniture(){
        String sql = "INSERT INTO demandefourniture (article, quantite, raison) VALUES (?, ?, ?)";
        try (Connection connection = new Database().getConnexion();
             java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, sp_article.getText());
            statement.setInt(2, (Integer) sp_quantite_demande.getValue());
            statement.setString(3, sp_raison.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Demande de fourniture ajouté avec succès !");

                sp_article.clear();
                sp_raison.clear();

            } else {
                System.out.println("Échec de l'ajout.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<DemandeFourniture> addDemandeFournitureData() {

        ObservableList<DemandeFourniture> demandeFourniture = FXCollections.observableArrayList();

        String sql = "SELECT * FROM demandefourniture";

        connect = database.getConnexion();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                DemandeFourniture fourniture = new DemandeFourniture(
                        result.getString("article"),
                        result.getInt("quantite"),
                        result.getString("raison")
                );
                demandeFourniture.add(fourniture);
            }
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

        return demandeFourniture;
    }

    private ObservableList<DemandeFourniture> addDemandeFourniture;

    public void addDemandeFournitureShow() {
        addDemandeFourniture = addDemandeFournitureData();

        addFourniture_row_article.setCellValueFactory(new PropertyValueFactory<>("article"));
        addFourniture_row_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        addFourniture_row_raison.setCellValueFactory(new PropertyValueFactory<>("raison"));

        addFourniture_tableView.setItems(null);
        addFourniture_tableView.layout();
        addFourniture_tableView.setItems(addDemandeFourniture);
    }

}
