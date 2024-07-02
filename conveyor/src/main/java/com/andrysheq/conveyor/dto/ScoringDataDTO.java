package com.andrysheq.conveyor.dto;

import com.andrysheq.conveyor.enums.Gender;
import com.andrysheq.conveyor.enums.MaritalStatus;
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
public class ScoringDataDTO {
    @Schema(description = "Сумма")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "Срок")
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

    @Schema(description = "Фамилия клиента")
    @NotNull
    private Gender gender;

    @Schema(description = "Дата рождения клиента")
    @NotNull
    private LocalDate birthDate;

    @Schema(description = "Серия паспорта")
    @NotNull
    private String passportSeries;

    @Schema(description = "Номер паспорта")
    @NotNull
    private String passportNumber;

    @Schema(description = "Дата истечения срока действительности паспорта")
    @NotNull
    private LocalDate passportIssueDate;

    @Schema(description = "Отделение выдачи паспорта")
    @NotNull
    private String passportIssueBranch;

    @Schema(description = "Семейное положение")
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество иждивенцов")
    @NotNull
    private Integer dependentAmount;

    @Schema(description = "Информация о трудоустройстве клиента")
    @NotNull
    private EmploymentDTO employment;

    @Schema(description = "Идентификатор аккаунта")
    @NotNull
    private String account;

    @Schema(description = "Наличие страховки кредита")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Наличие зарплаты")
    @NotNull
    private Boolean isSalaryClient;
}
