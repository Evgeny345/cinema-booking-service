package ru.kuzin.CornCinema.utilites;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeDuration;
import ru.kuzin.CornCinema.service.HallService;

/**
 * This class finds free space in halls schedule between dates depending on film duration for create show time form. 
 */

@Component
public class AvailableTimeForShowTime {
	
	private CinemaWorkingHours cinemaWorkingHousrs;
	private HallService hallService;	
	
	@Autowired
	public void setCinemaWorkingHousrs(CinemaWorkingHours cinemaWorkingHousrs) {this.cinemaWorkingHousrs = cinemaWorkingHousrs;}
	@Autowired
	public void setHallService(HallService hallService) {this.hallService = hallService;}

	public Map<LocalDate, Map<Integer, List<LocalTime>>> convert(List<ShowTimeDuration> showTimes, 
																 LocalTime filmDuration, 
																 LocalDateTime startPeriod, 
																 LocalDateTime endPeriod) {
		/**
		 * Convert set of show times to lists of LocalTime and sorted by dates and halls
		 */
		Map<LocalDate, Map<Integer, List<LocalTime>>> availableTimeForHallByDate = 
				showTimes
				.stream()
				.sorted()
				.collect(Collectors.collectingAndThen(Collectors.groupingBy(ShowTimeDuration::getStartDay, Collectors.toList()),
						  mapDates -> mapDates.entrySet()
											  .stream()								  
											  .collect(Collectors.toMap(k -> k.getKey(),
													                    v -> v.getValue()
													                    .stream()
													                    .collect(Collectors.collectingAndThen(
													                    		 Collectors.groupingBy(ShowTimeDuration::getHallId, Collectors.toList()), 
													                    		 mapHall -> mapHall.entrySet()
													                    		 			 .stream()
													                    		 			 .collect(Collectors.toMap(k1 -> k1.getKey(), 
													                    		 					 				   v1 -> getAvailableTime(v1.getValue(), filmDuration))))), 
													                    (k1, k2) -> k1, 
													                    TreeMap::new))));
		
		
		List<Integer> hallIdList = hallService.getAllHallIdView().stream().map(hall -> hall.getId()).sorted().toList();
		List<LocalDate> dates = startPeriod.toLocalDate().datesUntil(endPeriod.toLocalDate()).toList();
		
		/**
		 * Check whether each hall presented on map
		 * If not, created default list of LocalTime for relevant hall
		 */
		availableTimeForHallByDate.entrySet().forEach((entry) -> {
			if(entry.getValue().keySet().size() < hallIdList.size()) {
				hallIdList.stream().filter(i -> !entry.getValue().keySet().contains(i)).forEach((hall) -> {
					entry.getValue().put(hall, createDefaultTimeList(filmDuration));
				});
			}
		});			
		
		/**
		 * Check whether each date presented on map
		 * If not, created default list of LocalTime for relevant date
		 * IntStream.rangeClosed(1, hallList.size())
								 	  .boxed()
		 */
		dates.stream().filter(date -> !availableTimeForHallByDate.keySet().contains(date)).forEach((mismatchDate) -> {
			availableTimeForHallByDate.put(mismatchDate, hallIdList.stream()
								 	  .collect(Collectors.toMap(Function.identity(),
								 			  					v -> createDefaultTimeList(filmDuration), (k1, k2) -> k1, TreeMap::new)));
		});
		
		return availableTimeForHallByDate;
	}
	
	/**
	 * Create list available time for new show time excluding time of another show times
	 */
	private List<LocalTime> getAvailableTime(List<ShowTimeDuration> showTimes, LocalTime filmDuration) {
		List<LocalTime> listOfUnavailableTimeForShowTime = new ArrayList<>();
		
		IntStream.range(0, showTimes.size() - 1).forEach(i -> {
			LocalDateTime endFirstShowTime = showTimes.get(i).getEndTime();
			LocalDateTime startSecondShowTime = showTimes.get(i + 1).getStartTime();
			listOfUnavailableTimeForShowTime.addAll(removeUnavailableTimeBetweenShowTimes(endFirstShowTime, startSecondShowTime, filmDuration));
			listOfUnavailableTimeForShowTime.add(endFirstShowTime.toLocalTime());
		});
		
		LocalDateTime startFirstShowTimeOnThisDay = showTimes.get(0).getStartTime();
		LocalDateTime startCinemaWorkingOnThisDay = LocalDateTime.of(startFirstShowTimeOnThisDay.toLocalDate(), cinemaWorkingHousrs.getOpeningTime());
		listOfUnavailableTimeForShowTime.addAll(removeUnavailableTimeBetweenShowTimes(startCinemaWorkingOnThisDay, startFirstShowTimeOnThisDay, filmDuration));
		showTimes.stream().forEach((showTime) -> {
			listOfUnavailableTimeForShowTime.addAll(removeTimeDuringShowTime(showTime.getStartTime(), showTime.getEndTime()));
		});
		
		List<LocalTime> availableTimeList = createDefaultTimeList(filmDuration);
		availableTimeList.removeAll(listOfUnavailableTimeForShowTime);
		return availableTimeList;
	}
	
	/**
	 * Create list of LocalTimes from opening to closing cinema(minus film duration) with increment 15 minutes 
	 */
	private List<LocalTime> createDefaultTimeList(LocalTime filmDuration) {
		return Stream.iterate(cinemaWorkingHousrs.getOpeningDateTime(), 
							  d -> d.isBefore(cinemaWorkingHousrs.getClosingDateTime().minusMinutes(getFilmDurationInMinutesIncludedBreak(filmDuration))), 
							  d -> d.plusMinutes(15L))
					 .map(t -> t.toLocalTime()).collect(Collectors.toList());
	}
	
	private List<LocalTime> removeTimeDuringShowTime(LocalDateTime startShowTime, LocalDateTime endShowTime) {
		return Stream.iterate(startShowTime, t -> t.isBefore(endShowTime), t -> t.plusMinutes(15L))
					 .map(d -> d.toLocalTime())
					 .collect(Collectors.toList());
	}
	
	private List<LocalTime> removeUnavailableTimeBetweenShowTimes(LocalDateTime endFirstShowTime, LocalDateTime startSecondShowTime, LocalTime duration) {
		return Stream.iterate(endFirstShowTime, t -> t.isBefore(startSecondShowTime), t -> t.plusMinutes(15L))
					 .filter(t -> t.plusMinutes(getFilmDurationInMinutesIncludedBreak(duration)).isAfter(startSecondShowTime))
					 .map(d -> d.toLocalTime())
					 .collect(Collectors.toList());
	}
	
	private long getFilmDurationInMinutesIncludedBreak(LocalTime duration) {
		return duration.plusMinutes(cinemaWorkingHousrs.getIntervalBetweenShowTimes()).getLong(ChronoField.MINUTE_OF_DAY);
	}


}
