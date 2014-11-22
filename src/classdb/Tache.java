
package classdb;

import java.util.Date;

public class Tache {
    
    protected int idtache;

    protected String titre;

    protected String description;

    protected String etat;

    protected String date_tache;

    protected int num_ordre;

    protected int depanneur;

    protected int createur;

    public Tache() {

    }

    public Tache(String nom) {
        this.titre = titre;
    }

    public Tache(int idtache, String titre, String description, String etat, String date_tache, int num_ordre, int depanneur, int createur) {
        this.idtache = idtache;
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.date_tache = date_tache;
        this.num_ordre = num_ordre;
        this.depanneur = depanneur;
        this.createur = createur;

    }
    
    public Tache(String titre, String description, String etat, String date_tache, int num_ordre, int depanneur, int createur) {
        this.titre = titre;
        this.description = description;
        this.etat = etat;
        this.date_tache = date_tache;
        this.num_ordre = num_ordre;
        this.depanneur = depanneur;
        this.createur = createur;

    }

    public int getIdtache() {
        return idtache;
    }

    public void setIdtache(int idtache) {
        this.idtache = idtache;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDate_tache() {
        return date_tache;
    }

    public void setDate_tache(String date_tache) {
        this.date_tache = date_tache;
    }

    public int getNum_ordre() {
        return num_ordre;
    }

    public void setNum_ordre(int num_ordre) {
        this.num_ordre = num_ordre;
    }

    public int getDepanneur() {
        return depanneur;
    }

    public void setDepanneur(int depanneur) {
        this.depanneur = depanneur;
    }

    public int getCreateur() {
        return createur;
    }

    public void setCreateur(int createur) {
        this.createur = createur;
    }

    @Override
    public String toString() {
        return "Tache{" + "idtache=" + idtache + ", titre=" + titre + ", description=" + description + ", etat=" + etat + ", date_tache=" + date_tache + ", num_ordre=" + num_ordre + ", depanneur=" + depanneur + ", createur=" + createur + '}';
    }
    
}
