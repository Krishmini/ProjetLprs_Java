package controller;


import Model.DemandeFourniture;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DashboardGestionaireController {

    @FXML
    private AnchorPane Ap_DmPr;

    @FXML
    private AnchorPane Ap_DmPr1;

    @FXML
    private AnchorPane Main;

    @FXML
    private AnchorPane an_commande;

    @FXML
    private AnchorPane an_compte;

    @FXML
    private AnchorPane ap_home;

    @FXML
    private AnchorPane ap_main;

    @FXML
    private Button bt_cmPr;
    @FXML
    private Button bt_cmPr2;

    @FXML
    private Button bt_demande;

    @FXML
    private Button bt_demande1;

    @FXML
    private Button bt_envoyer1;

    @FXML
    private Button bt_envoyer11;

    @FXML
    private Button bt_home;

    @FXML
    private Button bt_miseAJour;

    @FXML
    private Button btnRetour;

    @FXML
    private Button logoutButton;
    @FXML
    private Button Ap_mainMain;

    @FXML
    private RadioButton rd_non;

    @FXML
    private RadioButton rd_oui;

    @FXML
    private Spinner<Integer> sp_fournisseur;

    @FXML
    private Spinner<Integer> sp_idDemande;

    @FXML
    private Spinner<?> sp_idstock;

    @FXML
    private Spinner<?> sp_quantite2;

    @FXML
    private BarChart<?, ?> sp_stock;

    @FXML
    private TableView<DemandeFourniture> tb_stock;

    @FXML
    private TableColumn<DemandeFourniture, Integer> tc_idStock;

    @FXML
    private TableColumn<DemandeFourniture, String> tc_libelle;

    @FXML
    private TableColumn<DemandeFourniture, Integer> tc_quantite;

    @FXML
    private TableColumn<DemandeFourniture, String> tc_description;

    @FXML
    private TableColumn<DemandeFourniture, String> tc_idFournisseur;

    @FXML
    private TextField tf_raison;

    @FXML
    private TextField tx_recherche;

    @FXML
    private Label txt_demande_accepte;

    @FXML
    private Label txt_demande_refuse;

    @FXML
    private Label txt_nouvelle_Demande;

    private Stage stage;



    public void close(){
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) Ap_mainMain.getScene().getWindow();
        stage.setIconified(true);

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == bt_home) {
            ap_home.setVisible(true);
            an_commande.setVisible(false);
        }
        else if (event.getSource() == bt_cmPr) {
            ap_home.setVisible(false);
            an_commande.setVisible(true);
        }
    }
    public void logout1(ActionEvent event){
        stage = (Stage) Main.getScene().getWindow();
        System.out.println("You successfully logged out!");
        stage.close();
    }

    public void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appli/EspaceGestionnaire.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void ajouterStockVueListeAvecQuantite() {
        ObservableList<DemandeFourniture> listeDemandes = FXCollections.observableArrayList();

        String url = "jdbc:mysql://localhost:3306/lprs_javafx";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM demandefourniture";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                DemandeFourniture demande = new DemandeFourniture(
                        rs.getInt("id_demandeFourniture"),
                        rs.getString("article"),
                        rs.getInt("quantite"),
                        rs.getString("raison"),
                        rs.getBoolean("approuver"),
                        rs.getInt("ref_stock"),
                        rs.getInt("ref_utilisateur")
                );

                listeDemandes.add(demande);
            }

            tb_stock.setItems(listeDemandes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void changerStatutDemande(ActionEvent event) {

        if (!rd_oui.isSelected() && !rd_non.isSelected()) {
            System.out.println("Veuillez sélectionner Oui ou Non.");
            return;
        }


        Integer idDemande = (Integer) sp_idDemande.getValue();
        if (idDemande == null) {
            System.out.println("Veuillez saisir un ID de demande.");
            return;
        }

        boolean approuver = rd_oui.isSelected();

        String url = "jdbc:mysql://localhost:3306/lprs_javafx";
        String user = "root";
        String password = "";

        String updateQuery = "UPDATE demandefourniture SET approuver = ? WHERE id_demandeFourniture = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setBoolean(1, approuver);
            pstmt.setInt(2, idDemande);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Statut mis à jour avec succès.");
                ajouterStockVueListeAvecQuantite();
            } else {
                System.out.println("Aucune demande trouvée avec cet ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        tc_idStock.setCellValueFactory(new PropertyValueFactory<>("id_demandeFourniture"));
        tc_libelle.setCellValueFactory(new PropertyValueFactory<>("article"));
        tc_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tc_description.setCellValueFactory(new PropertyValueFactory<>("raison"));
        tc_idFournisseur.setCellValueFactory(cellData -> {
            Boolean approuve = cellData.getValue().getApprouver();
            String status = (approuve != null && approuve) ? "Approuvé" : "En attente";
            return new SimpleStringProperty(status);
        });

        ajouterStockVueListeAvecQuantite();

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(0);
        sp_idDemande.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(0);
        sp_fournisseur.setValueFactory(valueFactory2);

    }

}
