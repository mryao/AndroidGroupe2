
package classdb;

import java.sql.*;
import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDB extends User implements CRUD, Parcelable{
    protected static Connection dbConnect = null;

    public UserDB() {
    }
    
    public UserDB( String login,String motdepasse) {
        super(0,"","",login, motdepasse,0);
    }

    public UserDB(String nom, String prenom, String login, String motdepasse, int admin) {
        super(nom, prenom, login, motdepasse, admin);
    }

    public UserDB(int iduser, String nom, String prenom, String login, String motdepasse, int admin) {
        super(iduser, nom, prenom, login, motdepasse, admin);
    }

    public UserDB(String nom) {
        this.nom = nom;
    }

    public UserDB(int iduser) {
        super(iduser, "", "", "", "", 0);
    }

    public static void setConnection(Connection nouvdbConnect) {
        dbConnect = nouvdbConnect;
    }

    public void create() throws Exception {
        CallableStatement cstmt = null;
        try {
            String req = "call create_user(?,?,?,?,?)";
            cstmt = dbConnect.prepareCall(req);
            
            cstmt.setString(1, nom);
            cstmt.setString(2, prenom);
            cstmt.setString(3, login);
            cstmt.setString(4, motdepasse);
            cstmt.setInt(5, admin);
            cstmt.executeUpdate();
            
            
            String query2="select id_user from users where login= ?" ;
            
            PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
            pstm2.setString(1,login);
            ResultSet rs= pstm2.executeQuery();
            if(rs.next()){
                int nc= rs.getInt(1);
                iduser = nc;
            }
            else System.out.println("erreur");
            
        } catch (SQLException e) {

            throw new Exception("Erreur de cr�ation " + e.getMessage());
        } finally {//effectu� dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    public void read(int nuser)throws Exception{
       
        CallableStatement cstmt=null;
        try{
            boolean trouve=false;
             String query1="SELECT * FROM users WHERE id_user = ?";
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             pstm1.setInt(1,nuser);
             ResultSet rs= pstm1.executeQuery();
             if(rs.next()){
                 trouve = true;
                 iduser = rs.getInt("ID_USER");
                 nom = rs.getString("NOM");
                 prenom = rs.getString("PRENOM");
                 login = rs.getString("LOGIN");
                 motdepasse = rs.getString("MOT_DE_PASSE");
                 admin = rs.getInt("ADMIN");
                 
             }
             if(!trouve)throw new Exception("ID_USER inconnu dans la table !");
        }
	catch(Exception e){
             
                throw new Exception("Erreur de lecture "+e.getMessage());
             }
        finally{//effectu� dans tous les cas 
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
     }

    public void update() throws Exception {
        CallableStatement cstmt = null;

        try {
            String req = "call update_user(?,?,?,?,?,?)";
            cstmt = dbConnect.prepareCall(req);
            PreparedStatement pstm = dbConnect.prepareStatement(req);
            cstmt.setInt(1, iduser);
            cstmt.setString(2, nom);
            cstmt.setString(3, prenom);
            cstmt.setString(4, login);
            cstmt.setString(5, motdepasse);
            cstmt.setInt(6, admin);
            cstmt.executeUpdate();

        } catch (Exception e) {

            throw new Exception("Erreur de mise � jour " + e.getMessage());
        } finally {//effectu� dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    public void delete() throws Exception {

        CallableStatement cstmt = null;
        try {
            String req = "call deluser(?)";
            cstmt = dbConnect.prepareCall(req);
            cstmt.setInt(1, iduser);
            cstmt.executeUpdate();

        } catch (Exception e) {

            throw new Exception("Erreur d'effacement " + e.getMessage());
        } finally {//effectu� dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }
    
    public static ArrayList<UserDB> all() throws Exception {//arraylist qui retourne tous les d�panneurs (pas les administrateurs)
        ArrayList<UserDB> all = new ArrayList<UserDB>();
        CallableStatement cstmt=null;
        try {
            boolean trouve=false;
            String query1="SELECT * FROM users WHERE admin = 0";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            ResultSet rs = pstm1.executeQuery();
            while(rs.next()){
                trouve=true;
                int idusertmp = rs.getInt("ID_USER");
                String nomtmp = rs.getString("NOM");
                String prenomtmp = rs.getString("PRENOM");
                String logintmp = rs.getString("LOGIN");
                String mdptmp = rs.getString("MOT_DE_PASSE");
                int admintmp = rs.getInt("ADMIN");
                
                all.add(new UserDB(idusertmp,nomtmp,prenomtmp,logintmp,mdptmp,admintmp));
            }
            if(!trouve)throw new Exception("rien trouv�");
            else return all;
        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }   
    }
    
    public void logon(String login,String mot_de_passe)throws Exception{
        
        CallableStatement cstmt=null;
        try{
            boolean trouve=false;
             String query1="SELECT * FROM users WHERE login = ? and mot_de_passe= ?";
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             pstm1.setString(1,login);
             pstm1.setString(2,mot_de_passe);
             ResultSet rs= pstm1.executeQuery();
             if(rs.next()){
                 trouve = true;
                 iduser = rs.getInt("ID_USER");
                 nom = rs.getString("NOM");
                 prenom = rs.getString("PRENOM");
                 login = rs.getString("LOGIN");
                 motdepasse = rs.getString("MOT_DE_PASSE");
                 admin = rs.getInt("ADMIN");
                 
             }
             if(!trouve)throw new Exception("Combinaison login/password invalide");
        }
	    catch(Exception e){
             
                throw new Exception("Erreur de lecture "+e.getMessage());
             }
        finally{//effectu� dans tous les cas 
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
     }
    
    @Override
    public int describeContents() {
      //On renvoie 0, car notre classe ne contient pas de FileDescriptor
      return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
      // On ajoute les objets dans l'ordre dans lequel on les a d�clar�s
      dest.writeInt(iduser);
      dest.writeString(nom);
      dest.writeString(prenom);
      dest.writeString(login);
      dest.writeString(motdepasse);
      dest.writeInt(admin);
    }

    public static final Parcelable.Creator<UserDB> CREATOR = new Parcelable.Creator<UserDB>() {
      @Override
      public UserDB createFromParcel(Parcel source) {
        return new UserDB(source);
      }
      @Override
      public UserDB[] newArray(int size) {
        return new UserDB[size];
      }
    };
    public UserDB(Parcel in) {	
    	  iduser = in.readInt();	
    	  nom = in.readString();
    	  prenom = in.readString();
    	  login = in.readString();
    	  motdepasse=in.readString();
    	  admin = in.readInt();
    	}
    

}
