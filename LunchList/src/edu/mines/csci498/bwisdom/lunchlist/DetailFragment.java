package edu.mines.csci498.bwisdom.lunchlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.support.v4.app.Fragment;

public class DetailFragment extends Fragment {
	
	private static final String ARG_REST_ID = "edu.mines.csci498.bwisdom.lunchlist.ARG_REST_ID";
	
	EditText name = null;
	EditText address = null;
	EditText phone = null;
	EditText notes = null;
	EditText feed = null;
	TextView location = null;
	RadioGroup types = null;
	RestaurantHelper helper = null; 	
	String restaurantId = null; 

	LocationManager locMgr = null;

	double latitude = 0.0d;
	double longitude = 0.0d;

	LocationListener onLocationChange = new LocationListener() {
		public void onLocationChanged(Location fix) {
			helper.updateLocation(restaurantId, fix.getLatitude(), fix.getLongitude());
			Log.d("DETAILFORM", "Location updated in Database!");
			location.setText(String.valueOf(fix.getLatitude()) + ", " + String.valueOf(fix.getLongitude()));
			Log.d("DETAILFORM", "Location textview updated!");
			locMgr.removeUpdates(onLocationChange);

			Toast.makeText(getActivity(), "Location Saved", Toast.LENGTH_LONG).show();

		}

		@Override
		public void onProviderDisabled(String provider) {
			// Required method for interface (NOT USED)

		}

		@Override
		public void onProviderEnabled(String provider) { 
			// Required method for interface (NOT USED)

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// Required method for interface (NOT USED)

		}

	};


	public static DetailFragment newInstance(long id) {
		DetailFragment result = new DetailFragment();
		Bundle args = new Bundle();
		
		args.putString(ARG_REST_ID, String.valueOf(id));
		result.setArguments(args);
		
		return result;
	}
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
		
	private RestaurantHelper getHelper() {
		if(helper == null) {
			helper = new RestaurantHelper(getActivity());
		}
		
		return helper;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		locMgr = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

		name = (EditText) getActivity().findViewById(R.id.name);
		address = (EditText) getActivity().findViewById(R.id.addr);
		phone = (EditText) getActivity().findViewById(R.id.phone);
		types = (RadioGroup) getActivity().findViewById(R.id.types);
		notes = (EditText) getActivity().findViewById(R.id.notes);
		feed = (EditText) getActivity().findViewById(R.id.feed);
		location = (TextView) getActivity().findViewById(R.id.location);
		
		Bundle args = getArguments();
		
		if(args != null) {
			loadRestaurant(args.getString(ARG_REST_ID));
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.detail_form,container,false);
	}
	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);

		state.putString("name", name.getText().toString());
		state.putString("address", address.getText().toString());
		state.putString("phone", phone.getText().toString());
		state.putInt("type", types.getCheckedRadioButtonId());
		state.putString("notes", notes.getText().toString());
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.details_option, menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		if(restaurantId == null ) {
			menu.findItem(R.id.location).setEnabled(false);
			menu.findItem(R.id.map).setEnabled(false);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("DetailForm","MenuItem Selected");
		if(item.getItemId() == R.id.menufeed) {
			if( isNetworkAvailable() ) {

				Intent i = new Intent(getActivity(), FeedActivity.class);
				Log.d("DetailForm", "NetworkAvailalbe Starting intent");
				i.putExtra(FeedActivity.FEED_URL, feed.getText().toString());
				startActivity(i);

			} else {

				Toast.makeText(getActivity(), "Sorry, The Internets are not available", Toast.LENGTH_LONG).show();

			}

			return true;
		} else if(item.getItemId() == R.id.location) {

			locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, onLocationChange);
			return true;

		} else if(item.getItemId() == R.id.map) {

			Intent i = new Intent(getActivity(),RestaurantMap.class);

			i.putExtra(RestaurantMap.EXTRA_LATITUDE, latitude);
			i.putExtra(RestaurantMap.EXTRA_LONGITUDE, longitude);
			i.putExtra(RestaurantMap.EXTRA_NAME, name.getText().toString());

			startActivity(i);

			return true;

		} else if(item.getItemId() == R.id.help) {
			startActivity(new Intent(getActivity(),HelpPage.class));
		}

		return super.onOptionsItemSelected(item);
	}

    private boolean isNetworkAvailable() {
    	ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    	
    	NetworkInfo info = cm.getActiveNetworkInfo();
    	
    	return (info != null);
    }
    
    public void loadRestaurant(String restaurantId) {
    	this.restaurantId = restaurantId;
    	
    	if(restaurantId != null) {
    		load();
    	}
    }

	public void save() {
		if(name.getText().toString().length() > 0 ) {
			String type = null;

			switch (types.getCheckedRadioButtonId()) {
			case R.id.sit_down:
				type = "sit_down";
				break;
			case R.id.takeout:
				type = "takeout";
				break;
			case R.id.delivery:
				type = "delivery";
				break;
			}

			if(restaurantId == null ){
				helper.insert(name.getText().toString(),
							  address.getText().toString(),
							  type, notes.getText().toString(),
							  feed.getText().toString(),
							  phone.getText().toString());
			} else {
				helper.update(restaurantId,name.getText().toString(),
						  address.getText().toString(),
						  type, notes.getText().toString(),
						  feed.getText().toString());
			}
		}
	}

	private void load() {
		Cursor c = getHelper().getById(restaurantId);

		c.moveToFirst();
		name.setText(getHelper().getName(c));
		address.setText(getHelper().getAddress(c));
		phone.setText(getHelper().getPhone(c));
		notes.setText(getHelper().getName(c));
		feed.setText(getHelper().getFeed(c));

		if(getHelper().getType(c).equals("sit_down")){
			types.check(R.id.sit_down);
		}else if(getHelper().getType(c).equals("takeout")){
			types.check(R.id.takeout);
		}else{
			types.check(R.id.delivery);
		}

		latitude = getHelper().getLatitude(c);
		longitude = getHelper().getLongitude(c);

		location.setText(String.valueOf(helper.getLatitude(c)) + ", " + String.valueOf(helper.getLongitude(c)));

		c.close();
	}

	@Override
	public void onPause() {

		save();
		helper.close();
		locMgr.removeUpdates(onLocationChange);
		super.onPause();
	}

}