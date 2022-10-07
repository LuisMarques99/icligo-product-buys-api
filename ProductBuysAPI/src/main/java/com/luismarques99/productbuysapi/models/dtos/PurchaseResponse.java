package com.luismarques99.productbuysapi.models.dtos;

import com.luismarques99.productbuysapi.models.Detail;
import com.luismarques99.productbuysapi.models.Purchase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class that represents the structure of a {@link Purchase purchase} generic response.
 *
 * @author Luis Marques
 */
@AllArgsConstructor
@Getter
@Setter
public class PurchaseResponse {

    /**
     * Numerical identifier.
     */
    private Long id;

    /**
     * Product type.
     */
    private String productType;

    /**
     * Expiration date.
     */
    private LocalDateTime expires;

    /**
     * Purchase {@link Detail details}.
     */
    private List<DetailResponse> purchaseDetails;

}
