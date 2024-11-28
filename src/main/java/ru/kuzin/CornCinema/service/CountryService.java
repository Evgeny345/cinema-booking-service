package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.countryView.CountryIdView;
import ru.kuzin.CornCinema.entityView.countryView.CountryView;

public interface CountryService {
	
	List<CountryView> getAllProducingCountries();
	CountryIdView getCountryIdViewById(Integer id);

}
