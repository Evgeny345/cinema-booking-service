package ru.kuzin.CornCinema.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.entityView.personView.PersonForCreateMovie;
import ru.kuzin.CornCinema.entityView.personView.PersonForCreateMovieImpl;

@Component
public class StringToPersonForCreateMovieConverter implements Converter<String, PersonForCreateMovie> {

	@Override
	public PersonForCreateMovie convert(String source) {
		String[] data = source.split(",");
		return new PersonForCreateMovieImpl(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
	}

}
