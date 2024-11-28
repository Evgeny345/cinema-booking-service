package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.models.Amplua;

public interface AmpluaService {
	
	List<AmpluaView> getAllAmplua();
	
	List<String> getAllAmpluaView2();
	
	Amplua getAmpluaById(Integer id);

}
