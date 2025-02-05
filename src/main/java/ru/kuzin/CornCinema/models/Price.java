package ru.kuzin.CornCinema.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "price_by_category")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Price {
	
	private PricePK pk;
	private ShowTime showTime;
	private SeatCategory seatCategory;
	private BigDecimal price;
	
	public Price(ShowTime showTime, SeatCategory seatCategory, BigDecimal price) {
		this.pk = new PricePK(showTime.getId(), seatCategory.getId());
		this.showTime = showTime;
		this.seatCategory = seatCategory;
		this.price = price;
	}

	@EmbeddedId
	public PricePK getPk() {return pk;}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "show_time_id", referencedColumnName = "id")
	@MapsId("showTimeId")
	public ShowTime getShowTime() {return showTime;}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_of_seat_id", referencedColumnName = "id")
	@MapsId("seatCategoryId")
	public SeatCategory getSeatCategory() {return seatCategory;}
	@Column(name = "price", nullable = false)
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=4, fraction=2)
	public BigDecimal getPrice() {return price;}

}
