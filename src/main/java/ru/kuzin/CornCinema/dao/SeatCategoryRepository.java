package ru.kuzin.CornCinema.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.entityView.seatCategoryView.SeatCategoryView;
import ru.kuzin.CornCinema.models.SeatCategory;

public interface SeatCategoryRepository extends JpaRepository<SeatCategory, Integer> {
	
	List<SeatCategoryView> getAllSeatCategoryView();

}
