package com.luismarques99.productbuysapi.models.dtos;

import com.luismarques99.productbuysapi.models.Detail;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Abstract class that represents the structure of a {@link Detail detail} generic request.
 *
 * @author Luis Marques
 */
@Getter
@ToString
public abstract class DetailRequest {

    /**
     * Description.
     */
    @NotBlank(message = "Description can't be blank")
    @Size(max = 100)
    private String description;

    /**
     * Quantity.
     */
    @NotBlank(message = "Quantity can't be blank")
    private Integer quantity;

    /**
     * Value.
     */
    @NotBlank(message = "Value can't be blank")
    private Double value;

}
