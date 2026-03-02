package ru.kuzin.CornCinema.entityView.ticketView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.Ticket;

@EntityView(Ticket.class)
public interface TicketPlaceView extends TicketIdView {
	
	@Mapping("seat.rowNumber")
	Integer getRowNumber();
	@Mapping("seat.seatNumber")
	Integer getSeatNumber();
	@Mapping("showTime.id")
	Integer getShowTimeId();

}
