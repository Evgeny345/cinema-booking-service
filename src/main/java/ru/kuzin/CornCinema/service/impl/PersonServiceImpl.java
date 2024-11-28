package ru.kuzin.CornCinema.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blazebit.persistence.view.ConvertOption;
import com.blazebit.persistence.view.EntityViewManager;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.PersonRepository;
import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.entityView.personView.PersonCreateView;
import ru.kuzin.CornCinema.entityView.personView.PersonFormView;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;
import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaView;
import ru.kuzin.CornCinema.models.Person;
import ru.kuzin.CornCinema.service.AmpluaService;
import ru.kuzin.CornCinema.service.PersonService;


@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonRepository personRepository;
	private AmpluaService ampluaService;
	private EntityManager entityManager;
	private EntityViewManager entityViewManager;
	private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);

	@Autowired
	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	@Autowired
	public void setAmpluaService(AmpluaService ampluaService) {
		this.ampluaService = ampluaService;
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
	public void savePerson(PersonFormView form) {
		PersonCreateView person = entityViewManager.convert(form, PersonCreateView.class, ConvertOption.CREATE_NEW);
		entityViewManager.save(entityManager, person);
	}
	
	@Override
	public Person getPersonForm1() {
		return new Person();
	}
	
	@Override
	public PersonIdView getPersonIdViewById(Integer id) {
		return personRepository.getPersonIdViewById(id);
	}
	
	@Override
	public PersonWithAmpluaView getPersonWithAmpluaViewById(Integer id) {
		return personRepository.getPersonWithAmpluaViewById(id);
	}
	
	@Override
	public List<PersonFullNameView> getAllDirectors() {
		return getAllPersonsByAmpluaId(1);
		
	}
	
	@Override
	public List<PersonFullNameView> getAllActors() {
		return getAllPersonsByAmpluaId(2);
	}
	
	@Override
	public Map<AmpluaView, List<PersonFullNameView>> getAllPersonsByAmplua() {
		return ampluaService.getAllAmplua()
				.stream()
				.collect(Collectors.toMap(Function.identity(), ampl -> getAllPersonsByAmpluaId(ampl.getId())));
	}
	
	private List<PersonFullNameView> getAllPersonsByAmpluaId(Integer id) {
		return personRepository.findPersonByAmpluas_IdIs(id);
	}
	
	@Override
	public PersonFullNameView getPersonFullNameViewById(Integer id) {
		return personRepository.getPersonFullNameViewById(id);
	}
	
}
