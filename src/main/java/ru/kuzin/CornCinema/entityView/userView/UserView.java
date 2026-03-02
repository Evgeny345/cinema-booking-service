package ru.kuzin.CornCinema.entityView.userView;

import java.util.Set;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.SubqueryProvider;

import ru.kuzin.CornCinema.entityView.ticketView.ReservableTicketView;
import ru.kuzin.CornCinema.models.Price;
import ru.kuzin.CornCinema.models.User;

@EntityView(User.class)
public interface UserView extends UserIdView {
	
	@Mapping("Ticket[user.id = VIEW(id)]")
	Set<ReservableTicketView> getTickets();
	
	class ticketPriceSubquery1 implements SubqueryProvider {

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
