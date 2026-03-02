package ru.kuzin.CornCinema.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class SecurityService {
	
	private UserDetailsServiceImpl userDetailsService;
	private AuthenticationManager authenticationManager;
	private SecurityContextRepository securityContextRepository;
	

	@Autowired
	public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {this.userDetailsService = userDetailsService;}
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {this.authenticationManager = authenticationManager;}
	@Autowired
	public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {this.securityContextRepository = securityContextRepository;}
	
	public void autoLogin(String userName, String duplicatePassword, HttpServletRequest request,  HttpServletResponse response) {
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        		userDetails, duplicatePassword, userDetails.getAuthorities());
		
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
		SecurityContext context = securityContextHolderStrategy.createEmptyContext();
		context.setAuthentication(authentication);
	    securityContextHolderStrategy.setContext(context);
	    
	    securityContextRepository.saveContext(context, request, response);
        
	}
	

}
