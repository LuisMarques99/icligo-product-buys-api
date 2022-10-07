package com.luismarques99.productbuysapi.exceptions;

import com.luismarques99.productbuysapi.models.Purchase;

/**
 * Abstract class that represents the structure of a {@link Purchase purchase} generic exception.
 *
 * @author Luis Marques
 */
public abstract class PurchaseException extends Exception {

    /**
     * Creates a generic {@link Purchase purchase} exception with a text message.
     *
     * @param message error message.
     */
    public PurchaseException(String message) {
        super(message);
    }

}
