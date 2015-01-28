package com.telstra.newsfeed.exception;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.telstra.newsfeed.constants.NewsFeedAppConstants;

/**
 * 
 * ClassName: NewsFeedUncaughtExceptionHandler.java
 * 
 * Description: This class to handle all unchecked exception.
 * 
 */
public class NewsFeedUncaughtExceptionHandler implements
		Thread.UncaughtExceptionHandler {
	
	private Context mBaseContext;
	
	/**
	 * constructor to initialize NewsFeedUncaughtExceptionHandler
	 * 
	 * @param context
	 *            : base context
	 */
	public NewsFeedUncaughtExceptionHandler(Context context) {
		mBaseContext = context;
		
	}

	/**
	 * default callback method for UncaughtExceptionHandler
	 * 
	 * @param thread
	 *            : main thread
	 * @param ex
	 *            : throwable object
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		
		ex.printStackTrace();
		

		Intent intent = new Intent(mBaseContext, ErrorScreenActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		/*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
		intent.putExtra(NewsFeedAppConstants.IS_EXCEPTION, true);
		PendingIntent pIntent = PendingIntent.getActivity(mBaseContext, 0,
				intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// for restarting the Activity
		AlarmManager mgr = (AlarmManager) mBaseContext
				.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, pIntent);
		System.exit(0);		
	}

}
