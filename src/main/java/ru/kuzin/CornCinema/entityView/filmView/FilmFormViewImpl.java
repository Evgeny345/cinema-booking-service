package ru.kuzin.CornCinema.entityView.filmView;

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
import ru.kuzin.CornCinema.entityView.personView.PersonForCreateFilmImpl;
import ru.kuzin.CornCinema.entityView.personView.PersonFormView;
import ru.kuzin.CornCinema.models.AgeRating;
import ru.kuzin.CornCinema.models.Film;
import ru.kuzin.CornCinema.validators.NotEmptyActorsList;
import ru.kuzin.CornCinema.validators.NotEmptyDirectorsList;

@StaticImplementation(FilmFormView.class)
public class FilmFormViewImpl implements FilmFormView, EntityViewProxy {
	
	public static volatile EntityViewManager ENTITY_VIEW_MANAGER;
	public static final SerializableEntityViewManager SERIALIZABLE_ENTITY_VIEW_MANAGER = new SerializableEntityViewManager(FilmFormViewImpl.class, ENTITY_VIEW_MANAGER);
	
	private Integer id;
	@NotEmpty(message = "must not be empty")
    @Size(max=64, message = "must be less than 64")
	private String title;
	@NotEmpty(message = "must not be empty")
    @Size(max=512, message = "must be less than 512")
	private String description;
	@NotNull(message = "must not be empty")
	@DateTimeFormat(iso = ISO.TIME, pattern = "HH:mm")
	private LocalTime duration;
	@NotNull(message = "must not be empty")
	private AgeRating ageRating;
	private Boolean inRolling;
	@NotEmpty(message = "must not be empty")
	private Set<GenreIdView> genres = new HashSet<>();;
	@NotEmpty(message = "must not be empty")
	private Set<CountryIdView> countries = new HashSet<>();;
	@NotEmptyActorsList
	@NotEmptyDirectorsList
	private Set<PersonForCreateFilmImpl> persons = new HashSet<>();

	public FilmFormViewImpl() {
	
	}
	
	public FilmFormViewImpl(FilmFormViewImpl noop, Map<String, Object> optionalParameters) {
		this.id = null;
		this.title = null;
		this.description = null;
		this.duration = null;
		this.ageRating = null;
	}
	
	public FilmFormViewImpl(Integer id) {
		this.id = id;
		this.title = null;
		this.description = null;
		this.duration = null;
		this.ageRating = null;
	}
	
	public FilmFormViewImpl(Integer id, String title, String description, LocalTime duration, AgeRating ageRating) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.ageRating = ageRating;
	}
	
	public FilmFormViewImpl(FilmFormViewImpl noop, int offset, Object[] tuple) {
		this.id = (Integer) tuple[offset + 0];
		this.title = (String) tuple[offset + 3];
		this.description = (String) tuple[offset + 7];
		this.duration = (LocalTime) tuple[offset + 4];
		this.ageRating = (AgeRating) tuple[offset + 1];
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
	public AgeRating getAgeRating() {return ageRating;}
	@Override
	public Boolean getInRolling() {return inRolling;}
	public Set<GenreIdView> getGenres() {return genres;}
	public Set<CountryIdView> getCountries() {return countries;}
	public Set<PersonForCreateFilmImpl> getPersons() {return persons;}
	
	@Override
	public void setTitle(String title) {this.title = title;}
	@Override
	public void setDescription(String description) {this.description = description;}
	@Override
	public void setDuration(LocalTime duration) {this.duration = duration;}
	@Override
	public void setAgeRating(AgeRating ageRating) {this.ageRating = ageRating;}
	public void setInRolling(Boolean inRolling) {this.inRolling = inRolling;}
	public void setGenres(Set<GenreIdView> genres) {this.genres = genres;}
	public void setCountries(Set<CountryIdView> countries) {this.countries = countries;}
	public void setPersons(Set<PersonForCreateFilmImpl> persons) {this.persons = persons;}
	
	public Set<Integer> getActorsIds() {
		return this.persons.stream().filter(p -> p.getAmpluaId().equals(2)).map(p -> p.getPersonId()).collect(Collectors.toSet());
	}

	public Set<Integer> getDirectorsIds() {
		return this.persons.stream().filter(p -> p.getAmpluaId().equals(1)).map(p -> p.getPersonId()).collect(Collectors.toSet());
	}

	@Override
	public Class<?> $$_getJpaManagedClass() {
		return Film.class;
	}

	@Override
	public Class<?> $$_getJpaManagedBaseClass() {
		return Film.class;
	}

	@Override
	public Class<?> $$_getEntityViewClass() {
		return FilmFormView.class;
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
        if (obj instanceof PersonFormView) {
        	FilmFormView other = (FilmFormView) obj;
            if (!Objects.equals(this.getId(), other.getId())) {
                return false;
            }
            return true;
        }
		return false;
	}

}
