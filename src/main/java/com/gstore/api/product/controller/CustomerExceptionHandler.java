package com.gstore.api.product.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.gstore.api.product.model.ResponseBean;
import com.gstore.api.product.util.ApiConstants;
import com.gstore.api.product.util.ApiException;

import io.jsonwebtoken.SignatureException;




@ControllerAdvice
public class CustomerExceptionHandler {

	@ExceptionHandler(value = { MissingServletRequestParameterException.class, MissingPathVariableException.class,
			ServletRequestBindingException.class, ConstraintViolationException.class, IllegalArgumentException.class,
			ApiException.class, BadCredentialsException.class, InternalAuthenticationServiceException.class,JsonParseException.class,SignatureException.class

	})
	protected ResponseEntity<ResponseBean<String>> handleRequestException(Exception ex, WebRequest request) {
		ResponseBean<String> rb = new ResponseBean<>();
		if (ex instanceof ConstraintViolationException) {
			StringBuilder sb = new StringBuilder();
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;
			for (ConstraintViolation<?> exception : constraintViolationException.getConstraintViolations()) {
				sb.append(exception.getPropertyPath());
				sb.append(" ");
				sb.append(exception.getMessage());
				sb.append(", invalid value: [");
				sb.append(exception.getInvalidValue());
				sb.append("]");
			}
			rb.setErrorMessage(sb.toString());
			rb.setStatus(ApiConstants.FAILED);
			rb.setStatusCode(400);
		}

		else {
			rb.setErrorMessage(ex.getMessage());
			rb.setStatus(com.gstore.api.product.util.ApiConstants.FAILED);
			if (ex instanceof ApiException) {
				ApiException e = (ApiException) ex;
				rb.setStatusCode(e.getCode());
				return ResponseEntity.status(e.getCode()).contentType(MediaType.APPLICATION_JSON).body(rb);
			} else if (ex instanceof BadCredentialsException || ex instanceof InternalAuthenticationServiceException) {
				rb.setStatusCode(401);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(rb);
			} else {
				rb.setStatusCode(400);
			}

		}

		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(rb);
	}

	@ExceptionHandler(value = { Exception.class, ClassNotFoundException.class })
	protected ResponseEntity<ResponseBean<String>> handleIllegalArgumentException(Exception ex, WebRequest request) {
		ResponseBean<String> rb = new ResponseBean<>();
		rb.setErrorMessage(ex.getMessage());
		rb.setStatus(ApiConstants.FAILED);
		
		rb.setStatusCode(500);
		

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(rb);
	}
	
	public ResponseEntity<ResponseBean<String>> handleRequestException1(Exception ex) {
		ResponseBean<String> rb = new ResponseBean<>();
		if (ex instanceof ConstraintViolationException) {
			StringBuilder sb = new StringBuilder();
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;
			for (ConstraintViolation<?> exception : constraintViolationException.getConstraintViolations()) {
				sb.append(exception.getPropertyPath());
				sb.append(" ");
				sb.append(exception.getMessage());
				sb.append(", invalid value: [");
				sb.append(exception.getInvalidValue());
				sb.append("]");
			}
			rb.setErrorMessage(sb.toString());
			rb.setStatus(ApiConstants.FAILED);
			rb.setStatusCode(400);
		}

		else {
			rb.setErrorMessage(ex.getMessage());
			rb.setStatus(com.gstore.api.product.util.ApiConstants.FAILED);
			if (ex instanceof ApiException) {
				ApiException e = (ApiException) ex;
				rb.setStatusCode(e.getCode());
				return ResponseEntity.status(e.getCode()).contentType(MediaType.APPLICATION_JSON).body(rb);
			} else if (ex instanceof BadCredentialsException || ex instanceof InternalAuthenticationServiceException) {
				rb.setStatusCode(401);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(rb);
			} else if(ex instanceof SignatureException)
			{
				rb.setStatusCode(401);
				rb.setErrorMessage("Invalid token.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(rb);
			 
			}else
			{
				rb.setStatusCode(400);
			}

		}

		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(rb);
	}
}
