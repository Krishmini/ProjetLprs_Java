package Controller;

import Database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils extends Database {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String nom, String prenom, String mdp, String mail, String role) {
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            root = loader.load();

            if (mail != null && mdp != null) {
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
    public static void inscriptionUtilisateur(ActionEvent event, String nom, String prenom, String mdp, String mail, String role) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lprs_javafx?serverTimezone=UTC","root", "");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM utilisateur WHERE mail = ?");
            psCheckUserExists.setString(1,mail);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("L'email existe déjà !");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Vous ne pouvez pas utiliser ce mail !");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("INSERT INTO utilisateur(role, nom, prenom, mail, mdp) VALUES (?,?,?,?,?)");
                psInsert.setString(1, role);
                psInsert.setString(2,nom);
                psInsert.setString(3,prenom);
                psInsert.setString(4,mail);
                psInsert.setString(5,mdp);
                psInsert.executeUpdate();

                changeScene(event, "logged-in.fxml", "Bienvenue!", nom, prenom, mdp, mail, role);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try{
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void loginUtilisateur(ActionEvent event,String mail, String mdp) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/lprs_javafx?serverTimezone=UTC","root", "");
            preparedStatement = connection.prepareStatement("SELECT role, nom, prenom, mdp FROM utilisateur WHERE mail = ?");
            preparedStatement.setString(1,mail);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()){
                System.out.println("Le compte n'existe pas dans la base de donnée!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ce compte n'existe pas !");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String role1 = resultSet.getString("role");
                    String nom1 = resultSet.getString("nom");
                    String prenom1 = resultSet.getString("prenom");
                    String mdp1 = resultSet.getString("mdp");
                    if (mdp1.equals(mdp)) {
                        changeScene(event, "logged-in.fxml", "Bienvenue!", mail, role1, nom1, prenom1, mdp1 );
                    } else {
                        System.out.println("Mots de passe incorecte !");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Le mots de passe est incorrecte !");
                        alert.show();
                    }
                }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
