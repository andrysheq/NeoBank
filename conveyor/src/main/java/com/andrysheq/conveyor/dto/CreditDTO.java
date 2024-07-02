package com.andrysheq.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    @Schema(description = "Сумма")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "Срок")
    @NotNull
    private Integer term;

    @Schema(description = "Ежемесячный платеж")
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(description = "Ставка")
    @NotNull
    private BigDecimal rate;

    @Schema(description = "ПСК")
    @NotNull
    private BigDecimal psk;

    @Schema(description = "Наличие страховки кредита")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Наличие заработной платы у клиента")
    @NotNull
    private Boolean isSalaryClient;

    @Schema(description = "Расписание платежей")
    @NotNull
    private List<PaymentScheduleElement> paymentSchedule;
}
