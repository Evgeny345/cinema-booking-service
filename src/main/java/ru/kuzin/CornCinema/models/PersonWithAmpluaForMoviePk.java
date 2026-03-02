package ru.kuzin.CornCinema.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PersonWithAmpluaForMoviePk implements Serializable {
	
private static final long serialVersionUID = -1623673102723802255L;
	
	private Integer movieId;
	private Integer personId;
	private Integer ampluaId;

}
