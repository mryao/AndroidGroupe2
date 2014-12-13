package be.condorcet.projetgroup2;

import java.sql.Connection;
import java.util.ArrayList;

import classdb.TacheDB;
import classdb.UserDB;
import Connexion.DBConnection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AfficherTache extends Activity {
	
	private Connection con=null;
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afficher_tache);
		

		try{
			Intent i=getIntent();			
			id = Integer.parseInt(i.getStringExtra("sendid2"));
			Log.d("robin",""+id);
		}catch(Exception ex){
			Log.d("Test Intend",""+ex.getMessage());
		}
		
	}
	
	 class MyAccesDB extends AsyncTask<String,Integer,Boolean> {
		    private String resultat;
		    private ProgressDialog pgd=null;
		    private ArrayList<TacheDB> liste = new ArrayList();
		    
								
					public MyAccesDB(CreerTache pActivity) {
					
						link(pActivity);
						// TODO Auto-generated constructor stub
					}

					private void link(CreerTache pActivity) {
						// TODO Auto-generated method stub
					
						
					}
					//se fait avant l'opération
					protected void onPreExecute(){
						 super.onPreExecute();
				         pgd=new ProgressDialog(AfficherTache.this);
						 pgd.setMessage("Affichage des tâches en cours");
						 pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			     		 pgd.show();
													
					}
					//se fait pendant l'opération
					@Override
					protected Boolean doInBackground(String... arg0) {
											
					   if(con==null){//premier invocation
						   con = new DBConnection().getConnection(); 
					    	if(con==null) {
						    resultat="echec de la connexion";
					      	return false;
						    }
					  
						   TacheDB.setConnection(con);
						   UserDB.setConnection(con);
					   }
					    					    					   					    
				        try{
				        	//UserDB us = new UserDB(id);
				    	    TacheDB ta = new TacheDB();
				    	    liste = ta.tachesDepanneur(id);
						    
				        	
				        }
				        catch(Exception e){		             
				         resultat="Erreur :" +e.getMessage(); 
				         return false;
				         
				        }
				        
				        resultat="Création effectuée";
				        
						return true;
					}
					//se fait après l'opération, impossible de modifier des valeurs
					protected void onPostExecute(Boolean result){
						 super.onPostExecute(result);
						  pgd.dismiss();
						  //error.setText(resultat);
						  		  
									
					}
			
				}
}
