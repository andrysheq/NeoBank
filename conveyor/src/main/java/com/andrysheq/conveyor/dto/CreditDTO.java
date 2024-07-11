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
@Schema(description = "Запрос для получения кредита")
public class CreditDTO {
    @Schema(description = "Сумма кредита", example = "10000000.00")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "Срок кредита", example = "24")
    @NotNull
    private Integer term;

    @Schema(description = "Ежемесячный платеж", example = "100000.00")
    @NotNull
    private BigDecimal monthlyPayment;

    @Schema(description = "Процентная годовая ставка", example = "12.00")
    @NotNull
    private BigDecimal rate;

    @Schema(description = "ПСК", example = "14.00")
    @NotNull
    private BigDecimal psk;

    @Schema(description = "Наличие страховки кредита", example = "true")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Наличие заработной платы у клиента", example = "true")
    @NotNull
    private Boolean isSalaryClient;

    @Schema(description = "Расписание платежей",
            example = "[{\"numberOfPayment\":\"1\",{\"paymentDate\":\"2024-07-01\",\"totalPayment\":15000.00}," +
                    "{\"interestPayment\":\"2000.00\",\"debtPayment\":13000.00},\"remainingDebt\":130000.00}]")
    @NotNull
    private List<PaymentScheduleElement> paymentSchedule;
}
