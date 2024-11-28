package ru.kuzin.CornCinema.entityView.ampluaView;

import com.blazebit.persistence.view.EntityView;

import ru.kuzin.CornCinema.models.Amplua;

@EntityView(Amplua.class)
public interface AmpluaView extends AmpluaIdView {
	
	String getProfession();

}
