package com.hotb.pgmacdesign.materialdesignexamples.pojo;

/**
 * Created by pmacdowell on 5/5/2015.
 */
public class Movie {

	private long id;
	private String title;
	//private Date releaseDateTheater;
	private String releaseDateTheater;
	private int audienceScore;
	private String synopsis;
	private String urlThumbnail;
	private String urlSelf;
	private String urlCast;
	private String urlReviews;
	private String urlSimilar;

	public Movie(long id, String title, String releaseDateTheater, int audienceScore,
	             String synopsis, String urlThumbnail, String urlSelf, String urlCast,
	             String urlReviews, String urlSimilar) {
		this.id = id;
		this.title = title;
		this.releaseDateTheater = releaseDateTheater;
		this.audienceScore = audienceScore;
		this.synopsis = synopsis;
		this.urlThumbnail = urlThumbnail;
		this.urlSelf = urlSelf;
		this.urlCast = urlCast;
		this.urlReviews = urlReviews;
		this.urlSimilar = urlSimilar;
	}

	public Movie(){

	}

	@Override
	public String toString(){
		return "ID:" + id
				+ "Title " + title
				+ "Date " + releaseDateTheater
				+ "Synopsis" + synopsis
				+ "Score" + audienceScore
				+ "urlThumbnail" + urlThumbnail;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setReleaseDateTheater(String releaseDateTheater) {
		this.releaseDateTheater = releaseDateTheater;
	}

	public void setAudienceScore(int audienceScore) {
		this.audienceScore = audienceScore;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public void setUrlThumbnail(String urlThumbnail) {
		this.urlThumbnail = urlThumbnail;
	}

	public void setUrlSelf(String urlSelf) {
		this.urlSelf = urlSelf;
	}

	public void setUrlCast(String urlCast) {
		this.urlCast = urlCast;
	}

	public void setUrlReviews(String urlReviews) {
		this.urlReviews = urlReviews;
	}

	public void setUrlSimilar(String urlSimilar) {
		this.urlSimilar = urlSimilar;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	//public Date getReleaseDateTheater() {
		//return releaseDateTheater;
	//}

	public String getReleaseDateTheater() {
		return releaseDateTheater;
	}

	public int getAudienceScore() {
		return audienceScore;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getUrlThumbnail() {
		return urlThumbnail;
	}

	public String getUrlSelf() {
		return urlSelf;
	}

	public String getUrlCast() {
		return urlCast;
	}

	public String getUrlReviews() {
		return urlReviews;
	}

	public String getUrlSimilar() {
		return urlSimilar;
	}
}
