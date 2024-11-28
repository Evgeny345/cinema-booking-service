package ru.kuzin.CornCinema.entityView.genreView;

import com.blazebit.persistence.view.EntityView;

import ru.kuzin.CornCinema.models.Genre;

@EntityView(Genre.class)
public interface GenreView extends GenreIdView {
	
	String getTitle();

}
