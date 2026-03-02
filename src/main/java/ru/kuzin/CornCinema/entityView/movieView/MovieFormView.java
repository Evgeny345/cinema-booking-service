package ru.kuzin.CornCinema.entityView.movieView;

import java.time.LocalTime;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableMapping;


import ru.kuzin.CornCinema.entityView.countryView.CountryIdView;
import ru.kuzin.CornCinema.entityView.genreView.GenreIdView;
import ru.kuzin.CornCinema.models.AgeRating;
import ru.kuzin.CornCinema.models.Movie;

@EntityView(Movie.class)
@CreatableEntityView
public interface MovieFormView extends MovieIdView {
	
	String getTitle();
	String getDescription();
	@DateTimeFormat(iso = ISO.TIME)
	LocalTime getDuration();
	String getPoster();
	AgeRating getAgeRating();
	Boolean getInPlayingNow();
	@UpdatableMapping
	Set<GenreIdView> getGenres();
	@UpdatableMapping
	Set<CountryIdView> getCountries();
	
	void setTitle(String title);
	void setDescription(String description);
	void setDuration(LocalTime duration);
	void setPoster(String poster);
	void setAgeRating(AgeRating ageRating);
	void setInPlayingNow(Boolean inPlayingNow);

}
