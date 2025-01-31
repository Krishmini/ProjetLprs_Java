package Model;

public class FicheEtudiant {
    private int id_ficheEtudiant;
    private String nom_e;
    private String prenom_e;
    private String dernier_diplome;
    private String email_e;
    private String telephone_e;
    private String ville_e;
    private String cp_e;
    private String rue_e;
    private int ref_utilisateur;

    public FicheEtudiant(int id_ficheEtudiant, String nom_e, String prenom_e, String dernier_diplome, String email_e, String telephone_e, String ville_e, String cp_e, String rue_e, int ref_utilisateur) {
        this.id_ficheEtudiant = id_ficheEtudiant;
        this.nom_e = nom_e;
        this.prenom_e = prenom_e;
        this.dernier_diplome = dernier_diplome;
        this.email_e = email_e;
        this.telephone_e = telephone_e;
        this.ville_e = ville_e;
        this.cp_e = cp_e;
        this.rue_e = rue_e;
        this.ref_utilisateur = ref_utilisateur;
    }

    public int getId_ficheEtudiant() {
        return id_ficheEtudiant;
    }

    public void setId_ficheEtudiant(int id_ficheEtudiant) {
        this.id_ficheEtudiant = id_ficheEtudiant;
    }

    public String getNom_e() {
        return nom_e;
    }

    public void setNom_e(String nom_e) {
        this.nom_e = nom_e;
    }

    public String getPrenom_e() {
        return prenom_e;
    }

    public void setPrenom_e(String prenom_e) {
        this.prenom_e = prenom_e;
    }

    public String getDernier_diplome() {
        return dernier_diplome;
    }

    public void setDernier_diplome(String dernier_diplome) {
        this.dernier_diplome = dernier_diplome;
    }

    public String getEmail_e() {
        return email_e;
    }

    public void setEmail_e(String email_e) {
        this.email_e = email_e;
    }

    public String getTelephone_e() {
        return telephone_e;
    }

    public void setTelephone_e(String telephone_e) {
        this.telephone_e = telephone_e;
    }

    public String getVille_e() {
        return ville_e;
    }

    public void setVille_e(String ville_e) {
        this.ville_e = ville_e;
    }

    public String getCp_e() {
        return cp_e;
    }

    public void setCp_e(String cp_e) {
        this.cp_e = cp_e;
    }

    public String getRue_e() {
        return rue_e;
    }

    public void setRue_e(String rue_e) {
        this.rue_e = rue_e;
    }

    public int getRef_utilisateur() {
        return ref_utilisateur;
    }

    public void setRef_utilisateur(int ref_utilisateur) {
        this.ref_utilisateur = ref_utilisateur;
    }
}

