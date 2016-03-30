package com.pramati.bean;

import java.util.StringTokenizer;

/**
 * @author lakshmanar
 *
 */
public class MessageData {
	
	private String author; // to store author details.
	private String url; // to store url value
	private String date;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = processDate(date);
	}
	
	@Override
	public String toString() {
		return "MessageData [author=" + author + ", url=" + url + ", date=" + date + "]";
	}
	
	private String processDate (String date) {
		
		if(null == date) {
			return null;
		}
		StringTokenizer tokenizer = new StringTokenizer(date, ",");
		String dateValue = "";
		while(tokenizer.hasMoreTokens()) {
			dateValue += tokenizer.nextToken();
		}
		return dateValue;
		
	}
	
}
