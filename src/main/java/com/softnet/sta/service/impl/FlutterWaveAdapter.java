package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.StaTransaction;
import com.softnet.sta.dto.request.FlutterWaveDto;
import com.softnet.sta.enums.PaymentGatewayType;
import com.softnet.sta.repository.StaTransactionRepository;
import com.softnet.sta.service.interfaces.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlutterWaveAdapter implements PaymentProcessor {

    private final ModelMapper modelMapper;
    private final StaTransactionRepository staTransactionRepository;

    @Override
    public void makePayment(Object paymentRequest) {
        FlutterWaveDto flutterWaveDto = modelMapper.map(paymentRequest, FlutterWaveDto.class);

        saveTransaction(flutterWaveDto);

    }

    private void saveTransaction(FlutterWaveDto flutterWaveDto) {
        StaTransaction staTransaction = new StaTransaction();
        staTransaction.setAmount(flutterWaveDto.getAmount());
        staTransaction.setCurrency(flutterWaveDto.getCurrency());
        staTransaction.setChargedAmount(flutterWaveDto.getChargedAmount());
        staTransaction.setCreatedDate(flutterWaveDto.getCreatedAt());
        staTransaction.setTransactionId(flutterWaveDto.getTransactionId());
        staTransaction.setPaymentGatewayType(PaymentGatewayType.FlutterWave);
        staTransaction.setStatus(flutterWaveDto.getStatus());
        staTransaction.setTransactionRef(flutterWaveDto.getTransactionRef());
        staTransaction.setCustomerEmail(flutterWaveDto.getCustomerEmail());
        staTransaction.setCustomerName(flutterWaveDto.getCustomerName());
        staTransaction.setChargeResponseCode(flutterWaveDto.getChargeResponseCode());
        staTransaction.setChargeResponseMessage(flutterWaveDto.getChargeResponseMessage());
        staTransaction.setCustomerPhoneNumber(flutterWaveDto.getCustomerPhoneNumber());

        staTransactionRepository.save(staTransaction);
    }
}
