package ru.kuzin.CornCinema.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seat")
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Seat {
	
	private Integer id;
	private Integer rowNumber;
	private Integer seatNumber;
	private SeatCategory category;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "row_number", nullable = false)
	public Integer getRowNumber() {return rowNumber;}
	@Column(name = "seat_number", nullable = false)
	public Integer getSeatNumber() {return seatNumber;}
	@ManyToOne
	@JoinColumn(name = "category_of_seat_id")
	public SeatCategory getCategory() {return category;}

}
