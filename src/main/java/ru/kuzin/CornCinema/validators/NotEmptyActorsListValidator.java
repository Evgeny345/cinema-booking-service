package ru.kuzin.CornCinema.validators;


import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.kuzin.CornCinema.entityView.personView.PersonForCreateFilmImpl;

public class NotEmptyActorsListValidator implements ConstraintValidator<NotEmptyActorsList, Set<PersonForCreateFilmImpl>> {

	@Override
    public boolean isValid(Set<PersonForCreateFilmImpl> values, ConstraintValidatorContext context) {
		
		if(values == null)
			return false;
        return values.stream().anyMatch(a -> a.getAmpluaId().equals(2));
	}

}
