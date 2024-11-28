package ru.kuzin.CornCinema.entityView.personView;

import java.time.LocalDate;
import java.util.Set;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;
import com.blazebit.persistence.view.UpdatableMapping;

import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Person;

@EntityView(Person.class)
@CreatableEntityView
@UpdatableEntityView
public interface PersonFormView extends PersonIdView {
	
	String getName();
	String getLastName();
	LocalDate getDateOfBirth();
	@UpdatableMapping
	Set<Amplua> getAmpluas();
	
	void setName(String name);
	void setLastName(String lastName);
	void setDateOfBirth(LocalDate dateOfBirth);
	
}
