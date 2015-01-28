package com.telstra.newsfeed.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import android.util.Log;

import com.telstra.newsfeed.constants.NewsFeedAppConstants;
import com.telstra.newsfeed.model.NewsItem;

public class NewsFeedUtil {
	
	public static List<NewsItem> newsItems = null;
	public static List<NewsItem> getJSONData() {
		JsonParser jsonParser = new JsonParser();
        String json = jsonParser
                .getJSONFromUrl(NewsFeedAppConstants.URL);

        Log.e("Response: ", "> " + json);
        

		 return newsItems = jsonParser.getNewsItems(json);
	}
	
	public static String getInt64HashCode(String strText)
	{
		String hashCode = "";
		StringBuffer sb = new StringBuffer();
		
		if (!strText.isEmpty())
	    {
			byte[] byteContents = strText.getBytes();
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(byteContents);
				 byte byteData[] = md.digest();
				 
			        //convert the byte to hex format method 1
			        
			        for (int i = 0; i < byteData.length; i++) {
			         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			        }
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sb.toString();
	    }
		
		return hashCode;
		
	}

}
