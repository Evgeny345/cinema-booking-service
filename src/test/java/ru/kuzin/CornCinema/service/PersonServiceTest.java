package ru.kuzin.CornCinema.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.kuzin.CornCinema.dao.PersonRepository;
import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.entityView.personView.PersonFullNameView;
import ru.kuzin.CornCinema.service.impl.PersonServiceImpl;


@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	@Mock
	private PersonRepository personRepository;
	@Mock
	private AmpluaService ampluaService;
	@InjectMocks
	private PersonService personService = new PersonServiceImpl();
	
	@Test
	public void sortingPersonsByAmpua_isCorrect() {
		//Arrange
		MockAmplua director = new MockAmplua(1, "director");
		MockAmplua actor = new MockAmplua(2, "actor");
		Mockito.when(ampluaService.getAllAmpluaView()).thenReturn(List.of(director, actor));	
		PersonFullName director1 = new PersonFullName(1, "David Warner");
		PersonFullName director2 = new PersonFullName(2, "Andrew Jacobs");
		Mockito.when(personRepository.findPersonByAmpluas_IdIs(1)).thenReturn(List.of(director1, director2));
		PersonFullName actor1 = new PersonFullName(3, "David Warner");
		PersonFullName actor2 = new PersonFullName(4, "Richard Ross");
		PersonFullName actor3 = new PersonFullName(5, "Carole Smith");
		Mockito.when(personRepository.findPersonByAmpluas_IdIs(2)).thenReturn(List.of(actor1, actor2, actor3));
		//Act
		Map<AmpluaView, List<PersonFullNameView>> sut = personService.getAllPersonsByAmplua();
		//Assert
		assertThat(sut.get(director).size()).isEqualTo(2);
		assertThat(sut.get(actor).size()).isEqualTo(3);
	}
	
	private class MockAmplua implements AmpluaView {

		private Integer id;
		private String profession;
		
		public MockAmplua(Integer id, String profession) {
			super();
			this.id = id;
			this.profession = profession;
		}
		
		@Override
		public Integer getId() {
			return id;
		}

		@Override
		public String getProfession() {
			return profession;
		}
		
	}
	
	private class PersonFullName implements PersonFullNameView {

		private Integer id;
		private String fullName;
		
		public PersonFullName(Integer id, String fullName) {
			super();
			this.id = id;
			this.fullName = fullName;
		}

		@Override
		public Integer getId() {
			return id;
		}

		@Override
		public String getFullName() {
			return fullName;
		}
		
	}

}
