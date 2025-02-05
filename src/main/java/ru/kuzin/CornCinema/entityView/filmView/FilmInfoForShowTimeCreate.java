package ru.kuzin.CornCinema.entityView.filmView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingParameter;

import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeDuration;
import ru.kuzin.CornCinema.models.Film;
import ru.kuzin.CornCinema.utilites.AvailableTimeForShowTime;

@EntityView(Film.class)
public abstract class FilmInfoForShowTimeCreate {
	
private final Map<LocalDate, Map<Integer, List<LocalTime>>> availableTimeForShowTimeCreate;
	
	public FilmInfoForShowTimeCreate(@MappingParameter("showTimeList")
									  List<ShowTimeDuration> showTimes,
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
