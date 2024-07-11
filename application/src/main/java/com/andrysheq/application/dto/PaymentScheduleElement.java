package com.andrysheq.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о платеже")
public class PaymentScheduleElement {

    @Schema(description = "Номер платежа", example = "1")
    @NotNull
    private Integer number;

    @Schema(description = "Дата платежа", example = "2024-07-01")
    @NotNull
    private LocalDate date;

    @Schema(description = "Общая сумма платежа", example = "10000.00")
    @NotNull
    private BigDecimal totalPayment;

    @Schema(description = "Сумма выплаты процентов", example = "2000.00")
    @NotNull
    private BigDecimal interestPayment;

    @Schema(description = "Сумма выплаты долга", example = "8000.00")
    @NotNull
    private BigDecimal debtPayment;

    @Schema(description = "Оставшаяся сумма долга", example = "100000.00")
    @NotNull
    private BigDecimal remainingDebt;
}
