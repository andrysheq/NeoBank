package com.andrysheq.deal.entity;

import com.andrysheq.deal.dto.PaymentScheduleElement;
import com.andrysheq.deal.enums.CreditStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credit")
@Getter
@Setter
public class CreditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;
    @ElementCollection
    @CollectionTable(name = "payment_schedule", joinColumns = @JoinColumn(name = "credit_id"))
    private List<PaymentScheduleElement> paymentSchedule;

}

