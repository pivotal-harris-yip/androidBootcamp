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

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class QueryTask extends AsyncTask<URL, Integer, String>{

    	private Context context;
    	private Object activity;
    	private ListView listView;
    	
    	public QueryTask(Object activity, Context context, ListView listView) {
    		this.context = context.getApplicationContext();
    		this.activity = activity;
    		this.listView = listView;
    	}
    	
    	public class Movie{
    		String title;
    		String picture;
    	}
    	
		@Override
		protected String doInBackground(URL... url) {
			HttpURLConnection urlConnection = null;
			String output = "";
	    	try {
				urlConnection = (HttpURLConnection) url[0].openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String inputLine = "";
				while( (inputLine = in.readLine()) != null){
					output += inputLine;
				}
	    	} catch (Exception e) {
				e.printStackTrace();
			} finally {
				urlConnection.disconnect();
			}
			return output;
		} 
		
		protected void onPostExecute(String output){
			try {
				JSONObject data = new JSONObject(output);
				JSONArray movies = data.getJSONArray("movies");
				List<String> titles = new ArrayList<String>();
				List<Movie> movieList = new ArrayList<Movie>();
				for(int i = 0; i < movies.length(); i++) {
					titles.add(movies.getJSONObject(i).getString("title"));
					Movie insert = new Movie();
					insert.title = movies.getJSONObject(i).getString("title");
					insert.picture = movies.getJSONObject(i).getJSONObject("posters").getString("detailed");
					movieList.add(insert);
				}

				ListView list = listView;
				ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, titles);
				list.setAdapter(titleAdapter);
				
				if(activity instanceof MainActivity) {
					((MainActivity) activity).setMovieData(movieList);
				} else if (activity instanceof SearchActivity) {
					((SearchActivity) activity).setMovieData(movieList);
				} else {
					throw new IllegalArgumentException("WHAT DID YOU DO???");
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
    }
