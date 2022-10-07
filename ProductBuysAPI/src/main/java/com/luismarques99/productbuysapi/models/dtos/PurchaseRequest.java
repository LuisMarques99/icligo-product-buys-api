package com.luismarques99.productbuysapi.models.dtos;

import com.luismarques99.productbuysapi.models.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Abstact clas that represents the structure of a {@link Purchase purchase} generic request.
 *
 * @author Luis Marques
 */
@Getter
@ToString
public abstract class PurchaseRequest {

    /**
     * Product type.
     */
    @NotBlank(message = "Product Type can't be blank")
    @Size(max = 50)
    private String productType;

    /**
     * Expiration date.
     */
    @NotNull
    private LocalDateTime expires;

}
