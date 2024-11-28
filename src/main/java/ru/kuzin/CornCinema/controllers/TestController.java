package ru.kuzin.CornCinema.controllers;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ru.kuzin.CornCinema.service.FilmService;
import ru.kuzin.CornCinema.service.PersonService;
import ru.kuzin.CornCinema.service.ShowTimeService;

@Controller
public class TestController {
	
	private ShowTimeService showTimeService;
	private PersonService personservice;
	private FilmService filmService;

	@Autowired
	public void setService(ShowTimeService showTimeService) {this.showTimeService = showTimeService;}
	@Autowired
	public void setPersonservice(PersonService personservice) {this.personservice = personservice;}
	@Autowired
	public void setFilmService(FilmService filmService) {this.filmService = filmService;}
	
	@GetMapping
	public String test() {
		filmService.deleteFilm(26);
		return "test";
	}

}
