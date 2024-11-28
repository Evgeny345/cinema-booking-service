package ru.kuzin.CornCinema.entityView.personView;

import java.util.Set;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.Person;

@EntityView(Person.class)
public interface PersonWithAmpluaView extends PersonIdView {
	
	@Mapping("ampluas.profession")
	Set<String> getAmpluas();

}
