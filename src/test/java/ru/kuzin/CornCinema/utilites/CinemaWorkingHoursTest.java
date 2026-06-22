package ru.kuzin.CornCinema.utilites;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class CinemaWorkingHoursTest {
	
	@Spy
	private CinemaWorkingHours cinemaWorkingHours = new CinemaWorkingHours();
	private LocalDate closingDate;
	
	@Test
	public void createCorrectTimeLine() {
		//Arrange
		Mockito.doReturn(LocalDateTime.of(2026, 5, 10, 20, 0)).when(cinemaWorkingHours).getOpeningDateTime();
		Mockito.doReturn(LocalDateTime.of(2026, 5, 11, 2, 0)).when(cinemaWorkingHours).getClosingDateTime();	
		Set<Integer> timeLinePattern = Set.of(20, 21, 22, 23, 0, 1);
		//Act
		Set<Integer> sut = cinemaWorkingHours.getTimeLine();
		//Assert
		assertThat(sut).isEqualTo(timeLinePattern);
	}

	@Nested
	@DisplayName("Cinema closes after midnight")
	class WorkingHoursWhenCinemaOpenAndCloseInDifferentDays {
		
		@BeforeEach
		public void init() {
			ReflectionTestUtils.setField(cinemaWorkingHours, "openingTime", LocalTime.of(10, 0));
			ReflectionTestUtils.setField(cinemaWorkingHours, "closingTime", LocalTime.of(2, 0));
			closingDate = LocalDate.of(2026, 5, 11); 
		}
		
		@Test
		@DisplayName("Cinema opens in one day and closes on the following day")
		public void cinemaOpeningAndClosingTime_inDifferentDays() {
			boolean sut = cinemaWorkingHours.checkOpenAndCloseDuringOneDay();		
			Assertions.assertFalse(sut);
		}
		
		@Test
		@DisplayName("If show time starts after midnight, reschedule it to previous day")
		public void startDayForSchedule_isCorrect_ifStartTimeIsAfterMidnight() {
			//Arrange
			LocalDateTime showTimeBeginnig = LocalDateTime.of(2026, 5, 11, 1, 0);
			LocalDate expectedDate = LocalDate.of(2026, 5, 10);
			//Act
			LocalDate sut = cinemaWorkingHours.getStartDayForSchedule(showTimeBeginnig);
			//Assert
			assertThat(sut).isEqualTo(expectedDate);
		}
		
		@Test
		@DisplayName("If show time starts at midnight, reschedule it to previous day")
		public void startDayForSchedule_isCorrect_ifStartTimeAtMidnight() {
			//Arrange
			LocalDateTime showTimeBeginnig = LocalDateTime.of(2026, 5, 11, 1, 0);
			LocalDate expectedDate = LocalDate.of(2026, 5, 10);
			//Act
			LocalDate sut = cinemaWorkingHours.getStartDayForSchedule(showTimeBeginnig);
			//Assert
			assertThat(sut).isEqualTo(expectedDate);
		}
		
		@Test
		@DisplayName("Cinema closes date is on the next day")
		public void cinemaClosingDateTimeOnCertainDay_isCorrect() {
			LocalDateTime expectedDateTime = LocalDateTime.of(closingDate, LocalTime.of(2, 0)).plusDays(1);
			
			LocalDateTime sut = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(closingDate);
			
			assertThat(sut).isEqualTo(expectedDateTime);
		}
	}
	
	@Nested
	@DisplayName("Cinema closes before midnight")
	class WorkingHoursWhenCinemaOpenAndCloseDuringOneDay {
		
		@BeforeEach
		public void init() {
			ReflectionTestUtils.setField(cinemaWorkingHours, "openingTime", LocalTime.of(10, 0));
			ReflectionTestUtils.setField(cinemaWorkingHours, "closingTime", LocalTime.of(23, 0));
			closingDate = LocalDate.of(2026, 5, 11); 
		}
		
		@Test
		@DisplayName("Cinema opens and closes during one day")
		public void cinemaOpeningAndClosingTime_duringOneDay() {
			boolean sut = cinemaWorkingHours.checkOpenAndCloseDuringOneDay();		
			Assertions.assertTrue(sut);
		}
		
		@Test
		@DisplayName("If show time starts before midnight do not reschedule it")
		public void startDayForSchedule_isCorrect_ifStartTimeBeforeMidnight() {
			//Arrange
			LocalDateTime showTimeBeginnig = LocalDateTime.of(2026, 5, 11, 22, 0);
			LocalDate expectedDate = showTimeBeginnig.toLocalDate();
			//Act
			LocalDate sut = cinemaWorkingHours.getStartDayForSchedule(showTimeBeginnig);
			//Assert
			assertThat(sut).isEqualTo(expectedDate);
		}
		
		@Test
		@DisplayName("Cinema closing date is on the same day")
		public void cinemaClosingDateTimeOnCertainDay_isCorrect() {
			LocalDateTime expectedDateTime = LocalDateTime.of(2026, 5, 11, 23, 0);
			
			LocalDateTime sut = cinemaWorkingHours.getCinemaClosingDateTimeOnCertainDay(closingDate);
			
			Assertions.assertTrue(expectedDateTime.equals(sut));
		}
		
	}
}
