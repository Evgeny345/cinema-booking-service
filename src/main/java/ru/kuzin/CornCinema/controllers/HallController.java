package ru.kuzin.CornCinema.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.kuzin.CornCinema.service.HallService;
import ru.kuzin.CornCinema.service.SeatCategoryService;

@Controller
@RequestMapping("/halls")
public class HallController {
	
	private HallService hallService;
	private SeatCategoryService seatCategoryService;
	
	@Autowired
	public void setHallService(HallService hallService) {this.hallService = hallService;}
	@Autowired
	public void setSeatCategoryService(SeatCategoryService seatCategoryService) {this.seatCategoryService = seatCategoryService;}

	@GetMapping("/scheme/{id}")
	public String getHallScheme(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("hall", hallService.getHallScheme(id));
		model.addAttribute("seatCategoryList", seatCategoryService.getAllCategoryOfSeats());
		return "halls/hallScheme";
	}

}
