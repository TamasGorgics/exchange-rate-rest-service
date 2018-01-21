package com.tgorgics.demo.error;

import com.tgorgics.demo.error.exception.ExchangeRateNotFound;
import com.tgorgics.demo.error.exception.InvalidRateTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = String.format("%s parameter is required!", ex.getParameterName());
        ErrorDetails errorDetails = ErrorDetails.builder().status(HttpStatus.BAD_REQUEST).error(error).message(ex.getLocalizedMessage()).build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = String.format("Input object is not valid json format!");
        ErrorDetails errorDetails = ErrorDetails.builder().status(HttpStatus.BAD_REQUEST).error(error).message(ex.getLocalizedMessage()).build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = String.format("Invalid argument: %s", ex.getParameter());
        ErrorDetails errorDetails = ErrorDetails.builder().status(HttpStatus.BAD_REQUEST).error(error).message(ex.getLocalizedMessage()).build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = String.format("Could not found handler to fulfil request: %s", ex.getRequestURL());
        ErrorDetails errorDetails = ErrorDetails.builder().status(HttpStatus.BAD_REQUEST).error(error).message(ex.getLocalizedMessage()).build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder errors = new StringBuilder();

        ex.getConstraintViolations().forEach(violation -> {
            errors.append(String.format("%s %s: %s ", violation.getRootBeanClass().getName(),
                    violation.getPropertyPath(), violation.getMessage()));
        });

        ErrorDetails errorDetails = ErrorDetails.builder().status(HttpStatus.BAD_REQUEST).message(ex.getLocalizedMessage()).error(errors.toString()).build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(InvalidRateTypeException.class)
    public ResponseEntity<ErrorDetails> handleInvalidRateType(InvalidRateTypeException ex) {
        String error = String.format("Invalid exchange rate type: %s. Type can only be 'buying' or 'selling'", ex.getRateType());
        ErrorDetails errorDetails = ErrorDetails.builder().status(HttpStatus.BAD_REQUEST).error(error).message(ex.getLocalizedMessage()).build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(ExchangeRateNotFound.class)
    public ResponseEntity<ErrorDetails> handleExchangeRateNotFound(ExchangeRateNotFound ex) {
        String error = String.format("Cannot find exchange rate from %s to %s", ex.getCurrencyFrom(), ex.getCurrencyTo());
        ErrorDetails errorDetails = ErrorDetails.builder().status(HttpStatus.BAD_REQUEST).error(error).message(ex.getLocalizedMessage()).build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

}
