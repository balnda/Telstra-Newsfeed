package com.telstra.newsfeed.app;

import java.io.File;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.telstra.newsfeed.constants.NewsFeedAppConstants;
import com.telstra.newsfeed.exception.NewsFeedUncaughtExceptionHandler;
import com.telstra.newsfeed.util.LruBitmapCache;

/*
 * This class creates the volley requestQueue and imageLoader 
 */
public class NewsFeedAppController extends Application {
	public static final String TAG = NewsFeedAppController.class.getSimpleName();
	private RequestQueue requestQueue;
	private ImageLoader imageLoader;
	
	
	private static NewsFeedAppController instance;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		Thread.setDefaultUncaughtExceptionHandler(new NewsFeedUncaughtExceptionHandler(
				getBaseContext()));
		createFolder();
	}
	
	public static synchronized NewsFeedAppController getInstance() {
        return instance;
    }
 
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
        	requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
 
        return requestQueue;
    }
    
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
        	imageLoader = new ImageLoader(this.requestQueue,
                    new LruBitmapCache());
        }
        return this.imageLoader;
    }
    
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    
    
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
    private boolean createFolder()
    {
    	/*logger.infoLog(TAG+"createFolder()", "Entered" );*/
    	
    	File fstpFolder = null;		

    	if (isSDCardPresent()) 
    	{	
    		fstpFolder = new File(Environment.getExternalStorageDirectory()+ NewsFeedAppConstants.CONFIG_FOLDER_NAME_SDCARD);
    		if (fstpFolder != null){}
    			/*logger.infoLog(TAG+"createFolder()", "SD Memory Availble" +fstpFolder.getPath());*/
    	}
    	else 
    	{				
    		fstpFolder = this.getApplicationContext().getDir(NewsFeedAppConstants.CONFIG_FOLDER_NAME_PHONE_MEM,this.getApplicationContext().MODE_PRIVATE);
    		if (fstpFolder != null){}
    			/*logger.infoLog(TAG+"createFolder()", "Phone Memory Availble" +fstpFolder.getPath());*/
    	}
    	
    	if ((fstpFolder != null) && (!fstpFolder.exists())) {
    		fstpFolder.mkdir();
    		/*logger.infoLog(TAG+"createFolder()", "Phone Memory Availble" +fstpFolder.getPath());*/
    	}			

    	return true;		
    }	
    
    /*
	 * Method:isSDCardPresent
	 * Description:This method will check the SD card and return the value
	 * Parameters:none
	 * Return parameter:Boolean variable 
	 * 
	 */
	public boolean isSDCardPresent() {
		boolean isSDPresent = false;		
	/*	logger.infoLog(TAG+"isSDCardPresent()", "SD Card Availble");*/
		isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);		
		return isSDPresent;
	}
   

}
