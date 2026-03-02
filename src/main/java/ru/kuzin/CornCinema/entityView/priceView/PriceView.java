package ru.kuzin.CornCinema.entityView.priceView;

import java.math.BigDecimal;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.Price;

@EntityView(Price.class)
public interface PriceView extends PriceIdView {
	
	@Mapping("seatCategory.name")
	String getCategoryName();
	BigDecimal getPrice();

}
