package com.example.androidbootcamp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

public void sendMessage (View view) {
	Intent intent = new Intent (this, DisplayMessageActivity.class);
	EditText editText = (EditText) findViewById(R.id.edit_message);
	String message = editText.getText().toString();
	intent.putExtra(EXTRA_MESSAGE, message);
	startActivity(intent);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private class queryTask extends AsyncTask<URL, Integer, String>{

    	Context context;
    	
    	public queryTask(Context context) {
    		this.context = context.getApplicationContext();
    	}
    	
		@Override
		protected String doInBackground(URL... arg0) {
			HttpURLConnection urlConnection = null;
			String output = "";
	    	try {
				URL url = new URL("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?limit=1&country=us&apikey=bapg8jv23rf37dcusgra7znj");
				urlConnection = (HttpURLConnection) url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				System.out.println("HERE");
				String inputLine = "";
				while( (inputLine = in.readLine()) != null){
					output += inputLine;
				}
	    	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				urlConnection.disconnect();
			}
	    	System.out.println(output);
			return output;
		} 
		
		protected void onPostExecute(String output){
			Intent intent = new Intent (context, DisplayMessageActivity.class);
	    	intent.putExtra(EXTRA_MESSAGE, output);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	context.startActivity(intent);
		}
		
    }
    
    public void loadList(View view){
		new queryTask(getApplicationContext()).execute();
    	/*Intent intent = new Intent (this, DisplayMessageActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, output);
    	startActivity(intent);*/
    }
    
}

