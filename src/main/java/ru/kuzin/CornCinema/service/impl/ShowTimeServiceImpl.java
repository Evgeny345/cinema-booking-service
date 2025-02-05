package ru.kuzin.CornCinema.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blazebit.persistence.view.ConvertOption;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.PrePersistEntityListener;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.ShowTimeRepository;
import ru.kuzin.CornCinema.entityView.priceView.TicketPriceFormImpl;
import ru.kuzin.CornCinema.entityView.seatCategoryView.SeatCategoryIdView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeCreateView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeDuration;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeFormViewImpl;
import ru.kuzin.CornCinema.models.SeatCategory;
import ru.kuzin.CornCinema.models.ShowTime;
import ru.kuzin.CornCinema.service.SeatCategoryService;
import ru.kuzin.CornCinema.service.ShowTimeService;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@Service
public class ShowTimeServiceImpl implements ShowTimeService{

	private ShowTimeRepository showTimeRepository;
	private SeatCategoryService seatCategoryService;
	private CinemaWorkingHours cinemaWorkingHours;
	private EntityManager em;
	private EntityViewManager evm;
	private static final Logger logger = LogManager.getLogger(ShowTimeServiceImpl.class);
	
	@Autowired
	public void setShowTimeRepository(ShowTimeRepository showTimeRepository) {
		this.showTimeRepository = showTimeRepository;
	}
	@Autowired
	public void setSeatCategoryService(SeatCategoryService seatCategoryService) {
		this.seatCategoryService = seatCategoryService;
	}
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {
		this.cinemaWorkingHours = cinemaWorkingHours;
	}
	@Autowired
	public void setEm(EntityManager em) {
		this.em = em;
	}
	@Autowired
	public void setEvm(EntityViewManager evm) {
		this.evm = evm;
	}
	
	@Override
	@Transactional
	public boolean deleteShowTime(Integer id) {
		//ShowTime s = showTimeRepository.findById(id).get();
		//s.getPrices().stream().forEach(p -> s.deletePrice(p));
		//showTimeRepository.delete(s);
		boolean isDeleted = false;
		if(showTimeRepository.existsById(id)) {
			em.createQuery("delete from ShowTime where id = :id").setParameter("id", id).executeUpdate();
			isDeleted = true;
		}
		return isDeleted;		
	}
	
	@Override
	public Map<LocalDate, List<ShowTimeDuration>> getAllShowTimesBetweenDatesByDate(LocalDate startPeriod, LocalDate endPeriod) {
		
		Map<LocalDate, List<ShowTimeDuration>> showTimesByDate =startPeriod.datesUntil(cinemaWorkingHours.getCinemaClosingDay(endPeriod))
				.collect(Collectors.toMap(Function.identity(), d -> getAllShowTimesBetweenDates(d, d)));
		return showTimesByDate;
	}
	
	@Override
	public List<ShowTimeDuration> getAllShowTimesBetweenDates(LocalDate startPeriod, LocalDate endPeriod) {
		LocalDateTime start = LocalDateTime.of(startPeriod, cinemaWorkingHours.getOpeningTime());
		LocalDateTime end = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(endPeriod);
		return showTimeRepository.findAllShowTimeDurationByStartTimeBetweenOrderByStartTimeAsc(start, end, cinemaWorkingHours);
	}
	
	@Override
	public ShowTimeFormViewImpl getShowTimeForm() {
		ShowTimeFormViewImpl showTime = new ShowTimeFormViewImpl();
		seatCategoryService.getAllCategoryOfSeats()
						   .forEach(sc -> showTime.getListTicketPrice()
								   				  .add(new TicketPriceFormImpl(sc.getId(), sc.getName())));
		return showTime;
	}
	
	@Override
	@Transactional
	public void createShowTime(ShowTimeFormViewImpl showTimeForm) {
		ShowTimeCreateView showTime = evm.convert(showTimeForm, ShowTimeCreateView.class, ConvertOption.CREATE_NEW);
		LocalDate showTimeBeganDate;
		LocalTime showTimeBeganTime = showTime.getStartTime().toLocalTime();
		
		if(showTimeBeganTime.isBefore(cinemaWorkingHours.getOpeningTime())) {
			showTimeBeganDate = showTime.getStartTime().toLocalDate().plusDays(1);
			showTime.setStartTime(LocalDateTime.of(showTimeBeganDate, showTimeBeganTime));
		}
		
		evm.saveWith(em, showTime)
		   .onPrePersist(ShowTimeCreateView.class, ShowTime.class, new PrePersistEntityListener<ShowTimeCreateView, ShowTime>() {
			   
			   @Override
			public void prePersist(EntityViewManager entityViewManager, EntityManager entityManager,
								   ShowTimeCreateView view, ShowTime entity) {

				showTimeForm.getListTicketPrice()
							.forEach((ticketPrice -> {
								SeatCategory category = entityViewManager
													   .getEntityReference(entityManager, 
															   			   entityViewManager.getReference(SeatCategoryIdView.class, ticketPrice.getSeatCategoryId()));

								entity.addPrice(category, ticketPrice.getPrice());
							}));
			}
			   
		   })
		   .flush();
		
	}
	@Override
	public ShowTimeDuration getShowTimeDurationById(Integer id) {
		ShowTimeDuration s = showTimeRepository.getShowTimeDurationById(id, cinemaWorkingHours);
		s.getEndTime();		
		return null;
	}

}
