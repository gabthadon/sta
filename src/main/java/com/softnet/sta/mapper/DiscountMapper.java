package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.Discount;
import com.softnet.sta.dto.response.DiscountResponse;

public class DiscountMapper {

    public static DiscountResponse toDiscountResponseMapper(Discount discountCode) {

        return new DiscountResponse(
                discountCode.getId(),
                discountCode.getCode(),
                discountCode.getName(),
                discountCode.getDiscountPercentage(),
                discountCode.getExpiryDate(),
                discountCode.getIsActive(),
                discountCode.getUsageLimit(),
                discountCode.getUsageCount()

        );
    }
}
