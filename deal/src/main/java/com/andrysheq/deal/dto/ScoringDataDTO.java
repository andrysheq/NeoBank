package com.andrysheq.deal.dto;

import com.andrysheq.deal.enums.Gender;
import com.andrysheq.deal.enums.MaritalStatus;
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
@Schema(description = "Запрос для скоринговой системы")
public class ScoringDataDTO {

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

    @Schema(description = "Пол")
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

    @Schema(description = "Дата выдачи паспорта")
    @NotNull
    private LocalDate passportIssueDate;

    @Schema(description = "Отделение выдачи паспорта")
    @NotNull
    private String passportIssueBranch;

    @Schema(description = "Семейное положение")
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество иждивенцев")
    @NotNull
    private Integer dependentAmount;

    @Schema(description = "Информация о трудоустройстве клиента")
    @NotNull
    private EmploymentDTO employment;

    @Schema(description = "Идентификатор аккаунта")
    @NotNull
    private String account;

    @Schema(description = "Наличие страхования кредита")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Наличие заработной платы")
    @NotNull
    private Boolean isSalaryClient;
}
