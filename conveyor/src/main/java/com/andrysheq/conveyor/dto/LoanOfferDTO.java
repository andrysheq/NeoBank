package com.andrysheq.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class LoanOfferDTO {
    @Schema(description = "Идентификатор сеанса")
    @NotNull
    private Long applicationId;

    @Schema(description = "Общая сумма")
    @NotNull
    private BigDecimal totalAmount;

    @Schema(description = "Запрашиваемое количество")
    @NotNull
    private BigDecimal requestedAmount;

    @Schema(description = "Срок")
    @NotNull
    private Integer term;

    @Schema(description = "Ежемесячный платеж")
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(description = "Процентная ставка")
    @NotNull
    private BigDecimal rate;

    @Schema(description = "Страховка")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Маркер трудоустроенного клиента")
    @NotNull
    private Boolean isSalaryClient;
}
