package ru.kuzin.CornCinema.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "profession", nullable = false, length = 32)
	public String getProfession() {return profession;}

}
