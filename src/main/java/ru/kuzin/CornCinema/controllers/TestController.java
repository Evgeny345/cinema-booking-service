package ru.kuzin.CornCinema.controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ru.kuzin.CornCinema.service.FilmService;
import ru.kuzin.CornCinema.service.HallService;
import ru.kuzin.CornCinema.service.PersonService;
import ru.kuzin.CornCinema.service.ShowTimeService;

@Controller
public class TestController {
	
	private ShowTimeService showTimeService;
	private PersonService personservice;
	private FilmService filmService;
	private HallService hallService;

	@Autowired
	public void setService(ShowTimeService showTimeService) {this.showTimeService = showTimeService;}
	@Autowired
	public void setPersonservice(PersonService personservice) {this.personservice = personservice;}
	@Autowired
	public void setFilmService(FilmService filmService) {this.filmService = filmService;}
	@Autowired
	public void setHallService(HallService hallService) {this.hallService = hallService;}
	
	@GetMapping
	public String test() {
		filmService.getFilmInfoForShowTimeCreateById(1);
		return "test";
	}

}
