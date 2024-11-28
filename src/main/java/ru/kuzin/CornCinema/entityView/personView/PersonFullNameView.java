package ru.kuzin.CornCinema.entityView.personView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.Person;

@EntityView(Person.class)
public interface PersonFullNameView extends PersonIdView {
	
	@Mapping("CONCAT(name,' ',lastName)")
	String getFullName();

}
