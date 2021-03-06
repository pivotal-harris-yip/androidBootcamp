package com.example.androidbootcamp;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		URL picture = null;
		String synopText = intent.getStringExtra(MainActivity.EXTRA_SYNOPSIS);
		String ratingsText = intent.getStringExtra(MainActivity.EXTRA_RATINGS);
		String consensusText = intent.getStringExtra(MainActivity.EXTRA_CONSENSUS);
		
		try {
			picture = new URL(intent.getStringExtra(MainActivity.EXTRA_PICTURE));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		TextView textView = (TextView) findViewById(R.id.movie_title);
		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Montserrat.ttf");
		textView.setTypeface(typeFace);
		textView.setTextSize(28);
		textView.setText(message);
		
		if (synopText.equals("")){
			synopText = "There does not seem to be an synopsis here! Go to www.rottentomatoes.com for further inquiries.";
		}
		
		TextView synopsis = (TextView) findViewById(R.id.synopsis);
		synopsis.setTextSize(15);
		synopsis.setText(synopText);
		
		TextView ratings = (TextView) findViewById(R.id.ratings);
		ratings.setTextSize(15);
		ratings.setText(ratingsText);
		
		TextView consensus = (TextView) findViewById(R.id.critic_consensus);
		consensus.setTextSize(15);
		consensus.setText(consensusText);
		
		new ImageGet().execute(picture);
		
		// Show the Up button in the action bar.
		setupActionBar();
	}
	
	private class ImageGet extends AsyncTask<URL, Integer, Drawable>{

		@Override
		protected Drawable doInBackground(URL... url) {
			Drawable d = null;
			try {
				InputStream content = (InputStream) url[0].getContent();
				d = Drawable.createFromStream(content , "src"); 
			} catch(Exception e) {
				e.printStackTrace();
			}
			return d;
		}
		
		protected void onPostExecute(Drawable d){
			ImageView imageView = (ImageView) findViewById(R.id.movie_image);
			imageView.setImageDrawable(d);
		}
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
		getMenuInflater().inflate(R.menu.display_message, menu);
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
			this.finish();
			return true;
			
		}
		return super.onOptionsItemSelected(item);
	}

}
