package ru.kuzin.CornCinema.utilites;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.kuzin.CornCinema.models.Amplua;
import ru.kuzin.CornCinema.service.AmpluaService;

@Component
public class AmpluaEditor extends PropertyEditorSupport {
	
	private AmpluaService ampluaService;
	
	@Autowired
	public AmpluaEditor(AmpluaService ampluaService) {
		this.ampluaService = ampluaService;
	}
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {
		Integer id = Integer.parseInt(text);
		Amplua amplua = ampluaService.getAmpluaById(id);
		setValue(amplua);
	}

}
