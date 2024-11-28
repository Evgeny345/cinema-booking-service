package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.genreView.GenreIdView;
import ru.kuzin.CornCinema.entityView.genreView.GenreView;

public interface GenreService {
	
	List<GenreView> getAllGenres();
	GenreIdView getGenreIdViewById(Integer id);

}
