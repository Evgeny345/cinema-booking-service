package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.AmpluaView;
import ru.kuzin.CornCinema.models.Amplua;

public interface AmpluaService {
	
	List<AmpluaView> getAllAmpluaView();

	List<Amplua> getAllAmpluaView1();
	
	List<String> getAllAmpluaView2();
	
	Amplua getAmpluaById(Integer id);

}
