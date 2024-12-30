package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Payment extends BaseEntity {

private BigDecimal amount;
    private Long refNo;
    private Long transactionId;
    private String paymentType;
    private Long studentId;


}
