package edu.mines.csci498.bwisdom.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class MainActivity extends Activity {

	Restaurant r = new Restaurant();
	List<Restaurant> model = new ArrayList<Restaurant>();
	ArrayAdapter<Restaurant> adapter = null; 
	ArrayAdapter<String> addressAdapter = null;
	
	
	String[] addresses = { "1600 Pennsylvania Avenue, NW Washington, DC 20500" , 
			"3765 Elvis Presley Blvd., Memphis, TN 38116.",
			"60 East Broadway Bloomington, MN 55425"};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button save = (Button) findViewById(R.id.save);

		save.setOnClickListener(onSave);
		
		initiateSpinner();
		
		initiateAutoTextComplete();

	}
	
	private void initiateSpinner(){
		Spinner restSpinner = (Spinner) findViewById(R.id.restaurants);
		
		adapter = new ArrayAdapter<Restaurant>(this,
				android.R.layout.simple_spinner_item, model);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		restSpinner.setAdapter(adapter);
	}
	
	private void initiateAutoTextComplete(){
		int THRESHOLD = 2;
		AutoCompleteTextView autoAddr = (AutoCompleteTextView) findViewById(R.id.addr);

		addressAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, addresses);

		autoAddr.setThreshold(THRESHOLD);
		autoAddr.setAdapter(addressAdapter);
	}

	private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			Restaurant r = new Restaurant();	
			EditText name = (EditText) findViewById(R.id.name);
			EditText address = (EditText) findViewById(R.id.addr);

			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			RadioGroup types = (RadioGroup) findViewById(R.id.types);
			
			switch(types.getCheckedRadioButtonId()){
				case R.id.sit_down:
					r.setType("sit_down");
					break;
				case R.id.takout:
					r.setType("takeout");
					break;
				case R.id.delivery:
					r.setType("delivery");
					break;
			}	
			adapter.add(r);  
		}
	};
}
