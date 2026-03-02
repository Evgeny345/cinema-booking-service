package ru.kuzin.CornCinema.entityView.ticketView;

import java.math.BigDecimal;
import java.util.Comparator;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.MappingSubquery;

import ru.kuzin.CornCinema.entityView.seatView.SeatView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeView.ticketPriceForShowTimeSubquery;
import ru.kuzin.CornCinema.models.Ticket;

@EntityView(Ticket.class)
public interface TicketView extends TicketIdView {
	
	SeatView getSeat();
	Boolean getSold();
	@MappingSubquery(ticketPriceForShowTimeSubquery.class)
	BigDecimal getPrice();
	
	static class seatRowNumberComparator implements Comparator<TicketView> {

		@Override
		public int compare(TicketView t1, TicketView t2) {
			Comparator<TicketView> compareByRow = Comparator.comparing(per -> per.getSeat().getRowNumber());		
			Comparator<TicketView> compareBySeat = Comparator.comparing(per -> per.getSeat().getSeatNumber());
			return compareByRow.thenComparing(compareBySeat).compare(t1, t2);
		}
		
	}

}
