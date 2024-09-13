package ru.kuzin.CornCinema.models;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category_of_seat")
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "prices")
public class SeatCategory {
	
	private Integer id;
	private String name;
	private Set<Price> prices;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "category_of_seat_name", nullable = false, length = 32)
	public String getName() {return name;}
	@OneToMany(mappedBy = "seatCategory", cascade = CascadeType.ALL)
	public Set<Price> getPrices() {return prices;}

}
