package com.softnet.sta.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseWrapper {
    private Integer TotalEnrollmentCount;
    private BigDecimal totalAmount;
    private String enrollmentId;
    private List<EnrollmentResponse> enrollmentResponses;
}

//