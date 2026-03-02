package ru.kuzin.CornCinema.entityView.userView;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

import ru.kuzin.CornCinema.models.User;

@EntityView(User.class)
public interface UserIdView {
	
	@IdMapping
	Integer getId();

}
