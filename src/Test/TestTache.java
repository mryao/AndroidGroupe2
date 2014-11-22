
package Test;

import classdb.TacheDB;
import java.sql.Connection;
import java.util.*;
import Connexion.*;
import java.sql.*;

public class TestTache {
    public static void main(String[] args) {
        
        DBConnection dbc =new DBConnection();
        Connection con = dbc.getConnection();

        if(con==null) { 
            System.out.println("[CONNEXION-ORACLE]Connexion a la base de donnée impossible !");
            System.exit(0);
        }else{
            System.out.println("[CONNEXION-ORACLE]Connexion réussie");
        }
        
        TacheDB.setConnection(con);
        TacheDB c1=null,c2=null,c3=null; 
        /*
        ==========================================================================================
            Insertion   
        ==========================================================================================
        */
        try{
            System.out.println("[INSERTION]Debut de l'ajout de la c1");
            c1=new TacheDB("tache1","tache test1","en cours","19/11/2014",4,2,3);
            c1.create();
            c2=new TacheDB("tache2","tache test2","en cours","20/11/2014",6,2,3);
            c2.create();
            
            System.out.println("[INSERTION]tache 1 = "+c1.toString());
            System.out.println("[INSERTION]Fin de l'ajout de la tache 1");
        }catch(Exception e){
            System.out.println("[INSERTION]Erreur :: "+e);
        }
        
        /*
        ==========================================================================================
            Doublon 
        ==========================================================================================
        */
        try{
            System.out.println("[DOUBLON]Debut de l'ajout du doublon");
            c3=new TacheDB("tache1","tache test1","en cours","19/11/2014",4,2,3);
            c3.create();
            
            
            System.out.println("[DOUBLON]doublon = "+c3.toString());
            System.out.println("[DOUBLON]Fin de l'ajout");
        }catch(Exception e){
            System.out.println("[DOUBLON]Erreur :: "+e);
        }
        /*
        ==========================================================================================
            Supression 
        ==========================================================================================
        */
        try{ 
            System.out.println("[SUPRESSION]Debut de la supression de la tache 2");
            System.out.println("[SUPRESSION]tache 2 = "+c2.toString());
            c2.delete();
            System.out.println("[SUPRESSION]Fin de la supression de la tache 2");
        }catch(Exception e){
            System.out.println("[SUPRESSION]Erreur :: "+e);
        }
        /*
        ==========================================================================================
            Mise a jour 
        ==========================================================================================
        */
        try{ 
            System.out.println("[UPDATE]Debut de l'update de la tache 1");
            System.out.println("[UPDATE]Avant -> tache 1 = "+c1.toString());
            c1.setTitre("tache updatée");
            c1.setDescription("Hello");
            System.out.println("[UPDATE]Apres -> tache 1 = "+c1.toString());
            c1.update();
            System.out.println("[UPDATE]Fin de l'update de la tache 1");
        }catch(Exception e){
            System.out.println("[UPDATE]Erreur :: "+e);
        }
        
        try{
            c1.delete();
        
        }catch(Exception e){
            System.out.println("[RECHERCHE]Erreur :: "+e);
        }
        /*
        ==========================================================================================
            Recherche 
        ==========================================================================================
        */
        try{ 
            System.out.println("[RECHERCHE]Debut de la recherche");
            c1.read(3);
            System.out.println(c1.toString());
            System.out.println("[RECHERCHE]Fin de la recherche");
        }catch(Exception e){
            System.out.println("[RECHERCHE]Erreur :: "+e);
        }
        
        
        /*
        ==========================================================================================
            Fermeture de la connexion vers Oracle
        ==========================================================================================
        */
        try{  
           con.close();
        }
        catch(Exception e){}
                
    } 
}
