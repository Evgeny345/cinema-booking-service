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
@Table(name = "country")
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Country {
	
	private Integer id;
	@NonNull
	private String name;
	private Set<Movie> movies;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "country_name", nullable = false, length = 32)
	public String getName() {return name;}
	@ManyToMany(mappedBy = "countries")
	public Set<Movie> getMovies() {return movies;}

}
