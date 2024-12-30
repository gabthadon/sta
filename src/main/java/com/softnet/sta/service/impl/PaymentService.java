package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.StaTransaction;
import com.softnet.sta.dto.response.StaTransactionResponse;
import com.softnet.sta.enums.PaymentGatewayType;
import com.softnet.sta.mapper.StaTransactionMapper;
import com.softnet.sta.repository.StaTransactionRepository;
import com.softnet.sta.service.interfaces.PaymentProcessor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;


@Service
public class PaymentService {

    @Autowired
    private  StaTransactionRepository staTransactionRepository;

    private Map<String, PaymentProcessor> paymentProcessorMap;

    public PaymentService(List<PaymentProcessor> paymentProcessors) {
        paymentProcessorMap = paymentProcessors.stream()
                .collect(Collectors.toMap(processor -> processor.getClass().getSimpleName(), Function.identity()));
    }

    public String processPayment(PaymentGatewayType paymentGatewayType, Object paymentRequest) {
        PaymentProcessor paymentProcessor = paymentProcessorMap.get(paymentGatewayType.name() + "Adapter");
        paymentProcessor.makePayment(paymentRequest);
        return "Success";
    }



    //Get All Transactions With Various filters
    public Page<StaTransactionResponse> getFilteredTransactions(String startDate, String endDate, String registrationType, String category, String cohort, int page) {
        Specification<StaTransaction> spec = Specification.where(filterByDateRange(startDate, endDate))
                .and(filterByRegistrationType(registrationType))
                .and(filterByCategory(category))
                .and(filterByCohort(cohort));

        Pageable pageable = PageRequest.of(page, 10);
        Page<StaTransaction> res = staTransactionRepository.findAll(spec, pageable);

        // Map the Page of StaTransaction to Page of StaTransactionResponse
        return StaTransactionMapper.toStaTransactionResponse(res);
    }



    public static Specification<StaTransaction> filterByDateRange(String startDate, String endDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (startDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate));
            }
            if (endDate != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate));
            }
            return predicate;
        };
    }

    public static Specification<StaTransaction> filterByRegistrationType(String registrationType) {
        return (root, query, criteriaBuilder) ->
                registrationType != null ? criteriaBuilder.equal(root.get("registrationType"), registrationType) : null;
    }

    public static Specification<StaTransaction> filterByCategory(String category) {
        return (root, query, criteriaBuilder) ->
                category != null ? criteriaBuilder.equal(root.get("category"), category) : null;
    }

    public static Specification<StaTransaction> filterByCohort(String cohort) {
        return (root, query, criteriaBuilder) ->
                cohort != null ? criteriaBuilder.equal(root.get("cohort"), cohort) : null;
    }
}
