package ru.kuzin.CornCinema.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.kuzin.CornCinema.service.MovieService;
import ru.kuzin.CornCinema.service.HallService;
import ru.kuzin.CornCinema.service.SeatCategoryService;
import ru.kuzin.CornCinema.service.ShowTimeService;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@Controller
public class MainController {
	
	private ShowTimeService showTimeService;
	private MovieService movieService;
	private HallService hallService;
	private SeatCategoryService seatCategoryService;
	private CinemaWorkingHours cinemaWorkingHours;
	
	@Autowired
	public void setShowTimeService(ShowTimeService showTimeService) {this.showTimeService = showTimeService;}
	@Autowired
	public void setMovieService(MovieService movieService) {this.movieService = movieService;}
	@Autowired
	public void setHallService(HallService hallService) {this.hallService = hallService;}
	@Autowired
	public void setSeatCategoryService(SeatCategoryService seatCategoryService) {this.seatCategoryService = seatCategoryService;}
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {this.cinemaWorkingHours = cinemaWorkingHours;}
	
	
	@GetMapping
	public String getAllMoviesInPlayingPage(Model model) {
		model.addAttribute("moviesList", movieService.getAllMoviesInPlaying());
		return "movies/allMoviesInPlaying";	
	}
	
	@GetMapping("/schedule")
	public String getSchedulePage(Model model) {
		LocalDate startShowingDate = LocalDate.now();
		LocalDate endShowingDate = startShowingDate.plusDays(3);
		model.addAttribute("timeLine", cinemaWorkingHours.getTimeLine());
		model.addAttribute("hallsList", hallService.getAllHallForCreateForm());
		model.addAttribute("filmsList", showTimeService.getAllShowTimesByMovieAndDate(startShowingDate, endShowingDate));
		model.addAttribute("datesList", startShowingDate.datesUntil(endShowingDate.plusDays(1L)).toList());
		model.addAttribute("open", startShowingDate);
		return "showTimes/showTimesSchedule";
	}
	
	@GetMapping("/hallsScheme")
	public String getHallSchemePage(Model model) {
		model.addAttribute("hallsList", hallService.getAllHallView());
		model.addAttribute("seatCategoryList", seatCategoryService.getAllCategoryOfSeats());
		return "halls/schemeOfHalls";
	}
	
	@GetMapping("/workingHours")
	public String getCinemaWorkingHoursPage(Model model) {
		model.addAttribute("firstShowTimeStartTime", cinemaWorkingHours.getOpeningTime());
		model.addAttribute("cinemaOpeningTime", cinemaWorkingHours.getOpeningTime().minusHours(1));
		model.addAttribute("ticketCounterOpeningTime", cinemaWorkingHours.getOpeningTime().minusMinutes(30));
		model.addAttribute("cinemaClosingTime", cinemaWorkingHours.getClosingTime());
		return "workingHours";
	}
	
	@GetMapping("/cinemaInfo")
	public String getCinemaInfo() {
		return "cinemaInfo";
	}
	
	@GetMapping("/privacyPolicy")
	public String getPrivacyPolicyPage() {
		return "privacyPolicy";
	}
	
	@GetMapping("/termsAndConditions")
	public String getTermsAndConditionsPage() {
		return "termsAndConditions";
	}
	
	@GetMapping("/accessDenied")
	public String getAccessDeniedPage() {
        return "accessDenied";
    }
	
	@GetMapping("/exception")
	public String getExceptionPage() {
		return "exception";
	}
	
}
