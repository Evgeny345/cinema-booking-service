package ru.kuzin.CornCinema.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.entityView.personView.PersonForCreateFilmImpl;

@Component
public class StringToPersonForCreateFilmConverter implements Converter<String, PersonForCreateFilmImpl> {

	@Override
	public PersonForCreateFilmImpl convert(String source) {
		String[] data = source.split(",");
		return new PersonForCreateFilmImpl(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
	}

}
