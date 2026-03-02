package ru.kuzin.CornCinema.entityView.ticketView;

import java.time.LocalDateTime;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FlushMode;
import com.blazebit.persistence.view.UpdatableEntityView;

import ru.kuzin.CornCinema.models.Ticket;

@EntityView(Ticket.class)
@UpdatableEntityView(mode = FlushMode.PARTIAL)
public interface ReserveTicketView extends TicketIdView {
	
	Boolean getSold();
	LocalDateTime getResirvationDate();
	
	void setSold(Boolean sold);
	void setResirvationDate(LocalDateTime rLocalDateTime);
	
}
