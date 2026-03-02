package ru.kuzin.CornCinema.entityView.ticketView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Ticket;

@EntityView(Ticket.class)
public interface TicketIdView {
	
	@IdMapping
	Long getId();

}
