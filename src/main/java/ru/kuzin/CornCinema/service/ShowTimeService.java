package ru.kuzin.CornCinema.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeDuration;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeFormViewImpl;

public interface ShowTimeService {
	
	boolean deleteShowTime(Integer id);
	Map<LocalDate, List<ShowTimeDuration>> getAllShowTimesBetweenDatesByDate(LocalDate startPeriod, LocalDate endPeriod);
	List<ShowTimeDuration> getAllShowTimesBetweenDates(LocalDate startPeriod, LocalDate endPeriod);
	ShowTimeFormViewImpl getShowTimeForm();
	void createShowTime(ShowTimeFormViewImpl showTimeForm);
	ShowTimeDuration getShowTimeDurationById(Integer id);

}
