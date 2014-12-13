package be.condorcet.projetgroup2;

import be.condorcet.projetgroup2.MainActivity.MyAccesDB;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuAdmin extends Activity {
	
	private int id;
	private Button creer;
	private Button affTache;
	private Button affStaff;
	private Button deconnexion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_admin);
		
		
		affTache=(Button)findViewById(R.id.AfficherTache1);
		affStaff=(Button)findViewById(R.id.AfficherTacheStaff);
		creer=(Button)findViewById(R.id.CreerTache);
		deconnexion=(Button)findViewById(R.id.Deconnexion2);
		
		try{
			Intent i=getIntent();			
			id = Integer.parseInt(i.getStringExtra("sendid2"));
			Log.d("robin",""+id);
		}catch(Exception ex){
			Log.d("Test Intend",""+ex.getMessage());
		}
		
		affTache.setOnClickListener(
				new OnClickListener(){									
					public void onClick(View v){									
												
														
					}
				  }
				);
		
		deconnexion.setOnClickListener(
				new OnClickListener(){									
					public void onClick(View v){									
												
														
					}
				  }
				);
		
		affStaff.setOnClickListener(
				new OnClickListener(){									
					public void onClick(View v){									
												
														
					}
				  }
				);
		
		creer.setOnClickListener(
				new OnClickListener(){									
					public void onClick(View v){									
						Intent i = new Intent(MenuAdmin.this,CreerTache.class);
						i.putExtra("sendid2",""+id);
						Log.d("Main",""+id);
						startActivity(i);
						finish();						
														
					}
				  }
				);
	}
}
