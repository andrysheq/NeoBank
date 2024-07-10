package com.andrysheq.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о платеже")
public class PaymentScheduleElement {

    @Schema(description = "Номер платежа")
    @NotNull
    private Integer number;

    @Schema(description = "Дата платежа")
    @NotNull
    private LocalDate date;

    @Schema(description = "Общая сумма платежа")
    @NotNull
    private BigDecimal totalPayment;

    @Schema(description = "Сумма выплаты процентов")
    @NotNull
    private BigDecimal interestPayment;

    @Schema(description = "Сумма выплаты долга")
    @NotNull
    private BigDecimal debtPayment;

    @Schema(description = "Оставшаяся сумма долга")
    @NotNull
    private BigDecimal remainingDebt;
}
