package com.softnet.sta.dto.response;

import com.softnet.sta.enums.PaymentGatewayType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaTransactionResponse {

    private String status;
    private String transactionId;
    private String transactionRef;
    private String currency;
    private BigDecimal amount;
    private BigDecimal chargedAmount;
    private String chargeResponseCode;
    private String chargeResponseMessage;
    private String createdAt;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private PaymentGatewayType paymentGatewayType;
    private String enrollmentId;
}
