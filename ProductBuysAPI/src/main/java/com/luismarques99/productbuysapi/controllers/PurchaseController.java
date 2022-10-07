package com.luismarques99.productbuysapi.controllers;

import com.luismarques99.productbuysapi.exceptions.DetailNotFoundException;
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

    @GetMapping("")
    public ResponseEntity<List<PurchaseResponse>> getAllPurchases() {
        List<PurchaseResponse> purchasesResponse = this.purchaseService.getAllPurchases().stream()
                .map(this::buildPurchaseResponse)
                .toList();
        return ResponseEntity.ok().body(purchasesResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchaseById(@PathVariable Long id) throws PurchaseNotFoundException {
        Purchase purchase = this.purchaseService.getPurchaseById(id);
        PurchaseResponse purchaseResponse = this.buildPurchaseResponse(purchase);
        return ResponseEntity.ok().body(purchaseResponse);
    }

    @GetMapping("/valid")
    public ResponseEntity<List<PurchaseResponse>> getValidPurchases() {
        List<PurchaseResponse> purchasesResponse = this.purchaseService.getValidPurchases().stream()
                .map(this::buildPurchaseResponse)
                .toList();
        return ResponseEntity.ok().body(purchasesResponse);
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePurchase(@PathVariable Long id,
                                               @RequestBody @Valid PurchaseUpdateRequest purchaseUpdateRequest)
            throws PurchaseNotFoundException {
        Purchase purchase = this.purchaseService.getPurchaseById(id);
        purchase = this.purchaseService.updatePurchase(purchase, purchaseUpdateRequest);
        this.purchaseService.savePurchase(purchase);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) throws PurchaseNotFoundException {
        Purchase purchase = this.purchaseService.getPurchaseById(id);
        this.purchaseService.deletePurchase(purchase);
        return ResponseEntity.noContent().build();
    }

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
