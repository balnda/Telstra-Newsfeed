package com.telstra.newsfeed;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.telstra.newsfeed.adapter.NewsFeedListAdapter;
import com.telstra.newsfeed.constants.NewsFeedAppConstants;
import com.telstra.newsfeed.database.NewsFeedDBHandler;
import com.telstra.newsfeed.model.NewsItem;
import com.telstra.newsfeed.util.AlertDialogFragment;
import com.telstra.newsfeed.util.AlertDialogFragment.AlertDialogListener;
import com.telstra.newsfeed.util.NetworkUtil;
import com.telstra.newsfeed.util.NewsFeedUtil;


public class NewsFeedActivity extends FragmentActivity {
	
	 private static final String TAG = NewsFeedActivity.class.getSimpleName();	
	 public static String Title = "";
	 NewsFeedDBHandler dbHandler;  
	    
	 private ProgressDialog pDialog;
	 public static List<NewsItem> newsFeeds = new ArrayList<NewsItem>();
	  

	 private NewsFeedListAdapter adapter;
	    
	 private PullToRefreshListView listView;		

	    //Handler to control listview on UI Thread
	    Handler handler = new Handler() {
			  @Override
			  public void handleMessage(Message msg) {
				  listView.onRefreshComplete();
				  
			     }
			 };

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        
        dbHandler = new NewsFeedDBHandler(this.getApplicationContext(),NewsFeedAppConstants.DATABASE_NAME,null,1);
        newsFeeds = dbHandler.fetchAllNewsFeeds();
        listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);      
              
		adapter = new NewsFeedListAdapter(this,newsFeeds);
		listView.setAdapter(adapter);
		
		
		listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				NetworkUtil.set_context(getApplicationContext());
				if(NetworkUtil.isConnectingToInternet()){
					
					Thread thread = new Thread(new Runnable(){
					    @Override
					    public void run() {
					        try {
					            //On Pull to Refresh, make a network connection and fetch fresh json data
					        	
					        	if(dbHandler.deleteNewsFeeds())
					        	{
					        		dbHandler.addNewsFeed(NewsFeedUtil.getJSONData());
					        		newsFeeds = dbHandler.fetchAllNewsFeeds();
					        	}
								
					        } catch (Exception e) {
					            e.printStackTrace();
					        }
					        //Notify listview on the UI Thread of completion
					        handler.sendEmptyMessage(0);    
					    }
					});

					thread.start(); 
					
					
				}else{
					//In case of network error, show dialog.
					showError(NewsFeedAppConstants.NETWORK_ERROR,0,1);
				}
			}
		});

		        
        getActionBar().setTitle(Title);       
        
        hidePDialog();
    }

	

 
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_feed, menu);
        return true;
    }
    
   
    
    /*
	 * This function is handling back button click on Android device. On back button system will show Exit application
	 * conformation message with Yes and No option. On click of Yes system will exit and on click of No system will remain 
	 * running in the same state
	 */
    
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        	/*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(NewsFeedAppConstants.EXIT_CONFIRMATION);
            alertDialogBuilder.setPositiveButton("Yes", 
            new DialogInterface.OnClickListener() {
      		
               @Override
               public void onClick(DialogInterface arg0, int arg1) {
                 //Exit App
            	   System.exit(1);
      			
               }
            });
            alertDialogBuilder.setNegativeButton("No", 
            new DialogInterface.OnClickListener() {
      			
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  //Hide dialog
      		 }
            });
      	    
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();*/
        	showError(NewsFeedAppConstants.EXIT_CONFIRMATION, 1, 0);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
	
	/*public void showError(int errorNo)
	{
		// TODO Auto-generated method stub
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(NewsFeedAppConstants.NETWORK_ERROR);
        alertDialogBuilder.setPositiveButton("OK", 
        new DialogInterface.OnClickListener() {
  		
           @Override
           public void onClick(DialogInterface arg0, int arg1) {
            
        	   listView.onRefreshComplete();
           }
        });
       
  	    
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

	}*/
	
	public void showError(String alertMsgString,int errorNo, int errorType)
	{
		// TODO Auto-generated method stub
		AlertDialogFragment alertDlgFrg;
		final String msgString = alertMsgString;
		final int errorTypeF = errorType; 
		View inflater = View.inflate(this, R.layout.alert, null);
		TextView textView = (TextView) inflater
				.findViewById(R.id.ExceptionMessage);
		//textView.setTypeface(RobotoTypeface.sRobotoRegular(this));
		textView.setText(msgString);
		textView.setTextColor(Color.parseColor("#ffffff"));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		if(errorType == 1)
		{
		alertDlgFrg = AlertDialogFragment.newInstance(
				"Network Error", inflater, 0, "Ok", null);
		}
		
		else
		{
			alertDlgFrg = AlertDialogFragment.newInstance(
					"Information", inflater, 0, "Yes","No");
		}
		alertDlgFrg.onClickAlertDialog(new AlertDialogListener() {

			@Override
			public void onDialogPositiveClick(DialogFragment dialog) {
				
				dialog.dismiss();
				if(errorTypeF == 1)
				{
					 listView.onRefreshComplete();
				
				}
				else
				{
					System.exit(1);
				}
				
			}

			@Override
			public void onDialogNegativeClick(DialogFragment dialog) {
				dialog.dismiss();
			}

		});
		alertDlgFrg.show(getSupportFragmentManager(), "Dialog");

	}
	
	

	
}



