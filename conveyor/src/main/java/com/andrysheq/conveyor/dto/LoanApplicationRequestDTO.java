package com.andrysheq.conveyor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Запрос для получения кредита")
public class LoanApplicationRequestDTO {
    @Schema(description = "Сумма кредита")
    @NotNull
    private BigDecimal amount;
    @Schema(description = "Срок кредитования")
    @NotNull
    private Integer term;
    @Schema(description = "Имя клиента")
    @NotNull
    private String firstName;
    @Schema(description = "Фамилия клиента")
    @NotNull
    private String lastName;
    @Schema(description = "Отчество клиента")
    @NotNull
    private String middleName;
    @Schema(description = "Адрес электронной почты")
    @NotNull
    private String email;
    @Schema(description = "Дата рождения клиента")
    @NotNull
    private LocalDate birthDate;
    @Schema(description = "Серия паспорта")
    @NotNull
    private String passportSeries;
    @Schema(description = "Номер паспорта")
    @NotNull
    private String passportNumber;

}
