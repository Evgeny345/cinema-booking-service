package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.hallView.HallForCreateForm;
import ru.kuzin.CornCinema.entityView.hallView.HallIdView;
import ru.kuzin.CornCinema.entityView.hallView.HallView;

public interface HallService {
	
	HallView getHallScheme(Integer id);
	
	HallIdView getHallIdView(Integer id);
	
	Integer numberOfHalls();
	
	List<HallView> getAllHallView();
	
	List<HallForCreateForm> getAllHallForCreateForm();
	
	List<HallIdView> getAllHallIdView();

}
