package ru.kuzin.CornCinema.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.entityView.countryView.CountryIdView;
import ru.kuzin.CornCinema.service.CountryService;

@Component
public class StringToCountryIdViewConverter implements Converter<String, CountryIdView> {

	private CountryService countryService;
	
	@Autowired
	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	@Override
	public CountryIdView convert(String source) {
		return countryService.getCountryIdViewById(Integer.parseInt(source));
	}

}
