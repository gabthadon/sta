package com.softnet.sta.controller;

import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.dto.response.StaTransactionResponse;
import com.softnet.sta.enums.PaymentGatewayType;
import com.softnet.sta.service.impl.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;



    @PostMapping
    public ResponseEntity<ApiResponse<String>> makePayment(@RequestParam(value = "paymentType", defaultValue = "REMITA") PaymentGatewayType paymentType, @RequestBody Object paymentRequest) {
        try {
            String response = paymentService.processPayment(paymentType, paymentRequest );
            if (response.equals("Success")) {
                return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Payment Successful", response));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Save Payment", null));
            }
        } catch (Exception e) {
            log.error("Error Logging In", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Save Payment", null));

        }
    }



    @GetMapping("/get/all/transactions")
    public  ResponseEntity<ApiResponse<Page<StaTransactionResponse>>> getTransactions(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "registrationType", required = false) String registrationType,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "cohort", required = false) String cohort,
            @RequestParam(value = "page", defaultValue = "0") int page) {

       Page<StaTransactionResponse> response =   paymentService.getFilteredTransactions(startDate, endDate, registrationType, category, cohort, page);

        return  ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Transaction Retreived Successfully", response));
    }

}
