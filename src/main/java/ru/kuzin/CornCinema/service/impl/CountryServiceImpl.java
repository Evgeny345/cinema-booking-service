package ru.kuzin.CornCinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.kuzin.CornCinema.dao.CountryRepository;
import ru.kuzin.CornCinema.entityView.countryView.CountryIdView;
import ru.kuzin.CornCinema.entityView.countryView.CountryView;
import ru.kuzin.CornCinema.models.Country;
import ru.kuzin.CornCinema.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService{

	private CountryRepository countryRepository;
	
	@Autowired
	public void setCountryRepository(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	@Cacheable("countriesList")
	public List<CountryView> getAllProducingCountries() {
		return countryRepository.getAllCountryView();
	}
	
	@Override
	@Cacheable("countriesIdList")
	public List<CountryIdView> getAllCountryIdView() {
		return countryRepository.getAllCountryIdView();
	}

	@Override
	public CountryIdView getCountryIdViewById(Integer id) {
		return countryRepository.getCountryIdViewById(id);
	}

	@Override
	public void save(Country country) {
		countryRepository.save(country);
	}

	@Override
	public long amountOfCountries() {
		return countryRepository.count();
	}

}
