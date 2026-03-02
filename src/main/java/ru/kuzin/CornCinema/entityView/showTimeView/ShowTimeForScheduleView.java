package ru.kuzin.CornCinema.entityView.showTimeView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.MappingParameter;

import ru.kuzin.CornCinema.entityView.movieView.MovieView;
import ru.kuzin.CornCinema.models.ShowTime;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@EntityView(ShowTime.class)
public abstract class ShowTimeForScheduleView extends SimpleShowTimeView {
	
	public abstract MovieView getMovie();
	private CinemaWorkingHours cinemaWorkingHours;
	
	public ShowTimeForScheduleView(@MappingParameter("cinemaWorkingHours") CinemaWorkingHours cinemaWorkingHours) {
		this.cinemaWorkingHours = cinemaWorkingHours;
	}
	
	/**
     * Check whether show time start in same day with schedule for certain day
     * If yes, change start day to matching with schedule
     */
	public LocalDate getStartDayForSchedule() {
		
		LocalDate startDayForSchedule = getStartTime().toLocalDate();
		LocalTime startTimeOfShowTime = getStartTime().toLocalTime();
		LocalTime midnight = LocalTime.of(0, 0);
		if(startTimeOfShowTime.isAfter(midnight) && startTimeOfShowTime.isBefore(cinemaWorkingHours.getClosingTime()))
			startDayForSchedule = getStartTime().toLocalDate().minusDays(1);
		if(startTimeOfShowTime.equals(midnight))
			startDayForSchedule = cinemaWorkingHours.checkOpenAndCloseDuringOneDay()? startDayForSchedule: getStartTime().toLocalDate().minusDays(1);
		return startDayForSchedule;
	}
	
	public LocalDateTime getEndTime() {		
		return getStartTime().plusMinutes(getMovie().getDuration().getLong(ChronoField.MINUTE_OF_DAY));		
	}

}
