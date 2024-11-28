package ru.kuzin.CornCinema.entityView.countryView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Country;

@EntityView(Country.class)
public interface CountryIdView {

	@IdMapping
	Integer getId();

}
