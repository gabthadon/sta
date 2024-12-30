package com.softnet.sta.dto.request;

import com.softnet.sta.database.entity.Packages;
import com.softnet.sta.enums.DiscountType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DiscountRequest {



    private String name;
    private Integer discountPercentage;
    private LocalDateTime expiryDate;
    private Boolean isActive;
    private Long usageLimit;

}
