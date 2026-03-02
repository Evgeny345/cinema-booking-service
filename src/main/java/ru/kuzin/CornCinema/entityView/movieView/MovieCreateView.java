package ru.kuzin.CornCinema.entityView.movieView;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;

import ru.kuzin.CornCinema.models.Movie;

@EntityView(Movie.class)
@CreatableEntityView
@UpdatableEntityView
public interface MovieCreateView extends MovieFormView {
	
}
