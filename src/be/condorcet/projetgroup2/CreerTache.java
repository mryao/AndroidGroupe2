package be.condorcet.projetgroup2;

import java.sql.Connection;

import classdb.TacheDB;
import classdb.UserDB;
import Connexion.DBConnection;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class CreerTache extends ActionBarActivity {

	private EditText titre;
	private EditText description;
	private EditText date_tache;
	private EditText num;
	private EditText depanneur;
	private EditText error;
	private Button suivant;
	private String recId;
	private int id;
	
	private Connection con=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creer_tache);
		
		titre=(EditText) findViewById(R.id.titre1);
		description=(EditText) findViewById(R.id.description2);
		date_tache=(EditText) findViewById(R.id.date3);
		num=(EditText) findViewById(R.id.ordre4);
		depanneur=(EditText) findViewById(R.id.depanneur5);
		error=(EditText) findViewById(R.id.erreur);
		suivant=(Button)findViewById(R.id.ajouter);
		
		Intent i=getIntent();
		recId = i.getParcelableExtra(MainActivity.sendid2);
		id = Integer.parseInt(recId);
	    UserDB us = new UserDB(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.creer_tache, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
					//se fait avant l'op�ration
					protected void onPreExecute(){
						 super.onPreExecute();
				         pgd=new ProgressDialog(CreerTache.this);
						 pgd.setMessage("chargement en cours");
						 pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			     		 pgd.show();
													
					}
					//se fait pendant l'op�ration
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
					    String vtitre=titre.getText().toString();
					    String vdesc=description.getText().toString();
					    String vdate=date_tache.getText().toString();
					    int vnum = Integer.parseInt(num.getText().toString());
					    int vdepanneur = Integer.parseInt(depanneur.getText().toString());
					    					   					    
				        try{
				        	
				        	TacheDB tc=new TacheDB(vtitre,vdesc,vdate,vnum,vdepanneur,id);         	       
				        }
				        catch(Exception e){		             
				         resultat="erreur" +e.getMessage(); 
				         return false;
				         
				        }
				        resultat="Cr�ation effectu�e";
						return true;
					}
					//se fait apr�s l'op�ration, impossible de modifier des valeurs
					protected void onPostExecute(Boolean result){
						 super.onPostExecute(result);
						  pgd.dismiss();
						  error.setText(resultat);
									
					}
			
				}
}
