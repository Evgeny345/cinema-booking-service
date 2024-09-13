package ru.kuzin.CornCinema.models;

import java.util.stream.Stream;



import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AgeRatingConvertor implements AttributeConverter <AgeRating, String>{
	
	@Override
	public String convertToDatabaseColumn(AgeRating attribute) {
		if (attribute == null) {
            return null;
        }
        return attribute.getName();
	}

	@Override
	public AgeRating convertToEntityAttribute(String name) {
		if (name == null) {
            return null;
        }

		return Stream.of(AgeRating.values())
		          .filter(c -> c.getName().equals(name))
		          .findFirst()
		          .orElseThrow(IllegalArgumentException::new);
	}

}
