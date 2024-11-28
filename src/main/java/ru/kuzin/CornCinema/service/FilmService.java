package ru.kuzin.CornCinema.service;

import org.springframework.web.multipart.MultipartFile;

import ru.kuzin.CornCinema.entityView.filmView.FilmFormView;
import ru.kuzin.CornCinema.entityView.filmView.FilmFormViewImpl;

public interface FilmService {
	
	FilmFormView getFilmForm();
	void saveFilm(FilmFormViewImpl form, MultipartFile[] uploadingFiles);
	void deleteFilm(Integer id);

}
