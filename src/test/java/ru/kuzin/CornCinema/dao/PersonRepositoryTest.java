package ru.kuzin.CornCinema.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import ru.kuzin.CornCinema.CornCinemaApplication;
import ru.kuzin.CornCinema.config.BlazePersistenceConfiguration;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;
import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaView;
import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Person;

@DataJpaTest
@ContextConfiguration(classes = {BlazePersistenceConfiguration.class, CornCinemaApplication.class})
@ActiveProfiles("test")
public class PersonRepositoryTest {
	
	@Autowired
    private TestEntityManager em;
	@Autowired
	private PersonRepository personRepository;
	private Amplua amplua;
	
	@BeforeEach
    public void init() {
		Amplua director = new Amplua();
		director.setProfession("Director");
		Person person = new Person();
		person.setDateOfBirth(LocalDate.of(1986, 10, 10));
		person.setName("Carole");
		person.setLastName("Smith");
		this.amplua = this.em.persist(director);
		person.setAmpluas(Set.of(director));
		this.em.persist(person);
		
		this.em.flush();
        this.em.clear();
	}
	
	@Test
	@DisplayName("Concatenating person's name and last name is correct")
	public void extractedFullName_isCorrect() {
		//Arrange
		Integer personId = personRepository.findAll().get(0).getId();
		//Act
		PersonFullNameView sut =  personRepository.getPersonFullNameViewById(personId);
		//Assert
		assertThat(sut.getFullName()).isEqualTo("Carole Smith");
	}
	
	@Test
	@DisplayName("Returns correct amount of appropriate entity views by amplua")
	public void test_findPersonByAmpluas() {
		List<PersonFullNameView> list = personRepository.findPersonByAmpluas_IdIs(this.amplua.getId());
		assertThat(list).hasSize(1);
	}
	
	@Test
	@DisplayName("Returns correct person's projection with amplua as string")
	public void findedPersonWithAmplua_isCorrect() {
		Integer personId = personRepository.findAll().get(0).getId();
		PersonWithAmpluaView sut = personRepository.getPersonWithAmpluaViewById(personId);
		assertThat(sut.getAmpluas().size()).isEqualTo(1);
		assertThat(sut.getAmpluas()).contains("Director");
	}

	
	@Test
	@DisplayName("Returns correct amount of appropriate entity views")
	public void test_getAllPersonFullName() {
		int sut = personRepository.getAllPersonFullNameView().size();
		assertThat(sut).isEqualTo(1);
	}
	
}
