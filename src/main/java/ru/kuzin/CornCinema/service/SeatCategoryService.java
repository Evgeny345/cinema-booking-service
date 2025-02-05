package ru.kuzin.CornCinema.service;

import java.util.List;

import ru.kuzin.CornCinema.entityView.seatCategoryView.SeatCategoryView;

public interface SeatCategoryService {
	
	List<SeatCategoryView> getAllCategoryOfSeats();

}
