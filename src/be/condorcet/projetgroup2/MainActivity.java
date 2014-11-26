package be.condorcet.projetgroup2;

import java.sql.Connection;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	private Connection con=null; 
	private Button suivant =null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*ed1=(EditText) findViewById(R.id.texte1);
		ed2=(EditText) findViewById(R.id.texte2);*/
		suivant=(Button)findViewById(R.id.ok);
		
		suivant.setOnClickListener(
				new OnClickListener(){
					
					public void onClick(View v){
						
						Intent i = new Intent(MainActivity.this,CreerTache.class);
						startActivity(i);
					
					}//fin m�thode onClick
				  }//fin classe onClickLister
				);//fin param�trage m�thode setOnClickListener
		
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
	    
							
				public MyAccesDB(MainActivity pActivity) {
				
					link(pActivity);
					// TODO Auto-generated constructor stub
				}

				private void link(MainActivity pActivity) {
					// TODO Auto-generated method stub
				
					
				}
				//se fait avant l'op�ration
				protected void onPreExecute(){
					 super.onPreExecute();
			         pgd=new ProgressDialog(MainActivity.this);
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
				  
					   UserDB.setConnection(con);
				   }
				    int id=Integer.parseInt(ed1.getText().toString());	
			        try{
			        	
			        	UserDB us=new UserDB(id);	
			            us.read(id);
			            int resultatId=us.getIduser();
			       
			        }
			        catch(Exception e){		             
			         resultat="erreur" +e.getMessage(); 
			         return false;
			         
			         }
			               
				
					return true;
				}
				//se fait apr�s l'op�ration, impossible de modifier des valeurs
				protected void onPostExecute(Boolean result){
					 super.onPostExecute(result);
					  pgd.dismiss();
					  ed2.setText(resultat);
								
				}
		
			}
}
