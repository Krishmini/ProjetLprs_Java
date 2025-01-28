package Model;

public class Stock {
    private int id_stock;
    private String libelle;
    private int nombre_stock;
    private String description;
    private int ref_fournisseur;
    public Stock(int id_stock, String libelle, int nombre_stock, String description, int ref_fournisseur) {
        this.id_stock = id_stock;
        this.libelle = libelle;
        this.nombre_stock = nombre_stock;
        this.description = description;
        this.ref_fournisseur = ref_fournisseur;
    }

    public int getId_stock() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getNombre_stock() {
        return nombre_stock;
    }

    public void setNombre_stock(int nombre_stock) {
        this.nombre_stock = nombre_stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRef_fournisseur() {
        return ref_fournisseur;
    }

    public void setRef_fournisseur(int ref_fournisseur) {
        this.ref_fournisseur = ref_fournisseur;
    }
}
