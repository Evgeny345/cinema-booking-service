package ru.kuzin.CornCinema.utilites;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Takes opening and closing time from application.properties as LocalTime, and
 * convert these to LocalDateTime if necessary. Creates time-line regardless of
 * open and close cinema on same or different day For example if cinema opening
 * at 22.00 and closing at 03.00 time-line wood look like: (22, 23, 00, 01, 02,
 * 03)
 */

@Component
public class CinemaWorkingHours {

	@Value("#{T(java.time.LocalTime).parse('${cinema.openingTime:10:00}')}")
	private LocalTime openingTime;
	@Value("#{T(java.time.LocalTime).parse('${cinema.closingTime:2:00}')}")
	private LocalTime closingTime;
	@Value("${cinema.intevalBetweenShowTimes:15}")
	private Integer intervalBetweenShowTimes;
	private static final LocalDate openAndCloseOnSameDay = LocalDate.of(2023, 1, 1);
	private static final LocalDate openAndCloseOnDifferentDay = LocalDate.of(2023, 1, 2);
	private static final LocalTime midnight = LocalTime.of(0, 0);

	public LocalTime getOpeningTime() {
		return openingTime;
	}

	public LocalTime getClosingTime() {
		return closingTime;
	}

	public Integer getIntervalBetweenShowTimes() {
		return intervalBetweenShowTimes;
	}

	public LocalDateTime getOpeningDateTime() {
		return LocalDateTime.of(openAndCloseOnSameDay, openingTime);
	}

	public LocalDateTime getClosingDateTime() {
		LocalDate closingDate = checkOpenAndCloseDuringOneDay() ? openAndCloseOnSameDay : openAndCloseOnDifferentDay;
		return LocalDateTime.of(closingDate, closingTime);
	}

	public LocalDateTime getCinemaOpeningDateTimeOnCertainDay(LocalDate date) {
		return LocalDateTime.of(date, openingTime);
	}
	
	public LocalDateTime getCinemaClosingDateTimeOnCertainDay(LocalDate date) {
		LocalDate closingDate = checkOpenAndCloseDuringOneDay() ? date : date.plusDays(1L);
		return LocalDateTime.of(closingDate, closingTime);
	}

	public LocalDate getCinemaClosingDay(LocalDate date) {
		return checkOpenAndCloseDuringOneDay() ? date : date.plusDays(1L);
	}

	/**
	 * Sequence of hours from opening time to closing time with increments of one hour
	 */
	public Set<Integer> getTimeLine() {
		return Stream.iterate(getOpeningDateTime(), d -> d.isBefore(getClosingDateTime()), d -> d.plusHours(1L))
				.map(h -> h.getHour()).collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public boolean checkOpenAndCloseDuringOneDay() {
		return openingTime.isBefore(closingTime);
	}
	
	public LocalDate getStartDayForSchedule(LocalDateTime showTimeStart) {
		
		LocalDate startDayForSchedule = showTimeStart.toLocalDate();
		LocalTime startTimeOfShowTime = showTimeStart.toLocalTime();
		
		if(startTimeOfShowTime.isAfter(midnight) && startTimeOfShowTime.isBefore(getClosingTime()))
			startDayForSchedule = showTimeStart.toLocalDate().minusDays(1);
		if(startTimeOfShowTime.equals(midnight))
			startDayForSchedule = checkOpenAndCloseDuringOneDay()? startDayForSchedule: showTimeStart.toLocalDate().minusDays(1);
		return startDayForSchedule;
	}

}
