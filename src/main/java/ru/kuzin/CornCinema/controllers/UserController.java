package ru.kuzin.CornCinema.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import ru.kuzin.CornCinema.entityView.userView.UserFormViewImpl;
import ru.kuzin.CornCinema.security.SecurityService;
import ru.kuzin.CornCinema.service.TicketService;
import ru.kuzin.CornCinema.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	private SecurityService securityService;
	private TicketService ticketService;

	@Autowired
	public void setUserService(UserService userService) {this.userService = userService;}
	@Autowired
	public void setSecurityService(SecurityService securityService) {this.securityService = securityService;}
	@Autowired
	public void setTicketService(TicketService ticketService) {this.ticketService = ticketService;}
	
	@GetMapping("/registrationForm")
	public String getUserCreateForm(Model model) {
		model.addAttribute("user", userService.getUserForm());
		return "users/userCreateForm";
	}
	
	@GetMapping("/login")
    public String login() {
		return "users/userLoginForm";
    }
	
	@GetMapping("/reservations")
    public String showAllActiveReservation(Model model, HttpServletRequest request) {
		LocalDate start = LocalDate.now();
		LocalDate end = start.plusDays(3);
		Principal principal = request.getUserPrincipal();
		if (principal != null)
			model.addAttribute("reservationsMap", ticketService.allActiveReservationsForUserByShowTime(principal.getName(), start, end));
		return "users/userReservations";
    }
	
	@PostMapping("/chancelReservation")
	public String chancelReservation(@RequestParam(name = "reservationDate") LocalDateTime reservationDate,
									 @RequestParam(name = "userId") Integer userId) {
		ticketService.chancelReservation(reservationDate, userId);
		return "redirect:/users/reservations";
	}
	
	@GetMapping("/logout")
	public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	    logoutHandler.logout(request, response, authentication);
	    return "redirect:/";
	}
	
	@PostMapping("/createUser")
	public String createUser(@ModelAttribute("user") @Valid UserFormViewImpl userForm, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if(bindingResult.hasErrors()) {
			return "users/userCreateForm";
		}
		userService.createUser(userForm, "ROLE_USER");
		securityService.autoLogin(userForm.getUserName(), userForm.getConfirmPassword(), request, response);
		return "redirect:/";
	}

}
