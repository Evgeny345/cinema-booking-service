package ru.kuzin.CornCinema.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.service.PersonService;

@Component
public class StringToPersonIdViewConverter implements Converter<String, PersonIdView> {

	private PersonService personService;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Override
	public PersonIdView convert(String source) {
		return personService.getPersonIdViewById(Integer.parseInt(source));
	}

}
