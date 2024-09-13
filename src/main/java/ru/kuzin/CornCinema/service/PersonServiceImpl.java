package ru.kuzin.CornCinema.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blazebit.persistence.view.ConvertOption;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.PrePersistEntityListener;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.PersonRepository;
import ru.kuzin.CornCinema.entityView.PersonCreateView;
import ru.kuzin.CornCinema.entityView.PersonFormView;
import ru.kuzin.CornCinema.entityView.PersonFormViewImpl;
import ru.kuzin.CornCinema.models.Person;


@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonRepository personRepository;
	private EntityManager entityManager;
	private EntityViewManager entityViewManager;
	private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);

	@Autowired
	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	@Autowired
	public void setEntityViewManager(EntityViewManager entityViewManager) {
		this.entityViewManager = entityViewManager;
	}

	@Override
	public PersonFormView getPersonForm() {
		PersonFormView person = entityViewManager.create(PersonFormView.class);
		return person;
	}

	@Override
	@Transactional
	public void savePerson(PersonFormView personFormView) {
		PersonCreateView p = entityViewManager.convert(personFormView, PersonCreateView.class, ConvertOption.CREATE_NEW);
		p.getAmpluas().forEach(a -> logger.info("Class: " + a.getClass() + ", id: " + a.getId()));
		entityViewManager.saveFull(entityManager, p);
	}
	
	@Override
	public Person getPersonForm1() {
		return new Person();
	}

}
