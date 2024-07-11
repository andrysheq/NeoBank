package com.andrysheq.application.dto;

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
@Schema(description = "Запрос для получения кредита")
public class LoanApplicationRequestDTO {

    @Schema(description = "Сумма кредита", example = "1000000.00")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "Срок кредитования", example = "12")
    @NotNull
    private Integer term;

    @Schema(description = "Имя клиента", example = "Andrey")
    @NotNull
    private String firstName;

    @Schema(description = "Фамилия клиента", example = "Andreev")
    @NotNull
    private String lastName;

    @Schema(description = "Отчество клиента", example = "Andreevich")
    @NotNull
    private String middleName;

    @Schema(description = "Адрес электронной почты", example = "andrey_neobank@mail.ru")
    @NotNull
    private String email;

    @Schema(description = "Дата рождения клиента", example = "2002-07-01")
    @NotNull
    private LocalDate birthDate;

    @Schema(description = "Серия паспорта", example = "1324")
    @NotNull
    private String passportSeries;

    @Schema(description = "Номер паспорта", example = "465768")
    @NotNull
    private String passportNumber;

}
