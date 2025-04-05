package controller;


import Model.RendezVous;
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

public class OrganiserRDVController {
    public RadioButton Salle1;
    public RadioButton Salle2;
    public RadioButton Salle3;
    @FXML
    public DatePicker date;
    @FXML
    public TextField heure;
    public Button creer;
    public TextField ref;
    public Button btnRetour;
    public Label logout;
    public TextField heure1;
    public HBox Main;
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
    public void initialize() {
        try {
            Database db = new Database();
            Connection connection = (Connection) db.getConnexion();

        } catch (Exception e) {
            e.printStackTrace();
        }
        addRendezVousShow();
    }

    private boolean isRefDossierValid(String refDossier) {
        String sql = "SELECT COUNT(*) FROM dossierinscription WHERE id_dossierInscription = ?";
        try (Connection connect = new Database().getConnexion();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setString(1, refDossier);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                return result.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

        if (!isRefDossierValid(refChoisie)) {
            showAlert("Erreur", "Référence dossier invalide !");
            return;
        }

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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