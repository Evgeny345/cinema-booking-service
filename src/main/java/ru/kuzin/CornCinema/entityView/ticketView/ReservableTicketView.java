package ru.kuzin.CornCinema.entityView.ticketView;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingSubquery;
import com.blazebit.persistence.view.SubqueryProvider;

import ru.kuzin.CornCinema.models.AgeRating;
import ru.kuzin.CornCinema.models.Price;
import ru.kuzin.CornCinema.models.Ticket;

@EntityView(Ticket.class)
public interface ReservableTicketView extends TicketIdView {
	
	LocalDateTime getResirvationDate();
	@Mapping("user.id")
	Integer getUserId();
	@Mapping("showTime.id")
	Integer getShowTimeId();
	@Mapping("showTime.movie.title")
	String getMovieTitle();
	@Mapping("showTime.movie.ageRating")
	AgeRating getAgeRating();
	@Mapping("showTime.movie.poster")
	String getMoviePosterLocation();
	@Mapping("showTime.hall.name")
	String getHallName();
	@Mapping("showTime.startTime")
	LocalDateTime getStartTime();
	@Mapping("seat.rowNumber")
	Integer getRowNumber();
	@Mapping("seat.seatNumber")
	Integer getSeatNumber();
	@MappingSubquery(ticketPriceSubquery.class)
	BigDecimal getPrice();
	
	class ticketPriceSubquery implements SubqueryProvider {

        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryBuilder) {
        	
				return subqueryBuilder.from(Price.class, "p")
						.select("p.price")
				        .where("p.seatCategory.id").eqExpression("VIEW(seat.category.id)")
				        .where("p.showTime.id").eqExpression("VIEW(showTime.id)")
				        .end();
        }
    }

}
