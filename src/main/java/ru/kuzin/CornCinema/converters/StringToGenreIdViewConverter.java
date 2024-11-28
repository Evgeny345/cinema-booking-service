package ru.kuzin.CornCinema.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.dao.GenreRepository;
import ru.kuzin.CornCinema.entityView.genreView.GenreIdView;

@Component
public class StringToGenreIdViewConverter implements Converter<String, GenreIdView> {
	
	private GenreRepository genreRepository;

	@Autowired
	public void setGenreRepository(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	@Override
	public GenreIdView convert(String source) {
		return genreRepository.getGenreIdViewById(Integer.parseInt(source));
	}

}
