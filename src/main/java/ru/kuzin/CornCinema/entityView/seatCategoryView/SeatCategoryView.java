package ru.kuzin.CornCinema.entityView.seatCategoryView;

import com.blazebit.persistence.view.EntityView;

import ru.kuzin.CornCinema.models.SeatCategory;

@EntityView(SeatCategory.class)
public interface SeatCategoryView extends SeatCategoryIdView {
	
	String getName();	

}
