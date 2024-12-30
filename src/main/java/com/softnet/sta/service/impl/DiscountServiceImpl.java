package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.Discount;
import com.softnet.sta.database.entity.Packages;
import com.softnet.sta.dto.request.DiscountRequest;
import com.softnet.sta.dto.response.DiscountResponse;
import com.softnet.sta.exception.NotFoundException;
import com.softnet.sta.mapper.DiscountMapper;
import com.softnet.sta.repository.DiscountRepository;
import com.softnet.sta.repository.PackageRepository;
import com.softnet.sta.service.interfaces.DiscountService;
import com.softnet.sta.util.IDGenerator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountCodeRepository;
    private final PackageRepository packageRepository;

    @Transactional
    @Override
    public DiscountResponse createDiscount(DiscountRequest discountRequest) {
        // Generate Discount code
        String code = IDGenerator.generateDiscountCode(6);

        // Create and populate DiscountCode object
        Discount discountCode = new Discount();
        discountCode.setCode(code);
        discountCode.setDiscountPercentage(discountRequest.getDiscountPercentage());
        discountCode.setName(discountRequest.getName());
        discountCode.setIsActive(discountRequest.getIsActive());
        discountCode.setUsageCount(0L);
        discountCode.setUsageLimit(discountRequest.getUsageLimit());
        discountCode.setExpiryDate(discountRequest.getExpiryDate());
       // Set the Packages entity

        // Save DiscountCode to the repository
        discountCodeRepository.save(discountCode);

        // Return the mapped response
        return DiscountMapper.toDiscountResponseMapper(discountCode);
    }

    @Override
    public List<DiscountResponse> getAllDiscounts() {
        List<Discount> discounts = discountCodeRepository.findAll();
        return discounts.stream()
                .map(DiscountMapper::toDiscountResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public DiscountResponse getDiscountById(Long id) {
        Discount discount = discountCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with id " + id));
        return DiscountMapper.toDiscountResponseMapper(discount);
    }

    @Transactional
    @Override
    public DiscountResponse updateDiscount(Long id, DiscountRequest discountRequest) {
        Discount discount = discountCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with id " + id));

        discount.setDiscountPercentage(discountRequest.getDiscountPercentage());
        discount.setName(discountRequest.getName());
        discount.setIsActive(discountRequest.getIsActive());
        discount.setUsageLimit(discountRequest.getUsageLimit());
        discount.setExpiryDate(discountRequest.getExpiryDate());

        Discount updatedDiscount = discountCodeRepository.save(discount);
        return DiscountMapper.toDiscountResponseMapper(updatedDiscount);
    }

    @Transactional
    @Override
    public void deleteDiscountById(Long id) {
        Discount discount = discountCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with id " + id));
        discountCodeRepository.delete(discount);
    }

}
