package com.andrysheq.deal.dto;

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
@Schema(description = "Запрос для скоринга")
public class LoanOfferDTO {

    @Schema(description = "Идентификатор заявки", example = "1")
    private Long applicationId;

    @Schema(description = "Общая сумма", example = "1000000.00")
    @NotNull
    private BigDecimal totalAmount;

    @Schema(description = "Запрашиваемая сумма", example = "1000000.00")
    @NotNull
    private BigDecimal requestedAmount;

    @Schema(description = "Срок кредитования", example = "12")
    @NotNull
    private Integer term;

    @Schema(description = "Ежемесячный платеж", example = "10000.00")
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(description = "Процентная ставка", example = "11.00")
    @NotNull
    private BigDecimal rate;

    @Schema(description = "Наличие страхования", example = "true")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Наличие заработной платы у клиента", example = "true")
    @NotNull
    private Boolean isSalaryClient;
}
