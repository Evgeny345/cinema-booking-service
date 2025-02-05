package ru.kuzin.CornCinema.entityView.hallView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Hall;

@EntityView(Hall.class)
public abstract class HallForCreateForm {
	
	@IdMapping
	public abstract Integer getId();
	public abstract String getName();
	
	public String getShortName() {
		String hallName = getName();
		String shortName = hallName.substring(0, hallName.length() - 5).toLowerCase();
		return shortName;
	}

}
