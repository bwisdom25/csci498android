package edu.mines.csci498.bwisdom.lunchlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements LunchFragment.OnRestaurantListener {
	
	public final static String ID_EXTRA = "edu.mines.csci498.bwisdom.lunchlist._ID";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LunchFragment lunch = (LunchFragment)getSupportFragmentManager().findFragmentById(R.id.lunch);
		
		lunch.setOnRestaurntListener(this);
	}
	
	public void onRestaurantSelected(long id) {
		
		if(findViewById(R.id.details) == null) {
			Intent i = new Intent(this, DetailForm.class);
			
			i.putExtra(ID_EXTRA, String.valueOf(id));
			startActivity(i);
		} else {
		//do something here!
		}
	} 
}
