package ru.kuzin.CornCinema.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import ru.kuzin.CornCinema.dao.TicketRepository;
import ru.kuzin.CornCinema.entityView.movieView.MovieInfoForShowTimeCreate;
import ru.kuzin.CornCinema.entityView.movieView.MovieView;
import ru.kuzin.CornCinema.entityView.priceView.TicketPriceFormImpl;
import ru.kuzin.CornCinema.entityView.seatCategoryView.SeatCategoryIdView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeCreateView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeForScheduleView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeFormViewImpl;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeView;
import ru.kuzin.CornCinema.exceptions.NotFoundException;
import ru.kuzin.CornCinema.models.Seat;
import ru.kuzin.CornCinema.models.SeatCategory;
import ru.kuzin.CornCinema.models.ShowTime;
import ru.kuzin.CornCinema.service.HallService;
import ru.kuzin.CornCinema.service.MovieService;
import ru.kuzin.CornCinema.service.SeatCategoryService;
import ru.kuzin.CornCinema.service.ShowTimeService;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@Service
public class ShowTimeServiceImpl implements ShowTimeService{

	private ShowTimeRepository showTimeRepository;
	private SeatCategoryService seatCategoryService;
	private CinemaWorkingHours cinemaWorkingHours;
	private HallService hallService;
	private MovieService movieService;
	private EntityManager em;
	private EntityViewManager evm;
	private static final Logger logger = LogManager.getLogger(ShowTimeServiceImpl.class);
	private TicketRepository ticketRepository;
	private static final Random random = new Random();
	
	@Autowired
	public void setShowTimeRepository(ShowTimeRepository showTimeRepository) {this.showTimeRepository = showTimeRepository;}
	@Autowired
	public void setSeatCategoryService(SeatCategoryService seatCategoryService) {this.seatCategoryService = seatCategoryService;}
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {this.cinemaWorkingHours = cinemaWorkingHours;}
	@Autowired
	public void setHallService(HallService hallService) {this.hallService = hallService;}
	@Autowired
	public void setMovieService(MovieService movieService) {this.movieService = movieService;}
	@Autowired
	public void setEm(EntityManager em) {this.em = em;}
	@Autowired
	public void setEvm(EntityViewManager evm) {this.evm = evm;}
	@Autowired
	public void setTicketRepository(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}
	
	@Override
	@Transactional
	public boolean deleteShowTime(Integer id) {
		ShowTime s = showTimeRepository.findById(id).get();
		//s.getPrices().stream().forEach(p -> s.deletePrice(p));
		s.getTickets().stream().forEach(t -> ticketRepository.deleteById(t.getId()));
		showTimeRepository.delete(s);
		/*boolean isDeleted = false;
		if(showTimeRepository.existsById(id)) {
			em.createQuery("delete from ShowTime where id = :id").setParameter("id", id).executeUpdate();
			isDeleted = true;
		}*/
		return true;		
	}
	
	@Override
	public Map<MovieView, Map<LocalDate, List<ShowTimeForScheduleView>>> getAllShowTimesByMovieAndDate(LocalDate startPeriod, LocalDate endPeriod) {
		Map<MovieView, Map<LocalDate, List<ShowTimeForScheduleView>>> showTimesByFilmAndDate = getAllShowTimesBetweenDates(startPeriod, endPeriod).stream()
						.collect(Collectors.collectingAndThen(
								 Collectors.groupingBy(ShowTimeForScheduleView::getMovie), map -> map.entrySet().stream()
						.collect(Collectors.toMap(k1 -> k1.getKey(), v1 -> v1.getValue().stream()
						.collect(Collectors.groupingBy(ShowTimeForScheduleView::getStartDayForSchedule, TreeMap::new, Collectors.toList()))))));
		List<LocalDate> dates = startPeriod.datesUntil(cinemaWorkingHours.getCinemaClosingDay(endPeriod)).toList();
		showTimesByFilmAndDate.forEach((film, showTimesByDate) -> {
			dates.stream().filter(date -> !showTimesByDate.keySet().contains(date)).forEach((mismatchDate) -> {
				showTimesByDate.put(mismatchDate, new ArrayList<>());
			});
		});
		return showTimesByFilmAndDate;
	}

	
	@Override
	public Map<LocalDate, List<ShowTimeForScheduleView>> getAllShowTimesBetweenDatesByDate(LocalDate startPeriod, LocalDate endPeriod) {
		
		Map<LocalDate, List<ShowTimeForScheduleView>> showTimesByDate = startPeriod.datesUntil(cinemaWorkingHours.getCinemaClosingDay(endPeriod))
				.collect(Collectors.toMap(Function.identity(), d -> getAllShowTimesBetweenDates(d, d)));
		
		return showTimesByDate;
	}
	
	@Override
	public List<ShowTimeForScheduleView> getAllShowTimesBetweenDates(LocalDate startPeriod, LocalDate endPeriod) {
		LocalDateTime startDate = LocalDateTime.of(startPeriod, cinemaWorkingHours.getOpeningTime());
		LocalDateTime endDate = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(endPeriod);
		return showTimeRepository.findAllShowTimeForScheduleViewByStartTimeBetweenOrderByStartTimeAsc(startDate, endDate, cinemaWorkingHours);
		
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
				
				hallService.getHallScheme(showTimeForm.getHall().getId()).getSeatsByRows().values()
							.forEach((collection -> {
								collection.stream().forEach((seatView -> {									
									Seat seat = entityViewManager.getEntityReference(entityManager, seatView);
									entity.addTicket(seat);
								}));
							}));

			}
			
		   })
		   .flush();
		
	}
	
	@Override
	public ShowTimeView getShowTimeViewById(Integer id) throws NotFoundException {
		return showTimeRepository.getShowTimeViewById(id).orElseThrow(() -> new NotFoundException(String.format("Show time with id %d not found", id)));
	}
	
	@Override
	@Transactional
	public void createRandomSchedule(LocalDate startPeriod, LocalDate endPeriod) {
		//LocalDate startPeriod = LocalDate.now();
		//LocalDate endPeriod = startPeriod.plusDays(3);
		//LocalDate startPeriod = LocalDate.of(2025, 12, 25);
		//LocalDate endPeriod = LocalDate.of(2025, 12, 26);
		
		Stream.generate(() -> 1).limit(100).forEach((i) -> {
			List<MovieInfoForShowTimeCreate> moviesForShowTimeCreateList = movieService.getAllMovieInfoForShowTimeCreate(startPeriod, endPeriod);
			MovieInfoForShowTimeCreate movie = moviesForShowTimeCreateList.get(random.nextInt(moviesForShowTimeCreateList.size()));		
			if(isThereAvailableTime(movie.getAvailableTimeForShowTimeCreate()))
				createRandomShowTime(movie);
		});	
	}
		
	private void createRandomShowTime(MovieInfoForShowTimeCreate movie) {
		Map<LocalDate, Map<Integer, List<LocalTime>>> availableTimeForHallByDate = movie.getAvailableTimeForShowTimeCreate();
		
		LocalDate showTimeDay = availableTimeForHallByDate.keySet().stream()
				.skip(random.nextInt(availableTimeForHallByDate.size())).findFirst().get();
		
		Map<Integer, List<LocalTime>> availableTimeForHall = availableTimeForHallByDate.get(showTimeDay);
		
		Integer hallId = availableTimeForHall.keySet().stream()
				.skip(random.nextInt(availableTimeForHall.size())).findFirst().get();
		
		List<LocalTime> availableTime = availableTimeForHall.get(hallId);
		
		if(!availableTime.isEmpty()) {
			ShowTimeFormViewImpl showTime = getShowTimeForm();
			LocalTime showTimeTime = availableTime.get(random.nextInt(availableTime.size()));
			
			BigDecimal standartPrice = new BigDecimal(randomPrice(null));
			BigDecimal forKissingPrice = new BigDecimal(randomPrice(standartPrice.intValue()));
			
			showTime.setMovie(movieService.getMovieIdView(movie.getId()));
			showTime.setHall(hallService.getHallIdView(hallId));
			showTime.setStartTime(LocalDateTime.of(showTimeDay, showTimeTime));
			showTime.getListTicketPrice().forEach((seat) -> {
				if(Objects.equals(seat.getSeatCategoryName(), "standart")) {
					seat.setPrice(standartPrice);
				} else {
					seat.setPrice(forKissingPrice);
				}
			});
			createShowTime(showTime);
		}
		
	}
	
	private Integer randomPrice(Integer lowerLimit) {
		Integer origin = lowerLimit == null? 50: lowerLimit + 50;
		List<Integer> possiblePrices = Stream.iterate(origin, i -> i + 50).limit(10).toList();
		return possiblePrices.get(random.nextInt(0, possiblePrices.size() - 1));
	}
	
	private boolean isThereAvailableTime(Map<LocalDate, Map<Integer, List<LocalTime>>> availableTimeForHallByDate) {
		List<LocalTime> availableTimeList = availableTimeForHallByDate.values().stream().flatMap(e -> e.values().stream().flatMap(e1 -> e1.stream())).collect(Collectors.toList());
		if(availableTimeList.isEmpty())
			return false;
		return true;
	}
	
}
