package ru.kuzin.CornCinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.kuzin.CornCinema.dao.GenreRepository;
import ru.kuzin.CornCinema.entityView.genreView.GenreIdView;
import ru.kuzin.CornCinema.entityView.genreView.GenreView;
import ru.kuzin.CornCinema.models.Genre;
import ru.kuzin.CornCinema.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	private GenreRepository genreRepository;
	
	@Autowired
	public void setGenreRepository(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	@Override
	@Cacheable("genresList")
	public List<GenreView> getAllGenres() {
		return genreRepository.getAllGenreView();
	}
	
	@Override
	@Cacheable("genresIdList")
	public List<GenreIdView> getAllGenreIdView() {
		return genreRepository.getAllGenreIdView();
	}

	@Override
	public GenreIdView getGenreIdViewById(Integer id) {
		return genreRepository.getGenreIdViewById(id);
	}

	@Override
	public void save(Genre genre) {
		genreRepository.save(genre);
	}

	@Override
	public long amountOfGenres() {
		return genreRepository.count();
	}

}
