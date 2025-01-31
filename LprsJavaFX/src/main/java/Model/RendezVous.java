package Model;

public class RendezVous {
    private int id_rendezVous;
    private String salle;
    private String date;
    private String heure;
    private int ref_utilisateur;
    private int ref_dossierInscription;

    public RendezVous(int id_rendezVous, String salle, String date, String heure, int ref_utilisateur, int ref_dossierInscription) {
        this.id_rendezVous = id_rendezVous;
        this.salle = salle;
        this.date = date;
        this.heure = heure;
        this.ref_utilisateur = ref_utilisateur;
        this.ref_dossierInscription = ref_dossierInscription;
    }

    public int getId_rendezVous() {
        return id_rendezVous;
    }

    public void setId_rendezVous(int id_rendezVous) {
        this.id_rendezVous = id_rendezVous;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
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

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getRef_utilisateur() {
        return ref_utilisateur;
    }

    public void setRef_utilisateur(int ref_utilisateur) {
        this.ref_utilisateur = ref_utilisateur;
    }

    public int getRef_dossierInscription() {
        return ref_dossierInscription;
    }

    public void setRef_dossierInscription(int ref_dossierInscription) {
        this.ref_dossierInscription = ref_dossierInscription;
    }
}
