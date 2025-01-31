package Model;

public class Quantite {
    private int id_quantite;
    private int quantite;
    private int ref_stock;
    private int ref_commande;

    public Quantite(int id_quantite, int quantite, int ref_stock, int ref_commande) {
        this.id_quantite = id_quantite;
        this.quantite = quantite;
        this.ref_stock = ref_stock;
        this.ref_commande = ref_commande;
    }

    public int getId_quantite() {
        return id_quantite;
    }

    public void setId_quantite(int id_quantite) {
        this.id_quantite = id_quantite;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getRef_stock() {
        return ref_stock;
    }

    public void setRef_stock(int ref_stock) {
        this.ref_stock = ref_stock;
    }

    public int getRef_commande() {
        return ref_commande;
    }

    public void setRef_commande(int ref_commande) {
        this.ref_commande = ref_commande;
    }
}
