package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.countryView.CountryIdView;
import ru.kuzin.CornCinema.entityView.countryView.CountryView;
import ru.kuzin.CornCinema.models.Country;

public interface CountryService {
	
	List<CountryView> getAllProducingCountries();
	
	List<CountryIdView> getAllCountryIdView();
	
	CountryIdView getCountryIdViewById(Integer id);
	
	void save(Country country);
	
	long amountOfCountries();

}
