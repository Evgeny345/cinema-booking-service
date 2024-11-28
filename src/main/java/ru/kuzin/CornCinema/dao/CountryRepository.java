package ru.kuzin.CornCinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.entityView.countryView.CountryIdView;
import ru.kuzin.CornCinema.entityView.countryView.CountryView;
import ru.kuzin.CornCinema.models.Country;

public interface CountryRepository extends JpaRepository<Country, Integer>{
	
	List<CountryView> getAllCountryView();
	CountryIdView getCountryIdViewById(Integer id);

}
