package ru.kuzin.CornCinema.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazebit.persistence.spring.data.annotation.OptionalParam;

import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeDuration;
import ru.kuzin.CornCinema.models.ShowTime;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer> {
	
	List<ShowTimeDuration> findAllShowTimeDurationByStartTimeBetweenOrderByStartTimeAsc(LocalDateTime startDate, LocalDateTime endDate, 
																						@OptionalParam("cinemaWorkingHours") CinemaWorkingHours cinemaWorkingHours);
	
	ShowTimeDuration getShowTimeDurationById(Integer id, @OptionalParam("cinemaWorkingHours") CinemaWorkingHours cinemaWorkingHours);
	
}
