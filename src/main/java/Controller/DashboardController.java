package Controller;

import Model.StockQuantite;
import Model.Quantite;
import database.Database;
import Model.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

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
    private Button bt_home;

    @FXML
    private Button bt_logout;

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

    private Spinner<Integer> sp_id_stock;

    @FXML
    private Spinner<Integer> sp_idstock;

    @FXML
    private Spinner<Integer> sp_quantite;

    @FXML
    private Spinner<Integer> sp_quantite1;

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
    private TextField tf_recherche;

    @FXML
    private TextField tx_article;

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
    int currentValue ;

    public void demandeCommande(){

        String sql = "INSERT INTO demandefourniture (article, quantite, raison, ref_stock, ref_utilisateur)"
                + " VALUES ( ?, ?, ?, ?, ?, ?)";

        connect = database.getConnexion();

        Alert alert;
        try {
            if(sp_id_stock.getValue().describeConstable().isEmpty()||sp_quantite.getValue().describeConstable().isEmpty()||tf_raison.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();


            }

            pst = connect.prepareStatement(sql);
            pst.setInt(1, sp_idstock.getValue());
            pst.setInt(2, sp_quantite.getValue());
            pst.setString(3, tf_raison.getText());
            pst.executeUpdate();



        }catch (Exception e){e.printStackTrace();

        }


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

        String sqlStock = "SELECT * FROM stock ORDER BY id_stock";
        String sqlQuantite = "SELECT * FROM quantite ORDER BY ref_stock";

        connect = database.getConnexion();

        try {
            assert connect != null;


            pst = connect.prepareStatement(sqlStock);
            ResultSet rsStock = pst.executeQuery();


            pst = connect.prepareStatement(sqlQuantite);
            ResultSet rsQuantite = pst.executeQuery();

            while (rsStock.next() && rsQuantite.next()) {
                Stock stock = new Stock(
                        rsStock.getInt("id_stock"),
                        rsStock.getString("libelle"),
                        rsStock.getInt("nombre_stock"),
                        rsStock.getString("description"),
                        rsStock.getInt("ref_fournisseur")
                );

                Quantite quantite = new Quantite(
                        rsQuantite.getInt("id_quantite"),
                        rsQuantite.getInt("quantite"),
                        rsQuantite.getInt("ref_stock"),
                        rsQuantite.getInt("ref_commande")
                );


                StockQuantite stockQuantite = new StockQuantite(stock, quantite);
                stockQuantiteListe.add(stockQuantite);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stockQuantiteListe;
    }

    public void ajouterStockVueListeAvecQuantite() {
        ObservableList<StockQuantite> stockQuantiteListe = ajouterStockListeAvecQuantite();

        tc_idStock.setCellValueFactory(new PropertyValueFactory<>("idStock"));
        tc_libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        tc_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tc_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        tc_idFournisseur.setCellValueFactory(new PropertyValueFactory<>("refFournisseur"));


        tb_stock.setItems(stockQuantiteListe);
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

    public void logout(){
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Voulez vous vraiment vous deconnecter ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK){

                Parent root = FXMLLoader.load(getClass().getResource("/appli/connexion.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
            }

        }catch (Exception e){
            e.printStackTrace();
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

        ajouterStockVueListeAvecQuantite();
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(1);
        sp_idstock.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory2 =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        valueFactory.setValue(1);
        sp_quantite1.setValueFactory(valueFactory2);


    }


    }

