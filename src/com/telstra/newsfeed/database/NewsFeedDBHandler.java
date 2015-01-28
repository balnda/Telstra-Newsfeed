/**
 * 
 */
package com.telstra.newsfeed.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.telstra.newsfeed.constants.NewsFeedAppConstants;
import com.telstra.newsfeed.model.NewsItem;
import com.telstra.newsfeed.util.NewsFeedUtil;

/**
 * @author Balajee_Raghbendra
 *
 */
public class NewsFeedDBHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = NewsFeedAppConstants.DATABASE_NAME;
	public static final String TABLE_NEWSFEED = "newsfeed";
	
	public static final String COLUMN_NEWS_ID = "_id";
	public static final String COLUMN_TITLE = "titel";
	public static final String COLUMN_DESCR = "descr";
	public static final String COLUMN_IMAGE_URL = "imageurl";
	public static final String COLUMN_CACHE_TIME = "cachetime";


	public NewsFeedDBHandler(Context context, String name,
			CursorFactory factory, int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String CREATE_NEWSFEED_TABLE = "CREATE TABLE " +
				TABLE_NEWSFEED + "("
	             + COLUMN_NEWS_ID + " TEXT PRIMARY KEY," + COLUMN_TITLE 
	             + " TEXT," + COLUMN_DESCR + " TEXT," + COLUMN_IMAGE_URL + " TEXT," +  COLUMN_CACHE_TIME + " TEXT)";
	      db.execSQL(CREATE_NEWSFEED_TABLE);


	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWSFEED);
	      onCreate(db);

	}
	
	/*
	 * add the newsItems into the DB.
	 */
	public void addNewsFeed(List<NewsItem> newsItems) {
		 SQLiteDatabase db = this.getWritableDatabase();
		 
		 String path = db.getPath();
		for(NewsItem newsItem : newsItems){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NEWS_ID, NewsFeedUtil.getInt64HashCode(newsItem.getImageURL()));
        values.put(COLUMN_TITLE, newsItem.getTitle());
        values.put(COLUMN_DESCR, newsItem.getDescr());
        values.put(COLUMN_IMAGE_URL, newsItem.getImageURL());
        
        Date dt = new Date();
        long timeMilliseconds = dt.getTime();      //get the current time
        
        
        values.put(COLUMN_CACHE_TIME, timeMilliseconds);
       
        
        db.insert(TABLE_NEWSFEED, null, values);
		}
        db.close();
}

	public List<NewsItem> fetchAllNewsFeeds(){
		String query = "Select * FROM " + TABLE_NEWSFEED;
		List<NewsItem> newsItems = new ArrayList<NewsItem>();
		SQLiteDatabase db = this.getWritableDatabase();
		
		
		Cursor cursor = db.rawQuery(query, null);
		
			while (cursor.moveToNext()) {			
				NewsItem newsItem = new NewsItem();
				newsItem.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
				newsItem.setDescr(cursor.getString(cursor.getColumnIndex(COLUMN_DESCR)));
				newsItem.setImageURL(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)));
				newsItem.setCachedTime(cursor.getString(cursor.getColumnIndex(COLUMN_CACHE_TIME)));
				newsItems.add(newsItem);
				
			} 
			cursor.close();
	        db.close();
	        return newsItems;

		
	}
	
	public boolean deleteNewsFeeds() {
		
		boolean result = true;		

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.delete(TABLE_NEWSFEED, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = false;
			e.printStackTrace();
		}		
		
		
	    db.close();
		return result;
	}




}
