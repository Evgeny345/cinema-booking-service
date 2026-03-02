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
@Table(name = "movie")
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Movie {
	
	@EqualsAndHashCode.Include
	private Integer id;
	private String title;
	private String description;
	private LocalTime duration;
	private String poster;
	private AgeRating ageRating;
	private Boolean inPlayingNow;
	private Set<Genre> genres;
	private Set<Country> countries;
	private Set<PersonWithAmpluaForMovie> persons = new HashSet<>();
	private Set<ShowTime> showTimes = new HashSet<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "title", nullable = false, length = 64)
	public String getTitle() {return title;}
	@Column(name = "description", length = 512)
	public String getDescription() {return description;}
	@Column(name = "duration", nullable = false)
	public LocalTime getDuration() {return duration;}
	@Column(name = "poster", length = 256)
	public String getPoster() {return poster;}
	@Column(name = "age_rating", nullable = false)
	public AgeRating getAgeRating() {return ageRating;}
	@Column(name = "in_playing")
	public Boolean getInPlayingNow() {return inPlayingNow;}
	@ManyToMany
	@JoinTable(name = "movie_genre", 
			   joinColumns = @JoinColumn(name = "movie_id"),
			   inverseJoinColumns = @JoinColumn(name = "genre_id"))
	public Set<Genre> getGenres() {return genres;}
	@ManyToMany
	@JoinTable(name = "movie_country", 
			   joinColumns = @JoinColumn(name = "movie_id"),
			   inverseJoinColumns = @JoinColumn(name = "country_id"))
	public Set<Country> getCountries() {return countries;}
	@OneToMany(mappedBy = "movie", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	public Set<PersonWithAmpluaForMovie> getPersons() {return persons;}
	@OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
	public Set<ShowTime> getShowTimes() {return showTimes;}
	
	public void addPerson(Person person, Amplua amplua) {
		PersonWithAmpluaForMovie personForMovie = new PersonWithAmpluaForMovie(this, person, amplua);
		persons.add(personForMovie);
		personForMovie.setMovie(this);
	}
	
	public boolean personIsExists(Integer personId, Integer ampluaId) {
		PersonWithAmpluaForMoviePk pk = new PersonWithAmpluaForMoviePk(this.getId(), personId, ampluaId);
		return persons.stream().anyMatch(p -> p.getPk().equals(pk));
	}
	
}
