package moviepackage;

import java.io.Serializable;

/**
 * 
 * This class implements the Serializable interface
 * It contains a custom constructor, accessor methods, and mutator methods 
 * 
 */
public class Movie implements Serializable {
	
	//Declare variables
	private int year;
	private String title;
	private int duration;
	private String genre;
	private String rating;
	private double score;
	private String director;
	private String actor1;
	private String actor2;
	private String actor3;
	
	/**
	 * 
	 * This custom constructor takes in 10 parameters and creates a Movie object
	 * 
	 * @param year A movie's release year
	 * @param title A movie's title
	 * @param duration A movie's duration, in minutes
	 * @param genre A movie's genre
	 * @param rating A movie's rating
	 * @param score A movie's score
	 * @param director The movie's director
	 * @param actor1 The first actor of a movie
	 * @param actor2 The second actor of a movie
	 * @param actor3 The third actor of a movie
	 */
	public Movie(int year, String title, int duration, String genre, String rating, double score, String director, String actor1, String actor2, String actor3) {
		this.year = year;
		this.title = title;
		this.duration = duration;
		this.genre = genre;
		this.rating = rating;
		this.score = score;
		this.director = director;
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.actor3 = actor3;
	}
	
	
	//Accessor methods
	public int getYear() {
		return year;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public double getScore() {
		return score;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public String getRating() {
		return rating;
	}
	
	public String getDirector() {
		return director;
	}
	
	public String getActor1() {
		return actor1;
	}
	
	public String getActor2() {
		return actor2;
	}
	
	public String getActor3() {
		return actor3;
	}
	
	//Mutator methods
	public void setYear(int year) {
		this.year = year;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public void setActor1(String actor1) {
		this.actor1 = actor1;
	}
	
	public void setActor2(String actor2) {
		this.actor2 = actor2;
	}
	
	public void setActor3(String actor3) {
		this.actor3 = actor3;
	}
	
	
	/**
	 * 
	 * A method that returns all of a Movie object's attributes as a String.
	 * 
	 */
	@Override
	public String toString() {
		return this.year + "," + this.title + "," + this.duration + "," + this.genre + ","
				+ this.rating + "," + this.score + "," + this.director + "," + this.actor1
				+ "," + this.actor2 + "," + this.actor3;
	}
	
	/**
	 * 
	 * A method that checks whether 2 Movie objects are equal.
	 * 
	 * @param obj A Movie object
	 * @return boolean
	 */
	public boolean equals(Movie obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		if (this.year == obj.year && this.title == obj.title && this.duration == obj.duration && this.genre == obj.genre && this.rating == obj.rating &&
			this.score == obj.score && this.director == obj.director && this.actor1 == obj.actor1 && this.actor2 == obj.actor2 && this.actor3 == obj.actor3)
			return true;
		else
			return false;
		
	}
	
}
