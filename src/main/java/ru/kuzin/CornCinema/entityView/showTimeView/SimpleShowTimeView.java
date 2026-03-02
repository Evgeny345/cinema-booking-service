package ru.kuzin.CornCinema.entityView.showTimeView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.ShowTime;

@EntityView(ShowTime.class)
public abstract class SimpleShowTimeView implements Comparable<SimpleShowTimeView> {
	
	@IdMapping
	public abstract Integer getId();
	@Mapping("hall.id")
	public abstract Integer getHallId();
	@Mapping("LOWER(SUBSTRING(hall.name, 0, LENGTH(hall.name)-4))")
	public abstract String getShortHallName();
	public abstract LocalDateTime getStartTime();
	@Mapping("movie.duration")
	public abstract LocalTime getMovieDuration();
	
	private LocalDate startDayForSchedule;
	
	
	public LocalDate getStartDayForSchedule() {
		return startDayForSchedule;
	}
	
	public void setStartDayForSchedule(LocalDate startDayForSchedule) {
		this.startDayForSchedule = startDayForSchedule;
	}
	
	@Override
	public int compareTo(SimpleShowTimeView s) {
		return this.getStartTime().compareTo(s.getStartTime());
	}

}
