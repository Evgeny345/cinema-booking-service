package ru.kuzin.CornCinema.models;

import java.util.Random;

public enum AgeRating {
	
ZERO("0+"), SIX("6+"), TWELVE("12+"), SIXTEEN("16+"), EIGHTEEN("18+");
	
	private String name;
	private static final Random RND = new Random();
	
	private AgeRating(String name) {
		this.name = name;
	}
	
	public String getName() {return name;}
	
	public static AgeRating randomAgeRating() {
		AgeRating[] ageRatings = values();
		return ageRatings[RND.nextInt(ageRatings.length)];
	}

}
