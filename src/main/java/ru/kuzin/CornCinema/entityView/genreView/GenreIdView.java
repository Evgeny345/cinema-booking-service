package ru.kuzin.CornCinema.entityView.genreView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Genre;

@EntityView(Genre.class)
public interface GenreIdView {
	
	@IdMapping
	Integer getId();

}
