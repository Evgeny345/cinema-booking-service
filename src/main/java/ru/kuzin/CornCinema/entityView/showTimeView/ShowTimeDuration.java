package ru.kuzin.CornCinema.entityView.showTimeView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingParameter;

import ru.kuzin.CornCinema.models.ShowTime;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@EntityView(ShowTime.class)
public abstract class ShowTimeDuration implements Comparable<ShowTimeDuration> {
	
	@IdMapping
	public abstract Integer getId();
	@Mapping("film.title")
	public abstract String getFilmTitle();
	@Mapping("hall.id")
	public abstract Integer getHallId();
	@Mapping("film.duration")
	public abstract LocalTime getFilmDuration();
	public abstract LocalDateTime getStartTime();
	private CinemaWorkingHours cinemaWorkingHours;
	
	public ShowTimeDuration(@MappingParameter("cinemaWorkingHours") CinemaWorkingHours cinemaWorkingHours) {
		this.cinemaWorkingHours = cinemaWorkingHours;
	}
	
	/**
     * Get rounded up end of show time for matching with available interval of time list 
     */
	public LocalDateTime getEndTime() {
		LocalTime durationForRound = getFilmDuration();
		long originalFilmDurationInMinutes = durationForRound.getMinute();
		double intervalBetweenShowTimes = cinemaWorkingHours.getIntervalBetweenShowTimes();
		if(originalFilmDurationInMinutes%intervalBetweenShowTimes != 0) {
			long roundedMinutes = (long) (Math.ceil(originalFilmDurationInMinutes/intervalBetweenShowTimes) * intervalBetweenShowTimes);
			LocalTime roundedDuration = durationForRound.minusMinutes(originalFilmDurationInMinutes).plusMinutes(roundedMinutes);
			return getStartTime().plusMinutes(roundedDuration.getLong(ChronoField.MINUTE_OF_DAY));
		}
		return getStartTime().plusMinutes(durationForRound.getLong(ChronoField.MINUTE_OF_DAY));

	}
	
	/**
     * Check whether show time start in same day with schedule for certain day
     * If yes, change start day to matching with schedule
     */
	public LocalDate getStartDay() {
		LocalDate startDayForSchedule = getStartTime().toLocalDate();
		LocalTime startTimeOfShowTime = getStartTime().toLocalTime();
		LocalTime midnight = LocalTime.of(0, 0);
		LocalTime cinemaClose = cinemaWorkingHours.getClosingTime();
		if(startTimeOfShowTime.isAfter(midnight) && startTimeOfShowTime.isBefore(cinemaClose))
			startDayForSchedule = getStartTime().toLocalDate().minusDays(1);
		return startDayForSchedule;
	}
	
	@Override
	public int compareTo(ShowTimeDuration s) {
		return this.getStartTime().compareTo(s.getStartTime());
	}

}
