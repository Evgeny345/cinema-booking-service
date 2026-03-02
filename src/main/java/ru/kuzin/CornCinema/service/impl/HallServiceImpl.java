package ru.kuzin.CornCinema.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.kuzin.CornCinema.dao.HallRepository;
import ru.kuzin.CornCinema.entityView.hallView.HallForCreateForm;
import ru.kuzin.CornCinema.entityView.hallView.HallIdView;
import ru.kuzin.CornCinema.entityView.hallView.HallView;
import ru.kuzin.CornCinema.service.HallService;

@Service
public class HallServiceImpl implements HallService {

	private HallRepository hallRepository;
	private static final Logger logger = LogManager.getLogger(HallServiceImpl.class);
	
	@Autowired
	public void setHallRepository(HallRepository hallRepository) {
		this.hallRepository = hallRepository;
	}

	@Override
	public HallView getHallScheme(Integer id) {
		return hallRepository.getHallViewById(id);
	}

	@Override
	@Cacheable("hallsCount")
	public Integer numberOfHalls() {
		return (int) hallRepository.count();
	}

	@Override
	public List<HallView> getAllHallView() {
		List<HallView> list = hallRepository.getAllHallView();
		
		Collections.sort(list, new Comparator<HallView>() {

			@Override
			public int compare(HallView o1, HallView o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
			
		});

		return list;
	}

	@Override
	public List<HallForCreateForm> getAllHallForCreateForm() {
		List<HallForCreateForm> list = hallRepository.getAllHallForCreateForm();
		
		Collections.sort(list, new Comparator<HallForCreateForm>() {

			@Override
			public int compare(HallForCreateForm o1, HallForCreateForm o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
			
		});

		return list;
	}

	@Override
	@Cacheable("hallsIdList")
	public List<HallIdView> getAllHallIdView() {
		return hallRepository.getAllHallIdView();
	}

	@Override
	public HallIdView getHallIdView(Integer id) {
		return hallRepository.getHallIdViewById(id);
	}

}
