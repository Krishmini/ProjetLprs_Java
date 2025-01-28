package Model;


public class Commande {
    private int id_commande;
    private String date;
    private String heure;
    private int ref_utilisateur;
    private int ref_fournisseur;

    public Commande(int id_commande, String date, String heure, int ref_utilisateur, int ref_fournisseur) {
        this.id_commande = id_commande;
        this.date = date;
        this.heure = heure;
        this.ref_utilisateur = ref_utilisateur;
        this.ref_fournisseur = ref_fournisseur;
    }

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setNom(String nom) {
        this.heure = heure;
    }

    public int getRef_utilisateur() {
        return ref_utilisateur;
    }

    public void setRef_utilisateur(int ref_utilisateur) {
        this.ref_utilisateur = ref_utilisateur;
    }

    public int getRef_fournisseur() {
        return ref_fournisseur;
    }

    public void setRef_fournisseur(int ref_fournisseur) {
        this.ref_fournisseur = ref_fournisseur;
    }
}
