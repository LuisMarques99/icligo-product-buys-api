package com.luismarques99.productbuysapi.exceptions;

import com.luismarques99.productbuysapi.models.Purchase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class that represents the structure of a not found {@link Purchase purchase} exception.
 *
 * @author Luis Marques
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PurchaseNotFoundException extends PurchaseException {

    /**
     * Creates a not found {@link Purchase purchase} exception.
     *
     * @param id numerical identifier.
     */
    public PurchaseNotFoundException(Long id) {
        super("Could not find purchase with id: " + id);
    }
}
