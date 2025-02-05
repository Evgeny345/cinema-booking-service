package ru.kuzin.CornCinema.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "show_time")
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShowTime {
	
	@EqualsAndHashCode.Include
	private Integer id;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime startTime;
	private Film film;
	private Hall hall;
	private Set<Price> prices = new HashSet<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	@Column(name = "start_time", nullable = false)
	public LocalDateTime getStartTime() {return startTime;}
	@ManyToOne
	@JoinColumn(name = "film_id", referencedColumnName = "id")
	public Film getFilm() {return film;}
	@ManyToOne
	@JoinColumn(name = "hall_id", referencedColumnName = "id")
	public Hall getHall() {return hall;}
	@OneToMany(mappedBy = "showTime", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	public Set<Price> getPrices() {return prices;}
	
	public void addPrice(SeatCategory category, BigDecimal price) {
		Price priceByCategory = new Price(this, category, price);
		prices.add(priceByCategory);
		priceByCategory.setShowTime(this);
	}
	
	public void deletePrice(Price price) {
		this.prices.remove(price);
		price.setShowTime(null);
	}

}
