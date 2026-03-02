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
@Table(name = "role")
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Role {
	
	private Integer id;
	private String name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	public Integer getId() {return id;}
	@Column(name = "role_name", nullable = false, unique = true)
	public String getName() {return name;}

}
