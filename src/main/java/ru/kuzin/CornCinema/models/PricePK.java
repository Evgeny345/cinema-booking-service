package ru.kuzin.CornCinema.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricePK implements Serializable {
	
	private static final long serialVersionUID = -7667825354613038476L;
	
	private Integer showTimeId;
	private Integer seatCategoryId;

}
