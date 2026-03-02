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
@Table(name = "hall")
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"showTimes", "seats"})
public class Hall {
	
	private Integer id;
	private String name;
	private Set<Seat> seats;
	private Set<ShowTime> showTimes;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "hall_name", nullable = false, length = 16)
	public String getName() {return name;}
	@OneToMany(mappedBy = "hall")
	public Set<Seat> getSeats() {return seats;}
	@OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
	public Set<ShowTime> getShowTimes() {return showTimes;}

}
