
package classdb;

public class User {
    protected int iduser;

    protected String nom;

    protected String prenom;

    protected String login;

    protected String motdepasse;

    protected int admin;

    public User() {

    }

    public User(String nom) {
        this.nom = nom;
    }

    public User(int iduser, String nom, String prenom, String login, String motdepasse, int admin) {
        this.iduser = iduser;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.motdepasse = motdepasse;
        this.admin = admin;

    }
    
    public User( String nom, String prenom, String login, String motdepasse, int admin) {
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.motdepasse = motdepasse;
        this.admin = admin;

    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" + "iduser=" + iduser + ", nom=" + nom + ", prenom=" + prenom + ", login=" + login + ", motdepasse=" + motdepasse + ", admin=" + admin + '}';
    }
        
}
