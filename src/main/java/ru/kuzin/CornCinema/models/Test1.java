package ru.kuzin.CornCinema.models;

import java.time.LocalTime;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Test1 {
	
	private String title;
	@EqualsAndHashCode.Include
	private String description;
	private LocalTime duration;
	private AgeRating ageRating;
	private Boolean inRolling;
	private Set<Country> countries;
	private Set<Genre> genres;
	private Set<Person> persons;

}
