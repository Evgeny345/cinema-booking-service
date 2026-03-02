package ru.kuzin.CornCinema.service;

import ru.kuzin.CornCinema.entityView.userView.UserFormView;
import ru.kuzin.CornCinema.entityView.userView.UserFormViewImpl;
import ru.kuzin.CornCinema.models.User;

public interface UserService {
	
	UserFormView getUserForm();
	
	User getUserByUserName(String userName);
	
	void createUser(UserFormViewImpl userForm, String roleName);
	
	long amountOfUsers();

}
