package ru.kuzin.CornCinema.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "genre")
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Genre {
	
	private Integer id;
	@NonNull
	private String title;
	private Set<Movie> movies;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "title", nullable = false, length = 32)
	public String getTitle() {return title;}
	@ManyToMany(mappedBy = "genres")
	public Set<Movie> getMovies() {return movies;}
	
}
