package ru.kuzin.CornCinema.entityView.ampluaView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.Amplua;

@EntityView(Amplua.class)
public interface AmpluaIdView {
	
	@IdMapping
	Integer getId();

}
