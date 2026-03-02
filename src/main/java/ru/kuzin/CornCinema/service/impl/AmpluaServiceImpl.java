package ru.kuzin.CornCinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.kuzin.CornCinema.dao.AmpluaRepository;
import ru.kuzin.CornCinema.entityView.ampluaView.AmpluaView;
import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.service.AmpluaService;

@Service
public class AmpluaServiceImpl implements AmpluaService {
	
	private AmpluaRepository ampluaRepository;

	@Autowired
	public void setAmpluaRepository(AmpluaRepository ampluaRepository) {this.ampluaRepository = ampluaRepository;}

	@Override
	public List<AmpluaView> getAllAmpluaView() {
		return ampluaRepository.getAllAmpluaView();
	}

	@Override
	public List<Amplua> getAllAmplua() {
		return ampluaRepository.findAll();
	}

	@Override
	public Amplua getAmpluaByProfession(String profession) {
		return ampluaRepository.findByProfession(profession);
	}

}
