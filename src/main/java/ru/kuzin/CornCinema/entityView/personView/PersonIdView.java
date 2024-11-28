package ru.kuzin.CornCinema.entityView.personView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Person;

@EntityView(Person.class)
public interface PersonIdView {
	
	@IdMapping
	Integer getId();

}
