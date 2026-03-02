package ru.kuzin.CornCinema.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ru.kuzin.CornCinema.entityView.movieView.MovieView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeForScheduleView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeFormViewImpl;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeView;

public interface ShowTimeService {
	
	boolean deleteShowTime(Integer id);
	
	Map<LocalDate, List<ShowTimeForScheduleView>> getAllShowTimesBetweenDatesByDate(LocalDate startPeriod, LocalDate endPeriod);
	
	List<ShowTimeForScheduleView> getAllShowTimesBetweenDates(LocalDate startPeriod, LocalDate endPeriod);
	
	ShowTimeFormViewImpl getShowTimeForm();
	
	void createShowTime(ShowTimeFormViewImpl showTimeForm);
	
	Map<MovieView, Map<LocalDate, List<ShowTimeForScheduleView>>> getAllShowTimesByMovieAndDate(LocalDate startPeriod, LocalDate endPeriod);
	
	ShowTimeView getShowTimeViewById(Integer id);
	
	void createRandomSchedule(LocalDate startPeriod, LocalDate endPeriod);

}
