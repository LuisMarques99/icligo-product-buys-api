package com.luismarques99.productbuysapi.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the structure of a purchase.
 *
 * @author Luis Marques
 */
@Entity
@Table(name = "purchase")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class Purchase {

    /**
     * Numerical identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Product type.
     */
    @Column(name = "product_type")
    @NonNull
    @Setter
    private String productType;

    /**
     * Expiration date.
     */
    @Column(name = "expires")
    @NonNull
    @Setter
    private LocalDateTime expires;

    /**
     * Purchase {@link Detail details}.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "purchase_details",
            joinColumns = @JoinColumn(name = "purchase_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id", referencedColumnName = "id"))
    @ToString.Exclude
    private final List<Detail> purchaseDetails = new ArrayList<>();

    /**
     * Adds a purchase {@link Detail detail}.
     *
     * @param detail {@link Detail purchase detail}.
     */
    public void addPurchaseDetail(Detail detail) {
        if (!this.purchaseDetails.contains(detail)) {
            this.purchaseDetails.add(detail);
        }
    }

    /**
     * Removes a purchase {@link Detail detail}.
     *
     * @param detail {@link Detail purchase detail}.
     */
    public void removePurchaseDetail(Detail detail) {
        this.purchaseDetails.remove(detail);
    }

}
