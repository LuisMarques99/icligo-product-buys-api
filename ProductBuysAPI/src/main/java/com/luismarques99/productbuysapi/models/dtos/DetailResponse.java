package com.luismarques99.productbuysapi.models.dtos;

import com.luismarques99.productbuysapi.models.Detail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents the structure of a {@link Detail detail} generic response.
 *
 * @author Luis Marques
 */
@AllArgsConstructor
@Getter
@Setter
public class DetailResponse {

    /**
     * Numerical idenifier.
     */
    private Long id;

    /**
     * Description.
     */
    private String description;

    /**
     * Quantity.
     */
    private Integer quantity;

    /**
     * Value.
     */
    private Double value;

}
