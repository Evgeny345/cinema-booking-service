package ru.kuzin.CornCinema.entityView.showTimeView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.SerializableEntityViewManager;
import com.blazebit.persistence.view.StaticImplementation;
import com.blazebit.persistence.view.spi.type.EntityViewProxy;

import jakarta.validation.Valid;
import ru.kuzin.CornCinema.entityView.hallView.HallIdView;
import ru.kuzin.CornCinema.entityView.movieView.MovieIdView;
import ru.kuzin.CornCinema.entityView.priceView.TicketPriceFormImpl;
import ru.kuzin.CornCinema.models.ShowTime;

@StaticImplementation(ShowTimeFormView.class)
public class ShowTimeFormViewImpl implements ShowTimeFormView, EntityViewProxy {
	
	public static volatile EntityViewManager ENTITY_VIEW_MANAGER;
	public static final SerializableEntityViewManager SERIALIZABLE_ENTITY_VIEW_MANAGER = new SerializableEntityViewManager(ShowTimeFormViewImpl.class, ENTITY_VIEW_MANAGER);

	private Integer id;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startTime;
	private HallIdView hall;
	private MovieIdView movie;
	@Valid
	private List<TicketPriceFormImpl> listTicketPrice = new ArrayList<>();
	
	public ShowTimeFormViewImpl() {
		
	}
	
	public ShowTimeFormViewImpl(ShowTimeFormViewImpl noop, Map<String, Object> optionalParameters) {
		this.id = null;
		this.startTime = null;
		this.hall = null;
		this.movie = null;
	}
	
	public ShowTimeFormViewImpl(Integer id) {
		this.id = id;
		this.startTime = null;
		this.hall = null;
		this.movie = null;
	}
	
	public ShowTimeFormViewImpl(Integer id, LocalDateTime startTime, HallIdView hall, MovieIdView movie) {
		this.id = id;
		this.startTime = startTime;
		this.hall = hall;
		this.movie = movie;
	}
	
	public ShowTimeFormViewImpl(ShowTimeFormViewImpl noop, int offset, Object[] tuple) {
		this.id = (Integer) tuple[offset + 0];
		this.startTime = (LocalDateTime) tuple[offset + 3];
		this.hall = (HallIdView) tuple[offset + 2];
		this.movie = (MovieIdView) tuple[offset + 1];
	}

	@Override
	public Integer getId() {return id;}
	@Override
	public LocalDateTime getStartTime() {return startTime;}
	@Override
	public HallIdView getHall() {return hall;}
	@Override
	public MovieIdView getMovie() {return movie;}
	public List<TicketPriceFormImpl> getListTicketPrice() {return listTicketPrice;}

	@Override
	public void setStartTime(LocalDateTime startTime) {this.startTime = startTime;}
	@Override
	public void setHall(HallIdView hall) {this.hall = hall;}
	@Override
	public void setMovie(MovieIdView movie) {this.movie = movie;}

	@Override
	public Class<?> $$_getJpaManagedClass() {
		return ShowTime.class;
	}

	@Override
	public Class<?> $$_getJpaManagedBaseClass() {
		return ShowTime.class;
	}

	@Override
	public Class<?> $$_getEntityViewClass() {
		return ShowTimeFormView.class;
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
        if (obj instanceof ShowTimeFormView) {
        	ShowTimeFormView other = (ShowTimeFormView) obj;
            if (!Objects.equals(this.getId(), other.getId())) {
                return false;
            }
            return true;
        }
		return false;
	}

}
