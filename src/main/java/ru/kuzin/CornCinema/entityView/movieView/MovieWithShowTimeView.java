package ru.kuzin.CornCinema.entityView.movieView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingIndex;
import com.blazebit.persistence.view.MappingParameter;
import com.blazebit.persistence.view.PostLoad;

import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaForMovieView;
import ru.kuzin.CornCinema.entityView.showTimeView.SimpleShowTimeView;
import ru.kuzin.CornCinema.models.AgeRating;
import ru.kuzin.CornCinema.models.Movie;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@EntityView(Movie.class)
public abstract class MovieWithShowTimeView {
	
	private Map<LocalDate, List<SimpleShowTimeView>> showTimesByDate;
	
	@IdMapping
	public abstract Integer getId();
	public abstract String getTitle();
	public abstract String getDescription();
	public abstract LocalTime getDuration();
	public abstract String getPoster();
	public abstract AgeRating getAgeRating();
	@Mapping("genres.title")
	public abstract Set<String> getGenres();
	@Mapping("countries.name")
	public abstract Set<String> getCountries();
	@MappingIndex("amplua.profession")
    @Mapping("persons")
	public abstract Map<String, Set<PersonWithAmpluaForMovieView>> getPersonsByAmplua();
	@Mapping("ShowTime[movie.id = VIEW(id) AND startTime >= :startDate AND startTime <= :endDate]")							
	public abstract Set<SimpleShowTimeView> getShowTimes();
	@MappingParameter("cinemaWorkingHours")
	public abstract CinemaWorkingHours getCinemaWorkingHours();
	@MappingParameter("startDate")
	public abstract LocalDateTime getStartPeriod();
	@MappingParameter("endDate")
	public abstract LocalDateTime getEndPeriod();
	
	public Map<LocalDate, List<SimpleShowTimeView>> getShowTimesByDate() {
		return showTimesByDate;
	}
	
	@PostLoad
	void init() {
	    this.showTimesByDate = getShowTimes().stream()
				.peek(s -> s.setStartDayForSchedule(getCinemaWorkingHours().getStartDayForSchedule(s.getStartTime())))
				.sorted()
				.collect(Collectors.groupingBy(SimpleShowTimeView::getStartDayForSchedule, TreeMap::new, Collectors.toList()));
		
	    	getStartPeriod().toLocalDate().datesUntil(getEndPeriod().toLocalDate().plusDays(1L))
				.filter(date -> !showTimesByDate.containsKey(date)).forEach((mismatchDate) -> {
					showTimesByDate.put(mismatchDate, new ArrayList<>());
				}); 
	}

}
