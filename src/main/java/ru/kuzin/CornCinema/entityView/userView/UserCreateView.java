package ru.kuzin.CornCinema.entityView.userView;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;

import ru.kuzin.CornCinema.models.User;

@EntityView(User.class)
@CreatableEntityView
@UpdatableEntityView
public interface UserCreateView extends UserFormView {
	
	
	
}
