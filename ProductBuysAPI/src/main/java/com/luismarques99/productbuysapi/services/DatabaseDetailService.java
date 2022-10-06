package com.luismarques99.productbuysapi.services;

import com.luismarques99.productbuysapi.exceptions.DetailAlreadyExistingException;
import com.luismarques99.productbuysapi.exceptions.DetailNotFoundException;
import com.luismarques99.productbuysapi.models.Detail;
import com.luismarques99.productbuysapi.models.dtos.DetailCreateRequest;
import com.luismarques99.productbuysapi.models.dtos.DetailRequest;
import com.luismarques99.productbuysapi.models.dtos.DetailUpdateRequest;
import com.luismarques99.productbuysapi.repositories.DetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Class that represents the implementation of the {@link DetailService Detail Service} that uses a database as the
 * datasource.
 *
 * @author Luis Marques
 */
@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class DatabaseDetailService implements DetailService {

    /**
     * {@link DetailRepository Detail Repository}.
     */
    private final DetailRepository detailRepository;

    @Override
    public void saveDetail(Detail detail) {
        log.info("\nSaving detail: {}", detail.toString());
        this.detailRepository.save(detail);
    }

    @Override
    public Detail createDetail(DetailCreateRequest detailCreateRequest) throws DetailAlreadyExistingException {
        // This is a list that can only contain one element.
        this.validateUniqueness(detailCreateRequest);
        return new Detail(detailCreateRequest.getDescription(), detailCreateRequest.getQuantity(),
                detailCreateRequest.getValue());
    }

    @Override
    public Detail getDetailById(Long id) throws DetailNotFoundException {
        log.info("\nFetching detail with id: {}", id);
        return this.detailRepository.findById(id).orElseThrow(() -> new DetailNotFoundException(id));
    }

    @Override
    public List<Detail> getDetailsByEqualityRules(String description, Integer quantity, Double value) {
        log.info("\nFetching details with:\n  >Description: {}\n  >Quantity: {}\n  >Value: {}", description, quantity,
                value);
        return this.detailRepository.findByEqualityRules(description, quantity, value);
    }

    @Override
    public List<Detail> getDetailsByExpressionContainedInDescription(String expression) {
        log.info("\nFetching details with expression contained in description: '{}'", expression);
        return this.detailRepository.findByExpressionContainedInDescription(expression);
    }

    @Override
    public List<Detail> getAllDetails() {
        log.info("\nFetching all details");
        return this.detailRepository.findAll();
    }

    @Override
    public void updateDetail(Detail detail, DetailUpdateRequest detailUpdateRequest) {
        log.info("\nUpdating detail: {}\n  >New one: {}", detail.toString(), detailUpdateRequest.toString());
        detail.setDescription(detailUpdateRequest.getDescription());
        detail.setQuantity(detailUpdateRequest.getQuantity());
        detail.setValue(detailUpdateRequest.getValue());
    }

    @Override
    public void deleteDetail(Detail detail) {
        log.info("\nDeleting detail: {}", detail);
        this.detailRepository.delete(detail);
    }

    @Override
    public void validateUniqueness(DetailRequest detailRequest) throws DetailAlreadyExistingException {
        log.info("\nValidating uniqueness on detail: {}", detailRequest.toString());
        // This is a list that can only contain one element.
        List<Detail> equalDetails = this.getDetailsByEqualityRules(detailRequest.getDescription(),
                detailRequest.getQuantity(), detailRequest.getValue());
        if (!equalDetails.isEmpty()) {
            throw new DetailAlreadyExistingException(equalDetails.get(0).getId());
        }
    }
}
