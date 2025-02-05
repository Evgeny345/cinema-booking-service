package ru.kuzin.CornCinema.entityView.priceView;

import java.math.BigDecimal;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.Price;

@EntityView(Price.class)
public interface TicketPriceForm {
	
	@IdMapping("this")
	@Mapping("seatCategory.id")
	Integer getSeatCategoryId();
	@Mapping("seatCategory.name")
	String getSeatCategoryName();
	BigDecimal getPrice();

}
