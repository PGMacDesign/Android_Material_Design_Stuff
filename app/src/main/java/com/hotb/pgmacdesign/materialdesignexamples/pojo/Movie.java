package com.hotb.pgmacdesign.materialdesignexamples.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.hotb.pgmacdesign.materialdesignexamples.misc.L;

/**
 * Created by pmacdowell on 5/5/2015.
 */
public class Movie implements Parcelable {

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

	/**
	 * Reads in the data from the parcel and loads it into the variables
	 * @param in
	 */
	public Movie(Parcel in) {
		//THESE ABSOLUTELY MUST BE IN ORDER!!!
		this.id = in.readLong();
		this.title = in.readString();
		//this.releaseDateTheater = new Date(in.readLong());
		this.releaseDateTheater = in.readString();
		//If a boolean, take the input as a separate int, if 1, make the boolean true, if 0, false.
		this.audienceScore = in.readInt();
		this.synopsis = in.readString();
		this.urlThumbnail = in.readString();
		this.urlSelf = in.readString();
		this.urlCast = in.readString();
		this.urlReviews = in.readString();
		this.urlSimilar = in.readString();


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

	@Override
	public int describeContents() {
		L.m("Describe contents movie");
		return 0;
	}

	/**
	 * Writes data to the parcel
	 * @param dest
	 * @param flags
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		L.m("Write to parcel movie");
		dest.writeLong(id);
		dest.writeString(title);
		//dest.writeLong(releaseDateTheater.getTime()); //Not using date atm
		dest.writeString(releaseDateTheater);
		dest.writeInt(audienceScore);
		dest.writeString(synopsis);
		dest.writeString(urlThumbnail);
		dest.writeString(urlSelf);
		dest.writeString(urlCast);
		dest.writeString(urlReviews);
		dest.writeString(urlSimilar);

		//NOTE! If you have to write a boolean, write an int, if true = 1, if false = 0;
	}


	public static final Parcelable.Creator<Movie> CREATOR =
		new Parcelable.Creator<Movie>(){

			/**
			 * Returns a Movie object by extracting the data from the parcel.
			 * @param in
			 * @return
			 */
			public Movie createFromParcel(Parcel in){
				L.m("create from parcel: movie");
				return new Movie(in);
			}

			/**
			 * This is used to check the size if you choose to check the array size of the parcel
			 * @param size
			 * @return
			 */
			public Movie[] newArray(int size){
				return new Movie[size];
			}
	};
}
