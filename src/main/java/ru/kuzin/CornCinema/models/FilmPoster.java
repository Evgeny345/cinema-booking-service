package ru.kuzin.CornCinema.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "film_poster")
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FilmPoster {
	
	@EqualsAndHashCode.Include
	private Integer id;
	@EqualsAndHashCode.Include
	private String title;
	private Film film;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "title", nullable = false, length = 256)
	public String getTitle() {return title;}
	@ManyToOne(fetch = FetchType.LAZY)
	public Film getFilm() {return film;}

}
