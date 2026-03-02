package ru.kuzin.CornCinema.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

	private UserDetailsService userDetailsService;
	private CustomAuthenticationFailureUrlHandler customAuthenticationFailureUrlHandler;
	
	@Autowired
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Autowired
	public void setCustomAuthenticationFailureUrlHandler(
			CustomAuthenticationFailureUrlHandler customAuthenticationFailureUrlHandler) {
		this.customAuthenticationFailureUrlHandler = customAuthenticationFailureUrlHandler;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
					.requestMatchers("/movies/admin/**").hasRole("ADMIN")
					.requestMatchers("/movies/**").permitAll()
					.requestMatchers("/showTimes/admin/**").hasRole("ADMIN")
					.requestMatchers("/showTimes/**").hasAnyRole("USER", "ANONYMOUS")
					.requestMatchers("/users/registrationForm").hasRole("ANONYMOUS")
					.requestMatchers("/users/reservations").hasRole("USER")
					.requestMatchers("/users/**").permitAll()
					.requestMatchers("/**").permitAll());
		
		http.formLogin(formLoginConfigurer -> formLoginConfigurer
				.failureHandler(customAuthenticationFailureUrlHandler)
				.successHandler(customAuthenticationSuccessHandler()));
				
					
		
		http.logout(logoutConfigurer -> logoutConfigurer
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.permitAll());
		
		http.exceptionHandling(exception -> exception
				.defaultAuthenticationEntryPointFor(loginUrlauthenticationEntryPoint(), new AntPathRequestMatcher("/**"))
				.accessDeniedPage("/accessDenied"));
		
		http.securityContext(contextConfigurer -> contextConfigurer
					.securityContextRepository(securityContextRepository()));
		
		http.sessionManagement(httpSecuritySessionManagementConfigurer -> 
			httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
		
		
		return http.build();
	}
	
	@Bean
	public AuthenticationEntryPoint loginUrlauthenticationEntryPoint(){
	    return new LoginUrlAuthenticationEntryPoint("/users/login");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }
	
	@Bean
	public SecurityContextRepository securityContextRepository() {
	    return new DelegatingSecurityContextRepository(
	    	   new RequestAttributeSecurityContextRepository(),
	           new HttpSessionSecurityContextRepository()
	    );
	  }
	
	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomUrlAuthenticationSuccessHandler();
	}
	
}
