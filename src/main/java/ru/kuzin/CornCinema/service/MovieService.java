package ru.kuzin.CornCinema.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import ru.kuzin.CornCinema.entityView.movieView.MovieFormView;
import ru.kuzin.CornCinema.entityView.movieView.MovieFormViewImpl;
import ru.kuzin.CornCinema.entityView.movieView.MovieIdView;
import ru.kuzin.CornCinema.entityView.movieView.MovieInfoForShowTimeCreate;
import ru.kuzin.CornCinema.entityView.movieView.MovieView;
import ru.kuzin.CornCinema.entityView.movieView.MovieWithShowTimeView;
import ru.kuzin.CornCinema.entityView.movieView.MovieWithSubqueryView;

public interface MovieService {
	
	MovieFormView getMovieForm();
	
	MovieFormViewImpl getMovieFormViewImplById(Integer id);
	
	void createMovie(MovieFormViewImpl form, MultipartFile poster);
	
	void updateMovie(MovieFormViewImpl movie, MultipartFile poster);
	
	void deleteMovieById(Integer id);
	
	void deletePoster(String directoryName, Integer movieId);
	
	List<MovieInfoForShowTimeCreate> getAllMovieInfoForShowTimeCreate(LocalDate startPeriod, LocalDate endPeriod);
	
	void deleteAll();
	
	List<MovieView> getAllMoviesInPlaying();
	
	List<MovieWithSubqueryView> getAllExistingAndAvailableMovies(LocalDate startPeriod);
	
	MovieWithShowTimeView getMovieDescription(Integer id, LocalDate startPeriod, LocalDate endPeriod);
	
	void checkAndSetUpMovieToPlaying(Map<String, String> map);
	
	String getPosterDirectory(Integer movieId);
	
	long amountOfMovies();
	
	MovieIdView getMovieIdView(Integer id);

}
