package com.andrysheq.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class LoanOfferDTO {
    @Schema(description = "Идентификатор сеанса")
    private Long applicationId;

    @Schema(description = "Общая сумма")
    @NotNull
    private BigDecimal totalAmount;

    @Schema(description = "Запрашиваемая сумма")
    @NotNull
    private BigDecimal requestedAmount;

    @Schema(description = "Срок кредитования")
    @NotNull
    private Integer term;

    @Schema(description = "Ежемесячный платеж")
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(description = "Процентная ставка")
    @NotNull
    private BigDecimal rate;

    @Schema(description = "Наличие страхования")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Наличие заработной платы у клиента")
    @NotNull
    private Boolean isSalaryClient;
}
