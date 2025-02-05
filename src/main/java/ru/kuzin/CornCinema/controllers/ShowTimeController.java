package ru.kuzin.CornCinema.controllers;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeFormViewImpl;
import ru.kuzin.CornCinema.service.FilmService;
import ru.kuzin.CornCinema.service.HallService;
import ru.kuzin.CornCinema.service.ShowTimeService;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@Controller
@RequestMapping("/showTimes")
public class ShowTimeController {
	
	private ShowTimeService showTimeService;
	private FilmService filmService;
	private CinemaWorkingHours cinemaWorkingHours;
	private HallService hallService;
	private static final Logger logger = LogManager.getLogger(ShowTimeController.class);
	
	@Autowired
	public void setShowTimeService(ShowTimeService showTimeService) {
		this.showTimeService = showTimeService;
	}
	@Autowired
	public void setFilmService(FilmService filmService) {
		this.filmService = filmService;
	}
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {
		this.cinemaWorkingHours = cinemaWorkingHours;
	}
	@Autowired
	public void setHallservice(HallService hallService) {
		this.hallService = hallService;
	}
	
	@GetMapping("/showTimeForm")
	public String getShowTimeForm(Model model, RedirectAttributes redirectAttributes) {
		LocalDate start = LocalDate.of(2023, 5, 5);
		LocalDate end = LocalDate.of(2023, 5, 7);
		if(!model.containsAttribute("showTime"))
			model.addAttribute("showTime", showTimeService.getShowTimeForm());
		model.addAttribute("availableTimeForCreateShowTime", filmService.getAllFilmInfoForShowTimeCreate(start, end));
		model.addAttribute("showTimesByDate", showTimeService.getAllShowTimesBetweenDatesByDate(start, end));
		model.addAttribute("timeLine", cinemaWorkingHours.getTimeLine());
		model.addAttribute("hallsList", hallService.getAllHallForCreateForm());
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		return "showTimes/showTimeForm";
	}
	
	@PostMapping("/createShowTime")
	public String createShowTime(@ModelAttribute("showTime") @Valid ShowTimeFormViewImpl showTime, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.showTime", result);
			redirectAttributes.addFlashAttribute("showTime", showTime);
			return "redirect:/showTimes/showTimeForm";
		}
		showTimeService.createShowTime(showTime);
		return "test";
	}
	
	@GetMapping("/delete/{showTimeId}")
	public String deleteShowTime(@PathVariable Integer showTimeId) {
		boolean isRemoved = showTimeService.deleteShowTime(showTimeId);
		if(!isRemoved)
			return "exception";
		return "redirect:/showTimes/showTimeForm";
	}

}
