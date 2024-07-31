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
@Table(name = "amplua")
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Amplua {
	
	private Integer id;
	private String profession;
	private Set<Person> persons;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "amplua_id", nullable = false)
	public Integer getId() {return id;}
	@Column(name = "profession", nullable = false, length = 32)
	public String getProfession() {return profession;}
	@ManyToMany(mappedBy = "ampluas")
	public Set<Person> getPersons() {return persons;}

}
