package com.example.weatherforecasts;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	// url to make request
	private static String url = "http://swellcast.com.au/api/v1/states.json?api_key=wBALPyzyiZAKfstMW3UF";
	
	// JSON Node names
	private static final String ID = "id";
	private static final String NAME = "name";

	// contacts JSONArray
	JSONArray statesArray = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> states = new ArrayList<HashMap<String, String>>();

		// Creating JSON Parser instance
		JSONParser parser = new JSONParser();

		// getting JSON string from URL
		//JSONArray json = parser.getJSONArrayfromURL(url);


	         
		try {
			// Getting Array of States
			statesArray = parser.getJSONArrayfromURL(url);
			
			// looping through All Contacts
			for(int i = 0; i < statesArray.length(); i++){
				JSONObject c = statesArray.getJSONObject(i);
				
				// Storing each json item in variable
				String id = c.getString(ID);
				String name = c.getString(NAME);
				
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				
				// adding each child node to HashMap key => value
				map.put(ID, id);
				map.put(NAME, name);

				// adding HashList to ArrayList
				states.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
		ListAdapter adapter = new SimpleAdapter(this, states,
				R.layout.list_item,
				new String[] { NAME, ID  }, new int[] {
						R.id.name, R.id.id});

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String locationid = ((TextView) view.findViewById(R.id.id)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(NAME, name);
				in.putExtra(ID, locationid);

				startActivity(in);

			}
		});
		
		

	}

}