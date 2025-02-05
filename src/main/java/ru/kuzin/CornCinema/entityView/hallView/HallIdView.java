package ru.kuzin.CornCinema.entityView.hallView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Hall;

@EntityView(Hall.class)
public interface HallIdView {
	
	@IdMapping
	Integer getId();

}
