package com.andrysheq.application.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;

@Entity
@Table(name = "loan_offer")
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о сохраненном предложении кредита")
public class LoanOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(description = "Общая сумма", example = "1000000.00")
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Schema(description = "Запрашиваемая сумма", example = "1000000.00")
    @Column(name = "requested_amount")
    private BigDecimal requestedAmount;

    @Schema(description = "Срок кредитования", example = "12")
    @Column(name = "term")
    private Integer term;

    @Schema(description = "Ежемесячный платеж", example = "10000.00")
    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Schema(description = "Процентная ставка", example = "11.00")
    @Column(name = "rate")
    private BigDecimal rate;

    @Schema(description = "Наличие страхования", example = "true")
    @Column(name = "is_insurance_enabled")
    private Boolean isInsuranceEnabled;

    @Schema(description = "Наличие заработной платы у клиента", example = "true")
    @Column(name = "is_salary_client")
    private Boolean isSalaryClient;

    @ManyToOne(optional = false)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false)
    private ApplicationEntity application;
}
