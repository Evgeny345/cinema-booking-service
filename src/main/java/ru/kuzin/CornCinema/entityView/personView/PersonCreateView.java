package ru.kuzin.CornCinema.entityView.personView;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;

import ru.kuzin.CornCinema.models.Person;

@EntityView(Person.class)
@CreatableEntityView
@UpdatableEntityView
public interface PersonCreateView extends PersonFormView {
	
	
}
