package ru.kuzin.CornCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.models.SeatCategory;

public interface SeatCategoryRepository extends JpaRepository<SeatCategory, Integer>{

}
