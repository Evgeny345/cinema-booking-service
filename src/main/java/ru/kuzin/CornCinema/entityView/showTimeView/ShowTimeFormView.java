package ru.kuzin.CornCinema.entityView.showTimeView;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;

import ru.kuzin.CornCinema.entityView.filmView.FilmIdView;
import ru.kuzin.CornCinema.entityView.hallView.HallIdView;
import ru.kuzin.CornCinema.models.ShowTime;

@EntityView(ShowTime.class)
@CreatableEntityView
public interface ShowTimeFormView extends ShowTimeIdView {
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	LocalDateTime getStartTime();
	HallIdView getHall();
	FilmIdView getFilm();
	
	public void setStartTime(LocalDateTime startTime);
	public void setHall(HallIdView hall);
	public void setFilm(FilmIdView film);

}
