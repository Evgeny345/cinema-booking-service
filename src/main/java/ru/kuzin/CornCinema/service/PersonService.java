package ru.kuzin.CornCinema.service;

import ru.kuzin.CornCinema.entityView.PersonFormView;
import ru.kuzin.CornCinema.models.Person;

public interface PersonService {
	
	PersonFormView getPersonForm();
	void savePerson(PersonFormView personFormView);
	Person getPersonForm1();

}
