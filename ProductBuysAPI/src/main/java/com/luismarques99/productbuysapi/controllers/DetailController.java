package com.luismarques99.productbuysapi.controllers;

import com.luismarques99.productbuysapi.exceptions.DetailAlreadyExistingException;
import com.luismarques99.productbuysapi.exceptions.DetailNotFoundException;
import com.luismarques99.productbuysapi.models.Detail;
import com.luismarques99.productbuysapi.models.dtos.DetailCreateRequest;
import com.luismarques99.productbuysapi.models.dtos.DetailResponse;
import com.luismarques99.productbuysapi.models.dtos.DetailUpdateRequest;
import com.luismarques99.productbuysapi.services.DetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Class that represents the structure of a {@link RestController REST Controller} for the {@link Detail Detail} domain.
 * It holds the response given for each request.
 *
 * @author Luis Marques
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/details")
public class DetailController {

    /**
     * {@link DetailService Detail Service}.
     */
    private final DetailService detailService;

    /**
     * Returns an ok response with a list of all {@link Detail details}.
     *
     * @return list of {@link DetailResponse detail responses}.
     */
    @GetMapping("")
    public ResponseEntity<List<DetailResponse>> getAllDetails() {
        List<DetailResponse> detailsResponse = this.detailService.getAllDetails().stream()
                .map(detail -> new DetailResponse(
                        detail.getId(),
                        detail.getDescription(),
                        detail.getQuantity(),
                        detail.getValue()))
                .toList();
        return ResponseEntity.ok().body(detailsResponse);
    }

    /**
     * Returns an ok response with the {@link Detail detail} found with the provided id.
     *
     * @param id numerical identifier
     * @return {@link DetailResponse Detail Response}.
     * @throws DetailNotFoundException if there is no {@link Detail detail} with the requested id, an exception is
     *                                 thrown.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetailResponse> getDetailById(@PathVariable Long id) throws DetailNotFoundException {
        Detail detail = this.detailService.getDetailById(id);
        DetailResponse detailResponse = new DetailResponse(
                detail.getId(),
                detail.getDescription(),
                detail.getQuantity(),
                detail.getValue()
        );
        return ResponseEntity.ok().body(detailResponse);
    }

    /**
     * Returns an ok response with the {@link Detail details} that contain a text expression in the description.
     *
     * @param expression text expression.
     * @return list of {@link DetailResponse}.
     */
    @GetMapping(path = "", params = "expression")
    public ResponseEntity<List<DetailResponse>> getDetailsByExpressionContainedInDescription(
            @RequestParam(required = false) String expression) {
        List<DetailResponse> detailsResponse = this.detailService.getDetailsByExpressionContainedInDescription(expression)
                .stream().map(detail -> new DetailResponse(
                        detail.getId(),
                        detail.getDescription(),
                        detail.getQuantity(),
                        detail.getValue()))
                .toList();
        return ResponseEntity.ok().body(detailsResponse);
    }

    /**
     * Returns a created response with the URI for the created {@link Detail detail} with the requested data.
     *
     * @param detailCreateRequest {@link DetailCreateRequest detail create request}
     * @return created {@link DetailResponse detail response}.
     * @throws DetailAlreadyExistingException if there is an equal {@link Detail detail} existing, an exception is
     *                                        thrown.
     */
    @PostMapping("")
    public ResponseEntity<DetailResponse> createNewDetail(@RequestBody @Valid DetailCreateRequest detailCreateRequest)
            throws DetailAlreadyExistingException {
        Detail detail = this.detailService.createDetail(detailCreateRequest);
        String path = "/api/v1/details" + detail.getId();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(path).toUriString());
        this.detailService.saveDetail(detail);
        DetailResponse detailResponse = new DetailResponse(
                detail.getId(),
                detail.getDescription(),
                detail.getQuantity(),
                detail.getValue()
        );
        return ResponseEntity.created(uri).body(detailResponse);
    }

    /**
     * Returns a no content response after updating a {@link Detail detail} identified by its id with the requested
     * data.
     *
     * @param id                  numerical identifier.
     * @param detailUpdateRequest {@link DetailUpdateRequest detail update request}.
     * @return no content response.
     * @throws DetailNotFoundException        if there is no {@link Detail detail} with the requested id, an exception
     *                                        is thrown.
     * @throws DetailAlreadyExistingException if there is an equal {@link Detail detail} existing, an exception is
     *                                        thrown.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDetail(@PathVariable Long id,
                                             @RequestBody @Valid DetailUpdateRequest detailUpdateRequest)
            throws DetailNotFoundException, DetailAlreadyExistingException {
        Detail detail = this.detailService.getDetailById(id);
        this.detailService.validateUniqueness(detailUpdateRequest);
        this.detailService.updateDetail(detail, detailUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns a no content response after deleting a {@link Detail detail} identified by its id.
     *
     * @param id numerical identifier.
     * @return no content response.
     * @throws DetailNotFoundException if there is no {@link Detail detail} with the requested id, an exception is
     *                                 thrown.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetail(@PathVariable Long id) throws DetailNotFoundException {
        Detail detail = this.detailService.getDetailById(id);
        this.detailService.deleteDetail(detail);
        return ResponseEntity.noContent().build();
    }
}
