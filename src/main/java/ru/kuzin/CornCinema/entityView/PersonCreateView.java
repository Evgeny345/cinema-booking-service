package ru.kuzin.CornCinema.entityView;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.PostConvert;
import com.blazebit.persistence.view.UpdatableEntityView;
import com.blazebit.persistence.view.UpdatableMapping;

import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Person;

@EntityView(Person.class)
@CreatableEntityView
@UpdatableEntityView
public interface PersonCreateView {
	
	@IdMapping
	Integer getId();
	String getName();
	String getLastName();
	@DateTimeFormat(iso = ISO.DATE)
	LocalDate getDateOfBirth();
	@UpdatableMapping
	List<Amplua> getAmpluas();
	
	public void setName(String name);
	public void setLastName(String lastName);
	public void setDateOfBirth(LocalDate dateOfBirth);
	
	@PostConvert
    default void postConvert() {
        System.out.println("listener");
    }

}
