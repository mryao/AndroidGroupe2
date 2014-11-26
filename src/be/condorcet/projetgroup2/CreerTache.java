package be.condorcet.projetgroup2;

import java.sql.Connection;

import classdb.TacheDB;
import Connexion.DBConnection;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class CreerTache extends ActionBarActivity {

	private Connection con=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creer_tache);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.creer_tache, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		 try {
	          con.close();
	          con=null;
	          Log.d("connexion","deconnexion OK");
	          }
	          catch (Exception e) { 
	          }
		 Log.d("connexion","deconnexion OK");
		
	}
	
	 class MyAccesDB extends AsyncTask<String,Integer,Boolean> {
		    private String resultat;
		    private ProgressDialog pgd=null;
		    
								
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
				         pgd=new ProgressDialog(CreerTache.this);
						 pgd.setMessage("chargement en cours");
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
					   }
					    int id=Integer.parseInt(ed1.getText().toString());	
				        try{
				        	
				        	TacheDB tc=new TacheDB(id);	
				           	       
				          		           
				        }
				        catch(Exception e){		             
				         resultat="erreur" +e.getMessage(); 
				         return false;
				         
				         }
				               
					
						return true;
					}
					//se fait après l'opération, impossible de modifier des valeurs
					protected void onPostExecute(Boolean result){
						 super.onPostExecute(result);
						  pgd.dismiss();
						  ed2.setText(resultat);
									
					}
			
				}
}
