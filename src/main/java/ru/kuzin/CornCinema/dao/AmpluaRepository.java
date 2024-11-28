package ru.kuzin.CornCinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.models.Amplua;

public interface AmpluaRepository extends JpaRepository<Amplua, Integer> {
	
	List<AmpluaView> getAllAmpluaView();

}
