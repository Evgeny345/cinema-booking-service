package ru.kuzin.CornCinema.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket")
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Ticket {
	
	private Long id;
	private Boolean sold;
	private ShowTime showTime;
	private LocalDateTime resirvationDate;
	private Seat seat;
	private User user;
	
	public Ticket(Boolean sold, ShowTime showTime, Seat seat) {
		this.sold = sold;
		this.showTime = showTime;
		this.seat = seat;
	}

	@Id
	@SequenceGenerator(name = "ticket_sequence", sequenceName = "ticket_seq", initialValue = 1, allocationSize = 5)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_sequence")
	public Long getId() {return id;}
	@Column(name = "is_sold", nullable = false)
	public Boolean getSold() {return sold;}
	@Column(name = "reservation_date")
	public LocalDateTime getResirvationDate() {return resirvationDate;}
	@ManyToOne(optional = false)
	public ShowTime getShowTime() {return showTime;}
	@ManyToOne(optional = false)
	public Seat getSeat() {return seat;}
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {return user;}

}
