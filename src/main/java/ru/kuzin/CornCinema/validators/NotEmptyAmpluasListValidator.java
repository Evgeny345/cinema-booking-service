package ru.kuzin.CornCinema.validators;

import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kuzin.CornCinema.models.Amplua;

public class NotEmptyAmpluasListValidator  implements ConstraintValidator<NotEmptyAmpluasList, Set<Amplua>> {

	@Override
	public boolean isValid(Set<Amplua> value, ConstraintValidatorContext context) {
		
		if(value == null)
			return false;
		return !value.isEmpty();
	}

}
