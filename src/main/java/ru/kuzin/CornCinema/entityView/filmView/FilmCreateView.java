package ru.kuzin.CornCinema.entityView.filmView;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;

import ru.kuzin.CornCinema.models.Film;

@EntityView(Film.class)
@CreatableEntityView
@UpdatableEntityView
public interface FilmCreateView extends FilmFormView {
	
	
}
