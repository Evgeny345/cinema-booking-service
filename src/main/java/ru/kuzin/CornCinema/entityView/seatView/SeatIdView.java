package ru.kuzin.CornCinema.entityView.seatView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Seat;

@EntityView(Seat.class)
public interface SeatIdView {
	
	@IdMapping
	Integer getId();

}
