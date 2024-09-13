package ru.kuzin.CornCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.entityView.PersonFormView;
import ru.kuzin.CornCinema.models.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

	void save(PersonFormView personFormView);

}
