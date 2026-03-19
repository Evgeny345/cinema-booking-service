package ru.kuzin.CornCinema.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.ConvertOption;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.persistence.view.PrePersistEntityListener;
import com.blazebit.persistence.view.PreUpdateListener;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.MovieRepository;
import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaIdView;
import ru.kuzin.CornCinema.entityView.movieView.MovieCreateView;
import ru.kuzin.CornCinema.entityView.movieView.MovieFormView;
import ru.kuzin.CornCinema.entityView.movieView.MovieFormViewImpl;
import ru.kuzin.CornCinema.entityView.movieView.MovieIdView;
import ru.kuzin.CornCinema.entityView.movieView.MovieInfoForShowTimeCreate;
import ru.kuzin.CornCinema.entityView.movieView.MovieView;
import ru.kuzin.CornCinema.entityView.movieView.MovieWithShowTimeView;
import ru.kuzin.CornCinema.entityView.movieView.MovieWithSubqueryView;
import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.exceptions.NotFoundException;
import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Movie;
import ru.kuzin.CornCinema.models.Person;
import ru.kuzin.CornCinema.service.AmpluaService;
import ru.kuzin.CornCinema.service.MovieService;
import ru.kuzin.CornCinema.service.PersonService;
import ru.kuzin.CornCinema.utilites.AvailableTimeForShowTime;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;
import ru.kuzin.CornCinema.utilites.ImageUploadService;

@Service
public class MovieServiceImpl implements MovieService {
	
	private EntityManager entityManager;
	private EntityViewManager entityViewManager;
	private MovieRepository movieRepository;
	private ImageUploadService imageUploadService;
	private AvailableTimeForShowTime availableTimeForShowTime;
	private CinemaWorkingHours cinemaWorkingHours;
	private CriteriaBuilderFactory cbf;
	private PersonService personService;
	private AmpluaService ampluaService;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {this.entityManager = entityManager;}
	@Autowired
	public void setEntityViewManager(EntityViewManager entityViewManager) {this.entityViewManager = entityViewManager;}
	@Autowired
	public void setMovieRepository(MovieRepository movieRepository) {this.movieRepository = movieRepository;}
	@Autowired
	public void setImageUploadService(ImageUploadService imageUploadService) {this.imageUploadService = imageUploadService;}
	@Autowired
	public void setAvailableTimeForShowTime(AvailableTimeForShowTime availableTimeForShowTime) {this.availableTimeForShowTime = availableTimeForShowTime;}
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {this.cinemaWorkingHours = cinemaWorkingHours;}
	@Autowired
	public void setCbf(CriteriaBuilderFactory cbf) {this.cbf = cbf;}
	@Autowired
	public void setPersonService(PersonService personService) {this.personService = personService;}
	@Autowired
	public void setAmpluaService(AmpluaService ampluaService) {this.ampluaService = ampluaService;}

	
	@Override
	public MovieFormView getMovieForm() {
		MovieFormView movie = entityViewManager.create(MovieFormView.class);
		return movie;
	}
	
	@Override
	public MovieFormViewImpl getMovieFormViewImplById(Integer id) {
		MovieFormViewImpl m = (MovieFormViewImpl) entityViewManager.find(entityManager, MovieFormView.class, id);
		m.setPersons(personService.getAllPersonsInMovie(id));
		return m;
	}

	@Override  
	@Transactional
	public void createMovie(MovieFormViewImpl form, MultipartFile poster) {
		MovieCreateView movie = entityViewManager.convert(form, MovieCreateView.class, ConvertOption.CREATE_NEW);
		movie.setInPlayingNow(Optional.ofNullable(movie.getInPlayingNow()).orElse(true));
		
		entityViewManager.saveWith(entityManager, movie)
			.onPrePersist(MovieCreateView.class, Movie.class, new PrePersistEntityListener<MovieCreateView, Movie>() {

				@Override
				public void prePersist(EntityViewManager entityViewManager, EntityManager entityManager,
									   MovieCreateView view, Movie entity) {
					
					form.getPersons().forEach(personForMovie -> {
						Person person = entityViewManager.getEntityReference(entityManager, 
								                                             entityViewManager.getReference(PersonIdView.class, personForMovie.getPersonId()));
						Amplua amplua = entityViewManager.getEntityReference(entityManager, 
																			 entityViewManager.getReference(AmpluaIdView.class, personForMovie.getAmpluaId()));
						entity.addPerson(person, amplua);
					});
				
					if (poster != null && !Objects.equals(poster.getContentType(), "application/octet-stream")) {
						/*if Set<FilmPoster> posters is empty, than set up default poster from static directory */
						String movieTitle = movie.getTitle();
						String uniquePosterTitle = imageUploadService.saveFileAndReturnUniqueTitle(poster, movieTitle);
						view.setPoster(uniquePosterTitle);
					}
					
				}
				
			})
			
			.flush();

	}
	
	@Override
	@Transactional
	public void updateMovie(MovieFormViewImpl movieForm, MultipartFile poster) {

		MovieCreateView movieToUpdate = entityViewManager.convert(movieForm, MovieCreateView.class);
		String titleFromDB = cbf.create(entityManager, String.class)
								.from(Movie.class).select("title")
								.where("id").eq(movieToUpdate.getId()).getResultStream().findFirst().get();

		if (!(movieForm.getPoster() == null) && !Objects.equals(movieForm.getTitle(), titleFromDB))
			imageUploadService.createDirectoryAndMoveFileToIt(movieForm.getTitle(), titleFromDB, movieForm.getPoster());
		Movie entity = entityManager.find(Movie.class, movieToUpdate.getId());
		entityViewManager.saveFullWith(entityManager, movieToUpdate)
			.onPreUpdate(MovieCreateView.class, new PreUpdateListener<MovieCreateView>() {

				@Override
				public void preUpdate(EntityViewManager entityViewManager, EntityManager entityManager,
								   	  MovieCreateView view) {
						
					entity.getPersons().removeIf(p -> !movieForm.personIsExists(p.getPerson().getId(), p.getAmplua().getId()));
					List<Amplua> ampluaList = ampluaService.getAllAmplua();
					List<Person> personList = personService.getAllPersons();
					movieForm.getPersons().stream().forEach(personForMovie -> {
						if(!entity.personIsExists(personForMovie.getPersonId(), personForMovie.getAmpluaId())) {
							Person person = personList.stream().filter(p -> p.getId().equals(personForMovie.getPersonId()))
															    .findFirst().orElseThrow(() -> 
																new NotFoundException("Person not found"));
							Amplua amplua = ampluaList.stream().filter(a -> a.getId().equals(personForMovie.getAmpluaId()))
															   .findFirst().orElseThrow(() -> 
															   	new NotFoundException("Amplua not found"));
							entity.addPerson(person, amplua);
						}
						
					});
				
					if (poster != null && !Objects.equals(poster.getContentType(), "application/octet-stream")) {
					/*if Set<FilmPoster> posters is empty, than set up default poster from static directory */
					String movieTitle = movieForm.getTitle();
					String uniquePosterTitle = imageUploadService.saveFileAndReturnUniqueTitle(poster, movieTitle);
					view.setPoster(uniquePosterTitle);
				}
					
			}
			
		})
		
		.flush();
		
	}
	
	@Override
	public void deleteMovieById(Integer id) {
		try {
			Movie movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Movie with id %d not found", id)));
			imageUploadService.deletePosterDirectory(movie.getTitle());
			movieRepository.delete(movie);
		} catch(NotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<MovieInfoForShowTimeCreate> getAllMovieInfoForShowTimeCreate(LocalDate startPeriod, LocalDate endPeriod) {
		LocalDateTime startDate = LocalDateTime.of(startPeriod, cinemaWorkingHours.getOpeningTime());
		LocalDateTime endDate = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(endPeriod);
		return movieRepository.getAllMovieInfoForShowTimeCreate(startDate, endDate, availableTimeForShowTime, processor -> processor.withViewFilter("inPlayingNowFilter"));
	}
	
	@Override
	public List<MovieView> getAllMoviesInPlaying() {
		EntityViewSetting<MovieView, CriteriaBuilder<MovieView>> settings = createEntityViewSetting();
		settings.addViewFilter("inPlayingNowFilter");
		settings.fetch("title");
		settings.fetch("ageRating");
		settings.fetch("poster");		
		List<MovieView> list = entityViewManager.applySetting(settings, createCriteriaBuilder()).getResultList();
		return list;
	}
	
	/**
	 * Method, which get list of all existing movies from DB, for set or remove isPlayingNow attribute.
	 * LocalDate startPeriod attribute is need to control whether remove movie from showing, 
	 * if show times since this date are exist, then its impossible
	 * So, list of available to showing movies is generated.
	 */
	
	@Override
	public List<MovieWithSubqueryView> getAllExistingAndAvailableMovies(LocalDate startPeriod) {
		LocalDateTime date = LocalDateTime.of(startPeriod, cinemaWorkingHours.getOpeningTime());
		EntityViewSetting<MovieWithSubqueryView, CriteriaBuilder<MovieWithSubqueryView>> settings = EntityViewSetting.create(MovieWithSubqueryView.class);
		settings.withOptionalParameter("showTimesExistSince", date);
		settings.fetch("title");
		settings.fetch("inPlayingNow");
		settings.fetch("poster");
		settings.fetch("showTimesAmountSinceDate");
		List<MovieWithSubqueryView> list = entityViewManager.applySetting(settings, createCriteriaBuilder()).getResultList();
		return list;
	}
	
	@Override
	public void deleteAll() {
		List<Integer> list = movieRepository.findAll().stream().map(f -> f.getId()).toList();
		list.forEach(i -> deleteMovieById(i));
	}
	
	@Override
	public MovieWithShowTimeView getMovieDescription(Integer id, LocalDate startPeriod, LocalDate endPeriod) throws NotFoundException {
		LocalDateTime startDate = LocalDateTime.of(startPeriod, cinemaWorkingHours.getOpeningTime());
		LocalDateTime endDate = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(endPeriod);
		return movieRepository.findMovieWithShowTimeViewById(id, startDate, endDate, cinemaWorkingHours)
				.orElseThrow(() -> new NotFoundException(String.format("Movie with id %d not found", id)));
		
		
								
	}
	
	@Override
	@Transactional
	public void checkAndSetUpMovieToPlaying(Map<String, String> moviesIsPlayingNow) {
		List<MovieCreateView> list = movieRepository.getAllMovieCreateView().stream().sorted((f1, f2) -> f1.getId().compareTo(f2.getId())).toList();
		Map<Integer, Boolean> idAndIsShowingNowMap = moviesIsPlayingNow.entrySet().stream().filter(e -> !e.getKey().contains("csrf")).collect(Collectors.toMap(k -> Integer.parseInt(k.getKey()), v -> Boolean.parseBoolean(v.getValue()),(k1, k2) -> k1, TreeMap::new));

		idAndIsShowingNowMap.forEach((id, isPlayingNow) -> {
			list.stream().filter(f -> (f.getId().intValue() == id.intValue() && !Objects.equals(f.getInPlayingNow(), isPlayingNow)))
						 .forEach((f) -> {
							 f.setInPlayingNow(isPlayingNow);
							 entityViewManager.save(entityManager, f);
						 });
		});
	}
	
	@Override
	public String getPosterDirectory(Integer movieId) {
		return movieRepository.getMovieFormViewById(movieId).getTitle().replaceAll("\\s+","");
	}
	
	@Override
	public void deletePoster(String directoryName, Integer movieId) {
		MovieCreateView movie = movieRepository.getMovieCreateViewById(movieId);
		movie.setPoster(null);
		movieRepository.save(movie);
		imageUploadService.deletePosterDirectory(directoryName);
	}
	
	@Override
	public long amountOfMovies() {
		return movieRepository.count();
	}
	
	@Override
	public MovieIdView getMovieIdView(Integer id) {
		return movieRepository.getMovieIdViewById(id);
	}
	
	private EntityViewSetting<MovieView, CriteriaBuilder<MovieView>> createEntityViewSetting() {
		return EntityViewSetting.create(MovieView.class);	
	}
	
	private CriteriaBuilder<Movie> createCriteriaBuilder() {
		CriteriaBuilder<Movie> cb = cbf.create(entityManager, Movie.class);
		return cb;
	}
	
	
}
