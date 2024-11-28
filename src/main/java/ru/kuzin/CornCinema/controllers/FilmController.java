package ru.kuzin.CornCinema.controllers;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.expression.Lists;
import org.thymeleaf.expression.Sets;

import jakarta.validation.Valid;
import ru.kuzin.CornCinema.entityView.filmView.FilmFormViewImpl;
import ru.kuzin.CornCinema.entityView.personView.PersonForCreateFilmImpl;
import ru.kuzin.CornCinema.entityView.personView.PersonFormViewImpl;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;
import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaView;
import ru.kuzin.CornCinema.models.Test1;
import ru.kuzin.CornCinema.service.AmpluaService;
import ru.kuzin.CornCinema.service.CountryService;
import ru.kuzin.CornCinema.service.FilmService;
import ru.kuzin.CornCinema.service.GenreService;
import ru.kuzin.CornCinema.service.PersonService;

@Controller
@RequestMapping("/films")
public class FilmController {
	
	private FilmService filmService;
	private AmpluaService ampluaService;
	private PersonService personService;
	private CountryService countryService;
	private GenreService genreService;
	private static final Logger logger = LogManager.getLogger(FilmController.class);
	
	@Autowired
	public void setFilmService(FilmService filmService) {
		this.filmService = filmService;
	}
	@Autowired
	public void setAmpluaService(AmpluaService ampluaService) {
		this.ampluaService = ampluaService;
	}
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	@Autowired
	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}
	@Autowired
	public void setGenreService(GenreService genreService) {
		this.genreService = genreService;
	}
	
	@GetMapping("/personForm")
	public String getPersonForm(Model model) {
		model.addAttribute("ampluaList", ampluaService.getAllAmplua());
		if(!model.containsAttribute("person")) /*flash attribute!!!!!*/
			model.addAttribute("person", personService.getPersonForm());
		return "films/personForm";
	}
	
	@GetMapping("/filmForm")
	public String getFilmForm(Model model) {
		model.addAttribute("countryList", countryService.getAllProducingCountries());
		model.addAttribute("genreList", genreService.getAllGenres());
		model.addAttribute("directorsList", personService.getAllDirectors());
		model.addAttribute("actorsList", personService.getAllActors());
		if(!model.containsAttribute("film"))
			model.addAttribute("film", filmService.getFilmForm());
		return "films/filmForm";
	}
	
	@GetMapping("/filmForm1")
	public String getFilmForm1(Model model) {
		model.addAttribute("countryList", countryService.getAllProducingCountries());
		model.addAttribute("genreList", genreService.getAllGenres());
		model.addAttribute("mapOfPersonsByAmplua", personService.getAllPersonsByAmplua());
		if(!model.containsAttribute("film"))
			model.addAttribute("film", filmService.getFilmForm());
		return "films/filmForm1";
	}
	
	@PostMapping("/createPerson")
	public String createPerson(@ModelAttribute("person") @Valid  PersonFormViewImpl person, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.person", bindingResult);
			redirectAttributes.addFlashAttribute("person", person);
			return "redirect:/films/personForm";
		}
		personService.savePerson(person);
		return "redirect:/films/filmForm1";
	}
	
	@PostMapping("/createFilm")
	public String createFilm(@ModelAttribute("film") @Valid  FilmFormViewImpl film, BindingResult bindingResult, RedirectAttributes redirectAttributes,
							 @RequestParam(name="posterImg", required=false) MultipartFile[] uploadingFiles) {
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.film", bindingResult);
			redirectAttributes.addFlashAttribute("film", film);
			return "redirect:/films/filmForm";
		}
		filmService.saveFilm(film, uploadingFiles);
		return "test";
	}
	
	@PostMapping("/createFilm1")
	public String createFilm1(@ModelAttribute("film") @Valid  FilmFormViewImpl film, BindingResult bindingResult, RedirectAttributes redirectAttributes,
			 				  @RequestParam(name="posterImg", required=false) MultipartFile[] uploadingFiles) {
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.film", bindingResult);
			redirectAttributes.addFlashAttribute("film", film);
			return "redirect:/films/filmForm1";
		}
		filmService.saveFilm(film, uploadingFiles);
		return "test";
	}

}
