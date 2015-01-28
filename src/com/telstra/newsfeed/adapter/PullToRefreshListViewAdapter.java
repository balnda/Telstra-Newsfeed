package com.telstra.newsfeed.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.telstra.newsfeed.R;
import com.telstra.newsfeed.app.NewsFeedAppController;
import com.telstra.newsfeed.model.NewsItem;
import com.telstra.newsfeed.util.NewsFeedUtil;

/**
 * The adapter used to display the results in the list
 * 
 */
public abstract class PullToRefreshListViewAdapter extends android.widget.BaseAdapter {

	
	private Activity activity;
    private LayoutInflater inflater;
    private List<NewsItem> newsItems;
    ImageLoader imageLoader = NewsFeedAppController.getInstance().getImageLoader();
    
    /*public PullToRefreshListViewAdapter(Activity activity, List<NewsItemPojo> newsItems) {
        this.activity = activity;
        this.newsItems = newsItems;
    }*/
	
	public class ViewHolder {
		public TextView txtHeader;
		public TextView txtSub;
		
	}

	/**
	 * Loads the data. 
	 */
	public void loadData() {
		
		newsItems = NewsFeedUtil.getJSONData();
		
		// MANDATORY: Notify that the data has changed
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return newsItems.size();
	}

	@Override
	public Object getItem(int position) {
		return newsItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*View rowView = convertView;

		String record = (String) getItem(position);

		if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ViewHolder viewHolder = new ViewHolder();

		if (convertView == null){
			rowView = inflater.inflate(R.layout.news_feed_list_item,null);

			viewHolder.name = (TextView) rowView.findViewById(R.id.textView1);

			rowView.setTag(viewHolder);
		}

		final ViewHolder holder = (ViewHolder) rowView.getTag();

		holder.name.setText(record); 

		return rowView;*/
		
		if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.news_feed_list_item, null);
        
        if (imageLoader == null)
            imageLoader = NewsFeedAppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.newsItemImage);
        
        NewsItem newsItem = newsItems.get(position);
        thumbNail.setImageUrl(newsItem.getImageURL(), imageLoader);
        
        TextView txtHeader = (TextView) convertView.findViewById(R.id.txtHeader);
        TextView txtSub = (TextView) convertView.findViewById(R.id.txtdesc);
        
        txtHeader.setText(newsItem.getTitle());
        txtSub.setText(newsItem.getDescr());
        
        return convertView;
	}
}

