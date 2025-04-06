package controller;

import Model.*;
import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private Connection connection;
    @FXML
    public AnchorPane Ap_moins;

    public AnchorPane Main;
    @FXML
    private AnchorPane Ap_DmPr;

    @FXML
    private AnchorPane Ap_DmPr1;
    @FXML
    private AnchorPane Ap_mainMain;

    @FXML
    private AnchorPane an_commande;

    @FXML
    private AnchorPane an_compte;

    @FXML
    private AnchorPane an_utiliser;

    @FXML
    private AnchorPane ap_home;

    @FXML
    private AnchorPane ap_main;

    @FXML
    private Button bt_cmPr;

    @FXML
    private Button bt_envoyer;

    @FXML
    private Button bt_demande;

    @FXML
    private Button bt_demande1;

    @FXML
    private Button bt_home;

    @FXML
    private Button bt_logout;
    @FXML
    private TableView<StockQuantite> tb_stock1;

    @FXML
    private Button bt_utPr;

    @FXML
    private TableColumn<StockQuantite, String> row_description;

    @FXML
    private TableColumn<StockQuantite, Integer> row_id_stock;


    @FXML
    private TableColumn<StockQuantite, String> row_libelle;

    @FXML
    private TableColumn<StockQuantite, Integer> row_nombre_stock;

    @FXML
    private TableColumn<StockQuantite, Integer> row_ref_fournisseur;


    @FXML
    private Spinner<Integer> sp_id_stock;

    @FXML
    private Spinner<Integer> sp_idstock;

    @FXML
    private Spinner<Integer> sp_quantite2;

    @FXML
    private Spinner<Integer> sp_quantite;

    @FXML
    private BarChart<?, ?> sp_stock;

    @FXML
    private TableView<StockQuantite> tb_stock;

    @FXML
    private TableColumn<StockQuantite, String> tc_description;

    @FXML
    private TableColumn<StockQuantite, Integer> tc_idFournisseur;

    @FXML
    private TableColumn<StockQuantite, Integer> tc_idStock;

    @FXML
    private TableColumn<StockQuantite, String> tc_libelle;

    @FXML
    private TableColumn<StockQuantite, Integer> tc_quantite;

    @FXML
    private TextField tf_raison;

    @FXML
    private TextField tf_article;

    @FXML
    private TextField tf_recherche;

    @FXML
    private TextField tx_recherche;

    @FXML
    private Label txt_demande_accepte;
    @FXML
    private Label txt_demande_refusé;

    @FXML
    private Label txt_nouvelle_Demande;

    private Connection connect = null;
    private Database database = new Database();
    private PreparedStatement pst;
    private Statement st;
    private ResultSet rs;
    private Label myLabel;
    int currentValue;
    private Stage stage;

    private void barreDeRecherche() {
        ObservableList<StockQuantite> stockList = ajouterStockListeAvecQuantite();
        FilteredList<StockQuantite> filteredData = new FilteredList<>(stockList, p -> true);

        tf_recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stock -> {

                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (stock.getLibelle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (stock.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(stock.getId_stock()).contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });


        tb_stock.setItems(filteredData);
    }



    @FXML
    private void demandeCommande(ActionEvent event) {

        int idStock = sp_idstock.getValue();
        int quantite = sp_quantite2.getValue();
        String raison = tf_raison.getText();
        int utilisateurId = Model.SessionUtilisateur.getUtilisateurId();


        System.out.println("ID utilisateur courant : " + utilisateurId);

        if (raison.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer une raison.");
            return;
        }

        String sql = "INSERT INTO demandeFourniture (article, quantite, raison, approuver, ref_stock, ref_utilisateur) VALUES (?, ?, ?, NULL, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, getNomArticle(idStock));
            statement.setInt(2, quantite);
            statement.setString(3, raison);
            statement.setInt(4, idStock);
            statement.setInt(5, utilisateurId);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert("Succès", "Demande envoyée avec succès !");
                tf_raison.clear();
            } else {
                showAlert("Erreur", "Échec de la demande. Veuillez réessayer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Problème lors de l'insertion dans la base de données : " + e.getMessage());
        }
    }
    @FXML
    private void UseStock(ActionEvent event) {

        int idStock = sp_id_stock.getValue();
        int quantite = sp_quantite.getValue();


        int utilisateurId = Model.SessionUtilisateur.getUtilisateurId();

        System.out.println("ID utilisateur courant : " + utilisateurId);


        String sql = "UPDATE stock SET nombre_stock = nombre_stock - ? WHERE id_stock = ? AND nombre_stock >= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quantite);
            statement.setInt(2, idStock);
            statement.setInt(3, quantite);


            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Succès", "Stock mis à jour avec succès !");
            } else {
                showAlert("Erreur", "La quantité en stock est insuffisante ou une erreur est survenue.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Problème lors de l'insertion dans la base de données : " + e.getMessage());
        }
    }

    private String getNomArticle(int idStock) {
        String libelle = "Article inconnu";
        String sql = "SELECT libelle FROM stock WHERE id_stock = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idStock);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                libelle = rs.getString("libelle");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return libelle;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ObservableList<Quantite> ajouterStockListe2() {
        ObservableList<Quantite> stockListe2 = FXCollections.observableArrayList();
        String sql = "SELECT * FROM quantite ORDER BY ref_stock";

        connect = database.getConnexion();

        try {
            assert connect != null;
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Quantite quantite = new Quantite(
                        rs.getInt("id_quantite"),
                        rs.getInt("quantite"),
                        rs.getInt("ref_stock"),
                        rs.getInt("ref_commande")

                );
                stockListe2.add(quantite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stockListe2;
    }

    public ObservableList<StockQuantite> ajouterStockListeAvecQuantite() {
        ObservableList<StockQuantite> stockQuantiteListe = FXCollections.observableArrayList();
        Map<Integer, Stock> stockMap = new HashMap<>();

        String sqlStock = "SELECT * FROM stock ORDER BY id_stock";
        String sqlQuantite = "SELECT * FROM quantite ORDER BY ref_stock";

        connect = database.getConnexion();

        try {
            assert connect != null;
            pst = connect.prepareStatement(sqlStock);
            rs = pst.executeQuery();

            while (rs.next()) {
                Stock stock = new Stock(
                        rs.getInt("id_stock"),
                        rs.getString("libelle"),
                        rs.getInt("nombre_stock"),
                        rs.getString("description"),
                        rs.getInt("ref_fournisseur")
                );
                stockMap.put(stock.getId_stock(), stock);
            }
            rs.close();
            pst.close();

            pst = connect.prepareStatement(sqlQuantite);
            rs = pst.executeQuery();

            while (rs.next()) {
                int refStock = rs.getInt("ref_stock");
                if (stockMap.containsKey(refStock)) {
                    Quantite quantite = new Quantite(
                            rs.getInt("id_quantite"),
                            rs.getInt("quantite"),
                            refStock,
                            rs.getInt("ref_commande")
                    );

                    StockQuantite stockQuantite = new StockQuantite(stockMap.get(refStock), quantite);
                    stockQuantiteListe.add(stockQuantite);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        for (StockQuantite sq : stockQuantiteListe) {
            System.out.println("Stock: " + sq.getLibelle() + ", Quantité: " + sq.getQuantite1());
        }

        return stockQuantiteListe;
    }

    public void ajouterStockVueListeAvecQuantite2() {
        ObservableList<StockQuantite> stockQuantiteListe = ajouterStockListeAvecQuantite();

        row_id_stock.setCellValueFactory(new PropertyValueFactory<>("id_stock"));
        row_libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        row_nombre_stock.setCellValueFactory(new PropertyValueFactory<>("quantite1"));
        row_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        row_ref_fournisseur.setCellValueFactory(new PropertyValueFactory<>("refFournisseur"));

        tb_stock1.setItems(stockQuantiteListe);
    }

    public void ajouterStockVueListeAvecQuantite() {
        ObservableList<StockQuantite> stockQuantiteListe = ajouterStockListeAvecQuantite();


        tc_idStock.setCellValueFactory(new PropertyValueFactory<>("id_stock"));
        tc_libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tc_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tc_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite1"));
        tc_idFournisseur.setCellValueFactory(new PropertyValueFactory<>("refFournisseur"));


        tb_stock.getItems().addAll(stockQuantiteListe);

        for (StockQuantite sq : stockQuantiteListe) {
            System.out.println("Stock: " + sq.getLibelle() + ", Quantité: " + sq.getQuantite1());
        }
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == bt_home) {
            ap_home.setVisible(true);
            an_commande.setVisible(false);
            an_utiliser.setVisible(false);
        }
        else if (event.getSource() == bt_cmPr) {
            ap_home.setVisible(false);
            an_commande.setVisible(true);
            an_utiliser.setVisible(false);
        }
        else if (event.getSource() == bt_utPr) {
            ap_home.setVisible(false);
            an_commande.setVisible(false);
            an_utiliser.setVisible(true);
        }
    }

    public void switchFormDemande(ActionEvent event) {
        if (event.getSource() == bt_demande1) {
            Ap_DmPr.setVisible(true);
            Ap_DmPr1.setVisible(false);

        } else if (event.getSource() == bt_demande) {
            Ap_DmPr.setVisible(false);
            Ap_DmPr1.setVisible(true);

        }
    }



    public void minimize() {
        Stage stage = (Stage) Ap_mainMain.getScene().getWindow();
        stage.setIconified(true);

    }

    public void close(){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Database db = new Database();
        connection = db.getConnexion();

        ajouterStockVueListeAvecQuantite();
        ajouterStockVueListeAvecQuantite2();


        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(0);
        sp_idstock.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(0);
        sp_id_stock.setValueFactory(valueFactory4);


        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory2.setValue(0);
        sp_quantite.setValueFactory(valueFactory2);

        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory3.setValue(0);
        sp_quantite2.setValueFactory(valueFactory3);


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
}

