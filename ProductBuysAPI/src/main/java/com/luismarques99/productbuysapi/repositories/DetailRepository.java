package com.luismarques99.productbuysapi.repositories;

import com.luismarques99.productbuysapi.models.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface that represents the structure of a JPA Repository of {@link Detail details}.
 *
 * @author Luis Marques
 */
public interface DetailRepository extends JpaRepository<Detail, Long> {

    /**
     * Finds the {@link Detail details} that contain the provided text expression in the description.
     *
     * @param expression text expression.
     * @return list of {@link Detail details}.
     */
    @Query(value = "SELECT * " +
            "FROM detail " +
            "WHERE description LIKE concat('%', :expression, '%')",
            nativeQuery = true)
    List<Detail> findByExpressionContainedInDescription(@Param(value = "expression") String expression);


    /**
     * Finds the {@link Detail details} with the provided equality parameters. This should only return one element,
     * but for SQL basic rules purposes it must return a list because the SELECT SINGLE should only be done with
     * the primary key.
     *
     * @param description description.
     * @param quantity quantity.
     * @param value value.
     * @return list of {@link Detail details}.
     */
    @Query(value = "SELECT * " +
            "FROM detail " +
            "WHERE description = :description " +
            "  AND quantity = :quantity " +
            "  AND value = :value",
            nativeQuery = true)
    List<Detail> findByEqualityRules(@Param(value = "description") String description,
                                     @Param(value = "quantity") Integer quantity, @Param(value = "value") Double value);


}
