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

    @Schema(description = "Пол", example = "MALE")
    @NotNull
    private Gender gender;

    @Schema(description = "Семейное положение", example = "MARRIED")
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(description = "Количество иждивенцев", example = "1")
    @NotNull
    private Integer dependentAmount;

    @Schema(description = "Дата выдачи паспорта", example = "2024-07-01")
    @NotNull
    private LocalDate passportIssueDate;

    @Schema(description = "Кем выдан паспорт", example = "ОМВД РОССИИ ПО ПЕНЗЕНСКОЙ ОБЛАСТИ")
    @NotNull
    private String passportIssueBranch;

    @Schema(description = "Трудовая книга",
            example = "[{\"employmentStatus\":\"UNEMPLOYED\",{\"employerInn\":\"123456789123\",\"salary\":12345.00}," +
                    "{\"position\":\"TOP_MANAGER\",\"workExperienceTotal\":24},\"workExperienceCurrent\":12}]")
    @NotNull
    private EmploymentDTO employment;

    @Schema(description = "Номер аккаунта", example = "andrey123")
    @NotNull
    private String account;

}
