package com.example.androidbootcamp;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.androidbootcamp.QueryTask.Movie;

public class ClickEvent implements OnItemClickListener {
	
	private Context context;
	private List<Movie> movieData;
	
	public ClickEvent(Context context, List<Movie> movieData) {
		this.context = context;
		this.movieData = movieData;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent (context, DisplayMessageActivity.class);
		String title = movieData.get(position).title;
		String picture = movieData.get(position).picture;
		String synopsis = movieData.get(position).synopsis;
		String criticRating = movieData.get(position).criticRating;
		String consensus = movieData.get(position).criticConsensus;
		String rating = "";
		
		if (!criticRating.equals("-1")){
			rating += "Critic Score: " + criticRating + "\n";
		}
		
		String userRating = movieData.get(position).userRating;
		if (!userRating.equals("-1")){
			rating += "Audience Score: " + userRating;
		}
		
		if (rating.equals("")){
			rating = "No ratings yet!";
		}
    	intent.putExtra(MainActivity.EXTRA_MESSAGE, title);
    	intent.putExtra(MainActivity.EXTRA_PICTURE, picture);
    	intent.putExtra(MainActivity.EXTRA_SYNOPSIS, synopsis);
    	intent.putExtra(MainActivity.EXTRA_RATINGS, rating);
    	intent.putExtra(MainActivity.EXTRA_CONSENSUS, consensus);
    	context.startActivity(intent);
	} 
}
