package edu.mines.csci498.bwisdom.lunchlist;

import android.os.Bundle;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	Restaurant r = new Restaurant();
	Restaurant current;
	Cursor model;
	RestaurantAdapter adapter;
	RestaurantHelper helper;
	EditText name;
	EditText address;
	RadioGroup types;
	EditText notes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		helper = new RestaurantHelper(this);
		model = helper.getAll();
		startManagingCursor(model);

		adapter = new RestaurantAdapter(model);
		setListAdapter(adapter);

	}

	
	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent i = new Intent(MainActivity.this,DetailForm.class);
			
			startActivity(i);
		}
	};

	class RestaurantAdapter extends CursorAdapter {
		RestaurantAdapter(Cursor c) {
			super(MainActivity.this, c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {

			RestaurantHolder holder = (RestaurantHolder) row.getTag();

			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {

			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);

			RestaurantHolder holder = new RestaurantHolder(row);

			row.setTag(holder);

			return (row);
		}
	}

	static class RestaurantHolder {
		private TextView name = null;
		private TextView address = null;
		private ImageView icon = null;

		RestaurantHolder(View row) {
			name = (TextView) row.findViewById(R.id.title);
			address = (TextView) row.findViewById(R.id.address);
			icon = (ImageView) row.findViewById(R.id.icon);
		}

		void populateFrom(Cursor c, RestaurantHelper helper) {
			name.setText(helper.getName(c));
			address.setText(helper.getAddress(c));

			if (helper.getType(c).equals("sit_down")) {
				icon.setImageResource(R.drawable.sit_down);
			} else if (helper.getType(c).equals("takeout")) {
				icon.setImageResource(R.drawable.takeout);
			} else {
				icon.setImageResource(R.drawable.delivery);
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		helper.close();
	}
}
