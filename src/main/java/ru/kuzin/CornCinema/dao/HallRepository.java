package ru.kuzin.CornCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.models.Hall;

public interface HallRepository extends JpaRepository<Hall, Integer>{

}
