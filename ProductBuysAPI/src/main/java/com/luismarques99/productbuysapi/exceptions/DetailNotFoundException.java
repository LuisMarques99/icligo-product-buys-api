package com.luismarques99.productbuysapi.exceptions;

import com.luismarques99.productbuysapi.models.Detail;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that represents the structure of a not found {@link Detail detail} exception.
 *
 * @author Luis Marques
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DetailNotFoundException extends DetailException {

    /**
     * Creates a not found {@link Detail detail} exception.
     *
     * @param id numerical identifier
     */
    public DetailNotFoundException(Long id) {
        super("Could not find detail with id: " + id);
    }

}
