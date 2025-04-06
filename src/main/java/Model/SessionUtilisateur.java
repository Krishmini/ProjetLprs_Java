package Model;

public class SessionUtilisateur {
    private static int utilisateurId;

    public static void setUtilisateurId(int id) {
        utilisateurId = id;
    }

    public static int getUtilisateurId() {
        return utilisateurId;
    }
}
