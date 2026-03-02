package ru.kuzin.CornCinema.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;
import ru.kuzin.CornCinema.entityView.ticketView.ReservableTicketView;
import ru.kuzin.CornCinema.entityView.ticketView.ReserveTicketView;
import ru.kuzin.CornCinema.entityView.ticketView.TicketIdView;
import ru.kuzin.CornCinema.entityView.ticketView.TicketPlaceView;
import ru.kuzin.CornCinema.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	ReserveTicketView getReserveTicketViewById(Long id);
	
	void save(TicketIdView ticket);
	
	List<TicketPlaceView> findAllTicketViewBySoldAndShowTime_id(Boolean sold, Integer id);
	
	List<ReservableTicketView> findAllReservableTicketViewByShowTime_StartTimeBetweenAndUser_userName(LocalDateTime startDate, LocalDateTime endDate, String userName);
	
	List<Ticket> findByResirvationDateAndUser_id(LocalDateTime resirvationDate, Integer id);

}
