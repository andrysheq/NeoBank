package com.andrysheq.deal.dto;

import com.andrysheq.deal.enums.Gender;
import com.andrysheq.deal.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для завершения регистрации заявки")
public class FinishRegistrationRequestDTO {

    @Schema(description = "Пол")
    @NotNull
    private Gender gender;

    @Schema(description = "Семейное положение")
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество иждивенцев")
    @NotNull
    private Integer dependentAmount;

    @Schema(description = "Дата выдачи паспорта")
    @NotNull
    private LocalDate passportIssueDate;

    @Schema(description = "Кем выдан паспорт")
    @NotNull
    private String passportIssueBranch;

    @Schema(description = "Трудовая книга")
    @NotNull
    private EmploymentDTO employment;

    @Schema(description = "Номер аккаунта")
    @NotNull
    private String account;

}
