package ru.kuzin.CornCinema.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
	@NotEmpty
    @Size(max=32, message = "must be less than 32")
	private String name;
	private String lastName;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dateOfBirth;
	private List<Amplua> ampluas;
	private Set<Film> films;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "person_name", nullable = false, length = 32)
	public String getName() {return name;}
	@Column(name = "person_last_name", nullable = false, length = 32)
	public String getLastName() {return lastName;}
	@Column(name = "birth_date", nullable = false)
	public LocalDate getDateOfBirth() {return dateOfBirth;}
	@ManyToMany
	@JoinTable(name = "person_amplua",
			   joinColumns = @JoinColumn(name = "person_id"),
			   inverseJoinColumns = @JoinColumn(name = "amplua_id"))
	public List<Amplua> getAmpluas() {return ampluas;}
	@ManyToMany(mappedBy = "persons")
	public Set<Film> getFilms() {return films;}
	
}
