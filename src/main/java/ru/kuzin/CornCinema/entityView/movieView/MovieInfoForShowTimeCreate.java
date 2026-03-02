package ru.kuzin.CornCinema.entityView.movieView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingParameter;
import com.blazebit.persistence.view.ViewFilter;

import ru.kuzin.CornCinema.entityView.showTimeView.SimpleShowTimeView;
import ru.kuzin.CornCinema.models.Movie;
import ru.kuzin.CornCinema.utilites.AvailableTimeForShowTime;

@EntityView(Movie.class)
@ViewFilter(name = "inPlayingNowFilter", value = ru.kuzin.CornCinema.entityView.movieView.MovieView.InPlayingNowFilter.class)
public abstract class MovieInfoForShowTimeCreate {
	
	private final Map<LocalDate, Map<Integer, List<LocalTime>>> availableTimeForShowTimeCreate;
	
	public MovieInfoForShowTimeCreate(@Mapping("ShowTime[startTime >= :startDate AND startTime <= :endDate]")
									 List<SimpleShowTimeView> showTimes,
									 @Mapping("duration")
									 LocalTime duration,
									 @MappingParameter("timeForShowTime")
									 AvailableTimeForShowTime service,
									 @MappingParameter("startDate")
									 LocalDateTime startPeriod,
									 @MappingParameter("endDate")
	  								 LocalDateTime endPeriod) {
	
		availableTimeForShowTimeCreate = service.convert(showTimes, duration, startPeriod, endPeriod);
		
	}
	
	@IdMapping
	public abstract Integer getId();
	public abstract String getTitle();
	public abstract LocalTime getDuration();
	
	public Map<LocalDate, Map<Integer, List<LocalTime>>> getAvailableTimeForShowTimeCreate() {
		return availableTimeForShowTimeCreate;
	}

}
