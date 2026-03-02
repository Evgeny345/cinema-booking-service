package ru.kuzin.CornCinema.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureUrlHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		String referer = request.getHeader("referer");
		String redirectUrl = referer.contains("?error")? referer.split("\\?")[0]: referer;
		super.setDefaultFailureUrl(redirectUrl + "?error");
        super.onAuthenticationFailure(request, response, exception);
	}

}
