package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.DiscountRequest;
import com.softnet.sta.dto.response.DiscountResponse;

import java.util.List;

public interface DiscountService {
    DiscountResponse createDiscount(DiscountRequest discountRequest);

    List<DiscountResponse> getAllDiscounts();

    DiscountResponse getDiscountById(Long id);

    DiscountResponse updateDiscount(Long id, DiscountRequest discountRequest);

    void deleteDiscountById(Long id);
}
