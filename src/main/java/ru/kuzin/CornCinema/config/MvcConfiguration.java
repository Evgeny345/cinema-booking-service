package ru.kuzin.CornCinema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	
	@Value("${upload_movie_poster.path}")
	private String posterUploadPath;
	@Value("${upload_qr_code.path}")
	private String qrUploadPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/imgDB/**")
		        .addResourceLocations("file:///" + posterUploadPath + "/")
		        .setCachePeriod(0);
		registry.addResourceHandler("/imgQR/**")
				.addResourceLocations("file:///" + qrUploadPath + "/")
				.setCachePeriod(0);
				
	}

}
