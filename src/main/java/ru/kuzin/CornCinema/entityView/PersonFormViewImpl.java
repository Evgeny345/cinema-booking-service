package ru.kuzin.CornCinema.entityView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.SerializableEntityViewManager;
import com.blazebit.persistence.view.StaticBuilder;
import com.blazebit.persistence.view.StaticImplementation;
import com.blazebit.persistence.view.spi.type.EntityViewProxy;

import jakarta.annotation.Generated;
import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Person;

@StaticImplementation(PersonFormView.class)
@Generated(value = "com.blazebit.persistence.view.processor.EntityViewAnnotationProcessor")
@StaticBuilder(PersonFormView.class)
public class PersonFormViewImpl implements PersonFormView, EntityViewProxy {
	
	public static volatile EntityViewManager ENTITY_VIEW_MANAGER;
	public static final SerializableEntityViewManager SERIALIZABLE_ENTITY_VIEW_MANAGER = new SerializableEntityViewManager(PersonFormViewImpl.class, ENTITY_VIEW_MANAGER);

	private Integer id;
	private String name;
	private String lastName;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dateOfBirth;
	private List<Amplua> ampluas = new ArrayList<>();
	
	public PersonFormViewImpl() {
		System.out.println("s0");
	}
	
	public PersonFormViewImpl(PersonFormViewImpl noop, Map<String, Object> optionalParameters) {
		System.out.println("s1");
		this.id = null;
		this.name = null;
		this.lastName = null;
		this.dateOfBirth = null;
	}
	
	public PersonFormViewImpl(Integer id) {
		System.out.println("s2");
		this.id = id;
		this.name = null;
		this.lastName = null;
		this.dateOfBirth = null;
	}
	
	public PersonFormViewImpl(Integer id, String name, String lastName, LocalDate dateOfBirth) {
		System.out.println("s3");
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}
	
	public PersonFormViewImpl(String name, String lastName, LocalDate dateOfBirth) {
		System.out.println("s4");
		this.$$_isNew();
		this.name = name;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}
	
	public PersonFormViewImpl(PersonFormViewImpl noop, int offset, Object[] tuple) {
		System.out.println("s5");
		this.id = (Integer) tuple[offset + 0];
		this.name = (String) tuple[offset + 4];
		this.lastName = (String) tuple[offset + 2];
		this.dateOfBirth = (LocalDate) tuple[offset + 1];
		//this.ampluas = (List<Amplua>) tuple[offset + 3];
	}
	
	public PersonFormViewImpl(PersonFormViewImpl noop, int offset, int[] assignment, Object[] tuple) {
		System.out.println("s6");
		this.id = (Integer) tuple[offset + assignment[0]];
		this.name = (String) tuple[offset + assignment[1]];
		this.lastName = (String) tuple[offset + assignment[2]];
		this.dateOfBirth = (LocalDate) tuple[offset + assignment[3]];
	}
	
	@Override
	public Integer getId() {return id;}
	@Override
	public String getName() {return name;}
	@Override
	public String getLastName() {return lastName;}
	@Override
	public LocalDate getDateOfBirth() {return dateOfBirth;}
	@Override
	public List<Amplua> getAmpluas() {return ampluas;}

	@Override
	public void setName(String name) {this.name = name;}
	@Override
	public void setLastName(String lastName) {this.lastName = lastName;}
	@Override
	public void setDateOfBirth(LocalDate dateOfBirth) {this.dateOfBirth = dateOfBirth;}
	public void setAmpluas(List<Amplua> ampluas) {this.ampluas = ampluas;}

	@Override
	public Class<?> $$_getJpaManagedClass() {
		return Person.class;
	}

	@Override
	public Class<?> $$_getJpaManagedBaseClass() {
		return Person.class;
	}

	@Override
	public Class<?> $$_getEntityViewClass() {
		return PersonFormView.class;
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
        	PersonFormView other = (PersonFormView) obj;
            if (!Objects.equals(this.getId(), other.getId())) {
                return false;
            }
            return true;
        }
		return false;
	}
}
