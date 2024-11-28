package ru.kuzin.CornCinema.entityView.filmView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Film;

@EntityView(Film.class)
public interface FilmIdView {
	
	@IdMapping
	Integer getId();

}
