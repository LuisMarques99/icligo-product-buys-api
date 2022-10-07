package com.luismarques99.productbuysapi.services;

import com.luismarques99.productbuysapi.exceptions.PurchaseNotFoundException;
import com.luismarques99.productbuysapi.models.Detail;
import com.luismarques99.productbuysapi.models.Purchase;
import com.luismarques99.productbuysapi.models.dtos.PurchaseCreateRequest;
import com.luismarques99.productbuysapi.models.dtos.PurchaseUpdateRequest;
import com.luismarques99.productbuysapi.repositories.PurchaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Class that represents the implementation of the {@link PurchaseService Purchase Service} that uses a database as the
 * datasource.
 *
 * @author Luis Marques
 */
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DatabasePurchaseService implements PurchaseService {

    /**
     * {@link PurchaseRepository Purchase Repository}.
     */
    private final PurchaseRepository purchaseRepository;

    @Override
    public void savePurchase(Purchase purchase) {
        log.info("\nSaving purchase: {}", purchase.toString());
        this.purchaseRepository.save(purchase);
    }

    @Override
    public Purchase createPurchase(PurchaseCreateRequest purchaseCreateRequest) {
        log.info("\nCreating new purchase: {}", purchaseCreateRequest.toString());
        return new Purchase(purchaseCreateRequest.getProductType(), purchaseCreateRequest.getExpires());
    }

    @Override
    public Purchase getPurchaseById(Long id) throws PurchaseNotFoundException {
        log.info("\nFetching purchase with id: {}", id);
        return this.purchaseRepository.findById(id).orElseThrow(() -> new PurchaseNotFoundException(id));
    }

    @Override
    public List<Purchase> getAllPurchases() {
        log.info("\nFetching all purchases");
        return this.purchaseRepository.findAll();
    }

    @Override
    public List<Purchase> getValidPurchases() {
        log.info("\nFetching valid purchases");
        return this.purchaseRepository.findValidPurchases();
    }

    @Override
    public Purchase updatePurchase(Purchase purchase, PurchaseUpdateRequest purchaseUpdateRequest) {
        log.info("\nUpdating purchase: {}\n  > New one: {}", purchase.toString(), purchaseUpdateRequest.toString());
        purchase.setProductType(purchaseUpdateRequest.getProductType());
        purchase.setExpires(purchaseUpdateRequest.getExpires());
        return purchase;
    }

    @Override
    public void deletePurchase(Purchase purchase) {
        log.info("\nDeleting purchase: {}", purchase);
        this.purchaseRepository.delete(purchase);
    }

    @Override
    public Purchase addPurchaseDetail(Purchase purchase, Detail detail) {
        log.info("\nAdding purchase detail: {}\n  to purchase: {}", detail.toString(), purchase.toString());
        purchase.addPurchaseDetail(detail);
        return purchase;
    }

    @Override
    public Purchase removePurchaseDetail(Purchase purchase, Detail detail) {
        log.info("\nRemoving purchase detail: {}\n  from purchase: {}", detail.toString(), purchase.toString());
        purchase.removePurchaseDetail(detail);
        return purchase;
    }

}
