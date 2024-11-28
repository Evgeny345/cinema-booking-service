package ru.kuzin.CornCinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.entityView.personView.PersonFormView;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;
import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaView;
import ru.kuzin.CornCinema.models.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	void save(PersonFormView personFormView);
	List<PersonFullNameView> getAllPersonFullNameView();
	List<PersonFullNameView> findPersonByAmpluas_IdIs(Integer id);
	PersonIdView getPersonIdViewById(Integer id);
	PersonWithAmpluaView getPersonWithAmpluaViewById(Integer id);
	PersonFullNameView getPersonFullNameViewById(Integer id);

}
