package ru.kuzin.CornCinema.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import ru.kuzin.CornCinema.entityView.ticketView.ReservableTicketView;
import ru.kuzin.CornCinema.entityView.ticketView.TicketPlaceView;

public interface TicketService {
	
	List<TicketPlaceView> reserveTickets(List<Long> ticketsIds, Integer showTimeId, String userName);
	
	Map<LocalDateTime, List<ReservableTicketView>> allActiveReservationsForUserByShowTime(String userName, LocalDate startPeriod, LocalDate endPeriod);

	void chancelReservation(LocalDateTime resirvationDate, Integer id);
	
}
