
package classdb;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TacheDB extends Tache implements CRUD, Parcelable{
    protected static Connection dbConnect = null;

    public TacheDB() {
    }

    public TacheDB(String titre, String description,
            String date_tache, int num_ordre, int depanneur, int createur) {
        super(titre, description, "Planifiée", date_tache, num_ordre, depanneur, createur);
    }//constructeur pour la création 

    public TacheDB(int idtache, String titre, String description, String etat,
            String date_tache, int num_ordre, int depanneur, int createur) {
        super(0, titre, description, etat, date_tache, num_ordre, depanneur, createur);
    }

    public TacheDB(String titre) {
        this.titre = titre;
    }

    public TacheDB(int idtache) {
        super(idtache, "", "", "", null, 0,0,0);
    }

    public static void setConnection(Connection nouvdbConnect) {
        dbConnect = nouvdbConnect;
    }

    public void create() throws Exception {
        CallableStatement cstmt = null;
        
        DateFormat df_date = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = df_date.parse(date_tache);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        try {
            String req = "call create_tache(?,?,?,?,?,?,?)";
            cstmt = dbConnect.prepareCall(req);
            
            cstmt.setString(1, titre);
            cstmt.setString(2, description);
            cstmt.setString(3, etat);
            cstmt.setDate(4, sqlDate);
            cstmt.setInt(5, num_ordre);
            cstmt.setInt(6, depanneur);
            cstmt.setInt(7, createur);
            cstmt.executeUpdate();
            
            String query2="select id_tache from tâche where titre= ?" ;
            
            PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
            pstm2.setString(1,titre);
            ResultSet rs= pstm2.executeQuery();
            if(rs.next()){
                int nc= rs.getInt(1);
                idtache = nc;
            }
            else System.out.println("erreur");
            
        } catch (SQLException e) {

            throw new Exception("Erreur de création " + e.getMessage());
        } finally {//effectué dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    public void read(int ntache)throws Exception{
       
        CallableStatement cstmt=null;
        try{
             boolean trouve=false;
             String query1="SELECT * FROM tâche WHERE id_tache = ?";
             PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             pstm1.setInt(1,ntache);
             ResultSet rs= pstm1.executeQuery();
             if(rs.next()){
                 trouve = true;
                 idtache = rs.getInt("ID_TACHE");
                 titre = rs.getString("TITRE");
                 description = rs.getString("DESCRIPTION");
                 etat = rs.getString("ETAT");
                 date_tache = rs.getString("DATE_TACHE");
                 num_ordre = rs.getInt("NUM_ORDRE");
                 depanneur = rs.getInt("DEPANNEUR");
                 createur = rs.getInt("CREATEUR");
             }
             if(!trouve)throw new Exception("ID_TACHE inconnu dans la table !");
        }
	catch(Exception e){
             
                throw new Exception("Erreur de lecture "+e.getMessage());
             }
        finally{//effectué dans tous les cas 
            try{
              cstmt.close();
            }
            catch (Exception e){}
        }
     }

    public void update() throws Exception {
        CallableStatement cstmt = null;        
       
        DateFormat df_date = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = df_date.parse(date_tache);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        try {
            String req = "call update_tache(?,?,?,?,?,?,?,?)";
            cstmt = dbConnect.prepareCall(req);
            PreparedStatement pstm = dbConnect.prepareStatement(req);
            cstmt.setInt(1, idtache);
            cstmt.setString(2, titre);
            cstmt.setString(3, description);
            cstmt.setString(4, etat);
            cstmt.setDate(5, sqlDate);
            cstmt.setInt(6, num_ordre);
            cstmt.setInt(7, depanneur);
            cstmt.setInt(8, createur);
            cstmt.executeUpdate();

        } catch (Exception e) {

            throw new Exception("Erreur de mise à jour " + e.getMessage());
        } finally {//effectué dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    public void delete() throws Exception {

        CallableStatement cstmt = null;
        try {
            String req = "call deltache(?)";
            cstmt = dbConnect.prepareCall(req);
            cstmt.setInt(1, idtache);
            cstmt.executeUpdate();

        } catch (Exception e) {

            throw new Exception("Erreur d'effacement " + e.getMessage());
        } finally {//effectué dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }
    
    public static ArrayList<TacheDB>tachesDepanneur(int iddep) throws Exception {//arraylist qui contient les tâches d'un dépanneur
        ArrayList<TacheDB> tachesDepanneur = new ArrayList<TacheDB>();
        //CallableStatement cstmt=null;
        try {
            boolean trouve=false;
            String query1="SELECT * FROM (SELECT * FROM tâche WHERE depanneur = ? order by NUM_ORDRE) order by DATE_TACHE;";
            Log.d("tacheDB", ""+iddep);
            //cstmt = dbConnect.prepareCall(query1);Log.d("tacheDB", "test1");
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);Log.d("tacheDB", "test1");	
            pstm1.setInt(1,iddep);Log.d("tacheDB", "test2");
            ResultSet rs= pstm1.executeQuery();Log.d("tacheDB", "test3");
            while(rs.next()){
                trouve=true;Log.d("tacheDB", "test4");
                int idtachetmp = rs.getInt("ID_TACHE");
                String titretmp = rs.getString("TITRE");
                String descriptiontmp = rs.getString("DESCRIPTION");
                String etattmp = rs.getString("ETAT");
                String datetmp = rs.getString("DATE_TACHE");
                int ordretmp = rs.getInt("NUM_ORDRE");
                int depanneurtmp = rs.getInt("DEPANNEUR");
                int createurtmp = rs.getInt("CREATEUR");
                Log.d("tacheDB", "test5");
                //tachesDepanneur.add(new TacheDB(idtachetmp,titretmp,descriptiontmp,etattmp,datetmp,ordretmp,depanneurtmp,createurtmp));
                tachesDepanneur.add(new TacheDB(titretmp));
                Log.d("tacheDB", tachesDepanneur.toString());
            }
            if(!trouve)throw new Exception("rien trouvé");
            else return tachesDepanneur;
        }
        catch(Exception e) {
            throw new Exception(e);
        }
    }
    
    @Override
    public int describeContents() {
      //On renvoie 0, car notre classe ne contient pas de FileDescriptor
      return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
      // On ajoute les objets dans l'ordre dans lequel on les a déclarés
      dest.writeInt(idtache);
      dest.writeString(titre);
      dest.writeString(description);
      dest.writeString(etat);
      dest.writeString(date_tache);
      dest.writeInt(num_ordre);
      dest.writeInt(depanneur);
      dest.writeInt(createur);
    }

    public static final Parcelable.Creator<TacheDB> CREATOR = new Parcelable.Creator<TacheDB>() {
      @Override
      public TacheDB createFromParcel(Parcel source) {
        return new TacheDB(source);
      }
      @Override
      public TacheDB[] newArray(int size) {
        return new TacheDB[size];
      }
    };
    public TacheDB(Parcel in) {	
    	  idtache = in.readInt();	
    	  titre = in.readString();
    	  description = in.readString();
    	  etat = in.readString();
    	  date_tache = in.readString();
    	  num_ordre=in.readInt();
    	  depanneur=in.readInt();
    	  createur = in.readInt();
    	}
    
        
}
    
    
