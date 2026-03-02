package ru.kuzin.CornCinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.entityView.hallView.HallForCreateForm;
import ru.kuzin.CornCinema.entityView.hallView.HallIdView;
import ru.kuzin.CornCinema.entityView.hallView.HallView;
import ru.kuzin.CornCinema.models.Hall;

public interface HallRepository extends JpaRepository<Hall, Integer> {
	
	HallIdView getHallIdViewById(Integer id);
	
	HallView getHallViewById(Integer id);
	
	List<HallView> getAllHallView();
	
	List<HallForCreateForm> getAllHallForCreateForm();
	
	List<HallIdView> getAllHallIdView();

}
