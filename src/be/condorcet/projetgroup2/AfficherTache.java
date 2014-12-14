package be.condorcet.projetgroup2;

import java.sql.Connection;
import java.util.ArrayList;

import be.condorcet.projetgroup2.MainActivity.MyAccesDB;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AfficherTache extends Activity {
	
	private Connection con=null;
	private int id;
	private ListView liste=null;
	private ArrayList<UserDB>listUser = new ArrayList();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afficher_tache);
		
		liste = (ListView) findViewById(R.id.listView1);

		try{
			Intent i=getIntent();			
			id = Integer.parseInt(i.getStringExtra("sendid2"));
			Log.d("robin",""+id);
			listUser = i.getParcelableArrayListExtra(MainActivity.LISTTACHE);
			Log.d("afficher get i","listeTache ok");
		}catch(Exception ex){
			Log.d("Test Intend",""+ex.getMessage());
		}
		
		
		
		MyAccesDB adb = new MyAccesDB(AfficherTache.this);
		adb.execute();
		
		ArrayAdapter<UserDB> adapter = new  ArrayAdapter<UserDB>(this,android.R.layout.simple_list_item_1, listUser);
		liste.setAdapter(adapter);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		    private ArrayList<TacheDB> liste = new ArrayList();
		    
								
					public MyAccesDB(AfficherTache pActivity) {
					
						link(pActivity);
						// TODO Auto-generated constructor stub
					}

					private void link(AfficherTache pActivity) {
						// TODO Auto-generated method stub
					
						
					}
					//se fait avant l'opération
					protected void onPreExecute(){
						 super.onPreExecute();
				         pgd=new ProgressDialog(AfficherTache.this);
						 pgd.setMessage(getString(R.string.Loading));
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
