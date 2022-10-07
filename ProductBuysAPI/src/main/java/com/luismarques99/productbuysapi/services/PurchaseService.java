package com.luismarques99.productbuysapi.services;

import com.luismarques99.productbuysapi.exceptions.PurchaseNotFoundException;
import com.luismarques99.productbuysapi.models.Detail;
import com.luismarques99.productbuysapi.models.Purchase;

import com.luismarques99.productbuysapi.models.dtos.DetailRequest;
import com.luismarques99.productbuysapi.models.dtos.PurchaseCreateRequest;
import com.luismarques99.productbuysapi.models.dtos.PurchaseUpdateRequest;

import java.util.List;

/**
 * Interface that represents the managed behaviours of a {@link Purchase purchase}.
 *
 * @author Luis Marques
 */
public interface PurchaseService {

    /**
     * Saves a {@link Purchase purchase} in the datasource.
     *
     * @param purchase {@link Purchase purchase} to be saved.
     */
    void savePurchase(Purchase purchase);

    /**
     * Creates a {@link Purchase purchase} with the {@link PurchaseCreateRequest requested data}.
     *
     * @param purchaseCreateRequest {@link PurchaseCreateRequest purchase creation request}.
     * @return {@link Purchase purchase}.
     */
    Purchase createPurchase(PurchaseCreateRequest purchaseCreateRequest);

    /**
     * Gets a {@link Purchase purchase} by its id.
     *
     * @param id numerical identifier.
     * @return {@link Purchase purchase}.
     * @throws PurchaseNotFoundException if the {@link Purchase purchase} with the requested id is not found, an
     *                                   exception is thrown.
     */
    Purchase getPurchaseById(Long id) throws PurchaseNotFoundException;

    /**
     * Gets all the {@link Purchase purchases}.
     *
     * @return list of {@link Purchase purchases}.
     */
    List<Purchase> getAllPurchases();

    /**
     * Gets the valid {@link Purchase purchases} (the ones with the expiration date higher or equals to the actual time).
     *
     * @return list of {@link Purchase purchases}.
     */
    List<Purchase> getValidPurchases();

    /**
     * Updates an existing {@link Purchase purchase} with the {@link PurchaseUpdateRequest requested data}.
     *
     * @param purchase              {@link Purchase purchase} to be updated.
     * @param purchaseUpdateRequest {@link PurchaseUpdateRequest purchase update request}.
     * @return updated {@link Purchase purchase}.
     */
    Purchase updatePurchase(Purchase purchase, PurchaseUpdateRequest purchaseUpdateRequest);

    /**
     * Deletes a {@link Purchase purchase}.
     *
     * @param purchase {@link Purchase purchase}.
     */
    void deletePurchase(Purchase purchase);

    /**
     * Adds a {@link Detail purchase detail} to a {@link Purchase purchase}.
     *
     * @param purchase {@link Purchase purchase}.
     * @param detail   {@link Detail detail}.
     */
    Purchase addPurchaseDetail(Purchase purchase, Detail detail);

    /**
     * Removes a {@link Detail purchase detail} from a {@link Purchase purchase}.
     *
     * @param purchase {@link Purchase purchase}.
     * @param detail   {@link Detail detail}.
     */
    Purchase removePurchaseDetail(Purchase purchase, Detail detail);

}
