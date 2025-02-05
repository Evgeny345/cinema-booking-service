package ru.kuzin.CornCinema.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.dao.HallRepository;
import ru.kuzin.CornCinema.entityView.hallView.HallIdView;

@Component
public class StringToHallIdViewConverter implements Converter<String, HallIdView> {

	private HallRepository hallRepository;
	
	@Autowired
	public void setHallRepository(HallRepository hallRepository) {
		this.hallRepository = hallRepository;
	}

	@Override
	public HallIdView convert(String source) {
		return hallRepository.getHallIdViewById(Integer.parseInt(source));
	}

}
