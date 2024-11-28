package ru.kuzin.CornCinema.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaView;
import ru.kuzin.CornCinema.service.PersonService;

@Component
public class StringToPersonWithAmpluaConverter implements Converter<String, PersonWithAmpluaView> {

private PersonService personService;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@Override
	public PersonWithAmpluaView convert(String source) {
		return personService.getPersonWithAmpluaViewById(Integer.parseInt(source));
	}

}
