package ru.kuzin.CornCinema.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.entityView.countryView.CountryIdView;
import ru.kuzin.CornCinema.entityView.genreView.GenreIdView;
import ru.kuzin.CornCinema.entityView.movieView.MovieFormViewImpl;
import ru.kuzin.CornCinema.entityView.personView.PersonForCreateMovieImpl;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;

import ru.kuzin.CornCinema.entityView.userView.UserFormViewImpl;
import ru.kuzin.CornCinema.models.AgeRating;
import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Country;
import ru.kuzin.CornCinema.models.Genre;
import ru.kuzin.CornCinema.models.Person;
import ru.kuzin.CornCinema.service.AmpluaService;
import ru.kuzin.CornCinema.service.CountryService;
import ru.kuzin.CornCinema.service.GenreService;
import ru.kuzin.CornCinema.service.MovieService;
import ru.kuzin.CornCinema.service.PersonService;
import ru.kuzin.CornCinema.service.ShowTimeService;
import ru.kuzin.CornCinema.service.UserService;

@Component
public class DataLoader implements CommandLineRunner {
	
	private CountryService countryService;
	private GenreService genreService;
	private UserService userService;
	private AmpluaService ampluaService;
	private PersonService personService;
	private MovieService movieService;
	private ShowTimeService showTimeService;
	private static final Random random = new Random();
	
	@Autowired
	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}
	@Autowired
	public void setGenreService(GenreService genreService) {
		this.genreService = genreService;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
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
	public void setMovieService(MovieService movieService) {
		this.movieService = movieService;
	}
	@Autowired
	public void setShowTimeService(ShowTimeService showTimeService) {
		this.showTimeService = showTimeService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		LocalDate startPeriod = LocalDate.now();
		LocalDate endPeriod = startPeriod.plusDays(3);
		if(countryService.amountOfCountries() == 0)
			createCountries();
		
		if(genreService.amountOfGenres() == 0)
			createGenres();
		
		if(userService.amountOfUsers() == 0)
			createUsers();
		
		if(personService.amountOfPersons() == 0)
			createPersons();
		
		if(movieService.amountOfMovies() == 0)
			createMovies();
		
		showTimeService.createRandomSchedule(startPeriod, endPeriod);
	
	}
	
	private void createCountries() {
		countryService.save(new Country("United States"));
		countryService.save(new Country("Russia"));
		countryService.save(new Country("Japan"));
		countryService.save(new Country("Italy"));
		countryService.save(new Country("Mexico"));
		countryService.save(new Country("France"));
		countryService.save(new Country("Germany"));
	}
	
	private void createGenres() {
		genreService.save(new Genre("thriller"));
		genreService.save(new Genre("action movie"));
		genreService.save(new Genre("family movie"));
		genreService.save(new Genre("western"));
		genreService.save(new Genre("sci-fi"));
		genreService.save(new Genre("anime"));
		genreService.save(new Genre("animation"));
		genreService.save(new Genre("comedy"));
		genreService.save(new Genre("romcom"));
		genreService.save(new Genre("melodrama"));
	}
	
	private void createUsers() {
		UserFormViewImpl regularUser = new UserFormViewImpl();
		regularUser.setUserName("User");
		regularUser.setEmail("plainUser@gmail.com");
		regularUser.setPassword("100");
		userService.createUser(regularUser, "ROLE_USER");
		UserFormViewImpl adminUser = new UserFormViewImpl();
		adminUser.setUserName("Admin");
		adminUser.setEmail("adminUser@gmail.com");
		adminUser.setPassword("100");
		userService.createUser(adminUser, "ROLE_ADMIN");
	}
	
	private void createPersons() {
		List<Person> personsList = new ArrayList<>();
		Amplua director = ampluaService.getAmpluaByProfession("Director");
		Amplua actor = ampluaService.getAmpluaByProfession("Actor");
		Person person1 = new Person();
		person1.setName("David");
		person1.setLastName("Warner");
		person1.setDateOfBirth(LocalDate.of(1968, 12, 17));
		person1.getAmpluas().add(director);
		personsList.add(person1);
		Person person2 = new Person();
		person2.setName("Andrew");
		person2.setLastName("Jacobs");
		person2.setDateOfBirth(LocalDate.of(1970, 2, 7));
		person2.getAmpluas().add(director);
		person2.getAmpluas().add(actor);
		personsList.add(person2);
		Person person3 = new Person();
		person3.setName("Richard");
		person3.setLastName("Ross");
		person3.setDateOfBirth(LocalDate.of(1988, 5, 13));
		person3.getAmpluas().add(director);
		personsList.add(person3);
		Person person4 = new Person();
		person4.setName("Carole");
		person4.setLastName("Smith");
		person4.setDateOfBirth(LocalDate.of(1979, 8, 19));
		person4.getAmpluas().add(director);
		personsList.add(person4);
		Person person5 = new Person();
		person5.setName("Brandy");
		person5.setLastName("Johanson");
		person5.setDateOfBirth(LocalDate.of(2001, 5, 5));
		person5.getAmpluas().add(director);
		personsList.add(person5);
		Person person6 = new Person();
		person6.setName("Elizabeth");
		person6.setLastName("Brown");
		person6.setDateOfBirth(LocalDate.of(1973, 1, 17));
		person6.getAmpluas().add(actor);
		personsList.add(person6);
		Person person7 = new Person();
		person7.setName("Doris");
		person7.setLastName("Sims");
		person7.setDateOfBirth(LocalDate.of(1997, 11, 1));
		person7.getAmpluas().add(actor);
		personsList.add(person7);
		Person person8 = new Person();
		person8.setName("Shawn");
		person8.setLastName("Higgins");
		person8.setDateOfBirth(LocalDate.of(2004, 11, 2));
		person8.getAmpluas().add(actor);
		personsList.add(person8);
		Person person9 = new Person();
		person9.setName("Ana");
		person9.setLastName("Jones");
		person9.setDateOfBirth(LocalDate.of(1981, 9, 21));
		person9.getAmpluas().add(actor);
		personsList.add(person9);
		Person person10 = new Person();
		person10.setName("Jose");
		person10.setLastName("Hanson");
		person10.setDateOfBirth(LocalDate.of(1967, 12, 25));
		person10.getAmpluas().add(actor);
		personsList.add(person10);
		Person person11 = new Person();
		person11.setName("Teresa");
		person11.setLastName("Baker");
		person11.setDateOfBirth(LocalDate.of(1971, 3, 11));
		person11.getAmpluas().add(actor);
		personsList.add(person11);
		Person person12 = new Person();
		person12.setName("Melanie");
		person12.setLastName("Evans");
		person12.setDateOfBirth(LocalDate.of(1994, 6, 11));
		person12.getAmpluas().add(actor);
		personsList.add(person12);
		Person person13 = new Person();
		person13.setName("James");
		person13.setLastName("Malone");
		person13.setDateOfBirth(LocalDate.of(1984, 8, 26));
		person13.getAmpluas().add(actor);
		personsList.add(person13);
		Person person14 = new Person();
		person14.setName("Robert");
		person14.setLastName("Clark");
		person14.setDateOfBirth(LocalDate.of(1964, 7, 12));
		person14.getAmpluas().add(actor);
		personsList.add(person14);
		personService.createListPersons(personsList);
	}
	
	private void createMovies() {
		List<PersonFullNameView> directorsList = personService.getAllDirectors();
		List<PersonFullNameView> actorsList = personService.getAllActors();
		IntStream.range(1, 9).forEach(x -> createMovie(x, directorsList, actorsList));
	}
	
	private void createMovie(Integer x, List<PersonFullNameView> directorsList, List<PersonFullNameView> actorsList) {
		MovieFormViewImpl movie = new MovieFormViewImpl();	
		movie.setTitle("Movie " + x);
		movie.setDuration(LocalTime.of(random.nextInt(1, 3), random.nextInt(5) * 10));
		movie.setAgeRating(AgeRating.randomAgeRating());
		IntStream.range(1, random.nextInt(2, 3)).forEach((i) -> {
			movie.getGenres().add(getGenre());
		});
		movie.getCountries().add(getCountry());
		movie.getPersons().add(new PersonForCreateMovieImpl(directorsList.get(random.nextInt(0, directorsList.size() - 1)).getId(), 1));
		IntStream.range(1, actorsList.size() - 1).forEach((i) -> {
			movie.getPersons().add(new PersonForCreateMovieImpl(actorsList.get(random.nextInt(0, actorsList.size() - 1)).getId(), 2));
		});
		movieService.createMovie(movie, null);
	}
	
	private GenreIdView getGenre() {
		List<GenreIdView> genresList = genreService.getAllGenreIdView();
		return genresList.get(random.nextInt(0, genresList.size() - 1));
	}
	
	private CountryIdView getCountry() {
		List<CountryIdView> countriesList = countryService.getAllCountryIdView();
		return countriesList.get(random.nextInt(0, countriesList.size() - 1));
	}

}
