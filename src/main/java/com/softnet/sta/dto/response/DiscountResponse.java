package com.softnet.sta.dto.response;

import com.softnet.sta.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class DiscountResponse {

    private Long id;
    private String code;
    private String name;
    private Integer discountPercentage;
    private LocalDateTime expiryDate;
    private Boolean isActive;
    private Long usageLimit;
    private Long usageCount ;

}
