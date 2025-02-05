package ru.kuzin.CornCinema.entityView.seatCategoryView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.SeatCategory;

@EntityView(SeatCategory.class)
public interface SeatCategoryIdView {
	
	@IdMapping
	Integer getId();

}
