package ru.kuzin.CornCinema.entityView.movieView;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.SerializableEntityViewManager;
import com.blazebit.persistence.view.StaticImplementation;
import com.blazebit.persistence.view.spi.type.EntityViewProxy;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.kuzin.CornCinema.entityView.countryView.CountryIdView;
import ru.kuzin.CornCinema.entityView.genreView.GenreIdView;
import ru.kuzin.CornCinema.entityView.personView.PersonForCreateMovie;
import ru.kuzin.CornCinema.models.AgeRating;
import ru.kuzin.CornCinema.models.Movie;
import ru.kuzin.CornCinema.validators.NotEmptyActorsList;
import ru.kuzin.CornCinema.validators.NotEmptyDirectorsList;

@StaticImplementation(MovieFormView.class)
public class MovieFormViewImpl implements MovieFormView, EntityViewProxy {
	
	public static volatile EntityViewManager ENTITY_VIEW_MANAGER;
	public static final SerializableEntityViewManager SERIALIZABLE_ENTITY_VIEW_MANAGER = new SerializableEntityViewManager(MovieFormViewImpl.class, ENTITY_VIEW_MANAGER);
	
	private Integer id;
	@NotEmpty(message = "must not be empty")
    @Size(max=64, message = "must be less than 64")
	private String title;
    @Size(max=512, message = "must be less than 512")
	private String description;
	@NotNull(message = "must not be empty")
	@DateTimeFormat(iso = ISO.TIME, pattern = "HH:mm")
	private LocalTime duration;
	private String poster;
	@NotNull(message = "must not be empty")
	private AgeRating ageRating;
	private Boolean inPlayingNow;
	@NotEmpty(message = "must not be empty")
	@Size(min = 1, max = 2, message = "should not be more than two genres")
	private Set<GenreIdView> genres = new HashSet<>();
	@NotEmpty(message = "must not be empty")
	private Set<CountryIdView> countries = new HashSet<>();
	@NotEmptyActorsList
	@NotEmptyDirectorsList
	private Set<PersonForCreateMovie> persons = new HashSet<>();

	public MovieFormViewImpl() {
	
	}
	
	public MovieFormViewImpl(MovieFormViewImpl noop, Map<String, Object> optionalParameters) {
		this.id = null;
		this.title = null;
		this.description = null;
		this.duration = null;
		this.ageRating = null;
	}

	public MovieFormViewImpl(Integer id) {
		this.id = id;
		this.title = null;
		this.description = null;
		this.duration = null;
		this.ageRating = null;
	}
	
	public MovieFormViewImpl(Integer id, String title, String description, LocalTime duration, AgeRating ageRating) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.ageRating = ageRating;
	}
	
	@SuppressWarnings("unchecked")
	public MovieFormViewImpl(MovieFormViewImpl noop, int offset, Object[] tuple) {
		this.id = (Integer) tuple[offset + 0];
		this.title = (String) tuple[offset + 8];
		this.description = (String) tuple[offset + 3];
		this.duration = (LocalTime) tuple[offset + 4];
		this.poster = (String) tuple[offset + 7];
		this.ageRating = (AgeRating) tuple[offset + 1];
		this.inPlayingNow = (Boolean) tuple[offset + 6];
		this.countries = (Set<CountryIdView>) tuple[offset + 2];
		this.genres = (Set<GenreIdView>) tuple[offset + 5];
	}

	@Override
	public Integer getId() {return id;}
	@Override
	public String getTitle() {return title;}
	@Override
	public String getDescription() {return description;}
	@Override
	public LocalTime getDuration() {return duration;}
	@Override
	public String getPoster() {return poster;}
	@Override
	public AgeRating getAgeRating() {return ageRating;}
	public Boolean getInPlayingNow() {return inPlayingNow;}
	public Set<GenreIdView> getGenres() {return genres;}
	public Set<CountryIdView> getCountries() {return countries;}
	public Set<PersonForCreateMovie> getPersons() {return persons;}
	
	public void setId(Integer id) {this.id = id;}
	@Override
	public void setTitle(String title) {this.title = title;}
	@Override
	public void setDescription(String description) {this.description = description;}
	@Override
	public void setDuration(LocalTime duration) {this.duration = duration;}
	@Override
	public void setPoster(String poster) {this.poster = poster;}
	@Override
	public void setAgeRating(AgeRating ageRating) {this.ageRating = ageRating;}
	public void setInPlayingNow(Boolean isShowingNow) {this.inPlayingNow = isShowingNow;}
	public void setGenres(Set<GenreIdView> genres) {this.genres = genres;}
	public void setCountries(Set<CountryIdView> countries) {this.countries = countries;}
	public void setPersons(Set<PersonForCreateMovie> persons) {this.persons = persons;}

	public Set<Integer> getActorsIds() {
		return this.persons.stream().filter(p -> p.getAmpluaId().equals(2)).map(p -> p.getPersonId()).collect(Collectors.toSet());
	}

	public Set<Integer> getDirectorsIds() {
		return this.persons.stream().filter(p -> p.getAmpluaId().equals(1)).map(p -> p.getPersonId()).collect(Collectors.toSet());
	}
	
	public boolean personIsExists(Integer personId, Integer ampluaId) {
		return persons.stream().anyMatch(p -> p.getPersonId().equals(personId) && p.getAmpluaId().equals(ampluaId));
	}

	@Override
	public Class<?> $$_getJpaManagedClass() {
		return Movie.class;
	}

	@Override
	public Class<?> $$_getJpaManagedBaseClass() {
		return Movie.class;
	}

	@Override
	public Class<?> $$_getEntityViewClass() {
		return MovieFormView.class;
	}

	@Override
	public boolean $$_isNew() {
		return false;
	}

	@Override
	public boolean $$_isReference() {
		return false;
	}

	@Override
	public void $$_setIsReference(boolean isReference) {
		
	}

	@Override
	public Object $$_getId() {
		return id;
	}

	@Override
	public Object $$_getVersion() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
    public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null || this.$$_getId() == null) {
            return false;
        }
        if (obj instanceof EntityViewProxy) {
            EntityViewProxy other = (EntityViewProxy) obj;
            if (this.$$_getJpaManagedBaseClass() == other.$$_getJpaManagedBaseClass() && this.$$_getId().equals(other.$$_getId())) {
                return true;
            } else {
                return false;
            }
        }
        if (obj instanceof MovieFormView) {
        	MovieFormView other = (MovieFormView) obj;
            if (!Objects.equals(this.getId(), other.getId())) {
                return false;
            }
            return true;
        }
		return false;
	}

}
