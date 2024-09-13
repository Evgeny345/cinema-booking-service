package ru.kuzin.CornCinema.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ru.kuzin.CornCinema.entityView.PersonFormView;
import ru.kuzin.CornCinema.entityView.PersonFormViewImpl;
import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Person;
import ru.kuzin.CornCinema.service.AmpluaService;
import ru.kuzin.CornCinema.service.PersonService;

@Controller
@RequestMapping("/films")
public class FilmController {
	
	private AmpluaService ampluaService;
	private PersonService personService;
	private static final Logger logger = LogManager.getLogger(FilmController.class);
	
	@Autowired
	public void setAmpluaService(AmpluaService ampluaService) {
		this.ampluaService = ampluaService;
	}
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/personForm")
	public String getPersonForm(Model model) {
		model.addAttribute("ampluaList", ampluaService.getAllAmpluaView());
		if(!model.containsAttribute("screening")) /*flash attribute!!!!!*/
			model.addAttribute("person", personService.getPersonForm());
		return "films/personForm";
	}
	
	@PostMapping("/createPerson")
	public String createPerson(@Valid @ModelAttribute("person") PersonFormViewImpl person, BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()) {
			model.addAttribute("ampluaList", ampluaService.getAllAmpluaView());
			return "films/personForm";
		}
		logger.info("Name: " + person.getName() + ", last name: " + person.getLastName() + ", date: " + person.getDateOfBirth() + ", set: " + person.getAmpluas().hashCode());
		person.getAmpluas().forEach(a -> logger.info("Class: " + a.getClass() + ", id: " + a.getId()));
		personService.savePerson(person);
		return "test";
	}

}
