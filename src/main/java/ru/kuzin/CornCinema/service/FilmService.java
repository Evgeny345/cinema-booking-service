package ru.kuzin.CornCinema.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ru.kuzin.CornCinema.entityView.filmView.FilmFormView;
import ru.kuzin.CornCinema.entityView.filmView.FilmFormViewImpl;
import ru.kuzin.CornCinema.entityView.filmView.FilmInfoForShowTimeCreate;

public interface FilmService {
	
	FilmFormView getFilmForm();
	void createFilm(FilmFormViewImpl form, MultipartFile[] uploadingFiles);
	void deleteFilm(Integer id);
	List<FilmInfoForShowTimeCreate> getAllFilmInfoForShowTimeCreate(LocalDate startPeriod, LocalDate endPeriod);
	FilmInfoForShowTimeCreate getFilmInfoForShowTimeCreateById(Integer id);

}
