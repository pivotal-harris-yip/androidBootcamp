package com.example.androidbootcamp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.androidbootcamp.QueryTask.Movie;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.androidbootcamp.MESSAGE";
	public final static String EXTRA_PICTURE = "com.example.androidbootcamp.PICTURE";
	
	private List<Movie> movieData;
	
	public void setMovieData(List<Movie> data) {
		this.movieData = data;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URL url = null;
        
		try {
			url = new URL("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?limit=50&country=us&apikey=bapg8jv23rf37dcusgra7znj");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		ListView list = (ListView) findViewById(R.id.list);
        
		new QueryTask(this, getApplicationContext(), list).execute(url);
	        	
        list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent (MainActivity.this, DisplayMessageActivity.class);
				String title = movieData.get(position).title;
				String picture = movieData.get(position).picture;
		    	intent.putExtra(EXTRA_MESSAGE, title);
		    	intent.putExtra(EXTRA_PICTURE, picture);
		    	startActivity(intent);
			}
		});
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
	    	case R.id.action_search:
	    		openSearch();
	    		return true;
    		default:
    			return false;
    	}
    }
    
    private void openSearch() {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
    
}

