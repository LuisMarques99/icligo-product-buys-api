package com.luismarques99.productbuysapi.handlers;

import com.luismarques99.productbuysapi.models.Error;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class that represents the structure of a {@link GlobalExceptionHandler Global Exception Handler} built using a
 * {@link ControllerAdvice Controller Advice}.
 *
 * @author Luis Marques
 */
@AllArgsConstructor
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * {@link MessageSource Message Source}
     */
    private MessageSource messageSource;

    /**
     * Handles the exceptions caused by one or more mehtod arguments not being valid.
     *
     * @param exception {@link MethodArgumentNotValidException Method Argument Not Valid Exception}.
     * @param headers   {@link HttpHeaders HTTP Headers}.
     * @param status    {@link HttpStatus HTTP Status}.
     * @param request   {@link WebRequest Web Request}.
     * @return response with the exception and the error entity built.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Error error = new Error(status.value(), status.getReasonPhrase(), "One or more fields are invalid.");
        exception.getBindingResult().getAllErrors().forEach(objectError -> error.addField(new Error.Field(
                ((FieldError) objectError).getField(),
                messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
        )));
        log.error("\nMethod with invalid arguments error occurred: {}", error.toString());
        return handleExceptionInternal(exception, error, headers, status, request);
    }

    /**
     * Handles all the uncaught {@link Exception exceptions}.
     *
     * @param exception {@link Exception Exception}.
     * @param request   {@link WebRequest Web Request}.
     * @return response with the exception and the error entity built.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleUncaughtException(Exception exception, WebRequest request) {
        Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Unknown error occurred.");
        error.addField(new Error.Field("message", exception.getMessage()));
        log.error("\nUnknown error occurred: {}", error.toString());
        return handleExceptionInternal(exception, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
