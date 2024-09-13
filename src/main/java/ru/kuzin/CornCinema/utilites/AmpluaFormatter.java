package ru.kuzin.CornCinema.utilites;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.service.AmpluaService;

public class AmpluaFormatter implements Formatter<Amplua> {
	
	private AmpluaService ampluaService;

	@Autowired
	public void setAmpluaService(AmpluaService ampluaService) {this.ampluaService = ampluaService;}

	@Override
	public String print(Amplua object, Locale locale) {
		return null;
	}

	@Override
	public Amplua parse(String text, Locale locale) throws ParseException {
		Integer id = Integer.parseInt(text);
		Amplua amplua = ampluaService.getAmpluaById(id);
		return amplua;
	}

}
