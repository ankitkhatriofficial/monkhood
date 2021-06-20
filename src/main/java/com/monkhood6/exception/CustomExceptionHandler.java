package com.monkhood6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { NoHandlerFoundException.class })
	public String handle404() {
		return "redirect:/404";
	}

	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	public String exception(HttpRequestMethodNotSupportedException exception, WebRequest request) {
		return "redirect:/404";
	}

	@ExceptionHandler(RuntimeException.class)
	public ModelAndView myError(Exception exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.setViewName("access/500.jsp");
		return mav;
	}
}