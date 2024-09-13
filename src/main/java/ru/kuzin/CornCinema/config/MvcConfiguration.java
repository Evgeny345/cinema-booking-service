package ru.kuzin.CornCinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ru.kuzin.CornCinema.utilites.AmpluaFormatter;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	
	@Override
    public void addFormatters (FormatterRegistry registry) {
		registry.addFormatter(ampluaFormatter());
	}
	
	@Bean
	public AmpluaFormatter ampluaFormatter() {
		return new AmpluaFormatter();
	}

}
