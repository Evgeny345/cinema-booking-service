package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.genreView.GenreIdView;
import ru.kuzin.CornCinema.entityView.genreView.GenreView;
import ru.kuzin.CornCinema.models.Genre;

public interface GenreService {
	
	List<GenreView> getAllGenres();
	
	List<GenreIdView> getAllGenreIdView();
	
	GenreIdView getGenreIdViewById(Integer id);
	
	void save(Genre genre);
	
	long amountOfGenres();

}
