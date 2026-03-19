package ru.kuzin.CornCinema.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.ConvertOption;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;

import jakarta.persistence.EntityManager;
import ru.kuzin.CornCinema.dao.PersonRepository;
import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.entityView.personView.PersonCreateView;
import ru.kuzin.CornCinema.entityView.personView.PersonForCreateMovie;
import ru.kuzin.CornCinema.entityView.personView.PersonFormView;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;
import ru.kuzin.CornCinema.entityView.personView.PersonIdView;
import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaView;
import ru.kuzin.CornCinema.models.Person;
import ru.kuzin.CornCinema.models.PersonWithAmpluaForMovie;
import ru.kuzin.CornCinema.service.AmpluaService;
import ru.kuzin.CornCinema.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	private PersonRepository personRepository;
	private AmpluaService ampluaService;
	private EntityManager entityManager;
	private EntityViewManager entityViewManager;
	private CriteriaBuilderFactory cbf;

	@Autowired
	public void setPersonRepository(PersonRepository personRepository) {this.personRepository = personRepository;}
	@Autowired
	public void setAmpluaService(AmpluaService ampluaService) {this.ampluaService = ampluaService;}
	@Autowired
	public void setEntityManager(EntityManager entityManager) {this.entityManager = entityManager;}
	@Autowired
	public void setEntityViewManager(EntityViewManager entityViewManager) {this.entityViewManager = entityViewManager;}
	@Autowired
	public void setCbf(CriteriaBuilderFactory cbf) {this.cbf = cbf;}

	@Override
	public PersonFormView getPersonForm() {
		PersonFormView person = entityViewManager.create(PersonFormView.class);
		return person;
	}

	@Override
	@Transactional
	public void createPerson(PersonFormView form) {
		PersonCreateView person = entityViewManager.convert(form, PersonCreateView.class, ConvertOption.CREATE_NEW);
		entityViewManager.save(entityManager, person);
	}
	
	@Override
	@Transactional
	public void createListPersons(List<Person> list) {
		personRepository.saveAll(list);
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
	public List<Person> getAllPersons() {
		return personRepository.findAll();
	}
	
	@Override
	public List<PersonFullNameView> getAllActors() {
		return getAllPersonsByAmpluaId(2);
	}
	
	@Override
	public Map<AmpluaView, List<PersonFullNameView>> getAllPersonsByAmplua() {
		return ampluaService.getAllAmpluaView()
				.stream()
				.collect(Collectors.toMap(Function.identity(), ampl -> getAllPersonsByAmpluaId(ampl.getId())));
	}
	
	@Override
	public Set<PersonForCreateMovie> getAllPersonsInMovie(Integer movieId) {
		CriteriaBuilder<PersonWithAmpluaForMovie> cb = cbf.create(entityManager, PersonWithAmpluaForMovie.class)
				.where("movie.id").eq(movieId);
		EntityViewSetting<PersonForCreateMovie, CriteriaBuilder<PersonForCreateMovie>> settings = EntityViewSetting.create(PersonForCreateMovie.class);
		return entityViewManager.applySetting(settings, cb).getResultStream().collect(Collectors.toSet());
	}
	
	@Override
	public long amountOfPersons() {
		return personRepository.count();
	}
	
	private List<PersonFullNameView> getAllPersonsByAmpluaId(Integer id) {
		return personRepository.findPersonByAmpluas_IdIs(id);
	}
	
}
