package be.condorcet.projetgroup2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MenuAdmin extends Activity {
	
	private int id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_admin);
		
		try{
			Intent i=getIntent();
			
			id = Integer.parseInt(i.getStringExtra("sendid2"));
			Log.d("robin",""+id);
		}catch(Exception ex){
			Log.d("Test Intend",""+ex.getMessage());
		}
	}
}
