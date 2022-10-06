package com.luismarques99.productbuysapi.exceptions;

import com.luismarques99.productbuysapi.models.Detail;

/**
 * Abstract class that represents the structure of a {@link Detail detail} generic exception.
 *
 * @author Luis Marques
 */
public abstract class DetailException extends Exception {

    /**
     * Creates a generic {@link Detail detail} exception with a text message.
     *
     * @param message error message
     */
    public DetailException(String message) {
        super(message);
    }

}
