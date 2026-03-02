package ru.kuzin.CornCinema.validators;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kuzin.CornCinema.dao.UserRepository;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {
	
	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		return userRepository.findByUserName(value) == null;
		
	}

}
