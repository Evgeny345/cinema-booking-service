package ru.kuzin.CornCinema.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomUrlAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
 
		String referer = request.getHeader("referer");
		String targetUrl = referer.contains("?error")? referer.split("\\?")[0]: referer;
        super.setDefaultTargetUrl(targetUrl);		
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
