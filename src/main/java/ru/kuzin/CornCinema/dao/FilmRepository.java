package ru.kuzin.CornCinema.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazebit.persistence.spring.data.annotation.OptionalParam;

import ru.kuzin.CornCinema.entityView.filmView.FilmIdView;
import ru.kuzin.CornCinema.entityView.filmView.FilmInfoForShowTimeCreate;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeDuration;
import ru.kuzin.CornCinema.models.Film;
import ru.kuzin.CornCinema.utilites.AvailableTimeForShowTime;

public interface FilmRepository extends JpaRepository<Film, Integer> {
	
	FilmIdView getFilmIdViewById(Integer id);
	List<FilmInfoForShowTimeCreate> getAllFilmInfoForShowTimeCreate(@OptionalParam("startDate") LocalDateTime startPeriod, 
																	@OptionalParam("endDate") LocalDateTime endPeriod, 
																	@OptionalParam("showTimeList") List<ShowTimeDuration> showTimes, 
																	@OptionalParam("timeForShowTime") AvailableTimeForShowTime service);
	FilmInfoForShowTimeCreate getFilmInfoForShowTimeCreateById(Integer id,
															   @OptionalParam("startDate") LocalDateTime startPeriod, 
															   @OptionalParam("endDate") LocalDateTime endPeriod, 
															   @OptionalParam("timeForShowTime") AvailableTimeForShowTime service);
	
}
