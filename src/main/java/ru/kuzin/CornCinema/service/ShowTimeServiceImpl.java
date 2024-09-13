package ru.kuzin.CornCinema.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.FilmRepository;
import ru.kuzin.CornCinema.dao.HallRepository;
import ru.kuzin.CornCinema.dao.SeatCategoryRepository;
import ru.kuzin.CornCinema.dao.ShowTimeRepository;
import ru.kuzin.CornCinema.models.Film;
import ru.kuzin.CornCinema.models.Hall;
import ru.kuzin.CornCinema.models.SeatCategory;
import ru.kuzin.CornCinema.models.ShowTime;

@Service
public class ShowTimeServiceImpl implements ShowTimeService{

	private ShowTimeRepository showTimeRepository;
	private FilmRepository filmRepository;
	private HallRepository hallRepository;
	private SeatCategoryRepository seatCategoryRepository;
	private EntityManager em;
	
	@Autowired
	public void setShowTimeRepository(ShowTimeRepository showTimeRepository) {
		this.showTimeRepository = showTimeRepository;
	}
	@Autowired
	public void setFilmRepository(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}
	@Autowired
	public void setHallRepository(HallRepository hallRepository) {
		this.hallRepository = hallRepository;
	}
	@Autowired
	public void setSeatCategoryRepository(SeatCategoryRepository seatCategoryRepository) {
		this.seatCategoryRepository = seatCategoryRepository;
	}
	@Autowired
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	@Override
	@Transactional
	public void addShowTime() {		
		Film film = filmRepository.findById(1).get();
		Hall hall = hallRepository.findById(1).get();
		LocalDateTime date = LocalDateTime.of(2024, 8, 11, 14, 20);
		SeatCategory category1 = seatCategoryRepository.findById(1).get();
		SeatCategory category2 = seatCategoryRepository.findById(2).get();
		BigDecimal price1 = new BigDecimal(100.0);
		BigDecimal price2 = new BigDecimal(200.0);
		ShowTime showTime = new ShowTime();
		showTime.setFilm(film);
		showTime.setHall(hall);
		showTime.setStartTime(date);
		showTime.addPrice(category2, price2);
		showTime.addPrice(category1, price1);
		showTimeRepository.save(showTime);
	}
	
	@Override
	@Transactional
	public void deleteShowTime(Integer id) {
		//ShowTime s = showTimeRepository.findById(id).get();
		//s.getPrices().stream().forEach(p -> s.deletePrice(p));
		//showTimeRepository.delete(s);
		em.createQuery("delete from ShowTime where id = :id").setParameter("id", id).executeUpdate();
	}

}
