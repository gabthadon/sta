package com.softnet.sta.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlutterWaveDto {
    private String status;
    private String transactionId;
    private String transactionRef;
    private String flutterwaveRef;
    private String currency;
    private BigDecimal amount;
    private BigDecimal chargedAmount;
    private String chargeResponseCode;
    private String chargeResponseMessage;
    private String createdAt;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private String invoiceNumber;
}
