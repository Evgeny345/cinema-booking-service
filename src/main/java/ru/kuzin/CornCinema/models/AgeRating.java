package ru.kuzin.CornCinema.models;

public enum AgeRating {
	
ZERO("0+"), SIX("6+"), TWELVE("12+"), SIXTEEN("16+"), EIGHTEEN("18+");
	
	private String name;
	private AgeRating(String name) {this.name = name;}
	public String getName() {return name;}

}
