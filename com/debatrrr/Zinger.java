package com.debatrrr;

public class Zinger {
	private int id;
	private int wtfId;
	private String text;
	private String user;
	private String datePosted;
	private String whoSaidIt;
	private String source;
	private int likesNumber;
	private int sharesNumber;
	private int flaggedCount;
	
	public Zinger(String text){
		this.text = text;
		
	}

	public Zinger(String text, String user, String datePosted, String whoSaidIt) {
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getLikesNumber() {
		return likesNumber;
	}

	public void setLikesNumber(int likesNumber) {
		this.likesNumber = likesNumber;
	}

	public int getSharesNumber() {
		return sharesNumber;
	}

	public void setSharesNumber(int sharesNumber) {
		this.sharesNumber = sharesNumber;
	}

	public int getFlaggedCount() {
		return flaggedCount;
	}

	public void setFlaggedCount(int flaggedCount) {
		this.flaggedCount = flaggedCount;
	}

	
}
