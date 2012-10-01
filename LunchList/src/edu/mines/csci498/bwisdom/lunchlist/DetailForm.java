package edu.mines.csci498.bwisdom.lunchlist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.util.Log;

public class DetailForm extends Activity {
	EditText name = null;
	EditText address = null;
	EditText notes = null;
	EditText feed = null;
	RadioGroup types = null;
	RestaurantHelper helper = null; 	
	String restaurantId = null; 
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		Log.d("DETAILFORM", "activity created!");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_form);
		
		helper = new RestaurantHelper(this);
		
		name = (EditText) findViewById(R.id.name);
		address = (EditText) findViewById(R.id.addr);
		types = (RadioGroup) findViewById(R.id.types);
		notes = (EditText) findViewById(R.id.notes);
		feed = (EditText) findViewById(R.id.feed);
		
		Button save = (Button) findViewById(R.id.save);

		save.setOnClickListener(onSave);
		
		restaurantId = getIntent().getStringExtra(MainActivity.ID_EXTRA);
		
		if(restaurantId != null){
			load();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		
		state.putString("name", name.getText().toString());
		state.putString("address", address.getText().toString());
		state.putInt("type", types.getCheckedRadioButtonId());
		state.putString("notes", notes.getText().toString());
	}
	
	@Override 
	public void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		
		name.setText(state.getString("name"));
		address.setText(state.getString("address"));
		types.check(state.getInt("type"));
		notes.setText(state.getString("notes"));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.details_option, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {

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
							  feed.getText().toString());
			} else {
				helper.update(restaurantId,name.getText().toString(),
						  address.getText().toString(),
						  type, notes.getText().toString(),
						  feed.getText().toString());
			}
			finish();
		}
	};
	
	private void load() {
		Cursor c = helper.getById(restaurantId);
		
		c.moveToFirst();
		name.setText(helper.getName(c));
		address.setText(helper.getAddress(c));
		notes.setText(helper.getName(c));
		feed.setText(helper.getFeed(c));
		
		if(helper.getType(c).equals("sit_down")){
			types.check(R.id.sit_down);
		}else if(helper.getType(c).equals("takeout")){
			types.check(R.id.takeout);
		}else{
			types.check(R.id.delivery);
		}

		c.close();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		helper.close();
	}
	
}
