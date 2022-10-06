package com.luismarques99.productbuysapi.exceptions;

import com.luismarques99.productbuysapi.models.Detail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that represents the structure of an already existing {@link Detail detail} exception.
 *
 * @author Luis Marques
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DetailAlreadyExistingException extends DetailException {
    /**
     * Creates an already existing {@link Detail detail} exception.
     */
    public DetailAlreadyExistingException(Long id) {
        super("Detail already exists with id: " + id);
    }
}
