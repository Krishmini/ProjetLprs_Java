package Model;

public class DossierInscription {
    private int id_dossierInscription;
    private String nomm;
    private String prenomm;
    private String date;
    private String heure;
    private String filiere_interet;
    private String motivation_etudiant;

    public DossierInscription(int id_dossierInscription, String nomm, String prenomm, String date, String heure, String filiere_interet, String motivation_etudiant) {
        this.id_dossierInscription = id_dossierInscription;
        this.nomm = nomm;
        this.prenomm = prenomm;
        this.date = date;
        this.heure = heure;
        this.filiere_interet = filiere_interet;
        this.motivation_etudiant = motivation_etudiant;
    }

    public DossierInscription(int idDossierInscription, String date, String heure, String filiereInteret, String motivationEtudiant) {
    }

    public int getId_dossierInscription() {
        return id_dossierInscription;
    }
    public String getNomm() {
        return nomm;
    }

    public String getPrenomm() {
        return prenomm;
    }

    public String getDate() {
        return date;
    }

    public String getHeure() {
        return heure;
    }

    public String getFiliere_interet() {
        return filiere_interet;
    }

    public String getMotivation_etudiant() {
        return motivation_etudiant;
    }


}

