package ru.kuzin.CornCinema.entityView.movieView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Movie;

@EntityView(Movie.class)
public interface MovieIdView {
	
	@IdMapping
	Integer getId();

}
