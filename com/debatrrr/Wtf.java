package com.debatrrr;

public class Wtf {
	private int id;
	private String text;
	private String user;
	private String datePosted;
	private String whoSaidIt;
	
	public Wtf(String text){
		this.text = text;
		
	}

	public Wtf(String text, String user, String datePosted, String whoSaidIt) {
		this.text = text;
		this.user = user;
		this.datePosted = datePosted;
		this.whoSaidIt = whoSaidIt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}

	public String getWhoSaidIt() {
		return whoSaidIt;
	}

	public void setWhoSaidIt(String whoSaidIt) {
		this.whoSaidIt = whoSaidIt;
	}

	
}
