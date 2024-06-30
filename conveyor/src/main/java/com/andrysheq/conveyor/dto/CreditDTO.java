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
    @Schema(description = "")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "")
    @NotNull
    private Integer term;

    @Schema(description = "")
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(description = "")
    @NotNull
    private BigDecimal rate;

    @Schema(description = "")
    @NotNull
    private BigDecimal psk;

    @Schema(description = "")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Маркер трудоустроенного клиента")
    @NotNull
    private Boolean isSalaryClient;

    @Schema(description = "")
    @NotNull
    private List<PaymentScheduleElement> paymentSchedule;
}
