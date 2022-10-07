package com.luismarques99.productbuysapi.services;

import com.luismarques99.productbuysapi.exceptions.DetailAlreadyExistingException;
import com.luismarques99.productbuysapi.exceptions.DetailNotFoundException;
import com.luismarques99.productbuysapi.models.Detail;
import com.luismarques99.productbuysapi.models.dtos.DetailCreateRequest;
import com.luismarques99.productbuysapi.models.dtos.DetailRequest;
import com.luismarques99.productbuysapi.models.dtos.DetailUpdateRequest;

import java.util.List;

/**
 * Interface that represents the managed behaviours of a {@link Detail detail}.
 *
 * @author Luis Marques
 */
public interface DetailService {

    /**
     * Saves a {@link Detail detail} in the datasource.
     *
     * @param detail {@link Detail detail} to be saved.
     */
    void saveDetail(Detail detail);

    /**
     * Creates a {@link Detail detail} with the {@link DetailCreateRequest requested data}.
     *
     * @param detailCreateRequest {@link DetailCreateRequest detail creation request}.
     * @return {@link Detail detail}.
     */
    Detail createDetail(DetailCreateRequest detailCreateRequest);

    /**
     * Gets a single {@link Detail detail} by its id.
     *
     * @param id numerical identifier.
     * @return {@link Detail detail}.
     * @throws DetailNotFoundException if the {@link Detail detail} with the requested id is not found, an exception is
     *                                 thrown.
     */
    Detail getDetailById(Long id) throws DetailNotFoundException;

    /**
     * Gets the {@link Detail details} with the provided equality rules.
     *
     * @param description description.
     * @param quantity    quantity.
     * @param value       value.
     * @return list of {@link Detail details}.
     */
    List<Detail> getDetailsByEqualityRules(String description, Integer quantity, Double value);

    /**
     * Gets the {@link Detail details} containing a single word or a couple of words in the description.
     *
     * @param expression text expression.
     * @return list of {@link Detail details}.
     */
    List<Detail> getDetailsByExpressionContainedInDescription(String expression);

    /**
     * Gets all the {@link Detail details}.
     *
     * @return list of {@link Detail details}.
     */
    List<Detail> getAllDetails();

    /**
     * Updates an existing {@link Detail detail} with the provided id and {@link DetailUpdateRequest requested data}.
     *
     * @param detail              current {@link Detail detail} to be updated.
     * @param detailUpdateRequest {@link DetailUpdateRequest detail update request}.
     * @return {@link Detail detail}.
     */
    Detail updateDetail(Detail detail, DetailUpdateRequest detailUpdateRequest);

    /**
     * Deletes an existing {@link Detail detail}.
     *
     * @param detail {@link Detail detail}.
     */
    void deleteDetail(Detail detail);

    /**
     * Validates if there are any other {@link Detail detail} with the same equality parameters.
     *
     * @param detailRequest {@link DetailRequest detail request data}.
     * @throws DetailAlreadyExistingException if there is already an existing {@link Detail detail} with the same data,
     *                                        an exception is thrown.
     */
    void validateUniqueness(DetailRequest detailRequest) throws DetailAlreadyExistingException;

}
