/**
 * 
 */
package com.telstra.newsfeed;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.telstra.newsfeed.constants.NewsFeedAppConstants;
import com.telstra.newsfeed.database.NewsFeedDBHandler;
import com.telstra.newsfeed.model.NewsItem;
import com.telstra.newsfeed.util.AlertDialogFragment;
import com.telstra.newsfeed.util.AlertDialogFragment.AlertDialogListener;
import com.telstra.newsfeed.util.NetworkUtil;
import com.telstra.newsfeed.util.NewsFeedUtil;


/**
 * @author Balajee_Raghbendra
 *
 */
public class SplashScreen extends FragmentActivity  {
	
	public List<NewsItem> newsItems = null;
	NewsFeedDBHandler dbHandler; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*
		 * Show Splash screen here
		 */
		setContentView(R.layout.activity_splash);
		
		/*
		 * Make network call to fetch the json data from https://dl.dropboxusercontent.com/u/746330/facts.json
		 */
		dbHandler = new NewsFeedDBHandler(this.getApplicationContext(),NewsFeedAppConstants.DATABASE_NAME,null,1);
		NetworkUtil.set_context(getApplicationContext());
		if(NetworkUtil.isConnectingToInternet())
		{
		new PrefetchData().execute();
		}
		else
		{
			showError(NewsFeedAppConstants.NETWORK_ERROR,1);
		}
		
	}
	
	/**
	 * Async Task implementation
	 * @author Balajee_Raghbendra
	 *
	 */
	private class PrefetchData extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled(Void result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			/**
			 * This method will make http request and fetch the news feed json
			 */
			newsItems = NewsFeedUtil.getJSONData();
			if(null != newsItems && newsItems.size() > 0)
			{
				dbHandler.addNewsFeed(newsItems);
			}
			
			return null;
		}



		
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			launchNewsFeedActivity();

		}

	
		
	}
	private void launchNewsFeedActivity() {
		Intent i = new Intent(SplashScreen.this, NewsFeedActivity.class);
		NewsFeedActivity.newsFeeds = newsItems;
        startActivity(i);

        // close this activity
        finish();
	}	
	
	
	
	public void showError(String alertMsgString,int errorNo)
	{
		// TODO Auto-generated method stub
		final String msgString = alertMsgString;
		View inflater = View.inflate(this, R.layout.alert, null);
		TextView textView = (TextView) inflater
				.findViewById(R.id.ExceptionMessage);
		//textView.setTypeface(RobotoTypeface.sRobotoRegular(this));
		textView.setText(msgString);
		textView.setTextColor(Color.parseColor("#ffffff"));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		AlertDialogFragment alertDlgFrg = AlertDialogFragment.newInstance(
				"Network Error", inflater, 0, "Ok", null);
		alertDlgFrg.onClickAlertDialog(new AlertDialogListener() {

			@Override
			public void onDialogPositiveClick(DialogFragment dialog) {
				
				 if(dbHandler.fetchAllNewsFeeds().size() > 0)
	        	   {
	        		   launchNewsFeedActivity();
	        	   }
	        	   else
	        	   {
	        		   System.exit(1);
	        	   }
	        	   
				/*dialog.dismiss();
				finish();*/
				
			}

			@Override
			public void onDialogNegativeClick(DialogFragment dialog) {
				dialog.dismiss();
			}

		});
		alertDlgFrg.show(getSupportFragmentManager(), "Dialog");

	}

	
}
