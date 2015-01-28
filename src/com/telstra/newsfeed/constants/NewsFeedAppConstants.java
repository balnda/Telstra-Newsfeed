package com.telstra.newsfeed.constants;

import org.json.JSONArray;

import android.os.Environment;

public class NewsFeedAppConstants {
	public static final String URL = "https://dl.dropboxusercontent.com/u/746330/facts.json";
	public static final String ROWS = "rows";
	public static final String TITLE = "title";
	public static final String DESC = "description";
	public static final String IMGURL = "imageHref";
	public JSONArray rows = null;
	public static final String TITLE_TOP = "title";
	public static final String NETWORK_ERROR = "Not able to connect to network. Please check your network and try again.";
	public static final String EXIT_CONFIRMATION = "Do you want to exit the application?";
	public static final String DATABASE_NAME = Environment.getExternalStorageDirectory()+"/TelstraNewsFeed/NewsFeedApp.db";
	//public static final String DATABASE_NAME = "NewsFeedApp.db";
	public static final String CONFIG_FOLDER_NAME_SDCARD = "/TelstraNewsFeed"; // Folder  name for SD Card

	public static final String CONFIG_FOLDER_NAME_PHONE_MEM = "TelstraNewsFeed";// Folder  name for Phone memory
	public static String IS_EXCEPTION = "isRunTimeException";
	public static String ERROR_MESSAGE = "ErrorMessage";
	public static String IS_108_EXCEPTION = "is108Error";


}
