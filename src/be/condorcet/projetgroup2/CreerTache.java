package be.condorcet.projetgroup2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import be.condorcet.projetgroup2.MainActivity.MyAccesDB;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;

public class CreerTache extends ActionBarActivity {

	private EditText titre;
	private EditText description;
	private EditText date_tache;
	private EditText num;
	private EditText depanneur;
	private Spinner spindep;
	private TextView error;
	private Button creer;
	private Button reset;
	private String recId;
	private int id;
	private ArrayList<UserDB>listUser = new ArrayList();
	
	private Connection con=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creer_tache);
				
		titre=(EditText) findViewById(R.id.titreedit);
		description=(EditText) findViewById(R.id.descriptionedit);
		date_tache=(EditText) findViewById(R.id.dateedit);
		num=(EditText) findViewById(R.id.ordreedit);
		//depanneur=(EditText) findViewById(R.id.depanneuredit);
		spindep=(Spinner) findViewById(R.id.depanneuredit);
		error=(TextView) findViewById(R.id.erreur);
		creer=(Button)findViewById(R.id.ajouter);
		reset=(Button)findViewById(R.id.reset);
		
		
		try{
			Intent i=getIntent();			
			id = Integer.parseInt(i.getStringExtra("sendid"));
			Log.d("creer get i",""+id);
			listUser = i.getParcelableExtra(MainActivity.LISTDEP);
			Log.d("creer get i","listeUseer ok");
		}catch(Exception ex){
			Log.d("Test Intend creer",""+ex.getMessage());
		}
		
		List<String> list = new ArrayList<String>();
		Iterator<UserDB> it = listUser.iterator();
		for (UserDB us : listUser) {
			it.next();			
			list.add(it.toString());
			
			//list.add (""+listUser.getNom()+ " "+listUser(n).getPrenom());
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spindep.setAdapter(dataAdapter);
		
		//id = 3;
		creer.setOnClickListener(
				new OnClickListener(){					
					public void onClick(View v){																
						MyAccesDB adb = new MyAccesDB(CreerTache.this);
						adb.execute();
						titre.setText("");
						description.setText("");
						date_tache.setText("");
						num.setText("");
						depanneur.setText("");						
					}
				  }
				);
	    
	  reset.setOnClickListener(
				new OnClickListener(){					
					public void onClick(View v){
						titre.setText("");
						description.setText("");
						date_tache.setText("");
						num.setText("");
						depanneur.setText("");																
					}
				  }
				);
	    	    
	}

	private void While() {
		// TODO Auto-generated method stub
		
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
					//se fait avant l'opération
					protected void onPreExecute(){
						 super.onPreExecute();
				         pgd=new ProgressDialog(CreerTache.this);
						 pgd.setMessage("création de la tâche en cours");
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
				        	UserDB us = new UserDB(id);
				    	    Log.e("test id user",""+us.getIduser());
				    	    Log.e("test titre",""+titre.getText().toString());
				    	    Log.e("test desc",""+description.getText().toString());
				        	String vtitre=titre.getText().toString();
						    String vdesc=description.getText().toString();
						    String vdate=date_tache.getText().toString();
						    int vnum = Integer.parseInt(num.getText().toString());
						    int vdepanneur = Integer.parseInt(depanneur.getText().toString());
						    
						    //vtitre="test";
						    //vdesc="tester";
						    
						    Log.e("test titre",""+vtitre);
						    Log.e("test description",""+vdesc);
						    Log.e("test date",vdate);
						    
				        	TacheDB tc=new TacheDB(vtitre,vdesc,vdate,vnum,vdepanneur,us.getIduser());
				        	tc.create();
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
						  error.setText(resultat);
						  		  
									
					}
			
				}
}
