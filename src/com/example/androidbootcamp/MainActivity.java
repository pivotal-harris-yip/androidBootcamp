package com.example.androidbootcamp;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.androidbootcamp.MESSAGE";
	public final static String EXTRA_PICTURE = "com.example.androidbootcamp.PICTURE";
	public final static String EXTRA_SYNOPSIS = "com.example.androidbootcamp.SYNOPSIS";
	public final static String EXTRA_RATINGS = "com.example.androidbootcamp.RATINGS";
	public final static String EXTRA_CONSENSUS = "com.example.androidbootcamp.CONSENSUS";
	public final static int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog loading;
		
	public ProgressDialog getDialog() {
		return loading;
	}
	
	@Override 
	protected Dialog onCreateDialog(int id) {
		switch(id){
		case DIALOG_DOWNLOAD_PROGRESS:
			loading = new ProgressDialog(this);
			loading.setMessage("Loading...");
			loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			loading.setCancelable(false);
			loading.show();
			return loading;
		default:
			return null;				
		}
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
	    	case R.id.action_settings:
	    		Toast toast = Toast.makeText(this, "There are no settings.", Toast.LENGTH_LONG);
	    		//toast.getView().setBackgroundColor(Color.CYAN);
	    		//toast.getView().setAlpha(0f);
	    		toast.show();
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

