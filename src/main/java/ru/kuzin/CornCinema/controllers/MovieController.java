package ru.kuzin.CornCinema.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import ru.kuzin.CornCinema.entityView.movieView.MovieFormViewImpl;
import ru.kuzin.CornCinema.entityView.personView.PersonFormViewImpl;
import ru.kuzin.CornCinema.exceptions.NotFoundException;
import ru.kuzin.CornCinema.service.AmpluaService;
import ru.kuzin.CornCinema.service.CountryService;
import ru.kuzin.CornCinema.service.MovieService;
import ru.kuzin.CornCinema.service.GenreService;
import ru.kuzin.CornCinema.service.PersonService;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@Controller
@RequestMapping("/movies")
public class MovieController {
	
	private MovieService movieService;
	private AmpluaService ampluaService;
	private PersonService personService;
	private CountryService countryService;
	private GenreService genreService;
	private CinemaWorkingHours cinemaWorkingHours;
	
	@Autowired
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
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
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {
		this.cinemaWorkingHours = cinemaWorkingHours;
	}
	
	@GetMapping("/admin/personForm")
	public String getPersonForm(Model model) {
		model.addAttribute("ampluaList", ampluaService.getAllAmplua());
		if(!model.containsAttribute("person"))
			model.addAttribute("person", personService.getPersonForm());
		return "movies/personForm";
	}
	
	@GetMapping("/admin/movieForm")
	public String getMovieCreateForm(Model model) {
		model.addAttribute("countryList", countryService.getAllProducingCountries());
		model.addAttribute("genreList", genreService.getAllGenres());
		model.addAttribute("mapOfPersonsByAmplua", personService.getAllPersonsByAmplua());
		if(!model.containsAttribute("movie"))
			model.addAttribute("movie", movieService.getMovieForm());
		return "movies/movieCreateForm";
	}
	
	@PostMapping("/admin/createPerson")
	public String createPerson(@ModelAttribute("person") @Valid PersonFormViewImpl person, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.person", bindingResult);
			redirectAttributes.addFlashAttribute("person", person);
			return "redirect:/movies/admin/personForm";
		}
		personService.createPerson(person);
		return "redirect:/movies/admin/movieForm";
	}
	
	@PostMapping("/admin/createMovie")
	public String createMovie(@ModelAttribute("movie") @Valid  MovieFormViewImpl movie, BindingResult bindingResult, RedirectAttributes redirectAttributes,
							  @RequestParam(name="posterImg", required=false) MultipartFile poster) {
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movie", bindingResult);
			redirectAttributes.addFlashAttribute("movie", movie);
			return "redirect:/movies/admin/movieForm";
		}
		movieService.createMovie(movie, poster);
		return "redirect:/movies/admin/allExistingMovies";
	}
	
	@PostMapping("/admin/updateMovie")
	public String updateMovie(@ModelAttribute("movie") @Valid  MovieFormViewImpl movie, BindingResult bindingResult, RedirectAttributes redirectAttributes,
							  @RequestParam(name="posterImg", required=false) MultipartFile poster) {
		if(bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movie", bindingResult);
			redirectAttributes.addFlashAttribute("movie", movie);
			redirectAttributes.addFlashAttribute("posterDirectory", movieService.getPosterDirectory(movie.getId()));
			return "redirect:/movies/admin/update/" + movie.getId();
		}
		movieService.updateMovie(movie, poster);
		return "redirect:/movies/admin/allExistingMovies";
	}
	
	@PostMapping("/admin/deletePoster")
	public String deletePoster(@RequestParam(name="posterDirectory") String posterDirectory, @RequestParam(name="movieId") Integer movieId) {
		movieService.deletePoster(posterDirectory, movieId);
		return "redirect:/movies/admin/update/" + movieId;
	}
	
	@PostMapping("/admin/delete/{movieId}")
	public String deleteMovie(@PathVariable Integer movieId) {
		movieService.deleteMovieById(movieId);
		return "redirect:/movies/admin/allExistingMovies";
	}
	
	@GetMapping("/admin/update/{movieId}")
	public String getMovieUpdateForm(@PathVariable Integer movieId, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("countryList", countryService.getAllProducingCountries());
		model.addAttribute("genreList", genreService.getAllGenres());
		model.addAttribute("mapOfPersonsByAmplua", personService.getAllPersonsByAmplua());
		if(!model.containsAttribute("movie")) {
			MovieFormViewImpl movie = movieService.getMovieFormViewImplById(movieId);
			model.addAttribute("movie", movie);
			model.addAttribute("posterDirectory", movie.getTitle().replaceAll("\\s+",""));
		}
		return "movies/movieUpdateForm";
	}
	
	@PostMapping("/admin/setUpMoviesToPlaying")
	public String setUpMoviesToShowing(@RequestParam Map<String, String> moviesIsShowingNow) {
		movieService.checkAndSetUpMovieToPlaying(moviesIsShowingNow);
		return "redirect:/movies/admin/allExistingMovies";
	}
	
	@GetMapping("/admin/allExistingMovies")
	public String moviesManagement(Model model) {
		model.addAttribute("moviesList", movieService.getAllExistingAndAvailableMovies(LocalDate.now()));
		return "movies/allExistingMoviesList";
	}
	
	@GetMapping("/movie/{movieId}")
	public String getMovieDescription(@PathVariable Integer movieId, Model model) {
		LocalDate startShowingDate = LocalDate.now();
		LocalDate endShowingDate = startShowingDate.plusDays(3);
		
		try {
			model.addAttribute("film", movieService.getMovieDescription(movieId, startShowingDate, endShowingDate));
		} catch (NotFoundException ex){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
		
		model.addAttribute("datesList", startShowingDate.datesUntil(endShowingDate.plusDays(1L)).toList());
		model.addAttribute("timeLine", cinemaWorkingHours.getTimeLine());
		
		return "movies/movieDescription";
	}
	
}
