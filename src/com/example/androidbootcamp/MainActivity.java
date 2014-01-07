package com.example.androidbootcamp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Handler handler = new Handler();
        setContentView(R.layout.activity_main);
        handler.postDelayed(new Runnable(){
    		int i = 10;
			@Override
			public void run() {
		        new queryTask(getApplicationContext(), i).execute();
	        	i++;
		}}, 5000);
    }

	/*public void sendMessage (View view) {
		Intent intent = new Intent (this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}*/
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private class queryTask extends AsyncTask<URL, Integer, String>{

    	Context context;
    	int number;
    	
    	public queryTask(Context context, int num) {
    		this.context = context.getApplicationContext();
    		this.number = num;
    	}
    	
		@Override
		protected String doInBackground(URL... arg0) {
			HttpURLConnection urlConnection = null;
			String output = "";
	    	try {
				URL url = new URL("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?limit=" + number + "&country=us&apikey=bapg8jv23rf37dcusgra7znj");
				urlConnection = (HttpURLConnection) url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				System.out.println("HERE");
				String inputLine = "";
				while( (inputLine = in.readLine()) != null){
					output += inputLine;
				}
	    	} catch (Exception e) {
				e.printStackTrace();
			} finally {
				urlConnection.disconnect();
			}
	    	System.out.println(output);
			return output;
		} 
		
		protected void onPostExecute(String output){
			try {
				JSONObject data = new JSONObject(output);
				JSONArray movies = data.getJSONArray("movies");
				List<String> titles = new ArrayList<String>();
				for(int i = 0; i < movies.length(); i++) {
					titles.add(movies.getJSONObject(i).getString("title"));
				}
				//System.out.println(Arrays.toString(titles.toArray(new String[titles.size()])));
				//add stuff to ListView
				ListView list = (ListView) findViewById(R.id.list);
				ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_activated_1, titles);
				list.setAdapter(titleAdapter);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			/*Intent intent = new Intent (context, DisplayMessageActivity.class);
	    	intent.putExtra(EXTRA_MESSAGE, output);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	context.startActivity(intent);*/
		}
		
    }
    
//    public void loadList(View view){
//		
//    	/*Intent intent = new Intent (this, DisplayMessageActivity.class);
//    	intent.putExtra(EXTRA_MESSAGE, output);
//    	startActivity(intent);*/
//    }
    
}

