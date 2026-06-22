package ru.kuzin.CornCinema.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import ru.kuzin.CornCinema.CornCinemaApplication;
import ru.kuzin.CornCinema.config.BlazePersistenceConfiguration;
import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.models.Amplua;

@DataJpaTest
@ContextConfiguration(classes = {BlazePersistenceConfiguration.class, CornCinemaApplication.class})
@ActiveProfiles("test")
public class AmpluaRepositoryTest {
	
	@Autowired
    private TestEntityManager em;
	@Autowired
	private AmpluaRepository ampluaRepository;
	
	@BeforeEach
	public void init() {
		Amplua director = new Amplua();
		director.setProfession("Director");
		Amplua actor = new Amplua();
		actor.setProfession("Actor");
		
		this.em.persist(director);
		this.em.persist(actor);
		this.em.flush();
        this.em.clear();
	}
	
	@Test
	public void findAllViews() {
		List<AmpluaView> sut = ampluaRepository.getAllAmpluaView();
		assertThat(sut).extracting("profession").contains("Director", "Actor");
	}
	
	@Test
	public void isFoundCorrectAmpluaByProfession() {
		Amplua sut = ampluaRepository.findByProfession("Actor");
		assertThat(sut.getProfession()).isEqualTo("Actor");
	}

}
