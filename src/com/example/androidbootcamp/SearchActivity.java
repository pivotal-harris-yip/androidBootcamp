package com.example.androidbootcamp;

import java.net.URL;
import java.net.URLEncoder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.activity_search);
	
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void SearchClick(View view){
		URL url = null;
		try {
			String searchQuery = URLEncoder.encode(((EditText) findViewById(R.id.search_field)).getText().toString(), "UTF-8");
			String urlString = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q="+ searchQuery + "&page_limit=50&page=1&apikey=bapg8jv23rf37dcusgra7znj";
			url = new URL(urlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ListView list = (ListView) findViewById(R.id.search_list);
        
		new QueryTask(this, getApplicationContext(), list).execute(url);        	
	}

	public Dialog getDialog() {
		// TODO Auto-generated method stub
		return null;
	}
}
