package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.models.Amplua;

public interface AmpluaService {
	
	List<AmpluaView> getAllAmpluaView();
	
	List<Amplua> getAllAmplua();
	
	Amplua getAmpluaByProfession(String profession);
	
}
