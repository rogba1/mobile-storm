package com.example.weatherforecasts;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.app.ListActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class SingleMenuItemActivity  extends ListActivity {
	
	private static String url;
	
	// JSON node keys
	private static final String ID = "id";
	private static final String NAME = "name";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String id = in.getStringExtra(ID);
        String name = in.getStringExtra(NAME);
        
        url  = "http://swellcast.com.au/api/v1/states/" + id + ".json?api_key=wBALPyzyiZAKfstMW3UF";
		

        
     // Hashmap for ListView
     		ArrayList<HashMap<String, String>> locationList = new ArrayList<HashMap<String, String>>();

     		JSONArray locationArray = null;
     		// Creating JSON Parser instance
     		JSONParser parser = new JSONParser();

     		// getting JSON string from URL
     		JSONObject json = parser.getJSONFromUrl(url);


     	         
     		try {
     		
     			// Getting Array of locations
     			locationArray = json.getJSONArray("locations");
    			
    			// looping through All locations
    			for(int i = 0; i < locationArray.length(); i++){
    				JSONObject c = locationArray.getJSONObject(i);

     				// Storing each json item in variable
     				id = c.getString("id");
     				name = c.getString("name");
     				String latitude = c.getString("latitude");
     				String longitude = c.getString("longitude");
     				
     				// creating new HashMap
     				HashMap<String, String> map = new HashMap<String, String>();
     				
     				// adding each child node to HashMap key => value
     				map.put("id", id);
     				map.put("name", name);

     				// adding HashList to ArrayList
     				locationList.add(map);
     			}
     		} catch (JSONException e) {
     			e.printStackTrace();
     		}
     		
    		/**
    		 * Updating parsed JSON data into ListView
    		 * */
    		ListAdapter adapter = new SimpleAdapter(this, locationList,
    				R.layout.location_list,
    				new String[] { "name", "id"  }, new int[] {
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
    				Intent in = new Intent(getApplicationContext(), WeatherActivity.class);
    				in.putExtra(NAME, name);
    				in.putExtra(ID, locationid);

    				startActivity(in);

    			}
    		});
    		
    }
}
