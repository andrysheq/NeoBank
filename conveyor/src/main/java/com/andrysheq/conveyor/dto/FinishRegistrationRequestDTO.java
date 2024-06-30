package com.andrysheq.conveyor.dto;

import com.andrysheq.conveyor.enums.Gender;
import com.andrysheq.conveyor.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinishRegistrationRequestDTO {

    @Schema(description = "Фамилия клиента")
    @NotNull
    private Gender gender;

    @Schema(description = "Семейное положение")
    @NotNull
    private String maritalStatus;

    @Schema(description = "")
    @NotNull
    private Integer dependentAmount;

    @Schema(description = "Дата истечения срока действительности паспорта")
    @NotNull
    private LocalDate passportIssueDate;

    @Schema(description = "")
    @NotNull
    private String passportIssueBranch;

    @Schema(description = "")
    @NotNull
    private EmploymentDTO employment;

    @Schema(description = "")
    @NotNull
    private String account;

}
