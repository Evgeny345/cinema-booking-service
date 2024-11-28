package ru.kuzin.CornCinema.models;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "film")
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {
	
	@EqualsAndHashCode.Include
	private Integer id;
	private String title;
	private String description;
	private LocalTime duration;
	private AgeRating ageRating;
	private Boolean inRolling;
	private Set<Genre> genres;
	private Set<Country> countries;
	private Set<PersonWithAmpluaForFilm> persons = new HashSet<>();
	private Set<ShowTime> showTimes;
	private Set<FilmPoster> posters = new HashSet<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "title", nullable = false, length = 64)
	public String getTitle() {return title;}
	@Column(name = "description", length = 512)
	public String getDescription() {return description;}
	@Column(name = "duration", nullable = false)
	public LocalTime getDuration() {return duration;}
	@Column(name = "age_rating", nullable = false)
	public AgeRating getAgeRating() {return ageRating;}
	@Column(name = "in_rolling")
	public Boolean getInRolling() {return inRolling;}
	@ManyToMany
	@JoinTable(name = "film_genre", 
			   joinColumns = @JoinColumn(name = "film_id"),
			   inverseJoinColumns = @JoinColumn(name = "genre_id"))
	public Set<Genre> getGenres() {return genres;}
	@ManyToMany
	@JoinTable(name = "film_country", 
			   joinColumns = @JoinColumn(name = "film_id"),
			   inverseJoinColumns = @JoinColumn(name = "country_id"))
	public Set<Country> getCountries() {return countries;}
	@OneToMany(mappedBy = "film", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	public Set<PersonWithAmpluaForFilm> getPersons() {return persons;}
	@OneToMany(mappedBy = "film")
	public Set<ShowTime> getShowTimes() {return showTimes;}
	@OneToMany(mappedBy = "film", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	public Set<FilmPoster> getPosters() {return posters;}
	
	public void addPoster(FilmPoster poster) {
		posters.add(poster);
		poster.setFilm(this);
	}
	
	public void addPerson(Person person, Amplua amplua) {
		PersonWithAmpluaForFilm personForFilm = new PersonWithAmpluaForFilm(this, person, amplua);
		persons.add(personForFilm);
		personForFilm.setFilm(this);
	}

}
