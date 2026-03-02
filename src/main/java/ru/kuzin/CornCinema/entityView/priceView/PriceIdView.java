package ru.kuzin.CornCinema.entityView.priceView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Price;
import ru.kuzin.CornCinema.models.PricePK;

@EntityView(Price.class)
public interface PriceIdView {
	
	@IdMapping
	PricePK getPk();

}
