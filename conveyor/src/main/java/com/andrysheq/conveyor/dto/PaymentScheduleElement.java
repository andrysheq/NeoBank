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
public class PaymentScheduleElement {
    @Schema(description = "")
    @NotNull
    private Integer number;

    @Schema(description = "")
    @NotNull
    private LocalDate date;

    @Schema(description = "")
    @NotNull
    private BigDecimal totalPayment;

    @Schema(description = "")
    @NotNull
    private BigDecimal interestPayment;

    @Schema(description = "")
    @NotNull
    private BigDecimal debtPayment;

    @Schema(description = "")
    @NotNull
    private BigDecimal remainingDebt;
}
