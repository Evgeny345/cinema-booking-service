package ru.kuzin.CornCinema.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie_amplua_person")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode
public class PersonWithAmpluaForMovie {
	
	private PersonWithAmpluaForMoviePk pk;
	private Movie movie;
	private Person person;
	private Amplua amplua;
	
	public PersonWithAmpluaForMovie(Movie movie, Person person, Amplua amplua) {
		this.pk = new PersonWithAmpluaForMoviePk(movie.getId(), person.getId(), amplua.getId());
		this.movie = movie;
		this.person = person;
		this.amplua = amplua;
	}

	@EmbeddedId
	public PersonWithAmpluaForMoviePk getPk() {return pk;}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
	@MapsId("movieId")
	public Movie getMovie() {return movie;}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	@MapsId("personId")
	public Person getPerson() {return person;}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "amplua_id", referencedColumnName = "id")
	@MapsId("ampluaId")
	public Amplua getAmplua() {return amplua;}

}
