package ru.kuzin.CornCinema.entityView.personView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.PersonWithAmpluaForMovie;

@EntityView(PersonWithAmpluaForMovie.class)
public interface PersonForCreateMovie {
	
	@Mapping("person.id")
	Integer getPersonId();
	@Mapping("amplua.id")
	Integer getAmpluaId();
	
}
