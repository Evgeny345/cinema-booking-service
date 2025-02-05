package ru.kuzin.CornCinema.service;

import java.util.List;
import java.util.Map;

import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.entityView.personView.PersonFormView;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;
import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaView;
import ru.kuzin.CornCinema.models.Person;

public interface PersonService {
	
	PersonFormView getPersonForm();
	void createPerson(PersonFormView personFormView);
	Person getPersonForm1();
	List<PersonFullNameView> getAllActors();
	List<PersonFullNameView> getAllDirectors();
	PersonIdView getPersonIdViewById(Integer id);
	PersonWithAmpluaView getPersonWithAmpluaViewById(Integer id);
	PersonFullNameView getPersonFullNameViewById(Integer id);
	Map<AmpluaView, List<PersonFullNameView>> getAllPersonsByAmplua();

}
