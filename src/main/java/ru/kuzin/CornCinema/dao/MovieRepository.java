package ru.kuzin.CornCinema.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazebit.persistence.spring.data.annotation.OptionalParam;
import com.blazebit.persistence.spring.data.repository.EntityViewSettingProcessor;

import ru.kuzin.CornCinema.entityView.movieView.MovieCreateView;
import ru.kuzin.CornCinema.entityView.movieView.MovieFormView;
import ru.kuzin.CornCinema.entityView.movieView.MovieIdView;
import ru.kuzin.CornCinema.entityView.movieView.MovieInfoForShowTimeCreate;
import ru.kuzin.CornCinema.entityView.movieView.MovieView;
import ru.kuzin.CornCinema.entityView.movieView.MovieWithShowTimeView;
import ru.kuzin.CornCinema.models.Movie;
import ru.kuzin.CornCinema.utilites.AvailableTimeForShowTime;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	MovieIdView getMovieIdViewById(Integer id);
	
	MovieFormView getMovieFormViewById(Integer id);
	
	MovieCreateView getMovieCreateViewById(Integer id);
	
	Optional<MovieWithShowTimeView> findMovieWithShowTimeViewById(Integer id, 
			 										@OptionalParam("startDate") LocalDateTime startDate, 
			 										@OptionalParam("endDate") LocalDateTime endDate,		
			 										@OptionalParam("cinemaWorkingHours") CinemaWorkingHours cinemaWorkingHours);
	
	List<MovieView> findAllMovieViewByShowTimes_StartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	List<MovieCreateView>  getAllMovieCreateView();
	
	List<MovieInfoForShowTimeCreate> getAllMovieInfoForShowTimeCreate(@OptionalParam("startDate") LocalDateTime startPeriod, 
																	  @OptionalParam("endDate") LocalDateTime endPeriod, 
																	  @OptionalParam("timeForShowTime") AvailableTimeForShowTime service,
																	  EntityViewSettingProcessor<MovieInfoForShowTimeCreate> processor);
	
	MovieInfoForShowTimeCreate getMovieInfoForShowTimeCreateById(Integer id, @OptionalParam("startDate") LocalDateTime startPeriod, 
			  @OptionalParam("endDate") LocalDateTime endPeriod, 
			  @OptionalParam("timeForShowTime") AvailableTimeForShowTime service,
			  EntityViewSettingProcessor<MovieInfoForShowTimeCreate> processor);
	
	void save(MovieCreateView movie);
	
}
