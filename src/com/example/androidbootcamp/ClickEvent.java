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
    	intent.putExtra(MainActivity.EXTRA_MESSAGE, title);
    	intent.putExtra(MainActivity.EXTRA_PICTURE, picture);
    	intent.putExtra(MainActivity.EXTRA_SYNOPSIS, synopsis);
    	context.startActivity(intent);
	} 
}
