package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.StaTransaction;
import com.softnet.sta.dto.response.StaTransactionResponse;
import org.springframework.data.domain.Page;

public class StaTransactionMapper {


    // Mapper Method to Convert Page of StaTransaction to Page of StaTransactionResponse
    public static Page<StaTransactionResponse> toStaTransactionResponse(Page<StaTransaction> transactions) {
        return transactions.map(transaction -> {
            StaTransactionResponse response = new StaTransactionResponse();
            response.setStatus(transaction.getStatus());
            response.setTransactionId(transaction.getTransactionId());
            response.setTransactionRef(transaction.getTransactionRef());
            response.setCurrency(transaction.getCurrency());
            response.setAmount(transaction.getAmount());
            response.setChargedAmount(transaction.getChargedAmount());
            response.setChargeResponseCode(transaction.getChargeResponseCode());
            response.setChargeResponseMessage(transaction.getChargeResponseMessage());
            response.setCustomerName(transaction.getCustomerName());
            response.setCustomerEmail(transaction.getCustomerEmail());
            response.setCustomerPhoneNumber(transaction.getCustomerPhoneNumber());
            response.setPaymentGatewayType(transaction.getPaymentGatewayType());
            response.setEnrollmentId(transaction.getEnrollmentId());

            // Map createdAt from BaseEntity if needed
            if (transaction.getCreatedDate() != null) {
                response.setCreatedAt(transaction.getCreatedDate().toString());
            }
            return response;
        });
    }
}
