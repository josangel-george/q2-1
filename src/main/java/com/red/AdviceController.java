package com.red;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdviceController {

	private static final Logger log = LoggerFactory.getLogger(AdviceController.class);
	
	@ExceptionHandler(SessionExpiredException.class)
	public ModelAndView handleSessionExpirationException(SessionExpiredException e){
		
		log.info("Handled sessionExpirationException, redirecting to register page.");

		log.error(e.getMessage());
		e.printStackTrace();
		
		return new ModelAndView("redirect:/register");
	}
}
