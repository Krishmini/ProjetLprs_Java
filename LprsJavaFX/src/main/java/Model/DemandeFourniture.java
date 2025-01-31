package Model;

public class DemandeFourniture {
    private int id_demandeFourniture;
    private String article;
    private int quantite;
    private String raison;
    private Boolean approuver;
    private int ref_stock;
    private int ref_utilisateur;

    public DemandeFourniture(int id_demandeFourniture, String article, int quantite, String raison, boolean approuver, int ref_stock, int ref_utilisateur) {
        this.id_demandeFourniture = id_demandeFourniture;
        this.article = article;
        this.quantite = quantite;
        this.raison = raison;
        this.approuver = approuver;
        this.ref_stock = ref_stock;
        this.ref_utilisateur = ref_utilisateur;
    }

    public int getId_demandeFourniture() {
        return id_demandeFourniture;
    }

    public void setId_demandeFourniture(int id_demandeFourniture) {
        this.id_demandeFourniture = id_demandeFourniture;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public Boolean getApprouver() {
        return approuver;
    }

    public void setApprouver(Boolean approuver) {
        this.approuver = approuver;
    }

    public int getRef_stock() {
        return ref_stock;
    }

    public void setRef_stock(int ref_stock) {
        this.ref_stock = ref_stock;
    }

    public int getRef_utilisateur() {
        return ref_utilisateur;
    }

    public void setRef_utilisateur(int ref_utilisateur) {
        this.ref_utilisateur = ref_utilisateur;
    }
}

