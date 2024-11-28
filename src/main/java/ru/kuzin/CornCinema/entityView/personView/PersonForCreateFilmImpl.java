package ru.kuzin.CornCinema.entityView.personView;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;


import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.SerializableEntityViewManager;
import com.blazebit.persistence.view.StaticImplementation;
import com.blazebit.persistence.view.spi.type.EntityViewProxy;

import ru.kuzin.CornCinema.models.PersonWithAmpluaForFilm;

@StaticImplementation(PersonForCreateFilm.class)
public class PersonForCreateFilmImpl implements PersonForCreateFilm, EntityViewProxy {
	
	public static volatile EntityViewManager ENTITY_VIEW_MANAGER;
	public static final SerializableEntityViewManager SERIALIZABLE_ENTITY_VIEW_MANAGER = new SerializableEntityViewManager(PersonForCreateFilmImpl.class, ENTITY_VIEW_MANAGER);

	private Integer personId;
	private Integer ampluaId;
	
	public PersonForCreateFilmImpl() {
		
	}
	
	public PersonForCreateFilmImpl(PersonForCreateFilmImpl noop, Map<String, Object> optionalParameters) {
		this.personId = null;
		this.ampluaId = null;
	}
	
	public PersonForCreateFilmImpl(Integer personId) {
		this.personId = personId;
		this.ampluaId = null;
	}
	
	public PersonForCreateFilmImpl(Integer personId, Integer ampluaId) {
		this.personId = personId;
		this.ampluaId = ampluaId;
	}
	
	public PersonForCreateFilmImpl(PersonForCreateFilmImpl noop, int offset, Object[] tuple) {
		this.personId = (Integer) tuple[offset + 0];
		this.ampluaId = (Integer) tuple[offset + 1];
	}
	
	@Override
	public Integer getPersonId() {return personId;}
	@Override
	public Integer getAmpluaId() {return ampluaId;}
	
	public void setPersonId(Integer personId) {this.personId = personId;}
	public void setAmpluaId(Integer ampluaId) {this.ampluaId = ampluaId;}
	
	@Override
	public Class<?> $$_getJpaManagedClass() {
		return PersonWithAmpluaForFilm.class;
	}
	
	@Override
	public Class<?> $$_getJpaManagedBaseClass() {
		return PersonWithAmpluaForFilm.class;
	}
	
	@Override
	public Class<?> $$_getEntityViewClass() {
		return PersonForCreateFilm.class;
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
		return Stream.of(ampluaId, personId).filter(x -> x!=null).mapToInt(Integer::intValue).sum();
	}
	
	@Override
	public Object $$_getVersion() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(personId, ampluaId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.$$_getId() == null)
			return false;
		if (obj instanceof EntityViewProxy) {
            EntityViewProxy other = (EntityViewProxy) obj;
            if (this.$$_getJpaManagedBaseClass() == other.$$_getJpaManagedBaseClass() && this.$$_getId().equals(other.$$_getId())) {
                return true;
            }         
            else {
                return false;
            }
        }
		if (obj instanceof PersonForCreateFilmImpl) {
			PersonForCreateFilmImpl other = (PersonForCreateFilmImpl) obj;
            if (!Objects.equals(this.getPersonId(), other.getPersonId()) && !Objects.equals(this.getAmpluaId(), other.getAmpluaId())) {
                return false;
            }
            return true;
        }
		
		return false;
	}

	
	
}
