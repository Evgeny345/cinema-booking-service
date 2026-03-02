package ru.kuzin.CornCinema.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.entityView.personView.PersonForCreateMovie;
import ru.kuzin.CornCinema.entityView.personView.PersonFormView;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;
import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaView;
import ru.kuzin.CornCinema.models.Person;

public interface PersonService {
	
	PersonFormView getPersonForm();
	
	void createPerson(PersonFormView personFormView);
	
	void createListPersons(List<Person> list);
	
	List<PersonFullNameView> getAllActors();
	
	List<PersonFullNameView> getAllDirectors();
	
	List<Person> getAllPersons();
	
	Set<PersonForCreateMovie> getAllPersonsInMovie(Integer movieId);
	
	PersonIdView getPersonIdViewById(Integer id);
	
	PersonWithAmpluaView getPersonWithAmpluaViewById(Integer id);
	
	Map<AmpluaView, List<PersonFullNameView>> getAllPersonsByAmplua();
	
	long amountOfPersons();

}
