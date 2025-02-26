package Model;

public class StockQuantite {
    private Stock stock;
    private Quantite quantite;

    public StockQuantite(Stock stock, Quantite quantite) {
        this.stock = stock;
        this.quantite = quantite;
    }


    public Stock getStock() {
        return stock;
    }

    public Quantite getQuantite() {
        return quantite;
    }


    public int getId_stock() {
        return stock.getId_stock();
    }

    public String getLibelle() {
        return stock.getLibelle();
    }

    public String getDescription() {
        return stock.getDescription();
    }

    public int getQuantite1() {
        return quantite.getQuantite();
    }

    public int getRefFournisseur() {
        return stock.getRef_fournisseur();
    }

}
