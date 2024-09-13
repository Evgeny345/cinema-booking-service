package ru.kuzin.CornCinema.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ru.kuzin.CornCinema.service.ShowTimeService;

@Controller
public class TestController {
	
	private ShowTimeService service;

	@Autowired
	public void setService(ShowTimeService service) {this.service = service;}
	
	@GetMapping
	public String test() {
		service.deleteShowTime(9);
		return "test";
	}

}
