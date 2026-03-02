package ru.kuzin.CornCinema.entityView.showTimeView;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingIndex;
import com.blazebit.persistence.view.MultiCollectionMapping;
import com.blazebit.persistence.view.SubqueryProvider;

import ru.kuzin.CornCinema.entityView.priceView.PriceView;
import ru.kuzin.CornCinema.entityView.ticketView.TicketView;
import ru.kuzin.CornCinema.models.Price;
import ru.kuzin.CornCinema.models.ShowTime;

@EntityView(ShowTime.class)
public interface ShowTimeView extends ShowTimeIdView {
	
	@Mapping("movie.id")
	Integer getMovieId();
	@Mapping("movie.title")
	String getMovieTitle();
	@Mapping("hall.name")
	String getHallName();
	@Mapping("LOWER(SUBSTRING(hall.name, 1, LENGTH(hall.name)-5))")
	String getShortHallName();
	LocalDateTime getStartTime();
	Set<PriceView> getPrices();
	@MappingIndex("seat.rowNumber")
	@Mapping("tickets")
	@MultiCollectionMapping(comparator = TicketView.seatRowNumberComparator.class)
	Map<Integer, Set<TicketView>> getTickets();
	
	class ticketPriceForShowTimeSubquery implements SubqueryProvider {

        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryBuilder) {
        	
				return subqueryBuilder.from(Price.class, "p")
						.select("p.price")
				        .where("p.seatCategory.id").eqExpression("VIEW(tickets.seat.category.id)")
				        .where("p.showTime.id").eqExpression("VIEW(tickets.showTime.id)")
				        .end();
        }
    }

}
