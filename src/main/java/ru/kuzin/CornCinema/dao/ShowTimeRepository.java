package ru.kuzin.CornCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.models.ShowTime;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Integer>{

}
