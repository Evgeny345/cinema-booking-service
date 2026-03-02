package ru.kuzin.CornCinema.entityView.personView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.PersonWithAmpluaForMovie;
import ru.kuzin.CornCinema.models.PersonWithAmpluaForMoviePk;

@EntityView(PersonWithAmpluaForMovie.class)
public interface PersonWithAmpluaForMovieView {
	
	@IdMapping
	PersonWithAmpluaForMoviePk getPk();
	@Mapping("CONCAT(person.name,' ',person.lastName)")
	String getFullName();
	
}
