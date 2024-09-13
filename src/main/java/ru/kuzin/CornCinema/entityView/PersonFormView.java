package ru.kuzin.CornCinema.entityView;

import java.time.LocalDate;
import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;

import ru.kuzin.CornCinema.models.Person;

@EntityView(Person.class)
@CreatableEntityView
@UpdatableEntityView
public interface PersonFormView extends PersonView {
	
	public void setName(String name);
	public void setLastName(String lastName);
	public void setDateOfBirth(LocalDate dateOfBirth);
	
}
