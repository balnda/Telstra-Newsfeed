package com.telstra.newsfeed.model;


public class NewsItem {
	private String title = "";
	private String descr = "";
	private String imageURL = "";
	private String cachedTime ;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getCachedTime() {
		return cachedTime;
	}

	public void setCachedTime(String cachedTime) {
		this.cachedTime = cachedTime;
	}

	
}
