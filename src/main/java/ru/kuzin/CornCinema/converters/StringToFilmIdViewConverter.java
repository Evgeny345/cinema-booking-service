package ru.kuzin.CornCinema.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.dao.FilmRepository;
import ru.kuzin.CornCinema.entityView.filmView.FilmIdView;

@Component
public class StringToFilmIdViewConverter implements Converter<String, FilmIdView> {

	private FilmRepository filmRepository;
	
	@Autowired
	public void setFilmRepository(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}

	@Override
	public FilmIdView convert(String source) {
		return filmRepository.getFilmIdViewById(Integer.parseInt(source));
	}

}
