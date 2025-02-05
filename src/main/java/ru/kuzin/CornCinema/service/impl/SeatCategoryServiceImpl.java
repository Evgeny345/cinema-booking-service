package ru.kuzin.CornCinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.kuzin.CornCinema.dao.SeatCategoryRepository;
import ru.kuzin.CornCinema.entityView.seatCategoryView.SeatCategoryView;
import ru.kuzin.CornCinema.service.SeatCategoryService;

@Service
public class SeatCategoryServiceImpl implements SeatCategoryService {

	private SeatCategoryRepository seatCategoryRepository;
	
	@Autowired
	public void setSeatCategoryRepository(SeatCategoryRepository seatCategoryRepository) {
		this.seatCategoryRepository = seatCategoryRepository;
	}

	@Override
	public List<SeatCategoryView> getAllCategoryOfSeats() {
		return seatCategoryRepository.getAllSeatCategoryView();
	}

}
