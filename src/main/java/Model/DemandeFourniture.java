package Model;

public class DemandeFourniture {
    private int id_demandeFourniture;
    private String article;
    private int quantite;
    private String raison;
    private Boolean approuver;
    private int ref_stock;
    private int ref_utilisateur;

    public DemandeFourniture(String article, int quantite, String raison) {
        this.article = article;
        this.quantite = quantite;
        this.raison = raison;
    }


    public int getId_demandeFourniture() {
        return id_demandeFourniture;
    }

    public String getArticle() {
        return article;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getRaison() {
        return raison;
    }

    public Boolean getApprouver() {
        return approuver;
    }

    public int getRef_stock() {
        return ref_stock;
    }

    public int getRef_utilisateur() {
        return ref_utilisateur;
    }


    public DemandeFourniture(int id_demandeFourniture, String article, int quantite, String raison, Boolean approuver, int ref_stock, int ref_utilisateur) {
        this.id_demandeFourniture = id_demandeFourniture;
        this.article = article;
        this.quantite = quantite;
        this.raison = raison;
        this.approuver = approuver;
        this.ref_stock = ref_stock;
        this.ref_utilisateur = ref_utilisateur;
    }
}

