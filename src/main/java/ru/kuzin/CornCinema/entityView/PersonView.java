package ru.kuzin.CornCinema.entityView;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.blazebit.persistence.view.EntityView;

import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.models.Person;

@EntityView(Person.class)
public interface PersonView extends PersonIdView {
	
	String getName();
	String getLastName();
	@DateTimeFormat(iso = ISO.DATE)
	LocalDate getDateOfBirth();
	List<Amplua> getAmpluas();

}
