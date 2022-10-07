package com.luismarques99.productbuysapi.controllers;

import com.luismarques99.productbuysapi.exceptions.MissingDetailException;
import com.luismarques99.productbuysapi.exceptions.PurchaseNotFoundException;
import com.luismarques99.productbuysapi.models.Detail;
import com.luismarques99.productbuysapi.models.Purchase;
import com.luismarques99.productbuysapi.models.dtos.*;
import com.luismarques99.productbuysapi.services.DetailService;
import com.luismarques99.productbuysapi.services.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Class that represents the structure of a {@link RestController REST Controller} for the {@link Purchase Purchase}
 * domain.
 *
 * @author Luis Marques
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

    /**
     * {@link PurchaseService Purchase Service}.
     */
    private final PurchaseService purchaseService;

    /**
     * {@link DetailService Detail Service}.
     */
    private final DetailService detailService;

    /**
     * Returns an ok response with a list of all {@link Purchase purchases}.
     *
     * @return list of {@link PurchaseResponse purchase responses}.
     */
    @GetMapping("")
    public ResponseEntity<List<PurchaseResponse>> getAllPurchases() {
        List<PurchaseResponse> purchasesResponse = this.purchaseService.getAllPurchases().stream()
                .map(this::buildPurchaseResponse)
                .toList();
        return ResponseEntity.ok().body(purchasesResponse);
    }

    /**
     * Returns an ok response with the {@link Purchase purchase} found with the provided id.
     *
     * @param id numerical identifier.
     * @return {@link PurchaseResponse Purchase Response}.
     * @throws PurchaseNotFoundException if there is no {@link Purchase purchase} with the requested id, an exception
     *                                   is thrown.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchaseById(@PathVariable Long id) throws PurchaseNotFoundException {
        Purchase purchase = this.purchaseService.getPurchaseById(id);
        PurchaseResponse purchaseResponse = this.buildPurchaseResponse(purchase);
        return ResponseEntity.ok().body(purchaseResponse);
    }

    /**
     * Returns an ok response with a list of valid {@link Purchase purchases}.
     *
     * @return list of {@link PurchaseResponse purchase responses}.
     */
    @GetMapping("/valid")
    public ResponseEntity<List<PurchaseResponse>> getValidPurchases() {
        List<PurchaseResponse> purchasesResponse = this.purchaseService.getValidPurchases().stream()
                .map(this::buildPurchaseResponse)
                .toList();
        return ResponseEntity.ok().body(purchasesResponse);
    }

    /**
     * Returns a created response with the URI for the created {@link Purchase purchase} with the requested data.
     *
     * @param purchaseCreateRequest {@link PurchaseCreateRequest purchase create request}.
     * @return created {@link PurchaseResponse purchase response}.
     */
    @PostMapping("")
    public ResponseEntity<PurchaseResponse> createNewPurchase(@RequestBody @Valid
                                                              PurchaseCreateRequest purchaseCreateRequest) {
        Purchase purchase = this.purchaseService.createPurchase(purchaseCreateRequest);
        this.purchaseService.savePurchase(purchase);
        String path = "/api/v1/purchases" + purchase.getId(); // FIXME: This path shouldn't be hardcoded!
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString());
        PurchaseResponse purchaseResponse = this.buildPurchaseResponse(purchase);
        return ResponseEntity.created(uri).body(purchaseResponse);
    }

    /**
     * Returns a no content response after updating a {@link Purchase purchase} identified by its id with the requested
     * data.
     *
     * @param id                    numerical identifier.
     * @param purchaseUpdateRequest {@link PurchaseUpdateRequest purchase update request}.
     * @return no content response.
     * @throws PurchaseNotFoundException if there is no {@link Purchase purchase} with the requested id, an exception
     *                                   is thrown.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePurchase(@PathVariable Long id,
                                               @RequestBody @Valid PurchaseUpdateRequest purchaseUpdateRequest)
            throws PurchaseNotFoundException {
        Purchase purchase = this.purchaseService.getPurchaseById(id);
        purchase = this.purchaseService.updatePurchase(purchase, purchaseUpdateRequest);
        this.purchaseService.savePurchase(purchase);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns a no content response after deleting a {@link Purchase purchase} identifier by its id.
     *
     * @param id numerical identifier.
     * @return no content response.
     * @throws PurchaseNotFoundException if there is no {@link Purchase purchase} with the requested id, an exception
     *                                   is thrown.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) throws PurchaseNotFoundException {
        Purchase purchase = this.purchaseService.getPurchaseById(id);
        this.purchaseService.deletePurchase(purchase);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns an ok response with a {@link PurchaseResponse purchase response} after adding a {@link Detail detail} to
     * this purchase.
     *
     * @param id                  {@link Purchase purchase} numerical identifier.
     * @param detailCreateRequest {@link DetailCreateRequest detail request data}.
     * @return {@link PurchaseResponse purchase response}.
     * @throws PurchaseNotFoundException if there is no {@link Purchase purchase} with the requested id, an exception
     *                                   is thrown.
     */
    @PostMapping("/{id}/add-detail")
    public ResponseEntity<PurchaseResponse> addPurchaseDetail(@PathVariable Long id,
                                                              @RequestBody @Valid DetailCreateRequest detailCreateRequest)
            throws PurchaseNotFoundException {
        Purchase purchase = this.purchaseService.getPurchaseById(id);
        Detail detail;
        List<Detail> equalDetails = this.detailService.getDetailsByEqualityRules(detailCreateRequest.getDescription(),
                detailCreateRequest.getQuantity(), detailCreateRequest.getValue()); // List with 1 or 0 elements
        if (equalDetails.isEmpty()) {
            detail = this.detailService.createDetail(detailCreateRequest);
            this.detailService.saveDetail(detail);
        } else {
            detail = equalDetails.get(0);
        }
        purchase = this.purchaseService.addPurchaseDetail(purchase, detail);
        this.purchaseService.savePurchase(purchase);
        PurchaseResponse purchaseResponse = this.buildPurchaseResponse(purchase);
        return ResponseEntity.ok().body(purchaseResponse);
    }

    /**
     * Returns an ok response with a {@link PurchaseResponse purchase response} after removing a {@link Detail detail}
     * from this purchase.
     *
     * @param id                  {@link Purchase purchase} numerical identifier.
     * @param detailCreateRequest {@link DetailCreateRequest detail request data}.
     * @return {@link PurchaseResponse purchase response}.
     * @throws PurchaseNotFoundException if there is no {@link Purchase purchase} with the requested id, an exception
     *                                   is thrown.
     * @throws MissingDetailException    if the {@link Detail detail} is non-existent, an exception is thrown.
     */
    @PostMapping("/{id}/remove-detail")
    public ResponseEntity<PurchaseResponse> removePurchaseDetail(@PathVariable Long id,
                                                                 @RequestBody @Valid DetailCreateRequest detailCreateRequest)
            throws PurchaseNotFoundException, MissingDetailException {
        Purchase purchase = this.purchaseService.getPurchaseById(id);
        Detail detail;
        List<Detail> equalDetails = this.detailService.getDetailsByEqualityRules(detailCreateRequest.getDescription(),
                detailCreateRequest.getQuantity(), detailCreateRequest.getValue()); // List with 1 or 0 elements
        if (equalDetails.isEmpty()) {
            throw new MissingDetailException();
        } else {
            detail = equalDetails.get(0);
        }
        purchase = this.purchaseService.removePurchaseDetail(purchase, detail);
        this.purchaseService.savePurchase(purchase);
        PurchaseResponse purchaseResponse = this.buildPurchaseResponse(purchase);
        return ResponseEntity.ok().body(purchaseResponse);
    }

    /**
     * Builds a {@link PurchaseResponse purchase response} with a purchase instance.
     *
     * @param purchase {@link Purchase purchase}.
     * @return {@link PurchaseResponse purchase response}
     */
    private PurchaseResponse buildPurchaseResponse(Purchase purchase) {
        return new PurchaseResponse(
                purchase.getId(),
                purchase.getProductType(),
                purchase.getExpires(),
                purchase.getPurchaseDetails().stream()
                        .map(detail -> new DetailResponse(
                                detail.getId(),
                                detail.getDescription(),
                                detail.getQuantity(),
                                detail.getValue()))
                        .toList()
        );
    }

}
