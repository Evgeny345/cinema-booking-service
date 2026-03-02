package ru.kuzin.CornCinema.entityView.movieView;

import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

import com.blazebit.persistence.WhereBuilder;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingIndex;
import com.blazebit.persistence.view.ViewFilter;
import com.blazebit.persistence.view.ViewFilterProvider;

import ru.kuzin.CornCinema.entityView.personView.PersonWithAmpluaForMovieView;
import ru.kuzin.CornCinema.models.AgeRating;
import ru.kuzin.CornCinema.models.Movie;

@EntityView(Movie.class)
@ViewFilter(name = "inPlayingNowFilter", value = ru.kuzin.CornCinema.entityView.movieView.MovieView.InPlayingNowFilter.class)
public interface MovieView extends MovieIdView {
	
	String getTitle();
	String getDescription();
	LocalTime getDuration();
	String getPoster();
	AgeRating getAgeRating();
	Boolean getInPlayingNow();
	@Mapping("genres.title")
	Set<String> getGenres();
	@Mapping("countries.name")
	Set<String> getCountries();
	@MappingIndex("amplua.profession")
    @Mapping("persons")
	Map<String, Set<PersonWithAmpluaForMovieView>> getPersonsByAmplua();

	
	class InPlayingNowFilter extends ViewFilterProvider {

		@Override
		public <T extends WhereBuilder<T>> T apply(T whereBuilder) {
			return whereBuilder.where("inPlayingNow").eq(Boolean.TRUE);
		}
		
	}

}
