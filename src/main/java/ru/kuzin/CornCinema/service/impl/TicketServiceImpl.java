package ru.kuzin.CornCinema.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.blazebit.persistence.view.EntityViewManager;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.TicketRepository;
import ru.kuzin.CornCinema.entityView.ticketView.ReservableTicketView;
import ru.kuzin.CornCinema.entityView.ticketView.ReserveTicketView;
import ru.kuzin.CornCinema.entityView.ticketView.TicketPlaceView;
import ru.kuzin.CornCinema.models.Ticket;
import ru.kuzin.CornCinema.models.User;
import ru.kuzin.CornCinema.service.TicketService;
import ru.kuzin.CornCinema.service.UserService;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;
import ru.kuzin.CornCinema.utilites.ImageUploadService;

@Service
public class TicketServiceImpl implements TicketService {
	
	private TicketRepository ticketRepository;
	private UserService userService;
	private CinemaWorkingHours cinemaWorkingHours;
	private EntityManager entityManager;
	private EntityViewManager entityViewManager;
	private ImageUploadService imageUploadService;
	
	@Autowired
	public void setTicketRepository(TicketRepository ticketRepository) {this.ticketRepository = ticketRepository;}
	@Autowired
	public void setUserService(UserService userService) {this.userService = userService;}
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {this.cinemaWorkingHours = cinemaWorkingHours;}
	@Autowired
	public void setEntityManager(EntityManager entityManager) {this.entityManager = entityManager;}
	@Autowired
	public void setEntityViewManager(EntityViewManager entityViewManager) {this.entityViewManager = entityViewManager;}
	@Autowired
	public void setImageUploadService(ImageUploadService imageUploadService) {this.imageUploadService = imageUploadService;}
	
	@Override
	@Transactional
	public List<TicketPlaceView> reserveTickets(List<Long> ticketsId, Integer showTimeId, String userName) {
		List<TicketPlaceView> unavailableForReservationTickets = exposeAlredyReservedTickets(showTimeId, ticketsId);
		if(CollectionUtils.isEmpty(unavailableForReservationTickets)) {
			User user = userService.getUserByUserName(userName);
			LocalDateTime registrationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
			ticketsId.forEach((id) -> {
				ReserveTicketView ticket = ticketRepository.getReserveTicketViewById(id);
				ticket.setSold(true);
				ticket.setResirvationDate(registrationDate);
				user.addTicket(entityViewManager.getEntityReference(entityManager, ticket));
				ticketRepository.save(ticket);
			});
			imageUploadService.createQRcode(user.getId(), registrationDate, ticketsId);
		}
		return unavailableForReservationTickets;
	}
	
	@Override
	public Map<LocalDateTime, List<ReservableTicketView>> allActiveReservationsForUserByShowTime(String userName, LocalDate startPeriod, LocalDate endPeriod) {
		LocalDateTime startDate = LocalDateTime.of(startPeriod, cinemaWorkingHours.getOpeningTime());
		LocalDateTime endDate = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(endPeriod);
		List<ReservableTicketView> listActiveReservations = ticketRepository.findAllReservableTicketViewByShowTime_StartTimeBetweenAndUser_userName(startDate, endDate, userName);
		Map<LocalDateTime, List<ReservableTicketView>> activeReservationsByShowTime =
				listActiveReservations.stream().collect(Collectors.groupingBy(ReservableTicketView::getResirvationDate));
		return activeReservationsByShowTime;
	}
	
	@Override
	public void chancelReservation(LocalDateTime resirvationDate, Integer id) {
		List<Ticket> ticketsToChancelReservation = ticketRepository.findByResirvationDateAndUser_id(resirvationDate, id);
		ticketsToChancelReservation.stream().forEach((ticket) -> {
			ticket.setResirvationDate(null);
			ticket.setUser(null);
			ticket.setSold(false);
			ticketRepository.save(ticket);
		});
		imageUploadService.deleteQrCode(resirvationDate, id);
	}
	
	private List<TicketPlaceView> exposeAlredyReservedTickets(Integer showTimeId, List<Long> ticketsIds) {
		List<TicketPlaceView> unavailableTickets = new ArrayList<>();
		ticketRepository.findAllTicketViewBySoldAndShowTime_id(true, showTimeId).forEach(t -> {
			if(ticketsIds.contains(t.getId()))
				unavailableTickets.add(t);
		});
		return unavailableTickets;
	}
	
}
