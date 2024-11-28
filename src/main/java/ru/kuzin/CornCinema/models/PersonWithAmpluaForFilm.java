package ru.kuzin.CornCinema.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "film_amplua_person")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode
public class PersonWithAmpluaForFilm {
	
	private PersonWithAmpluaForFilmPk pk;
	private Film film;
	private Person person;
	private Amplua amplua;
	
	public PersonWithAmpluaForFilm(Film film, Person person, Amplua amplua) {
		this.pk = new PersonWithAmpluaForFilmPk(film.getId(), person.getId(), amplua.getId());
		this.film = film;
		this.person = person;
		this.amplua = amplua;
	}

	@EmbeddedId
	public PersonWithAmpluaForFilmPk getPk() {return pk;}
	@ManyToOne
	@JoinColumn(name = "film_id", referencedColumnName = "id")
	@MapsId("filmId")
	public Film getFilm() {return film;}
	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	@MapsId("personId")
	public Person getPerson() {return person;}
	@ManyToOne
	@JoinColumn(name = "amplua_id", referencedColumnName = "id")
	@MapsId("ampluaId")
	public Amplua getAmplua() {return amplua;}

}
