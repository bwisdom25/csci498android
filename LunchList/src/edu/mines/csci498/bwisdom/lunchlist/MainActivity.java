package edu.mines.csci498.bwisdom.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	Restaurant r = new Restaurant();
	List<Restaurant> model = new ArrayList<Restaurant>();
	RestaurantAdapter adapter = null; 
	EditText name = null;
	EditText address = null;
	RadioGroup types = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		name = (EditText)findViewById(R.id.name);
		address = (EditText)findViewById(R.id.addr);
		types = (RadioGroup)findViewById(R.id.types);
		
		Button save = (Button) findViewById(R.id.save);

		save.setOnClickListener(onSave);
		
		ListView list = (ListView) findViewById(R.id.restaurants);
		
		adapter = new RestaurantAdapter();
		list.setAdapter(adapter);
		
		
		TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
		
		spec.setContent(R.id.restaurants);
		spec.setIndicator("List", getResources().getDrawable(R.drawable.list));
		getTabHost().addTab(spec);
		
		spec = getTabHost().newTabSpec("tag2");
		spec.setContent(R.id.details);
		
		spec.setIndicator("Details", getResources().getDrawable(R.drawable.restaurant));
		getTabHost().addTab(spec);
		
		getTabHost().setCurrentTab(0);
		
		list.setOnItemClickListener( onListClick);		
	}

	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Restaurant r = model.get(position);
			
			name.setText(r.getName());
			address.setText(r.getAddress());
			
			if(r.getType().equals("sit_down")){
				types.check(R.id.sit_down);
			}else if(r.getType().equals("takeout")){
				types.check(R.id.takeout);
			}else{
				types.check(R.id.delivery);
			}
			getTabHost().setCurrentTab(1);

		}
	};
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
				case R.id.takeout:
					r.setType("takeout");
					break;
				case R.id.delivery:
					r.setType("delivery");
					break;
			}	
			adapter.add(r);  
		}
	};
	
	class RestaurantAdapter extends ArrayAdapter<Restaurant>{
		RestaurantAdapter(){
			super(MainActivity.this, android.R.layout.simple_list_item_1,model);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			RestaurantHolder holder = null;

			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();

				row = inflater.inflate(R.layout.row, parent, false);
				holder = new RestaurantHolder(row);
				row.setTag(holder);
			} else {
				holder = (RestaurantHolder) row.getTag();
			}

			holder.populateFrom(model.get(position));

			return row;
		}
	}
	
	static class RestaurantHolder{
		private TextView name = null;
		private TextView address = null;
		private ImageView icon = null; 
		
		RestaurantHolder(View row) {
			name = (TextView) row.findViewById(R.id.title);
			address = (TextView) row.findViewById(R.id.address);
			icon = (ImageView) row.findViewById(R.id.icon);
		}
		
		void populateFrom(Restaurant r) {
			name.setText(r.getName());
			address.setText(r.getAddress());

			if (r.getType().equals("sit_down")) {
				icon.setImageResource(R.drawable.sit_down);
			} else if (r.getType().equals("takeout")) {
				icon.setImageResource(R.drawable.takeout);
			} else {
				icon.setImageResource(R.drawable.delivery);
			}
		}
		
	}
}
