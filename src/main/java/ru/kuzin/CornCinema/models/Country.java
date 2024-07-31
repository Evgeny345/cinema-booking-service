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
import lombok.Setter;

@Entity
@Table(name = "country")
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Country {
	
	private Integer id;
	private String name;
	private Set<Film> films;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id", nullable = false)
	public Integer getId() {return id;}
	@Column(name = "country_name", nullable = false, length = 32)
	public String getName() {return name;}
	@ManyToMany(mappedBy = "countries")
	public Set<Film> getFilms() {return films;}

}
