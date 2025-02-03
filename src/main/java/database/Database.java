package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String serveur = "localhost";
    private String nomDeLaBase = "lprs_javafx";
    private String utilisateur = "root";
    private String MotDePasse = "";

    public String getServeur() {

        return serveur;
    }

    public void setServeur(String serveur) {

        this.serveur = serveur;
    }

    public String getNomDeLaBase() {
        return nomDeLaBase;
    }

    public void setNomDeLaBase(String nomDeLaBase) {

        this.nomDeLaBase = nomDeLaBase;
    }

    public String getUtilisateur() {

        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {

        this.utilisateur = utilisateur;
    }

    public String getMotDePasse() {

        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {

        MotDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Database{" +
                "serveur='" + serveur + '\'' +
                ", nomDeLaBase='" + nomDeLaBase + '\'' +
                ", utilisateur='" + utilisateur + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                '}';
    }

    private String getUrl() {

        return "jdbc:mysql://" + serveur + "/" + nomDeLaBase + "?serverTimezone=UTC";
    }
    public Connection getConnexion() {
        try {
            Connection cnx = DriverManager.getConnection(this.getUrl(), this.utilisateur, this.MotDePasse);
            System.out.println("Etat de la connexion : ");
            System.out.println(cnx.isClosed() ? "fermée" : "ouverte \r\n");
            return cnx;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la tentative de connexion à la base de données");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
