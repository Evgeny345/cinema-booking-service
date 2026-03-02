package ru.kuzin.CornCinema.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.ServletException;

@ControllerAdvice
public class CustomExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger(CustomExceptionHandler.class);
	
	@ExceptionHandler(ResponseStatusException.class)
	public String handleNoFound(ResponseStatusException ex) {
		logger.info(ex.getLocalizedMessage());
		return "notFound";
	}
	
	@ExceptionHandler(ServletException.class)
	public String handleServletException(ServletException ex) {
		logger.info(ex.getLocalizedMessage());
		if(ex.getClass().equals(NoResourceFoundException.class))
			return "notFound";
		return "exception";
	}
	 
	@ExceptionHandler(RuntimeException.class)
	public String handleAllExceptions(RuntimeException ex) {
		ex.printStackTrace();
		return "exception";
	}
	
}
