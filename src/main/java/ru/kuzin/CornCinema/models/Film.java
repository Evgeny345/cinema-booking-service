package ru.kuzin.CornCinema.models;

import java.time.LocalTime;
import java.util.Set;

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
	private String longDescription;
	private LocalTime duration;
	private String posterUrl;
	private AgeRating ageRating;
	private Set<Genre> genres;
	private Set<Country> countries;
	private Set<Person> persons;
	private Set<ShowTime> showTimes;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "title", nullable = false, length = 64)
	public String getTitle() {return title;}
	@Column(name = "description", length = 512)
	public String getDescription() {return description;}
	@Column(name = "long_description", length = 2048)
	public String getLongDescription() {return longDescription;}
	@Column(name = "duration", nullable = false)
	public LocalTime getDuration() {return duration;}
	@Column(name = "poster_url", length = 256)
	public String getPosterUrl() {return posterUrl;}
	@Column(name = "age_rating", nullable = false)
	public AgeRating getAgeRating() {return ageRating;}
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
	@ManyToMany
	@JoinTable(name = "film_person", 
			   joinColumns = @JoinColumn(name = "film_id"),
			   inverseJoinColumns = @JoinColumn(name = "person_id"))
	public Set<Person> getPersons() {return persons;}
	@OneToMany(mappedBy = "film")
	public Set<ShowTime> getShowTimes() {return showTimes;}

}
