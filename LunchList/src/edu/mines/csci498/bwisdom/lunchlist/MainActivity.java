package edu.mines.csci498.bwisdom.lunchlist;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

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
			FragmentManager fragMgr = getSupportFragmentManager();
			DetailFragment details = (DetailFragment)fragMgr.findFragmentById(R.id.details);
			
			if(details == null) {
				details = DetailFragment.newInstance(id);
				
				FragmentTransaction xaction = fragMgr.beginTransaction();
				
				xaction.add(R.id.details, details);
				xaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				xaction.addToBackStack(null);
				xaction.commit();
			} else {
				details.loadRestaurant(String.valueOf(id));
			}
		}
	} 
}
