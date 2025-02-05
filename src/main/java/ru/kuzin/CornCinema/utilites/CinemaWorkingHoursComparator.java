package ru.kuzin.CornCinema.utilites;

import java.time.LocalDateTime;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;

public class CinemaWorkingHoursComparator implements Comparator <LocalDateTime> {

	private CinemaWorkingHours cinemaWorkingHousrs;
	
	@Autowired
	public void setCinemaWorkingHousrs(CinemaWorkingHours cinemaWorkingHousrs) {this.cinemaWorkingHousrs = cinemaWorkingHousrs;}
	
	@Override
	public int compare(LocalDateTime o1, LocalDateTime o2) {
		if(cinemaWorkingHousrs.checkOpenAndCloseDuringOneDay())
			return o1.compareTo(o2);
		return 0;
	}

}
