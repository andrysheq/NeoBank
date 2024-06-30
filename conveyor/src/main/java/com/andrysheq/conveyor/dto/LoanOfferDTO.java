package com.andrysheq.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class LoanOfferDTO {
    @Schema(description = "Идентификатор сеанса")
    @NotNull
    private Long applicationId;

    @Schema(description = "Общее количество")
    @NotNull
    private BigDecimal totalAmount;

    @Schema(description = "Ожидаемое количество")
    @NotNull
    private BigDecimal requestedAmount;

    @Schema(description = "")
    @NotNull
    private Integer term;

    @Schema(description = "Ежемесячный платеж")
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(description = "")
    @NotNull
    private BigDecimal rate;

    @Schema(description = "")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Маркер трудоустроенного клиента")
    @NotNull
    private Boolean isSalaryClient;
}
