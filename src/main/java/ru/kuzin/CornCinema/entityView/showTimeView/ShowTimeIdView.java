package ru.kuzin.CornCinema.entityView.showTimeView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.ShowTime;

@EntityView(ShowTime.class)
public interface ShowTimeIdView {
	
	@IdMapping
	Integer getId();

}
