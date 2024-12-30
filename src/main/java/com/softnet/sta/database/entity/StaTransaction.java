package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import com.softnet.sta.enums.PaymentGatewayType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StaTransaction extends BaseEntity {

    private String status;
    private String transactionId;
    private String transactionRef;
    private String currency;
    private BigDecimal amount;
    private BigDecimal chargedAmount;
    private String chargeResponseCode;
    private String chargeResponseMessage;
//    private String createdAt;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    @Enumerated(EnumType.STRING)
    private PaymentGatewayType paymentGatewayType;

    private String enrollmentId;
}
