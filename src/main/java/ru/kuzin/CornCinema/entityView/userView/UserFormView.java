package ru.kuzin.CornCinema.entityView.userView;

import java.time.LocalDateTime;
import java.util.Set;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableMapping;

import ru.kuzin.CornCinema.models.Role;
import ru.kuzin.CornCinema.models.User;

@EntityView(User.class)
@CreatableEntityView(excludedEntityAttributes = "registrationDate")
public interface UserFormView extends UserIdView {
	
	String getUserName();
	String getPassword();
	String getEmail();
	LocalDateTime getRegistrationDate();
	@UpdatableMapping
	Set<Role> getRoles();
	
	void setUserName(String userName);
	void setPassword(String password);
	void setEmail(String email);
	void setRegistrationDate(LocalDateTime registrationDate);

}
