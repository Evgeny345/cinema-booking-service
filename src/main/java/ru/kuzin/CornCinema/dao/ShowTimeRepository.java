package ru.kuzin.CornCinema.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazebit.persistence.spring.data.annotation.OptionalParam;

import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeForScheduleView;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeView;
import ru.kuzin.CornCinema.models.ShowTime;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer> {
	
	Optional<ShowTimeView> getShowTimeViewById(Integer id);
	
	List<ShowTimeForScheduleView> findAllShowTimeForScheduleViewByStartTimeBetweenOrderByStartTimeAsc(
								  LocalDateTime startDate, LocalDateTime endDate,
								  @OptionalParam("cinemaWorkingHours")CinemaWorkingHours cinemaWorkingHours);
	
}
