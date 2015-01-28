package com.telstra.newsfeed.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.telstra.newsfeed.NewsFeedActivity;
import com.telstra.newsfeed.constants.NewsFeedAppConstants;
import com.telstra.newsfeed.model.NewsItem;


public class JsonParser {

	static InputStream is = null;
	static String json = "";
	public JSONArray rows =null;
	private List<NewsItem> newsItems = null;
	private String jsonString = "";

	// constructor
	public JsonParser() {

	}

	/*
	 * method to download the JSON from the network location
	 */
	public String getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		return json;

	}
	
	/*
	 * Method to parse the json string and fill the NewsItem Pojo
	 */
	public List<NewsItem> getNewsItems(String jsonString)
	{
		if (jsonString != null) {
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(jsonString);
			//set the title field in the NewsFeedActivity, to be displayed on the title bar
			NewsFeedActivity.Title = (String)jsonObj.get(NewsFeedAppConstants.TITLE_TOP);
			rows = jsonObj.getJSONArray(NewsFeedAppConstants.ROWS);
			newsItems = new ArrayList<NewsItem>(rows.length());
			for (int i = 0; i < rows.length(); i++) {
				JSONObject news = rows.getJSONObject(i);
				String title = news.getString(NewsFeedAppConstants.TITLE);
				String desc = news.getString(NewsFeedAppConstants.DESC);
				String imgUrl = news.getString(NewsFeedAppConstants.IMGURL);
				NewsItem newsItem = new NewsItem();
				newsItem.setTitle(title);
				newsItem.setDescr(desc);
				newsItem.setImageURL(imgUrl);
				newsItems.add(newsItem);
			}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	return newsItems;
	}
}