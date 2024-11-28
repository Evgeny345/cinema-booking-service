package ru.kuzin.CornCinema.entityView.countryView;

import com.blazebit.persistence.view.EntityView;

import ru.kuzin.CornCinema.models.Country;

@EntityView(Country.class)
public interface CountryView extends CountryIdView {

	String getName();

}
