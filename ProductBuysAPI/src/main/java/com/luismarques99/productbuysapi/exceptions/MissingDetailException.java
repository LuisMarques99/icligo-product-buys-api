package com.luismarques99.productbuysapi.exceptions;

import com.luismarques99.productbuysapi.models.Detail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that represents the structure of a missing {@link Detail detail} exception.
 *
 * @author Luis Marques
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MissingDetailException extends DetailException {

    /**
     * Creates a missing {@link Detail detail} exception.
     */
    public MissingDetailException() {
        super("Detail is missing");
    }

}
