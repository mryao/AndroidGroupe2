
package Test;

import classdb.UserDB;
import java.sql.Connection;
import java.util.ArrayList;
import Connexion.*;
import java.sql.Date;

public class TestUser {
    public static void main(String[] args) {
        
        DBConnection dbc =new DBConnection();
        Connection con = dbc.getConnection();

        if(con==null) { 
            System.out.println("[CONNEXION-ORACLE]Connexion a la base de donnée impossible !");
            System.exit(0);
        }else{
            System.out.println("[CONNEXION-ORACLE]Connexion réussie");
        }
        
        UserDB.setConnection(con);
        UserDB c1=null,c2=null,c3=null; 
        /*
        ==========================================================================================
            Insertion   
        ==========================================================================================
        */
        try{
            System.out.println("[INSERTION]Debut de l'ajout de la c1");
            c1=new UserDB("Michel","Charles","cmichel","maggle",0);
            c1.create();
            c2=new UserDB("DiRupo","Elio","Dirupette","alleze",0);
            c2.create();
            
            System.out.println("[INSERTION]user 1 = "+c1.toString());
            System.out.println("[INSERTION]Fin de l'ajout de user 1");
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
            c3=new UserDB("Michel","Charles","cmichel","maggle",0);
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
            System.out.println("[SUPRESSION]Debut de la supression de user 2");
            System.out.println("[SUPRESSION]user 2 = "+c2.toString());
            c2.delete();
            System.out.println("[SUPRESSION]Fin de la supression de user 2");
        }catch(Exception e){
            System.out.println("[SUPRESSION]Erreur :: "+e);
        }
        /*
        ==========================================================================================
            Mise a jour 
        ==========================================================================================
        */
        try{ 
            System.out.println("[UPDATE]Debut de l'update de user 1");
            System.out.println("[UPDATE]Avant -> user 1 = "+c1.toString());
            c1.setNom("Marteau");
            c1.setPrenom("Requin");
            System.out.println("[UPDATE]Apres -> user 1 = "+c1.toString());
            c1.update();
            System.out.println("[UPDATE]Fin de l'update de user 1");
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
