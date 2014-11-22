package Connexion;

import java.sql.*;
import java.util.*;

public class ODBConnection {
   protected String dbName;
   protected String username;
   protected String password;
  
   public ODBConnection() {
     PropertyResourceBundle properties = (PropertyResourceBundle)
     PropertyResourceBundle.getBundle("Sources.properties");
     dbName =properties.getString("cours.DB.database");
     username=properties.getString("cours.DB.login");
     password=properties.getString("cours.DB.password");
    }

  public ODBConnection(String username,String password){
     this();
     this.username=username;
     this.password=password;
  }  
 

 public Connection getConnection() {
    try
    {  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
       dbName="magasinOracle";
       String url = "jdbc:odbc:"+dbName;
       return DriverManager.getConnection(url,username,password);
    }
    catch(Exception e) {
       System.out.println("erreur de connexion "+e);
       e.printStackTrace();
       return null ;
    }    
  }      
}