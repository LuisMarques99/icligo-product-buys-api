package com.luismarques99.productbuysapi.repositories;

import com.luismarques99.productbuysapi.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface that represents the structure of a JPA Repository of {@link Purchase purchases}.
 *
 * @author Luis Marques
 */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query(value = "SELECT * " +
            "FROM purchase " +
            "WHERE expires >= now()",
            nativeQuery = true)
    List<Purchase> findValidPurchases();

}
