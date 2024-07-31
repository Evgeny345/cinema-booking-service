package ru.kuzin.CornCinema.models;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "person")
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Person {
	
	private Integer id;
	private String name;
	private LocalDate dateOfBirth;
	private Set<Amplua> ampluas;
	private Set<Film> films;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id", nullable = false)
	public Integer getId() {return id;}
	@Column(name = "person_name", nullable = false, length = 32)
	public String getName() {return name;}
	@Column(name = "birth_date", nullable = false)
	public LocalDate getDateOfBirth() {return dateOfBirth;}
	@ManyToMany
	@JoinTable(name = "person_amplua",
			   joinColumns = @JoinColumn(name = "person_id"),
			   inverseJoinColumns = @JoinColumn(name = "amplua_id"))
	public Set<Amplua> getAmpluas() {return ampluas;}
	@ManyToMany(mappedBy = "persons")
	public Set<Film> getFilms() {return films;}

}
