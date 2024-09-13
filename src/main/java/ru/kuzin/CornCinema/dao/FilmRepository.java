package ru.kuzin.CornCinema.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kuzin.CornCinema.models.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>{

}
