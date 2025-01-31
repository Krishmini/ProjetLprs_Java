package Model;

public class DossierInscription {
    private int id_dossierInscription;
    private String date;
    private String heure;
    private String filliere_interet;
    private String motivation_etudiant;
    private int ref_utilisateur;
    private int ref_ficheEtudiant;

    public DossierInscription(int id_dossierInscription, String date, String heure, String filliere_interet, String motivation_etudiant, int ref_utilisateur, int ref_ficheEtudiant ) {
        this.id_dossierInscription = id_dossierInscription;
        this.date = date;
        this.heure = heure;
        this.filliere_interet = filliere_interet;
        this.motivation_etudiant = motivation_etudiant;
        this.ref_utilisateur = ref_utilisateur;
        this.ref_ficheEtudiant = ref_ficheEtudiant;
    }

    public int getId_dossierInscription() {
        return id_dossierInscription;
    }

    public void setId_dossierInscription(int id_dossierInscription) {
        this.id_dossierInscription = id_dossierInscription;
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

    public String getFilliere_interet() {
        return filliere_interet;
    }

    public void setFilliere_interet(String filliere_interet) {
        this.filliere_interet = filliere_interet;
    }

    public String getMotivation_etudiant() {
        return motivation_etudiant;
    }

    public void setMotivation_etudiant(String motivation_etudiant) {
        this.motivation_etudiant = motivation_etudiant;
    }

    public int getRef_utilisateur() {
        return ref_utilisateur;
    }

    public void setRef_utilisateur(int ref_utilisateur) {
        this.ref_utilisateur = ref_utilisateur;
    }

    public int getRef_ficheEtudiant() {
        return ref_ficheEtudiant;
    }

    public void setRef_ficheEtudiant(int ref_ficheEtudiant) {
        this.ref_ficheEtudiant = ref_ficheEtudiant;
    }

}
