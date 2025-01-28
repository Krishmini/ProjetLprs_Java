package Controller;

import Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtils extends Database {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String nom, String prenom, String mdp, String email, String role) {
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("/appli/lprsjavafx/login.fxml"));
            root = loader.load();

            if (email != null && mdp != null) {
                LoggedInController loggedInController = loader.getController();
                loggedInController.setLabelBienvenue(nom, prenom);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur : Impossible de charger le fichier FXML au chemin " + fxmlFile);
            return;
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
    public static void inscriptionUtilisateur(ActionEvent event, String nom, String prenom, String mdp, String email, String role) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

    }
}
