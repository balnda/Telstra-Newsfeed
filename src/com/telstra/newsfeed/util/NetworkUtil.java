package com.telstra.newsfeed.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	/*
	 * This class provide helper functions for network tasks and also helps in getting the state of the network.
	 */
	private static Context _context;
    
    public static void set_context(Context _context) {
		NetworkUtil._context = _context;
	}

	public NetworkUtil(Context context){
        this._context = context;
    }
	
	/*
	 * Utility method to check if we can connect to internet 
	 */

	public static boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null) 
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }


}
