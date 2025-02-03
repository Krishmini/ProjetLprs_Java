package Model;

public class Fournisseur {
    private int id_fournisseur;
    private String nom_f;
    private String prix;
    public Fournisseur(int id_fournisseur, String nom_f, String prix) {
        this.id_fournisseur = id_fournisseur;
        this.nom_f = nom_f;
        this.prix = prix;
    }

    public int getId_fournisseur() {
        return id_fournisseur;
    }

    public void setId_fournisseur(int id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }

    public String getNom_f() {
        return nom_f;
    }

    public void setNom_f(String nom_f) {
        this.nom_f = nom_f;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}

