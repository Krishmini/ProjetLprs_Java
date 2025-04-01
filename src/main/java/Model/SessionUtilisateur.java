package Model;

public class SessionUtilisateur {
    private static SessionUtilisateur instance;
    private Utilisateur utilisateur;

    private SessionUtilisateur() {}

    public static SessionUtilisateur getInstance() {
        if (instance == null) {
            instance = new SessionUtilisateur();
        }
        return instance;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void clearSession() {
        utilisateur = null;
    }
}
