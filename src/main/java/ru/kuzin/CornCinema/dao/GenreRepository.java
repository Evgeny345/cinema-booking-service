package ru.kuzin.CornCinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.entityView.genreView.GenreIdView;
import ru.kuzin.CornCinema.entityView.genreView.GenreView;
import ru.kuzin.CornCinema.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
	
	List<GenreView> getAllGenreView();
	GenreIdView getGenreIdViewById(Integer id);

}
