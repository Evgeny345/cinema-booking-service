package ru.kuzin.CornCinema.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.dao.MovieRepository;
import ru.kuzin.CornCinema.entityView.movieView.MovieIdView;

@Component
public class StringToMovieIdViewConverter implements Converter<String, MovieIdView> {

	private MovieRepository movieRepository;
	
	@Autowired
	public void setMovieRepository(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public MovieIdView convert(String source) {
		return movieRepository.getMovieIdViewById(Integer.parseInt(source));
	}

}
