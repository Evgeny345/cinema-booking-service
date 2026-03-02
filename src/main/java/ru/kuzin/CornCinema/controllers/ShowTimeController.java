package ru.kuzin.CornCinema.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import ru.kuzin.CornCinema.entityView.showTimeView.ShowTimeFormViewImpl;
import ru.kuzin.CornCinema.entityView.ticketView.TicketPlaceView;
import ru.kuzin.CornCinema.exceptions.NotFoundException;
import ru.kuzin.CornCinema.service.MovieService;
import ru.kuzin.CornCinema.service.HallService;
import ru.kuzin.CornCinema.service.ShowTimeService;
import ru.kuzin.CornCinema.service.TicketService;
import ru.kuzin.CornCinema.utilites.CinemaWorkingHours;

@Controller
@RequestMapping("/showTimes")
public class ShowTimeController {
	
	private ShowTimeService showTimeService;
	private MovieService movieService;
	private CinemaWorkingHours cinemaWorkingHours;
	private HallService hallService;
	private TicketService ticketService;
	
	@Autowired
	public void setShowTimeService(ShowTimeService showTimeService) {this.showTimeService = showTimeService;}
	@Autowired
	public void setMovieService(MovieService movieService) {this.movieService = movieService;}
	@Autowired
	public void setCinemaWorkingHours(CinemaWorkingHours cinemaWorkingHours) {this.cinemaWorkingHours = cinemaWorkingHours;}
	@Autowired
	public void setHallService(HallService hallService) {this.hallService = hallService;}
	@Autowired
	public void setTicketService(TicketService ticketService) {this.ticketService = ticketService;}
	
	@GetMapping("/admin/showTimeForm")
	public String getShowTimeForm(Model model, RedirectAttributes redirectAttributes) {
		LocalDate start = LocalDate.now().plusDays(2);
		LocalDate end = start.plusDays(3);
		if(!model.containsAttribute("showTime"))
			model.addAttribute("showTime", showTimeService.getShowTimeForm());
		model.addAttribute("availableTimeForCreateShowTime", movieService.getAllMovieInfoForShowTimeCreate(start, end));
		model.addAttribute("showTimesByDate", showTimeService.getAllShowTimesBetweenDatesByDate(start, end));
		model.addAttribute("timeLine", cinemaWorkingHours.getTimeLine());
		model.addAttribute("hallsList", hallService.getAllHallForCreateForm());
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		return "showTimes/showTimeForm";
	}
	
	@PostMapping("/admin/createShowTime")
	public String createShowTime(@ModelAttribute("showTime") @Valid ShowTimeFormViewImpl showTime, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.showTime", result);
			redirectAttributes.addFlashAttribute("showTime", showTime);
			return "redirect:/showTimes/admin/showTimeForm";
		}
		showTimeService.createShowTime(showTime);
		return "redirect:/showTimes/admin/showTimeForm";
	}
	
	@GetMapping("/admin/deleteShowTime/{showTimeId}")
	public String deleteShowTime(@PathVariable Integer showTimeId) {
		boolean isRemoved = showTimeService.deleteShowTime(showTimeId);
		if(!isRemoved)
			return "exception";
		return "redirect:/showTimes/admin/showTimeForm";
	}
	
	@GetMapping("/showTime/{showTimeId}")
	public String getShowTime(@PathVariable Integer showTimeId, Model model) {
		try {
			model.addAttribute("showTime", showTimeService.getShowTimeViewById(showTimeId));
		} catch (NotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
		
		if(model.containsAttribute("unbookedTickets")) {
			model.addAttribute("showModal", true);
		}
		return "showTimes/showTimeWithResrveTicketsForm";
	}
	
	@PostMapping("/reserveTickets")
	public String reserveTickets(@RequestParam(name="ticketsList") List<Long> ticketsList, 
								 @RequestParam(name="showTimeId") Integer showTimeId, Principal principal, RedirectAttributes redirectAttributes) {
		String userName = principal.getName();
		List<TicketPlaceView> unbookedTickets = ticketService.reserveTickets(ticketsList, showTimeId, userName);
		if(!CollectionUtils.isEmpty(unbookedTickets)) {
			redirectAttributes.addFlashAttribute("unbookedTickets", unbookedTickets);
			return "redirect:/showTimes/showTime/" + showTimeId;
		}
		return "redirect:/";
	}

}
