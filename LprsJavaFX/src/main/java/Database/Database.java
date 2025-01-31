package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String serveur = "localhost";
    private String nomDeLaBase = "lprs_javafx";
    private String utilisateur = "root";
    private String motDePasse = "";

    private String getUrl() {
        return "jdbc:mysql://" + serveur + "/" + nomDeLaBase + "?serverTimezone=UTC";
    }

    public Connection getConnexion() {
        Connection cnx = null;
        try {
            cnx = DriverManager.getConnection(getUrl(), utilisateur, motDePasse);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnx;
    }
}
