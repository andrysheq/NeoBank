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
    @Schema(description = "")
    @NotNull
    private BigDecimal amount;

    @Schema(description = "")
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

    @Schema(description = "")
    @NotNull
    private String passportIssueBranch;

    @Schema(description = "Семейное положение")
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(description = "")
    @NotNull
    private Integer dependentAmount;

    @Schema(description = "")
    @NotNull
    private EmploymentDTO employment;

    @Schema(description = "")
    @NotNull
    private String account;

    @Schema(description = "")
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(description = "Маркер трудоустроенного клиента")
    @NotNull
    private Boolean isSalaryClient;
}
