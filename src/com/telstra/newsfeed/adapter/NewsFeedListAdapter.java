package com.telstra.newsfeed.adapter;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.telstra.newsfeed.R;
import com.telstra.newsfeed.app.NewsFeedAppController;
import com.telstra.newsfeed.model.NewsItem;

public class NewsFeedListAdapter extends BaseAdapter {
	
	private Activity activity;
    private LayoutInflater inflater;
    private List<NewsItem> newsItems;
    ImageLoader imageLoader = NewsFeedAppController.getInstance().getImageLoader();

    public NewsFeedListAdapter(Activity activity, List<NewsItem> newsItems) {
        this.activity = activity;
        this.newsItems = newsItems;
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 return newsItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newsItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.news_feed_list_item, null);
        
        //Volley imageLoader to download images asynchronously
        if (imageLoader == null)
            imageLoader = NewsFeedAppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.newsItemImage);
        
        NewsItem newsItem = newsItems.get(position);
        thumbNail.setImageUrl(newsItem.getImageURL(), imageLoader);
        //thumbNail.setImageUrl("http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg", imageLoader);
        
        TextView txtHeader = (TextView) convertView.findViewById(R.id.txtHeader);
        TextView txtSub = (TextView) convertView.findViewById(R.id.txtdesc);
        TextView txtCachedTime = (TextView) convertView.findViewById(R.id.txtCacheTime);
        
        txtHeader.setText(newsItem.getTitle());
        if(null != newsItem.getDescr() || !newsItem.getDescr().isEmpty() || !newsItem.getDescr().equalsIgnoreCase("null"))
        {
        txtSub.setText(newsItem.getDescr());
        }
        else
        {
        	txtSub.setText("No Description");
        }
        
        Date date = new Date();
        long timeMilliseconds = Long.parseLong(newsItem.getCachedTime());
        
        //To convert back to Date
        Date cachedDateTime = new Date(timeMilliseconds);
        txtCachedTime.setText(cachedDateTime.toLocaleString().toString());
        
        return convertView;
	}
	
	

}
