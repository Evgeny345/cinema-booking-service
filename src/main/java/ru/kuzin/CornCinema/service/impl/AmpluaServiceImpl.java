package ru.kuzin.CornCinema.service.impl;

import java.util.ArrayList;
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
	public void setAmpluaRepository(AmpluaRepository ampluaRepository) {
		this.ampluaRepository = ampluaRepository;
	}

	@Override
	public List<AmpluaView> getAllAmplua() {
		return ampluaRepository.getAllAmpluaView();
	}

	public List<Amplua> getAllAmpluaView1() {
		return ampluaRepository.findAll();
	}

	@Override
	public List<String> getAllAmpluaView2() {
		List<String> list = new ArrayList<>();
		ampluaRepository.findAll().forEach(w -> {
			list.add(w.getProfession());
		});
		return list;
	}

	@Override
	public Amplua getAmpluaById(Integer id) {
		return ampluaRepository.findById(id).get();
	}
}
