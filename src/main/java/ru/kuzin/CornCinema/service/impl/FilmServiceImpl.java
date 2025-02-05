package ru.kuzin.CornCinema.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.blazebit.persistence.view.ConvertOption;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.PrePersistEntityListener;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.FilmRepository;
import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaIdView;
import ru.kuzin.CornCinema.entityView.filmView.FilmCreateView;
import ru.kuzin.CornCinema.entityView.filmView.FilmFormView;
import ru.kuzin.CornCinema.entityView.filmView.FilmFormViewImpl;
import ru.kuzin.CornCinema.entityView.filmView.FilmInfoForShowTimeCreate;
import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeDuration;
import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Film;
import ru.kuzin.CornCinema.models.FilmPoster;
import ru.kuzin.CornCinema.models.Person;
import ru.kuzin.CornCinema.service.FilmService;
import ru.kuzin.CornCinema.service.ShowTimeService;
import ru.kuzin.CornCinema.utilites.AvailableTimeForShowTime;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;
import ru.kuzin.CornCinema.utilites.ImageUploadService;

@Service
public class FilmServiceImpl implements FilmService {
	
	private EntityManager entityManager;
	private EntityViewManager entityViewManager;
	private FilmRepository filmRepository;
	private ImageUploadService imageUploadService;
	private AvailableTimeForShowTime availableTimeForShowTime;
	private CinemaWorkingHours cinemaWorkingHours;
	private ShowTimeService showTimeService;
	private static final Logger logger = LogManager.getLogger(FilmServiceImpl.class);
	
	@Autowired
	public void setEntityManager(EntityManager entityManager) {this.entityManager = entityManager;}
	@Autowired
	public void setEntityViewManager(EntityViewManager entityViewManager) {this.entityViewManager = entityViewManager;}
	@Autowired
	public void setFilmRepository(FilmRepository filmRepository) {this.filmRepository = filmRepository;}
	@Autowired
	public void setImageUploadService(ImageUploadService imageUploadService) {this.imageUploadService = imageUploadService;}
	@Autowired
	public void setAvailableTimeForShowTime(AvailableTimeForShowTime availableTimeForShowTime) {this.availableTimeForShowTime = availableTimeForShowTime;}
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {this.cinemaWorkingHours = cinemaWorkingHours;}
	@Autowired
	public void setShowTimeService(ShowTimeService showTimeService) {this.showTimeService = showTimeService;}
	
	@Override
	public FilmFormView getFilmForm() {
		FilmFormView film = entityViewManager.create(FilmFormView.class);
		return film;
	}
	
	@Override
	@Transactional
	public void createFilm(FilmFormViewImpl form, MultipartFile[] uploadingFiles) {
		FilmCreateView film = entityViewManager.convert(form, FilmCreateView.class, ConvertOption.CREATE_NEW);
		entityViewManager.saveWith(entityManager, film)
			.onPrePersist(FilmCreateView.class, Film.class, new PrePersistEntityListener<FilmCreateView, Film>() {

				@Override
				public void prePersist(EntityViewManager entityViewManager, EntityManager entityManager,
									   FilmCreateView view, Film entity) {
					
					entity.setInRolling(true);
					
					form.getPersons().forEach(personForCreateFilm -> {
						Person person = entityViewManager.getEntityReference(entityManager, 
								                                             entityViewManager.getReference(PersonIdView.class, personForCreateFilm.getPersonId()));
						Amplua amplua = entityViewManager.getEntityReference(entityManager, 
																			 entityViewManager.getReference(AmpluaIdView.class, personForCreateFilm.getAmpluaId()));
						entity.addPerson(person, amplua);
					});
					
					/*if Set<FilmPoster> posters is empty, than set up default poster from static directory */
					String filmTitle = film.getTitle();
					Arrays.asList(uploadingFiles).forEach(file -> {				
						String uniquePosterTitle = imageUploadService.saveFileAndReturnUniqueTitle(file, filmTitle);
						if(uniquePosterTitle != null) {
							FilmPoster poster = new FilmPoster();
							poster.setTitle(uniquePosterTitle);						
							entity.addPoster(poster);
						}
						
						entity.getPosters().forEach(p -> System.out.println("Poster: " + p.getTitle()));
					});
				}
				
			})
			
			.flush();

	}
	
	@Override
	public void deleteFilm(Integer id) {
		try {
			filmRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<FilmInfoForShowTimeCreate> getAllFilmInfoForShowTimeCreate(LocalDate startPeriod, LocalDate endPeriod) {
		LocalDateTime startDate = LocalDateTime.of(startPeriod, cinemaWorkingHours.getOpeningTime());
		LocalDateTime endDate = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(endPeriod);
		List<ShowTimeDuration> showTimesList = showTimeService.getAllShowTimesBetweenDates(startPeriod, endPeriod);
		return filmRepository.getAllFilmInfoForShowTimeCreate(startDate, endDate, showTimesList, availableTimeForShowTime);
	}
	
	@Override
	public FilmInfoForShowTimeCreate getFilmInfoForShowTimeCreateById(Integer id) {
		LocalDate start = LocalDate.of(2023, 5, 5);
		LocalDate end = LocalDate.of(2023, 5, 7);
		LocalDateTime startDate = LocalDateTime.of(start, cinemaWorkingHours.getOpeningTime());
		LocalDateTime endDate = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(end);
		FilmInfoForShowTimeCreate f = filmRepository.getFilmInfoForShowTimeCreateById(id, startDate, endDate, availableTimeForShowTime);
		logger.info(f.getTitle());
		f.getAvailableTimeForShowTimeCreate().forEach((date, map) -> {
			logger.info("+++ " + date + " +++");
			map.forEach((key, value) -> {
				logger.info("-= Hall #" + key + " =-");
				value.forEach(t -> logger.info(t));
			});
		});
		return null;
	}

}
