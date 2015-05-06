package com.hotb.pgmacdesign.materialdesignexamples.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by pmacdowell on 5/6/2015.
 */
public class MovieSorter {

	//Sort alphabetically by date
	public static void sortMoviesByName(ArrayList<Movie> movies){
		//@params, 1st is object to sort, second is comparator
		Collections.sort(movies, new Comparator<Movie>() {
			/**
			 * Comparator for sorting movies
			 * @param lhs First item to compare to (with the passed collection)
			 * @param rhs Second item to compare to (with the passed collection)
			 * @return Returns an int.
			 * If negative integer, means that first argument is less than the second
			 * If zero, means that the first argument is equal to the second
			 * If positive integer, means that first argument is greater than the second
			 */
			@Override
			public int compare(Movie lhs, Movie rhs) {
				//Returns an int
				return lhs.getTitle().compareTo(rhs.getTitle());
			}
		});
	}

	//Sort chronologically by date
	public static void sortMoviesByDate(ArrayList<Movie> movies){
		//@params, 1st is object to sort, second is comparator
		Collections.sort(movies, new Comparator<Movie>() {
			/**
			 * Comparator for sorting movies
			 * @param lhs First item to compare to (with the passed collection)
			 * @param rhs Second item to compare to (with the passed collection)
			 * @return Returns an int.
			 * If negative integer, means that first argument is less than the second
			 * If zero, means that the first argument is equal to the second
			 * If positive integer, means that first argument is greater than the second
			 */
			@Override
			public int compare(Movie lhs, Movie rhs) {
				//Returns an int
				return lhs.getReleaseDateTheater().compareTo(rhs.getReleaseDateTheater());
			}
		});
	}

	//Sort numerically by rating
	public static void sortMoviesByRating(ArrayList<Movie> movies){
		//@params, 1st is object to sort, second is comparator
		Collections.sort(movies, new Comparator<Movie>() {
			/**
			 * Comparator for sorting movies
			 * @param lhs First item to compare to (with the passed collection)
			 * @param rhs Second item to compare to (with the passed collection)
			 * @return Returns an int.
			 * If negative integer, means that first argument is less than the second
			 * If zero, means that the first argument is equal to the second
			 * If positive integer, means that first argument is greater than the second
			 */
			@Override
			public int compare(Movie lhs, Movie rhs) {
				//Returns an int
				int ratingLhs = lhs.getAudienceScore();
				int ratingRhs = rhs.getAudienceScore();

				if(ratingLhs < ratingRhs){
					return 1; //Second one is greater. Change to -1 if you want to make the list ascending instead of descending
				} else if (ratingLhs > ratingRhs ){
					return -1; //First one is greater. Change to -1 if you want to make the list ascending instead of descending
				} else {
					return 0; //they are ==
				}
			}
		});
	}
}
